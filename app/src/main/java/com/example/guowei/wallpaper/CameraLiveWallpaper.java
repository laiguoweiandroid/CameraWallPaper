package com.example.guowei.wallpaper;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Guowei on 2017/5/12.
 */

public class CameraLiveWallpaper extends WallpaperService {
    private Camera camera;
    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    class CameraEngine extends Engine implements Camera.PreviewCallback{
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            startPriview();
            //设置处理触摸事件
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            //事件处理，点击拍照，长按拍照

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }



        @Override
        public void onVisibilityChanged(boolean visible) {
            if(visible){
                startPriview();
            }else{
                stopPreview();
            }
        }

        public void startPriview() {
            camera =Camera.open();
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }
        private void stopPreview() {
            if(camera!=null){
                try {
                    camera.stopPreview();
                    camera.setPreviewCallback(null);
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera =null;
            }
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            camera.addCallbackBuffer(data);
        }
    }

}
