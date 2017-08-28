package com.jokerfishlib.bean;

/**
 * Created by JokerFish on 2017/7/13.
 * 联系人数据
 */

public class ContactInfo {
    private String name;
    private String phone;
    private int timesContacted;

    public ContactInfo(String name, String phone, int timesContacted) {
        this.name = name;
        this.phone = phone;
        this.timesContacted = timesContacted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(int timesContacted) {
        this.timesContacted = timesContacted;
    }
}
