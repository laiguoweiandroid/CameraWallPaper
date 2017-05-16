package com.example.guowei.wallpaper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CAMERA = 454;
    private Context context;
    static  final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= this;
        findViewById(R.id.tvtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelfPerssion();
            }
        });
    }

    private void checkSelfPerssion() {
        if(ContextCompat.checkSelfPermission(context,PERMISSION_CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{PERMISSION_CAMERA},PERMISSIONS_REQUEST_CAMERA);
        }else{
            startWallpaper();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSIONS_REQUEST_CAMERA:
                if(grantResults.length>0&&
                        grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    startWallpaper();
                }else {
                    Toast.makeText(context,"要打开相应的权限",Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void startWallpaper() {
        final Intent pickIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent chooser = Intent.createChooser(pickIntent,"选择壁纸");
        startActivity(chooser);
    }
}
