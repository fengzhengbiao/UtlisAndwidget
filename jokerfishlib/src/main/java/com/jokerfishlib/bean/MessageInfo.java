package com.jokerfishlib.bean;

/**
 * Created by JokerFish on 2017/8/2.
 */

public class MessageInfo {
    private String name;        //收发人姓名,没有默认为电话号码
    private String phoneNumb;       //收发人电话号码
    private String smsContent;      //短信内容
    private long date;          //收发日期
    private int type;           //收发类型 1表示接受 2表示发送
    private int read;           //是否已读 0未读 1已读

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
