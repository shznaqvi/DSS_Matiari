package edu.aku.hassannaqvi.dss_matiari.contracts;

import android.provider.BaseColumns;

public class TableContracts {

    public static abstract class HouseholdTable implements BaseColumns {
        public static final String TABLE_NAME = "households";
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


        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class MWRATable implements BaseColumns {
        public static final String TABLE_NAME = "MWRA";
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
        public static final String COLUMN_SB = "s2";


        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class UsersTable implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FULLNAME = "full_name";

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

        public static final String _ID = "id";
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
}
