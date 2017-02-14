package br.com.netpoint.integraapp.com.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 31/01/2017.
 */

public class ApplicationLifeCycle {

    //Application Lifecycle
    public interface AppLifeCycleCallback {
        void onApplicationForeground();
        void onApplicationBackground();
    }

    private static HashMap<String, Integer> activities;

    public static void Setup(Application application){
        activities = new HashMap<>();
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }
            @Override
            public void onActivityStarted(Activity activity) {
                activities.put(activity.getLocalClassName(), 1);
                notifyLifeCycleObservers();
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
                //map Activity unique class name with 0 on foreground
                activities.put(activity.getLocalClassName(), 0);
                notifyLifeCycleObservers();
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    //Callbacks
    private static List<AppLifeCycleCallback> lifecycleObservers = new ArrayList<AppLifeCycleCallback>();
    public static void registerLifecycleObserver(AppLifeCycleCallback observer){
        if(lifecycleObservers.indexOf(observer) == -1){
            lifecycleObservers.add(observer);
        }
    }
    public static void unregisterLifecycleObserver(AppLifeCycleCallback observer){
        if(lifecycleObservers.indexOf(observer) != -1){
            lifecycleObservers.remove(observer);
        }
    }
    private static void notifyLifeCycleObservers(){
        if(isApplicationInBackground() != isApplicationInBackgroundCache) {
            isApplicationInBackgroundCache = isApplicationInBackground();

            for (final AppLifeCycleCallback observer : lifecycleObservers) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (isApplicationInBackgroundCache)
                            observer.onApplicationBackground();
                        else
                            observer.onApplicationForeground();

                    }

                }).start();
            }
        }
    }
    /**
     * Check if any activity is in the foreground
     */
    private static boolean isApplicationInBackgroundCache;
    public static boolean isApplicationInBackground() {
        for (String s : activities.keySet()) {
            if (activities.get(s) == 1) {
                return false;
            }
        }
        return true;
    }
}
