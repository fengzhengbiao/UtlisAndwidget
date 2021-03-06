package com.jokerfishlib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by jokerFish on 2016/10/27.
 * 状态栏工具类
 */

public class StatusbarUtils {
    private static int statusBarHeight = -1;

    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && activity != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  //该参数指布局能延伸到navigationbar，我们场景中不应加这个参数
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            if (titleViewGroup == null) return;
            // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
            int statusBarHeight = getStatusBarHeight(activity);
            titleViewGroup.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 反射获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight != -1) {
            return statusBarHeight;
        }
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }
        statusBarHeight = sbar;
        return sbar;
    }

    /**
     * 获取底部导航栏的高度
     *
     * @param context
     * @return
     */
    public static int getNeivigationBarHeight(Context context) {
        try {
            Resources res = context.getResources();
            int navigationHeight = res.getIdentifier("navigation_bar_height", "dimen", "android");
            return res.getDimensionPixelSize(navigationHeight);
        } catch (Exception e) {
            return 0;
        }
    }
}
