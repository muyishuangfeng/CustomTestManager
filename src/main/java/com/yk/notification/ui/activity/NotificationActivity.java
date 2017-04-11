package com.yk.notification.ui.activity;


import android.app.NotificationManager;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yk.notification.R;
import com.yk.notification.base.BaseActivity;

/**
 * Created by Silence on 2017/4/5.
 */

public class NotificationActivity extends BaseActivity {

    TextView mTxtNotification;
    Intent mIntent;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_notification);
    }

    @Override
    public void initView() {
        mTxtNotification = $(R.id.txt_notification);
    }

    @Override
    public void initData() {
        mIntent=getIntent();
        if (!TextUtils.isEmpty(mIntent.getStringExtra("text"))){
            mTxtNotification.setText(mIntent.getStringExtra("text").toString());
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }

    @Override
    public void initClick(View view) {

    }

    @Override
    public void setListener() {

    }
}
