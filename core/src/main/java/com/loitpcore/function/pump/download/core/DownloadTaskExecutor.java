package com.loitpcore.function.pump.download.core;

import com.loitpcore.function.pump.download.core.task.DownloadTask;

public interface DownloadTaskExecutor {
    /**
     * Do some initialization and it will only be called once.
     */
    void init();

    /**
     * Executes the given task sometime in the future.
     *
     * @param downloadTask Download task
     */
    void execute(DownloadTask downloadTask);

    /**
     * Return the maximum number of download to execute concurrently.
     *
     * @return The maximum number of download to execute concurrently
     */
    int getMaxDownloadNumber();

    /**
     * Return the name of this executor,use for logging.
     *
     * @return The name of this executor,use for logging.
     */
    String getName();

    String getTag();

    /**
     * Terminal this Executor.
     */
    void shutdown();
}
