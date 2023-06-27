package edu.aku.hassannaqvi.dss_matiari.newstruct.models;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BuildConfig;
import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.SharedPrefs;

public class AppInfoNew {
    public static String NAME = "versionApp";

    private long installedOn;
    private String versionName;
    private int versionCode;
    private String deviceId;
    private String appVersion;
    private String sysDate;

    public static AppInfoNew initAppInfo(Context context) {
        try {
            AppInfoNew appInfoNew = new AppInfoNew();
            String packageName = context.getPackageName();
            PackageInfo pm = context.getPackageManager().getPackageInfo(packageName, 0);

            appInfoNew.installedOn = pm.lastUpdateTime;
            appInfoNew.versionName = pm.versionName;
            appInfoNew.versionCode = pm.versionCode;
            appInfoNew.deviceId = AppConstants.DEVICE_ID;
            appInfoNew.appVersion = String.format(Locale.getDefault(), "%s.%d", appInfoNew.versionName, appInfoNew.versionCode);
            appInfoNew.sysDate = DateUtils.getCurrentDateTime();

            return appInfoNew;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppVersion() {
        return appVersion;
    }

    // For getting formatted version name and code
    public static String getAppInfo() {
        return String.format(Locale.getDefault(), "Ver. %s (Last Updated: %s)",
                MainApp.appInfoNew.appVersion, DateUtils.getFormattedDateTime(
                        new Date(MainApp.appInfoNew.installedOn), AppConstants.APP_DATE_FORMAT));
    }

    // Setting updated app version
    public static void setUpdatedAppInfo(AppInfoNew appInfoNew) {
        SharedPrefs.write(SharedPrefs.APP_VERSION_NAME, appInfoNew.getVersionName());
        SharedPrefs.write(SharedPrefs.APP_VERSION_CODE, appInfoNew.getVersionCode());
    }

    // Getting updated app version from shared preferences
    public static String getUpdatedAppInfo(Activity activity) {
        String updatedVersionName = SharedPrefs.read(SharedPrefs.APP_VERSION_NAME, BuildConfig.VERSION_NAME);
        int updatedVersionCode = SharedPrefs.read(SharedPrefs.APP_VERSION_CODE, BuildConfig.VERSION_CODE);

        return String.format(Locale.getDefault(), activity.getString(R.string.updated_version),
                updatedVersionName, updatedVersionCode);
    }

    // Check if app version is up to date
    public static boolean isAppUpdated() {
        String updatedVersionName = SharedPrefs.read(SharedPrefs.APP_VERSION_NAME, BuildConfig.VERSION_NAME);
        int updatedVersionCode = SharedPrefs.read(SharedPrefs.APP_VERSION_CODE, BuildConfig.VERSION_CODE);

        String currentVersionName = BuildConfig.VERSION_NAME;
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // If false then the app is NOT up to date
        // If true then the app is up to date
        return currentVersionCode >= updatedVersionCode;
    }

}
