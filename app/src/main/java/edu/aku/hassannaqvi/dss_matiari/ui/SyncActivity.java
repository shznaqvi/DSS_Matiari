package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sdDir;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.uploadData;
import static edu.aku.hassannaqvi.dss_matiari.utils.AndroidUtilityKt.isNetworkConnected;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.SyncListAdapter;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableVillage;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.VersionTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySyncBinding;
import edu.aku.hassannaqvi.dss_matiari.models.SyncModel;
import edu.aku.hassannaqvi.dss_matiari.workers.DataDownWorkerALL;
import edu.aku.hassannaqvi.dss_matiari.workers.DataUpWorkerALL;
import edu.aku.hassannaqvi.dss_matiari.workers.PhotoUploadWorker2;
import edu.aku.hassannaqvi.dss_matiari.workers.ReadJSONWorker;


public class SyncActivity extends AppCompatActivity {
    private static final String TAG = "SyncActivity";
    final Handler handler = new Handler();
    DatabaseHelper db;
    SyncListAdapter syncListAdapter;
    ActivitySyncBinding bi;
    List<SyncModel> uploadTables;
    List<SyncModel> downloadTables;
    Boolean listActivityCreated;
    Boolean uploadlistActivityCreated;
    String campCode;
    private int totalFiles;
    private long tStart;
    private String progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_sync);
        bi.setCallback(this);
        setSupportActionBar(bi.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = MainApp.appInfo.getDbHelper();
        uploadTables = new ArrayList<>();
        downloadTables = new ArrayList<>();
        MainApp.uploadData = new ArrayList<>();
        sdDir = new File(this.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);

        //bi.noItem.setVisibility(View.VISIBLE);
        bi.noDataItem.setVisibility(View.VISIBLE);
        listActivityCreated = true;
        uploadlistActivityCreated = true;

        db = new DatabaseHelper(this);
        //dbBackup(this);
        OneTimeWorkRequest JSONWorker =
                new OneTimeWorkRequest.Builder(ReadJSONWorker.class)
                        .build();
        WorkManager.getInstance(this).enqueue(JSONWorker);

    }

    void setAdapter(List<SyncModel> tables) {
        syncListAdapter = new SyncListAdapter(tables);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        bi.rvUploadList.setLayoutManager(mLayoutManager2);
        bi.rvUploadList.setItemAnimator(new DefaultItemAnimator());
        bi.rvUploadList.setAdapter(syncListAdapter);
        syncListAdapter.notifyDataSetChanged();
        if (syncListAdapter.getItemCount() > 0) {
            bi.noDataItem.setVisibility(View.GONE);
        } else {
            bi.noDataItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    public void ProcessStart(View view) {
        if (!isNetworkConnected(this))
            return;

        switch (view.getId()) {

            case R.id.btnUpload:

                bi.activityTitle.setText("Upload Data");

                bi.dataLayout.setVisibility(View.VISIBLE);
                bi.photoLayout.setVisibility(View.GONE);
                bi.mTextViewS.setVisibility(View.GONE);
                bi.pBar.setVisibility(View.GONE);
                uploadTables.clear();
                MainApp.uploadData.clear();
                try {
                    // Forms
                    uploadTables.add(new SyncModel(HouseholdTable.TABLE_NAME));
                    MainApp.uploadData.add(db.getUnsyncedHouseholds());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ProcessStart (JSONException): " + e.getMessage());

                }
                try {
                    // MWRA
                    uploadTables.add(new SyncModel(MWRATable.TABLE_NAME));
                    MainApp.uploadData.add(db.getUnsyncedMWRA());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ProcessStart (JSONException): " + e.getMessage());
                }

                MainApp.downloadData = new String[uploadData.size()];
                setAdapter(uploadTables);
                BeginUpload();
                break;
            case R.id.btnSync:

                bi.activityTitle.setText("Download Data");

                MainApp.downloadData = new String[0];
                bi.dataLayout.setVisibility(View.VISIBLE);
                bi.photoLayout.setVisibility(View.GONE);
                bi.mTextViewS.setVisibility(View.GONE);
                bi.pBar.setVisibility(View.GONE);
                downloadTables.clear();
                boolean sync_flag = getIntent().getBooleanExtra("login", false);
                if (sync_flag) {
                    String select = null;
                    String filter = " colflag is null ";
                    downloadTables.add(new SyncModel(UsersTable.TABLE_NAME, select, filter));
                    downloadTables.add(new SyncModel(VersionTable.TABLE_NAME));
                    downloadTables.add(new SyncModel(TableVillage.TABLE_NAME, select, filter));
                } else {
                    // Set tables to DOWNLOAD
                    String select = null;
                    String filter = " colflag is null ";
                    downloadTables.add(new SyncModel(UsersTable.TABLE_NAME, select, filter));

                    downloadTables.add(new SyncModel(VersionTable.TABLE_NAME));
                    downloadTables.add(new SyncModel(TableVillage.TABLE_NAME, select, filter));


                 /*   String select = " idCamp, camp_no, dist_id, district, ucCode, ucName, area_name, plan_date ";
                    String filter = " camp_status = 'Planned' AND locked = 0 ";
                    downloadTables.add(new SyncModel(Camps.TableCamp.TABLE_NAME, select, filter));
                    downloadTables.add(new SyncModel(Doctor.TableDoctor.TABLE_NAME));*/
                }
                MainApp.downloadData = new String[downloadTables.size()];
                setAdapter(downloadTables);
                BeginDownload();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void BeginDownload() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();

        for (int i = 0; i < downloadTables.size(); i++) {
            Data.Builder data = new Data.Builder()
                    .putString("table", downloadTables.get(i).gettableName())
                    .putInt("position", i)
                    .putString("select", downloadTables.get(i).getSelect() != null ? downloadTables.get(i).getSelect() : " * ")
                    .putString("where", downloadTables.get(i).getFilter() != null ? downloadTables.get(i).getFilter() : " 1=1 ");
            /*if (downloadTables.get(i).gettableName().equals(Doctor.TableDoctor.TABLE_NAME)) {
                data.putString("where", Doctor.TableDoctor.COLUMN_ID_CAMP + "='" + campCode + "'");
            }*/

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DataDownWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data.build()).build();
            workRequests.add(workRequest);

        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance(this);
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        wc.getWorkInfosLiveData().observe(this, workInfos -> {
            Log.d(TAG, "workInfos: " + workInfos.size());
            for (WorkInfo workInfo : workInfos) {
                Log.d(TAG, "workInfo: getState " + workInfo.getState());
                Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("data"));
                Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
            }
            for (WorkInfo workInfo : workInfos) {
                int position = workInfo.getOutputData().getInt("position", 0);
                String tableName = downloadTables.get(position).gettableName();

                        /*String progress = workInfo.getProgress().getString("progress");
                        bi.wmError.setText("Progress: " + progress);*/

                if (workInfo.getState() != null &&
                        workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                    //String result = workInfo.getOutputData().getString("data");
                    String result = MainApp.downloadData[position];
//Do something with the JSON string
                    if (result != null) {
                        if (result.length() > 0) {
                            Log.d(TAG, "onChanged: result " + result);
                            System.out.println("SYSTEM onChanged: result" + result);
                            DatabaseHelper db = new DatabaseHelper(SyncActivity.this);
                            try {
                                JSONArray jsonArray = new JSONArray();
                                int insertCount = 0;
                                switch (tableName) {
                                    case UsersTable.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncUser(jsonArray);
                                        break;
                                    case VersionTable.TABLE_NAME:
                                        insertCount = db.syncVersionApp(new JSONObject(result));
                                        if (insertCount == 1) jsonArray.put("1");
                                        break;
                                    case TableVillage.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncVillage(jsonArray);
                                        break;
                          /*          case Camps.TableCamp.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncCamp(jsonArray);
                                        Log.d(TAG, "onChanged: " + tableName + " " + workInfo.getOutputData().getInt("position", 0));
                                        break;*/

                                }

                                downloadTables.get(position).setmessage("Received: " + jsonArray.length() + ", Saved: " + insertCount);
                                downloadTables.get(position).setstatus(insertCount == 0 ? "Unsuccessful" : "Successful");
                                downloadTables.get(position).setstatusID(insertCount == 0 ? 1 : 3);
                                syncListAdapter.updatesyncList(downloadTables);

//                    pd.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                downloadTables.get(position).setstatus("Process Failed");
                                downloadTables.get(position).setstatusID(1);
                                downloadTables.get(position).setmessage(result);
                                syncListAdapter.updatesyncList(downloadTables);
                            }
                        } else {
                            downloadTables.get(position).setmessage("Received: " + result.length() + "");
                            downloadTables.get(position).setstatus("Successful");
                            downloadTables.get(position).setstatusID(3);
                            syncListAdapter.updatesyncList(downloadTables);
//                pd.show();
                        }
                    } else {
                        downloadTables.get(position).setstatus("Process Failed");
                        downloadTables.get(position).setstatusID(1);
                        downloadTables.get(position).setmessage("Server not found!");
                        syncListAdapter.updatesyncList(downloadTables);
//            pd.show();
                    }
                }
                //mTextView1.append("\n" + workInfo.getState().name());
                if (workInfo.getState() != null &&
                        workInfo.getState() == WorkInfo.State.FAILED) {
                    String message = workInfo.getOutputData().getString("error");
                    downloadTables.get(position).setstatus("Process Failed");
                    downloadTables.get(position).setstatusID(1);
                    downloadTables.get(position).setmessage(message);
                    syncListAdapter.updatesyncList(downloadTables);

                }
            }
        });
    }

    private void BeginUpload() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();

        for (int i = 0; i < uploadTables.size(); i++) {
            Data data = new Data.Builder()
                    .putString("table", uploadTables.get(i).gettableName())
                    .putInt("position", i)
                    //    .putString("data", uploadData.get(i).toString())

                    //.putString("columns", "_id, sysdate")
                    // .putString("where", where)
                    .build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DataUpWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data).build();
            workRequests.add(workRequest);

        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance(this);
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        // FOR WORKREQUESTS CHAIN (ONE TABLE DOWNLOADS AT A TIME)
/*        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests.get(0));
        for (int i=1; i < workRequests.size(); i++ ) {
            wc = wc.then(workRequests.get(i));
        }
        wc.enqueue();*/


        wc.getWorkInfosLiveData().observe(this, workInfos -> {
            Log.d(TAG, "workInfos: " + workInfos.size());
            for (WorkInfo workInfo : workInfos) {
                Log.d(TAG, "workInfo: getState " + workInfo.getState());
                Log.d(TAG, "workInfo: data " + workInfo.getTags());
                Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("message"));
                Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
            }
            for (WorkInfo workInfo : workInfos) {
                int position = workInfo.getOutputData().getInt("position", 0);
                String tableName = uploadTables.get(position).gettableName();
                String result = MainApp.downloadData[position];

                        /*String progress = workInfo.getProgress().getString("progress");
                        bi.wmError.setText("Progress: " + progress);*/

                if (workInfo.getState() != null &&
                        workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                    //String result = workInfo.getOutputData().getString("message");

                    int sSynced = 0;
                    int sDuplicate = 0;
                    StringBuilder sSyncedError = new StringBuilder();
                    JSONArray json;

                    if (result != null) {
                        if (result.length() > 0) {
                            try {
                                Log.d(TAG, "onPostExecute: " + result);
                                json = new JSONArray(result);

                                // DatabaseHelper db = new DatabaseHelper(SyncActivity.this); // Database Helper

                                Method method = null;
                                for (Method method1 : db.getClass().getDeclaredMethods()) {

                                    Log.d(TAG, "onChanged Methods: " + method1.getName());
                                    /**
                                     * MAKE SURE TABLE_NAME = <table> IS SAME AS updateSynced<table> :
                                     *
                                     *      -   public static final String TABLE_NAME = "<table>";  // in Contract
                                     *      -   public JSONArray updateSynced<table>() {              // in DatabaseHelper
                                     *
                                     *      e.g: Forms and updateSyncedForms
                                     *
                                     */
                                    Log.d(TAG, "onChanged Names: updateSynced" + tableName);
                                    Log.d(TAG, "onChanged Compare: " + method1.getName().equals("updateSynced" + tableName));
                                    if (method1.getName().equals("updateSynced" + tableName)) {
                                        method = method1;
                                        //Toast.makeText(SyncActivity.this, "updateSynced not found: updateSynced" + tableName, Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                                if (method != null) {
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObject = new JSONObject(json.getString(i));
                                        Log.d(TAG, "onChanged: " + json.getString(i));
                                        if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {
                                            method.invoke(db, jsonObject.getString("id"));
                                            sSynced++;
                                        } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                                            method.invoke(db, jsonObject.getString("id"));
                                            sDuplicate++;
                                        } else {
                                            sSyncedError.append("\nError: ").append(jsonObject.getString("message"));
                                        }
                                    }
                                    Toast.makeText(SyncActivity.this, tableName + " synced: " + sSynced + "\r\nErrors: " + sSyncedError, Toast.LENGTH_SHORT).show();

                                    if (sSyncedError.toString().equals("")) {
                                        uploadTables.get(position).setmessage(" Synced: " + sSynced + "  •  Duplicates: " + sDuplicate + "  •  Errors: " + sSyncedError);
                                        uploadTables.get(position).setstatus("Completed");
                                        uploadTables.get(position).setstatusID(3);
                                        syncListAdapter.updatesyncList(uploadTables);
                                    } else {
                                        uploadTables.get(position).setmessage(" Synced: " + sSynced + "  •  Duplicates: " + sDuplicate + "  •  Errors: " + sSyncedError);
                                        uploadTables.get(position).setstatus("Process Failed6");
                                        uploadTables.get(position).setstatusID(1);
                                        syncListAdapter.updatesyncList(uploadTables);
                                    }
                                } else {
                                    uploadTables.get(position).setmessage("Method not found: updateSynced" + tableName);
                                    uploadTables.get(position).setstatus("Process Failed5");
                                    uploadTables.get(position).setstatusID(1);
                                    syncListAdapter.updatesyncList(uploadTables);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SyncActivity.this, "Sync Result:  " + result, Toast.LENGTH_SHORT).show();

                                uploadTables.get(position).setmessage(result);
                                uploadTables.get(position).setstatus("Process Failed4");
                                uploadTables.get(position).setstatusID(1);
                                    syncListAdapter.updatesyncList(uploadTables);

                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                uploadTables.get(position).setstatus("Process Failed3");
                                uploadTables.get(position).setstatusID(1);
                                uploadTables.get(position).setmessage(e.getMessage());
                                syncListAdapter.updatesyncList(uploadTables);
                            }
                        } else {
                            uploadTables.get(position).setmessage("Received: " + result.length() + "");
                            uploadTables.get(position).setstatus("Successful");
                            uploadTables.get(position).setstatusID(3);
                            syncListAdapter.updatesyncList(uploadTables);
//                pd.show();
                        }
                    } else {
                        uploadTables.get(position).setstatus("Process Failed2");
                        uploadTables.get(position).setstatusID(1);
                        uploadTables.get(position).setmessage("Server not found!");
                        syncListAdapter.updatesyncList(uploadTables);
//            pd.show();
                    }
                }
                //mTextView1.append("\n" + workInfo.getState().name());
                if (workInfo.getState() != null &&
                        workInfo.getState() == WorkInfo.State.FAILED) {
                    String message = workInfo.getOutputData().getString("error");
                    if (message.equals("No new records to upload")) {
                        uploadTables.get(position).setmessage(message);
                        uploadTables.get(position).setstatus("Not processed");
                        uploadTables.get(position).setstatusID(4);
                        syncListAdapter.updatesyncList(uploadTables);
                    } else {
                        uploadTables.get(position).setstatus("Process Failed");
                        uploadTables.get(position).setstatusID(1);
                        uploadTables.get(position).setmessage(message);
                        syncListAdapter.updatesyncList(uploadTables);
                    }
                }
            }
        });

    }

    public void UploadPhotos(View view) {
        bi.dataLayout.setVisibility(View.GONE);
        bi.photoLayout.setVisibility(View.VISIBLE);
        bi.mTextViewS.setVisibility(View.VISIBLE);
        bi.pBar.setVisibility(View.VISIBLE);
        bi.photoLayout.removeAllViews();
        /*File directory = new File(this.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);*/
        Log.d("Directory", "uploadPhotos: " + sdDir);
        if (sdDir.exists()) {
            File[] files = sdDir.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            sortBySize(files);
            bi.pBar.setProgress(0);
            Log.d("Files", "Count: " + files.length);
            if (files.length > 0) {

                int fcount = Math.min(files.length, 300);
                for (int i = 0; i < fcount; i++) {
                    TextView textView = new TextView(edu.aku.hassannaqvi.dss_matiari.ui.SyncActivity.this);
                    textView.setText("PROCESSING: " + files[i].getName());
                    textView.setId(i);
                    bi.photoLayout.addView(textView);
                    bi.mTextViewS.setText(i + " Photos found (processing...)");

                    int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            totalFiles = files.length;


                            File fileZero = files[finalI];

                            tStart = System.currentTimeMillis();

                            // Log.d("Files", "FileName:" + fileZero.getName());
                            // Log.d("Files", "FileName:" + fileZero.getName());
                            //   SyncAllPhotos syncAllPhotos = new SyncAllPhotos(file.getName(), this);

                            Constraints constraints = new Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build();

                            Data data = new Data.Builder()
                                    .putString("filename", fileZero.getName())
                                    .putInt("fCount", finalI)
                                    .build();

                            //This is the subclass of our WorkRequest

                            OneTimeWorkRequest photoUpload = new OneTimeWorkRequest.Builder(PhotoUploadWorker2.class).setInputData(data).setConstraints(constraints).build();


                            WorkManager.getInstance(SyncActivity.this).enqueue(photoUpload);
                            //Listening to the work status
                            final TextView[] mTextView1 = new TextView[1];

                            WorkManager.getInstance(SyncActivity.this).getWorkInfoByIdLiveData(photoUpload.getId())
                                    .observe(edu.aku.hassannaqvi.dss_matiari.ui.SyncActivity.this, new Observer<WorkInfo>() {

                                        @Override
                                        public void onChanged(@Nullable WorkInfo workInfo) {
                                            //mTextViewS.append("\n" + workInfo.getState().name());
                                            //Displaying the status into TextView
                                            // mTextViewS.append("\n"+workInfo.getState().name());
                                            mTextView1[0] = findViewById(finalI);
                                            String message = workInfo.getState().name() + ": " + files[finalI].getName();
                                            mTextView1[0].setText(message);
                                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                                message = workInfo.getState().name() + ": " + workInfo.getOutputData().getString("message");
                                                mTextView1[0].setText(message);
                                                mTextView1[0].setTextColor(Color.parseColor("#007f00"));
                                                mTextView1[0].animate()
                                                        .translationY(-mTextView1[0].getHeight() * finalI)
                                                        .alpha(0.0f)
                                                        .setDuration(3500)
                                                        .setListener(new AnimatorListenerAdapter() {
                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                super.onAnimationEnd(animation);
                                                                mTextView1[0].setVisibility(View.GONE);
                                                            }
                                                        });
                                                upDatePhotoCount();
                                            }

                                            if (workInfo.getState() == WorkInfo.State.FAILED) {
                                                Toast.makeText(edu.aku.hassannaqvi.dss_matiari.ui.SyncActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                String error = workInfo.getState().name() + ": " + workInfo.getOutputData().getString("error");
                                                mTextView1[0].setText(error);
                                                mTextView1[0].setTextColor(Color.RED);

                                                upDatePhotoCount();
                                            }

                                            if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                                                //String message = workInfo.getState().name() + ": " + workInfo.getOutputData().getString("message");
                                                mTextView1[0].setText("CANCELLED: " + fileZero.getName());
                                                mTextView1[0].setTextColor(Color.RED);

                                                upDatePhotoCount();
                                            }
                                    /*String fCount = String.valueOf(workInfo.getOutputData().getInt("fCount", 0));
                                    Toast.makeText(SyncActivity.this, "Files Left: " + fCount, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });

                            if (finalI % 25 == 0) {
                                try {
                                    //3000 ms delay to process upload of next file.
                                    Thread.sleep(14000);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 300);
                }

                bi.mTextViewS.setText(totalFiles + " Photos found (waiting for server...)");

            } else {
                bi.mTextViewS.setText(files.length + " Photos remaining");

                Toast.makeText(this, "No photos to upload.", Toast.LENGTH_SHORT).show();
            }
        } else {
            bi.mTextViewS.setText("No photots were taken.");

            Toast.makeText(this, "No photos were taken", Toast.LENGTH_SHORT).show();
        }
    }

    private void upDatePhotoCount() {
        if (sdDir.exists()) {
            Log.d("DIR", "onCreate: " + sdDir.getName());
            File[] files = sdDir.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));


            if (files.length < totalFiles) {

                int filesProcessed = totalFiles - files.length;
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                String elapsedSeconds = String.format("%dh:%dm %ds",
                        TimeUnit.MILLISECONDS.toHours(tDelta),
                        TimeUnit.MILLISECONDS.toMinutes(tDelta),
                        TimeUnit.MILLISECONDS.toSeconds(tDelta) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tDelta))
                );
                long tPerFile = tDelta / filesProcessed;
                long timeRemaining = files.length * tPerFile;
                long absTimeRemaining = ((timeRemaining / 25) * 14) + timeRemaining;
                double secRemain = absTimeRemaining / 1000;
                String tRemain = String.format("%dh:%dm %ds",
                        TimeUnit.MILLISECONDS.toHours(absTimeRemaining),
                        TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining),
                        TimeUnit.MILLISECONDS.toSeconds(absTimeRemaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining))
                );

                // WORK MANAGER
                progress = files.length + "/" + totalFiles + " Photos remaining \r\nTime remaining: " + tRemain;
                if (absTimeRemaining > 0 && files.length > 0) {
                    if (TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining / files.length) > 1) {
                        progress += "\r\n (!) - slow internet detected";
                    }
                }
                bi.mTextViewS.setText(progress);
                int fProgress = (int) Math.ceil(((totalFiles - files.length) * 100) / totalFiles);
                bi.pBar.setProgress(fProgress);
                //This is for setting Contraints for sync

            } else {
                bi.mTextViewS.setText(progress + "\r\n DONE!");

            }
        } else {
            bi.mTextViewS.setText("No photos found.");
        }
    }

    private void sortBySize(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File t, File t1) {
                return (int) (t.length() - t1.length());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //onBackPressed();
                finish();
                //   downloadApp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*    private void downloadApp() throws MalformedURLException {

        URL url = new URL(MainApp._HOST_URL + _UPDATE_URL);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addDataScheme("package");
        registerReceiver(br, intentFilter);

    }*/


}
