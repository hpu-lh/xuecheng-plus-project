package com.lh.media;

import com.alibaba.nacos.common.utils.IoUtils;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MinioTest {
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();


    @Test
    public void testUpload() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().
                bucket("test").
                filename("D:\\zuomianwenjian\\sitama.jpg").
                object("sitama.jpg").build();
        minioClient.uploadObject(uploadObjectArgs);



    }

    @Test
    public void testDelete() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().
                bucket("test").object("data1").build();

        minioClient.removeObject(removeObjectArgs);

    }

    @Test
    public void testSelect() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        GetObjectArgs getObjectArgs =GetObjectArgs.builder().bucket("test").object("data1").build();
        FilterInputStream filterInputStream = minioClient.getObject(getObjectArgs);

        FileOutputStream fileOutputStream=new FileOutputStream(new File("D:\\zuomianwenjian\\data1.pdf"));
        IoUtils.copy(filterInputStream,fileOutputStream);

        //对文件的完整性进行校验

        FileInputStream inputStream=new FileInputStream(new File("D:\\zuomianwenjian\\data1.pdf"));
        String sourse_md5 = DigestUtils.md5Hex(filterInputStream);
        String target_md5 = DigestUtils.md5Hex(inputStream);
        if(sourse_md5.equals(target_md5))
            System.out.println("下载成功");
    }

    @Test
    public void uploadChunk() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File chunkPath = new File("D:\\liulanqixiazai\\download\\");
        int length = chunkPath.listFiles().length;
        for (int i = 0; i < length; i++) {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().
                    bucket("test").
                    filename("D:\\liulanqixiazai\\download\\"+i).
                    object("chunk/"+i).build();
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传文件分块"+i+"成功");


        }

    }

    @Test
    public void testMerge() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<ComposeSource> list=new ArrayList<>();
        File chunkPath = new File("D:\\liulanqixiazai\\download\\");
        int length = chunkPath.listFiles().length;
        ComposeSource composeSource=null;
        for (int i = 0; i < length; i++) {
            composeSource= ComposeSource.builder().bucket("test").object("chunk/" + i).build();
            list.add(composeSource);
        }

        ComposeObjectArgs composeObjectArgs=ComposeObjectArgs.builder().
                bucket("test").object("merger01.mp4").sources(list).build();
        minioClient.composeObject(composeObjectArgs);
    }
}
