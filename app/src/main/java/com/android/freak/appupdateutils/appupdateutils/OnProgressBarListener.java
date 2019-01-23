package com.android.freak.appupdateutils.appupdateutils;

/**
 * @author freak
 * @date 2019/1/2
 */

public interface OnProgressBarListener {
    /**
     * 进度改变接口
     * @param current 当前进度
     * @param max 最大进度
     */
    void onProgressChange(int current, int max);

}
