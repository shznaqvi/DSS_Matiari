package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants.IS_CALL_ENCRYPTED;
import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import java.util.List;
import java.util.regex.Pattern;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.core.UserAuth;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityChangePassNewBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.Loading;
import edu.aku.hassannaqvi.dss_matiari.models.ResetPass;
import edu.aku.hassannaqvi.dss_matiari.webcall.web_client.CryptoUtil;
import edu.aku.hassannaqvi.dss_matiari.webcall.web_client.WebAPI;
import edu.aku.hassannaqvi.dss_matiari.webcall.web_client.WebCall;
import edu.aku.hassannaqvi.dss_matiari.webcall.web_client.WebClient;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;

public class ChangePassNewAC extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final Activity activity = ChangePassNewAC.this;

    private ActivityChangePassNewBinding bi;

    private WebAPI webAPI;
    private WebCall webCall;
    private Loading loading;
    private Gson gson;
    private DssRoomDatabase appDatabase;

    private static final String TAG_RESET_PASS = "RESET_PASS";
    private static final int POP_RESET_PASS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(activity, R.layout.activity_change_pass_new);

        webAPI = WebClient.getInstance(activity).getWebAPI();
        webCall = new WebCall(activity, iWebCallback);
        loading = new Loading(activity, false);
        gson = new Gson();
        appDatabase = MainApp.appInfo.dbHelper;
    }

    // Password reset web service call
    public void attemptReset(View view) {
        if (!isPasswordValid()) return;

        // Generate hashed password
        String hashedPassword = UserAuth.generatePassword(bi.newPassET.getText().toString(), null);
        if (AppConstants.isEmpty(hashedPassword)) {
            AppConstants.showSimpleSnackBar(activity, getString(R.string.secured_pass_not_generated),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            return;
        }

        ResetPass resetPass = new ResetPass();
        resetPass.setUserName(MainApp.user.getUsername());
        resetPass.setOldPassword(MainApp.user.getPasswordEnc());
        resetPass.setNewPassword(hashedPassword);

        loading.showLoading();
        String postJson = CryptoUtil.encrypt(gson.toJson(resetPass));
        webCall.call(webAPI.resetPassword(postJson), AppConstants.UPLOAD_DATA, TAG_RESET_PASS, 0, 0, IS_CALL_ENCRYPTED);
    }

    // Change password callback
    WebCall.IWebCallback iWebCallback = new WebCall.IWebCallback() {
        @Override
        public void onSuccess(String tag, String responseBody, int index, int total, List<String> list) {
            // Update user new password (after encryption) in the local db to prevent downloading
            // the user data again from server for login with new password
            MainApp.user.setPasswordEnc(UserAuth.generatePassword(bi.newPassET.getText().toString(), null));
            MainApp.user.setNewUser(_EMPTY_);
            appDatabase.syncFunctionsDao().updateUser(MainApp.user);
            Toast.makeText(activity, getString(R.string.password_reset_successful), Toast.LENGTH_SHORT).show();
            loading.hideLoading();
            finish();
        }

        @Override
        public void onFailure(String tag, String errorMessage, int index, int total, List<String> list) {
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            loading.hideLoading();
        }
    };

    // Password validation
    private boolean isPasswordValid() {
        String oldPass = bi.oldPassET.getText().toString();
        String newPass = bi.newPassET.getText().toString();
        String confirmPass = bi.confirmPassET.getText().toString();

        // Username and Password cannot be same
        if (newPass.equals(MainApp.user.getUsername())) {
            bi.newPassET.setError(getString(R.string.username_pass_same));
            return false;
        }

        // Old password cannot be empty
        if (AppConstants.isEmpty(oldPass)) {
            bi.oldPassET.setError(getString(R.string.field_empty_error));
            return false;
        }

        // Check for a valid password, if the user entered one.
        if (newPass.length() < AppConstants.MIN_PASS_LENGTH) {
            bi.newPassET.setError(String.format(getString(R.string.invalid_password_new),
                    AppConstants.MIN_PASS_LENGTH));
            return false;
        }

        // Password creation criteria
        String passRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{" + AppConstants.MIN_PASS_LENGTH + ",}$";
        Pattern pattern = Pattern.compile(passRegex);
        if (!pattern.matcher(newPass).matches()) {
            bi.newPassET.setError(getString(R.string.password_creation_criteria));
            return false;
        }

        // Old and New Passwords cannot be same
        if (UserAuth.checkPassword(newPass, MainApp.user.getPasswordEnc())) {
            bi.newPassET.setError(getString(R.string.current_previous_password_cannot_same));
            return false;
        }

        // New and Confirm Passwords field must be same
        if (!newPass.equals(confirmPass)) {
            bi.confirmPassET.setError(getString(R.string.password_not_matched));
            return false;
        }

        return true;
    }

    // Show/Hide password
    public void togglePassVisibility(View view) {
        int viewId = view.getId();
        EditText editText;
        ImageView imageView;
        if (viewId == R.id.oldPassIV) {
            editText = bi.oldPassET;
            imageView = bi.oldPassIV;
        } else if (viewId == R.id.newPassIV) {
            editText = bi.newPassET;
            imageView = bi.newPassIV;
        } else {
            editText = bi.confirmPassET;
            imageView = bi.confirmPassIV;
        }
        if (editText.getTransformationMethod() == null) {
            editText.setTransformationMethod(new PasswordTransformationMethod());
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_locked));
        } else {
            editText.setTransformationMethod(null);
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_unlocked));
        }
    }

}
