package com.yk.notification.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yk.notification.service.MyService;

/**
 * Created by Silence on 2017/4/1.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MyService.class);
        context.startService(intent1);


    }
}
