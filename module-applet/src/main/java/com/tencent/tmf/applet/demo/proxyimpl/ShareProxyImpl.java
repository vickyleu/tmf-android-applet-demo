package com.tencent.tmf.applet.demo.proxyimpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.qqmini.sdk.annotation.ProxyService;
import com.tencent.qqmini.sdk.launcher.AppLoaderFactory;
import com.tencent.qqmini.sdk.launcher.core.IMiniAppContext;
import com.tencent.qqmini.sdk.launcher.core.proxy.ShareProxy;
import com.tencent.qqmini.sdk.launcher.model.ShareData;
import com.tencent.qqmini.sdk.launcher.ui.MoreItem;
import com.tencent.qqmini.sdk.ui.MorePanel;
import com.tencent.qqmini.sdk.widget.MiniToast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

@ProxyService(proxy = ShareProxy.class)
public class ShareProxyImpl implements ShareProxy {

    private static final String TAG = "ShareProxyImpl";

    private static final String OPEN_APP_ID = "101794394";

    // this init is so slow...
    private static volatile Tencent tencent;

    private static Tencent getTencent() {
        if (tencent == null) {
            synchronized (ShareProxyImpl.class) {
                if (tencent == null) {
                    tencent = Tencent
                            .createInstance(OPEN_APP_ID, AppLoaderFactory.g().getContext());
                }
            }
        }
        return tencent;
    }

    // 扩展按钮的ID需要设置为[100, 200]这个区间中的值，否则，添加无效。
    public static final int OTHER_MORE_ITEM_1 = 101;
    public static final int OTHER_MORE_ITEM_2 = 102;
    public static final int OTHER_MORE_ITEM_INVALID = 201;

    private IUiListener mQQShareUiListener;

    /**
     * 获取默认的分享目标，即默认的宿主分享渠道。
     * 默认分享目标的ID需要设置为[100, 200]这个区间中的值，否则，添加无效。
     */
    @Override
    public int getDefaultShareTarget() {
        return OTHER_MORE_ITEM_1;
    }

    @Override
    public boolean isShareTargetAvailable(Context context, int shareTarget) {
        if (shareTarget == ShareData.ShareTarget.WECHAT_MOMENTS ||
                shareTarget == ShareData.ShareTarget.WECHAT_FRIEND) {
            // Demo没有集成微信分享
            return false;
        }

        return true;
    }

    /**
     * 分享
     *
     * @param shareData 分享数据
     */
    @Override
    public void share(Activity activity, ShareData shareData) {
        switch (shareData.shareTarget) {
            case ShareData.ShareTarget.QQ:
            case ShareData.ShareTarget.SHARE_CHAT:
            case ShareData.ShareTarget.QQ_DIRECTLY:
            case ShareData.ShareTarget.FRIEND_LIST:
                shareToQQ(activity, shareData);
                break;

            case ShareData.ShareTarget.QZONE:
                shareToQZone(activity, shareData);
                break;

            case ShareData.ShareTarget.WECHAT_FRIEND:
                shareToWxSession(activity, shareData);
                break;

            case ShareData.ShareTarget.WECHAT_MOMENTS:
                shareToWxTimeline(activity, shareData);
                break;

            case ShareData.ShareTarget.UPDATABLE_MSG:
                shareUpdatableMsg(shareData);
                break;

            default:
                break;
        }

        if (MoreItem.isValidExtendedItemId(shareData.shareTarget)) {
            shareToOther(activity, shareData);
        }
    }

    @Override
    public void sharePic(Activity activity, ShareData shareData) {

    }

    /**
     * 启动第三方分享结果的返回
     */
    @Override
    public void onShareActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mQQShareUiListener);
    }

    /**
     * 调用互联SDK，分享到QQ
     */
    public void shareToQQ(Activity activity, ShareData shareData) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareData.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareData.summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareData.targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareData.sharePicPath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "MiniSDKDemo");

        mQQShareUiListener = new QQShareListener(activity, shareData);
        getTencent().shareToQQ(activity, params, mQQShareUiListener);
    }

    /**
     * 调用互联SDK，分享到QQ空间
     */
    public void shareToQZone(Activity activity, ShareData shareData) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareData.title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareData.summary);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareData.targetUrl);//必填
        ArrayList imageUrlList = new ArrayList();
        imageUrlList.add(shareData.sharePicPath);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrlList);

        mQQShareUiListener = new QQShareListener(activity, shareData);
        getTencent().shareToQzone(activity, params, mQQShareUiListener);
    }

    /**
     * 调用微信SDK，分享到微信好友
     */
    public void shareToWxSession(Activity activity, ShareData shareData) {

    }

    /**
     * 调用微信SDK，分享到微信朋友圈
     */
    public void shareToWxTimeline(Activity activity, ShareData shareData) {

    }

    /**
     * 发送ark动态消息
     */
    private void shareUpdatableMsg(ShareData shareData) {

    }

    /**
     * 调用第三方分享
     */
    public void shareToOther(Activity activity, ShareData shareData) {
        switch (shareData.shareItemId) {
            case OTHER_MORE_ITEM_1:
                if (shareData.shareInMiniProcess) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MiniToast.makeText(activity, "thrirdParty done", Toast.LENGTH_LONG).show();
                        }
                    });
                    shareToOtherItem1(activity, shareData);
                }
                break;
            case OTHER_MORE_ITEM_2:
            default:
                MiniToast.makeText(activity, "wait thirdparty do it", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void shareToOtherItem1(Activity activity, ShareData shareData) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareData.title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareData.summary);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareData.targetUrl);//必填
        ArrayList imageUrlList = new ArrayList();
        imageUrlList.add(shareData.sharePicPath);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrlList);

        mQQShareUiListener = new QQShareListener(activity, shareData);
        getTencent().shareToQzone(activity, params, mQQShareUiListener);
    }

    private class QQShareListener implements IUiListener {

        private Context mContext;
        private ShareData mShareData;

        QQShareListener(Context context, ShareData shareData) {
            mContext = context;
            mShareData = shareData;
        }

        @Override
        public void onComplete(Object o) {
            mShareData.notifyShareResult(mContext, ShareData.ShareResult.SUCCESS);
        }

        @Override
        public void onError(UiError uiError) {
            mShareData.notifyShareResult(mContext, ShareData.ShareResult.FAIL);
        }

        @Override
        public void onCancel() {
            mShareData.notifyShareResult(mContext, ShareData.ShareResult.CANCEL);
        }
    }

    @Override
    public void onJsShareAppMessage(Object shareData) {

    }

    @Override
    public void onJsShareAppPictureMessage(Object shareData) {

    }

    @Override
    public void showSharePanel(IMiniAppContext miniAppContext) {
        MorePanel.show(miniAppContext);
    }
}
