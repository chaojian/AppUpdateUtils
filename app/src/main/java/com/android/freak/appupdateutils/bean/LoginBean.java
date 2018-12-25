package com.android.freak.appupdateutils.bean;

/**
 * Created by Administrator on 2018/12/25.
 */

public class LoginBean {
    private String user_role;
    private String qcwd_mcht;
    private String user_code;
    private String user_mobil;
    private String KJL;
    private String identify_status;
    private String key;
    private String is_show;

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getQcwd_mcht() {
        return qcwd_mcht;
    }

    public void setQcwd_mcht(String qcwd_mcht) {
        this.qcwd_mcht = qcwd_mcht;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_mobil() {
        return user_mobil;
    }

    public void setUser_mobil(String user_mobil) {
        this.user_mobil = user_mobil;
    }

    public String getKJL() {
        return KJL;
    }

    public void setKJL(String KJL) {
        this.KJL = KJL;
    }

    public String getIdentify_status() {
        return identify_status;
    }

    public void setIdentify_status(String identify_status) {
        this.identify_status = identify_status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "user_role='" + user_role + '\'' +
                ", qcwd_mcht='" + qcwd_mcht + '\'' +
                ", user_code='" + user_code + '\'' +
                ", user_mobil='" + user_mobil + '\'' +
                ", KJL='" + KJL + '\'' +
                ", identify_status='" + identify_status + '\'' +
                ", key='" + key + '\'' +
                ", is_show='" + is_show + '\'' +
                '}';
    }
}
