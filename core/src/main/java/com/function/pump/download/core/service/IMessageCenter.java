package com.function.pump.download.core.service;

import android.content.Context;

import com.function.pump.download.core.DownloadDetailsInfo;
import com.function.pump.download.core.DownloadListener;

public interface IMessageCenter {
    void start(Context context);

    void register(DownloadListener downloadListener);

    void unRegister(String url);

    void unRegister(DownloadListener downloadListener);

    void notifyProgressChanged(DownloadDetailsInfo downloadInfo);

}
