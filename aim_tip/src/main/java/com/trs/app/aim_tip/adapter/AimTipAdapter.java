package com.trs.app.aim_tip.adapter;

import android.app.Activity;

import com.trs.app.aim_tip.bean.AimData;


/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 13:50
 * Desc:用于将特定权限转换为说明的适配器
 */
public interface AimTipAdapter {
    /**
     *
     * @param permission
     * @param activity
     * @return 返回空表示不支持该项说明。
     */
    AimData getDescriptionForPermission(String permission, Activity activity);
}
