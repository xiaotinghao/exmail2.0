package com.xs.common.utils.sftp;

import java.io.File;
import java.util.Vector;

import static com.xs.common.utils.sftp.SftpConstants.*;

/**
 * 文件传输工具测试类
 *
 * @author xiaotinghao
 */
public class SftpUtilsTest {

    public static void main(String[] args) {
        SftpUtils sf = new SftpUtils();
        String host = HOST;
        int port = PORT;
        String username = USERNAME;
        String password = PASSWORD;
        // 创建sftp方式的连接
        sf.getConnect(host, port, username, password);

        String directory = "/home/usr/";

        /**
         * 上传附件
         */
//        String uploadFile = "/home/airport/airport.xml";
//        sf.upload(directory, uploadFile);
//        System.out.println("上传成功");

        /**
         * 下载附件
         */
//        String downloadFile = "airport.xml";
//        String saveFile = "/home/download/test.xml";
//        sf.download(directory, downloadFile, saveFile);
//        System.out.println("下载成功");

        /**
         * 删除附件
         */
        String deleteFile = "airport.xml";
        sf.delete(directory, deleteFile);
        System.out.println("删除成功");

        Vector<String> objects = sf.listFiles("/home/download/");
        System.out.println(objects.size());
        System.out.println(objects.get(0));

        // 关闭连接
        sf.closeConnect();

    }

}
