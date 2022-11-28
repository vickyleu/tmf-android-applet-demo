package com.tencent.tmf.applet.demo.jsplugin;

import com.tencent.qqmini.sdk.annotation.JsEvent;
import com.tencent.qqmini.sdk.annotation.JsPlugin;
import com.tencent.qqmini.sdk.launcher.core.model.RequestEvent;
import com.tencent.qqmini.sdk.launcher.core.plugins.BaseJsPlugin;
import com.tencent.qqmini.sdk.launcher.log.QMLog;

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
        req.ok(new JSONObject());
    }

    @JsEvent({"getSystemInfo", "getSystemInfoSync"})
    public String custom1(final RequestEvent req) {
        //获取参数
        //req.jsonParams
        //同步返回数据(必须返回json数据)
        return new JSONObject().toString();
    }
}
