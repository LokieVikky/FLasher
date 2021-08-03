package com.thesoftwarecompany.flasher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class FlashService extends Service {

    Context mContext = this;
    boolean isFlashOn = false;
    final long defaultRingingLength = 30000;
    final long interval = 200;

    CountDownTimer blinker = new CountDownTimer(defaultRingingLength, interval) {
        @Override
        public void onTick(long millisUntilFinished) {
            if(isFlashOn){
                turnOffFlash(mContext);
            }else {
                turnOnFlash(mContext);
            }
        }

        @Override
        public void onFinish() {
            turnOffFlash(mContext);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        blinker.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        blinker.cancel();
        turnOffFlash(mContext);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void turnOnFlash(Context context) {
        isFlashOn = true;
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlash(Context context) {
        isFlashOn = false;
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}
