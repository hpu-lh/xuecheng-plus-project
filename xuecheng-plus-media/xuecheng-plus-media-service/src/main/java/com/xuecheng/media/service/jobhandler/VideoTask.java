package com.xuecheng.media.service.jobhandler;

import com.lh.base.utils.Mp4VideoUtil;
import com.xuecheng.media.model.po.MediaProcess;
import com.xuecheng.media.service.MediaFileProcessService;
import com.xuecheng.media.service.MediaFileService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class VideoTask {
    private static Logger logger = LoggerFactory.getLogger(VideoTask.class);

    @Autowired
    private MediaFileProcessService mediaFileProcessService;

    @Value("${videoprocess.ffmpegpath}")
    private String ffmpegpath;


    @Autowired
    private MediaFileService mediaFileService;

    /**
     * 2、分片广播任务
     */
    @XxlJob("videoJobHander")
    public void videoJobHander() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        List<MediaProcess> mediaProcessList = null;
        int size = 0;
        try {
            //取出cpu核心数作为一次处理数据的条数
            int processors = Runtime.getRuntime().availableProcessors();
            //一次处理视频数量不要超过cpu核心数
            mediaProcessList = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, processors);
            size = mediaProcessList.size();
            log.debug("取出待处理视频任务{}条", size);
            if (size < 0) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //启动size个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(size);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);
        //将处理任务加入线程池
        mediaProcessList.forEach(mediaProcess -> {
            threadPool.execute(() -> {
                try {
                    //任务id
                    Long taskId = mediaProcess.getId();
                    //抢占任务
                    boolean b = mediaFileProcessService.startTask(taskId);
                    if (!b) {
                        return;
                    }
                    log.debug("开始执行任务:{}", mediaProcess);
                    //下边是处理逻辑
                    //桶
                    String bucket = mediaProcess.getBucket();
                    //存储路径
                    String filePath = mediaProcess.getFilePath();
                    //原始视频的md5值
                    String fileId = mediaProcess.getFileId();
                    //原始文件名称
                    String filename = mediaProcess.getFilename();
                    //将要处理的文件下载到服务器上
                    File originalFile = mediaFileService.downloadFileFromMinIO(mediaProcess.getBucket(), mediaProcess.getFilePath());
                    if (originalFile == null) {
                        log.debug("下载待处理文件失败,originalFile:{}", mediaProcess.getBucket().concat(mediaProcess.getFilePath()));
                        mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, "下载待处理文件失败");
                        return;
                    }
                    //处理结束的视频文件
                    File mp4File = null;
                    try {
                        mp4File = File.createTempFile("temp", ".mp4");
                    } catch (IOException e) {
                        log.error("创建mp4临时文件失败");
                        mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, "创建mp4临时文件失败");
                        return;
                    }
                    //视频处理结果
                    String result = "";
                    try {
                        //开始处理视频
                        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegpath, originalFile.getAbsolutePath(), mp4File.getName(), mp4File.getAbsolutePath());
                        //开始视频转换，成功将返回success
                        result = videoUtil.generateMp4();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("处理视频文件:{},出错:{}", mediaProcess.getFilePath(), e.getMessage());
                    }
                    if (!result.equals("success")) {
                        //记录错误信息
                        log.error("处理视频失败,视频地址:{},错误信息:{}", bucket + filePath, result);
                        mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, result);
                        return;
                    }

                    //将mp4上传至minio
                    //mp4在minio的存储路径
                    String objectName = getFilePath(fileId, ".mp4");
                    //访问url
                    String url = "/" + bucket + "/" + objectName;
                    try {
                        mediaFileService.addMediaFilesToMinIO(mp4File.getAbsolutePath(), "video/mp4", bucket, objectName);
                        //将url存储至数据，并更新状态为成功，并将待处理视频记录删除存入历史
                        mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "2", fileId, url, null);
                    } catch (Exception e) {
                        log.error("上传视频失败或入库失败,视频地址:{},错误信息:{}", bucket + objectName, e.getMessage());
                        //最终还是失败了
                        mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, "处理后视频上传或入库失败");
                    }
                }finally {
                    countDownLatch.countDown();
                }
            });
        });
        //等待,给一个充裕的超时时间,防止无限等待，到达超时时间还没有处理完成则结束任务
        countDownLatch.await(30, TimeUnit.MINUTES);


//        // 分片参数
//        int shardIndex = XxlJobHelper.getShardIndex();//执行器的序号
//        int shardTotal = XxlJobHelper.getShardTotal();//执行器总数
//        int processors = Runtime.getRuntime().availableProcessors();
//        List<MediaProcess> mediaProcessList = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, processors);
//        int size = mediaProcessList.size();
//        if(size<=0){
//            log.debug("取到的视频任务数：{}",size);
//            return;
//        }
//        ExecutorService executorService = Executors.newFixedThreadPool(size);
//        CountDownLatch countDownLatch = new CountDownLatch(size);
//
//        mediaProcessList.forEach(mediaProcess->{
//            executorService.execute(()->{
//                try {
//                    Long id = mediaProcess.getId();
//                    boolean result = mediaFileProcessService.startTask(id);
//                    if(!result){
//                        log.debug("抢占任务失败：{}",id);
//                        return;
//                    }
//
//                    String bucket = mediaProcess.getBucket();
//                    String objectName = mediaProcess.getFilePath();
//                    String fileMD5 = mediaProcess.getFileId();
//                    //ffmpeg的路径
//                    //String ffmpeg_path = "D:\\soft\\ffmpeg\\ffmpeg.exe";//ffmpeg的安装位置
//                    //源avi视频的路径
//                    File file = mediaFileService.downloadFileFromMinIO(bucket, objectName);
//                    if(file==null) {
//                        log.debug("下载任务失败：{}",bucket+objectName);
//                        mediaFileProcessService.saveProcessFinishStatus(id,"3",fileMD5,null,"下载视频到本地失败");
//                        return;
//                    }
//                    String absolutePath = file.getAbsolutePath();
//
//                    String video_path = absolutePath;
//                    //转换后mp4文件的名称
//                    String mp4_name = fileMD5+".mp4";
//
//                    //处理结束的视频文件
//                    File mp4File = null;
//                    try {
//                        mp4File = File.createTempFile("temp", ".mp4");
//                    } catch (IOException e) {
//                        log.error("创建mp4临时文件失败");
//                        mediaFileProcessService.saveProcessFinishStatus(id,"3",fileMD5,null,"创建临时文件失败");
//                    }
//                    String mp4_path = mp4File.getAbsolutePath();
//                    //转换后mp4文件的路径
//                    //String mp4_path = "D:\\develop\\bigfile_test\\";
//                    //创建工具类对象
//                    Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegpath,video_path,mp4_name,mp4_path);
//                    //开始视频转换，成功将返回success
//                    String resultMP4 = videoUtil.generateMp4();
//                    if(!"success".equals(resultMP4)){
//                        log.debug("视频转码失败：{}",bucket+objectName);
//                        mediaFileProcessService.saveProcessFinishStatus(id,"3",fileMD5,null,"视频转码失败");
//                    }
//                    boolean b = mediaFileService.addMediaFilesToMinIO(mp4File.getAbsolutePath(), "video/mp4", bucket, mp4_name);
//                    if(!b){
//                        log.debug("上传视频到minio失败：{}",id);
//                        mediaFileProcessService.saveProcessFinishStatus(id,"3",fileMD5,null,"上传视频到minio失败");
//                    }
//
//                    String url = getFilePath(fileMD5, ".mp4");
//                    mediaFileProcessService.saveProcessFinishStatus(id,"2",fileMD5,url,"");
//                    countDownLatch.countDown();
//                }finally {
//                    countDownLatch.countDown();
//                }
//            });
//
//        });
//        //最多等待时间
//        countDownLatch.await(30,TimeUnit.MINUTES);
    }

    private String getFilePath(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }


}
