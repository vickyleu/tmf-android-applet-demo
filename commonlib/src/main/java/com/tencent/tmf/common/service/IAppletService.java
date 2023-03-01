package com.tencent.tmf.common.service;

import android.app.Activity;
import android.content.Context;

public interface IAppletService {
    void startAppletModule(Activity activity);

    boolean isPrivacyAuth(Context context);

    void agreePrivacyAuth(Context context);
}
