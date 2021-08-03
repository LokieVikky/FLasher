package com.thesoftwarecompany.flasher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneStateReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            context.startService(new Intent(context, FlashService.class));
        } else {
            context.stopService(new Intent(context, FlashService.class));
        }
        if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
            Log.d(TAG, "onReceive: Offhook");
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.d(TAG, "onReceive: Idle");
        }
    }

}
