package com.android.freak.appupdateutils.appupdateutils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;


import com.android.freak.appupdateutils.R;

/**
 * @author freak
 * @date 2019/1/23
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected Activity mActivity;
    protected Context mContext;
    protected Dialog mView;

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 设置dialog style
     *
     * @return
     */
    protected abstract int getDialogStyle();

    /**
     * 初始化view
     *
     * @param view
     */
    protected abstract void initView(Dialog view);


    /**
     * 设置下载进度是否显示
     *
     * @param b
     */
    protected abstract void setProgressBarVisibility(int b);

    /**
     * 设置下载进度
     *
     * @param progress
     */
    protected abstract void setProgress(int progress);

    /**
     * 设置更新不能点击按钮
     *
     * @param b
     */
    protected abstract void disableClick(boolean b);

    /**
     * 设置按钮回调接口
     *
     * @param onUpdateListener
     */
    protected abstract void setOnUpdateListener(OnUpdateListener onUpdateListener);

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mView = new Dialog(mActivity, getDialogStyle() == 0 ? R.style.dialog : getDialogStyle());
        mView.setContentView(getLayoutId());
        mView.setCancelable(false);
        initView(mView);
        initData();
        return mView;
    }


}
