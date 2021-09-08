package edu.aku.hassannaqvi.dss_matiari.workers;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp._PHOTO_UPLOAD_URL;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sdDir;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class PhotoUploadWorker2 extends Worker {

    private final String TAG = "PhotoUploadWorker2()";
    private final Context mContext;
    private final int photoid;
    private final String nTitle = "Naunehal: Photo Upload";
    public Boolean errMsg = false;
    HttpURLConnection urlConnection;
    File fileZero;
    private Data data;

    // private File sdDir;

    public PhotoUploadWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
      /*  sdDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);*/

        fileZero = new File(workerParams.getInputData().getString("filename"));
        photoid = new Random().nextInt();
        // sdDir = new File("/storage/emulated/0/Pictures/UenTmkEl2020/uploaded/");
//        Log.d(TAG, "PhotoUploadWorker: " + sdDir.getAbsolutePath());
        displayNotification(fileZero.toString(), "Starting...", 100, 0);
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


        /*File directory = new File(mContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);*/

        //Log.d("Files", "FileName:" + fileZero.getName());
/*                    SyncAllPhotos syncAllPhotos = new SyncAllPhotos(file.getName(), this);
                    syncAllPhotos.execute();*/

        String res = null;
        try {
            res = uploadPhoto(String.valueOf(new File(sdDir + File.separator + fileZero.getName())));


        } catch (Exception e) {
            Log.d(TAG, "doWork: " + e.getMessage());
            displayNotification(fileZero.toString(), "Error: " + e.getMessage(), 100, 0);

            data = new Data.Builder()
                    .putString("error", "1 " + e.getMessage()).build();
            return Result.failure(data);
        } finally {
            if (errMsg) {
                return Result.failure(data);
            }
        }

        onPostExecute(res, fileZero.getName());
        return errMsg ? Result.failure(data) : Result.success(data);
        //return Result.success(data);

    }


    /*
     * The method is doing nothing but only generating
     * a simple notification
     * If you are confused about it
     * you should check the Android Notification Tutorial
     * */
    private void displayNotification(String title, String task, int maxProgress, int curProgress) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", nTitle, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        if (curProgress >= maxProgress) {

            notification.setContentTitle("[DONE] " + title);

            notification.setTimeoutAfter(3500);
        }
        notification.setProgress(maxProgress, curProgress, false);


        notificationManager.notify(photoid, notification.build());

    }

    private void moveFile(String inputFile) {
        Log.d(TAG, "moveFile: " + inputFile);
        /*sdDir = new File(mContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);*/
        InputStream in = null;
        OutputStream out = null;
        File inputPath = sdDir;
        File outputPath = new File(sdDir + File.separator + "uploaded");
        try {

            //create output directory if it doesn't exist (not needed, just a precaution)
            //File dir = getDir(1);
            if (!outputPath.exists()) {
                outputPath.mkdirs();
            }

            in = new FileInputStream(inputPath + File.separator + inputFile);
            out = new FileOutputStream(outputPath + File.separator + inputFile);
            Log.d(TAG, "moveFile: (in)" + in);
            Log.d(TAG, "moveFile: (out)" + out);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + File.separator + inputFile).delete();

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private void onPostExecute(String result, String fileName) {
        displayNotification(fileZero.toString(), "Processing...", 100, 0);

        JSONArray json;


        try {
            Log.d(TAG, "onPostExecute: " + result);
            // json = new JSONArray(result);


            JSONObject jsonObject = new JSONObject(result);

            if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {

                //TODO:   db.updateUploadedPhoto(jsonObject.getString("id"));  // UPDATE SYNCED
                displayNotification(fileZero.toString(), "Successfully Uploaded.", 100, 0);

                data = new Data.Builder()
                        .putString("message", fileName).build();
                errMsg = false;
                moveFile(fileName);


            } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                displayNotification(fileZero.toString(), "Duplicate file.", 100, 0);

                data = new Data.Builder()
                        .putString("message", "Duplicate: " + fileName).build();
                errMsg = false;
                moveFile(fileName);


            } else {
                displayNotification(fileZero.toString(), "Error: " + jsonObject.getString("message"), 100, 0);

                data = new Data.Builder()
                        .putString("error", jsonObject.getString("message")).build();
                errMsg = true;
            }
            //syncStatus.setText(syncStatus.getText() + "\r\nDone uploading +" + syncClass + " data");

        } catch (JSONException e) {
            e.printStackTrace();
            //syncStatus.setText(syncStatus.getText() + "\r\n" + syncClass + " Sync Failed");
            displayNotification(fileZero.toString(), "Error: " + e.getMessage(), 100, 0);

            data = new Data.Builder()
                    .putString("error", "2 " + e.getMessage()).build();
            errMsg = true;
        }
    }


    private String uploadPhoto(String filepath) {
        displayNotification(fileZero.toString(), "Connecting...", 100, 0);

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + System.currentTimeMillis() + "*****";
        String lineEnd = "\r\n";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        String filefield = "image";

        String[] q = filepath.split("/");
        int idx = q.length - 1;

        File file = new File(filepath);
        FileInputStream fileInputStream = null;
        Log.d(TAG, "uploadPhoto: (" + file.length() + ")" + file);

        try {
            fileInputStream = new FileInputStream(file);

            URL url = null;
            try {
                url = new URL(_PHOTO_UPLOAD_URL);
            } catch (MalformedURLException e) {
                Log.d(TAG, "uploadPhoto: " + e.getMessage());
                return e.getMessage();
            }
            Log.d(TAG, "uploadPhoto: " + file);

            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/households-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: households-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            int progress = 0;
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                Log.d(TAG, "uploadPhoto: " + bytesRead);
                Log.d(TAG, "uploadPhoto: " + buffer.length);
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                progress += bytesRead;
                // update progress bar
                // publishProgress(progress);
                displayNotification(fileZero.toString(), "Connected to server...", 100, Math.round(progress * 100 / file.length()));

            }

            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: households-data; name=\"tagname\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(MainApp.appInfo.getTagName() == null ? "" : MainApp.appInfo.getTagName());  // DEVICETAG
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            inputStream = connection.getInputStream();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                displayNotification(fileZero.toString(), "Connected to server...", 100, 0);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {


                    response.append(inputLine);
                    displayNotification(fileZero.toString(), "Uploading...", 100, 0);
                }

                inputStream.close();
                connection.disconnect();
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();

                return response.toString();
            }
            return String.valueOf(status);
        } catch (FileNotFoundException | ProtocolException e) {
            e.printStackTrace();
            data = new Data.Builder()
                    .putString("error", "1 " + e.getMessage()).build();
            errMsg = true;
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            data = new Data.Builder()
                    .putString("error", "1 " + e.getMessage()).build();
            errMsg = true;

            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            data = new Data.Builder()
                    .putString("error", "1 " + e.getMessage()).build();
            errMsg = true;

            return e.getMessage();
        }
    }

}