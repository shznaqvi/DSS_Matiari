package edu.aku.hassannaqvi.dss_matiari.database

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.*
import edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME

object CreateTable {

    const val DATABASE_NAME = "$PROJECT_NAME.db"
    const val DATABASE_COPY = "${PROJECT_NAME}_copy.db"
    const val DATABASE_VERSION = 1

    const val SQL_CREATE_FORMS = ("CREATE TABLE "
            + FormsTable.TABLE_NAME + "("
            + FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsTable.COLUMN_UID + " TEXT,"
            + FormsTable.COLUMN_USERNAME + " TEXT,"
            + FormsTable.COLUMN_SYSDATE + " TEXT,"
            + FormsTable.COLUMN_ISTATUS + " TEXT,"
            + FormsTable.COLUMN_DEVICEID + " TEXT,"
            + FormsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsTable.COLUMN_SYNCED + " TEXT,"
            + FormsTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FormsTable.COLUMN_APPVERSION + " TEXT,"
            + FormsTable.COLUMN_HDSSID + " TEXT,"
            + FormsTable.COLUMN_SA + " TEXT"

            + " );"
            )

    const val SQL_CREATE_MWRA = ("CREATE TABLE "
            + MWRATable.TABLE_NAME + "("
            + MWRATable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MWRATable.COLUMN_PROJECT_NAME + " TEXT,"
            + MWRATable.COLUMN_UID + " TEXT,"
            + MWRATable.COLUMN_UUID + " TEXT,"
            + MWRATable.COLUMN_USERNAME + " TEXT,"
            + MWRATable.COLUMN_SYSDATE + " TEXT,"
            + MWRATable.COLUMN_ISTATUS + " TEXT,"
            + MWRATable.COLUMN_DEVICEID + " TEXT,"
            + MWRATable.COLUMN_DEVICETAGID + " TEXT,"
            + MWRATable.COLUMN_SYNCED + " TEXT,"
            + MWRATable.COLUMN_SYNCED_DATE + " TEXT,"
            + MWRATable.COLUMN_APPVERSION + " TEXT,"
            + MWRATable.COLUMN_HDSSID + " TEXT,"
            + MWRATable.COLUMN_SB + " TEXT"
            + " );"
            )


    const val SQL_CREATE_USERS = ("CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.COLUMN_USERNAME + " TEXT,"
            + UsersTable.COLUMN_PASSWORD + " TEXT,"
            + UsersTable.COLUMN_FULLNAME + " TEXT"
            + " );"
            )

    const val SQL_CREATE_VERSIONAPP = ("CREATE TABLE " + VersionTable.TABLE_NAME + " ("
            + VersionTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VersionTable.COLUMN_VERSION_CODE + " TEXT, "
            + VersionTable.COLUMN_VERSION_NAME + " TEXT, "
            + VersionTable.COLUMN_PATH_NAME + " TEXT "
            + ");"
            )

    const val SQL_CREATE_VILLAGES = ("CREATE TABLE " + TableVillage.TABLE_NAME + "("
            + TableVillage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableVillage.COLUMN_UCNAME + " TEXT,"
            + TableVillage.COLUMN_VILLAGE_NAME + " TEXT,"
            + TableVillage.COLUMN_VILLAGE_CODE + " TEXT,"
            + TableVillage.COLUMN_UC_CODE + " TEXT" + " );")


    const val SQL_CREATE_ZSTANDARD = "CREATE TABLE " + ZScoreTable.TABLE_NAME + " (" +
            ZScoreTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ZScoreTable.COLUMN_SEX + " TEXT, " +
            ZScoreTable.COLUMN_AGE + " TEXT, " +
            ZScoreTable.COLUMN_MEASURE + " TEXT, " +
            ZScoreTable.COLUMN_L + " TEXT, " +
            ZScoreTable.COLUMN_M + " TEXT, " +
            ZScoreTable.COLUMN_S + " TEXT, " +
            ZScoreTable.COLUMN_CAT + " TEXT " +
            ");"

}