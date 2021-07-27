package edu.aku.hassannaqvi.dss_matiari.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FormsTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableVillage;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.VersionTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.ZScoreTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.Form;
import edu.aku.hassannaqvi.dss_matiari.models.MWRA;
import edu.aku.hassannaqvi.dss_matiari.models.Users;
import edu.aku.hassannaqvi.dss_matiari.models.VersionApp;
import edu.aku.hassannaqvi.dss_matiari.models.Villages;
import edu.aku.hassannaqvi.dss_matiari.models.ZStandard;

import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_MWRA;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VERSIONAPP;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VILLAGES;



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
        db.execSQL(SQL_CREATE_FORMS);
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
    public Long addForm(Form form) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, form.getProjectName());
        values.put(FormsTable.COLUMN_UID, form.getUid());
        values.put(FormsTable.COLUMN_USERNAME, form.getUserName());
        values.put(FormsTable.COLUMN_SYSDATE, form.getSysDate());
        values.put(FormsTable.COLUMN_ASSESMENT_NO, form.getAssessNo());
        values.put(FormsTable.COLUMN_HDSSID, form.getHdssId());
        values.put(FormsTable.COLUMN_INFANT_NAME, form.getInfantName());
//        values.put(FormsTable.COLUMN_TSF305, form.getTsf305());
        values.put(FormsTable.COLUMN_S1, form.getS1());
        values.put(FormsTable.COLUMN_S2, form.getS2());
        values.put(FormsTable.COLUMN_S3, form.getS3());
        values.put(FormsTable.COLUMN_S4, form.getS4());
        values.put(FormsTable.COLUMN_S5, form.getS5());
        values.put(FormsTable.COLUMN_ISTATUS, form.getiStatus());

        values.put(FormsTable.COLUMN_DEVICETAGID, form.getDeviceTag());
        values.put(FormsTable.COLUMN_DEVICEID, form.getDeviceId());
        values.put(FormsTable.COLUMN_APPVERSION, form.getAppver());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMWRA(MWRA mwra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MWRATable.COLUMN_PROJECT_NAME, mwra.getProjectName());
        values.put(MWRATable.COLUMN_UID, mwra.getUuid());
        values.put(MWRATable.COLUMN_UUID, mwra.getUid());
        values.put(MWRATable.COLUMN_USERNAME, mwra.getUserName());
        values.put(MWRATable.COLUMN_SYSDATE, mwra.getSysDate());
        values.put(MWRATable.COLUMN_ASSESMENT_NO, mwra.getAssessNo());
        values.put(MWRATable.COLUMN_HDSSID, mwra.getHdssId());
        values.put(MWRATable.COLUMN_S1, mwra.getS1());
        values.put(MWRATable.COLUMN_S2, mwra.getS2());
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


    public ArrayList<Form> getFormsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_USERNAME,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        ArrayList<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form forms = new Form();
                forms.setId(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                forms.setUid(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                forms.setSysDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                forms.setUserName(c.getString(c.getColumnIndex(FormsTable.COLUMN_USERNAME)));
                allForms.add(forms);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }


    // istatus examples: (1) or (1,9) or (1,3,5)
    public Form getFormByAssessNo(String assesNo, String istatus) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormsTable.COLUMN_ASSESMENT_NO + "=? AND " +
                FormsTable.COLUMN_ISTATUS + " in " + istatus;

        String[] whereArgs = {assesNo};

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        Form allFC = null;
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC = new Form().Hydrate(c);
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


    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.getId())};

        return db.update(FormsTable.TABLE_NAME,
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


    public int updateTemp(String assessNo, String temp) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_TSF305, temp);
        values.put(FormsTable.COLUMN_SYSDATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()) + "-Updated");
        values.put(FormsTable.COLUMN_ISTATUS, "1");
        values.put(FormsTable.COLUMN_SYNCED, (byte[]) null);

        String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? AND " + FormsTable.COLUMN_ISTATUS + " =? ";
        // String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? ";
        String[] selectionArgs = {assessNo, "9"};
        // String[] selectionArgs = {assessNo};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, MainApp.form.getiStatus());

        // Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.getId())};

        return db.update(FormsTable.TABLE_NAME,
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
    public JSONArray getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = FormsTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        JSONArray allForms = new JSONArray();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                /** WorkManager Upload
                 /*Form fc = new Form();
                 allFC.add(fc.Hydrate(c));*/
                Log.d(TAG, "getUnsyncedForms: " + c.getCount());
                Form form = new Form();
                allForms.put(form.Hydrate(c).toJSONObject());


            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "getUnsyncedForms: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedForms: " + allForms);
        return allForms;
    }

    public JSONArray getUnsyncedMWRA() {
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
    public void updateSyncedforms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
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

    public Form getFormByHDSSID(String hdssid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormsTable.COLUMN_HDSSID + "=?";

        String[] whereArgs = {hdssid};

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        Form form = null;
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                form = new Form().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return form;
    }

    public List<MWRA> getMWRABYHDSSID(String hdssid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = MWRATable.COLUMN_HDSSID + "=?";

        String[] whereArgs = {hdssid};

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
}