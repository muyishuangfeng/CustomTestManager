package com.yk.notification.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.yk.notification.R;
import com.yk.notification.model.GetGpsBean;
import com.yk.notification.thread.GetGpsThread;
import com.yk.notification.ui.activity.NotificationActivity;
import com.yk.notification.ui.widget.NotificationUtil;
import com.yk.notification.util.BitmapUtil;
import com.yk.notification.util.Constants;
import com.yk.notification.util.MessageBox;
import com.yk.notification.util.TimeUtils;
import com.yk.notification.util.ToastUtils;

/**
 * Created by Silence on 2017/4/1.
 */

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startGpsThread();
        //定时管理
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int time = 30 * 1000;
//        long triggerTime = SystemClock.elapsedRealtime() + time;
        // Intent intent1 = new Intent(this, MyBroadCastReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent1, 0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开始线程
     */
    public void startGpsThread() {
        GetGpsThread mThread = new GetGpsThread(this, mHandler, Constants.INTERVAL_TIME,
                Constants.CONNECTION_TIME);
        mThread.start();
    }

    /**
     * Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageBox.MESSAGE_LOCATION_SU: {//定位成功
                    GetGpsBean getGpsBean = (GetGpsBean) msg.obj;
                    //showNotification(MyService.this,getGpsBean.getAddress());
                    Log.e("定位",getGpsBean.getAddress());
                    NotificationUtil.sendCustomerNotification(
                            MyService.this,"定位",18,
                            getGpsBean.getAddress(),
                            14, TimeUtils.getCurrentTime(System.currentTimeMillis()), 14,
                            BitmapUtil.getRoundBitmap(BitmapFactory.decodeResource(MyService.this.getResources(),
                                    R.drawable.ic_photo1)),
                            BitmapFactory.decodeResource(MyService.this.getResources(),
                                    R.drawable.icon_photo0),
                            "text",getGpsBean.getAddress(),"你有一条短消息",NotificationActivity.class);
                }
                break;
                case MessageBox.MESSAGE_LOCATION_ERROR: {//定位失败
                    ToastUtils.showMessage(getApplicationContext(), "定位失败");
                }
                break;
            }


        }
    };

    /**
     * 显示通知
     */
    public void showNotification(Context context,String text) {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuild = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ic_launcher))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("定位提示")
                .setContentText(text)
                .setOnlyAlertOnce(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        //自定义打开的界面
        Intent resultIntent = new Intent(context, NotificationActivity.class);
        resultIntent.putExtra("text",text);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                resultIntent, 0);
        mBuild.setContentIntent(contentIntent);
        manager.notify(1, mBuild.build());

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        GetGpsThread.stopLocation();
        GetGpsThread.destroyLocation();


    }
}