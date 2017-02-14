package com.core.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.netpoint.integraapp.com.core.AppDelegate;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 31/01/2017.
 *
 * Control the callbacks for Push Notifications
 */

public class PushNotificationController {

    public interface PushNotificationCallback {
        void onRegistered(String token);
        void onReceivedPush(String from, Bundle data);
    }

    public static void Setup(Context context){
        Intent i = new Intent(context, PushRegisterService.class);
        context.startService(i);

        PushRegisterService.callback = callback;
        PushGcmListenerService.callback = callback;
    }
    static PushNotificationCallback callback = new PushNotificationCallback() {
        @Override
        public void onRegistered(String token) {
            notifyTokenObservers(token);
        }
        @Override
        public void onReceivedPush(String from, Bundle data) {
            notifyReceivedObservers(from, data);
        }
    };

    //Callbacks
    private static List<PushNotificationCallback> pushObservers = new ArrayList<PushNotificationCallback>();
    public static void registerPushObserver(PushNotificationCallback observer){
        if(pushObservers.indexOf(observer) == -1){
            pushObservers.add(observer);
        }
    }
    public static void unregisterPushObserver(PushNotificationCallback observer){
        if(pushObservers.indexOf(observer) != -1){
            pushObservers.remove(observer);
        }
    }
    //Notify Token
    private static void notifyTokenObservers(final String token){
        for (final PushNotificationCallback observer : pushObservers) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        observer.onRegistered(token);
                }
            }).start();
        }
    }
    //Notify Push received
    private static void notifyReceivedObservers(final String from, final Bundle data){
        for (final PushNotificationCallback observer : pushObservers) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    observer.onReceivedPush(from, data);
                }
            }).start();
        }
    }

}
