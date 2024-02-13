package edu.aku.hassannaqvi.dss_matiari.core;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;

public class UserAuth {

    private final static String PBKDF2_NAME = "PBKDF2WithHmacSHA1";
    private final static int HASH_BYTE_SIZE = 16;
    private final static int SALT_BYTE_SIZE = 16;
    private final static int ITERATIONS = 1000;

    public static String generatePassword(String password, byte[] oldSalt) {
        try {
            char[] chars = password.toCharArray();
            byte[] salt;
            if (oldSalt == null)
                salt = getSalt();
            else
                salt = oldSalt;

            PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, HASH_BYTE_SIZE);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_NAME);

            byte[] hash = skf.generateSecret(spec).getEncoded();
            byte[] salt_hash = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, salt_hash, 0, salt.length);
            System.arraycopy(hash, 0, salt_hash, salt.length, hash.length);
            return Base64.encodeToString(salt_hash, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return AppConstants._EMPTY_;
    }

    public static boolean checkPassword(String password, String oldPassword) {
        byte[] salt = Arrays.copyOfRange(Base64.decode(oldPassword, Base64.NO_WRAP), 0, SALT_BYTE_SIZE);
        String genPass = generatePassword(password, salt);
        return Objects.requireNonNull(genPass).equals(oldPassword);
    }

    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    /*private static final String TAG = "UserAuth";
    //private final static String PBKDF2_NAME = "PBKDF2WithHmacSHA512";
    private final static String PBKDF2_NAME = "PBKDF2WithHmacSHA1";
    private final static int HASH_BYTE_SIZE = 16;
    private final static int SALT_BYTE_SIZE = 16;
    private final static int ITERATIONS = 1000;


    public static String generatePassword(String password, byte[] oldsalt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        Log.d(TAG, "oldSalt: " + oldsalt);
        char[] chars = password.toCharArray();
        byte[] salt;
        if (oldsalt == null) {
            salt = getSalt();
            Log.d(TAG, "getSalt(): " + salt);

        } else {
            salt = oldsalt;
        }

        PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, HASH_BYTE_SIZE);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_NAME);


        byte[] hash = skf.generateSecret(spec).getEncoded();
        byte[] salt_hash = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, salt_hash, 0, salt.length);
        System.arraycopy(hash, 0, salt_hash, salt.length, hash.length);
        Log.d(TAG, "generatePassword: " + Base64.encodeToString(salt_hash, Base64.NO_WRAP));
        return Base64.encodeToString(salt_hash, Base64.NO_WRAP);
    }


    public static boolean checkPassword(String password, String oldPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

//        byte[] salt = Base64.decode(oldPassword.substring(0, 24), Base64.NO_WRAP);
        byte[] salt = Arrays.copyOfRange(Base64.decode(oldPassword, Base64.NO_WRAP), 0, SALT_BYTE_SIZE);
        Log.d(TAG, "oldStorngPasswordHash: " + oldPassword);

        String genPass = generatePassword(password, salt);

        return genPass.equals(oldPassword);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return salt;
    }*/


}
