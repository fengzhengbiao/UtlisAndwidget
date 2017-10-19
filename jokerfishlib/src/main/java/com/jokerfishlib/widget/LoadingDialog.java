package com.jokerfishlib.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.jokerfishlib.R;

/**
 * Created by JokerFish on 2017/9/29.
 */

public class LoadingDialog extends Dialog {

    private TextView tvDesc;
    private static LoadingDialog dialog;
    private static ObjectAnimator objectAnimator;

    private static LoadingDialog getInstance(Context context) {
        if (dialog == null) {
            dialog = new LoadingDialog(context);
        }
        return dialog;
    }

    private LoadingDialog(@NonNull Context context) {
        this(context, R.style.ScaleCenterDialog);
    }


    private LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        View viewById = findViewById(R.id.iv_loading);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        objectAnimator = ObjectAnimator.ofFloat(viewById, "rotation", 0, 360);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public static void show(Context context, String msg) {
        LoadingDialog instance = LoadingDialog.getInstance(context);
        if (instance != null && instance.isShowing()) {
            instance.tvDesc.setText(msg);
            instance.show();
        } else {
            instance.show();
            instance.tvDesc.setText(msg);
        }
    }

    public static void dissmiss() {
        if (LoadingDialog.dialog != null) {
            if (LoadingDialog.objectAnimator != null) {
                objectAnimator.end();
            }
            if (LoadingDialog.dialog.isShowing()) {
                LoadingDialog.dialog.dismiss();
            }
            LoadingDialog.dialog = null;
        }
    }
}
