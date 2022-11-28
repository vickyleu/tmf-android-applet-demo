package com.tencent.tmf.demo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tmf.share.api.TMFShareService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String TAG = "TMF_APPLET_DEMO";

    private IWXAPI api;
    private MyHandler handler;

    private static class MyHandler extends Handler {

        String openId = "";
        String accessToken = null;
        String refreshToken = null;
        String scope = "";
        private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;

        public MyHandler(WXEntryActivity wxEntryActivity) {
            wxEntryActivityWeakReference = new WeakReference<WXEntryActivity>(wxEntryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            Bundle data = msg.getData();
            JSONObject json = null;

            switch (tag) {
                case NetworkUtil.GET_TOKEN: {

                    try {
                        json = new JSONObject(data.getString("result"));
                        Log.i(TAG, "NetworkUtil.GET_TOKEN " + json);

                        openId = json.getString("openid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                        scope = json.getString("scope");
                        Log.i(TAG, "get weachat login1 ret " + json);
//                        Toast.makeText(wxEntryActivityWeakReference.get().getApplicationContext(), "获取Token" + json,
//                                Toast.LENGTH_LONG).show();

                        NetworkUtil.sendWxAPI(this, String.format("https://api.weixin.qq.com/sns/auth?" +
                                "access_token=%s&openid=%s", accessToken, openId), NetworkUtil.CHECK_TOKEN);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    break;
                }
                case NetworkUtil.GET_INFO: {
                    try {
                        json = new JSONObject(data.getString("result"));
                        Log.i(TAG, "NetworkUtil.GET_INFO get wx accountInfo " + json);

//                        Toast.makeText(wxEntryActivityWeakReference.get().getApplicationContext(), "获取账户信息" + json,
//                                Toast.LENGTH_LONG).show();
                        json.put("success", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    WxApiUtil.getInstance().sendLoginCallBack(json);
                    break;
                }
                case NetworkUtil.CHECK_TOKEN: {
                    try {
                        json = new JSONObject(data.getString("result"));
                        Log.i(TAG, "NetworkUtil.CHECK_TOKEN " + json);

                        int errcode = json.getInt("errcode");
                        if (errcode == 0) {
                            Log.i(TAG, "CHECK_TOKEN success " + accessToken);
                            NetworkUtil.sendWxAPI(this, String.format("https://api.weixin.qq.com/sns/userinfo?" +
                                    "access_token=%s&openid=%s", accessToken, openId), NetworkUtil.GET_INFO);
                        } else {
                            NetworkUtil.sendWxAPI(this,
                                    String.format("https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                                                    "appid=%s&grant_type=refresh_token&refresh_token=%s", WxApiUtil.APP_ID,
                                            refreshToken),
                                    NetworkUtil.REFRESH_TOKEN);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    break;
                }
                case NetworkUtil.REFRESH_TOKEN: {

                    try {
                        json = new JSONObject(data.getString("result"));
                        Log.i(TAG, "NetworkUtil.REFRESH_TOKEN " + json);
                        openId = json.getString("openid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                        scope = json.getString("scope");
                        NetworkUtil.sendWxAPI(this, String.format("https://api.weixin.qq.com/sns/userinfo?" +
                                "access_token=%s&openid=%s", accessToken, openId), NetworkUtil.GET_INFO);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isDebugWxLogin(getIntent())) {
            api = WXAPIFactory.createWXAPI(this, WxApiUtil.APP_ID, false);
            handler = new MyHandler(this);

            try {
                Intent intent = getIntent();
                api.handleIntent(intent, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TMFShareService.getInstance().handleWxIntent(getIntent());
            finish();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (isDebugWxLogin(getIntent())) {
            setIntent(intent);
            api.handleIntent(intent, this);
        } else {
            TMFShareService.getInstance().handleWxIntent(getIntent());
            finish();
        }
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//			goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//			goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
//        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
//        Log.i(App.TAG,"get data resp type {} code {} errStr {}", resp.getType(), resp.errCode, resp.errStr);

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            final String code = authResp.code;
//            Log.i(App.TAG,"wechat http auth {} {}", code);
            NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code", WxApiUtil.APP_ID,
                    "77d441ad8a73dfd17471b6901eade71c", code), NetworkUtil.GET_TOKEN);

        }
        finish();
    }

    ///sns/userinfo
    private void getUserInfo() {
    }

    private boolean isDebugWxLogin(Intent intent) {
        Bundle data = intent.getExtras();
        StringBuilder builder = new StringBuilder("extra");
        for (String key : data.keySet()) {
            builder.append(key).append(":").append(data.get(key)).append("|");
        }
        String wxapi_sendauth_resp_token = data.getString("_wxapi_sendauth_resp_token");

        Log.i(TAG, "wxentry handle intent {} with scope " + builder);

        return !TextUtils.isEmpty(wxapi_sendauth_resp_token);
    }

    private void showLongToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

}