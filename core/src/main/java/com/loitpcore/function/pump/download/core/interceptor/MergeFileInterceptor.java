package com.loitpcore.function.pump.download.core.interceptor;

import static com.loitpcore.function.pump.download.utils.Util.DOWNLOAD_PART;

import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;

import com.loitpcore.function.pump.download.ErrorCode;
import com.loitpcore.function.pump.download.PumpFactory;
import com.loitpcore.function.pump.download.core.DownloadDetailsInfo;
import com.loitpcore.function.pump.download.core.DownloadInfo;
import com.loitpcore.function.pump.download.core.DownloadInterceptor;
import com.loitpcore.function.pump.download.core.DownloadRequest;
import com.loitpcore.function.pump.download.core.PumpFile;
import com.loitpcore.function.pump.download.core.service.IDownloadManager;
import com.loitpcore.function.pump.download.core.task.DownloadTask;
import com.loitpcore.function.pump.download.utils.FileUtil;
import com.loitpcore.function.pump.download.utils.LogUtil;
import com.loitpcore.function.pump.download.utils.Util;

import java.io.File;
import java.io.FilenameFilter;

public class MergeFileInterceptor implements DownloadInterceptor {
    private DownloadDetailsInfo downloadInfo;

    @Override
    public DownloadInfo intercept(DownloadChain chain) {
        DownloadRequest downloadRequest = chain.request();
        downloadInfo = downloadRequest.getDownloadInfo();
        DownloadTask downloadTask = downloadInfo.getDownloadTask();
        Object lock = downloadTask.getLock();
        if (lock == null) {
            return downloadInfo.snapshot();
        }
        synchronized (lock) {
            long contentLength = downloadInfo.getContentLength();
            long completedSize = downloadInfo.getCompletedSize();
            File tempDir = downloadInfo.getTempDir();
            File[] downloadPartFiles = tempDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(DOWNLOAD_PART);
                }
            });
            if (contentLength > 0 && completedSize == contentLength && downloadPartFiles != null
                    && downloadPartFiles.length == downloadInfo.getThreadNum()) {
                PumpFile file = downloadInfo.getDownloadFile();
                if (checkIsSpaceInsufficient(contentLength)) {
                    downloadInfo.setErrorCode(ErrorCode.ERROR_MERGE_FILE_FAILED);
                    return downloadInfo.snapshot();
                }
                long startTime = System.currentTimeMillis();
                boolean mergeSuccess;
                if ((file.getSchemaUri() == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)) {
                    if (downloadPartFiles.length == 1) {
                        mergeSuccess = FileUtil.renameTo(downloadPartFiles[0], file.getFile());
                    } else {
                        mergeSuccess = FileUtil.mergeFiles(downloadPartFiles, file.getFile());
                    }
                } else {
                    mergeSuccess = file.mergeFiles(downloadPartFiles);
                }

                if (mergeSuccess) {
                    downloadInfo.deleteTempDir();
                    LogUtil.d("Merge " + downloadInfo.getName() + " spend=" +
                            (System.currentTimeMillis() - startTime) + "; file.length=" + file.length());
                    checkDownloadResult(contentLength, completedSize);
                } else {
                    downloadInfo.setErrorCode(ErrorCode.ERROR_MERGE_FILE_FAILED);
                }
            }
        }
        return downloadInfo.snapshot();
    }

    private boolean checkIsSpaceInsufficient(long contentLength) {
        Context context = PumpFactory.getService(IDownloadManager.class).getContext();
        PumpFile downloadFile = downloadInfo.getDownloadFile();
        long downloadDirUsableSpace = Util.getUsableSpace(downloadFile.getFile());
        if (downloadDirUsableSpace < contentLength) {
            String downloadFileAvailableSize = Formatter.formatFileSize(context, downloadDirUsableSpace);
            LogUtil.e("Merge file failed! Download directory is" + downloadFile.getFile() + " and usable space is " +
                    downloadFileAvailableSize + ";but download file's contentLength is " + contentLength);
            return true;
        }
        return false;
    }

    private void checkDownloadResult(long contentLength, long completedSize) {
        long downloadFileLength = downloadInfo.getDownloadFile().length();
        if (downloadInfo.getStatus() != DownloadInfo.Status.FAILED &&
                downloadFileLength > 0 && downloadFileLength == contentLength
                && downloadFileLength == completedSize) {
            downloadInfo.setFinished(1);
            downloadInfo.setStatus(DownloadInfo.Status.FINISHED);
            downloadInfo.setCompletedSize(completedSize);
        } else {
            downloadInfo.setFinished(0);
            downloadInfo.setErrorCode(ErrorCode.ERROR_DOWNLOAD_FAILED);
        }
    }

}
