# GCM Push Notification Controller

Controller to handle registration and received pushs with GCM System

## Usage:
````java
//Push Notification Controller Setup
PushNotificationController.Setup(context);
//Register callbacks
PushNotificationController.registerPushObserver(new PushNotificationController.PushNotificationCallback() {
    @Override
    public void onRegistered(String token) {

    }

    @Override
    public void onReceivedPush(String from, Bundle data) {

    }
});
````

## Add in AndroidManifest.xml
````xml

<!-- PUSH NOTIFICATION GCM -->
<uses-permission android:name="android.permission.INTERNET" />
<permission android:name="br.com.netpoint.integraapp.permission.C2D_MESSAGE"
    android:protectionLevel="signature" />
<uses-permission android:name="br.com.netpoint.integraapp.permission.C2D_MESSAGE" />

<application>
    <!-- Push Notification -->
    <receiver
        android:name="com.google.android.gms.gcm.GcmReceiver"
        android:exported="true"
        android:permission="com.google.android.c2dm.permission.SEND" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
            <category android:name="br.com.netpoint.integraapp" />
        </intent-filter>
    </receiver>
    <service
        android:name=".com.core.pushnotification.PushGcmListenerService"
        android:exported="false" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />
        </intent-filter>
    </service>
    <service
        android:name=".com.core.pushnotification.PushRegisterService"
        android:exported="false" >
    </service>
    <service
        android:name=".com.core.pushnotification.PushInstanceIDListenerService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.android.gms.iid.InstanceID" />
        </intent-filter>
    </service>
    <meta-data android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />
</application>
````
## Add in build.gradle(Module)
````js
dependencies {
    ...
    ...
    ...
    compile "com.google.android.gms:play-services:10.0.1"
}
apply plugin: 'com.google.gms.google-services'
````
## Add in build.gradle(Project)
````js
buildscript {
  repositories {
      ...
  }
  dependencies {
      ...

      //Push Notification Configuration
      classpath 'com.google.gms:google-services:3.0.0'
  }
}
````

License
----

MIT

**Free Software**
