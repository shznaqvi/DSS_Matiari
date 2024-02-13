package edu.aku.hassannaqvi.dss_matiari.global;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    /*Keys*/
    public static String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static String FCM_TOKEN = "FCM_TOKEN";
    public static String LOCALE = "LOCALE";
    public static String APP_VERSION_NAME = "APP_VERSION_NAME";
    public static String APP_VERSION_CODE = "APP_VERSION_CODE";

    // For GPS Location
    public static String GPS_LAT = "LAT";
    public static String GPS_LON = "LON";
    public static String GPS_ACC = "ACC";
    public static String GPS_DATE = "DATE";

    private static SharedPreferences mSharedPref;

    private SharedPrefs() {
    }

    // Init SharedPreferences instance once on application launch
    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    // Read SharedPreference string
    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    // Write SharedPreference string
    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    // Read SharedPreference boolean
    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    // Write SharedPreference boolean
    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    // Read SharedPreference integer
    public static int read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    // Write SharedPreference integer
    public static void write(String key, int value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    // Clear All SharedPreferences
    public static void clear() {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.clear().apply();
    }

}
