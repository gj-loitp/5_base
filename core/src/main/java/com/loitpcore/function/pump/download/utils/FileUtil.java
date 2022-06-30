package com.loitpcore.function.pump.download.utils;

import static com.loitpcore.function.pump.download.utils.Util.closeQuietly;

import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class FileUtil {
    private FileUtil() {
    }

    public static boolean deleteDir(File dirFile) {
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return deleteFile(dirFile);
        } else {
            File[] children = dirFile.listFiles();
            if (children != null) {
                for (File file : children) {
                    deleteDir(file);
                }
            }
        }
        return deleteFile(dirFile);
    }

    public static boolean renameTo(File source, File dest) {
        if (dest.exists()) {
            if (dest.delete()) {
                return source.renameTo(dest);
            }
            return false;
        }
        return source.renameTo(dest);
    }

    public static void copyFile(File sourceFile, File destFile) {
        if (destFile.getParentFile() != null && !destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        BufferedSource bufferedSource = null;
        BufferedSink bufferedSink = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(sourceFile));
            bufferedSink = Okio.buffer(Okio.sink(destFile));
            bufferedSink.writeAll(bufferedSource);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedSink);
            closeQuietly(bufferedSource);
        }

    }

    public static boolean createNewFile(File file) {
        File fileParent = file.getParentFile();
        if (fileParent == null || !fileParent.exists() && !fileParent.mkdirs()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean rename(String filePathName, String newPathName) {
        if (TextUtils.isEmpty(filePathName)) return false;
        if (TextUtils.isEmpty(newPathName)) return false;

        delete(newPathName);

        File file = new File(filePathName);
        File newFile = new File(newPathName);
        if (!file.exists()) {
            return false;
        }
        File parentFile = newFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file.renameTo(newFile);
    }

    public static boolean delete(String filePathName) {
        if (TextUtils.isEmpty(filePathName)) return false;
        File file = new File(filePathName);
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        if (file == null) return false;
        File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
        return file.renameTo(to) && to.delete();
    }

    public static boolean mergeFiles(File[] sources, File dest) {
        File[] sortedFiles = new File[sources.length];
        for (File partFile : sources) {
            String partFileName = partFile.getName();
            int idIndex = partFileName.lastIndexOf("-") + 1;
            int id = Integer.parseInt(partFileName.substring(idIndex));
            if (id < sortedFiles.length) {
                sortedFiles[id] = partFile;
            } else {
                return false;
            }
        }
        BufferedSink bufferedSink = null;
        BufferedSource bufferedSource = null;
        try {
            byte[] buffer = new byte[8092];
            int len;
            bufferedSink = Okio.buffer(Okio.appendingSink(sortedFiles[0]));
            for (int i = 1; i < sortedFiles.length; i++) {
                File file = sortedFiles[i];
                bufferedSource = Okio.buffer(Okio.source(file));
                while ((len = bufferedSource.read(buffer)) != -1) {
                    bufferedSink.write(buffer, 0, len);
                }
                closeQuietly(bufferedSource);
            }
            bufferedSink.flush();
            renameTo(sortedFiles[0], dest);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedSink);
            closeQuietly(bufferedSource);
        }
        return false;
    }
}
