package com.core.pushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import br.com.netpoint.integraapp.R;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 30/01/2017.
 * Following https://developers.google.com/cloud-messaging/android/client?configured=true
 */

public class PushRegisterService extends IntentService {

    public static PushNotificationController.PushNotificationCallback callback;

    private static final String TAG = "PushRegisterService";
    public PushRegisterService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try{
            String token = instanceID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );
            Log.i(TAG, "GCM Token: " + token);

            //Callback
            if(callback != null)
                callback.onRegistered(token);
        }
        catch(Exception e){
            Log.d(TAG, "Failed to complete token refresh ", e);
        }

    }
}
