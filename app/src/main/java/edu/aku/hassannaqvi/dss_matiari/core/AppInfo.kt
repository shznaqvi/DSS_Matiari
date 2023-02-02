package edu.aku.hassannaqvi.dss_matiari.core

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import org.apache.commons.lang3.StringUtils
import java.text.SimpleDateFormat
import java.util.*

/*
 * @author Mustufa
 * @update Ali Azaz Alam dt. 12.16.20
 * */
class AppInfo {
    var versionName: String = StringUtils.EMPTY
    var installedOn: Long = 0
    var versionCode = 0
    var tagName: String? = null
    var deviceID: String = StringUtils.EMPTY
    var appVersion: String = StringUtils.EMPTY
    val dtToday: String
        get() = SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(Date().time)
    val isTestingApp: Boolean
        // get() = versionName.split("\\.".toRegex())[0].toInt() > 0
        get() = versionName.split(".").first().toInt() > 0


    lateinit var dbHelper: DssRoomDatabase

    constructor(context: Context) {
        try {
            installedOn = context.packageManager.getPackageInfo(context.applicationContext.packageName, 0).lastUpdateTime
            versionCode = context.packageManager.getPackageInfo(context.applicationContext.packageName, 0).versionCode
            versionName = context.packageManager.getPackageInfo(context.applicationContext.packageName, 0).versionName
            deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            appVersion = "$versionName.$versionCode"
            tagName = getTagName(context)
            setupDatabase(context)
            /*synchronized(this) {
                dbHelper = DssRoomDatabase.dbInstance!!
            }*/
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    constructor(versionName: String, installedOn: Long, versionCode: Int) {
        this.versionName = versionName
        this.installedOn = installedOn
        this.versionCode = versionCode
    }

    private fun getTagName(mContext: Context): String? {
        val sharedPref = mContext.getSharedPreferences("tagName", Context.MODE_PRIVATE)
        return sharedPref.getString("tagName", null)
    }

    fun updateTagName(mContext: Context) {
        tagName = getTagName(mContext)
    }

    fun getInfo(): AppInfo {
        return AppInfo(versionName, installedOn, versionCode)
    }

    fun getAppInfo(): String {
        return """Ver. $versionName.$versionCode ( Last Updated: ${SimpleDateFormat("dd MMM. yyyy", Locale.ENGLISH).format(Date(getInfo().installedOn))} )"""
    }

    private fun setupDatabase(context: Context) {
        synchronized(this) {
            dbHelper = if (DssRoomDatabase.dbInstance != null)
                DssRoomDatabase.dbInstance!!
            else {
                initSecure(context)
                DssRoomDatabase.dbInstance!!
            }
        }
    }

    private fun initSecure(context: Context) = context.apply {
        // Initialize SQLCipher library
        SQLiteDatabase.loadLibs(this);

        try {
            val ai: ApplicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            val bundle: Bundle = ai.metaData;
            val trats: Int = bundle.getInt("YEK_TRATS");
            //IBAHC = bundle.getString("YEK_REVRES").substring(TRATS, TRATS + 16);
            MainApp.IBAHC = bundle.getString("YEK_REVRES");
            MainApp.IBAHC = bundle.getString("YEK_REVRES")!!.substring(trats, trats + 16)

            // Room DB
            if (MainApp.IBAHC != null) {
                DssRoomDatabase.init(this, MainApp.IBAHC)
            };

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace();
        }
    }
}