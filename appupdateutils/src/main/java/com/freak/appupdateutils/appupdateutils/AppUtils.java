package com.freak.appupdateutils.appupdateutils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.freak.appupdateutils.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * app更新工具类
 *
 * @author freak
 * @date 2019/1/2
 */

public class AppUtils {
    private static final String TAG = "AppUtils";
    /**
     * 保存文件路径
     */
    private String savePath;
    /**
     * 8.0以下notify builder
     */
    private NotificationCompat.Builder builder;
    /**
     * 8.0以上notify builder
     */
    private Notification.Builder mBuilder;
    private NotificationManager notificationManager;
    /**
     * 下载进度
     */
    private int preProgress;
    private AppCompatActivity mActivity;
    private Notification notification = null;
    private static final int PUSH_NOTIFICATION_ID = 100;
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
    public static final String UPDATE_DIALOG_SIMPLENESS = "UPDATE_DIALOG_SIMPLENESS";
    public static final String UPDATE_DIALOG_PARTICULAR = "UPDATE_DIALOG_PARTICULAR";
    public static final String APK_INFO = "apk_info";

    /**
     * 自定义更新弹窗
     */
    private BaseDialogFragment updateDialogFragment;
    /**
     * 弹窗样式
     */
    private String dialogStyle;
    /**
     * app名字
     */
    private String appName;
    /**
     * 版本号
     */
    private Integer versionCode;

    /**
     * 版本名
     */
    private String versionName;

    /**
     * 是否强制更新
     */
    private Boolean isForce;

    /**
     * 版本信息
     */
    private String versionInfo;

    /**
     * apk路径
     */
    private String apkURL;

    /**
     * apk大小
     */
    private Long apkSize;

    /**
     * 新增
     */
    private String addContent;

    /**
     * 修复
     */
    private String fixContent;

    /**
     * 取消
     */
    private String cancelContent;

    /**
     * 更新时间
     */
    private String createDate;

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 下载域名
     */
    private String baseUrl;

    public String fileProvider;

    private String notificationTitle;

    private static ApkInfoBean mApkInfoBean;

    public static ApkInfoBean getApkInfoBean() {
        return mApkInfoBean;
    }

    public void setApkInfoBean(ApkInfoBean apkInfoBean) {
        mApkInfoBean = apkInfoBean;
    }

    /**
     * 初始化
     *
     * @param activity     上下文
     * @param baseUrl      服务器域名
     * @param fileProvider 6.0以上手机文件下载路径配置
     */
    public AppUtils(@Nullable AppCompatActivity activity, @Nullable String baseUrl, @Nullable String fileProvider) {
        mActivity = activity;
        this.baseUrl = baseUrl;
        this.fileProvider = fileProvider;
    }


    public String getNotificationTitle() {
        return notificationTitle;
    }

    /**
     * 设置Notification标题
     *
     * @return
     */
    public AppUtils setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
        return this;
    }


    /**
     * 设置app名字
     *
     * @param appName
     * @return
     */
    public AppUtils setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    /**
     * 设置版本号
     *
     * @param versionCode 版本号
     */
    public AppUtils setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    /**
     * 设置版本名
     *
     * @param versionName
     */
    public AppUtils setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    /**
     * 设置是否强制更新
     *
     * @param force
     */
    public AppUtils setForce(Boolean force) {
        isForce = force;
        return this;
    }

    /**
     * 设置版本信息
     *
     * @param versionInfo 版本信息
     */
    public AppUtils setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
        return this;
    }

    /**
     * 设置apk下载地址
     *
     * @param apkURL
     */
    public AppUtils setApkURL(@Nullable String apkURL) {
        this.apkURL = apkURL;
        return this;
    }

    /**
     * 设置apk下载大小
     *
     * @param apkSize apk内存大小
     */
    public AppUtils setApkSize(Long apkSize) {
        this.apkSize = apkSize;
        return this;
    }

    /**
     * 设置新增内容
     *
     * @param addContent
     */
    public AppUtils setAddContent(String addContent) {
        this.addContent = addContent;
        return this;
    }

    /**
     * 设置修复内容
     *
     * @param fixContent
     */
    public AppUtils setFixContent(String fixContent) {
        this.fixContent = fixContent;
        return this;
    }

    /**
     * 设置取消内容
     *
     * @param cancelContent
     */
    public AppUtils setCancelContent(String cancelContent) {
        this.cancelContent = cancelContent;
        return this;
    }

    /**
     * 设置更新时间
     *
     * @param createDate
     */
    public AppUtils setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    /**
     * 设置apk文件名
     *
     * @param fileName
     */
    public AppUtils setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置自定dialog
     *
     * @return
     */
    public BaseDialogFragment getUpdateDialogFragment() {
        return updateDialogFragment;
    }

    public AppUtils setUpdateDialogFragment(BaseDialogFragment updateDialogFragment) {
        this.updateDialogFragment = updateDialogFragment;
        return this;
    }

    public String getDialogStyle() {
        return dialogStyle;
    }

    /**
     * 选择默认的两种弹窗样式中的一种
     *
     * @param dialogStyle
     * @return
     */
    public AppUtils setDialogStyle(String dialogStyle) {
        this.dialogStyle = dialogStyle;
        return this;
    }

    public void build() {
        if (versionCode > getVersionCode(mActivity)) {
            ApkInfoBean apkInfoBean = new ApkInfoBean();
            if (TextUtils.isEmpty(apkURL)) {
                Toast.makeText(mActivity, "更新文件地址不能为空，请检查", Toast.LENGTH_LONG).show();
                return;
            }
            apkInfoBean.setVersionCode(versionCode);
            apkInfoBean.setVersionName(versionName);
            apkInfoBean.setForce(isForce);
            apkInfoBean.setVersionInfo(versionInfo);
            apkInfoBean.setApkURL(apkURL);
            apkInfoBean.setApkSize(apkSize);
            apkInfoBean.setAddContent(addContent);
            apkInfoBean.setFixContent(fixContent);
            apkInfoBean.setCancelContent(cancelContent);
            apkInfoBean.setCreateDate(createDate);
            apkInfoBean.setFileName(fileName);
            apkInfoBean.setAppName(appName);
            setApkInfoBean(apkInfoBean);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (TextUtils.isEmpty(fileProvider)) {
                    Log.e(TAG, "6.0版本以上手机，请配置fileProvider");
                } else {
                    showUpdateDialog(getUpdateDialogFragment(), apkInfoBean, getDialogStyle());
                }
            } else {
                showUpdateDialog(getUpdateDialogFragment(), apkInfoBean, getDialogStyle());
            }
        } else {
            Log.d(TAG, "当前是最新版本");
        }
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 显示update dialog
     *
     * @param apkInfoBean 更新信息
     */
    public void showUpdateDialog(BaseDialogFragment baseDialogFragment, final ApkInfoBean apkInfoBean, String dialogStyle) {
        if (baseDialogFragment == null) {
            if (TextUtils.isEmpty(dialogStyle)) {
                updateDialogFragment = AppUpDateDialogFragment.newInstance();
            } else {
                switch (dialogStyle) {
                    case UPDATE_DIALOG_SIMPLENESS:
                        updateDialogFragment = AppUpDateDialogFragment.newInstance();
                        break;
                    case UPDATE_DIALOG_PARTICULAR:
                        updateDialogFragment = UpdateDialogFragment.newInstance();
                        break;
                    default:
                        break;
                }
            }
        } else {
            updateDialogFragment = baseDialogFragment;
        }
        updateDialogFragment.setOnUpdateListener(new OnUpdateListener() {
            @Override
            public void onCancel() {
                //是否开启强制更新，已开启的话不更新则强制退出软件
                if (apkInfoBean.getForce() == null ? false : apkInfoBean.getForce()) {
                    //退出
                    updateDialogFragment.dismiss();

                    Process.killProcess(Process.myPid());
                } else {
                    updateDialogFragment.dismiss();
                }
            }

            @Override
            public void onSucceed() {
                //开始下载
                startDownloadApk(baseUrl, apkInfoBean.getApkURL(), TextUtils.isEmpty(apkInfoBean.getFileName()) ? "appUpdate.apk" : apkInfoBean.getFileName());
                updateDialogFragment.setProgressBarVisibility(View.VISIBLE);
                //初始化通知栏
                initNotification();
                updateDialogFragment.disableClick(false);
            }
        });

        //处理 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (updateDialogFragment != null && updateDialogFragment.getDialog() != null && updateDialogFragment.getDialog().isShowing()) {
            updateDialogFragment.dismissAllowingStateLoss();
        }

        if (!updateDialogFragment.isAdded()) {
            FragmentManager supportFragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.add(updateDialogFragment, UpdateDialogFragment.class.getSimpleName());
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 下载Apk文件
     *
     * @param apkURL 下载地址
     * @param name   apk名字
     */
    public void startDownloadApk(String baseUrl, String apkURL, String name) {

        ApiServer apiService = DownloadHelper.getInstance().createApiService(ApiServer.class, baseUrl, new DownloadProgressListener());

        savePath = mActivity.getExternalFilesDir(null) + File.separator + name;
        apiService.downloadApk(apkURL).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(final ResponseBody responseBody) {
                //保存文件
                Observable.just(responseBody).map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        return writeFileToSDCard(responseBody, savePath);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    AppUtils.installApk(mActivity, savePath, fileProvider);
                                } else {
                                    Log.e(TAG, "apk保存失败");
                                    cancelNotification();
                                }
                                updateDialogFragment.dismiss();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (throwable != null && !TextUtils.isEmpty(throwable.getLocalizedMessage())) {
                                    throwable.printStackTrace();
                                    Log.d(TAG, "apk保存过程出错" + throwable.getLocalizedMessage());
                                    updateDialogFragment.dismiss();
                                    cancelNotification();
                                }
                            }
                        });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable != null && !TextUtils.isEmpty(throwable.getLocalizedMessage())) {
                    throwable.printStackTrace();
                    Log.d(TAG, "下载出错" + throwable.getLocalizedMessage());
                    updateDialogFragment.dismiss();
                    cancelNotification();
                }
            }
        });
    }

    /**
     * 下载进度监听
     */
    private class DownloadProgressListener implements ProgressListener {

        @Override
        public void onProgress(final long bytesRead, final long contentLength, final boolean done) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bytesRead != 0) {
                        int progress = (int) ((bytesRead * 100) / contentLength);
                        updateDialogFragment.setProgress(progress);
                        updateNotification(progress);
                    }

                    if (done) {
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        File file = new File(savePath);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            /**
                             * authority参数 如果直接设置为applicationId.fileProvider,
                             *  例如我的applicationId是com.android.freak.appupdatautils，则就是直接设置authority参数为com.android.freak.appupdatautils.fileProvider
                             *  则会报java空指针异常：java.lang.NullPointException
                             *  以为是要的配置文件的包不一致导致，所以改为以下方式
                             *  BuildConfig.APPLICATION_ID + ".fileProvider"
                             *  注意：BuildConfig的导包是这个项目的，不要导包错误
                             */
                            if (TextUtils.isEmpty(fileProvider)) {
                                Log.e(TAG, "文件保存失败，在Build.VERSION.SDK_INT》=24时请配置fileProvider");
                                return;
                            }
                            Uri apkUri = FileProvider.getUriForFile(mActivity, fileProvider, file);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            it.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        } else {
                            it.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity.getBaseContext(), 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
                        /**
                         * 在8.0版本手机中，notify的是不显示的 要通过以下步骤创建（注意：通知栏的权限要设置允许显示才能显示）
                         * 1、定义通知id、通知渠道id、通知渠道名
                         * 2、创建通知渠道
                         * 3、创建通知并显示
                         */
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notification = mBuilder.
                                    setContentTitle("下载完成")
                                    .setContentText("点击安装")
                                    .setContentIntent(pendingIntent)
                                    .build();
                        } else {
                            builder.setContentTitle("下载完成").setContentText("点击安装").setContentIntent(pendingIntent);
                            notification = builder.build();
                        }
                        notificationManager.notify(PUSH_NOTIFICATION_ID, notification);
                    }
                }
            });

        }
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        notificationManager = (NotificationManager) mActivity.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            mBuilder = new Notification.Builder(mActivity, PUSH_CHANNEL_ID);
            notification = mBuilder
                    .setContentTitle(TextUtils.isEmpty(getNotificationTitle()) ? "软件更新" : getNotificationTitle())
                    .setContentText("0%")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        } else {
            builder = new NotificationCompat.Builder(mActivity, PUSH_CHANNEL_ID)
                    .setContentTitle(TextUtils.isEmpty(getNotificationTitle()) ? "软件更新" : getNotificationTitle())
                    .setContentText("0%")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true);
            notification = builder.build();
        }
        notificationManager.notify(PUSH_NOTIFICATION_ID, notification);

    }

    /**
     * 更新下载进度通知
     */

    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification = mBuilder
                        .setContentTitle(TextUtils.isEmpty(getNotificationTitle()) ? "软件更新" : getNotificationTitle())
                        .setContentText(progress + "%")
                        .setProgress(100, (int) progress, false)
                        .build();
            } else {
                builder.setContentTitle(TextUtils.isEmpty(getNotificationTitle()) ? "软件更新" : getNotificationTitle());
                builder.setContentText(progress + "%");
                builder.setProgress(100, (int) progress, false);
                notification = builder.build();
            }
            notificationManager.notify(PUSH_NOTIFICATION_ID, notification);
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(PUSH_NOTIFICATION_ID);
    }

    /**
     * 安装
     *
     * @param context
     * @param filePath
     * @return
     */
    public static boolean installApk(Context context, String filePath, String fileProvider) {
        setPermission(filePath);
        Log.e("INSTALL", "downloadDir:" + filePath);
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            Log.e("INSTALL", "文件不存在");
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);

        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (TextUtils.isEmpty(fileProvider)) {
                Log.e(TAG, "安装失败，在Build.VERSION.SDK_INT》=24时请配置fileProvider");
                return false;
            }
            Uri apkUri = FileProvider.getUriForFile(context, fileProvider, file);

            Log.e("install", "apkUri7.0:" + apkUri.toString());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");

        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Log.e("install", "apkUri:" + Uri.fromFile(file).toString());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        context.startActivity(intent);
        return true;
    }

    /**
     * 设置权限
     *
     * @param filePath
     */
    public static void setPermission(String filePath) {

        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存文件到本地
     *
     * @param body
     * @return 保存结果
     */
    private boolean writeFileToSDCard(ResponseBody body, String path) {

        try {
            File file = new File(path);

            if (file.exists()) {
                if (file.delete()) {
                    Log.d(TAG, "文件存在:删除成功");
                } else {
                    Log.d(TAG, "文件存在:删除失败");
                }
            }
            if (file.createNewFile()) {
                Log.d(TAG, "新文件创建成功");
            } else {
                Log.d(TAG, "新文件创建失败");
            }
            Log.d(TAG, "path:" + path);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
