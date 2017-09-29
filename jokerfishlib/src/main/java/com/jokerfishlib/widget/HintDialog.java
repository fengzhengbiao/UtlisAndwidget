package com.jokerfishlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jokerfishlib.R;


/**
 * Created by jokerFish on 2016/12/30.
 */

public class HintDialog extends Dialog {

    private Button btnOk, btnCancle;
    private TextView tvTitle;
    private TextView tvMessage;
    private String title;
    private int titleBackground;
    private String message;
    private String leftButtonName;
    private String rightButtonName;
    private OnRightClickListener rightClickListener;
    private OnLeftClickListener leftClickListener;
    private int buttonTextSize;
    private int leftButtonBackground;
    private int rightButtonBackground;

    public HintDialog(Context context) {
        this(context, R.style.FromButtonDialogStyle);
    }

    public HintDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public HintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
    }

    private void initView() {
        btnOk = (Button) findViewById(R.id.btn_confirm);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_message);
    }

    public static class Builder {
        private HintDialog mHintDialog;

        public Builder(Context context) {
            mHintDialog = new HintDialog(context);
        }

        public Builder title(String title) {
            mHintDialog.title = title;
            return this;
        }

        public Builder title(@StringRes int title) {
            mHintDialog.title = mHintDialog.getContext().getString(title);
            return this;
        }

        public Builder titleBackground(@DrawableRes int titleBackground) {
            mHintDialog.titleBackground = titleBackground;
            return this;
        }

        public Builder message(String message) {
            mHintDialog.message = message;
            return this;
        }

        public Builder leftButtonName(String name) {
            mHintDialog.leftButtonName = name;
            return this;
        }

        public Builder rightButtonName(String name) {
            mHintDialog.rightButtonName = name;
            return this;
        }

        public Builder rightButtonListener(OnRightClickListener onRightClickListener) {
            mHintDialog.rightClickListener = onRightClickListener;
            return this;
        }

        public Builder leftButtonListener(OnLeftClickListener onLeftClickListener) {
            mHintDialog.leftClickListener = onLeftClickListener;
            return this;
        }

        public Builder buttonTextSize(int spSize) {
            mHintDialog.buttonTextSize = spSize;
            return this;
        }

        public Builder leftButtonBackground(@DrawableRes int leftBackground) {
            mHintDialog.leftButtonBackground = leftBackground;
            return this;
        }

        public Builder rightButtonBackground(@DrawableRes int rightBackground) {
            mHintDialog.rightButtonBackground = rightBackground;
            return this;
        }

        public HintDialog build() {
            return mHintDialog;
        }


    }

    @Override
    public void show() {
        super.show();
        createHintDialog();
    }

    private void createHintDialog() {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (titleBackground != 0) {
            tvTitle.setBackgroundResource(titleBackground);
        }
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }
        if (!TextUtils.isEmpty(leftButtonName)) {
            btnCancle.setText(leftButtonName);
        }
        if (!TextUtils.isEmpty(rightButtonName)) {
            btnOk.setText(rightButtonName);
        }
        if (buttonTextSize > 0) {
            btnCancle.setTextSize(buttonTextSize);
        }
        if (leftButtonBackground != 0) {
            btnCancle.setBackgroundResource(leftButtonBackground);
        }
        if (rightButtonBackground != 0) {
            btnOk.setBackgroundResource(rightButtonBackground);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightClickListener != null) {
                    rightClickListener.onRightClicked(HintDialog.this);
                } else {
                    dismiss();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftClickListener != null) {
                    leftClickListener.onLeftClicked(HintDialog.this);
                } else {
                    dismiss();
                }
            }
        });
    }

    public interface OnRightClickListener {
        void onRightClicked(DialogInterface dialog);
    }

    public interface OnLeftClickListener {
        void onLeftClicked(DialogInterface dialog);
    }
}
