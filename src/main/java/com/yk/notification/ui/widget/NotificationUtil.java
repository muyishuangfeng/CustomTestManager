package com.yk.notification.ui.widget;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.yk.notification.R;

/**
 * Created by Silence on 2017/4/5.
 */

public class NotificationUtil {
    private static NotificationCompat.Builder mBuilder;


    /**
     * 自定义通知
     *
     * @param context      上下文
     * @param title        标题
     * @param content      内容
     * @param largeIcon    大图标
     * @param smallIcon    小图标
     * @param key          传递的key
     * @param value        传递的值
     */
    public static void sendCustomerNotification(Context context, String title,
                                                float titleSize, String content,
                                                float contentSize, String time,
                                                float timeSize, Bitmap largeIcon, Bitmap smallIcon,
                                                String key, String value, String msg, Class cl) {
        NotificationManager manager = (NotificationManager)
               context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        //设置小图标
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);
        //标题
        remoteViews.setTextViewText(R.id.txt_notify_title, title);
        remoteViews.setTextViewTextSize(R.id.txt_notify_title, TypedValue.COMPLEX_UNIT_DIP,
                titleSize);
        //内容
        remoteViews.setTextViewText(R.id.txt_notify_content, content);
        remoteViews.setTextViewTextSize(R.id.txt_notify_content, TypedValue.COMPLEX_UNIT_DIP,
                contentSize);
        //时间
        remoteViews.setTextViewText(R.id.txt_notify_end,time);
        remoteViews.setTextViewTextSize(R.id.txt_notify_end,TypedValue.COMPLEX_UNIT_DIP,timeSize);
        //大图标
        remoteViews.setImageViewBitmap(R.id.img_lardge_icon, largeIcon);
        //小图标
        remoteViews.setImageViewBitmap(R.id.img_small_icon, smallIcon);
        mBuilder.setContent(remoteViews);
        mBuilder.setTicker(msg);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        Intent intent = new Intent(context, cl);
        intent.putExtra(key, value);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        manager.notify(1,mBuilder.build());

    }
}
