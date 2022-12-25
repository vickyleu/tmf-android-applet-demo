package com.tencent.tmf.applet.demo;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.tencent.tmf.common.CommonApp;

import java.io.Serializable;

public class DemoApp extends Application implements Serializable {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决demo工程开启混淆后报错: java.lang.ClassNotFoundException: Didn't find class "androidx.core.app.CoreComponentFactory"
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApp.get().onCreate(this);
    }


}
