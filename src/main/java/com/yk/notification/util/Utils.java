package com.yk.notification.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Silence on 2017/4/5.
 */

public class Utils {
    /**
     * 判断服务是否正在运行
     *
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
