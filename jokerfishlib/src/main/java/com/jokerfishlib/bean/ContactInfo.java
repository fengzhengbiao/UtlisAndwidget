package com.jokerfishlib.bean;

import java.util.List;

/**
 * Created by JokerFish on 2017/7/13.
 * 联系人数据
 */

public class ContactInfo {
    private String name;
    private List<String> phones;
    private int timesContacted;

    public ContactInfo(String name, List<String> phone, int timesContacted) {
        this.name = name;
        this.phones = phone;
        this.timesContacted = timesContacted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public int getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(int timesContacted) {
        this.timesContacted = timesContacted;
    }
}
