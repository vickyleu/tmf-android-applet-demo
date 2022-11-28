package com.tencent.tmf.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.tmf.common.R;
import com.tencent.tmf.common.gen.ModuleAppletConst;
import com.tencent.tmf.common.service.IAppletService;
import com.tencent.tmf.portal.Portal;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为无标题(去掉Android自带的标题栏)，(全屏功能与此无关)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置为全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.applet_activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IAppletService service = Portal.getService(IAppletService.class);
                service.startAppletModule(WelcomeActivity.this);
                finish();
            }
        }, 1000);
    }
}















