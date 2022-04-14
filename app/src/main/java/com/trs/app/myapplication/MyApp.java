package com.trs.app.myapplication;

import android.app.Application;

import com.trs.app.aim_tip.PermissionAimTipDelegate;
import com.trs.app.aim_tip.impl.TRSTipShowController;
import com.trs.app.aim_tip.impl.adatper.RawAimTipAdapter;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 15:33
 * Desc:
 */
public class MyApp  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PermissionAimTipDelegate.initPermissionTip(new TRSTipShowController
                (new RawAimTipAdapter(this, R.raw.permission_aim_description)));
    }
}
