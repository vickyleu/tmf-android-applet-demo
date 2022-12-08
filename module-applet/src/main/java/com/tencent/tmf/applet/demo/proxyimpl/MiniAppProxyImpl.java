package com.tencent.tmf.applet.demo.proxyimpl;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.tencent.qqmini.sdk.annotation.ProxyService;
import com.tencent.qqmini.sdk.launcher.core.IMiniAppContext;
import com.tencent.qqmini.sdk.launcher.core.proxy.BaseMiniAppProxyImpl;
import com.tencent.qqmini.sdk.launcher.core.proxy.MiniAppProxy;
import com.tencent.qqmini.sdk.launcher.ui.MoreItem;
import com.tencent.qqmini.sdk.launcher.ui.MoreItemList;
import com.tencent.qqmini.sdk.launcher.ui.OnMoreItemSelectedListener;
import com.tencent.tmf.applet.demo.R;
import com.tencent.tmf.applet.demo.utils.UniversalDrawable;
import com.tencent.tmf.base.api.TMFBase;

import java.util.ArrayList;
import java.util.List;

@ProxyService(proxy = MiniAppProxy.class)
public class MiniAppProxyImpl extends BaseMiniAppProxyImpl {

    private static final String TAG = "MiniAppProxyImpl";

    /**
     * 接入方APP的版本信息
     */
    @Override
    public String getAppVersion() {
        return "8.2.0";
    }

    /**
     * 接入方APP的名称
     */
    @Override
    public String getAppName() {
        return "miniApp";
    }

    /**
     * 接入方APP是否是debug版本
     */
    @Override
    public boolean isDebugVersion() {
        return true;
    }

    /**
     * 获取Drawable对象
     *
     * @param context         上下文
     * @param source          来源，可以是本地或者网络
     * @param width           图片宽度
     * @param hight           图片高度
     * @param defaultDrawable 默认图片,用于加载中和加载失败
     */
    @Override
    public Drawable getDrawable(Context context, String source, int width, int hight, Drawable defaultDrawable) {
        //接入方接入自己的ImageLoader
        //demo里使用开源的universalimageloader
        UniversalDrawable drawable = new UniversalDrawable();
        if (TextUtils.isEmpty(source)) {
            return drawable;
        }
//        if (!PathUtil.isNetworkUrl(source)) {
//            source = Uri.fromFile(new File(source)).toString();
//        }
        drawable.loadImage(context, source);
        return drawable;
    }

    @Override
    public Drawable getDrawable(Context context, String source, int width, int hight, Drawable defaultDrawable,
                                boolean useApng) {
        return getDrawable(context, source, width, hight, defaultDrawable);
    }

    /**
     * 打开选图界面
     *
     * @param context        当前Activity
     * @param maxSelectedNum 允许选择的最大数量
     * @param listner        回调接口
     * @return 不支持该接口，请返回false
     */
    @Override
    public boolean openChoosePhotoActivity(Context context, int maxSelectedNum, IChoosePhotoListner listner) {
        return false;
    }

    /**
     * 打开图片预览界面
     *
     * @param context       当前Activity
     * @param selectedIndex 当前选择的图片索引
     * @param pathList      图片路径列表
     * @return 不支持该接口，请返回false
     */
    @Override
    public boolean openImagePreview(Context context, int selectedIndex, List<String> pathList) {
        return false;
    }


    /**
     * 点击胶囊按钮的更多选项
     *
     * @param miniAppContext 小程序运行环境
     * @return 不支持该接口，请返回false
     */
    @Override
    public boolean onCapsuleButtonMoreClick(IMiniAppContext miniAppContext) {
        return false;
    }

    /**
     * 点击胶囊按钮的关闭选项
     *
     * @param miniAppContext         小程序运行环境(小程序进程，非主进程)
     * @param onCloseClickedListener 点击小程序关闭时回调
     * @return 不支持该接口，请返回false
     */
    @Override
    public boolean onCapsuleButtonCloseClick(IMiniAppContext miniAppContext,
                                             DialogInterface.OnClickListener onCloseClickedListener) {
        return false;
    }

    /**
     * 返回胶囊更多面板的按钮，扩展按钮的ID需要设置为[100, 200]这个区间中的值，否则，添加无效
     */
    @Override
    public ArrayList<MoreItem> getMoreItems(MoreItemList.Builder builder) {
        MoreItem item1 = new MoreItem();
        item1.id = ShareProxyImpl.OTHER_MORE_ITEM_1;
//        item1.text = "其他1";
        item1.text = getString(R.string.applet_mini_proxy_impl_other1);
        item1.shareInMiniProcess = true;
        item1.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item2 = new MoreItem();
        item2.id = ShareProxyImpl.OTHER_MORE_ITEM_2;
        item2.text = getString(R.string.applet_mini_proxy_impl_other2);
        item2.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item3 = new MoreItem();
        item3.id = DemoMoreItemSelectedListener.CLOSE_MINI_APP;
        item3.text = getString(R.string.applet_mini_proxy_impl_float_app);
        item3.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item4 = new MoreItem();
        item4.id = ShareProxyImpl.OTHER_MORE_ITEM_INVALID;
        item4.text = getString(R.string.applet_mini_proxy_impl_out_of_effect);
        item4.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        // 自行调整顺序。
        builder.addMoreItem(item1)
                .addMoreItem(item2)
                .addShareQQ("QQ", com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_qq)
                .addMoreItem(item3)
                .addShareQzone(getString(R.string.applet_mini_proxy_impl_Qzone),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_qzone)
                .addShareWxFriends(getString(R.string.applet_mini_proxy_impl_wechat_friend),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_wx_friend)
                .addShareWxMoments(getString(R.string.applet_mini_proxy_impl_wechat_group),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_wx_moment)
                .addRestart(getString(R.string.applet_mini_proxy_impl_restart),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_restart_miniapp)
                .addAbout(getString(R.string.applet_mini_proxy_impl_about),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addDebug(getString(R.string.applet_debug_info), com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addMonitor(getString(R.string.applet_mini_proxy_impl_performance),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addComplaint(getString(R.string.applet_mini_proxy_impl_complain_and_report),
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_browser_report);

        return builder.build();
    }

    private String getString(int id) {
        return TMFBase.getContext().getString(id);
    }

    /**
     * 返回胶囊更多面板按钮点击监听器
     *
     * @return
     */
    @Override
    public OnMoreItemSelectedListener getMoreItemSelectedListener() {
        return new DemoMoreItemSelectedListener();
    }
}


