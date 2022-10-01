package edu.aku.hassannaqvi.dss_matiari.database;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.IBAHC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcomeFollowups;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_DOB;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_GENDER;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_MEMBER_TYPE;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_MUID;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_MUID_FPHOUSEHOLDS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_RA01;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_ADD_RC15;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_ALTER_FOLLOWUPSCHE;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_FOLLOWUPS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_FOLLOWUPSCHE;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_FP_HOUSEHOLDS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_HOUSEHOLDS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_MWRA;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_OUTCOME;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_OUTCOME_FOLLOWUPS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_PREGNANCY;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VERSIONAPP;
import static edu.aku.hassannaqvi.dss_matiari.database.CreateTable.SQL_CREATE_VILLAGES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FPHouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FollowupsTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MaxHhnoTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.OutcomeTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableFollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableVillage;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.VersionTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.ZScoreTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.FPHouseholds;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.models.Followups;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.MWRA;
import edu.aku.hassannaqvi.dss_matiari.models.MaxHhno;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.models.OutcomeFollowups;
import edu.aku.hassannaqvi.dss_matiari.models.Pregnancy;
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
    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    public static final String DATABASE_COPY = PROJECT_NAME + "_copy.db";
    public static final String DATABASE_COPY2 = PROJECT_NAME + "_copy.db";
    public static final String DATABASE_PASSWORD = IBAHC;
    private final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 4;
    private static final String SQL_DELETE_OUTCOME_FOLLOWUPS = "DROP TABLE IF EXISTS " + TableContracts.OutcomeFollowupTable.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate(users): " + SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_HOUSEHOLDS);
        db.execSQL(SQL_CREATE_FP_HOUSEHOLDS);
        db.execSQL(CreateTable.SQL_CREATE_MAXHHNO);
        db.execSQL(SQL_CREATE_MWRA);
        db.execSQL(SQL_CREATE_FOLLOWUPS);
        db.execSQL(SQL_CREATE_PREGNANCY);
        db.execSQL(SQL_CREATE_OUTCOME);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_VILLAGES);
        db.execSQL(SQL_CREATE_FOLLOWUPSCHE);
        db.execSQL(SQL_CREATE_OUTCOME_FOLLOWUPS);


//        db.execSQL(SQL_CREATE_ZSTANDARD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("GUL VERSIONS", "Old " + oldVersion + " -- New " + newVersion);
        try {
            switch (oldVersion) {
                case 1:
                    db.execSQL(SQL_ALTER_FOLLOWUPSCHE);

                case 2:
                    executeSafeQuery(db, SQL_ALTER_ADD_DOB);
                    executeSafeQuery(db, SQL_ALTER_ADD_GENDER);
                    executeSafeQuery(db, SQL_ALTER_ADD_MEMBER_TYPE);
                    executeSafeQuery(db, SQL_CREATE_OUTCOME_FOLLOWUPS);
                    executeSafeQuery(db, SQL_ALTER_ADD_MUID);
                    executeSafeQuery(db, SQL_ALTER_ADD_RA01);
                    executeSafeQuery(db, SQL_ALTER_ADD_RC15);

                case 3:
                    executeSafeQuery(db, SQL_ALTER_ADD_MUID_FPHOUSEHOLDS);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void executeSafeQuery(SQLiteDatabase db, String query) {
        try {
            db.execSQL(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Addition in DB
    public Long addHousehold(Households households) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HouseholdTable.COLUMN_PROJECT_NAME, households.getProjectName());
        values.put(HouseholdTable.COLUMN_UID, households.getUid());
        values.put(HouseholdTable.COLUMN_USERNAME, households.getUserName());
        values.put(HouseholdTable.COLUMN_SYSDATE, households.getSysDate());
        values.put(HouseholdTable.COLUMN_HDSSID, households.getHdssId());
        values.put(HouseholdTable.COLUMN_UC_CODE, households.getUcCode());
        values.put(HouseholdTable.COLUMN_VILLAGE_CODE, households.getVillageCode());
        values.put(HouseholdTable.COLUMN_HOUSEHOLD_NO, households.getHhNo());
        values.put(HouseholdTable.COLUMN_STRUCTURE_NO, households.getStructureNo());
        values.put(HouseholdTable.COLUMN_VISIT_NO, households.getVisitNo());
        values.put(HouseholdTable.COLUMN_ISTATUS, households.getiStatus());
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
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
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
        values.put(MWRATable.COLUMN_SNO, mwra.getsNo());
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

    public Long addFpHousehold(FPHouseholds fpHouseholds) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FPHouseholdTable.COLUMN_PROJECT_NAME, fpHouseholds.getProjectName());
        values.put(FPHouseholdTable.COLUMN_UID, fpHouseholds.getUid());
        values.put(FPHouseholdTable.COLUMN_USERNAME, fpHouseholds.getUserName());
        values.put(FPHouseholdTable.COLUMN_SYSDATE, fpHouseholds.getSysDate());
        values.put(FPHouseholdTable.COLUMN_HDSSID, fpHouseholds.getHdssId());
        values.put(FPHouseholdTable.COLUMN_UC_CODE, fpHouseholds.getUcCode());
        values.put(FPHouseholdTable.COLUMN_VILLAGE_CODE, fpHouseholds.getVillageCode());
        values.put(FPHouseholdTable.COLUMN_HOUSEHOLD_NO, fpHouseholds.getHhNo());
        values.put(FPHouseholdTable.COLUMN_ISTATUS, fpHouseholds.getiStatus());
        values.put(FPHouseholdTable.COLUMN_DEVICETAGID, fpHouseholds.getDeviceTag());
        values.put(FPHouseholdTable.COLUMN_DEVICEID, fpHouseholds.getDeviceId());
        values.put(FPHouseholdTable.COLUMN_APPVERSION, fpHouseholds.getAppver());
        values.put(FPHouseholdTable.COLUMN_FP_ROUND, fpHouseholds.getFround());
        values.put(FPHouseholdTable.COLUMN_MUID, fpHouseholds.getMuid());
        values.put(FPHouseholdTable.COLUMN_VISIT_NO, fpHouseholds.getVisitNo());

        long newRowId;
        newRowId = db.insert(
                FPHouseholdTable.TABLE_NAME,
                FPHouseholdTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addFollowup(Followups followups) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FollowupsTable.COLUMN_PROJECT_NAME, followups.getProjectName());
        values.put(FollowupsTable.COLUMN_UID, followups.getUid());
        values.put(FollowupsTable.COLUMN_UUID, followups.getUuid());
        values.put(FollowupsTable.COLUMN_FMUID, followups.getFmuid());
        values.put(FollowupsTable.COLUMN_USERNAME, followups.getUserName());
        values.put(FollowupsTable.COLUMN_SYSDATE, followups.getSysDate());
        values.put(FollowupsTable.COLUMN_HDSSID, followups.getHdssId());
        values.put(FollowupsTable.COLUMN_UC_CODE, followups.getUcCode());
        values.put(FollowupsTable.COLUMN_VILLAGE_CODE, followups.getVillageCode());
        values.put(FollowupsTable.COLUMN_SNO, followups.getSno());
        values.put(FollowupsTable.COLUMN_FP_ROUND, followups.getfRound());
        values.put(FollowupsTable.COLUMN_HOUSEHOLD_NO, followups.getHhNo());
        values.put(FollowupsTable.COLUMN_VISIT_NO, followups.getVisitNo());
        values.put(FollowupsTable.COLUMN_SC, followups.sCtoString());
        //values.put(FollowupsTable.COLUMN_SD, followups.sDtoString());
        values.put(FollowupsTable.COLUMN_ISTATUS, followups.getiStatus());
        values.put(FollowupsTable.COLUMN_DEVICETAGID, followups.getDeviceTag());
        values.put(FollowupsTable.COLUMN_DEVICEID, followups.getDeviceId());
        values.put(FollowupsTable.COLUMN_APPVERSION, followups.getAppver());

        long newRowId;
        newRowId = db.insert(
                FollowupsTable.TABLE_NAME,
                FollowupsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Long addOutcomeFollowup(OutcomeFollowups followups) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_PROJECT_NAME, followups.getProjectName());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_UID, followups.getUid());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_UUID, followups.getUuid());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_MUID, followups.getMuid());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_MSNO, followups.getMsno());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_USERNAME, followups.getUserName());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_SYSDATE, followups.getSysDate());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_HDSSID, followups.getHdssId());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_UC_CODE, followups.getUcCode());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_VILLAGE_CODE, followups.getVillageCode());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_SNO, followups.getSno());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_FP_ROUND, followups.getRound());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_HOUSEHOLD_NO, followups.getHhNo());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_VISIT_NO, followups.getVisitNo());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_SE, followups.sEtoString());
        //values.put(FollowupsTable.COLUMN_SD, followups.sDtoString());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_ISTATUS, followups.getiStatus());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_DEVICETAGID, followups.getDeviceTag());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_DEVICEID, followups.getDeviceId());
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_APPVERSION, followups.getAppver());

        long newRowId;
        newRowId = db.insert(
                TableContracts.OutcomeFollowupTable.TABLE_NAME,
                TableContracts.OutcomeFollowupTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addPregnancy(Pregnancy pregnancy) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.PregnancyTable.COLUMN_PROJECT_NAME, pregnancy.getProjectName());
        values.put(TableContracts.PregnancyTable.COLUMN_UID, pregnancy.getUid());
        values.put(TableContracts.PregnancyTable.COLUMN_UUID, pregnancy.getUuid());
        values.put(TableContracts.PregnancyTable.COLUMN_USERNAME, pregnancy.getUserName());
        values.put(TableContracts.PregnancyTable.COLUMN_SYSDATE, pregnancy.getSysDate());
        values.put(TableContracts.PregnancyTable.COLUMN_HDSSID, pregnancy.getHdssId());
        values.put(TableContracts.PregnancyTable.COLUMN_UC_CODE, pregnancy.getUcCode());
        values.put(TableContracts.PregnancyTable.COLUMN_VILLAGE_CODE, pregnancy.getVillageCode());
        values.put(TableContracts.PregnancyTable.COLUMN_STRUCTURE_NO, pregnancy.getStructureNo());
        values.put(TableContracts.PregnancyTable.COLUMN_HOUSEHOLD_NO, pregnancy.getHhNo());
        values.put(TableContracts.PregnancyTable.COLUMN_SD, pregnancy.sDtoString());
        values.put(TableContracts.PregnancyTable.COLUMN_ISTATUS, pregnancy.getiStatus());
        values.put(TableContracts.PregnancyTable.COLUMN_DEVICETAGID, pregnancy.getDeviceTag());
        values.put(TableContracts.PregnancyTable.COLUMN_DEVICEID, pregnancy.getDeviceId());
        values.put(TableContracts.PregnancyTable.COLUMN_APPVERSION, pregnancy.getAppver());

        long newRowId;
        newRowId = db.insert(
                TableContracts.PregnancyTable.TABLE_NAME,
                TableContracts.PregnancyTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addOutcome(Outcome outcome) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.OutcomeTable.COLUMN_PROJECT_NAME, outcome.getProjectName());
        values.put(TableContracts.OutcomeTable.COLUMN_UID, outcome.getUid());
        values.put(TableContracts.OutcomeTable.COLUMN_UUID, outcome.getUuid());
        values.put(OutcomeTable.COLUMN_MUID, outcome.getMuid());
        values.put(OutcomeTable.COLUMN_MSNO, outcome.getMsno());
        values.put(TableContracts.OutcomeTable.COLUMN_USERNAME, outcome.getUserName());
        values.put(TableContracts.OutcomeTable.COLUMN_SYSDATE, outcome.getSysDate());
        values.put(TableContracts.OutcomeTable.COLUMN_HDSSID, outcome.getHdssId());
        values.put(TableContracts.OutcomeTable.COLUMN_UC_CODE, outcome.getUcCode());
        values.put(TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE, outcome.getVillageCode());
        values.put(TableContracts.OutcomeTable.COLUMN_SNO, outcome.getSno());
        values.put(TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO, outcome.getHhNo());
        values.put(TableContracts.OutcomeTable.COLUMN_SE, outcome.sEtoString());
        values.put(TableContracts.OutcomeTable.COLUMN_ISTATUS, outcome.getiStatus());
        values.put(TableContracts.OutcomeTable.COLUMN_DEVICETAGID, outcome.getDeviceTag());
        values.put(TableContracts.OutcomeTable.COLUMN_DEVICEID, outcome.getDeviceId());
        values.put(TableContracts.OutcomeTable.COLUMN_APPVERSION, outcome.getAppver());

        long newRowId;
        newRowId = db.insert(
                TableContracts.OutcomeTable.TABLE_NAME,
                TableContracts.OutcomeTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    /*
     * Functions that dealing with table data
     * */
    public boolean doLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = UsersTable.COLUMN_USERNAME + "=? AND " + UsersTable.COLUMN_PASSWORD + "=?";
        String[] whereArgs = {username, password};
        String groupBy = null;
        String having = null;
        String orderBy = UsersTable.COLUMN_ID + " ASC";

        Users loggedInUser = null;
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

        c.close();

        db.close();

        MainApp.user = loggedInUser;
        return c.getCount() > 0;
    }


    public ArrayList<Households> getHouseholdsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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
            households.setId(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_ID)));
            households.setUid(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_UID)));
            households.setSysDate(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_SYSDATE)));
            households.setUserName(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_USERNAME)));
            allHouseholds.add(households);
        }

        c.close();

        db.close();

        return allHouseholds;
    }

    public ArrayList<Households> getUnclosedHouseholds() {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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
            households.setId(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_ID)));
            households.setUid(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_UID)));
            households.setSysDate(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_SYSDATE)));
            households.setUserName(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_USERNAME)));
            households.setiStatus(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_ISTATUS)));
            households.setSynced(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_SYNCED)));
            households.setVisitNo(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_VISIT_NO)));
            households.setStructureNo(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_STRUCTURE_NO)));
            households.setVillageCode(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_VILLAGE_CODE)));
            households.setUcCode(c.getString(c.getColumnIndexOrThrow(TableContracts.HouseholdTable.COLUMN_UC_CODE)));
            households.setHhNo(c.getString(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_HOUSEHOLD_NO)));
            allHouseholds.add(households);
        }

        c.close();

        db.close();

        return allHouseholds;
    }

    // istatus examples: (1) or (1,9) or (1,3,5)
  /*  public Households getFormByAssessNo(String assesNo, String istatus) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
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
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

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
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = MWRATable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.mwra.getId())};

        return db.update(MWRATable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesFollowUpsScheColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableFollowUpsSche.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fpMwra.getId())};

        return db.update(TableFollowUpsSche.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesFollowUpsColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FollowupsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.followups.getId())};

        return db.update(FollowupsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateOutcomeFollouwps(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.OutcomeFollowupTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(outcomeFollowups.getId())};

        return db.update(TableContracts.OutcomeFollowupTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesFPHouseholdsColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FPHouseholdTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fpHouseholds.getId())};

        return db.update(FPHouseholdTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

/*    public int updatesPregnancyColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.PregnancyTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.pregnancy.getId())};

        return db.update(TableContracts.PregnancyTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }*/

    public int updatesOutcomeColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.OutcomeTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.outcome.getId())};

        return db.update(TableContracts.OutcomeTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


  /*  public int updateTemp(String assessNo, String temp) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

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
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

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


    public int syncVersionApp(JSONObject VersionList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(VersionTable.TABLE_NAME, null, null);
        long count = 0;
        JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionTable.COLUMN_VERSION_PATH)).getJSONObject(0);
        VersionApp Vc = new VersionApp();
        Vc.sync(jsonObjectCC);

        ContentValues values = new ContentValues();

        values.put(VersionTable.COLUMN_PATH_NAME, Vc.getPathname());
        values.put(VersionTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
        values.put(VersionTable.COLUMN_VERSION_NAME, Vc.getVersionname());

        count = db.insert(VersionTable.TABLE_NAME, null, values);
        if (count > 0) count = 1;


        db.close();


        return (int) count;
    }

    public int syncMaxHhno(JSONArray maxHhnoList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(MaxHhnoTable.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < maxHhnoList.length(); i++) {

            JSONObject json = maxHhnoList.getJSONObject(i);

            MaxHhno maxHhno = new MaxHhno().sync(json);
            ContentValues values = new ContentValues();

            values.put(MaxHhnoTable.COLUMN_UC_CODE, maxHhno.getUccode());
            values.put(MaxHhnoTable.COLUMN_VILLAGE_CODE, maxHhno.getVillageCode());
            values.put(MaxHhnoTable.COLUMN_MAX_HHNO, maxHhno.getMaxHhno());

            long rowID = db.insert(MaxHhnoTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }

        db.close();

        return insertCount;
    }

    public int syncUser(JSONArray userList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < userList.length(); i++) {

            JSONObject jsonObjectUser = userList.getJSONObject(i);

            Users user = new Users();
            user.sync(jsonObjectUser);
            ContentValues values = new ContentValues();

            values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
            values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
            values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
            values.put(UsersTable.COLUMN_DESIGNATION, user.getDesignation());
            long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }


        db.close();

        return insertCount;
    }

    public int syncVillage(JSONArray villageList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableVillage.TABLE_NAME, null, null);
        int insertCount = 0;
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


        db.close();

        return insertCount;
    }

    public int syncFollowUpsSche(JSONArray followupsList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableFollowUpsSche.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < followupsList.length(); i++) {

            JSONObject jsonObjectVil = followupsList.getJSONObject(i);

            FollowUpsSche followUpsSche = new FollowUpsSche();
            followUpsSche.Sync(jsonObjectVil);
            ContentValues values = new ContentValues();

            values.put(TableFollowUpsSche.COLUMN_UC_CODE, followUpsSche.getUcCode());
            values.put(TableFollowUpsSche.COLUMN_VILLAGE_CODE, followUpsSche.getVillageCode());
            values.put(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO, followUpsSche.getHhNo());
            values.put(TableFollowUpsSche.COLUMN_HDSSID, followUpsSche.getHdssid());
            values.put(TableFollowUpsSche.COLUMN_MUID, followUpsSche.getMuid());
            values.put(TableFollowUpsSche.COLUMN_RA01, getDateFromJson(followUpsSche.getRa01()));
            values.put(TableFollowUpsSche.COLUMN_RA08, followUpsSche.getRa08());
            values.put(TableFollowUpsSche.COLUMN_RA14, followUpsSche.getRa14());
            values.put(TableFollowUpsSche.COLUMN_RA18, followUpsSche.getRa18());
            values.put(TableFollowUpsSche.COLUMN_FROUND, followUpsSche.getfRound());
            values.put(TableFollowUpsSche.COLUMN_DONE_DATE, followUpsSche.getfpDoneDt());
            values.put(TableFollowUpsSche.COLUMN_ISTATUS, followUpsSche.getiStatus());
            values.put(TableFollowUpsSche.COLUMN_RB01, followUpsSche.getRb01());
            values.put(TableFollowUpsSche.COLUMN_RB02, followUpsSche.getRb02());
            values.put(TableFollowUpsSche.COLUMN_RB03, followUpsSche.getRb03());
            values.put(TableFollowUpsSche.COLUMN_RB04, followUpsSche.getRb04());
            values.put(TableFollowUpsSche.COLUMN_RC12, followUpsSche.getRc12());
            values.put(TableFollowUpsSche.COLUMN_RC15, followUpsSche.getRc15());
            values.put(TableFollowUpsSche.COLUMN_RB05, followUpsSche.getRb05());
            values.put(TableFollowUpsSche.COLUMN_RB07, followUpsSche.getRb07());
            values.put(TableFollowUpsSche.COLUMN_RB06, followUpsSche.getRb06());
            values.put(TableFollowUpsSche.COLUMN_MEMBERTYPE, followUpsSche.getMemberType());

            long rowID = db.insert(TableFollowUpsSche.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }


        db.close();

        return insertCount;
    }

    public boolean isDateInJsonFormat(String date) {
        try {
            JSONObject dateObj = new JSONObject(date);
            return true;
        } catch (JSONException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public String getDateFromJson(String jsonStr) throws JSONException {
        String date = jsonStr;
        if (isDateInJsonFormat(jsonStr)) {
            JSONObject dateObj = new JSONObject(jsonStr);
            String ra01Date = dateObj.getString("date");
            String[] dateParts = ra01Date.split(" ");
            date = dateParts[0];
        }

        return date;
    }


    public Collection<Villages> getVillage() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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

        c.close();

        db.close();

        return allVil;
    }

    public Collection<Villages> getVillageUc() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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

        c.close();

        db.close();

        return allVil;
    }

    //get UnSyncedTables
    public JSONArray getUnsyncedHouseholds() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedHouseholds: " + allHouseholds.toString().length());
        Log.d(TAG, "getUnsyncedHouseholds: " + allHouseholds);
        return allHouseholds;
    }


    public JSONArray getUnsyncedFPHouseholds() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = TableContracts.FPHouseholdTable.COLUMN_SYNCED + " is null AND (" +
                TableContracts.FPHouseholdTable.COLUMN_ISTATUS + " = 1 OR " +
                TableContracts.FPHouseholdTable.COLUMN_VISIT_NO + " > 2 ) ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.FPHouseholdTable.COLUMN_ID + " ASC";

        JSONArray allHouseholds = new JSONArray();
        c = db.query(
                TableContracts.FPHouseholdTable.TABLE_NAME,  // The table to query
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
            Log.d(TAG, "getUnsyncedFPHouseholds: " + c.getCount());
            FPHouseholds households = new FPHouseholds();
            allHouseholds.put(households.Hydrate(c).toJSONObject());


        }

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedFPHouseholds: " + allHouseholds.toString().length());
        Log.d(TAG, "getUnsyncedFPHouseholds: " + allHouseholds);
        return allHouseholds;
    }


    public JSONArray getUnsyncedOutcomes() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = OutcomeTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = OutcomeTable.COLUMN_ID + " ASC";

        JSONArray allOutcome = new JSONArray();
        c = db.query(
                OutcomeTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedOutcome: " + c.getCount());
            Outcome outcome = new Outcome();
            allOutcome.put(outcome.Hydrate(c).toJSONObject());
        }

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedOutcome: " + allOutcome.toString().length());
        Log.d(TAG, "getUnsyncedOutcome: " + allOutcome);
        return allOutcome;
    }


    public JSONArray getUnsyncedMWRA() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = MWRATable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = MWRATable.COLUMN_ID + " ASC";

        JSONArray allMwra = new JSONArray();
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

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedMWRA: " + allMwra.toString().length());
        Log.d(TAG, "getUnsyncedMWRA: " + allMwra);
        return allMwra;
    }

    public JSONArray getUnsyncedFollowups() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = FollowupsTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FollowupsTable.COLUMN_ID + " ASC";

        JSONArray allFollowups = new JSONArray();
        c = db.query(
                FollowupsTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedFollowups: " + c.getCount());
            Followups Followups = new Followups();
            allFollowups.put(Followups.Hydrate(c).toJSONObject());
        }

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedFollowups: " + allFollowups.toString().length());
        Log.d(TAG, "getUnsyncedFollowups: " + allFollowups);
        return allFollowups;
    }

    public JSONArray getUnsyncedOutcomeFollowups() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = TableContracts.OutcomeFollowupTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.OutcomeFollowupTable.COLUMN_ID + " ASC";

        JSONArray allFollowups = new JSONArray();
        c = db.query(
                TableContracts.OutcomeFollowupTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedFollowups: " + c.getCount());
            OutcomeFollowups Followups = new OutcomeFollowups();
            allFollowups.put(Followups.Hydrate(c).toJSONObject());
        }

        c.close();

        db.close();

        Log.d(TAG, "getUnsyncedOutcomeFollowups: " + allFollowups.toString().length());
        Log.d(TAG, "getUnsyncedOutcomeFollowups: " + allFollowups);
        return allFollowups;
    }

    //update SyncedTables
    public void updateSyncedhouseholds(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

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
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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

    //update SyncedTables
    public void updateSyncedfphouseholds(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FPHouseholdTable.COLUMN_SYNCED, true);
        values.put(FPHouseholdTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FPHouseholdTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FPHouseholdTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedfollowups(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FollowupsTable.COLUMN_SYNCED, true);
        values.put(FollowupsTable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = FollowupsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                FollowupsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedoutcome(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(OutcomeTable.COLUMN_SYNCED, true);
        values.put(OutcomeTable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = OutcomeTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                OutcomeTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedoutcomeFollowup(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_SYNCED, true);
        values.put(TableContracts.OutcomeFollowupTable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = TableContracts.OutcomeFollowupTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                TableContracts.OutcomeFollowupTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
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
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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

        c.close();

        db.close();

        return allVil;
    }

    public List<String> getLMS(int age, int gender, String catA, String catB) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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
            lms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_L)));
            Log.d(TAG, "getLMS: L -> " + c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_L)));
            lms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_M)));
            Log.d(TAG, "getLMS: M -> " + c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_M)));
            lms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_S)));
            Log.d(TAG, "getLMS: S -> " + c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_S)));

        }
        return lms;
    }

    public List<String> getWHLMS(Double height, int gender, String catA) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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
            whlms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_L)));
            whlms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_M)));
            whlms.add(c.getString(c.getColumnIndexOrThrow(ZScoreTable.COLUMN_S)));

        }
        c.close();
        return whlms;
    }


    public int syncZStandard(JSONArray zsList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(ZScoreTable.TABLE_NAME, null, null);
        int insertCount = 0;
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

        db.close();

        return insertCount;
    }

    public Households getHouseholdByHDSSID(String hdssid) throws JSONException {

        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String[] hdssidSplit = hdssid.split("-");
        String newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", Integer.parseInt(hdssidSplit[2]));

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_HDSSID + "=? OR " +
                HouseholdTable.COLUMN_HDSSID + "=? ";

        String[] whereArgs = {hdssid, newHDSSID};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        Households households = null;
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

        c.close();

        db.close();

        return households;
    }

    public Households getHouseholdByUID(String uid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_UID + "=?";

        String[] whereArgs = {uid};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        Households households = null;
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

        c.close();
        db.close();

        return households;
    }


    public List<MWRA> getAllMWRAByHH(String uc, String village, String structure, String hhNo) throws JSONException {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String newhhNo = hhNo;
        if (hhNo.length() == 3) {
            newhhNo = String.format("%04d", Integer.parseInt(hhNo));
        } else {
            newhhNo = String.format("%03d", Integer.parseInt(hhNo));
        }
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = MWRATable.COLUMN_UC_CODE + "=? AND "
                + MWRATable.COLUMN_VILLAGE_CODE + "=? AND "
                + MWRATable.COLUMN_STRUCTURE_NO + "=? AND (  "
                + MWRATable.COLUMN_HOUSEHOLD_NO + "=? OR "
                + MWRATable.COLUMN_HOUSEHOLD_NO + "=?  ) ";

        String[] whereArgs = {uc, village, structure, hhNo, newhhNo};

        String groupBy = null;
        String having = null;

        String orderBy = MWRATable.COLUMN_ID + " ASC";

        ArrayList<MWRA> mwraByHH = new ArrayList<>();
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

        c.close();

        db.close();

        return mwraByHH;
    }

    public List<FollowUpsSche> getAllfollowupsScheByHH(String village, String ucCode, String hhNo) {

        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String newhhNo = hhNo;
        if (hhNo.length() < 4) {
            newhhNo = String.format("%04d", Integer.parseInt(hhNo));
        } else {
            newhhNo = String.format("%03d", Integer.parseInt(hhNo));
        }

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = TableFollowUpsSche.COLUMN_VILLAGE_CODE + "=? AND "
                + TableFollowUpsSche.COLUMN_UC_CODE + "=? AND ( "
                + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + "=? OR "
                + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + "=?  ) ";

        String[] whereArgs = {village, ucCode, hhNo, newhhNo};

        String groupBy = null;
        String having = null;

        String orderBy = TableFollowUpsSche.COLUMN_ID + " ASC";

        ArrayList<FollowUpsSche> followupsByHH = new ArrayList<>();
        c = db.query(
                TableFollowUpsSche.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            FollowUpsSche followups = new FollowUpsSche().Hydrate(c);
            if (!followups.getRb01().equals("null"))
                followupsByHH.add(followups);
        }

        c.close();

        db.close();

        return followupsByHH;
    }

    public int getMaxStructure(String uc, String vCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + HouseholdTable.COLUMN_STRUCTURE_NO + ") AS " + HouseholdTable.COLUMN_STRUCTURE_NO +
                        " FROM " + TableContracts.HouseholdTable.TABLE_NAME +
                        " WHERE " + HouseholdTable.COLUMN_UC_CODE + "=? AND " + HouseholdTable.COLUMN_VILLAGE_CODE + "=? " +
                        " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE
                ,
                new String[]{uc, vCode});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_STRUCTURE_NO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public int getMaxHouseholdNo(String ucCode, String vCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + HouseholdTable.COLUMN_HOUSEHOLD_NO + ") AS " + HouseholdTable.COLUMN_HOUSEHOLD_NO +
                        " FROM " + HouseholdTable.TABLE_NAME +
                        " WHERE " + HouseholdTable.COLUMN_UC_CODE + "=? AND " + HouseholdTable.COLUMN_VILLAGE_CODE + "=? " +
                        " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE
                ,
                new String[]{ucCode, vCode});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndexOrThrow(HouseholdTable.COLUMN_HOUSEHOLD_NO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public int getMaxHHNoByVillage(String ucCode, String vCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {MaxHhnoTable.COLUMN_MAX_HHNO};

        String whereClause;
        whereClause = MaxHhnoTable.COLUMN_UC_CODE + "=? AND " +
                MaxHhnoTable.COLUMN_VILLAGE_CODE + "=?";


        String[] whereArgs = {ucCode, vCode};

        String groupBy = null;
        String having = null;

        String orderBy = MaxHhnoTable.COLUMN_ID + " ASC";

        //ArrayList<FollowUpsSche> followupsByHH = new ArrayList<>();
        c = db.query(
                MaxHhnoTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy,                    // The sort order
                "1"
        );
        int maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(MaxHhnoTable.COLUMN_MAX_HHNO)));
        }

        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public int getMaxMWRSNoBYHH(String ucCode, String vCode, String hhNo) {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String newhhNo = hhNo;

        if (hhNo.length() < 4) {
            newhhNo = String.format("%04d", Integer.parseInt(hhNo));
        }

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + MWRATable.COLUMN_SNO + ") AS " + MWRATable.COLUMN_SNO +
                        " FROM " + MWRATable.TABLE_NAME +
                        " WHERE " + MWRATable.COLUMN_UC_CODE + "=? AND " + MWRATable.COLUMN_VILLAGE_CODE + "=? AND " +
                        "( " +
                        MWRATable.COLUMN_HOUSEHOLD_NO + "=? OR " +
                        MWRATable.COLUMN_HOUSEHOLD_NO + "=? ) " +
                        " GROUP BY " + MWRATable.COLUMN_HOUSEHOLD_NO
                ,
                new String[]{ucCode, vCode, hhNo, newhhNo});

        float maxHHno = 0;

        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndexOrThrow(MWRATable.COLUMN_SNO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }


    public int getMaxHHNoFromFolloupsSche(String ucCode, String vCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + ") AS " + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO +
                        " FROM " + TableFollowUpsSche.TABLE_NAME +
                        " WHERE " + TableFollowUpsSche.COLUMN_UC_CODE + "=? AND "
                        + TableFollowUpsSche.COLUMN_VILLAGE_CODE + "=? "
                        + " GROUP BY " + TableFollowUpsSche.COLUMN_VILLAGE_CODE
                ,
                new String[]{ucCode, vCode});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public int getMaxMWRANoBYHHFromFolloupsSche(String uc, String vCode, String hhNo) {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String newhhNo = hhNo;
        if (hhNo.length() < 4) {
            newhhNo = String.format("%04d", Integer.parseInt(hhNo));
        }/* else {
            newhhNo = String.format("%03d", Integer.parseInt(hhNo));
        }*/
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT " +
                        "MAX(" + TableFollowUpsSche.COLUMN_RB01 + ") AS " + TableFollowUpsSche.COLUMN_RB01 +
                        " FROM " + TableFollowUpsSche.TABLE_NAME +
                        " WHERE " + TableFollowUpsSche.COLUMN_UC_CODE + "=? AND " + TableFollowUpsSche.COLUMN_VILLAGE_CODE + "=? AND ( " +
                        TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + "=?  OR " +
                        TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + "=? ) " +
                        " GROUP BY " + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO
                ,
                new String[]{uc, vCode, hhNo, newhhNo});
        float maxHHno = 0;
        while (c.moveToNext()) {
            maxHHno = c.getFloat(c.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB01));
        }
        Log.d(TAG, "getMaxHHNo: " + maxHHno);
        return Math.round(maxHHno);
    }

    public List<Households> getHouseholdBYStructure(String uc, String village, String structure) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_UC_CODE + "=? AND " + HouseholdTable.COLUMN_VILLAGE_CODE + "=? AND " + HouseholdTable.COLUMN_STRUCTURE_NO + "=? ";

        String[] whereArgs = {uc, village, structure};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        ArrayList<Households> householdByHH = new ArrayList<>();
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

        c.close();
        db.close();

        return householdByHH;
    }

    public List<Households> getHouseholdBYVillage(String uc, String village) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = HouseholdTable.COLUMN_UC_CODE + "=? AND "
                + HouseholdTable.COLUMN_VILLAGE_CODE + "=? ";

        String[] whereArgs = {uc, village};

        String groupBy = null;
        String having = null;

        String orderBy = HouseholdTable.COLUMN_ID + " ASC";

        ArrayList<Households> householdByHH = new ArrayList<>();
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

        c.close();

        db.close();

        return householdByHH;
    }


    public List<FollowUpsSche> getFollowUpsScheHHBYVillage(String uc, String village, String hhead) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                TableFollowUpsSche.COLUMN_VILLAGE_CODE,
                TableFollowUpsSche.COLUMN_UC_CODE,
                TableFollowUpsSche.COLUMN_HOUSEHOLD_NO,
                TableFollowUpsSche.COLUMN_HDSSID,
                TableFollowUpsSche.COLUMN_MUID,
                TableFollowUpsSche.COLUMN_RA01,
                TableFollowUpsSche.COLUMN_RA08,
                TableFollowUpsSche.COLUMN_RA14,
                TableFollowUpsSche.COLUMN_RA18,
                TableFollowUpsSche.COLUMN_FROUND,
                TableFollowUpsSche.COLUMN_DONE_DATE,
                TableFollowUpsSche.COLUMN_ISTATUS,
                TableFollowUpsSche.COLUMN_RB01,
                TableFollowUpsSche.COLUMN_RB02,
                TableFollowUpsSche.COLUMN_RB03,
                TableFollowUpsSche.COLUMN_RB04,
                TableFollowUpsSche.COLUMN_RC12,
                TableFollowUpsSche.COLUMN_RC15,
                TableFollowUpsSche.COLUMN_RB05,
                TableFollowUpsSche.COLUMN_RB06,
                TableFollowUpsSche.COLUMN_MEMBERTYPE,
                "sum(case when " + TableFollowUpsSche.COLUMN_RB07 + "='1' then 1 else 0 end) AS " + TableFollowUpsSche.COLUMN_RB07
        };

        String whereClause;
        whereClause = TableFollowUpsSche.COLUMN_UC_CODE + "=? AND " +
                TableFollowUpsSche.COLUMN_VILLAGE_CODE + "=? AND " +
                TableFollowUpsSche.COLUMN_RA14 + " Like ? ";

        String[] whereArgs = {uc, village, "%" + hhead + "%"};

        String groupBy = TableFollowUpsSche.COLUMN_HDSSID;
        String having = null;

        String orderBy = TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " ASC";

        ArrayList<FollowUpsSche> followUpsSches = new ArrayList<>();
        c = db.query(
                TableContracts.TableFollowUpsSche.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order

        );
        while (c.moveToNext()) {

            followUpsSches.add(new FollowUpsSche().Hydrate(c));
        }

        c.close();

        db.close();

        return followUpsSches;
    }

    public FPHouseholds getFPHouseholdBYHdssid(String hdssid) throws JSONException {

        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String[] hdssidSplit = hdssid.split("-");
        String newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", Integer.parseInt(hdssidSplit[2]));

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FPHouseholdTable.COLUMN_HDSSID + "=? OR " +
                FPHouseholdTable.COLUMN_HDSSID + "=? ";

        String[] whereArgs = {hdssid, newHDSSID};

        String groupBy = FPHouseholdTable.COLUMN_HDSSID;
        String having = null;

        String orderBy = FPHouseholdTable.COLUMN_HDSSID + " Desc";

        FPHouseholds fpHousholds = new FPHouseholds();
        c = db.query(
                FPHouseholdTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy,                    // The sort order
                "1"
        );
        while (c.moveToNext()) {

            fpHousholds = new FPHouseholds().Hydrate(c);
        }

        c.close();

        db.close();

        return fpHousholds;
    }

    public int getMWRACountBYUUID(String uid) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT Count(*) AS mwraCount" +

                        " FROM " + MWRATable.TABLE_NAME +
                        " WHERE " + MWRATable.COLUMN_UUID + " =? "

                ,
                new String[]{uid});
        float maxHHno = 0;
        int mwraCount = 0;
        while (c.moveToNext()) {
            mwraCount = c.getInt(c.getColumnIndexOrThrow("mwraCount"));
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return mwraCount;
    }

    /*public int getMWRACountBYSNO(String hdssid) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT Count(*) AS mwraCount" +

                        " FROM " + TableFollowUpsSche.TABLE_NAME +
                        " WHERE " + TableFollowUpsSche.COLUMN_RB01 + " != ''"

                ,
                new String[]{hdssid});
        float maxHHno = 0;
        int mwraCount = 0;
        while (c.moveToNext()) {
            mwraCount = c.getInt(c.getColumnIndexOrThrow("mwraCount"));
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return mwraCount;
    }
*/

    public int getHouseholdCountByVillage(String villagecode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT Count(*) AS hhCount" +

                        " FROM " + HouseholdTable.TABLE_NAME +
                        " WHERE " + HouseholdTable.COLUMN_VILLAGE_CODE + " =? "

                ,
                new String[]{villagecode});
        float maxHHno = 0;
        int hhCount = 0;
        while (c.moveToNext()) {
            hhCount = c.getInt(c.getColumnIndexOrThrow("hhCount"));
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return hhCount;
    }

    public Collection<Users> getTeamleaders() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = db.rawQuery(
                "SELECT * " +

                        " FROM " + UsersTable.TABLE_NAME +
                        " WHERE " + UsersTable.COLUMN_DESIGNATION + " like '%team%' ", null);
        ArrayList<Users> users = new ArrayList<>();
        while (c.moveToNext()) {
            Users user = new Users().hydrate(c);

            users.add(user);
        }
        // Log.d(TAG, "getMaxHHNo: " + mwraCount);
        return users;
    }

    public OutcomeFollowups getOutcomeFollowupsBySno(String rb01, String fRound, String muid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = TableContracts.OutcomeFollowupTable.COLUMN_UUID + "=? AND " +
                TableContracts.OutcomeFollowupTable.COLUMN_SNO + "=? AND " +
                TableContracts.OutcomeFollowupTable.COLUMN_MUID + "=? AND " +
                TableContracts.OutcomeFollowupTable.COLUMN_FP_ROUND + "=? ";

        String[] whereArgs = {MainApp.fpHouseholds.getUid(), rb01, muid, fRound};

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.OutcomeFollowupTable.COLUMN_ID + " ASC";

        OutcomeFollowups fp = new OutcomeFollowups();

        c = db.query(
                TableContracts.OutcomeFollowupTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            fp = new OutcomeFollowups().Hydrate(c);
        }

        c.close();

        db.close();

        return fp;
    }

    public Followups getFollowupsBySno(String rb01, String fRound) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FollowupsTable.COLUMN_UUID + "=? AND " +
                FollowupsTable.COLUMN_SNO + "=? AND " +
                FollowupsTable.COLUMN_FP_ROUND + "=? ";

        String[] whereArgs = {MainApp.fpHouseholds.getUid(), rb01, fRound};

        String groupBy = null;
        String having = null;

        String orderBy = FollowupsTable.COLUMN_ID + " ASC";

        Followups followup = new Followups();

        c = db.query(
                FollowupsTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            followup = new Followups().Hydrate(c);
        }

        c.close();

        db.close();

        return followup;
    }

    public Outcome getOutcomesBySno(String rb01) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = OutcomeTable.COLUMN_UUID + "=? AND " +
                OutcomeTable.COLUMN_MUID + "=? AND " +
                OutcomeTable.COLUMN_SNO + "=? ";

        String[] whereArgs = {MainApp.followups.getUid(), MainApp.followups.getFmuid(), rb01};

        String groupBy = null;
        String having = null;

        String orderBy = OutcomeTable.COLUMN_ID + " ASC";

        Outcome outcome = new Outcome();

        c = db.query(
                OutcomeTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            outcome = new Outcome().Hydrate(c);
        }

        c.close();

        //db.close();

        return outcome;
    }


    public Outcome getOutComeBYID(String sno) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause =
                OutcomeTable.COLUMN_MUID + "=? AND " +
                        OutcomeTable.COLUMN_SNO + "=? ";

        String[] whereArgs = {MainApp.followups.getUid().split("_")[0], sno};

        String groupBy = null;
        String having = null;

        String orderBy = OutcomeTable.COLUMN_ID + " ASC";

        Outcome outcome = new Outcome();

        c = db.query(
                OutcomeTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            outcome = new Outcome().Hydrate(c);
        }

        c.close();

        //db.close();

        return outcome;
    }


    public Outcome getChildByUUid(String pSNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause;
        whereClause = OutcomeTable.COLUMN_MUID + "=? AND " +
                OutcomeTable.COLUMN_UUID + "=? AND " +
                OutcomeTable.COLUMN_SNO + "=? "
        ;

        String[] whereArgs = {MainApp.outcome.getUid(), MainApp.followups.getUid(), pSNo};

        String groupBy = null;
        String having = null;

        String orderBy = OutcomeTable.COLUMN_ID + " ASC";

        Outcome outcome = new Outcome();  // Outcome can never be null.

        c = db.query(
                OutcomeTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            outcome = new Outcome().Hydrate(c);
        }

        db.close();

        return outcome;
    }





    /*public VersionApp getAppDetails() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
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