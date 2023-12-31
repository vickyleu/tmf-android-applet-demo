package com.tencent.tmf.applet.demo.proxyimpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.tmf.applet.demo.R;
import com.tencent.tmf.mini.api.bean.MiniAppLoading;
import com.tencent.tmf.mini.api.bean.MiniAuthInfo;
import com.tencent.tmf.mini.api.callback.IAuthView;
import com.tencent.tmf.mini.api.callback.IMiniLoading;
import com.tencent.tmfmini.sdk.annotation.ProxyService;
import com.tencent.tmfmini.sdk.launcher.core.proxy.AbsMiniUiProxy;
import com.tencent.tmfmini.sdk.launcher.core.proxy.IMiniUiProxy;

@ProxyService(proxy = IMiniUiProxy.class)
public class MiniUiProxyImpl extends AbsMiniUiProxy {
    /**
     * 自定义导航栏返回icon, 宽高比要求=24x43
     * @param mode 导航背景颜色, 1:black 0:white
     * @return
     */
    @Override
    public int navBarBackRes(int mode) {
//        if(mode == BLACK) {
//            return R.drawable.back_icon;
//        }else {
//            return R.drawable.back_icon;
//        }

        return 0;
    }

    /**
     * 导航栏返回主页图标icon, 宽高比要求=48x48
     * @param mode 导航背景颜色, 1:black 0:white
     * @return
     */
    @Override
    public int homeButtonRes(int mode) {
        return 0;
    }

    /**
     * 胶囊更多图标icon, 宽高比要求=80x59
     * @param mode 导航背景颜色, 1:black 0:white
     * @return
     */
    @Override
    public int moreButtonRes(int mode) {
//        if(mode == BLACK) {
//            return R.drawable.back_icon;
//        }else {
//            return R.drawable.back_icon;
//        }

        return 0;
    }

    /**
     * 胶囊关闭图标icon, 宽高比要求=80x59
     * @param mode 导航背景颜色, 1:black 0:white
     * @return
     */
    @Override
    public int closeButtonRes(int mode) {
//        if(mode == BLACK) {
//            return R.drawable.back_icon;
//        }else {
//            return R.drawable.back_icon;
//        }

        return 0;
    }

    /**
     * 胶囊按钮中间分割线背景颜色
     * @return
     */
    @Override
    public int lineSplitBackgroundColor() {
//        return R.color.applet_color_primary;
        return 0;
    }

    /**
     * 自定义小程序检查更新loading页面
     * @param context
     * @return
     */
    @Override
    public IMiniLoading updateLoadingView(Context context) {
//        return new IMiniLoading() {
//            @Override
//            public View create() {
//                return LayoutInflater.from(context).inflate(R.layout.applet_activity_custom_update_loading, null);
//            }
//
//            @Override
//            public void show(View v) {
//
//            }
//
//            @Override
//            public void stop(View v) {
//
//            }
//        };

        return null;
    }

    /**
     * 自定义小程序加载loading页面
     * 调用环境：主进程
     *
     * @param context 小程序上下文
     * @param app 小程序信息
     * @return 返回小程序loading UI
     */
    @Override
    public IMiniLoading startLoadingView(Context context, MiniAppLoading app) {
//        return new IMiniLoading() {
//            @Override
//            public View create() {
//                return LayoutInflater.from(context).inflate(R.layout.applet_activity_custom_start_loading, null);
//            }
//
//            @Override
//            public void show(View v) {
//
//            }
//
//            @Override
//            public void stop(View v) {
//
//            }
//        };

        return null;
    }

    /**
     * 自定义授权弹窗view
     * 调用环境：子进程
     *
     * @param context
     * @param authInfo
     * @param authView
     * @return true:自定义授权view;false:使用内置
     */
    @Override
    public boolean authView(Context context, MiniAuthInfo authInfo, IAuthView authView) {
        boolean isCustom = false;
        if (isCustom) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.mini_auth_view, null);
            //必须设置
            inflate.findViewById(R.id.mini_auth_btn_refuse).setOnClickListener(authInfo.refuseListener);
            //必须设置
            inflate.findViewById(R.id.mini_auth_btn_grant).setOnClickListener(authInfo.grantListener);

            //返回自定义View
            authView.getView(inflate);
        }

        return isCustom;
    }
}
