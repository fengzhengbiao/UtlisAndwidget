package com.jokerfishlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * 设置的数据必须实现IActionItem接口
 */

public class ActionSheetDialog extends Dialog {

    private SheetDialogListView mListView;
    private TextView tvTitle;
    private Button btnCancle;
    private String mTitle;
    private int mTitleColor;
    private float mTitleSize;
    private float mContentTextSize;
    private int mContentTextColor;
    private String mButtonText;
    private int mButtonColor;
    private OnActionClickListener mListener;
    private List mActionList;
    private String mDefaultActionCode;
    private int mTitleRes;
    private int mButtonRes;
    private float mButtonTextSize;
    private int mTitleBackground;
    private int mButtonBackground;

    public ActionSheetDialog(@NonNull Context context) {
        this(context, R.style.FromButtonDialogStyle);
    }

    public ActionSheetDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_actionsheet, null);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        view.setMinimumWidth(display.getWidth());
        mListView = (SheetDialogListView) view.findViewById(R.id.sdlv_content);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        btnCancle = (Button) view.findViewById(R.id.btn_cancle);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        showSheet();
    }

    private void showSheet() {
        if (mTitleRes == 0 && TextUtils.isEmpty(mTitle)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mTitle)) {
                tvTitle.setText(mTitle);
            }
            if (mTitleRes != 0) {
                tvTitle.setText(mTitleRes);
            }
            if (mTitleColor != 0) {
                tvTitle.setTextColor(mTitleColor);
            }
            if (mTitleSize != 0) {
                tvTitle.setTextSize(mTitleSize);
            }
            if (mTitleBackground != 0) {
                tvTitle.setBackgroundResource(mTitleBackground);
            }
        }
        if (mActionList != null && mActionList.size() > 0) {
            mListView.setAdapter(new ItemAdapter());
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListener != null) {
                    mListener.onActionClicked(ActionSheetDialog.this, (IActionItem) mActionList.get(i), i);
                } else {
                    dismiss();
                }
            }
        });
        if (!TextUtils.isEmpty(mButtonText)) {
            btnCancle.setText(mButtonText);
        }
        if (mButtonRes != 0) {
            btnCancle.setText(mButtonRes);
        }
        if (mButtonColor != 0) {
            btnCancle.setTextColor(mButtonColor);
        }
        if (mButtonTextSize != 0) {
            btnCancle.setTextSize(mButtonTextSize);
        }
        if (mButtonBackground != 0) {
            btnCancle.setBackgroundResource(mTitleBackground);
        }
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder {
        private ActionSheetDialog mDialog;

        public Builder(Context context) {
            mDialog = new ActionSheetDialog(context);
        }

        public Builder setTitle(String title) {
            mDialog.mTitle = title;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            mDialog.mTitleRes = title;
            return this;
        }

        public Builder setTitleColor(@ColorRes int colorRes) {
            mDialog.mTitleColor = colorRes;
            return this;
        }

        public Builder setTitleBackground(@DrawableRes int draRes) {
            mDialog.mTitleBackground = draRes;
            return this;
        }

        public Builder setTitleTextSize(float titleSize) {
            mDialog.mTitleSize = titleSize;
            return this;
        }

        public Builder setContentTextSize(float titleSize) {
            mDialog.mContentTextSize = titleSize;
            return this;
        }

        public Builder setActionTextColor(@ColorRes int actColorRes) {
            mDialog.mContentTextColor = actColorRes;
            return this;
        }

        public Builder setActionData(List actions) {
            mDialog.mActionList = actions;
            return this;
        }

        public Builder setDefaultAction(String actCode) {
            mDialog.mDefaultActionCode = actCode;
            return this;
        }

        public Builder setButton(String buttonText) {
            mDialog.mButtonText = buttonText;
            return this;
        }

        public Builder setButton(@StringRes int btnRes) {
            mDialog.mButtonRes = btnRes;
            return this;
        }

        public Builder setButtonTextColor(@ColorRes int colorRes) {
            mDialog.mButtonColor = colorRes;
            return this;
        }

        public Builder setButtonBackground(@DrawableRes int draRes) {
            mDialog.mButtonBackground = draRes;
            return this;
        }

        public Builder setButtonTextSize(float size) {
            mDialog.mButtonTextSize = size;
            return this;
        }

        public Builder setActionItemClickListener(OnActionClickListener listener) {
            mDialog.mListener = listener;
            return this;
        }

        public ActionSheetDialog build() {
            return mDialog;
        }
    }


    private class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mActionList == null ? 0 : mActionList.size();
        }

        @Override
        public Object getItem(int position) {
            return mActionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_action_sheet, parent, false);
                viewHolder = new ItemViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }
            IActionItem actionItem = (IActionItem) mActionList.get(position);
            if (TextUtils.equals(mDefaultActionCode, actionItem.actionSheetId())) {
                viewHolder.textView.setEnabled(false);
            } else {
                viewHolder.textView.setEnabled(true);
            }
            viewHolder.textView.setText(actionItem.getDescription());
            return convertView;
        }

        class ItemViewHolder {
            public TextView textView;

            public ItemViewHolder(View itemView) {
                textView = (TextView) itemView.findViewById(R.id.tv_desc);
                if (mContentTextSize != 0) {
                    textView.setTextSize(mContentTextSize);
                }
                if (mContentTextColor != 0) {
                    textView.setTextColor(mContentTextColor);
                }
            }
        }
    }

    public interface OnActionClickListener {

        void onActionClicked(DialogInterface dialogInterface, IActionItem item, int position);

    }

}
