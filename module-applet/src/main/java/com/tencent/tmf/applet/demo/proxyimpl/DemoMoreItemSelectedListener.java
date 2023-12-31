package com.tencent.tmf.applet.demo.proxyimpl;

import static com.tencent.tmf.applet.demo.proxyimpl.ShareProxyImpl.OTHER_MORE_ITEM_1;

import android.app.Activity;
import android.widget.Toast;

import com.tencent.tmfmini.sdk.launcher.core.IMiniAppContext;
import com.tencent.tmfmini.sdk.launcher.log.QMLog;
import com.tencent.tmfmini.sdk.ui.DefaultMoreItemSelectedListener;

public class DemoMoreItemSelectedListener extends DefaultMoreItemSelectedListener {
    public static final int CLOSE_MINI_APP = 150;

    @Override
    public void onMoreItemSelected(IMiniAppContext miniAppContext, int moreItemId) {
        //处理开发者自定义点击事件(自定义分享事件除外)
        switch (moreItemId) {
            case CLOSE_MINI_APP:
                close(miniAppContext);
                return;
            case OTHER_MORE_ITEM_1:
                miniAppContext.getAttachedActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(miniAppContext.getAttachedActivity(), "custom menu click", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
        }

        //处理内置分享和开发者自定义分享，例如：微博、twitter等
        super.onMoreItemSelected(miniAppContext, moreItemId);
    }

    public void close(IMiniAppContext miniAppContext) {
        Activity activity = miniAppContext.getAttachedActivity();
        if (activity != null && !activity.isFinishing()) {
            boolean moved = activity.moveTaskToBack(true);
            if (!moved) {
                QMLog.e("Demo", "moveTaskToBack failed, finish the activity.");
                activity.finish();
            }
        }
    }
}
