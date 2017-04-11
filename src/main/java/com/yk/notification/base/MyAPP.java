package com.yk.notification.base;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

import com.yk.notification.broad.MyBroadCastReceiver;

/**
 * Created by Silence on 2017/4/1.
 */

public class MyAPP extends Application {

    public  static MyAPP sInstance=null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        CrashHandler crashHandler= CrashHandler.getInstance();
        crashHandler.init(this);

        // 这个广播动作是以每分钟一次的形式发送
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        MyBroadCastReceiver receiver = new MyBroadCastReceiver();
        registerReceiver(receiver, filter);
    }

    public static MyAPP getInstance() {
        return sInstance;
    }

}
