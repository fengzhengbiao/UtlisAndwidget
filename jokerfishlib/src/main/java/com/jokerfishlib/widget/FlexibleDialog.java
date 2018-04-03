package com.jokerfishlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jokerfishlib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*********************************************
 *  Author  JokerFish 
 *  Create   2018/4/2
 *  Description 
 *  Email fengzhengbiao@vcard100.com
 **********************************************/
public class FlexibleDialog extends Dialog {
    private FlexiableParams flexibleParams;

    private FlexibleDialog(@NonNull Context context) {
        this(context, R.style.FromButtonDialogStyle);
    }

    private FlexibleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private FlexibleDialog(FlexiableParams params) {
        this(params.context, params.style);
        flexibleParams = params;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(flexibleParams.gravity);
        setContentView(flexibleParams.layRes);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = flexibleParams.width == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : flexibleParams.width;
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
    }

    private void initView() {
        TextView tvTitle = findViewById(flexibleParams.titleId);
        tvTitle.setText(flexibleParams.title);
        if (flexibleParams.textMap != null) {
            Iterator<Map.Entry<Integer, String>> iterator = flexibleParams.textMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, String> item = iterator.next();
                TextView textView = findViewById(item.getKey());
                textView.setText(item.getValue());
            }
        }
        if (flexibleParams.imageViewIds != null) {
            if (flexibleParams.listener == null) {
                throw new RuntimeException("You must set OnImageLoadListener to load images");
            }
            for (int i = 0; i < flexibleParams.imageViewIds.size(); i++) {
                int viewId = flexibleParams.imageViewIds.get(i);
                ImageView imageView = findViewById(viewId);
                flexibleParams.listener.load(imageView, viewId, i);
            }
        }

        if (flexibleParams.backgroundMaps != null) {
            Iterator<Integer> iterator = flexibleParams.backgroundMaps.keySet().iterator();
            while (iterator.hasNext()) {
                Integer viewId = iterator.next();
                findViewById(viewId).setBackgroundResource(flexibleParams.backgroundMaps.get(viewId));
            }
        }
        if (flexibleParams.onClickListener != null) {
            for (int listenerId : flexibleParams.listenerIds) {
                findViewById(listenerId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flexibleParams.onClickListener.onClick(v, FlexibleDialog.this);
                    }
                });
            }
        }

    }


    public static class Builder {
        FlexiableParams mParams;

        public Builder(Context context) {
            mParams = new FlexiableParams(context);
        }

        public Builder layoutRes(@LayoutRes int layRes) {
            mParams.layRes = layRes;
            return this;
        }

        public Builder style(@StyleRes int styRes) {
            mParams.style = styRes;
            return this;
        }

        public Builder width(int width) {
            mParams.width = width;
            return this;
        }

        public Builder left(int left) {
            mParams.left = left;
            return this;
        }

        public Builder buttom(int buttom) {
            mParams.buttom = buttom;
            return this;
        }

        public Builder showAt(int gravity) {
            mParams.gravity = gravity;
            return this;
        }

        public Builder title(@IdRes int titleId, String title) {
            mParams.titleId = titleId;
            mParams.title = title;
            return this;
        }


        public Builder title(@IdRes int titleId, @StringRes int titleTexId) {
            mParams.titleId = titleId;
            mParams.title = mParams.context.getString(titleTexId);
            return this;
        }

        public Builder text(@IdRes int titleId, @StringRes int titleTexId) {
            if (mParams.textMap == null) {
                mParams.textMap = new HashMap<>();
            }
            mParams.textMap.put(titleId, mParams.context.getString(titleTexId));
            return this;
        }


        public Builder text(@IdRes int textId, String text) {
            if (mParams.textMap == null) {
                mParams.textMap = new HashMap<>();
            }
            mParams.textMap.put(textId, text);
            return this;
        }

        public Builder imageIds(@IdRes int... imageId) {
            if (mParams.imageViewIds == null) {
                mParams.imageViewIds = new ArrayList<>();
            }
            for (int id : imageId) {
                mParams.imageViewIds.add(id);
            }
            return this;
        }

        public Builder onImageLoadListener(OnImageLoadListener listener) {
            mParams.listener = listener;
            return this;
        }

        public Builder backgroundRes(@IdRes int viewId, @DrawableRes int drawableResId) {
            if (mParams.backgroundMaps == null) {
                mParams.backgroundMaps = new HashMap<>();
            }
            mParams.backgroundMaps.put(viewId, drawableResId);
            return this;
        }

        public Builder onClickListener(OnClickListener listener, @IdRes int... ids) {
            mParams.onClickListener = listener;
            mParams.listenerIds = ids;
            return this;
        }

        public Dialog build() {
            return new FlexibleDialog(mParams);
        }

    }

    static class FlexiableParams {
        Context context;
        @StyleRes
        int style;
        @LayoutRes
        int layRes;
        @IdRes
        int titleId;
        String title;
        int gravity;
        int width = 0;
        int left = -1;
        int buttom = -1;
        Map<Integer, String> textMap;
        List<Integer> imageViewIds;
        int[] listenerIds;
        OnImageLoadListener listener;
        Map<Integer, Integer> backgroundMaps;
        OnClickListener onClickListener;


        public FlexiableParams(Context context) {
            this.context = context;
            style = R.style.FromButtonDialogStyle;

        }
    }


    public interface OnImageLoadListener {

        void load(ImageView view, @IdRes int viewId, int position);

    }

    public interface OnClickListener {
        void onClick(View view, DialogInterface dialogInterface);
    }

}
