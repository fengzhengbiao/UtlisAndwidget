package com.jokerfishlib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by JokerFish on 2017/8/30.
 */

public class KeyBoardUtil {
    /**
     * 解决透明状态栏下，布局无法自动拉起的问题
     * 手动设置View的高度
     */
    private static int lastHeightDiff;

    public static void autoResizeForKeyBoard(final Activity activity) {
        final View rootView = ((ViewGroup) activity.findViewById(android.R.id.content))
                .getChildAt(0);
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                int heightDifferent = screenHeight - rect.bottom - StatusbarUtils.getNeivigationBarHeight(activity);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                if (heightDifferent != lastHeightDiff) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        lp.setMargins(0, 0, 0, heightDifferent);
                        rootView.requestLayout();
                        lastHeightDiff = heightDifferent;
                    }
                }
            }
        });
    }

    /**
     * 隐藏系统键盘
     * @param mcontext
     */
    public static void closeKeyBoard(Activity mcontext) {
        InputMethodManager imm = (InputMethodManager) mcontext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        if (imm.isActive()) { //一直是true
            imm.hideSoftInputFromWindow(mcontext.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 显示键盘
     *
     * @param editText
     */
    public static void showKeybord(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }
}



