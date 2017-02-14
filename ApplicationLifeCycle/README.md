# Application Life Cycle

Controller to handle when application is in foreground or background mode.

## Usage:
````java
//Inside AppDelegate class (extends Application)
ApplicationLifeCycle.Setup(this);

//Register callbacks
ApplicationLifeCycle.registerLifecycleObserver(new ApplicationLifeCycle.AppLifeCycleCallback() {
    @Override
    public void onApplicationForeground() {

    }

    @Override
    public void onApplicationBackground() {

    }
});
````

## Add in AndroidManifest.xml
````xml
<application
    android:name=".com.core.AppDelegate"
    >
    ...
    ...
</application>
````

License
----

MIT

**Free Software**
