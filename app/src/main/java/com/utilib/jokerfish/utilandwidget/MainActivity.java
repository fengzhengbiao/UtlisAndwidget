package com.utilib.jokerfish.utilandwidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        }
    }
}
