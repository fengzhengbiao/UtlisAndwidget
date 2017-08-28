package com.jokerfishlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by jokerFish on 2016/11/14.
 * 一个可以自动测量高度的Listview,当条目数少于7时显示所有条目的高度,大于7时高度为1/3屏幕高度
 */

public class SheetDialogListView extends ListView {
    public SheetDialogListView(Context context) {
        super(context);
    }

    public SheetDialogListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SheetDialogListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            if (adapter.getCount() > 3) {
                WindowManager windowManager = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                setMeasuredDimension(display.getWidth(), display.getHeight() / 3);
            } else {
                int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, expandSpec);
            }
        }
    }

}
