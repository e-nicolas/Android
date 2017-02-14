package com.core;

import android.app.Application;
import android.content.Context;

/**
 * Created by Emmanouil Nicolas Papadimitropoulos on 27/01/2017.
 */

public class AppDelegate extends Application {

    //Context
    private static Context context;
    public static Context getAppContext() {
        return AppDelegate.context;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        AppDelegate.context = getApplicationContext();		
		
        ApplicationLifeCycle.Setup(this);
		ApplicationLifeCycle.registerLifecycleObserver(new ApplicationLifeCycle.AppLifeCycleCallback() {
            @Override
            public void onApplicationForeground() {
                
            }

            @Override
            public void onApplicationBackground() {

            }
        });

    }

}
