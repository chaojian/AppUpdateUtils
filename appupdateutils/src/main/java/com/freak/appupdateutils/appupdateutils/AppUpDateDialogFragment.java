package com.freak.appupdateutils.appupdateutils;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.freak.appupdateutils.R;


/**
 * app检测更新dialog
 *
 * @author freak
 * @date 2019/1/23
 */

public class AppUpDateDialogFragment extends BaseDialogFragment implements OnProgressBarListener {
    private TextView mTextViewTitle;
    private TextView mTextViewUpdateContext;
    private TextView mTextViewCommit;
    private TextView mTextViewCancel;
    private NumberProgressBar mProgressBar;
    private ApkInfoBean mApkInfoBean;
    private OnUpdateListener mOnUpdateListener;


    @Override
    protected void initData() {
        mApkInfoBean = (ApkInfoBean) getArguments().getSerializable(AppUtils.APK_INFO);
//        mTextViewUpdateContext.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : getResources().getString(R.string.app_update_update_content, mApkInfoBean.getAddContent()));
        mTextViewUpdateContext.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : "更新内容\n" + mApkInfoBean.getAddContent());
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
        mProgressBar.setOnProgressBarListener(this);
    }

    public static AppUpDateDialogFragment newInstance(ApkInfoBean apkInfoBean) {
        Bundle args = new Bundle();
        args.putSerializable(AppUtils.APK_INFO, apkInfoBean);
        AppUpDateDialogFragment fragment = new AppUpDateDialogFragment();
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
