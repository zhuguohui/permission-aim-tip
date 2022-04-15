package com.trs.app.myapplication;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    public static String[] locationPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,

    };

    public static String[] storePermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, new TestFragment(), "test")
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionByCompat(View view) {
        ActivityCompat.requestPermissions(this, locationPermission, 100);
    }


    public void toTowPage(View view) {

    }

    public void requestPermissionByRx(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.request(storePermission)
                .subscribe(get -> {

                });
    }


}