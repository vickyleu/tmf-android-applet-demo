package com.tencent.tmf.applet.demo;

import android.app.Application;

import com.tencent.tmf.common.CommonApp;

import java.io.Serializable;

public class DemoApp extends Application implements Serializable {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonApp.get().onCreate(this);
    }


}
