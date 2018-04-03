package com.utilib.jokerfish.utilandwidget;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jokerfishlib.utils.PhoneUtils;
import com.jokerfishlib.utils.WifiListener;
import com.jokerfishlib.utils.WifiUtils;
import com.jokerfishlib.widget.ActionSheetDialog;
import com.jokerfishlib.widget.FlexibleDialog;
import com.jokerfishlib.widget.HintDialog;
import com.jokerfishlib.widget.LoadingDialog;
import com.utilib.jokerfish.utilandwidget.bean.TestData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSheetDiaglog;
    private List<TestData> datas;
    private WifiUtils wifiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSheetDiaglog = (Button) findViewById(R.id.btn_sheetdialog);
        findViewById(R.id.btn_all_app).setOnClickListener(this);
        findViewById(R.id.bt_hint_dialog).setOnClickListener(this);
        findViewById(R.id.bt_loading).setOnClickListener(this);
        findViewById(R.id.bt_flexiable).setOnClickListener(this);
        btnSheetDiaglog.setOnClickListener(this);
        wifiUtils = new WifiUtils(this);
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new TestData(i + "", "测试条目" + i));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sheetdialog:
                new ActionSheetDialog.Builder(this)
                        .setTitle("提示")
                        .setActionData(datas)
                        .setButton("取消")
                        .build()
                        .show();
                break;
            case R.id.btn_all_app:
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    PhoneUtils.getUsageStatus(this);
                }
                break;
            case R.id.bt_close:
                wifiUtils.CloseWifi();
                break;
            case R.id.bt_open:
                wifiUtils.OpenWifi();
                break;
            case R.id.bt_flexiable:
                new FlexibleDialog.Builder(this)
                        .layoutRes(R.layout.flexiable_dialog)
                        .title(R.id.tv_title, "可变对话框")
                        .imageIds(R.id.iv_image)
                        .onImageLoadListener(new FlexibleDialog.OnImageLoadListener() {
                            @Override
                            public void load(ImageView view, int viewId, int position) {
                                view.setImageResource(R.mipmap.ic_launcher);
                            }
                        }).onClickListener(new FlexibleDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                }, R.id.button)
                        .showAt(Gravity.CENTER)
                        .build()
                        .show();

                break;
            case R.id.bt_hint_dialog:
                new HintDialog.Builder(this)
                        .title("警告")
                        .leftButtonName("忽略")
                        .rightButtonName("明白")
                        .leftButtonListener(new HintDialog.OnLeftClickListener() {
                            @Override
                            public void onLeftClicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .rightButtonListener(new HintDialog.OnRightClickListener() {
                            @Override
                            public void onRightClicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .message("这是一个警告！")
                        .build()
                        .show();


                break;
            case R.id.bt_loading:
                LoadingDialog.show(this, "登录...");
                break;
        }
    }

    private void regist() {
        wifiUtils.register(new WifiListener() {

            @Override
            public void wifiOpen() {
                Log.i("haha", "wifiOpen");
            }

            @Override
            public void wifiNotConnect(NetworkInfo networkInfo) {
                Log.i("haha", "wifiNotConnect");
            }

            @Override
            public void wifiConnected(NetworkInfo networkInfo) {
                Log.i("haha", "wifiConnected");
            }

            @Override
            public void wifiClose() {
                Log.i("haha", "wifiClose");
            }

            @Override
            public void notConnected() {
                Log.i("haha", "notConnected");
            }

            @Override
            public void connected(ConnectivityManager manager, NetworkInfo networkInfo) {
                Log.i("haha", "connected");

            }
        });
    }
}
