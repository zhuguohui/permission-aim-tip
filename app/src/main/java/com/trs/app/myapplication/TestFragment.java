package com.trs.app.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.trs.app.aim_tip.PermissionAimTipHelper;
import com.trs.app.aim_tip.impl.TRSTipShowController;
import com.trs.app.aim_tip.impl.adatper.RawAimTipAdapter;
import com.trs.app.aim_tip.impl.view.DialogStyleData;

import java.util.Objects;

/**
 * Created by zhuguohui
 * Date: 2022/4/14
 * Time: 16:40
 * Desc:
 */
public class TestFragment extends Fragment {
    public static String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };
    public static String[] permissions2 = {
            Manifest.permission.READ_CONTACTS
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(permissions, 123);
            }
        });
        view.findViewById(R.id.btn_test_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogStyleData dialogStyleData = new DialogStyleData(R.layout.custom_dialog, DialogStyleData.USE_DEFAULT_STYLE);
                //修改样式
                PermissionAimTipHelper
                        .getInstance()
                        .setShowController(new TRSTipShowController(
                                new RawAimTipAdapter(v.getContext(),
                                        R.raw.permission_aim_description),
                                        dialogStyleData));

                requestPermissions(permissions2, 456);
            }
        });
    }
}
