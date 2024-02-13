/*
package edu.aku.hassannaqvi.dss_matiari.webcall;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import edu.aku.omarshoaib.appstructure.R;
import edu.aku.omarshoaib.appstructure.activity.SyncAC;
import edu.aku.omarshoaib.appstructure.adapter.SyncAdapter;
import edu.aku.omarshoaib.appstructure.database.AppDatabase;
import edu.aku.omarshoaib.appstructure.global.AppConstants;
import edu.aku.omarshoaib.appstructure.global.ImageUtils;
import edu.aku.omarshoaib.appstructure.model.SyncModel;
import edu.aku.omarshoaib.appstructure.webcall.web_client.WebAPI;
import edu.aku.omarshoaib.appstructure.webcall.web_client.WebCall;
import edu.aku.omarshoaib.appstructure.webcall.web_client.WebClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPhotos {
    private final SyncAC activity;
    private final WebAPI webAPI;
    private final WebCall webCall;
    private final SyncAdapter syncAdapter;
    private final List<SyncModel> syncTablesList;
    private List<File> uploadPhotosList;
    private final AppDatabase appDatabase;
    private final Gson gson;

    private int index;

    */
/**
     * INIT
     *//*


    public UploadPhotos(SyncAC activity, SyncAdapter syncAdapter, List<SyncModel> syncTablesList) {
        this.activity = activity;
        webAPI = WebClient.getInstance(activity).getWebAPI();
        webCall = new WebCall(activity, iWebCallback);
        this.syncAdapter = syncAdapter;
        this.syncTablesList = syncTablesList;
        // For deletion after upload
        uploadPhotosList = ImageUtils.getAllImagesFiles(activity);
        appDatabase = AppDatabase.getDBInstance();
        gson = new Gson();
    }

    */
/**
     * POST PHOTOS TO SERVER
     *//*


    // Post Photos
    public void postPhotos() {
        List<File> allImages = ImageUtils.getAllImagesFiles(activity);
        if (allImages != null && allImages.size() > 0) {
            index = 0;
            String twoHyphens = "--";
            String boundary = "*****" + System.currentTimeMillis() + "*****";
            for (File file : allImages) {
                // Photo Body
                RequestBody reqFile = RequestBody.create(file, MediaType.parse("image/*"));
                MultipartBody.Part photoBody = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                // Text body
                RequestBody textBody = RequestBody.create(twoHyphens + boundary + twoHyphens, MediaType.parse("text/plain"));
                // Call encryption in upload photos is always disabled because the response is already decrypted
                webCall.call(webAPI.uploadPhotos(textBody, photoBody), AppConstants.UPLOAD_PHOTOS, file.getName(), index++, allImages.size(), false);
            }
        }
    }

    */
/**
     * CALLBACK FOR UPLOAD PHOTOS
     *//*


    WebCall.IWebCallback iWebCallback = new WebCall.IWebCallback() {

        @Override
        public void onSuccess(String tag, String jsonResponse, int index, int total, List<String> list) {

            // For enabling other sync when failure occurs
            activity.checkIfAllSynced(syncTablesList.size(), AppConstants.UPLOAD_PHOTOS);

            // Update sync list view
            SyncModel.WebResponse response;
            try {
                response = gson.fromJson(jsonResponse, SyncModel.WebResponse.class);
            } catch (Exception e) {
                response = null;
            }
            SyncModel syncModel = getUpdatedSyncUploadPhotoItem(activity, response, uploadPhotosList.get(index),
                    syncTablesList.get(index), AppConstants.RESPONSE_SUCCESS, null);
            syncTablesList.set(index, syncModel);
            syncAdapter.notifyItemChanged(index);
        }

        @Override
        public void onFailure(String tag, String errorMessage, int index, int total, List<String> list) {
            // Update sync list view
            SyncModel syncModel = getUpdatedSyncUploadPhotoItem(activity, null, null,
                    syncTablesList.get(index), AppConstants.RESPONSE_ERROR, errorMessage);
            syncTablesList.set(index, syncModel);
            syncAdapter.notifyItemChanged(index);

            // For enabling other sync buttons if all tasks are done
            activity.checkIfAllSynced(syncTablesList.size(), AppConstants.UPLOAD_PHOTOS);
        }
    };

    */
/**
     * UPDATE SYNC ITEM ON ADAPTER
     *//*


    */
/*This function is used to update list item after UPLOAD*//*

    // The reason to make this function instance-wise is because if we make it static then there
    // will be a probability of data messing as we call this method after parallel calls
    public SyncModel getUpdatedSyncUploadPhotoItem(Activity activity, SyncModel.WebResponse response, File photo, SyncModel syncModel, int callStatus, String error) {
        String message = "";
        if (response != null) {
            // Success
            if (response.getError() == 0) {
                if (response.getStatus() == 1)
                    // Synced
                    message = String.format(activity.getString(R.string.status), activity.getString(R.string.sync_upload_photo_success));
                else if (response.getStatus() == 2 && response.getError() == 0)
                    // Duplicate
                    message = String.format(activity.getString(R.string.status), activity.getString(R.string.sync_upload_duplicate));
                // Move file to different folder for later use
                ImageUtils.moveImage(activity, photo.getName());
            } else
                // Error
                message = String.format(activity.getString(R.string.status), activity.getString(R.string.somethings_not_right));
        } else
            // Error
            message = String.format(activity.getString(R.string.status), error);

        syncModel.setMessage(message);
        syncModel.setStatusId(callStatus);
        return syncModel;
    }

}
*/
