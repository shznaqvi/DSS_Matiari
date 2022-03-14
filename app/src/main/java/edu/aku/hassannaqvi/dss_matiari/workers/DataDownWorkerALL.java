package edu.aku.hassannaqvi.dss_matiari.workers;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.CipherSecure;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;


public class DataDownWorkerALL extends Worker {

    private final String TAG = "DataDownWorkerALL";

    private final int position;
    private final Context mContext;
    private final String uploadTable;
    private final String uploadWhere;
    private final String uploadColumns;
    HttpsURLConnection urlConnection;
    private long startTime;
    private int responseLength = 0, requestLength = 0;


    public DataDownWorkerALL(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
        uploadTable = workerParams.getInputData().getString("table");
        position = workerParams.getInputData().getInt("position", -2);
        uploadColumns = workerParams.getInputData().getString("select");
        uploadWhere = workerParams.getInputData().getString("where");

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

    private static SSLSocketFactory buildSslSocketFactory(Context context) {
        try {


            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            AssetManager assetManager = context.getAssets();
            InputStream caInput = assetManager.open("pedres2_aku_edu.cer");
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
/*

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
            */
            // Create an SSLContext that uses our TrustManager
            SSLContext context1 = SSLContext.getInstance("TLSv1.2");
            context1.init(null, tmf.getTrustManagers(), null);
            return context1.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public Result doWork() {
        startTime = System.currentTimeMillis();

        String nTitle = uploadTable + " : Data Upload";

        StringBuilder result = new StringBuilder();
        CipherSecure cipherSecure = new CipherSecure();

        URL url;
        Data data;
        InputStream caInput = null;
        Certificate ca = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            AssetManager assetManager = mContext.getAssets();
            caInput = assetManager.open("pedres2_aku_edu.cer");


            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
            Log.d(TAG + " : " + uploadTable, "doWork: URL: " + url);

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    //Logcat.d(hostname + " / " + apiHostname);
                    Log.d(TAG + " : " + uploadTable, "verify: hostname " + hostname);
                    return true;
                }
            };
            //HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(buildSslSocketFactory(mContext));

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("User-Agent", "SAMSUNG SM-T295");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setUseCaches(false);
            startTime = System.currentTimeMillis();
            urlConnection.connect();

            Certificate[] certs = urlConnection.getServerCertificates();

            if (certIsValid(certs, ca)) {


                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

                JSONObject jsonTable = new JSONObject();
                JSONArray jsonParam = new JSONArray();

                jsonTable.put("table", uploadTable);
                jsonTable.put("select", uploadColumns);
                jsonTable.put("filter", uploadWhere);

                jsonTable.put("check", "");

                if (uploadTable.equals(TableContracts.VersionTable.TABLE_NAME)) {
                    jsonTable.put("folder", "/");
                }

                //jsonTable.put("limit", "3");
                //jsonTable.put("orderby", "rand()");
                //jsonSync.put(uploadData);
                jsonParam.put(jsonTable);
                // .put(jsonSync);


                Log.d(TAG + " : " + uploadTable, "doWork: jsonTable: " + jsonTable);
                String cipheredRequest = CipherSecure.encrypt(String.valueOf(jsonTable));
                requestLength = cipheredRequest.length();
                wr.writeBytes(cipheredRequest);

                Log.d(TAG, "Content Length: " + uploadTable + " Request " + cipheredRequest.length());

                Log.d(TAG + " : " + uploadTable, "doWork: Encrypted: " + cipheredRequest);
                wr.flush();
                wr.close();


                if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                    responseLength = urlConnection.getContentLength();
                    Log.d(TAG, "Content Length: " + uploadTable + " conn " + responseLength);

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    Log.d(TAG + " : " + uploadTable, "doWork: result-server: " + result);
                    Log.d(TAG, "Content Length: " + uploadTable + " response " + result.length());
                    try {
                        result = new StringBuilder(CipherSecure.decrypt(result.toString()));

                    } catch (IllegalArgumentException e) {
                        data = new Data.Builder()
                                .putString("time", getTime())
                                .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                                .putString("error", e.getMessage() + " | " + Html.fromHtml(String.valueOf(result)))
                                .putInt("position", this.position)
                                .build();
                        return Result.failure(data);
                    }
                    Log.d(TAG + " : " + uploadTable, "doWork: result-decrypt: " + result);

                    if (result.toString().equals("[]")) {
                        data = new Data.Builder()
                                .putString("time", getTime())
                                .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                                .putString("error", "No data received from server: " + result)
                                .putInt("position", this.position)
                                .build();
                        return Result.failure(data);
                    }
                } else {
                    data = new Data.Builder()
                            .putString("time", getTime())
                            .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                            .putString("error", String.valueOf(urlConnection.getResponseCode()))
                            .putInt("position", this.position)
                            .build();
                    return Result.failure(data);
                }
            } else {
                data = new Data.Builder()
                        .putString("time", getTime())
                        .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                        .putString("error", "Invalid Certificate")
                        .putInt("position", this.position)
                        .build();

                return Result.failure(data);
            }
        } catch (java.net.SocketTimeoutException e) {
            data = new Data.Builder()
                    .putString("time", getTime())
                    .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                    .putString("error", e.getMessage())
                    .putInt("position", this.position)
                    .build();
            return Result.failure(data);

        } catch (SSLPeerUnverifiedException e) {
            Toast.makeText(mContext, "(SSLPeerUnverifiedException): %s" + e.getMessage(), Toast.LENGTH_SHORT).show();
            data = new Data.Builder()
                    .putString("time", getTime())
                    .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                    .putString("error", e.getMessage())
                    .putInt("position", this.position)
                    .build();

            return Result.failure(data);
        } catch (IOException | JSONException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            data = new Data.Builder()
                    .putString("time", getTime())
                    .putString("size", getSize(requestLength) + "/" + getSize(responseLength))
                    .putString("error", e.getMessage())
                    .putInt("position", this.position)
                    .build();

            return Result.failure(data);

        }

        ///BE CAREFULL DATA.BUILDER CAN HAVE ONLY 1024O BYTES. EACH CHAR HAS 8 bits
        MainApp.downloadData[this.position] = String.valueOf(result);

        data = new Data.Builder()
                .putString("time", getTime())
                .putString("size", getSize(requestLength) + "/" + getSize(responseLength))

                //     .putString("data", String.valueOf(result))
                .putInt("position", this.position)
                .build();


        return Result.success(data);
    }

    private String getSize(int length) {
        if (length < 0) return "0B";
        double lengthM = length / 1024 / 1024;
        return lengthM > 1 ? lengthM + "MB" : (length / 1024) > 1 ? (length / 1024) + "KB" : length + "B";
    }

    private String getTime() {

        long timeElapsed = System.currentTimeMillis() - startTime;
        long toMinutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed);
        long toSeconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed - (toMinutes * 60 * 1000));

        return toMinutes > 0 ? toMinutes + "m " + toSeconds + "s" : toSeconds > 0 ? TimeUnit.MILLISECONDS.toSeconds(timeElapsed) + "s" : timeElapsed + "ms";
    }

    private boolean certIsValid(Certificate[] certs, Certificate ca) {
        for (Certificate cert : certs) {
            System.out.println("Certificate is: " + cert);
            if (cert instanceof X509Certificate) {

                try {
                    ((X509Certificate) cert).checkValidity();

                    System.out.println("Certificate is active for current date");
                    if (cert.equals(ca)) {

                        return true;
                    }
                    //  Toast.makeText(mContext, "Certificate is active for current date", Toast.LENGTH_SHORT).show();
                } catch (CertificateExpiredException | CertificateNotYetValidException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return false;
    }

}