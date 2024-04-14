package com.lh.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

public class BigFileTest {

    @Test
    public void testChunk() throws IOException {

        File file = new File("D:\\privates.mp4");
        //分块文件存储路径
        String chunkPath="D:\\liulanqixiazai\\download\\";
        int chunkSize=1024*1024*5;
        int chunkNum=(int) Math.ceil(file.length()*1.0/chunkSize);
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");
        byte[] buffer=new byte[1024];
        for (int i = 0; i < chunkNum; i++) {
            File file1 = new File(chunkPath + i);
            RandomAccessFile rw = new RandomAccessFile(file1, "rw");
            int len=-1;
            while((len=randomFile.read(buffer))!=-1){
                rw.write(buffer,0,len);
                if(file1.length()>=chunkSize){
                    break;
                }
            }
            rw.close();
        }
        randomFile.close();

    }


    @Test
    public void testMerge() throws IOException {

        File sourceFile = new File("D:\\privates.mp4");
        //分块文件存储路径
        String chunkPath="D:\\liulanqixiazai\\download\\";
        File chunkDir = new File(chunkPath);
        File targetFile = new File("D:\\privates_2.mp4");
        RandomAccessFile targetStream = new RandomAccessFile(targetFile, "rw");
        int chunkSize=1024*1024*1;
        File[] chunkFiles = chunkDir.listFiles();
        List<File> fileList = Arrays.asList(chunkFiles);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
            }
        });

        byte[] buffer=new byte[1024];
        for (File file : fileList) {
            RandomAccessFile readStream = new RandomAccessFile(file, "r");
            int len=-1;
            while((len=readStream.read(buffer))!=-1){
                targetStream.write(buffer,0,len);
            }
            readStream.close();
        }
        targetStream.close();

        FileInputStream sourseFileInputStream = new FileInputStream(sourceFile);
        FileInputStream targetFileInputStream = new FileInputStream(targetFile);

        String soursemd5 = DigestUtils.md5Hex(sourseFileInputStream);
        String targetmd5 = DigestUtils.md5Hex(targetFileInputStream);

        if(soursemd5.equals(targetmd5)){
            System.out.println("合并完成");
        }else{
            System.out.println("合并失败");
        }


    }
}
