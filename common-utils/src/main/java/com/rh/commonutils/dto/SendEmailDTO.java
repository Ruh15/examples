package com.rh.commonutils.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/4
 **/
public class SendEmailDTO implements Serializable {
    private static final long serialVersionUID = -4863081332775935240L;

    String emailHost; // 指定要使用的邮件服务器
    String emailName; //使用的邮件服务器需提供已注册的用户名
    String emailPwd; //使用的邮件服务器需提供已注册的密码
    String emailCharset;
    String emailFrom;// 发件人
    String emailTo; // 收件人
    String emailCcs; // 抄送人
    String emailSubject; // 邮件主题
    String context; // 邮件内容

    List<Map> data;// 用来生成 excel 的数据

    /**
     * 是否需要开启 SSL 服务
     */
    boolean sslOnConnect;
    boolean sslCheckServerIdentity;
    boolean startTLSEnabled;
    boolean startTLSRequired;
    String sslSmtpPort;// ssl 端口

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isSslOnConnect() {
        return sslOnConnect;
    }

    public void setSslOnConnect(boolean sslOnConnect) {
        this.sslOnConnect = sslOnConnect;
    }

    public boolean isSslCheckServerIdentity() {
        return sslCheckServerIdentity;
    }

    public void setSslCheckServerIdentity(boolean sslCheckServerIdentity) {
        this.sslCheckServerIdentity = sslCheckServerIdentity;
    }

    public boolean isStartTLSEnabled() {
        return startTLSEnabled;
    }

    public void setStartTLSEnabled(boolean startTLSEnabled) {
        this.startTLSEnabled = startTLSEnabled;
    }

    public boolean isStartTLSRequired() {
        return startTLSRequired;
    }

    public void setStartTLSRequired(boolean startTLSRequired) {
        this.startTLSRequired = startTLSRequired;
    }

    public String getSslSmtpPort() {
        return sslSmtpPort;
    }

    public void setSslSmtpPort(String sslSmtpPort) {
        this.sslSmtpPort = sslSmtpPort;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    public String getEmailCharset() {
        return emailCharset;
    }

    public void setEmailCharset(String emailCharset) {
        this.emailCharset = emailCharset;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailCcs() {
        return emailCcs;
    }

    public void setEmailCcs(String emailCcs) {
        this.emailCcs = emailCcs;
    }
}
