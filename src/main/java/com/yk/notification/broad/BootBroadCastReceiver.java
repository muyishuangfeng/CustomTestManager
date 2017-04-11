package com.yk.notification.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yk.notification.service.MyService;

/**
 * Created by Silence on 2017/4/1.
 */

public class BootBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机自启动
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent intent1= new Intent(context, MyService.class);
            context.startService(intent1);
        }
    }
}
