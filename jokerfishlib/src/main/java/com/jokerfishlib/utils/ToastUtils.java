package com.jokerfishlib.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by JokerFish on 2017/07/11.
 * 吐司工具类
 */

public class ToastUtils {
    private static final String TAG = SpUtils.class.getSimpleName();
    private static Toast sToast;
    private static Context appContext;

    public static synchronized void init(Context context) {
        if (appContext == null) {
            appContext = context.getApplicationContext();
        }
        Log.i(TAG, "SpUtils has been initialized ");
    }

    public static void showToast(String msg) {
        if (appContext == null) {
            throw new RuntimeException("You should call init() after use ToastUtils");
        }
        if (sToast == null) {
            sToast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToast(@StringRes int resId) {
        if (appContext == null) {
            throw new RuntimeException("You should call init() after use ToastUtils");
        }
        if (sToast == null) {
            sToast = Toast.makeText(appContext, appContext.getResources().getString(resId), Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(appContext.getResources().getString(resId));
        }
        sToast.show();
    }

    public static void cancleToast() {
        if (appContext == null) {
            throw new RuntimeException("You should call init() after use ToastUtils");
        }
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
