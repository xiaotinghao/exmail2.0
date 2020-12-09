package com.xs.common.utils;

import java.io.*;
import java.util.Date;
import java.util.List;

import static com.xs.common.constants.CodeConstants.UTF8;
import static com.xs.common.constants.SymbolConstants.*;
import static com.xs.common.utils.DateUtil.DATE_SDF;

/**
 * 文件工具类
 *
 * @author xiaotinghao
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 默认备份目录
     */
    private static final String BACKUPS = "backups/";

    /**
     * 获取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    @SuppressWarnings("unused")
    public static String getFileContent(String filePath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        StringBuilder fileContent = new StringBuilder();

        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis, UTF8);
            fileContent.append(inputStream2String(isr));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContent.toString();
    }

    /**
     * 文件备份
     *
     * @param originFilePath 原始文件路径
     */
    @SuppressWarnings("unused")
    public static boolean backup(String originFilePath) {
        String backupPath = originFilePath.substring(0, originFilePath.lastIndexOf(SLASH));
        return backup(originFilePath, backupPath);
    }

    /**
     * 文件备份
     *
     * @param originFilePath 原始文件路径
     * @param backupPath     文件备份路径
     */
    @SuppressWarnings("unused")
    public static boolean backup(String originFilePath, String backupPath) {
        if (StringUtils.isEmpty(backupPath)) {
            backupPath = originFilePath.substring(0, originFilePath.lastIndexOf(SLASH) + 1);
            backupPath += BACKUPS;
        } else {
            backupPath += SLASH;
        }
        File originFile = new File(originFilePath);
        String nowDateStr = DATE_SDF.format(new Date());
        String backupFileName = nowDateStr + HYPHEN + System.currentTimeMillis()
                + HYPHEN + originFile.getName();
        // 备份目录
        File backupDir = new File(backupPath);
        // 备份文件
        File backupFile = new File(backupPath + backupFileName);
        try {
            // 目录不存在则生成目录
            if (!backupDir.exists()) {
                if (!backupDir.mkdirs()) {
                    return false;
                }
            }
            // 文件不存在生成文件
            if (!backupFile.exists()) {
                if (!backupFile.createNewFile()) {
                    return false;
                }
            }
            copyFile(new File(originFilePath), backupFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * 从输入流中获取文件内容
     *
     * @param isr 输入流
     * @return 文件内容
     */
    private static String inputStream2String(InputStreamReader isr) {
        BufferedReader in = new BufferedReader(isr);
        StringBuilder result = new StringBuilder();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 读取目录下的所有文件（包含子目录）
     *
     * @param dir       目录
     * @param fileNames 保存文件名的集合
     */
    public static void findFileList(File dir, List<String> fileNames) {
        // 判断是否存在目录（递归调用结束条件）
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 读取目录下的所有目录文件信息
        String[] files = dir.list();
        if (files != null) {
            // 循环，添加文件名或回调自身
            for (String item : files) {
                File file = new File(dir, item);
                if (file.isFile()) {
                    // 如果文件，添加文件全路径名
                    fileNames.add(dir + BACKSLASH + file.getName());
                } else {
                    // 如果是目录，回调自身继续查询
                    findFileList(file, fileNames);
                }
            }
        }
    }

    /**
     * 读取目录下的所有文件（包含子目录）
     *
     * @param filePath 文件路径
     */
    public static String getDir(String filePath) {
        File file = new File(filePath);
        // 判断是否存在
        if (!file.exists() || file.isDirectory()) {
            return "";
        }
        String path = file.getPath();
        String name = file.getName();
        return path.replace(name, "");
    }

    /**
     * 向文件中追加文本
     *
     * @param fileName 文件路径（包含名称）
     * @param text     文本内容
     */
    public static void appendText(String fileName, String text) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, true);
            fw.write(text);
            fw.write("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}