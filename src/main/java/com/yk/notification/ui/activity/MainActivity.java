package com.yk.notification.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yk.notification.R;
import com.yk.notification.base.BaseActivity;
import com.yk.notification.inter.PermissionResultListener;
import com.yk.notification.model.GetGpsBean;
import com.yk.notification.service.MyService;
import com.yk.notification.thread.GetGpsThread;
import com.yk.notification.util.Constants;
import com.yk.notification.util.MessageBox;
import com.yk.notification.util.ToastUtils;
import com.yk.notification.util.Utils;

public class MainActivity extends BaseActivity {

    private static final int PER_REQUEST_CODE = 0x01;
    TextView mTxtTitle;
    Button mBtnNotification;
    Button mBtnWeather;


    /**
     * 绑定布局
     *
     * @return
     */
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        mTxtTitle = $(R.id.txt_title);
        mBtnNotification = $(R.id.btn_notification);
        mBtnWeather = $(R.id.btn_weather);

    }

    /**
     * 绑定点击事件
     *
     * @param view
     */
    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_notification: {//通知
                Intent intent = new Intent(this, MyService.class);
                if (!Utils.isServiceRunning(MainActivity.this, "com.yk.notification.service.MyService")) {
                    startService(intent);
                }

                // startGpsThread();
            }
            break;
            case R.id.btn_weather: {//天气
                //startActivity(new Intent(MainActivity.this,UserActivity.class));
                // 在这里模拟异常抛出情况，人为抛出一个运行时异常

                throw new RuntimeException("自定义异常：这是自己抛出的异常");
            }
        }

    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        mBtnNotification.setOnClickListener(this);
        mBtnWeather.setOnClickListener(this);

    }

    @Override
    public void initData() {
        initPermission();
    }


    //权限
    public void initPermission() {
        performRequestPermissions(getString(R.string.permission_desc),
                new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE}
                , PER_REQUEST_CODE, new PermissionResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        ToastUtils.showMessage(MainActivity.this, "已申请权限");


                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showMessage(MainActivity.this, "拒绝申请权限");
                    }
                });

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
                    mTxtTitle.setText(getGpsBean.getAddress());
                }
                break;

                case MessageBox.MESSAGE_LOCATION_ERROR: {//定位失败
                    ToastUtils.showMessage(getApplicationContext(), "定位失败");
                }
                break;
            }

        }
    };

}
