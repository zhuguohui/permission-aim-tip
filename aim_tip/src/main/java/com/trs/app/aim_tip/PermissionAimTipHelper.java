package com.trs.app.aim_tip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import com.trs.app.aim_tip.dialog.AimTipShowController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 10:14
 * Desc:用于提示申请权限的目的的。
 * 在申请权限的时候自动提示。
 */
public class PermissionAimTipHelper implements ActivityCompat.PermissionCompatDelegate {
    static PermissionAimTipHelper instance;
    /**
     * 这个字段是FragmentActivity中所有的。如果fragment发起权限申请会被置为true。
     * 但是在Delegate的requestPermissions 方法执行后会必然被置为false。
     * 因此无法正确的回调到fragment的请求中。需要手动设置为true。
     */
    private Field fromFragmentField;
    private AimTipShowController showController;
    private static boolean callInitMethod=false;

    private PermissionAimTipHelper(AimTipShowController showController) {
        this.showController = showController;
        try {
            fromFragmentField = FragmentActivity.class.getDeclaredField("mRequestedPermissionsFromFragment");
            fromFragmentField.setAccessible(true);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static synchronized PermissionAimTipHelper getInstance() {
        if(!callInitMethod){
            throw new IllegalStateException("请先调用init方法进行初始化");
        }
        return instance;
    }

    public void setShowController(AimTipShowController showController) {
        this.showController = showController;
    }

    /**
     * 入口函数
     * @param showController
     */
    public static synchronized void init(AimTipShowController showController) {
        if (instance != null) {
            return;
        }
        callInitMethod=true;
        instance = new PermissionAimTipHelper(showController);
        ActivityCompat.setPermissionCompatDelegate(instance);
    }

    @Override
    public boolean requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
        boolean fromFragment = false;
        FragmentActivity fragmentActivity = null;
        if (activity instanceof FragmentActivity && fromFragmentField != null) {
            fragmentActivity = (FragmentActivity) activity;
            //检查是否是来自fragment的请求
            try {
                fromFragment = (boolean) fromFragmentField.get(fragmentActivity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        boolean finalFromFragment = fromFragment;
        FragmentActivity finalFragmentActivity = fragmentActivity;
        //过滤已经获取的权限，避免重复提示。
        String[] needPermissions=getNeedPermissions(activity,permissions);
        if(needPermissions.length==0) {
            //已经授予全部权限，不需要拦截弹出提示框。
            return false;
        }
        showController.showTipDialog(activity, needPermissions, requestCode, () -> {
            if (finalFromFragment) {
                //将字段重置
                try {
                    fromFragmentField.set(finalFragmentActivity, true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            requestPermissionsDefaultImpl(activity, permissions, requestCode);
        });

        return true;
    }

    private String[] getNeedPermissions(Activity activity, String[] permissions) {
        PackageManager packageManager = activity.getPackageManager();
        String packageName = activity.getPackageName();
        List<String> permissionList=new ArrayList<>();
        for (String permission : permissions) {
            if (checkNeedPermission(permission, packageManager, packageName)) {
                permissionList.add(permission);
            }
        }
        return permissionList.toArray(new String[]{});
    }

    private boolean checkNeedPermission(String string,PackageManager packageManager,String PackageName) {
        int state = packageManager.checkPermission(string, PackageName);
        if (PackageManager.PERMISSION_GRANTED ==state){
            //已经授予获取已经拒绝就不需要重复获取
            return false;
        }
        return true;
    }
    @Override
    public boolean onActivityResult(@NonNull Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        return false;
    }


    /**
     * 从ActivityCompat.requestPermissions()方法，copy过来的。
     * 也就是默认的实现
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    @SuppressLint("RestrictedApi")
    private void requestPermissionsDefaultImpl(final @NonNull Activity activity,
                                               final @NonNull String[] permissions, final @IntRange(from = 0) int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof ActivityCompat.RequestPermissionsRequestCodeValidator) {
                ((ActivityCompat.RequestPermissionsRequestCodeValidator) activity)
                        .validateRequestPermissionsRequestCode(requestCode);
            }
            activity.requestPermissions(permissions, requestCode);
        } else if (activity instanceof ActivityCompat.OnRequestPermissionsResultCallback) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];

                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();

                    final int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(
                                permissions[i], packageName);
                    }

                    ((ActivityCompat.OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(
                            requestCode, permissions, grantResults);
                }
            });
        }
    }

}
