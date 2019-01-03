package com.android.freak.appupdateutils.appupdateutils;

import android.app.Activity;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.freak.appupdateutils.BuildConfig;
import com.android.freak.appupdateutils.R;
import com.android.freak.appupdateutils.app.ApiServer;

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
 * @author Administrator
 * @date 2019/1/2
 */

public class AppUtils {
    private final String TAG = "AppUtils";
    private String savePath;
    private NotificationCompat.Builder builder;
    private Notification.Builder mBuilder;
    private NotificationManager notificationManager;
    private int notifyId = 100;
    private int preProgress;
    private boolean downloading = false;
    private Activity mActivity;
    private UpdateDialogFragment updateDialogFragment;
    Notification notification = null;
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";


    public AppUtils(Activity activity) {
        mActivity = activity;
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
    public void showUpdateDialog(final ApkInfoBean apkInfoBean) {

        if (updateDialogFragment == null) {
            updateDialogFragment = UpdateDialogFragment.newInstance(apkInfoBean);
        }


        updateDialogFragment.setOnConfirmListener(new UpdateDialogFragment.OnTipsListener() {
            @Override
            public void onCancel() {
                if (apkInfoBean.getForce()) {
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
                startDownloadApk(apkInfoBean.getApkURL(), TextUtils.isEmpty(apkInfoBean.getFileName()) ? "jiuchasheng.apk" : apkInfoBean.getFileName());
                updateDialogFragment.setProgressBarVisibility(View.VISIBLE);
                downloading = true;
                initNotification();

                updateDialogFragment.disableClick(false);

            }
        });

        //处理 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (updateDialogFragment != null && updateDialogFragment.getDialog() != null && updateDialogFragment.getDialog().isShowing()) {
            updateDialogFragment.dismissAllowingStateLoss();
        }

        if (!updateDialogFragment.isAdded()) {
            android.app.FragmentManager fm = mActivity.getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            ft.add(updateDialogFragment, UpdateDialogFragment.class.getSimpleName());
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 下载Apk文件
     * @param apkURL 下载地址
     * @param name apk名字
     */
    public void startDownloadApk(String apkURL, String name) {

        ApiServer apiService = DownloadHelper.getInstance().createApiService(ApiServer.class, new DownloadProgressListener());

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
                                    AppUtils.installApk(mActivity, savePath);
                                } else {
                                    Log.e(TAG, "apk保存失败");
                                    cancelNotification();
                                }
                                downloading = false;
                                updateDialogFragment.dismiss();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (throwable != null && !TextUtils.isEmpty(throwable.getLocalizedMessage())) {
                                    throwable.printStackTrace();
                                    Log.d(TAG, "apk保存过程出错" + throwable.getLocalizedMessage());
                                    downloading = false;
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
                    downloading = false;
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
                    downloading = !done;
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
                            Uri apkUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            it.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        } else {
                            it.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity.getBaseContext(), 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
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
                        notificationManager.notify(notifyId, notification);
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
                    .setContentTitle("软件更新")
                    .setContentText("0%")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        } else {
            builder = new NotificationCompat.Builder(mActivity, PUSH_CHANNEL_ID)
                    .setContentTitle("软件更新")
                    .setContentText("0%")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true);
            notification = builder.build();
        }
        notificationManager.notify(notifyId, notification);

    }

    /**
     * 更新下载进度通知
     */

    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification = mBuilder
                        .setContentTitle("软件更新")
                        .setContentText(progress + "%")
                        .setProgress(100, (int) progress, false)
                        .build();
            } else {
                builder.setContentTitle("软件更新");
                builder.setContentText(progress + "%");
                builder.setProgress(100, (int) progress, false);
                notification = builder.build();
            }
            notificationManager.notify(notifyId, notification);
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(notifyId);
    }

    /**
     * 安装
     *
     * @param context
     * @param filePath
     * @return
     */
    public static boolean installApk(Context context, String filePath) {

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
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);

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
     * @param body 微信配置
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
