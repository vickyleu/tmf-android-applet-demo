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
import com.tencent.tmf.applet.demo.utils.UniversalDrawable;

import java.util.ArrayList;
import java.util.List;

@ProxyService(proxy = MiniAppProxy.class)
public class MiniAppProxyImpl extends BaseMiniAppProxyImpl {
    private static final String TAG = "MiniAppProxyImpl";

    /**
     * 用户账号，uin或者openid
     */
    @Override
    public String getAccount() {
        return "123456";
    }

    /**
     * 用户昵称
     */
    @Override
    public String getNickName() {
        return "nickname";
    }

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
        return "demo";
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
    public Drawable getDrawable(Context context, String source, int width, int hight, Drawable defaultDrawable, boolean useApng) {
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
        item1.text = "其他1";
        item1.shareInMiniProcess = true;
        item1.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item2 = new MoreItem();
        item2.id = ShareProxyImpl.OTHER_MORE_ITEM_2;
        item2.text = "其他2";
        item2.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item3 = new MoreItem();
        item3.id = DemoMoreItemSelectedListener.CLOSE_MINI_APP;
        item3.text = "浮窗";
        item3.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        MoreItem item4 = new MoreItem();
        item4.id = ShareProxyImpl.OTHER_MORE_ITEM_INVALID;
        item4.text = "无效，id不属于区间[100,200]，不会显示在面板上";
        item4.drawable = com.tencent.qqmini.sdk.R.drawable.mini_sdk_about;

        // 自行调整顺序。不支持删除关于，举报。
        builder.addMoreItem(item1)
                .addMoreItem(item2)
                .addFavorite(builder.isMyFavorite() ? "取消置顶" : "置顶",
                        com.tencent.qqmini.sdk.R.drawable.mini_sdk_favorite)
                .addShareQQ("QQ", com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_qq)
                .addMoreItem(item3)
                .addShortcut("添加到桌面", com.tencent.qqmini.sdk.R.drawable.mini_sdk_shortcut)
                .addShareQzone("QQ空间", com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_qzone)
                .addShareWxFriends("微信好友", com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_wx_friend)
                .addShareWxMoments("微信朋友圈", com.tencent.qqmini.sdk.R.drawable.mini_sdk_channel_wx_moment)
                .addRestart("重启小程序", com.tencent.qqmini.sdk.R.drawable.mini_sdk_restart_miniapp)
                .addAbout("关于", com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addComplaint("举报", com.tencent.qqmini.sdk.R.drawable.mini_sdk_browser_report)
                .addDebug("调试", com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addExportLog("导出日志", com.tencent.qqmini.sdk.R.drawable.mini_sdk_about)
                .addMonitor("性能", com.tencent.qqmini.sdk.R.drawable.mini_sdk_about);

        return builder.build();
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


