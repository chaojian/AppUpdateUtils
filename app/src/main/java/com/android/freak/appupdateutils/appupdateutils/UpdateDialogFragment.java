package com.android.freak.appupdateutils.appupdateutils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.freak.appupdateutils.R;
import com.android.freak.appupdateutils.app.Constants;


/**
 * apk更新窗口
 *
 * @author Administrator
 * @date 2019/1/2
 */
public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener, OnProgressBarListener {

    private Dialog mDetailDialog;

    private ApkInfoBean mApkInfoBean;

    private NumberProgressBar progressBar;
    private Button btnUpdateLater;
    private Button btnUpdateNow;

    public static UpdateDialogFragment newInstance(ApkInfoBean apkInfoBean) {

        Bundle args = new Bundle();
        args.putSerializable(Constants.APKINFO, apkInfoBean);
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDetailDialog = new Dialog(getActivity(), R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_app_update);
        mDetailDialog.setCancelable(false);

        mApkInfoBean = (ApkInfoBean) getArguments().getSerializable(Constants.APKINFO);

        initView();
        return mDetailDialog;
    }

    private void initView() {


        progressBar = (NumberProgressBar) mDetailDialog.findViewById(R.id.progress_bar);

        progressBar.setOnProgressBarListener(this);
        progressBar.setProgressTextColor(R.color.colorAccent);
        //标题
        TextView tvUpdateTitle = (TextView) mDetailDialog.findViewById(R.id.tv_update_title);
        tvUpdateTitle.setText(getResources().getString(R.string.app_update_title, mApkInfoBean.getVersionName()));

        //简介
        TextView tvUpdateIntroduction = (TextView) mDetailDialog.findViewById(R.id.tv_apk_introduction);
        tvUpdateIntroduction.setText(mApkInfoBean.getVersionInfo());

        //apk版本
        TextView tvVersionName = (TextView) mDetailDialog.findViewById(R.id.tv_version_name);
        tvVersionName.setText(getResources().getString(R.string.app_version, mApkInfoBean.getVersionName()));

        //apk大小
        TextView tvApkSize = (TextView) mDetailDialog.findViewById(R.id.tv_apk_size);

        Long size = mApkInfoBean.getApkSize() == null ? 0L : mApkInfoBean.getApkSize();

        String apkSize = Formatter.formatFileSize(getActivity(), size);
        tvApkSize.setText(getResources().getString(R.string.app_size, apkSize));

        //apk更新时间
        TextView tvApkUpdateDate = (TextView) mDetailDialog.findViewById(R.id.tv_apk_update_date);
        tvApkUpdateDate.setText(getResources().getString(R.string.app_update_time, mApkInfoBean.getCreateDate()));

        //apk新增信息
        TextView tvApkIntroductionAdd = (TextView) mDetailDialog.findViewById(R.id.tv_apk_introduction_add);
        tvApkIntroductionAdd.setText(TextUtils.isEmpty(mApkInfoBean.getAddContent()) ? "" : getResources().getString(R.string.app_update_add_content, mApkInfoBean.getAddContent()));

        //apk修复信息
        TextView tvApkIntroductionFix = (TextView) mDetailDialog.findViewById(R.id.tv_apk_introduction_fix);
        tvApkIntroductionFix.setText(TextUtils.isEmpty(mApkInfoBean.getFixContent()) ? "" : getResources().getString(R.string.app_update_fix_content, mApkInfoBean.getFixContent()));

        //apk取消信息
        TextView tvApkIntroductionCancel = (TextView) mDetailDialog.findViewById(R.id.tv_apk_introduction_cancel);
        tvApkIntroductionCancel.setText(TextUtils.isEmpty(mApkInfoBean.getCancelContent()) ? "" : getResources().getString(R.string.app_update_cancel_content, mApkInfoBean.getCancelContent()));

        btnUpdateLater = (Button) mDetailDialog.findViewById(R.id.btn_update_later);

        btnUpdateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsListener != null) {
                    onTipsListener.onCancel();
                }
            }
        });

        btnUpdateNow = (Button) mDetailDialog.findViewById(R.id.btn_update_now);

        btnUpdateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsListener != null) {
                    onTipsListener.onSucceed();
                }
            }
        });
    }


    private OnTipsListener onTipsListener;

    public void setOnConfirmListener(OnTipsListener onTipsListener) {
        this.onTipsListener = onTipsListener;
    }


    @Override
    public void onProgressChange(int current, int max) {

    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    public void setProgressBarVisibility(int b) {
        progressBar.setVisibility(b);
    }

    public void disableClick(boolean b) {
        btnUpdateLater.setEnabled(b);
        btnUpdateNow.setEnabled(b);
    }

    public interface OnTipsListener {
        /**
         * 取消更新
         */
        void onCancel();

        /**
         * 现在更新
         */
        void onSucceed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:

                break;
        }
    }

    public NumberProgressBar getProgressBar() {
        return progressBar;
    }

}

