package com.core.pushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.netpoint.integraapp.MainActivity;
import br.com.netpoint.integraapp.R;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 30/01/2017.
 * Following https://developers.google.com/cloud-messaging/downstream
 */

public class PushGcmListenerService extends GcmListenerService {

    public static PushNotificationController.PushNotificationCallback callback;

    private static final String TAG = "PushGcmListenerService";
    @Override
    public void onMessageReceived(String from, Bundle data) {
        try{
            JSONObject jsonObject = new JSONObject(data.getString("notification"));
            String message = jsonObject.getString("alert");
            String title = jsonObject.getString("title");

            //Callback
            if(callback != null)
                callback.onReceivedPush(from, data);

            //Mostrar notificação
            showNotification(title, message);
        }
        catch (JSONException e) {
            Log.e("ERRO", "Error while Parsing JSON", e);
        }
    }
    private void showNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_local_movies_white_24px)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
