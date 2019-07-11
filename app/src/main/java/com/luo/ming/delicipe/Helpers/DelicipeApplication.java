package com.luo.ming.delicipe.Helpers;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class DelicipeApplication extends Application {
    
    private static DelicipeApplication instance;
    private static Context context;

    public static DelicipeApplication getInstance(){
        return instance;
    }

    public static Context getAppContext(){
        return context;
    }

    public void setAppContext(Context mContext){
        this.context = mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
