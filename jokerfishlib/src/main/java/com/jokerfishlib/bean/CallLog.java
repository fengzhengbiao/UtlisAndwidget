package com.jokerfishlib.bean;

/**
 * Created by JokerFish on 2017/8/2.
 * 通话记录
 */

public class CallLog {
    private int type;   //0 呼入  1呼出 2未接
    private String name;       //称呼
    private String number;   //电话
    private long duration;   //通话时长
    private long date;   //通话时间

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
