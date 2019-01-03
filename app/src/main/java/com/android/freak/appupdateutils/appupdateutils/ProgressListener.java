package com.android.freak.appupdateutils.appupdateutils;

/**
 *
 * @author Administrator
 * @date 2019/1/2
 */

public interface ProgressListener {
    void onProgress(long bytesRead, long contentLength, boolean done);
}
