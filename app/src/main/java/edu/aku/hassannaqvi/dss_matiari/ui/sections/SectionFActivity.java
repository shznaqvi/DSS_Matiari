package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.allMwraMigrated;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;
import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionFBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.AppTextWatcher;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.global.ImageUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.ui.ImageViewerAC;

public class SectionFActivity extends AppCompatActivity {

    private static final String TAG = "OutcomeFollowupActivity";
    private final Activity activity = SectionFActivity.this;
    ActivitySectionFBinding bi;
    private DssRoomDatabase db;
    private Outcome.SE sE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f);
        db = MainApp.appInfo.dbHelper;

        try {
            outcome = db.OutcomeDao().getOutcomeFollowupsBySno(MainApp.households.getUid(), fpMwra.getRb01(), fpMwra.getMuid(), fpMwra.getFRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sE = Outcome.SE.getData();
        if (sE == null) {
            sE = new Outcome.SE();
            sE.populateMetaFollowups();
        }
        bi.setOutcome(sE);

        MainApp.imageNames = (!AppConstants.isEmpty(sE.getRc09a()) ? String.format("%s\n", sE.getRc09a()) : AppConstants._EMPTY_);

        // Set Text watcher on image names string to show/hide 'view' button
        bi.rc09a.addTextChangedListener(new AppTextWatcher(bi.rc09a.getId(), iAppTextWatcher));

        bi.rc09.setOnCheckedChangeListener((radioGroup, i) -> MainApp.imageNames = _EMPTY_);
    }

    private void initUI() {
        String dov = DateUtils.changeDateFormat("2023-01-01");
        bi.rc01a.setMinDate(dov);

        MainApp.ROUND = MainApp.fpMwra.getFRound();
        setDateRanges();
        setImmersive(true);

        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");

        bi.rc05.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.rc0502.isChecked()) {
                    String date = DateUtils.changeDateFormat(fpMwra.getRb04());
                    bi.rc06.setMinDate(date);
                }
            }
        });

        bi.rc08.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                boolean isMigratedOrRefused = false;
                // Put status of Migrated or Refused in its HashMap

                if (bi.rc0802.isChecked() || bi.rc0803.isChecked()) {
                    for (String[] arr : allMwraMigrated.keySet()) {
                        if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                            isMigratedOrRefused = true;
                            break;
                        }
                    }
                    if (!isMigratedOrRefused) {
                        allMwraMigrated.put(new String[]{fpMwra.getMuid(), fpMwra.getHdssid()}, false);
                    }
                } else {
                    if (!allMwraMigrated.isEmpty()) {
                        for (String[] arr : allMwraMigrated.keySet()) {
                            if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                                allMwraMigrated.remove(arr);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(Objects.requireNonNull(sdf.parse(fpMwra.getRa01().getDate())));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinLMP date to 2 months back from DOV
            cal.add(Calendar.MONTH, -9);
            String minLMP = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +9); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minLMP);

            // Set MaxLMP same as DOV
            String maxLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + maxLMP);

            // Set MinEDD same as DOV
            String minEDD = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minEDD);

            // Set MaxEDD to 9 months from DOV
            cal.add(Calendar.MONTH, +9);
            String maxEDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -9);
            Log.d(TAG, "onCreate: " + maxLMP);

            // Date of Death from Date of Deliver(RC10)
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            //cal.setTime(Objects.requireNonNull(sdf.parse(sE.getRc06())));// all done
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String minDOD = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        Outcome.saveMainDataFup(households.getUid(), fpMwra.getRb01(), fpMwra.getMuid(), fpMwra.getFRound(), sE);
        setResult(RESULT_OK);
        finish();
    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean formValidation() {
        setDateRanges();
        if (!Validator.emptyCheckingContainer(this, bi.GrpName))
            return false;

        if (sE.getRc09().equals("1") && AppConstants.isEmpty(sE.getRc09a()))
            return Validator.emptyCustomTextBox(this, bi.rc09a, getString(R.string.image_not_taken));

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
        bi.rc09a.setText(MainApp.imageNames);
    }

    public void takePhoto(View view) {
        ImagePicker.with(activity).maxResultSize(512, 512).saveDir(AppConstants.GALLERY_DIR).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imageName = ImageUtils.generateImageName("SectionD", outcome.getHdssId());
            MainApp.imageNames = MainApp.imageNames.length() > 0 ? String.format("%s\n%s", MainApp.imageNames, imageName) : imageName;
            bi.rc09a.setText(MainApp.imageNames);

            // This code is used to rename a captured image as per our need
            // because it was saving with default name
            Uri uri = Objects.requireNonNull(data).getData();
            File file = new File(uri.getPath());
            ImageUtils.renameTo(activity, file.getName(), imageName);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            AppConstants.showSimpleSnackBar(activity, ImagePicker.getError(data), AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
        } else {
            AppConstants.showSimpleSnackBar(activity, getString(R.string.image_not_taken), AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
        }
    }

    /*
     * VIEW IMAGE CODE
     * */

    AppTextWatcher.IAppTextWatcher iAppTextWatcher = new AppTextWatcher.IAppTextWatcher() {
        @Override
        public void afterTextChanged(int viewId, String text) {
            bi.viewImageTV.setVisibility(text.length() > 0 ? View.VISIBLE : View.GONE);
        }
    };

    public void viewPhoto(View view) {
        // Call the function with the array of image names
        ArrayList<File> matchingImages = ImageUtils.getImageFilesByNames(activity, MainApp.imageNames.split("\n"));
        if (matchingImages == null) {
            AppConstants.showSimpleSnackBar(activity, getString(R.string.no_image_found),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            return;
        }
        // Create an intent to start the ImageViewerActivity
        Intent intent = new Intent(SectionFActivity.this, ImageViewerAC.class);
        // Pass the list of file paths to ImageViewerActivity
        intent.putExtra("image_files", matchingImages);
        intent.putExtra("image_names", MainApp.imageNames.split("\n"));
        startActivity(intent);
    }
}