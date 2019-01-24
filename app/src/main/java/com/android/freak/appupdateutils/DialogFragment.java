package com.android.freak.appupdateutils;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freak.appupdateutils.appupdateutils.ApkInfoBean;
import com.freak.appupdateutils.appupdateutils.AppUtils;
import com.freak.appupdateutils.appupdateutils.BaseDialogFragment;
import com.freak.appupdateutils.appupdateutils.OnUpdateListener;

/**
 * Created by Administrator on 2019/1/24.
 */

public class DialogFragment extends BaseDialogFragment   {
    private TextView mTextViewTitle;
    private TextView mTextViewUpdateContext;
    private TextView mTextViewCommit;
    private TextView mTextViewCancel;
    private ProgressBar mProgressBar;
    private ApkInfoBean mApkInfoBean;
    private OnUpdateListener mOnUpdateListener;


    @Override
    protected void initData() {
        mApkInfoBean = AppUtils.getApkInfoBean();
        mTextViewUpdateContext.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : "更新内容\n" + mApkInfoBean.getAddContent());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_update;
    }

    @Override
    protected int getDialogStyle() {
        return com.freak.appupdateutils.R.style.dialog;
    }

    @Override
    protected void initView(Dialog view) {

        mTextViewTitle = view.findViewById(com.freak.appupdateutils.R.id.text_view_title);
        mTextViewUpdateContext = view.findViewById(com.freak.appupdateutils.R.id.text_view_update_context);
        mTextViewCommit = view.findViewById(com.freak.appupdateutils.R.id.text_view_commit);
        mTextViewCancel = view.findViewById(com.freak.appupdateutils.R.id.text_view_cancel);
        mProgressBar = view.findViewById(com.freak.appupdateutils.R.id.progress_bar);
        mTextViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onCancel();
                }
            }
        });
        mTextViewCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onSucceed();
                }
            }
        });

    }

    public static DialogFragment newInstance(ApkInfoBean apkInfoBean) {
        Bundle args = new Bundle();
        args.putSerializable(AppUtils.APK_INFO, apkInfoBean);
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void disableClick(boolean b) {
        mTextViewCancel.setEnabled(b);
        mTextViewCommit.setEnabled(b);
    }

    @Override
    protected void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }


    /**
     * 设置下载进度
     *
     * @param progress
     */
    @Override
    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    /**
     * 设置下载进度是否显示
     *
     * @param b
     */
    @Override
    public void setProgressBarVisibility(int b) {
        mProgressBar.setVisibility(b);
    }

}
