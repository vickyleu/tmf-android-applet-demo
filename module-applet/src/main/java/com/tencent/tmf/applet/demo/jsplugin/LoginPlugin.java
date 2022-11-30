package com.tencent.tmf.applet.demo.jsplugin;

import com.tencent.qqmini.sdk.annotation.JsEvent;
import com.tencent.qqmini.sdk.annotation.JsPlugin;
import com.tencent.qqmini.sdk.launcher.core.model.RequestEvent;
import com.tencent.qqmini.sdk.launcher.core.plugins.BaseJsPlugin;

import org.json.JSONObject;

@JsPlugin(secondary = true)
public class LoginPlugin extends BaseJsPlugin {
    /**
     * 对应小程序wx.login调用
     * @param req
     */
    @JsEvent("login")
    public void login(final RequestEvent req) {
        //获取参数
        //req.jsonParams
        //异步返回数据
        //req.fail();
        //req.ok();
        req.ok(new JSONObject());
    }
}
