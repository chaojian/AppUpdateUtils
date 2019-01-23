package com.freak.appupdateutils.appupdateutils;

/**
 * 检测更新弹窗接口
 *
 * @author freak
 * @date 2019/1/23
 */

public interface OnUpdateListener {
    /**
     * 取消更新
     */
    void onCancel();

    /**
     * 现在更新
     */
    void onSucceed();
}
