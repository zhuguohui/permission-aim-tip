package com.trs.app.myapplication;

import android.app.Application;

import com.trs.app.aim_tip.PermissionAimTipHelper;
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
        PermissionAimTipHelper.init(new TRSTipShowController
                (new RawAimTipAdapter(this, R.raw.permission_aim_description)));
    }
}
