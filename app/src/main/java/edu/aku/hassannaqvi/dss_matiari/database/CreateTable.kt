package edu.aku.hassannaqvi.dss_matiari.database

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.*
import edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME

object CreateTable {

    const val DATABASE_NAME = "$PROJECT_NAME.db"
    const val DATABASE_COPY = "${PROJECT_NAME}_copy.db"
    const val DATABASE_COPY2 = "${PROJECT_NAME}_copy.db"
    const val DATABASE_VERSION = 3

    const val SQL_CREATE_HOUSEHOLDS = ("CREATE TABLE "
            + HouseholdTable.TABLE_NAME + "("
            + HouseholdTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + HouseholdTable.COLUMN_PROJECT_NAME + " TEXT,"
            + HouseholdTable.COLUMN_UID + " TEXT,"
            + HouseholdTable.COLUMN_USERNAME + " TEXT,"
            + HouseholdTable.COLUMN_SYSDATE + " TEXT,"
            + HouseholdTable.COLUMN_ISTATUS + " TEXT,"
            + HouseholdTable.COLUMN_DEVICEID + " TEXT,"
            + HouseholdTable.COLUMN_DEVICETAGID + " TEXT,"
            + HouseholdTable.COLUMN_SYNCED + " TEXT,"
            + HouseholdTable.COLUMN_SYNCED_DATE + " TEXT,"
            + HouseholdTable.COLUMN_APPVERSION + " TEXT,"
            + HouseholdTable.COLUMN_HDSSID + " TEXT,"
            + HouseholdTable.COLUMN_UC_CODE + " TEXT,"
            + HouseholdTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + HouseholdTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + HouseholdTable.COLUMN_STRUCTURE_NO + " TEXT,"
            + HouseholdTable.COLUMN_VISIT_NO + " TEXT,"
            + HouseholdTable.COLUMN_SA + " TEXT"
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
            + MWRATable.COLUMN_VILLAGE_CODE + " TEXT,"
            + MWRATable.COLUMN_UC_CODE + " TEXT,"
            + MWRATable.COLUMN_STRUCTURE_NO + " TEXT,"
            + MWRATable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + MWRATable.COLUMN_SNO + " TEXT,"
            + MWRATable.COLUMN_SB + " TEXT"
            + " );"
            )


    const val SQL_CREATE_FOLLOWUPS = ("CREATE TABLE "
            + FollowupsTable.TABLE_NAME + "("
            + FollowupsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FollowupsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FollowupsTable.COLUMN_UID + " TEXT,"
            + FollowupsTable.COLUMN_UUID + " TEXT,"
            + FollowupsTable.COLUMN_USERNAME + " TEXT,"
            + FollowupsTable.COLUMN_SYSDATE + " TEXT,"
            + FollowupsTable.COLUMN_ISTATUS + " TEXT,"
            + FollowupsTable.COLUMN_DEVICEID + " TEXT,"
            + FollowupsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FollowupsTable.COLUMN_SYNCED + " TEXT,"
            + FollowupsTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FollowupsTable.COLUMN_APPVERSION + " TEXT,"
            + FollowupsTable.COLUMN_HDSSID + " TEXT,"
            + FollowupsTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + FollowupsTable.COLUMN_UC_CODE + " TEXT,"
            + FollowupsTable.COLUMN_SNO + " TEXT,"
            + FollowupsTable.COLUMN_FP_ROUND + " TEXT,"
            + FollowupsTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + FollowupsTable.COLUMN_VISIT_NO + " TEXT,"
            + FollowupsTable.COLUMN_SC + " TEXT,"
            + FollowupsTable.COLUMN_SD + " TEXT"
            + " );"
            )

    const val SQL_CREATE_OUTCOME_FOLLOWUPS = ("CREATE TABLE "
            + OutcomeFollowupTable.TABLE_NAME + "("
            + OutcomeFollowupTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + OutcomeFollowupTable.COLUMN_PROJECT_NAME + " TEXT,"
            + OutcomeFollowupTable.COLUMN_UID + " TEXT,"
            + OutcomeFollowupTable.COLUMN_UUID + " TEXT,"
            + OutcomeFollowupTable.COLUMN_USERNAME + " TEXT,"
            + OutcomeFollowupTable.COLUMN_SYSDATE + " TEXT,"
            + OutcomeFollowupTable.COLUMN_ISTATUS + " TEXT,"
            + OutcomeFollowupTable.COLUMN_DEVICEID + " TEXT,"
            + OutcomeFollowupTable.COLUMN_DEVICETAGID + " TEXT,"
            + OutcomeFollowupTable.COLUMN_SYNCED + " TEXT,"
            + OutcomeFollowupTable.COLUMN_SYNCED_DATE + " TEXT,"
            + OutcomeFollowupTable.COLUMN_APPVERSION + " TEXT,"
            + OutcomeFollowupTable.COLUMN_HDSSID + " TEXT,"
            + OutcomeFollowupTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + OutcomeFollowupTable.COLUMN_UC_CODE + " TEXT,"
            + OutcomeFollowupTable.COLUMN_SNO + " TEXT,"
            + OutcomeFollowupTable.COLUMN_FP_ROUND + " TEXT,"
            + OutcomeFollowupTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + OutcomeFollowupTable.COLUMN_VISIT_NO + " TEXT,"
            + OutcomeFollowupTable.COLUMN_SE + " TEXT"
            + " );"
            )

    const val SQL_CREATE_PREGNANCY = ("CREATE TABLE "
            + PregnancyTable.TABLE_NAME + "("
            + PregnancyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PregnancyTable.COLUMN_PROJECT_NAME + " TEXT,"
            + PregnancyTable.COLUMN_UID + " TEXT,"
            + PregnancyTable.COLUMN_UUID + " TEXT,"
            + PregnancyTable.COLUMN_USERNAME + " TEXT,"
            + PregnancyTable.COLUMN_SYSDATE + " TEXT,"
            + PregnancyTable.COLUMN_ISTATUS + " TEXT,"
            + PregnancyTable.COLUMN_DEVICEID + " TEXT,"
            + PregnancyTable.COLUMN_DEVICETAGID + " TEXT,"
            + PregnancyTable.COLUMN_SYNCED + " TEXT,"
            + PregnancyTable.COLUMN_SYNCED_DATE + " TEXT,"
            + PregnancyTable.COLUMN_APPVERSION + " TEXT,"
            + PregnancyTable.COLUMN_HDSSID + " TEXT,"
            + PregnancyTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + PregnancyTable.COLUMN_UC_CODE + " TEXT,"
            + PregnancyTable.COLUMN_STRUCTURE_NO + " TEXT,"
            + PregnancyTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + PregnancyTable.COLUMN_SD + " TEXT"
            + " );"
            )
    const val SQL_CREATE_OUTCOME = ("CREATE TABLE "
            + OutcomeTable.TABLE_NAME + "("
            + OutcomeTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + OutcomeTable.COLUMN_PROJECT_NAME + " TEXT,"
            + OutcomeTable.COLUMN_UID + " TEXT,"
            + OutcomeTable.COLUMN_UUID + " TEXT,"
            + OutcomeTable.COLUMN_MUID + " TEXT,"
            + OutcomeTable.COLUMN_USERNAME + " TEXT,"
            + OutcomeTable.COLUMN_SYSDATE + " TEXT,"
            + OutcomeTable.COLUMN_ISTATUS + " TEXT,"
            + OutcomeTable.COLUMN_DEVICEID + " TEXT,"
            + OutcomeTable.COLUMN_DEVICETAGID + " TEXT,"
            + OutcomeTable.COLUMN_SYNCED + " TEXT,"
            + OutcomeTable.COLUMN_SYNCED_DATE + " TEXT,"
            + OutcomeTable.COLUMN_APPVERSION + " TEXT,"
            + OutcomeTable.COLUMN_HDSSID + " TEXT,"
            + OutcomeTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + OutcomeTable.COLUMN_UC_CODE + " TEXT,"
            + OutcomeTable.COLUMN_SNO + " TEXT,"
            + OutcomeTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + OutcomeTable.COLUMN_SE + " TEXT"
            + " );"
            )


    const val SQL_CREATE_USERS = ("CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.COLUMN_USERNAME + " TEXT,"
            + UsersTable.COLUMN_PASSWORD + " TEXT,"
            + UsersTable.COLUMN_FULLNAME + " TEXT,"
            + UsersTable.COLUMN_DESIGNATION + " TEXT"
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

    const val SQL_CREATE_MAXHHNO = ("CREATE TABLE "
            + MaxHhnoTable.TABLE_NAME + "("
            + MaxHhnoTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MaxHhnoTable.COLUMN_UC_CODE + " TEXT,"
            + MaxHhnoTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + MaxHhnoTable.COLUMN_MAX_HHNO + " TEXT"
            + " );"
            )
    const val SQL_CREATE_FOLLOWUPSCHE = ("CREATE TABLE " + TableFollowUpsSche.TABLE_NAME + "("
            + TableFollowUpsSche.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableFollowUpsSche.COLUMN_UC_CODE + " TEXT,"
            + TableFollowUpsSche.COLUMN_VILLAGE_CODE + " TEXT,"
            + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + TableFollowUpsSche.COLUMN_HDSSID + " TEXT,"
            + TableFollowUpsSche.COLUMN_RA01 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RA08 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RA14 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RA18 + " TEXT,"
            + TableFollowUpsSche.COLUMN_FROUND + " TEXT,"
            + TableFollowUpsSche.COLUMN_DONE_DATE + " TEXT,"
            + TableFollowUpsSche.COLUMN_ISTATUS + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB01 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB02 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB03 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB04 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RC12 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB05 + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB06 + " TEXT,"
            + TableFollowUpsSche.COLUMN_MEMBERTYPE + " TEXT,"
            + TableFollowUpsSche.COLUMN_RB07 + " TEXT" + " );")

    const val SQL_CREATE_FP_HOUSEHOLDS = ("CREATE TABLE "
            + FPHouseholdTable.TABLE_NAME + "("
            + FPHouseholdTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FPHouseholdTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FPHouseholdTable.COLUMN_UID + " TEXT,"
            + FPHouseholdTable.COLUMN_USERNAME + " TEXT,"
            + FPHouseholdTable.COLUMN_SYSDATE + " TEXT,"
            + FPHouseholdTable.COLUMN_ISTATUS + " TEXT,"
            + FPHouseholdTable.COLUMN_DEVICEID + " TEXT,"
            + FPHouseholdTable.COLUMN_DEVICETAGID + " TEXT,"
            + FPHouseholdTable.COLUMN_SYNCED + " TEXT,"
            + FPHouseholdTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FPHouseholdTable.COLUMN_APPVERSION + " TEXT,"
            + FPHouseholdTable.COLUMN_HDSSID + " TEXT,"
            + FPHouseholdTable.COLUMN_UC_CODE + " TEXT,"
            + FPHouseholdTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + FPHouseholdTable.COLUMN_HOUSEHOLD_NO + " TEXT,"
            + FPHouseholdTable.COLUMN_STRUCTURE_NO + " TEXT,"
            + FPHouseholdTable.COLUMN_FP_ROUND + " TEXT,"
            + FPHouseholdTable.COLUMN_SA + " TEXT,"
            + FPHouseholdTable.COLUMN_VISIT_NO + " TEXT"
            + " );"
            )


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


    const val SQL_ALTER_FOLLOWUPSCHE =
        "ALTER TABLE " + TableFollowUpsSche.TABLE_NAME + " ADD " + TableFollowUpsSche.COLUMN_ISTATUS + " TEXT;"

    const val SQL_ALTER_ADD_DOB =
        "ALTER TABLE " + TableFollowUpsSche.TABLE_NAME + " ADD " + TableFollowUpsSche.COLUMN_RB04 + " TEXT; "


    const val SQL_ALTER_ADD_GENDER =
        "ALTER TABLE " + TableFollowUpsSche.TABLE_NAME + " ADD " + TableFollowUpsSche.COLUMN_RC12 + " TEXT; "

    const val SQL_ALTER_ADD_MEMBER_TYPE =
        "ALTER TABLE " + TableFollowUpsSche.TABLE_NAME + " ADD " + TableFollowUpsSche.COLUMN_MEMBERTYPE + " TEXT; "


}