package com.tencent.tmf.applet.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.tmf.applet.demo.R;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applet_activity_test);

        findViewById(R.id.btn_overlay).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_overlay) {
            openSetting(this);
        }
    }

    public static void openSetting(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean hasShowPermission = Settings.canDrawOverlays(context);  // 检测是否拥有显示在其他应用程序上层的权限
            // 没有权限  前往设置页面开启权限
//            if (!hasShowPermission) {
//                Toast.makeText(context, "请开启app显示在其他应用程序上层权限", Toast.LENGTH_SHORT).show();
//
//            }

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
