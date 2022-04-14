package com.trs.app.aim_tip.dialog;

import android.app.Activity;

import androidx.annotation.NonNull;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 13:44
 * Desc:用来控制dialog的显示
 */
public interface AimTipShowController {
    void showTipDialog(@NonNull Activity activity, @NonNull String[] permissions, int requestCode,ShowAction showAction);
}
