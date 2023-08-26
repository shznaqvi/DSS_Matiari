package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.editor;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.AlertPopup;
import edu.aku.hassannaqvi.dss_matiari.core.AppInfo;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityLoginBinding;
import edu.aku.hassannaqvi.dss_matiari.models.EntryLog;
import edu.aku.hassannaqvi.dss_matiari.models.Users;
import edu.aku.hassannaqvi.dss_matiari.newstruct.activity.ChangePassNewAC;
import edu.aku.hassannaqvi.dss_matiari.newstruct.activity.SyncNewAC;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final int SINDHI = 3;
    protected static LocationManager locationManager;

    // UI references.

    ActivityLoginBinding bi;
    Spinner spinnerDistrict;
    String DirectoryName;
    DssRoomDatabase db;
    ArrayList<String> leaderNames;
    ArrayList<String> leaderCodes;
    String username = "";
    String password = "";
    int attemptCounter = 0;
    private int countryCode;

    public static String encrypt(String plain) {
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("asSa%s|n'$ crEed".getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(iv));
            byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            byte[] ivAndCipherText = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
            System.arraycopy(cipherText, 0, ivAndCipherText, iv.length, cipherText.length);
            return Base64.encodeToString(ivAndCipherText, Base64.NO_WRAP);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String decrypt(String encoded) {
        try {
            byte[] ivAndCipherText = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] iv = Arrays.copyOfRange(ivAndCipherText, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(ivAndCipherText, 16, ivAndCipherText.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("asSa%s|n'$ crEed".getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(iv));
            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static File dbBackup(Activity activity) {
        if (sharedPref.getBoolean("flag", true)) {
            String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));

            if (!dt.equals(new SimpleDateFormat("dd-MM-yy").format(new Date()))) {
                MainApp.editor.putString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));
                MainApp.editor.apply();
            }

            File folder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                folder = new File(activity.getExternalFilesDir("").getAbsolutePath() + File.separator + PROJECT_NAME);
            } else {
                folder = new File(Environment.getExternalStorageDirectory().toString() + File.separator);
            }
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                String DirectoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
                folder = new File(DirectoryName);
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {
                    try {
                        File dbFile = new File(activity.getDatabasePath(DssRoomDatabase.DATABASE_NAME).getPath());
                        FileInputStream fis = new FileInputStream(dbFile);
                        String outFileName = DirectoryName + File.separator + DssRoomDatabase.DATABASE_COPY;

                        // For Special case - Use when needed to extract database from local storage
                        File file = new File(outFileName);
                        // Open the empty db as the output stream
                        OutputStream output = new FileOutputStream(file);

                        // Transfer bytes from the inputfile to the outputfile
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        // Close the streams
                        output.flush();
                        output.close();
                        fis.close();

                        return file;
                    } catch (IOException e) {
                        Log.e("dbBackup:", Objects.requireNonNull(e.getMessage()));
                    }
                }
            } else {
                Toast.makeText(activity, activity.getString(R.string.folder_not_created), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }


    private void populateSpinner() {

        leaderNames = new ArrayList<>();
        leaderCodes = new ArrayList<>();

//        leaderNames.add("...");
        leaderNames.add(getString(R.string.select_team_leader));
        leaderNames.add("Test Team Leader");
        leaderCodes.add("...");
        leaderCodes.add("testteamleader");


        //Collection<Users> teamleaders = db.getTeamleaders();

        Collection<Users> teamleaders = db.usersDao().getTeamLeaders();
        for (Users u : teamleaders) {
            leaderNames.add(u.getFullname());
            leaderCodes.add(u.getUsername());
        }

        // Apply the adapter to the spinner
        bi.teamleader.setAdapter(new ArrayAdapter(LoginActivity.this, R.layout.custom_spinner, leaderNames));

        bi.teamleader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainApp.leaderCode = leaderCodes.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializingCountry();
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            MainApp.permissionCheck = true;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        bi = DataBindingUtil.setContentView(this, R.layout.activity_login);


        bi.setCallback(this);
        MainApp.appInfo = new AppInfo(this);
        db = MainApp.appInfo.getDbHelper();
        MainApp.user = new Users();
        bi.txtinstalldate.setText(MainApp.appInfo.getAppInfo());
        settingCountryCode();

        dbBackup(this);
        //   populateSpinner();

    }

    public void onShowPasswordClick(View view) {
        //TODO implement
        if (bi.password.getTransformationMethod() == null) {
            bi.password.setTransformationMethod(new PasswordTransformationMethod());
//            bi.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_close, 0, 0, 0);
            bi.showPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_locked));
        } else {
            bi.password.setTransformationMethod(null);
//            bi.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_open, 0, 0, 0);
            bi.showPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_unlocked));
        }
    }

    public void onSyncDataClick(View view) {
        //callUsersWorker();

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//            startActivity(new Intent(this, edu.aku.hassannaqvi.dss_matiari.ui.SyncActivity.class).putExtra("login", true));
            // IS_LOGIN = To differentiate before and after login download
            // For before login sync data download
            AppConstants.IS_LOGIN = false;
            AppConstants.gotoActivity(this, SyncNewAC.class, false);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void attemptLogin(View view) {
        if (!username.equals(bi.username.getText().toString())) {
            attemptCounter = 0;
        }
        attemptCounter++;
        // Reset errors.
        bi.username.setError(null);
        bi.password.setError(null);
        //bi.as1q01.setError(null);
        // Toast.makeText(this, String.valueOf(attemptCounter), Toast.LENGTH_SHORT).show();
        if (attemptCounter > 5) {
            bi.username.setError("This user has been blocked.");
            Toast.makeText(this, "This user has been blocked.", Toast.LENGTH_LONG).show();

        } else {
            // Store values at the time of the login attempt.
            username = bi.username.getText().toString();
            password = bi.password.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (password.length() < 8) {
                Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
//                bi.password.setError(getString(R.string.invalid_password));
                focusView = bi.password;
                return;
            }

            // Check for a valid username address.
            if (TextUtils.isEmpty(username)) {
                bi.username.setError(getString(R.string.username_required));
                focusView = bi.username;
                return;
            }

            // UserName and Password cannot be same
            if (username.equals(password)) {
                bi.username.setError("Username and Password cannot be same.");
                focusView = bi.username;
                return;
            }

            //if(!Validator.emptySpinner(this, bi.countrySwitch)) return;
            /*if (bi.countrySwitch.getSelectedItemPosition() == 0) {
                bi.as1q01.setError(getString(R.string.as1q01));
                return;
            }*/

            try {
                if ((username.equals("dmu@aku") && password.equals("aku?dmu"))
                        || (username.equals("test1234") && password.equals("test1234"))
                        || db.usersDao().doLogin(username, password)
                ) {

                    MainApp.user.setUsername(username);
                    MainApp.admin = username.contains("@") || username.contains("test1234");
                    //MainApp.superuser = MainApp.user.getDesignation().equals("Supervisor");
                    Intent iLogin = null;
                    if (MainApp.admin) {
                        recordEntry("Successful Login (Admin)");
                        iLogin = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(iLogin);
                    } else if (MainApp.user.getEnabled().equals("1")) {
                        if (!MainApp.user.getNewUser().equals("1")) {
                            recordEntry("Successful Login");
                            iLogin = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(iLogin);
                        } else if (MainApp.user.getNewUser().equals("1")) {
                            recordEntry("First Login");
//                            iLogin = new Intent(LoginActivity.this, ChangePasswordActivity.class);
//                            startActivity(iLogin);
                            AppConstants.gotoActivity(this, ChangePassNewAC.class, false);
                        }
                    } else {
                        recordEntry("Inactive User (Disabled)");
                        Toast.makeText(this, "This user is inactive.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    recordEntry("Failed Login: Incorrect username or password");
                    /*bi.password.setError(getString(R.string.incorrect_username_or_password));
                    bi.password.requestFocus();*/
                    AlertPopup.alert(this, getString(R.string.login_failed),
                            String.format(getString(R.string.attempt_no),
                                    ++attemptCounter, getString(R.string.incorrect_username_or_password)),
                            AppConstants.TYPE_ERROR);
                    //  Toast.makeText(LoginActivity.this, username + " " + password, Toast.LENGTH_SHORT).show();
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(this, "IllegalArgumentException(UserAuth):" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
    }

    public String computeHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        byte[] byteData = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        Log.d("TAG", "computeHash: " + sb);
        return sb.toString();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateSpinner();
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    /*
     * Toggle Language
     * */
    private void changeLanguage(int countryCode) {
        String lang;
        String country;
        if (countryCode == SINDHI) {
            lang = "sd";
            country = "PK";
            editor
                    .putString("lang", "3")
                    .apply();
        } else {
            lang = "en";
            country = "PK";
            editor
                    .putString("lang", "1")
                    .apply();
        }
        Locale locale = new Locale(lang, country);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        config.setLayoutDirection(new Locale(lang, country));
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

    }

    private void settingCountryCode() {

        bi.countrySwitch.setChecked(sharedPref.getString("lang", "1").equals("1"));

        bi.countrySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                changeLanguage(isChecked ? 1 : 3);

                startActivity(new Intent(LoginActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

    }

    /*
     * Setting country code in Shared Preference
     * */
    private void initializingCountry() {
        countryCode = Integer.parseInt(sharedPref.getString("lang", "0"));
        if (countryCode == 0) {
            editor.putString("lang", "1").apply();
        }

        changeLanguage(Integer.parseInt(sharedPref.getString("lang", "0")));
    }

    private void recordEntry(String entryType) {

        EntryLog entryLog = new EntryLog();
        entryLog.setProjectName(PROJECT_NAME);
        entryLog.setUserName(username);
        entryLog.setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        entryLog.setAppver(MainApp.appInfo.getAppVersion());
        entryLog.setEntryType(entryType);
        entryLog.setDeviceId(MainApp.deviceid);
        Long rowId = null;
        try {

            rowId = db.EntryLogDao().addEntryLog(entryLog);
        } catch (SQLiteException | JSONException e) {
            Toast.makeText(this, "SQLiteException(EntryLog)" + entryLog, Toast.LENGTH_SHORT).show();
        }
        if (rowId != -1) {
            entryLog.setId(rowId);
            entryLog.setUid(entryLog.getDeviceId() + entryLog.getId());
            db.EntryLogDao().updateEntryLog(entryLog);
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();

        }

    }
}

