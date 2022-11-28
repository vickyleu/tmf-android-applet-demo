package com.tencent.tmf.common;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.tencent.tmf.portal.Portal;

public class CommonApp {

    private static CommonApp sInstance;
    private Application application;
    private Context context;

    public static CommonApp get() {
        if (sInstance == null) {
            sInstance = new CommonApp();
        }
        return sInstance;
    }

    public Application getApplication() {
        return application;
    }


    public void onCreate(Application application) {
        this.application = application;
        this.context = application.getApplicationContext();

        initPortal();
    }

    /**
     * 初始化Demo组件框架
     */
    private void initPortal() {
        // 设置可输出log
        Portal.setDebuggable(true);

        Bundle param = new Bundle();
        Portal.init(context, null, param);
    }
}
