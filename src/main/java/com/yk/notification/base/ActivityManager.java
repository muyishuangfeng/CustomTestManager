package com.yk.notification.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2017/3/31.
 */

public class ActivityManager {
    //Activity集合
    public static  List<Activity>mActivityList= new ArrayList<>();

    /**
     * 添加Activity
     * @param activity
     */
    public static void addActivity(Activity activity){
       mActivityList.add(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public static void removeActivity(Activity activity){
        mActivityList.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public static void finishAll(){
        for (Activity activity:mActivityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
