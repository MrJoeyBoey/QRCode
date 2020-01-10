package com.joey.qrcodelibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.joey.qrcodelibrary.zxing.android.CaptureActivity;

public class QRCodeUtil {

    public static void scanQRCode(Activity activity, int requestCode){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity,"没有相机权限,请先获取相机权限",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(activity, CaptureActivity.class);
            activity.startActivityForResult(intent,requestCode);
        }
    }

}
