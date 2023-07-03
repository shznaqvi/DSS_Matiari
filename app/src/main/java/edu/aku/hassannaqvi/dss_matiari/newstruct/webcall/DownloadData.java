package edu.aku.hassannaqvi.dss_matiari.newstruct.webcall;

import static edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants.IS_CALL_ENCRYPTED;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.models.Hhs;
import edu.aku.hassannaqvi.dss_matiari.models.MaxHhno;
import edu.aku.hassannaqvi.dss_matiari.models.Users;
import edu.aku.hassannaqvi.dss_matiari.models.Villages;
import edu.aku.hassannaqvi.dss_matiari.newstruct.activity.SyncNewAC;
import edu.aku.hassannaqvi.dss_matiari.newstruct.adapters.SyncAdapter;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.AppInfoNew;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.CryptoUtil;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebAPI;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebCall;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client.WebClient;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class DownloadData {
    private final SyncNewAC activity;
    private final WebAPI webAPI;
    private final WebCall webCall;
    private final SyncAdapter syncAdapter;
    private final List<SyncModelNew> syncTablesList;
    private final DssRoomDatabase appDatabase;
    private final Gson gson;
    // This boolean is used for date checking device date validation
    // and show popup only once
    private boolean isDateError;

    /**
     * TABLES TO DOWNLOAD
     */

    // Downloadable tables before login
    public static List<String> DT_BEFORE_LOGIN = new ArrayList<String>() {{
        // AppInfoNew - For version checking
        // App info is not a table. The info is stored in shared preferences
        // Just added here to show it on sync list as item
        add(AppInfoNew.NAME);
        add(TableContracts.UsersTable.TABLE_NAME);
        add(TableContracts.TableVillage.TABLE_NAME);
    }};

    // Downloadable tables after login
    public static List<String> DT_AFTER_LOGIN = new ArrayList<String>() {{
        add(TableContracts.TableHHS.TABLE_NAME);
        add(TableContracts.TableFollowUpsSche.TABLE_NAME);
        add(TableContracts.MaxHhnoTable.TABLE_NAME);


    }};

    /**
     * INIT
     */

    public DownloadData(SyncNewAC activity, SyncAdapter syncAdapter, List<SyncModelNew> syncTablesList) {
        this.activity = activity;
        webAPI = WebClient.getInstance(activity).getWebAPI();
        webCall = new WebCall(activity, iWebCallback);
        this.syncAdapter = syncAdapter;
        this.syncTablesList = syncTablesList;
        appDatabase = MainApp.appInfo.dbHelper;
        gson = new Gson();
    }

    /**
     * DOWNLOAD DATA FROM SERVER
     */

    // Get Data
    // isLogin = This is used to differentiate between before and after login data download
    public void getData(boolean isLogin) {
        // Default filters
        String select = " * ";
        String filter = " (colflag is null or colflag = 0) ";
        String check = "";

        isDateError = false;
        int index = -1;

        if (!isLogin) {
            // Before Login tables download
            // AppInfoNew - For version checking
            // App info is not a table. The info is stored in shared preferences
            SyncModelNew appInfo = new SyncModelNew(DT_BEFORE_LOGIN.get(0), select, filter, check);
            appInfo.setFolder(WebAPI.VERSION_OUTPUT_JSON_FILE_PATH);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(appInfo))), AppConstants.DOWNLOAD_DATA, DT_BEFORE_LOGIN.get(0), ++index, 0, IS_CALL_ENCRYPTED);

            SyncModelNew s1 = new SyncModelNew(DT_BEFORE_LOGIN.get(1), select, filter, check);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(s1))), AppConstants.DOWNLOAD_DATA, DT_BEFORE_LOGIN.get(1), ++index, 0, IS_CALL_ENCRYPTED);

            SyncModelNew s2 = new SyncModelNew(DT_BEFORE_LOGIN.get(2), select, filter, check);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(s2))), AppConstants.DOWNLOAD_DATA, DT_BEFORE_LOGIN.get(2), ++index, 0, IS_CALL_ENCRYPTED);
        } else {
            // After Login tables download
            SyncModelNew s0 = new SyncModelNew(DT_AFTER_LOGIN.get(0), select, "DATEADD(MONTH,2,ra01) between ra01 AND GETDATE() ", check);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(s0))), AppConstants.DOWNLOAD_DATA, DT_AFTER_LOGIN.get(0), ++index, 0, IS_CALL_ENCRYPTED);

            SyncModelNew s1 = new SyncModelNew(DT_AFTER_LOGIN.get(1), select, "DATEADD(MONTH,2,ra01) between ra01 AND GETDATE()", check);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(s1))), AppConstants.DOWNLOAD_DATA, DT_AFTER_LOGIN.get(1), ++index, 0, IS_CALL_ENCRYPTED);

            SyncModelNew s2 = new SyncModelNew(DT_AFTER_LOGIN.get(2), select);
            webCall.call(webAPI.downloadEncData(CryptoUtil.encrypt(gson.toJson(s2))), AppConstants.DOWNLOAD_DATA, DT_AFTER_LOGIN.get(2), ++index, 0, IS_CALL_ENCRYPTED);
        }
    }

    /**
     * CALLBACK FOR DOWNLOAD DATA
     */

    WebCall.IWebCallback iWebCallback = new WebCall.IWebCallback() {
        @Override
        public void onSuccess(String tag, String jsonResponse, int index, int total, List<String> list) {

            // For enabling other sync buttons if all tasks are done
            activity.checkIfAllSynced(syncTablesList.size(), SyncNewAC.DOWNLOAD_DATA);

            // Call Success but error occurred while performing request
            if (!AppConstants.isJSONArrayValid(jsonResponse)) {
                // Update sync list view - Error
                SyncModelNew.WebResponse response = gson.fromJson(jsonResponse, SyncModelNew.WebResponse.class);
                if (!AppConstants.isEmpty(response.getError())) {
                    // Update sync list view - Error
                    String errorMessage = response.getMessage();
                    SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), 0, AppConstants.RESPONSE_ERROR, errorMessage);
                    syncTablesList.set(index, syncModel);
                    syncAdapter.notifyItemChanged(index);
                    return;
                }
            }

            // For Success
            if (tag.equals(AppInfoNew.NAME)) {
                // For App Version
                // App version call download the content of output-metadata.json file of apk placed on server
                try {
                    JSONObject appInfoJson = new JSONObject(jsonResponse);
                    JSONObject appVersionJson = appInfoJson.getJSONArray("elements").getJSONObject(0);
                    AppInfoNew appInfo = new AppInfoNew();
                    appInfo.setVersionName(appVersionJson.getString("versionName"));
                    appInfo.setVersionCode(appVersionJson.getInt("versionCode"));

                    // Update sync list view
                    SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), 1, AppConstants.RESPONSE_SUCCESS, null);
                    syncTablesList.set(index, syncModel);
                    syncAdapter.notifyItemChanged(index);

                    // Setting app version
                    AppInfoNew.setUpdatedAppInfo(appInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (tag.equals(TableContracts.UsersTable.TABLE_NAME)) {
                Users[] users = gson.fromJson(jsonResponse, Users[].class);
                // Update sync list view
                SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), users.length, AppConstants.RESPONSE_SUCCESS, null);
                syncTablesList.set(index, syncModel);
                syncAdapter.notifyItemChanged(index);

                // Clear and Add data to db
                appDatabase.usersDao().reinsert(users);
            } else if (tag.equals(TableContracts.TableVillage.TABLE_NAME)) {
                Villages[] villages = gson.fromJson(jsonResponse, Villages[].class);
                for (Villages village : villages) {
                    village.setVillagecode(village.getVillagecode().substring(1));
                    village.setUccode(String.format("0%s", village.getUccode()));
                }
                // Update sync list view
                SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), villages.length, AppConstants.RESPONSE_SUCCESS, null);
                syncTablesList.set(index, syncModel);
                syncAdapter.notifyItemChanged(index);

                // Clear and Add data to db
                appDatabase.VillagesDao().reinsert(villages);
            } else if (tag.equals(TableContracts.TableFollowUpsSche.TABLE_NAME)) {
                FollowUpsSche[] followUpsSches = gson.fromJson(jsonResponse, FollowUpsSche[].class);
                // Update sync list view
                SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), followUpsSches.length, AppConstants.RESPONSE_SUCCESS, null);
                syncTablesList.set(index, syncModel);
                syncAdapter.notifyItemChanged(index);

                // Clear and Add data to db
                appDatabase.FollowUpsScheDao().reinsert(followUpsSches);
            } else if (tag.equals(TableContracts.TableHHS.TABLE_NAME)) {
                Hhs[] hhs = gson.fromJson(jsonResponse, Hhs[].class);
                // Update sync list view
                SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), hhs.length, AppConstants.RESPONSE_SUCCESS, null);
                syncTablesList.set(index, syncModel);
                syncAdapter.notifyItemChanged(index);

                // Clear and Add data to db
                appDatabase.HhsDao().reinsert(hhs);
            } else if (tag.equals(TableContracts.MaxHhnoTable.TABLE_NAME)) {
                MaxHhno[] maxHhnos = gson.fromJson(jsonResponse, MaxHhno[].class);
                // Update sync list view
                SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), maxHhnos.length, AppConstants.RESPONSE_SUCCESS, null);
                syncTablesList.set(index, syncModel);
                syncAdapter.notifyItemChanged(index);

                // Clear and Add data to db
                appDatabase.MaxHHNoDao().reinsert(maxHhnos);
            }
        }

        @Override
        public void onFailure(String tag, String errorMessage, int index, int total, List<String> list) {
            // Check if error message is a incorrect date error then split it to show formatted message on alert
            String[] dateErr;
            if (!AppConstants.isEmpty(errorMessage) && errorMessage.contains("APP DATE ERROR")) {
                dateErr = errorMessage.split("_");
                errorMessage = dateErr[1];

                // To prevent showing date error dialog again
                if (!isDateError) {
                    isDateError = true;
                    DateUtils.showDeviceDateErrorAlert(activity, dateErr[2], dateErr[3]);
                }
            }

            SyncModelNew syncModel = getUpdatedSyncDownloadItem(activity, syncTablesList.get(index), 0, AppConstants.RESPONSE_ERROR, errorMessage);
            syncTablesList.set(index, syncModel);
            syncAdapter.notifyItemChanged(index);

            // For enabling other sync when failure occurs
            activity.checkIfAllSynced(syncTablesList.size(), SyncNewAC.DOWNLOAD_DATA);
        }
    };

    /**
     * UPDATE SYNC ITEM ON ADAPTER
     */

    /*This function is used to update list item after DOWNLOAD*/
    // The reason to make this function instance-wise is because if we make it static then there
    // will be a probability of data messing as we call this method after parallel calls
    public SyncModelNew getUpdatedSyncDownloadItem(Activity activity, SyncModelNew syncModel, int size, int callStatus, String error) {
        String message;
        if (error == null) {
            // Success
            message = String.format(activity.getString(R.string.sync_download_success), size);
        } else
            // Error
            message = String.format(activity.getString(R.string.sync_error), error);

        syncModel.setMessage(message);
        syncModel.setStatusId(callStatus);
        return syncModel;
    }

}