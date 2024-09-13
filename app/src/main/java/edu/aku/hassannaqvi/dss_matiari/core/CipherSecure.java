package edu.aku.hassannaqvi.dss_matiari.core;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.IBAHC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.TRATS;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class CipherSecure {
    private static final String TAG = "CipherSecure";

    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 16;

    public static String encryptGCM(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        byte[] iv = new byte[IV_LENGTH];
        (new SecureRandom()).nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH * Byte.SIZE, iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(hashSHA256().getBytes(StandardCharsets.UTF_8), "AES"), ivSpec);

        byte[] ciphertext = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] encrypted = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);
        String encoded = Base64.encodeToString(encrypted, Base64.NO_WRAP);

        return encoded;
    }

    public static String decryptGCM(String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        byte[] decoded = Base64.decode(encrypted, Base64.NO_WRAP);
        byte[] iv = Arrays.copyOfRange(decoded, 0, IV_LENGTH);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH * Byte.SIZE, iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hashSHA256().getBytes(StandardCharsets.UTF_8), "AES"), ivSpec);
        byte[] ciphertext = cipher.doFinal(decoded, IV_LENGTH, decoded.length - IV_LENGTH);
        String newString = new String(ciphertext, StandardCharsets.UTF_8);
        return newString;
    }

    public static SSLSocketFactory buildSslSocketFactory(Context context) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            AssetManager assetManager = context.getAssets();
            InputStream caInput = assetManager.open("vcoe1_aku_edu.cer");
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
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

            // Create an SSLContext that uses our TrustManager
            SSLContext context1 = SSLContext.getInstance("TLSv1.2");
            context1.init(null, tmf.getTrustManagers(), null);
            return context1.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean certIsValid(Certificate[] certs, Certificate ca) {
        for (Certificate cert : certs) {
            if (cert instanceof X509Certificate) {
                try {
                    ((X509Certificate) cert).checkValidity();
                    if (cert.equals(ca)) {
                        return true;
                    }
                } catch (CertificateExpiredException | CertificateNotYetValidException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public static String hashSHA256()
            throws NoSuchAlgorithmException {
        String input = IBAHC;
        MessageDigest mDigest = MessageDigest.getInstance("SHA-384");
        byte[] shaByteArr = mDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(shaByteArr, Base64.NO_WRAP).substring(TRATS, TRATS + 32);
    }
}

