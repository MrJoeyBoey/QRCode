package com.joey.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joey.qrcodelibrary.QRCodeUtil;

public class MainActivity extends AppCompatActivity {

    private Button btnScan,btnCreate;
    private TextView tvResultScan;
    private ImageView ivResultCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView(){
        btnScan = findViewById(R.id.btn_scan);
        btnCreate = findViewById(R.id.btn_create);
        tvResultScan = findViewById(R.id.tv_scan_result);
        ivResultCreate = findViewById(R.id.iv_create_result);
    }

    private void initEvent(){
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        QRCodeUtil.scanQRCode(MainActivity.this,0);
                    }else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},1);
                    }
                }
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(MainActivity.this)
                        .load(QRCodeUtil.createQRCode("害!",1080,1080, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)))
                        .into(ivResultCreate);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                QRCodeUtil.scanQRCode(MainActivity.this,0);
            }else {
                Toast.makeText(MainActivity.this,"拒绝该权限将无法扫描二维码",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            if(data == null)return;
            tvResultScan.setText(("扫描结果:"+data.getStringExtra(QRCodeUtil.SCAN_RESULT)));
        }
    }
}
