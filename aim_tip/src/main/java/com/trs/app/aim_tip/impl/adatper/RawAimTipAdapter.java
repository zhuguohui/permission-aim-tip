package com.trs.app.aim_tip.impl.adatper;

import android.app.Activity;
import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trs.app.aim_tip.adapter.AimTipAdapter;
import com.trs.app.aim_tip.bean.AimData;
import com.trs.app.aim_tip.bean.AimDescription;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 14:03
 * Desc:从raw目录下解析配置文件的adapter
 */
public class RawAimTipAdapter implements AimTipAdapter {
    int rawId;
    List<AimDescription> aimDescriptionList;

    public RawAimTipAdapter(Context context, int rawId) {
        this.rawId = rawId;
        InputStream inputStream = context.getResources().openRawResource(rawId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer)) >= 0) {
                baos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = baos.toString();

        aimDescriptionList = new Gson().fromJson(s, new TypeToken<List<AimDescription>>() {
        }.getType());
        if (aimDescriptionList == null) {
            aimDescriptionList = Collections.emptyList();
        }
    }

    @Override
    public AimData getDescriptionForPermission(String permission, Activity activity) {
        AimData aimData = null;
        for (AimDescription description : aimDescriptionList) {
            aimData = description.getAimData(permission);
            if (aimData != null) {
                break;
            }
        }
        return aimData;
    }
}
