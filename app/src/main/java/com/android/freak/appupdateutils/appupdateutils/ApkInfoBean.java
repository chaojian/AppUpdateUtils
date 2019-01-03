package com.android.freak.appupdateutils.appupdateutils;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 检查更新
 *
 * @author Administrator
 * @date 2019/1/2
 */

public class ApkInfoBean implements Serializable {
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


    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Boolean getForce() {
        return isForce;
    }

    public void setForce(Boolean force) {
        isForce = force;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getApkURL() {
        return apkURL;
    }

    public void setApkURL(String apkURL) {
        this.apkURL = apkURL;
    }

    public Long getApkSize() {
        return apkSize;
    }

    public void setApkSize(Long apkSize) {
        this.apkSize = apkSize;
    }

    public String getAddContent() {
        return addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public String getFixContent() {
        return fixContent;
    }

    public void setFixContent(String fixContent) {
        this.fixContent = fixContent;
    }

    public String getCancelContent() {
        return cancelContent;
    }

    public void setCancelContent(String cancelContent) {
        this.cancelContent = cancelContent;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
