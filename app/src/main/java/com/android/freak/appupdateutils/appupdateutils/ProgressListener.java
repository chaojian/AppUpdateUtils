package com.android.freak.appupdateutils.appupdateutils;

/**
 * @author Administrator
 * @date 2019/1/2
 */

public interface ProgressListener {
    /**
     * 下载进度监听
     * @param bytesRead
     * @param contentLength
     * @param done
     */
    void onProgress(long bytesRead, long contentLength, boolean done);
}
