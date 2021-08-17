package com.function.pump.download.core.service;

import com.function.pump.download.config.DownloadConfig;
import com.function.pump.download.core.DownloadInterceptor;
import com.function.pump.download.core.connection.DownloadConnection;

import java.util.List;

public interface IDownloadConfigService {
    void setConfig(DownloadConfig downloadConfig);

    int getMaxRunningTaskNumber();

    long getMinUsableSpace();

    List<DownloadInterceptor> getDownloadInterceptors();

    DownloadConnection.Factory getDownloadConnectionFactory();
}
