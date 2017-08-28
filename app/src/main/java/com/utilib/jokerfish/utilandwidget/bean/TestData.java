package com.utilib.jokerfish.utilandwidget.bean;

import com.jokerfishlib.widget.IActionItem;

/**
 * Created by JokerFish on 2017/8/28.
 */

public class TestData implements IActionItem {
    private String id;
    private String content;

    public TestData(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDescription() {
        return content;
    }

    @Override
    public String actionSheetId() {
        return id;
    }
}
