package com.yk.notification.inter;

/**
 * Created by Silence on 2017/3/15.
 * 运行时权限接口
 */

public interface PermissionResultListener {

    void onPermissionGranted();

    void onPermissionDenied();
}
