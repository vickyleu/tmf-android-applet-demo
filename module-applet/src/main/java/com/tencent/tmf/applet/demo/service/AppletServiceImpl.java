package com.tencent.tmf.applet.demo.service;

import android.app.Activity;
import android.content.Context;

import com.tencent.tmf.applet.demo.ModuleApplet;
import com.tencent.tmf.mini.api.TmfMiniSDK;
import com.tencent.tmf.applet.demo.sp.impl.CommonSp;
import com.tencent.tmf.common.gen.ModuleAppletConst;
import com.tencent.tmf.common.service.IAppletService;
import com.tencent.tmf.portal.Portal;

public class AppletServiceImpl implements IAppletService {

    @Override
    public void startAppletModule(Activity activity) {
        if (CommonSp.getInstance().isSkipLogin()) {
            Portal.from(activity).url(ModuleAppletConst.U_MAIN_ACTIVITY).launch();
        } else {
            if (TmfMiniSDK.isLoginOvertime()) {
                Portal.from(activity).url(ModuleAppletConst.U_LOGIN_ACTIVITY).launch();
            } else {
                Portal.from(activity).url(ModuleAppletConst.U_MAIN_ACTIVITY).launch();
            }
        }
    }

    @Override
    public boolean isPrivacyAuth(Context context) {
        return CommonSp.getInstance().isPrivacyAuth(context);
    }

    @Override
    public void agreePrivacyAuth(Context context) {
        CommonSp.getInstance().putPrivacyAuth(context, true);
        //同意隐私授权
        TmfMiniSDK.agreePrivacyAuth();
        TmfMiniSDK.setLocation(ModuleApplet.COUNTRY, ModuleApplet.PROVINCE, ModuleApplet.CITY);
        TmfMiniSDK.preloadMiniApp(context, null);
    }
}
