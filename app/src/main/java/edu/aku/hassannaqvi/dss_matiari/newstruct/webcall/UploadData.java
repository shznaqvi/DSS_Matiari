package edu.aku.hassannaqvi.dss_matiari.newstruct.webcall;

import static edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants.IS_CALL_ENCRYPTED;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.EntryLog;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.newstruct.activity.SyncNewAC;
import edu.aku.hassannaqvi.dss_matiari.newstruct.adapters.SyncAdapter;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.CryptoUtil;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebAPI;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebCall;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebClient;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class UploadData {
    private final SyncNewAC activity;
    private final WebAPI webAPI;
    private final WebCall webCall;
    private final SyncAdapter syncAdapter;
    private final List<SyncModelNew> syncTablesList;
    private final DssRoomDatabase appDatabase;
    private final Gson gson;

    /**
     * TABLES TO POST
     */

    // The boolean is used to identify that this table is a main form or sub-section
    // in order to generalize the upload data logic.
    /*Only the MAIN FORM/TABLE will be true*/
    // We used/changed SyncModelNew instead of Table string as Key because of showing section
    // names in the upload item
    public static LinkedHashMap<SyncModelNew, Boolean> UPLOAD_TABLES = new LinkedHashMap<SyncModelNew, Boolean>() {{
        put(new SyncModelNew(TableContracts.EntryLogTable.TABLE_NAME, AppConstants._EMPTY_), false);
        put(new SyncModelNew(TableContracts.HouseholdTable.TABLE_NAME, ""), true);
        put(new SyncModelNew(TableContracts.MWRATable.TABLE_NAME, ""), false);
        put(new SyncModelNew(TableContracts.OutcomeTable.TABLE_NAME, ""), false);
    }};

    /**
     * INIT
     */

    public UploadData(SyncNewAC activity, SyncAdapter syncAdapter, List<SyncModelNew> syncTablesList) {
        this.activity = activity;
        webAPI = WebClient.getInstance(activity).getWebAPI();
        webCall = new WebCall(activity, iWebCallback);
        this.syncAdapter = syncAdapter;
        this.syncTablesList = syncTablesList;
        appDatabase = MainApp.appInfo.dbHelper;
        gson = new Gson();
    }

    /**
     * PREPARE DATA TO POST
     */

    /*Prepare UPLOAD data to post*/
    public static String prepareUploadData(String tableName, JSONArray uploadData) {
        try {
            // Table
            JSONObject table = new JSONObject();
            table.put("table", tableName);
            JSONArray value = new JSONArray();
            value.put(table);
            value.put(uploadData);
            Log.e("POST_JSON", value.toString());
            return CryptoUtil.encrypt(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST DATA TO SERVER
     */

    // Post Data
    public void postData() {
        // index is used to update sync data list adapter item
        int index = -1;
        JSONArray postJSON;
        String tableName, postData;
        try {
            tableName = TableContracts.EntryLogTable.TABLE_NAME;
            postJSON = appDatabase.syncFunctionsDao().getUnsyncedEntryLog();
            if (postJSON != null && postJSON.length() > 0) {
                postData = prepareUploadData(tableName, postJSON);
                webCall.call(webAPI.uploadEncData(postData), AppConstants.UPLOAD_DATA, tableName, 0, postJSON.length(), IS_CALL_ENCRYPTED);
            } else
                iWebCallback.onFailure(tableName, activity.getString(R.string.no_new_records_to_upload), 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*// Ths hashmap is used to store all the form completed uIds with respect to table name
        HashMap<String, List<String>> uIdsHM = new HashMap<>();
        for (int i = 0; i < UPLOAD_TABLES.size(); i++) {
            // This looping is used to get those un-synced forms
            // whose iStatus != null i.e. Interview Completed and also get
            // other models of the related form.
            // This is used to reduce data invalidation on server
            SyncModelNew syncModel = (SyncModelNew) UPLOAD_TABLES.keySet().toArray()[i];
            boolean isMainTable = Boolean.TRUE.equals(UPLOAD_TABLES.get(syncModel));
            if (isMainTable) {
                SimpleSQLiteQuery syncedQuery = new SimpleSQLiteQuery("SELECT _uid FROM " + syncModel.getTable() +
                        " WHERE ((synced IS '' OR synced IS null) AND (synced_date IS '' OR synced_date IS null)" +
                        " AND (iStatus != '' OR iStatus != null)) OR isError IS 1");
                List<String> uIdsList = new ArrayList<>();
                uIdsHM.put(syncModel.getTable(), uIdsList);
                uIdsList = appDatabase.syncFunctionsDao().getUnsyncedDataUIds(syncedQuery);
                if (uIdsList != null && uIdsList.size() > 0) {
                    Objects.requireNonNull(uIdsHM.get(syncModel.getTable())).addAll(uIdsList);
                }
            }
        }*/

        // Now access only those forms whose uIds are in iFormCompletedUIDs list

//            List<String> iFormCompletedUIds;

//        iFormCompletedUIds = uIdsHM.get(TableContracts.HouseholdTable.TABLE_NAME);
//        tableName = ((SyncModelNew) UPLOAD_TABLES.keySet().toArray()[1]).getTable();
//        List<Households> list1 = appDatabase.householdsDao().getAllUnSyncedDataByUIds(iFormCompletedUIds);
//        if (list1 != null && list1.size() > 0) {
        try {
            tableName = TableContracts.HouseholdTable.TABLE_NAME;
            postJSON = appDatabase.syncFunctionsDao().getUnsyncedHouseholds();
            if (postJSON != null && postJSON.length() > 0) {
                postData = prepareUploadData(tableName, postJSON);
                webCall.call(webAPI.uploadEncData(postData), AppConstants.UPLOAD_DATA, tableName, 1, postJSON.length(), IS_CALL_ENCRYPTED);
            } else
                iWebCallback.onFailure(tableName, activity.getString(R.string.no_new_records_to_upload), 1, 0, null);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            tableName = TableContracts.MWRATable.TABLE_NAME;
            postJSON = appDatabase.syncFunctionsDao().getUnsyncedMwras();
            if (postJSON != null && postJSON.length() > 0) {
                postData = prepareUploadData(tableName, postJSON);
                webCall.call(webAPI.uploadEncData(postData), AppConstants.UPLOAD_DATA, tableName, 2, postJSON.length(), IS_CALL_ENCRYPTED);
            } else
                iWebCallback.onFailure(tableName, activity.getString(R.string.no_new_records_to_upload), 2, 0, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tableName = TableContracts.OutcomeTable.TABLE_NAME;
            postJSON = appDatabase.syncFunctionsDao().getUnsyncedOutcome();
            if (postJSON != null && postJSON.length() > 0) {
                postData = prepareUploadData(tableName, postJSON);
                webCall.call(webAPI.uploadEncData(postData), AppConstants.UPLOAD_DATA, tableName, 3, postJSON.length(), IS_CALL_ENCRYPTED);
            } else
                iWebCallback.onFailure(tableName, activity.getString(R.string.no_new_records_to_upload), 3, 0, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /* ADD MORE TABLE HERE TO UPLOAD IF NECESSARY */

    }

    /**
     * CALLBACK FOR UPLOAD DATA
     */

    WebCall.IWebCallback iWebCallback = new WebCall.IWebCallback() {
        @Override
        public void onSuccess(String tag, String jsonResponse, int index, int total, List<String> list) {

            // For enabling other sync buttons if all tasks are done
            activity.checkIfAllSynced(syncTablesList.size(), SyncNewAC.UPLOAD_DATA);

            // Update sync list view
            List<SyncModelNew.WebResponse> responses = Arrays.asList(gson.fromJson(jsonResponse, SyncModelNew.WebResponse[].class));
            SyncModelNew syncModel = getUpdatedSyncUploadItem(activity, responses,
                    syncTablesList.get(index), AppConstants.RESPONSE_SUCCESS, null, total);
            syncTablesList.set(index, syncModel);
            syncAdapter.notifyItemChanged(index);

            // Update sync status to Success
            updateSyncStatus(tag, responses, list, true);
        }

        // list = list of uIds that has been selected for post
        @Override
        public void onFailure(String tag, String errorMessage, int index, int total, List<String> list) {
            // Update sync list view
            SyncModelNew syncModel = getUpdatedSyncUploadItem(activity, null,
                    syncTablesList.get(index), AppConstants.RESPONSE_ERROR, errorMessage, total);
            syncTablesList.set(index, syncModel);
            syncAdapter.notifyItemChanged(index);

            // For enabling other sync when failure occurs
            activity.checkIfAllSynced(syncTablesList.size(), SyncNewAC.UPLOAD_DATA);

            if (total == 0) return;
            // Update sync status to Error
            updateSyncStatus(tag, null, list, false);
        }
    };

    /**
     * UPDATE SYNC STATUS ON DB
     */

    // Update sync status to db i.e.
    // - In case of success, update synced and synced status value and isError to false
    // - In case of error, update isError to true
    // list = list of uIds of selected forms to post - This parameter is used to update isError
    // to true on selected records that are failed to upload
    private void updateSyncStatus(String tag, List<SyncModelNew.WebResponse> responses, List<String> list, boolean isSuccess) {
        if (isSuccess) {
            // Update sync success status to db
            if (tag.equals(TableContracts.EntryLogTable.TABLE_NAME)) {
                appDatabase.EntryLogDao().updateSyncSuccess(responses);
            } else if (tag.equals(TableContracts.HouseholdTable.TABLE_NAME)) {
                appDatabase.householdsDao().updateSyncSuccess(responses);
            } else if (tag.equals(TableContracts.MWRATable.TABLE_NAME)) {
                appDatabase.mwraDao().updateSyncSuccess(responses);
            } else if (tag.equals(TableContracts.OutcomeTable.TABLE_NAME)) {
                appDatabase.OutcomeDao().updateSyncSuccess(responses);
            }
        } else {
            // Update sync error status to db
            if (tag.equals(TableContracts.EntryLogTable.TABLE_NAME)) {
                appDatabase.EntryLogDao().updateSyncError(Objects.requireNonNull(appDatabase.EntryLogDao().getAllUnSyncedData()));
            } else if (tag.equals(TableContracts.HouseholdTable.TABLE_NAME)) {
                appDatabase.householdsDao().updateSyncError(Objects.requireNonNull(appDatabase.householdsDao().getAllUnSyncedDataByUIds(list)));
            } else if (tag.equals(TableContracts.MWRATable.TABLE_NAME)) {
                appDatabase.mwraDao().updateSyncError(Objects.requireNonNull(appDatabase.mwraDao().getAllUnSyncedDataByUIds(list)));
            } else if (tag.equals(TableContracts.OutcomeTable.TABLE_NAME)) {
                appDatabase.OutcomeDao().updateSyncError(Objects.requireNonNull(appDatabase.OutcomeDao().getAllUnSyncedDataByUIds(list)));
            }
        }
    }

    /**
     * UPDATE SYNC ITEM ON ADAPTER
     */

    /*This function is used to update list item after UPLOAD*/
    // The reason to make this function instance-wise is because if we make it static then there
    // will be a probability of data messing as we call this method after parallel calls
    public SyncModelNew getUpdatedSyncUploadItem(Activity activity, List<SyncModelNew.WebResponse> response, SyncModelNew syncModel, int callStatus, String error, int total) {
        String statusMessage = "", message;
        if (response != null) {
            // Success
            syncModel.setStatusId(callStatus);
            int synced = 0, duplicates = 0;
            for (int i = 0; i < response.size(); i++) {
                if (response.get(i).getError() == 0) {
                    if (response.get(i).getStatus() == 1) {
                        // Synced
                        synced++;
                        statusMessage = response.get(i).getMessage();
                    } else if (response.get(i).getStatus() == 2) {
                        // Duplicate
                        duplicates++;
                        statusMessage = String.format("%s%s", statusMessage, response.get(i).getMessage());
                    }
                } else {
                    syncModel.setStatusId(AppConstants.RESPONSE_ERROR);
                    statusMessage = response.get(i).getMessage();
                }
            }
            message = String.format(activity.getString(R.string.sync_upload_success), total, synced, duplicates,
                    statusMessage);
        } else {
            // Error
            message = String.format(activity.getString(R.string.status), error);
            syncModel.setStatusId(AppConstants.RESPONSE_ERROR);
        }

        syncModel.setMessage(message);
        return syncModel;
    }

}