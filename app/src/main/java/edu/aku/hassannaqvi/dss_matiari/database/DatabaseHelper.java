package edu.aku.hassannaqvi.dss_matiari.database;

import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_HOUSEHOLDS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_MWRA;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VERSIONAPP;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VILLAGES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableVillage;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.VersionTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.ZScoreTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.MWRA;
import edu.aku.hassannaqvi.dss_matiari.models.Users;
import edu.aku.hassannaqvi.dss_matiari.models.VersionApp;
import edu.aku.hassannaqvi.dss_matiari.models.Villages;
import edu.aku.hassannaqvi.dss_matiari.models.ZStandard;



/*import edu.aku.hassannaqvi.naunehal.models.Immunization;*/

/**
 * @author hassan.naqvi on 11/30/2016.
 * @update ali azaz on 01/07/21
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_HOUSEHOLDS);
        db.execSQL(SQL_CREATE_MWRA);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_VILLAGES);

//        db.execSQL(SQL_CREATE_ZSTANDARD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
        }
    }


    //Addition in DB
    public Long addHousehold(Households households) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HouseholdTable.COLUMN_PROJECT_NAME, households.getProjectName());
        values.put(HouseholdTable.COLUMN_UID, households.getUid());
        values.put(HouseholdTable.COLUMN_USERNAME, households.getUserName());
        values.put(HouseholdTable.COLUMN_SYSDATE, households.getSysDate());
        values.put(TableContracts.HouseholdTable.COLUMN_HDSSID, households.getHdssId());
        values.put(HouseholdTable.COLUMN_UC_CODE, households.getUcCode());
        values.put(HouseholdTable.COLUMN_VILLAGE_CODE, households.getVillageCode());
        values.put(HouseholdTable.COLUMN_HOUSEHOLD_NO, households.getHhNo());
        values.put(HouseholdTable.COLUMN_STRUCTURE_NO, households.getStructureNo());
        values.put(HouseholdTable.COLUMN_VISIT_NO, households.getVisitNo());
        values.put(TableContracts.HouseholdTable.COLUMN_ISTATUS, households.getiStatus());
        values.put(HouseholdTable.COLUMN_DEVICETAGID, households.getDeviceTag());
        values.put(HouseholdTable.COLUMN_DEVICEID, households.getDeviceId());
        values.put(HouseholdTable.COLUMN_APPVERSION, households.getAppver());
        values.put(HouseholdTable.COLUMN_SA, households.sAtoString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                HouseholdTable.TABLE_NAME,
                HouseholdTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMWRA(MWRA mwra) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MWRATable.COLUMN_PROJECT_NAME, mwra.getProjectName());
        values.put(MWRATable.COLUMN_UID, mwra.getUid());
        values.put(MWRATable.COLUMN_UUID, mwra.getUuid());
        values.put(MWRATable.COLUMN_USERNAME, mwra.getUserName());
        values.put(MWRATable.COLUMN_SYSDATE, mwra.getSysDate());
        values.put(MWRATable.COLUMN_HDSSID, mwra.getHdssId());
        values.put(MWRATable.COLUMN_UC_CODE, mwra.getUcCode());
        values.put(MWRATable.COLUMN_VILLAGE_CODE, mwra.getVillageCode());
        values.put(MWRATable.COLUMN_STRUCTURE_NO, mwra.getStructureNo());
        values.put(MWRATable.COLUMN_HOUSEHOLD_NO, mwra.getHhNo());
        values.put(MWRATable.COLUMN_SB, mwra.sBtoString());
        values.put(MWRATable.COLUMN_ISTATUS, mwra.getiStatus());
        values.put(MWRATable.COLUMN_DEVICETAGID, mwra.getDeviceTag());
        values.put(MWRATable.COLUMN_DEVICEID, mwra.getDeviceId());
        values.put(MWRATable.COLUMN_APPVERSION, mwra.getAppver());

        long newRowId;
        newRowId = db.insert(
                MWRATable.TABLE_NAME,
                MWRATable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    /*
     * Functions that dealing with table data
     * */
    public boolean doLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                UsersTable.COLUMN_ID,
                UsersTable.COLUMN_USERNAME,
                UsersTable.COLUMN_PASSWORD,
                UsersTable.COLUMN_FULLNAME,
        };
        String whereClause = UsersTable.COLUMN_USERNAME + "=? AND " + UsersTable.COLUMN_PASSWORD + "=?";
        String[] whereArgs = {username, password};
        String groupBy = null;
        String having = null;
        String orderBy = UsersTable.COLUMN_ID + " ASC";

        Users loggedInUser = null;
        try {
            c = db.query(
                    UsersTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                loggedInUser = new Users().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        MainApp.user = loggedInUser;
        return c.getCount() > 0;
    }


    public ArrayList<Households> getHouseholdsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                HouseholdTable._ID,
                HouseholdTable.COLUMN_UID,
                HouseholdTable.COLUMN_SYSDATE,
                TableContracts.HouseholdTable.COLUMN_USERNAME,
                HouseholdTable.COLUMN_ISTATUS,
                HouseholdTable.COLUMN_SYNCED,

        };
        String whereClause = HouseholdTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
        String groupBy = null;
        String having = null;
        String orderBy = HouseholdTable.COLUMN_ID + " ASC";
        ArrayList<Households> allHouseholds = new ArrayList<>();
        try {
            c = db.query(
                    TableContracts.HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Households households = new Households();
                households.setId(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_ID)));
                households.setUid(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_UID)));
                households.setSysDate(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_SYSDATE)));
                households.setUserName(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_USERNAME)));
                allHouseholds.add(households);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHouseholds;
    }

    public ArrayList<Households> getUnclosedHouseholds() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                HouseholdTable._ID,
                HouseholdTable.COLUMN_UID,
                TableContracts.HouseholdTable.COLUMN_SYSDATE,
                HouseholdTable.COLUMN_USERNAME,
                TableContracts.HouseholdTable.COLUMN_ISTATUS,
                HouseholdTable.COLUMN_SYNCED,
                HouseholdTable.COLUMN_VISIT_NO,
                HouseholdTable.COLUMN_STRUCTURE_NO,
                HouseholdTable.COLUMN_VILLAGE_CODE,
                TableContracts.HouseholdTable.COLUMN_UC_CODE,
                TableContracts.HouseholdTable.COLUMN_HOUSEHOLD_NO,

        };
        String whereClause = HouseholdTable.COLUMN_ISTATUS + " != ? AND "
                + TableContracts.HouseholdTable.COLUMN_VISIT_NO + " < ?";


        String[] whereArgs = {"1", "3"};
        String groupBy = null;
        String having = null;
        String orderBy = TableContracts.HouseholdTable.COLUMN_ID + " ASC";
        ArrayList<Households> allHouseholds = new ArrayList<>();
        try {
            c = db.query(
                    HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Households households = new Households();
                households.setId(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_ID)));
                households.setUid(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_UID)));
                households.setSysDate(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_SYSDATE)));
                households.setUserName(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_USERNAME)));
                households.setiStatus(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_ISTATUS)));
                households.setSynced(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_SYNCED)));
                households.setVisitNo(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_VISIT_NO)));
                households.setStructureNo(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_STRUCTURE_NO)));
                households.setVillageCode(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_VILLAGE_CODE)));
                households.setUcCode(c.getString(c.getColumnIndex(TableContracts.HouseholdTable.COLUMN_UC_CODE)));
                households.setHhNo(c.getString(c.getColumnIndex(HouseholdTable.COLUMN_HOUSEHOLD_NO)));
                allHouseholds.add(households);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHouseholds;
    }

    // istatus examples: (1) or (1,9) or (1,3,5)
  /*  public Households getFormByAssessNo(String assesNo, String istatus) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_ASSESMENT_NO + "=? AND " +
                HouseholdTable.COLUMN_ISTATUS + " in " + istatus;

        String[] whereArgs = {assesNo};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        Households allFC = null;
        try {
            c = db.query(
                    HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC = new Households().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }
*/
    public ArrayList<Cursor> getDatabaseManagerData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public int updatesHouseholdColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.HouseholdTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.households.getId())};

        return db.update(HouseholdTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesMWRAColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = MWRATable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.mwra.getId())};

        return db.update(MWRATable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


  /*  public int updateTemp(String assessNo, String temp) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(HouseholdTable.COLUMN_SYSDATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()) + "-Updated");
        values.put(HouseholdTable.COLUMN_ISTATUS, "1");
        values.put(HouseholdTable.COLUMN_SYNCED, (byte[]) null);

        String selection = HouseholdTable.COLUMN_ASSESMENT_NO + " =? AND " + HouseholdTable.COLUMN_ISTATUS + " =? ";
        // String selection = HouseholdTable.COLUMN_ASSESMENT_NO + " =? ";
        String[] selectionArgs = {assessNo, "9"};
        // String[] selectionArgs = {assessNo};

        return db.update(HouseholdTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }*/

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(HouseholdTable.COLUMN_ISTATUS, MainApp.households.getiStatus());

        // Which row to update, based on the ID
        String selection = HouseholdTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.households.getId())};

        return db.update(TableContracts.HouseholdTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionApp Vc = new VersionApp();
            Vc.sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                Users user = new Users();
                user.sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
                values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
                values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
                long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncVillage(JSONArray villageList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableVillage.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < villageList.length(); i++) {

                JSONObject jsonObjectVil = villageList.getJSONObject(i);

                Villages village = new Villages();
                village.Sync(jsonObjectVil);
                ContentValues values = new ContentValues();

                values.put(TableVillage.COLUMN_UCNAME, village.getUcname());
                values.put(TableVillage.COLUMN_VILLAGE_NAME, village.getVillagename());
                values.put(TableVillage.COLUMN_VILLAGE_CODE, village.getVillagecode());
                values.put(TableVillage.COLUMN_UC_CODE, village.getUccode());
                long rowID = db.insert(TableVillage.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncVillage(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public Collection<Villages> getVillage() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TableVillage.COLUMN_UCNAME,
                TableVillage.COLUMN_VILLAGE_NAME,
                TableVillage.COLUMN_VILLAGE_CODE,
                TableVillage.COLUMN_UC_CODE
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVillage.COLUMN_UCNAME + " ASC";

        Collection<Villages> allVil = new ArrayList<Villages>();
        try {
            c = db.query(
                    TableVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Villages vil = new Villages();
                allVil.add(vil.HydrateUc(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVil;
    }

    public Collection<Villages> getVillageUc() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + TableVillage.COLUMN_UCNAME,
                TableVillage.COLUMN_VILLAGE_CODE,
                TableVillage.COLUMN_UC_CODE,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = TableVillage.COLUMN_UCNAME;
        String having = null;

        String orderBy =
                TableVillage.COLUMN_UCNAME + " ASC";

        Collection<Villages> allVil = new ArrayList<Villages>();
        try {
            c = db.query(
                    TableVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Villages vil = new Villages();
                allVil.add(vil.HydrateUc(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVil;
    }

    //get UnSyncedTables
    public JSONArray getUnsyncedHouseholds() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = HouseholdTable.COLUMN_SYNCED + " is null AND (" +
                HouseholdTable.COLUMN_ISTATUS + " = 1 OR " +
                HouseholdTable.COLUMN_VISIT_NO + " > 2 ) ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.HouseholdTable.COLUMN_ID + " ASC";

        JSONArray allHouseholds = new JSONArray();
        try {
            c = db.query(
                    HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                /** WorkManager Upload
                 /*Households fc = new Households();
                 allFC.add(fc.Hydrate(c));*/
                Log.d(TAG, "getUnsyncedHouseholds: " + c.getCount());
                Households households = new Households();
                allHouseholds.put(households.Hydrate(c).toJSONObject());


            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "getUnsyncedHouseholds: " + allHouseholds.toString().length());
        Log.d(TAG, "getUnsyncedHouseholds: " + allHouseholds);
        return allHouseholds;
    }

    public JSONArray getUnsyncedMWRA() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = MWRATable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = MWRATable.COLUMN_ID + " ASC";

        JSONArray allMwra = new JSONArray();
        try {
            c = db.query(
                    MWRATable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Log.d(TAG, "getUnsyncedMWRA: " + c.getCount());
                MWRA mwra = new MWRA();
                allMwra.put(mwra.Hydrate(c).toJSONObject());
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "getUnsyncedMWRA: " + allMwra.toString().length());
        Log.d(TAG, "getUnsyncedMWRA: " + allMwra);
        return allMwra;
    }


    //update SyncedTables
    public void updateSyncedhouseholds(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(HouseholdTable.COLUMN_SYNCED, true);
        values.put(HouseholdTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = HouseholdTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                HouseholdTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedMWRA(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(MWRATable.COLUMN_SYNCED, true);
        values.put(MWRATable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = MWRATable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                MWRATable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public Collection<Villages> getVillageByUc(String ucCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TableVillage.COLUMN_VILLAGE_NAME,
                TableVillage.COLUMN_VILLAGE_CODE
        };

        String whereClause = TableVillage.COLUMN_UC_CODE + "=?";
        String[] whereArgs = new String[]{ucCode};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVillage.COLUMN_VILLAGE_NAME + " ASC";

        Collection<Villages> allVil = new ArrayList<Villages>();
        try {
            c = db.query(
                    TableVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Villages vil = new Villages();
                allVil.add(vil.HydrateVil(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVil;
    }

    public List<String> getLMS(int age, int gender, String catA, String catB) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "getLMS: " + age + " | " + gender + " | " + catA + " | " + catB);
        Cursor c = db.rawQuery("SELECT l,m,s " +
                        "FROM " + ZScoreTable.TABLE_NAME + " " +
                        "WHERE " + ZScoreTable.COLUMN_AGE + "=? " +
                        "AND "
                        + ZScoreTable.COLUMN_SEX + "=?" +
                        "AND "
                        + ZScoreTable.COLUMN_CAT + " IN (?,?)"
                ,
                new String[]{String.valueOf(age), String.valueOf(gender), catA, catB});
        List<String> lms = null;
        while (c.moveToNext()) {
            lms = new ArrayList<>();
            lms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_L)));
            Log.d(TAG, "getLMS: L -> " + c.getString(c.getColumnIndex(ZScoreTable.COLUMN_L)));
            lms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_M)));
            Log.d(TAG, "getLMS: M -> " + c.getString(c.getColumnIndex(ZScoreTable.COLUMN_M)));
            lms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_S)));
            Log.d(TAG, "getLMS: S -> " + c.getString(c.getColumnIndex(ZScoreTable.COLUMN_S)));

        }
        return lms;
    }

    public List<String> getWHLMS(Double height, int gender, String catA) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT l,m,s " +
                        "FROM " + ZScoreTable.TABLE_NAME +
                        " WHERE " + ZScoreTable.COLUMN_MEASURE + "=?" +
                        " AND " + ZScoreTable.COLUMN_SEX + "=?" +
                        " AND " + ZScoreTable.COLUMN_CAT + "=?"
                ,
                new String[]{String.valueOf(height), String.valueOf(gender), catA});
        List<String> whlms = new ArrayList<>();
        Log.d(TAG, "getWHLMS: height " + height);
        Log.d(TAG, "getWHLMS: " + c.getCount());
        while (c.moveToNext()) {
            whlms = new ArrayList<>();
            whlms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_L)));
            whlms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_M)));
            whlms.add(c.getString(c.getColumnIndex(ZScoreTable.COLUMN_S)));

        }
        c.close();
        return whlms;
    }


    public int syncZStandard(JSONArray zsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ZScoreTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < zsList.length(); i++) {

                JSONObject jsonObjectzs = zsList.getJSONObject(i);

                ZStandard Zstandard = new ZStandard();
                Zstandard.Sync(jsonObjectzs);
                ContentValues values = new ContentValues();

                values.put(ZScoreTable.COLUMN_SEX, Zstandard.getSex());
                values.put(ZScoreTable.COLUMN_AGE, Zstandard.getAge());
                values.put(ZScoreTable.COLUMN_MEASURE, Zstandard.getMeasure());
                values.put(ZScoreTable.COLUMN_L, Zstandard.getL());
                values.put(ZScoreTable.COLUMN_M, Zstandard.getM());
                values.put(ZScoreTable.COLUMN_S, Zstandard.getS());
                values.put(ZScoreTable.COLUMN_CAT, Zstandard.getCat());
                long rowID = db.insert(ZScoreTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncZStandard(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public Households getHouseholdByHDSSID(String hdssid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_HDSSID + "=?";

        String[] whereArgs = {hdssid};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        Households households = null;
        try {
            c = db.query(
                    TableContracts.HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                households = new Households().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return households;
    }

    public Households getHouseholdByUID(String uid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_UID + "=?";

        String[] whereArgs = {uid};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        Households households = null;
        try {
            c = db.query(
                    HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                households = new Households().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return households;
    }


    public List<MWRA> getAllMWRAByHH(String village, String structure, String hhNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = MWRATable.COLUMN_VILLAGE_CODE + "=? AND "
                + MWRATable.COLUMN_STRUCTURE_NO + "=? AND "
                + MWRATable.COLUMN_HOUSEHOLD_NO + "=? ";

        String[] whereArgs = {village, structure, hhNo};

        String groupBy = null;
        String having = null;

        String orderBy = MWRATable.COLUMN_ID + " ASC";

        ArrayList<MWRA> mwraByHH = new ArrayList<>();
        try {
            c = db.query(
                    MWRATable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MWRA mwra = new MWRA().Hydrate(c);

                mwraByHH.add(mwra);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return mwraByHH;
    }

    public int getMaxStructure(String vCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + HouseholdTable.COLUMN_STRUCTURE_NO + ") AS " + HouseholdTable.COLUMN_STRUCTURE_NO +
                        " FROM " + TableContracts.HouseholdTable.TABLE_NAME +
                        " WHERE " + HouseholdTable.COLUMN_VILLAGE_CODE + "=? " +
                        " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE
                ,
                new String[]{vCode});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndex(HouseholdTable.COLUMN_STRUCTURE_NO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public int getMaxHHNo(String vCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + HouseholdTable.COLUMN_HOUSEHOLD_NO + ") AS " + HouseholdTable.COLUMN_HOUSEHOLD_NO +
                        " FROM " + HouseholdTable.TABLE_NAME +
                        " WHERE " + HouseholdTable.COLUMN_VILLAGE_CODE + "=?" +
                        " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE
                ,
                new String[]{vCode});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndex(HouseholdTable.COLUMN_HOUSEHOLD_NO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public List<Households> getHouseholdBYStructure(String village, String structure) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_VILLAGE_CODE + "=? AND " + HouseholdTable.COLUMN_STRUCTURE_NO + "=? ";

        String[] whereArgs = {village, structure};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        ArrayList<Households> householdByHH = new ArrayList<>();
        try {
            c = db.query(
                    HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Households household = new Households().Hydrate(c);

                householdByHH.add(household);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return householdByHH;
    }

    public List<Households> getHouseholdBYVillage(String village) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_VILLAGE_CODE + "=? ";

        String[] whereArgs = {village};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        ArrayList<Households> householdByHH = new ArrayList<>();
        try {
            c = db.query(
                    TableContracts.HouseholdTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Households household = new Households().Hydrate(c);

                householdByHH.add(household);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return householdByHH;
    }

    public int getMWRACountBYUUID(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT Count(*) AS mwraCount" +

                        " FROM " + MWRATable.TABLE_NAME +
                        " WHERE " + MWRATable.COLUMN_UUID + " =? "

                ,
                new String[]{uid});
        float maxHHno = 0;
        int mwraCount = 0;
        while (c.moveToNext()) {
            mwraCount = c.getInt(c.getColumnIndex("mwraCount"));
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return mwraCount;
    }

    public Collection<Users> getTeamleaders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * " +

                        " FROM " + UsersTable.TABLE_NAME +
                        " WHERE " + UsersTable.COLUMN_USERNAME + " like '%team%' ", null);
        ArrayList<Users> users = new ArrayList<>();
        while (c.moveToNext()) {
            Users user = new Users().hydrate(c);

            users.add(user);
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return users;
    }

    /*public VersionApp getAppDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + VersionTable.COLUMN_UCNAME,
                TableVillage.COLUMN_VILLAGE_CODE,
                TableVillage.COLUMN_UC_CODE,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = TableVillage.COLUMN_UCNAME;
        String having = null;

        String orderBy =
                TableVillage.COLUMN_UCNAME + " ASC";

        Collection<Villages> allVil = new ArrayList<Villages>();
        try {
            c = db.query(
                    TableVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Villages vil = new Villages();
                allVil.add(vil.HydrateUc(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVil;
    }*/
}