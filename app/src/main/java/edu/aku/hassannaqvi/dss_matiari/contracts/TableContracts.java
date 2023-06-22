package edu.aku.hassannaqvi.dss_matiari.contracts;

import android.provider.BaseColumns;

public class TableContracts {

    public static abstract class HouseholdTable implements BaseColumns {
        public static final String TABLE_NAME = "hhs";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_STRUCTURE_NO = "structureNo";
        public static final String COLUMN_VISIT_NO = "visitNo";
        public static final String COLUMN_SA = "s1";
        public static final String COLUMN_MUID = "muid";
        public static final String COLUMN_REGROUND = "regRound";
        //public static final String COLUMN_FROUND = "fRound";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class MWRATable implements BaseColumns {
        public static final String TABLE_NAME = "MWRAs";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_STRUCTURE_NO = "structureNo";
        public static final String COLUMN_ROUND = "round";
        public static final String COLUMN_REGROUND = "regRound";
        public static final String COLUMN_CHILD_COUNT = "child_count";
        //public static final String COLUMN_FROUND = "fRound";
        public static final String COLUMN_SNO = "sNo";
        public static final String COLUMN_SB = "s2";
        public static final String COLUMN_SC = "s3";
        public static final String COLUMN_SD = "s4";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }


    public static abstract class FollowupsTable implements BaseColumns {
        public static final String TABLE_NAME = "followups";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_FMUID = "_muid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_FP_ROUND = "fRound";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_VISIT_NO = "visitNo";
        public static final String COLUMN_SNO = "sNo";

        public static final String COLUMN_SC = "s3";
        public static final String COLUMN_SD = "s4";


        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class PregnancyTable implements BaseColumns {
        public static final String TABLE_NAME = "pregnancy";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_STRUCTURE_NO = "structureNo";
        public static final String COLUMN_SD = "s4";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class OutcomeTable implements BaseColumns {
        public static final String TABLE_NAME = "outcomes";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_MUID = "_muid";
        public static final String COLUMN_MSNO = "msno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_REGROUND = "regRound";
        //public static final String COLUMN_FROUND = "fRound";
        public static final String COLUMN_ROUND = "round";
        public static final String COLUMN_SE = "s5";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }


    public static abstract class OutcomeFollowupTable implements BaseColumns {
        public static final String TABLE_NAME = "outcomeFollowup";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_MUID = "_muid";
        public static final String COLUMN_MSNO = "msno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_FP_ROUND = "fRound";
        public static final String COLUMN_SE = "s5";
        public static final String COLUMN_VISIT_NO = "visitNo";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }
    public static abstract class UsersTable implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PASSWORD_ENC = "passwordEnc";
        public static final String COLUMN_FULLNAME = "full_name";
        public static final String COLUMN_DESIGNATION = "designation";
        public static final String COLUMN_ISNEW_USER = "isNewUser";
        public static final String COLUMN_ENABLED = "enabled";
        public static final String COLUMN_PWD_EXPIRY = "pwdExpiry";

    }

    public static abstract class VersionTable implements BaseColumns {


        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String TABLE_NAME = "version";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_VERSION_PATH = "elements";
        public static final String COLUMN_VERSION_CODE = "versionCode";
        public static final String COLUMN_VERSION_NAME = "versionName";
        public static final String COLUMN_PATH_NAME = "outputFile";
        public static final String SERVER_URI = "output-metadata.json";

    }

    public static abstract class ZScoreTable implements BaseColumns {

        public static final String TABLE_NAME = "zstandards";

        public static final String _ID = "_id";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_L = "l";
        public static final String COLUMN_M = "m";
        public static final String COLUMN_S = "s";
        public static final String COLUMN_CAT = "cat";
        public static final String SERVER_URI = "zstandards.php";
        public static String PATH = "zstandards";
       /* public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        //        public static final String REGION_DSS = "region";
        public static Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)
                .buildUpon().appendPath(PATH).build();*/

/*        public static Uri buildUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }*/
    }

    public static abstract class TableVillage implements BaseColumns {

        public static final String TABLE_NAME = "villages";
        public static final String ID = "id";
        public static final String COLUMN_UCNAME = "ucname";
        public static final String COLUMN_VILLAGE_NAME = "villagename";
        public static final String COLUMN_VILLAGE_CODE = "villagecode";
        public static final String COLUMN_UC_CODE = "uccode";


        //public static final String SERVER_URI = "villages.php";

/*        public static String PATH = "villages";

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)
                .buildUpon().appendPath(PATH).build();

        public static String getMovieKeyFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }*/
    }

    public static abstract class MaxHhnoTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "maxhhno";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_MAX_HHNO = "maxhhno";
    }

    public static abstract class TableHHS implements BaseColumns {

        public static final String TABLE_NAME = "hhs_view";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_VISITNO = "visitNo";
        public static final String COLUMN_RA01 = "ra01"; // Date of First Visit
        public static final String COLUMN_RA08 = "ra08"; // Para
        public static final String COLUMN_RA12 = "ra12";
        public static final String COLUMN_ROUND = "round";
        public static final String COLUMN_RA17_A1 = "ra17_a1";
        public static final String COLUMN_RA17_A2 = "ra17_a2";
        public static final String COLUMN_RA17_A3 = "ra17_a3";
        public static final String COLUMN_RA17_B1 = "ra17_b1";
        public static final String COLUMN_RA17_B2 = "ra17_b2";
        public static final String COLUMN_RA17_B3 = "ra17_b3";
        public static final String COLUMN_RA17_C1 = "ra17_c1";
        public static final String COLUMN_RA17_C2 = "ra17_c2";
        public static final String COLUMN_RA17_C3 = "ra17_c3";
        public static final String COLUMN_RA17_D1 = "ra17_d1";
        public static final String COLUMN_RA17_D2 = "ra17_d2";
        public static final String COLUMN_RA17_D3 = "ra17_d3";
        public static final String COLUMN_RA05 = "ra05";
        public static final String COLUMN_RA18 = "ra18"; // No. of MWRA in the household


    }

    public static abstract class TableFollowUpsSche implements BaseColumns {

        public static final String TABLE_NAME = "hhfuplist_view";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_MUID = "_muid";
        public static final String COLUMN_HOUSEHOLD_NO = "hhno";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_RA01 = "ra01"; // Date of First Visit
        public static final String COLUMN_RA08 = "ra08"; // Para
        public static final String COLUMN_RA12 = "ra12"; // Head of Household
        public static final String COLUMN_RA18 = "ra18"; // No. of MWRA in the household
        public static final String COLUMN_FROUND = "fRound";
        public static final String COLUMN_DONE_DATE = "fup_targetDt";
        public static final String COLUMN_ISTATUS = "istatus";
        public static final String COLUMN_RB01 = "rb01"; // MWRA Sno
        public static final String COLUMN_RB02 = "rb02"; // MWRA Name
        public static final String COLUMN_RB03 = "rb03"; // Age
        public static final String COLUMN_RB04 = "rb04"; // DOB
        public static final String COLUMN_RC04 = "rc04"; // Gender
        public static final String COLUMN_MSNO = "msno";
        public static final String COLUMN_CHILD_COUNT = "child_count";
        public static final String COLUMN_PREG_COUNT = "pregnum";
        public static final String COLUMN_RB22 = "rb22";
        public static final String COLUMN_RB23 = "rb23";
        //public static final String COLUMN_RC15 = "rc15"; // Gender
        public static final String COLUMN_RB05 = "rb05"; // Husband or Father
        public static final String COLUMN_RB07 = "rb07";  // Pregnancy Status
        public static final String COLUMN_RB06 = "rb06";  // Marital Status
        public static final String COLUMN_MEMBERTYPE = "memberType";  // Marital Status
    }


    public static abstract class FPHouseholdTable implements BaseColumns {
        public static final String TABLE_NAME = "fphouseholds";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HDSSID = "hdssid";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_HOUSEHOLD_NO = "hhNo";
        public static final String COLUMN_FP_ROUND = "ROUND";
        public static final String COLUMN_MUID = "muid";
        public static final String COLUMN_STRUCTURE_NO = "structureNo";
        public static final String COLUMN_VISIT_NO = "visitNo";
        public static final String COLUMN_SA = "s1";


        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class EntryLogTable implements BaseColumns {
        public static final String TABLE_NAME = "EntryLog";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_ENTRY_DATE = "entryDate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_ENTRY_TYPE = "entry_type";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
        public static final String COLUMN_ISTATUS96x = "istatus96x";
    }
}
