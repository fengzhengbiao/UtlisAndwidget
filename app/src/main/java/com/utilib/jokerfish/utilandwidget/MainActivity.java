package com.utilib.jokerfish.utilandwidget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jokerfishlib.utils.PhoneUtils;
import com.jokerfishlib.widget.ActionSheetDialog;
import com.utilib.jokerfish.utilandwidget.bean.TestData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSheetDiaglog;
    private List<TestData> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSheetDiaglog = (Button) findViewById(R.id.btn_sheetdialog);
        findViewById(R.id.btn_all_app).setOnClickListener(this);
        btnSheetDiaglog.setOnClickListener(this);

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
        }
    }
}
