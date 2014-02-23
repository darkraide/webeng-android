package com.example.webeng;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by sangcu on 2/15/14.
 */
public class WebengApplication extends Application {
    public final static String tag = "MyApplication";
    public static Context s_applicationContext = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(tag, "configuration changed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        s_applicationContext = getApplicationContext();
    }
}
