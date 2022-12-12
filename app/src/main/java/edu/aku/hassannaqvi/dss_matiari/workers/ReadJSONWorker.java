package edu.aku.hassannaqvi.dss_matiari.workers;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;


public class ReadJSONWorker extends Worker {

    private final String TAG = "DataWorkerEN()";
    HttpsURLConnection urlConnection;
    private int length;
    private DssRoomDatabase db;

    public ReadJSONWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        // to be initialised by workParams
        //  uploadTable = workerParams.getInputData().getString("table");
        // position = workerParams.getInputData().getInt("position", -2);
        // Log.d(TAG, "DataDownWorkerALL: position " + position);
        //uploadColumns = workerParams.getInputData().getString("columns");
        //  uploadWhere = workerParams.getInputData().getString("where");
    }

    /*
     * This method is responsible for doing the work
     * so whatever work that is needed to be performed
     * we will put it here
     *
     * For example, here I am calling the method displayNotification()
     * It will display a notification
     * So that we will understand the work is executed
     * */

    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "doWork: Starting");
        String nTitle = "ZScore Data";
        displayNotification(nTitle, "Starting upload");

        StringBuilder result = new StringBuilder();

        Data data;
        result = new StringBuilder();
        result.append(loadJSONFromAsset());

        //Do something with the JSON string
        if (result != null) {
            displayNotification(nTitle, "Starting Data Processing");

            //String json = result.toString();
            /*if (json.length() > 0) {*/
            displayNotification(nTitle, "Data Sise: " + result.length());


            // JSONArray jsonArray = new JSONArray(json);


            //JSONObject jsonObjectCC = jsonArray.getJSONObject(0);
            ///BE CAREFULL DATA.BUILDER CAN HAVE ONLY 1024O BYTES. EACH CHAR HAS 8 bits

            //   MainApp.downloadData[this.position] = String.valueOf(result);
            data = new Data.Builder()
                    //    .putString("data", String.valueOf(result))
                    //      .putInt("position", this.position)
                    .build();

            db = MainApp.appInfo.dbHelper;
           /* try {
                //    Log.d(TAG, "doWork: JSON: "+result);
                db.syncZStandard(new JSONArray(result.toString()));
                return Result.success(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/


            //displayNotification(nTitle, "Uploaded successfully");
            Log.d(TAG, "doWork: " + result);
            Log.d(TAG, "doWork (success) : position " + data.getInt("position", -1));
            return Result.success(data);

        } else {
            data = new Data.Builder()
                    .putString("error", String.valueOf(result))
                    //      .putInt("position", this.position)
                    .build();
            displayNotification(nTitle, "Error Received");
            return Result.failure(data);
        }


    }

    /*
     * The method is doing nothing but only generating
     * a simple notification
     * If you are confused about it
     * you should check the Android Notification Tutorial
     * */
    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", PROJECT_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "1")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        final int maxProgress = 100;
        int curProgress = 0;
        notification.setProgress(length, curProgress, false);

        notificationManager.notify(1, notification.build());
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {


            InputStream is = getApplicationContext().getAssets().open("zStandardJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}