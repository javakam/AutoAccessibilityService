package com.ando.autoservice;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

/**
 * 自动点击
 *
 * @author ChangBao
 * @date 2020-04-27 15:02:28
 */
public class AutoClickService extends AccessibilityService {
    private static final String TAG = "123";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        ztLog("===start search===");
        try {
            AccessibilityNodeInfo rootInfo = getRootInActiveWindow();
            if (rootInfo != null) {
                DFS(rootInfo);
            }
        } catch (Exception e) {
            ztLog("Exception:" + e.getMessage(), true);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ztLog("开启服务成功", true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ztLog("关闭服务成功", true);
    }

    @Override
    public void onInterrupt() {
    }

    private static final String NOTIFY_VIEW = "android.widget.TextView";
    private static final String NOTIFY_TEXT = "动态";

    /**
     * 深度优先遍历寻找目标节点
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void DFS(AccessibilityNodeInfo rootInfo) {
        if (rootInfo == null || TextUtils.isEmpty(rootInfo.getClassName())) {
            return;
        }

        if (!TextUtils.equals(rootInfo.getClassName(), NOTIFY_VIEW)) {
            ztLog(rootInfo.getClassName().toString());
            for (int i = 0; i < rootInfo.getChildCount(); i++) {
                DFS(rootInfo.getChild(i));
            }
        } else {
            ztLog("==find==");
            final AccessibilityNodeInfo viewInfo = rootInfo;
            if (!TextUtils.isEmpty(viewInfo.getText()) && viewInfo.getText().toString().equals(NOTIFY_TEXT)) {
                AccessibilityNodeInfo parent = viewInfo.getParent();
                Log.w(TAG, "==text111 == " + viewInfo.getText().toString() + "  " + viewInfo.getViewIdResourceName() + "  " + parent.getViewIdResourceName());
                if (parent.getViewIdResourceName() == null) {
                    performClick(parent);
                    return;
                }
            }

            for (int i = 0; i < viewInfo.getChildCount(); i++) {
                final AccessibilityNodeInfo frameLayoutInfo = viewInfo.getChild(i);
                if (!TextUtils.isEmpty(frameLayoutInfo.getText()) && frameLayoutInfo.getText().toString().equals(NOTIFY_TEXT)) {
                    Log.w(TAG, "==text222 ==");
                    performClick(frameLayoutInfo);
                    return;
                }

                final AccessibilityNodeInfo childInfo = frameLayoutInfo.getChild(0);
                Log.w(TAG, "==text333 ==");

                String text = childInfo.getText().toString();
                if (text.equals(NOTIFY_TEXT)) {
                    Log.w(TAG, "==result ==");
                    performClick(frameLayoutInfo);
                } else {
                    ztLog(text);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void performClick(AccessibilityNodeInfo targetInfo) {
        targetInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private void ztLog(String str) {
        ztLog(str, false);
    }

    private void ztLog(String str, boolean showToast) {
        Log.i(TAG, str);
        if (showToast) {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }

}
