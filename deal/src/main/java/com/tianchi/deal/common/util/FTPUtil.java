//package com.tianchi.deal.common.util;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * @Author: zhouheng
// * @Created: with IntelliJ IDEA.
// * @Description:
// * @Date: 2020-05-14
// * @Time: 14:30
// */
//public class FTPUtil {
//
//
//    /**
//     * 获取某个日期的日终收益
//     *
//     * @param date
//     * @return
//     */
//    public String syncFundPath(String date, String name) {
//        FTPClient ftpClient = null;
//        try {
//            ftpClient = getFtpClient();
//            ftpClient.enterLocalPassiveMode();
//            // 设置传输二进制文件
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            int reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                throw new IOException("failed to connect to the FTP Server:");
//            }
//            // ftp文件获取文件
//            String filename = getFundFileName(date, name);
//            InputStream inputStream = ftpClient.retrieveFileStream(filename);
//            if (inputStream == null) {//文件不存在
//                return null;
//            }
//            String path = getCommonFilePath(filename);
//            File file = new File(path);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            FileOutputStream fileOutputStream = new FileOutputStream(path);
//            int offset = -1;
//            byte[] bytes = new byte[1024];
//            while ((offset = inputStream.read(bytes)) != -1) {
//                fileOutputStream.write(bytes, 0, offset);
//            }
//            fileOutputStream.close();
//            inputStream.close();
//            return path;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient != null) {
//                try {
//                    if (ftpClient.isConnected()) {
//                        ftpClient.logout();
//                        ftpClient.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//        return "";//为空说明没有下载成功或者网络异常，需要重新下载
//    }
//
//    /**
//     * 获取FTP客户端
//     *
//     * @return
//     * @throws Exception
//     */
//    private FTPClient getFtpClient() throws Exception {
//        FTPClient ftpClient = new FTPClient();
//        ftpClient.setConnectTimeout(6000);
//        ftpClient.connect("loaclhost", 21);
////        boolean isLogin = ftpClient.login("properties.getFtp().getUser()", "properties.getFtp().getPassword()");
////        if (!isLogin) {
////            ftpClient.disconnect();
////            throw new Exception("ftp login error");
////        }
//        return ftpClient;
//    }
//}
