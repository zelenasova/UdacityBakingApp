package com.kalata.peter.bakingapp.common.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.kalata.peter.bakingapp.App;

public class SharedPrefsUtils {

    private static final String FIRST_RUN_KEY = "first_run";

    public static SharedPreferences getSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
    }

    public static SharedPreferences.Editor getSharedPrefsEditor() {
        return getSharedPrefs().edit();
    }


    public static boolean isFirstRun() {
        return getSharedPrefs().getBoolean(FIRST_RUN_KEY, true);
    }

    public static void setFirstRun(boolean isFirstRun) {
        getSharedPrefsEditor().putBoolean(FIRST_RUN_KEY, isFirstRun).apply();
    }

}
