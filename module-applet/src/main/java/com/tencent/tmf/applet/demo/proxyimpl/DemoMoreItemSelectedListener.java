package com.tencent.tmf.applet.demo.proxyimpl;

import android.app.Activity;

import com.tencent.qqmini.sdk.launcher.core.IMiniAppContext;
import com.tencent.qqmini.sdk.launcher.log.QMLog;
import com.tencent.qqmini.sdk.ui.DefaultMoreItemSelectedListener;

public class DemoMoreItemSelectedListener extends DefaultMoreItemSelectedListener {
    public static final int CLOSE_MINI_APP = 150;

    @Override
    public void onMoreItemSelected(IMiniAppContext miniAppContext, int moreItemId) {
        switch (moreItemId) {
            case CLOSE_MINI_APP:
                close(miniAppContext);
                return;
        }

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
