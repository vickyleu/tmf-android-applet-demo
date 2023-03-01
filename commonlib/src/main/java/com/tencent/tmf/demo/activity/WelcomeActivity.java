package com.tencent.tmf.demo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.tmf.common.R;
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

        final IAppletService service = Portal.getService(IAppletService.class);
        if (service.isPrivacyAuth(this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    service.startAppletModule(WelcomeActivity.this);
                    finish();
                }
            }, 1000);
        } else {
            showPrivateAuth();
        }
    }

    private void showPrivateAuth() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.applet_main_privacy_auth)//标题
                .setMessage(R.string.applet_main_privacy_auth_content)//内容
                .setPositiveButton(R.string.applet_main_act_delete_msg_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final IAppletService service = Portal.getService(IAppletService.class);
                        //同意隐私授权
                        service.agreePrivacyAuth(WelcomeActivity.this);
                        service.startAppletModule(WelcomeActivity.this);
                        finish();
                    }
                })
                .setNegativeButton(R.string.applet_main_act_delete_msg_cancal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setCancelable(false)
                .create();
        alertDialog.show();
    }
}















