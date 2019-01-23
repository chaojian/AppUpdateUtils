package com.android.freak.appupdateutils.appupdateutils;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.freak.appupdateutils.R;
import com.android.freak.appupdateutils.app.Constants;


/**
 * app检测更新dialog
 *
 * @author freak
 * @date 2019/1/23
 */

public class AppUpDateDialogFragment extends BaseDialogFragment implements View.OnClickListener, OnProgressBarListener {
    private TextView mTextViewTitle;
    private TextView mTextViewUpdateContext;
    private TextView mTextViewCommit;
    private TextView mTextViewCancel;
    private NumberProgressBar mProgressBar;
    private ApkInfoBean mApkInfoBean;
    private OnUpdateListener mOnUpdateListener;


    @Override
    protected void initData() {
        mApkInfoBean = (ApkInfoBean) getArguments().getSerializable(Constants.APKINFO);
//        mTextViewUpdateContext.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : getResources().getString(R.string.app_update_update_content, mApkInfoBean.getAddContent()));
        mTextViewUpdateContext.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : "更新内容\n"+mApkInfoBean.getAddContent());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_app_update;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected void initView(Dialog view) {

        mTextViewTitle = view.findViewById(R.id.text_view_title);
        mTextViewUpdateContext = view.findViewById(R.id.text_view_update_context);
        mTextViewCommit = view.findViewById(R.id.text_view_commit);
        mTextViewCancel = view.findViewById(R.id.text_view_cancel);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mTextViewCancel.setOnClickListener(this);
        mTextViewCommit.setOnClickListener(this);
        mProgressBar.setOnProgressBarListener(this);
    }
    public static AppUpDateDialogFragment newInstance(ApkInfoBean apkInfoBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.APKINFO, apkInfoBean);
        AppUpDateDialogFragment fragment = new AppUpDateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_cancel:
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onCancel();
                }
                break;
            case R.id.text_view_commit:
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onSucceed();
                }
                break;
            default:
                break;
        }

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

    @Override
    public void onProgressChange(int current, int max) {

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
