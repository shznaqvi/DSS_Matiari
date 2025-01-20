package edu.aku.hassannaqvi.dss_matiari.webcall.web_client;

import android.content.Context;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class CryptoUtil {

    private static final String cypherInstance = "AES/GCM/NoPadding";

    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 16;

    private static X509TrustManager x509;

    /* WEB CALL CIPHERING - START */

    // Web call encryption
    public static String encrypt(String data) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            (new SecureRandom()).nextBytes(iv);
            Cipher cipher = Cipher.getInstance(cypherInstance);
            GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH * Byte.SIZE, iv);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(hashSHA256().getBytes(StandardCharsets.UTF_8), "AES"), ivSpec);
            byte[] ciphertext = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] encrypted = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, encrypted, 0, iv.length);
            System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Web call decryption
    public static String decrypt(String data) {
        try {
            byte[] decoded = Base64.decode(data, Base64.NO_WRAP);
            byte[] iv = Arrays.copyOfRange(decoded, 0, IV_LENGTH);
            Cipher cipher = Cipher.getInstance(cypherInstance);
            GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH * Byte.SIZE, iv);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hashSHA256().getBytes(StandardCharsets.UTF_8), "AES"), ivSpec);
            byte[] ciphertext = cipher.doFinal(decoded, IV_LENGTH, decoded.length - IV_LENGTH);
            return new String(ciphertext, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String hashSHA256()
            throws NoSuchAlgorithmException {
        String input = AppConstants.IBAHC;
        MessageDigest mDigest = MessageDigest.getInstance("SHA-384");

        byte[] shaByteArr = mDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(shaByteArr, Base64.NO_WRAP).substring(AppConstants.TRATS,
                AppConstants.TRATS + 32);
    }/*

     *//* WEB CALL CIPHERING - END *//*

     *//* WEB CALL SSL/TLS VERIFICATION - START *//*

     *//* public static SSLContext getSSLContext(Activity activity) {
        SSLContext sslContext = null;
        try {
            sslContext = createCertificate(activity);
        } catch (CertificateException | IOException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private static SSLContext createCertificate(Activity activity)
            throws CertificateException, IOException, KeyStoreException, KeyManagementException, NoSuchAlgorithmException {

        Certificate ca = getValidCertificate(activity);

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, tmf.getTrustManagers(), null);
        x509 = (X509TrustManager) tmf.getTrustManagers()[0];

        return sslContext;
    }

    public static X509TrustManager getX509TrustManager() {
        return x509;
    }*//*

    // Get valid certificate from assets folder
    public static Certificate getValidCertificate(Activity activity) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = activity.getResources().openRawResource(R.raw.pedres3_aku_edu);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
            } finally {
                caInput.close();
            }
            return ca;
        } catch (CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Check server certificate validity
    public static boolean checkCertValidity(Activity activity, SSLSession session) {
        Certificate[] certs;
        try {
            certs = session.getPeerCertificates();
        } catch (SSLException e) {
            e.printStackTrace();
            return false;
        }

        for (Certificate cert : certs) {
            if (cert instanceof X509Certificate) {
                try {
                    ((X509Certificate) cert).checkValidity();
                    if (cert.equals(getValidCertificate(activity))) return true;
                } catch (CertificateExpiredException | CertificateNotYetValidException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    *//* WEB CALL SSL/TLS VERIFICATION - END *//*
     */

    public static OkHttpClient generateSecureOkHttpClient(Context context) {
        try {
            // For logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            // Load the custom PEM certificate from resources
            InputStream inputStream = context.getResources().openRawResource(R.raw.pedres3_aku_edu);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            // Create KeyStore and load certificate
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            Certificate certificate = certificateFactory.generateCertificate(inputStream);
            keyStore.setCertificateEntry("ca", certificate);
            inputStream.close();

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Set up SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Build OkHttpClient with the SSLContext
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                    .addInterceptor(loggingInterceptor)
                    .build();
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a secure OkHttpClient", e);
        }
    }

}
