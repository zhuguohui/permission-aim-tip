package com.trs.app.aim_tip.impl;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;


import com.trs.app.aim_tip.adapter.AimTipAdapter;
import com.trs.app.aim_tip.bean.AimData;
import com.trs.app.aim_tip.dialog.AimTipShowController;
import com.trs.app.aim_tip.dialog.ShowAction;
import com.trs.app.aim_tip.impl.view.DialogStyleData;
import com.trs.app.aim_tip.impl.view.PermissionsAimDescribeDialog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 13:48
 * Desc:
 */
public class TRSTipShowController implements AimTipShowController {

    AimTipAdapter adapter;
    DialogStyleData dialogStyleData;
    public TRSTipShowController(AimTipAdapter adapter) {
        this(adapter,new DialogStyleData());
    }

    public TRSTipShowController(AimTipAdapter adapter, DialogStyleData dialogStyleData) {
        this.adapter = adapter;
        this.dialogStyleData = dialogStyleData;
    }

    @Override
    public void showTipDialog(@NonNull Activity activity, @NonNull String[] permissions, int requestCode, ShowAction showAction) {
        FragmentActivity fragmentActivity = null;
        if (activity instanceof FragmentActivity) {
            fragmentActivity = (FragmentActivity) activity;
        }
        if (fragmentActivity != null) {
            List<AimData> aims = getAims(activity, permissions);
            if (!aims.isEmpty()) {
                PermissionsAimDescribeDialog dialog = new PermissionsAimDescribeDialog(activity, aims,dialogStyleData);
                dialog.setCancelable(false);
                dialog.setOnDismissLister(showAction::onShowComplete);
                dialog.show(fragmentActivity.getSupportFragmentManager(), "top");
                return;
            }
        }
        showAction.onShowComplete();
    }

    private List<AimData> getAims(Activity activity, String[] permissions) {
        //此处使用LinkedHashSet 是因为LinkedHashSet 有顺序。这样和请求权限的顺序更好的对应
        //同时也过滤了重复的权限说明
        Set set = new LinkedHashSet();
        for (int i = 0; i < permissions.length; i++) {
            AimData aimData = adapter.getDescriptionForPermission(permissions[i], activity);
            if (aimData != null) {
                set.add(aimData);
            }

        }
        return new ArrayList<>(set);
    }
}
