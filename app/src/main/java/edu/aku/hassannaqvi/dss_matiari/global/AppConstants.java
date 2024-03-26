package edu.aku.hassannaqvi.dss_matiari.global;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.util.List;
import java.util.Locale;

import de.mateware.snacky.Snacky;
import edu.aku.hassannaqvi.dss_matiari.R;

public class AppConstants {

    public static final String PROJECT_NAME = "DSS_MATIARI";
    public static final String DATABASE_NAME = PROJECT_NAME + "_DB";

    // For service api
    public static final String API_NAME = "dss_matiari";

    // For email db
    public static final String[] SEND_DB_TO_EMAIL = new String[]{"omar.shoaib@aku.edu"};

    /**
     * =============================
     * SWITCHES OF THE APP - START
     * ==============================
     */

    // For toggling between Production and Testing server redirection
    // true = Production Server
    // false = Testing Server
    public static boolean IS_PRODUCTION_SERVER = true;

    // To check as an admin
    // true = Show admin level features
    // false = Hide admin level features
    public static boolean IS_ADMIN = false;
    public static String TEST_USERNAME = "test0002";
    public static String TEST_PASSWORD = "Test0002";

    // For visibility of send db option
    // This will always be visible if IS_ADMIN is true
    // true = Show Send DB feature
    // false = Hide Send DB feature
    public static boolean IS_SEND_DB = false;

    // For toggling gps
    // true = GPS ON
    // false = GPS OFF
    public static boolean IS_GPS_ON = false;

    // To enable or disable encryption on web calls
    public static final boolean IS_CALL_ENCRYPTED = true;

    /**
     * =============================
     * SWITCHES OF THE APP - END
     * ==============================
     */

    // Minimum password length
    public static int MIN_PASS_LENGTH = 8;

    // Maximum number of login attempts
    public static int MAX_LOGIN_ATTEMPT_COUNT = 7;

    public static final String _EMPTY_ = "";

    // Connection timeout in seconds - 15 seconds
    public static int CONNECTION_TIMEOUT = 15 * 1000;

    // Screen idle timeout - 15 minutes
    public static long IDLE_TIMEOUT = 15 * 60 * 1000;

    // KishGrid
    public static int[][] KISH_GRID;
    public static int KG_HOUSEHOLD_COUNT = 0;
    public static int KG_MAX_ELIGIBLE_COUNT = 0;

    // Entry Logs type
    public static String ADMIN_LOGIN_SUCCESS = "Admin-Login-Success";
    public static String USER_LOGIN_SUCCESS = "User-Login-Success";
    public static String USER_LOGIN_FIRST = "User-Login-First";
    public static String USER_INACTIVE = "User-Inactive";
    public static String LOGIN_FAILED = "Login-Failed-Incorrect-User-Pass";

    // Device Id
    public static String DEVICE_ID;

    // Languages
    public static String LOCALE_ENGLISH = "en";
    public static String LOCALE_URDU = "ur";
    public static String LOCALE_SINDHI = "sd";

    // Date formats
    public static String APP_DATE_FORMAT = "yyyy-MM-dd";
//    public static String APP_DATE_PICKER_FORMAT = "dd/MM/yyyy";
    public static String APP_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static String SERVER_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static String CUSTOM_SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss.000000";
    public static String DEFAULT_MIN_DATE = "01/01/1925";
    public static String DEFAULT_MIN_YEAR = "1925";

    // Date difference (calculation) types
    public static int DATE_DIFF_IN_DAYS = 1;
    public static int DATE_DIFF_IN_HOURS = 2;
    public static int DATE_DIFF_IN_MINUTES = 3;

    // Response Types - Custom
    public static int RESPONSE_SUCCESS = 1;
    public static int RESPONSE_ERROR = 2;
    // This is used when there's is no record to to upload
    public static int RESPONSE_NOT_PROCESSED = 3;

    // Sync Types
    public static int DOWNLOAD_DATA = 111;
    public static int UPLOAD_DATA = 112;
    public static int UPLOAD_PHOTOS = 113;

    // Status Types
    public static int TYPE_ERROR = 0;
    public static int TYPE_SUCCESS = 1;
    public static int TYPE_WARNING = 2;
    public static int TYPE_INFO = 3;
    public static int TYPE_SB_DEFAULT = 4;

    // Drawable Position values
    public static int POSITION_LEFT = 1;
    public static int POSITION_TOP = 2;
    public static int POSITION_RIGHT = 3;
    public static int POSITION_BOTTOM = 4;

    // Default SnackBar Duration
    public static int MSG_DURATION = 3000;

    // Screen timeout when any code is not scanned
    public static int SCAN_CODE_TIMEOUT = 15 * 1000;

    // Response codes
    public static int NO_CONTENT = 204;
    public static int BAD_REQUEST = 400;
    public static int UNAUTHORIZED = 401;

    public static String IBAHC = _EMPTY_;
    public static int TRATS = 0;

    public static File GALLERY_DIR;

    // Passing Intent Data Tags
    // To differentiate before and after login download
    public static boolean IS_LOGIN = false;

    // Reset All Constants that was set conditionally
    public static void ResetConstants() {
    }

    // Uid generation scheme
    public static String generateUid() {
        // Uid Scheme = 6 characters of device id + current date time in millis
        String deviceIdSS = DEVICE_ID.substring(0, 6);
        long timeInMillis = System.currentTimeMillis();
        return String.format(Locale.getDefault(), "%s%d", deviceIdSS, timeInMillis);
    }

    // Goto Activity
    public static void gotoActivity(Activity currentActivity, Class<? extends Activity> nextActivity, boolean isFinish) {
        currentActivity.startActivity(new Intent(currentActivity, nextActivity));
        if (isFinish) currentActivity.finish();
    }

    // Init Toolbar
    public static void initToolbar(Activity activity, String title, String subTitle, boolean isBackEnabled) {
        AppCompatActivity appCompatActivity = ((AppCompatActivity) activity);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView titleTV = toolbar.findViewById(R.id.titleTV);
        titleTV.setText(title);
        if (!AppConstants.isEmpty(subTitle)) {
            TextView subTitleTV = toolbar.findViewById(R.id.subTitleTV);
            subTitleTV.setText(subTitle);
        }
        // Add back arrow to toolbar
        if (isBackEnabled && appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Check Edittext, TextView and String is not empty
    public static boolean isEmpty(Object object) {
        if (object == null)
            return true;
        if (object instanceof EditText) {
            return ((EditText) object).getText() == null
                    || ((EditText) object).getText().toString().trim().equals("");
        } else if (object instanceof TextView) {
            return ((TextView) object).getText() == null
                    || ((TextView) object).getText().toString().equals("");
        } else if (object instanceof String) {
            return object.equals("");
        }
        return false;
    }

    // Convert simple text to rich text i.e. parse <b><i> tags etc. inside string
    @SuppressWarnings("deprecation")
    public static Spanned getRichText(String simpleText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(simpleText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(simpleText);
        }
    }

    // Scroll to focused view
    public static void focusOnView(ScrollView sv, View view) {
        sv.post(() -> sv.smoothScrollTo(0, view.getBottom()));
    }

    // Disable single radio group
    public static void disableViews(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++)
            radioGroup.getChildAt(i).setEnabled(false);
    }

    // Enable single radio group
    public static void enableViews(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++)
            radioGroup.getChildAt(i).setEnabled(true);
    }

    // Disable list of radio groups
    public static void disableViews(List<View> views) {
        for (View view : views)
            if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                for (int i = 0; i < radioGroup.getChildCount(); i++)
                    radioGroup.getChildAt(i).setEnabled(false);
            } else {
                view.setEnabled(false);
            }
    }

    // Enable list of radio groups
    public static void enableViews(List<View> views) {
        for (View view : views)
            if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                for (int i = 0; i < radioGroup.getChildCount(); i++)
                    radioGroup.getChildAt(i).setEnabled(true);
            } else {
                view.setEnabled(true);
            }
    }

    // Disable all views in a specified linear layout
    public static void disableViews(LinearLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++)
            parentLayout.getChildAt(i).setEnabled(false);
    }

    // Enable all views in a specified linear layout
    public static void enableViews(LinearLayout parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++)
            parentLayout.getChildAt(i).setEnabled(true);
    }

    // Show customized simple SnackBar
    public static void showSimpleSnackBar(Activity activity, String message, int duration, int type) {

        Snacky.Builder snackBar = Snacky.builder();
        snackBar.setActivity(activity);
        snackBar.setText(message);
        snackBar.setDuration(duration);
        snackBar.setView(activity.findViewById(android.R.id.content));
        snackBar.setTextTypeface(ResourcesCompat.getFont(activity, R.font.roboto));
        snackBar.setTextTypefaceStyle(Typeface.BOLD);

        if (type == TYPE_ERROR) {
            snackBar.error().show();
        } else if (type == TYPE_SUCCESS) {
            snackBar.success().show();
        } else if (type == TYPE_INFO) {
            snackBar.info().show();
        } else if (type == TYPE_WARNING) {
            snackBar.warning().show();
        } else {
            // Default
            snackBar.build().show();
        }
    }

    // Check if json is valid JSONArray
    public static boolean isJSONArrayValid(String json) {
        try {
            new Gson().fromJson(json, Object[].class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    // Check if json is valid JSONObject
    public static boolean isJSONObjectValid(String json) {
        try {
            new Gson().fromJson(json, Object.class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    // Change Drawable Color
    public static void setDrawableColor(Activity activity, Drawable drawable, int color) {
        assert drawable != null;
        drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(activity, color), PorterDuff.Mode.MULTIPLY));
    }

    // Show Keyboard
    public static void showSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    // Hide Keyboard
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // Change Drawable Color
    public static void setDrawable(Activity activity, Object object, Drawable drawable, int color, int position) {
        assert drawable != null;
        drawable.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(activity, color), PorterDuff.Mode.MULTIPLY));
        if (object instanceof EditText) {
            if (position == POSITION_LEFT) {
                // Left
                ((EditText) object).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            } else if (position == POSITION_TOP) {
                // Top
                ((EditText) object).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            } else if (position == POSITION_RIGHT) {
                // Right
                ((EditText) object).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            } else {
                // Bottom
                ((EditText) object).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            }
        } else if (object instanceof TextView) {
            if (position == POSITION_LEFT) {
                // Left
                ((TextView) object).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            } else if (position == POSITION_TOP) {
                // Top
                ((TextView) object).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            } else if (position == POSITION_RIGHT) {
                // Right
                ((TextView) object).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            } else {
                // Bottom
                ((TextView) object).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            }
        }
    }

    /*For Activity Screen Lock*/
   /* public static void lockScreen(Activity activity) {
        if (MainApp.timer != null) MainApp.timer.cancel();
        MainApp.timer = new CountDownTimer(15 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) < 14) {
                    MainApp.toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                }
            }

            public void onFinish() {
                gotoActivity(activity, LockAC.class, false);
                MainApp.timer.cancel();
            }
        };
        MainApp.timer.start();
    }*/

    /*For Double Back Pressed*/
   /* private static boolean isDoubleBackPressed = false;

    public static void checkDoubleBackPress(Activity activity, Class<? extends Activity> nextActivity) {
        if (isDoubleBackPressed) {
            if (nextActivity != null)
                gotoActivity(activity, nextActivity, true);
            else
                activity.onBackPressed();
            activity.finish();
            return;
        }
        isDoubleBackPressed = true;
        showSimpleSnackBar(activity, activity.getString(R.string.double_back_press), MSG_DURATION, TYPE_INFO);
        new Handler().postDelayed(() -> isDoubleBackPressed = false, 2000);
    }*/

    // Check if device is rooted
    public static void checkIfDeviceRooted(Activity activity) {
//        if (CommonUtils.isRooted()) {
//            // Exit the app if device is rooted
//            AlertPopup.alert(0, activity, activity.getString(R.string.rooted_device_title),
//                    activity.getString(R.string.rooted_device_desc), AppConstants.TYPE_ERROR,
//                    activity.getString(R.string.ok), new AlertPopup.IAlertCallback() {
//                        @Override
//                        public void onClick(int popupId, boolean isOkClick, String text) {
//                            activity.finish();
//                            System.exit(0);
//                        }
//                    });
//        }
    }

    // Toggle view visibility with time
    public static void toggleViewVisibilityTimer(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        view.postDelayed(() -> view.setVisibility(View.GONE), duration);
    }

}