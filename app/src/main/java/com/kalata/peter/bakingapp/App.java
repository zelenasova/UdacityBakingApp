package com.kalata.peter.bakingapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.kalata.peter.bakingapp.injection.AppComponent;
import com.kalata.peter.bakingapp.injection.AppModule;
import com.kalata.peter.bakingapp.injection.DaggerAppComponent;

public class App extends Application {

    private AppComponent mAppComponent;
    private static App sInstance;

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static AppComponent getAppComponent() {
        return sInstance.mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initDagger();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void initDagger() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
    }

}
