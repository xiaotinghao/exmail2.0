package com.xs.common.utils.sftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.xs.common.utils.StreamUtils;
import com.xs.common.utils.StringUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import static com.xs.common.constants.SymbolConstants.SLASH;

/**
 * 文件传输工具类
 *
 * @author 18871430207@163.com
 */

public class SftpUtils {

    private static Logger logger = Logger.getLogger(SftpUtils.class);
    private static Session sshSession = null;
    private static Channel channel = null;
    private static ChannelSftp sftp = null;

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return sftp服务通道
     */
    public boolean getConnect(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            channel = sshSession.openChannel("sftp");
            channel.connect();
        } catch (Exception e) {
            logger.error("链接主机异常，主机：" + host + "用户：" + username + "密码：" + password);
            return false;
        }
        sftp = (ChannelSftp) channel;
        return true;
    }

    /**
     * 关闭连接
     */
    public void closeConnect() {
        sftp.quit();
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
        }
    }

    /**
     * 文件上传
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     */
    public boolean upload(String directory, String uploadFile) {
        // 目录不存在则生成目录
        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            if (!directoryFile.mkdirs()) {
                return false;
            }
        }
        FileInputStream fis = null;
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            fis = new FileInputStream(file);
            sftp.put(fis, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toRootPath();
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 文件上传
     *
     * @param directory 上传的目录
     * @param fileName  要上传的文件名
     * @param content   要上传的文件内容
     */
    public boolean uploadByContent(String directory, String fileName, String content) {
        // 目录不存在则生成目录
        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            if (!directoryFile.mkdirs()) {
                return false;
            }
        }
        try {
            sftp.cd(directory);
            sftp.put(new ByteArrayInputStream(content.getBytes("UTF-8")), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toRootPath();
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) {
        FileOutputStream fos = null;
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            fos = new FileOutputStream(file);
            sftp.get(downloadFile, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toRootPath();
            StreamUtils.close(fos);
        }
    }

    /**
     * 批量下载文件
     *
     * @param remotePath 远程下载目录(以路径符号结束)
     * @param localPath  本地保存目录(以路径符号结束)
     * @param fileFormat 下载文件格式(以特定字符开头,为空不做检验)
     * @param del        下载后是否删除sftp文件
     * @return 批量下载结果
     */
    public boolean batchDownLoad(String remotePath, String localPath, String fileFormat, boolean del) {
        FileOutputStream fos = null;
        try {
            Vector v = sftp.ls(remotePath);
            if (v != null && v.size() > 0) {
                Iterator it = v.iterator();
                for (; it.hasNext(); ) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        // 下载文件格式为空 或者 以特定字符开头
                        boolean flag = (!StringUtils.isEmpty(fileFormat) && filename.startsWith(fileFormat))
                                || StringUtils.isEmpty(fileFormat);
                        if (flag) {
                            sftp.cd(remotePath);
                            File file = new File(localPath + filename);
                            fos = new FileOutputStream(file);
                            sftp.get(remotePath + filename, fos);
                            fos.flush();
                            if (del) {
                                sftp.rm(remotePath + filename);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            toRootPath();
            StreamUtils.close(fos);
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            if (new File(deleteFile).exists()) {
                sftp.rm(deleteFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toRootPath();
        }
    }

    /**
     * 切换到sftp根目录
     */
    private void toRootPath() {
        try {
            sftp.cd(SLASH);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的所有文件名称
     *
     * @param directory 要列出的目录
     * @return 文件名称
     */
    public Vector<String> listFiles(String directory) {
        Vector<String> result = new Vector<>();
        Vector<?> fileList = null;
        try {
            fileList = sftp.ls(directory);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        if (fileList != null && fileList.size() > 0) {
            Iterator it = fileList.iterator();
            for (; it.hasNext(); ) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                String filename = entry.getFilename();
                SftpATTRS attrs = entry.getAttrs();
                if (!attrs.isDir()) {
                    result.add(filename);
                }
            }
        }
        return result;
    }

}