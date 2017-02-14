package com.core.pushnotification;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 30/01/2017.
 * Following https://developers.google.com/cloud-messaging/android/client?configured=true
 */

public class PushInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, PushRegisterService.class);
        startService(intent);
    }
}
