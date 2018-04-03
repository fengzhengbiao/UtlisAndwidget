package com.jokerfishlib.bean;

import com.jokerfishlib.widget.IActionItem;

/**
 * Created by JokerFish on 2017/9/7.
 */

public class SimAction implements IActionItem {
    private String code;
    private String desc;

    public SimAction(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String actionSheetId() {
        return code;
    }
}
