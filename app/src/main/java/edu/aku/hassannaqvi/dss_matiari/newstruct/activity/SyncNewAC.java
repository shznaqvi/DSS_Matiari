package edu.aku.hassannaqvi.dss_matiari.newstruct.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySyncNewBinding;
import edu.aku.hassannaqvi.dss_matiari.newstruct.adapters.SyncAdapter;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.DownloadData;
import edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.UploadData;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SyncNewAC extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final Activity activity = SyncNewAC.this;

    private ActivitySyncNewBinding bi;
    private DssRoomDatabase appDatabase;
    private Gson gson;

    // Table names list to download/upload
    private List<SyncModelNew> syncTablesList;
    private SyncAdapter syncAdapter;

    // For disabling other sync buttons if one task is performing
    private int syncRecordsCount;

    // Sync Types
    public static int DOWNLOAD_DATA = 111;
    public static int UPLOAD_DATA = 112;
    public static int UPLOAD_PHOTOS = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(activity, R.layout.activity_sync_new);

        // Init toolbar
        AppConstants.initToolbar(activity, getString(R.string.data_sync_activity), null, true);

        appDatabase = MainApp.appInfo.dbHelper;

        // Init sync list
        syncTablesList = new ArrayList<>();
        initSyncList();
    }

    // Init sync list adapter
    private void initSyncList() {
        syncAdapter = new SyncAdapter(activity, syncTablesList, null);
        bi.syncRV.setAdapter(syncAdapter);
    }

    // Download Data
    @SuppressLint("NotifyDataSetChanged")
    public void downloadData(View view) {
        DisableOtherViews(DOWNLOAD_DATA);
        syncTablesList.clear();
        if (!AppConstants.IS_LOGIN)
            syncTablesList.addAll(SyncModelNew.initSyncList(DownloadData.DT_BEFORE_LOGIN));
        else syncTablesList.addAll(SyncModelNew.initSyncList(DownloadData.DT_AFTER_LOGIN));
        syncAdapter.notifyDataSetChanged();
        DownloadData downloadData = new DownloadData(this, syncAdapter, syncTablesList);
        downloadData.getData(AppConstants.IS_LOGIN);
    }

    // Upload Data
    @SuppressLint("NotifyDataSetChanged")
    public void uploadData(View view) {
        DisableOtherViews(UPLOAD_DATA);
        syncTablesList.clear();
        syncTablesList.addAll(UploadData.UPLOAD_TABLES.keySet());
        syncAdapter.notifyDataSetChanged();
        UploadData uploadData = new UploadData(this, syncAdapter, syncTablesList);
        uploadData.postData();
    }

    /*// Upload Photos
    @SuppressLint("NotifyDataSetChanged")
    public void uploadPhotos(View view) {
        syncTablesList.clear();
        List<String> imagesNamesList = ImageUtils.getAllImagesNames(activity);
        if (imagesNamesList == null || imagesNamesList.size() == 0) {
            AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_photos_to_upload),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            syncAdapter.notifyDataSetChanged();
            return;
        }
        DisableOtherViews(UPLOAD_PHOTOS);
        syncTablesList.addAll(SyncModelNew.initSyncList(imagesNamesList));
        syncAdapter.notifyDataSetChanged();
        UploadPhotos uploadPhotos = new UploadPhotos(this, syncAdapter, syncTablesList);
        uploadPhotos.postPhotos();
    }*/

    // For disabling other sync buttons if one task is performing
    private void DisableOtherViews(int syncType) {
        int disabledColor = ContextCompat.getColor(activity, R.color.disabled_text_color);
        if (syncType == DOWNLOAD_DATA) {
            // Download Data - Disable Upload and Upload Photos buttons
            bi.uploadBtn.setEnabled(false);
            bi.uploadBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
//            bi.uploadPhotosBtn.setEnabled(false);
//            bi.uploadPhotosBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
        } else if (syncType == UPLOAD_DATA) {
            // Upload Data - Disable Download and Upload Photos buttons
            bi.downloadBtn.setEnabled(false);
            bi.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
//            bi.uploadPhotosBtn.setEnabled(false);
//            bi.uploadPhotosBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
        } else {
            // Upload Photos - Disable Download and Upload buttons
            bi.downloadBtn.setEnabled(false);
            bi.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
            bi.uploadBtn.setEnabled(false);
            bi.uploadBtn.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
        }
    }

    // For enabling other sync buttons if all tasks are done
    private void EnableOtherViews(int syncType) {
        // Reset sync records count
        syncRecordsCount = 0;
        int enabledColor = ContextCompat.getColor(activity, R.color.primary_color_dark);
        int enabledPhotosColor = ContextCompat.getColor(activity, R.color.secondary_color_dark);
        if (syncType == DOWNLOAD_DATA) {
            // Download Data - Disable Upload and Upload Photos buttons
            bi.uploadBtn.setEnabled(true);
            bi.uploadBtn.setBackgroundTintList(ColorStateList.valueOf(enabledColor));
//            bi.uploadPhotosBtn.setEnabled(true);
//            bi.uploadPhotosBtn.setBackgroundTintList(ColorStateList.valueOf(enabledPhotosColor));
        } else if (syncType == UPLOAD_DATA) {
            // Upload Data - Disable Download and Upload Photos buttons
            bi.downloadBtn.setEnabled(true);
            bi.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(enabledColor));
//            bi.uploadPhotosBtn.setEnabled(true);
//            bi.uploadPhotosBtn.setBackgroundTintList(ColorStateList.valueOf(enabledPhotosColor));
        } else {
            // Upload Photos - Disable Download and Upload buttons
            bi.downloadBtn.setEnabled(true);
            bi.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(enabledColor));
            bi.uploadBtn.setEnabled(true);
            bi.uploadBtn.setBackgroundTintList(ColorStateList.valueOf(enabledColor));
        }
    }

    // For enabling other sync buttons if all tasks are done
    public void checkIfAllSynced(int total, int syncType) {
        if (syncRecordsCount < total - 1) {
            syncRecordsCount++;
            return;
        }
        EnableOtherViews(syncType);
    }

    // For toolbar back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
