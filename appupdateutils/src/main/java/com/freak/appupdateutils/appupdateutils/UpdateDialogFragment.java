package com.freak.appupdateutils.appupdateutils;

import android.app.Dialog;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.freak.appupdateutils.R;


/**
 * apk更新窗口
 *
 * @author freak
 * @date 2019/1/2
 */
public class UpdateDialogFragment extends BaseDialogFragment implements OnProgressBarListener {
    private ApkInfoBean mApkInfoBean;

    private NumberProgressBar progressBar;
    private TextView btnUpdateLater;
    private TextView btnUpdateNow;
    private OnUpdateListener mOnUpdateListener;
    private TextView mTvUpdateTitle;
    private TextView mTvUpdateIntroduction;
    private TextView mTvVersionName;
    private TextView mTvApkSize;
    private TextView mTvApkUpdateDate;
    private TextView mTvApkIntroductionAdd;
    private TextView mTvApkIntroductionFix;
    private TextView mTvApkIntroductionCancel;

    public static UpdateDialogFragment newInstance() {
        return new UpdateDialogFragment();
    }

    @Override
    public void onProgressChange(int current, int max) {

    }

    @Override
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    protected void initData() {
        mApkInfoBean = AppUtils.getApkInfoBean();

        //标题
        mTvUpdateTitle.setText(TextUtils.isEmpty(mApkInfoBean.getAppName()) ? "发现新版本" : mApkInfoBean.getAppName());
        //简介
        if (TextUtils.isEmpty(mApkInfoBean.getVersionInfo())) {
            mTvUpdateIntroduction.setVisibility(View.GONE);
        } else {
            mTvUpdateIntroduction.setText(mApkInfoBean.getVersionInfo());
        }
        //apk版本
        if (TextUtils.isEmpty(mApkInfoBean.getVersionName())) {
            mTvVersionName.setVisibility(View.GONE);
        } else {
            mTvVersionName.setText(getResources().getString(R.string.app_version, mApkInfoBean.getVersionName()));
        }
        //apk大小
        if (mApkInfoBean.getApkSize() == null) {
            mTvApkSize.setVisibility(View.GONE);
        } else {
            Long size = mApkInfoBean.getApkSize() == null ? 0L : mApkInfoBean.getApkSize();
            String apkSize = Formatter.formatFileSize(getActivity(), size);
            mTvApkSize.setText(mApkInfoBean.getApkSize() == null ? "" : getResources().getString(R.string.app_size, apkSize));
        }
        //apk更新时间
        if (TextUtils.isEmpty(mApkInfoBean.getCreateDate())) {
            mTvApkUpdateDate.setVisibility(View.GONE);
        } else {
            mTvApkUpdateDate.setText(getResources().getString(R.string.app_update_time, mApkInfoBean.getCreateDate()));
        }
        //apk新增信息
        if (TextUtils.isEmpty(mApkInfoBean.getAddContent())) {
            mTvApkIntroductionAdd.setVisibility(View.GONE);
        } else {
            mTvApkIntroductionAdd.setText(getResources().getString(R.string.app_update_add_content, mApkInfoBean.getAddContent()));
        }
        //apk修复信息
        if (TextUtils.isEmpty(mApkInfoBean.getFixContent())) {
            mTvApkIntroductionFix.setVisibility(View.GONE);
        } else {
            mTvApkIntroductionFix.setText(getResources().getString(R.string.app_update_fix_content, mApkInfoBean.getFixContent()));
        }
        //apk取消信息
        if (TextUtils.isEmpty(mApkInfoBean.getCancelContent())) {
            mTvApkIntroductionCancel.setVisibility(View.GONE);
        } else {
            mTvApkIntroductionCancel.setText(getResources().getString(R.string.app_update_cancel_content, mApkInfoBean.getCancelContent()));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_app_update;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected void initView(Dialog view) {
        progressBar = (NumberProgressBar) view.findViewById(R.id.progress_bar);
        mTvUpdateTitle = (TextView) view.findViewById(R.id.tv_update_title);
        mTvUpdateIntroduction = (TextView) view.findViewById(R.id.tv_apk_introduction);
        mTvVersionName = (TextView) view.findViewById(R.id.tv_version_name);
        mTvApkSize = (TextView) view.findViewById(R.id.tv_apk_size);
        mTvApkUpdateDate = (TextView) view.findViewById(R.id.tv_apk_update_date);
        mTvApkIntroductionAdd = (TextView) view.findViewById(R.id.tv_apk_introduction_add);
        mTvApkIntroductionFix = (TextView) view.findViewById(R.id.tv_apk_introduction_fix);
        mTvApkIntroductionCancel = (TextView) view.findViewById(R.id.tv_apk_introduction_cancel);
        btnUpdateLater = view.findViewById(R.id.btn_update_later);
        btnUpdateNow = view.findViewById(R.id.btn_update_now);
        btnUpdateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onCancel();
                }
            }
        });
        btnUpdateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onSucceed();
                }
            }
        });
        progressBar.setOnProgressBarListener(this);
    }

    @Override
    public void setProgressBarVisibility(int b) {
        progressBar.setVisibility(b);
    }

    @Override
    public void disableClick(boolean b) {
        btnUpdateLater.setEnabled(b);
        btnUpdateNow.setEnabled(b);
    }

    @Override
    protected void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }

}

