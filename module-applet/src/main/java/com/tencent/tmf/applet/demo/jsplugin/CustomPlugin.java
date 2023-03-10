package com.tencent.tmf.applet.demo.jsplugin;

import android.util.Log;

import com.tencent.tmf.applet.demo.ModuleApplet;
import com.tencent.tmfmini.sdk.annotation.JsEvent;
import com.tencent.tmfmini.sdk.annotation.JsPlugin;
import com.tencent.tmfmini.sdk.launcher.core.model.RequestEvent;
import com.tencent.tmfmini.sdk.launcher.core.plugins.BaseJsPlugin;

import org.json.JSONException;
import org.json.JSONObject;

@JsPlugin(secondary = true)
public class CustomPlugin extends BaseJsPlugin {
    @JsEvent("custom_event")
    public void custom(final RequestEvent req) {
        //获取参数
        //req.jsonParams
        //异步返回数据
        //req.fail();
        //req.ok();
        Log.d(ModuleApplet.TAG, "CustomPlugin=" + req.jsonParams);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", "test");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        req.ok(jsonObject);
    }

    @JsEvent({"getSystemInfo", "getSystemInfoSync"})
    public String custom1(final RequestEvent req) {
        //获取参数
        //req.jsonParams
        //同步返回数据(必须返回json数据)
        return new JSONObject().toString();
    }
}
