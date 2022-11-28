package com.tencent.tmf.demo.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.webkit.ValueCallback;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

public class WxApiUtil {

    public static final String APP_ID = "wx8071141a542f9dad";

    IWXAPI api;
    ValueCallback<JSONObject> callback;

    public static WxApiUtil getInstance() {
        return Holder.sInstance;
    }

    public void regToWx(Context context) {
        // 通过 WXAPIFactory 工厂，获取 IWXAPI 的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        // 将应用的 appId 注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该 app 注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public void sendLogin(JSONObject jsonObject, ValueCallback<JSONObject> callback) {
        this.callback = callback;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        boolean ret = api.sendReq(req);
        Log.i(WXEntryActivity.TAG, "send login to wx " + ret);

    }

    public void sendLoginCallBack(JSONObject jsonObject) {
        if (null != callback) {
            callback.onReceiveValue(jsonObject);
        }
    }


    private static class Holder {

        private static final WxApiUtil sInstance = new WxApiUtil();
    }
}
