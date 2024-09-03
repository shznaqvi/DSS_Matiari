package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;
import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.AppTextWatcher;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.global.ImageUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.ui.ImageViewerAC;

public class SectionEActivity extends AppCompatActivity {

    private static final String TAG = "SectionOutcomeActivity";
    private final Activity activity = SectionEActivity.this;
    ActivitySectionEBinding bi;
    private DssRoomDatabase db;
    private Outcome.SE sE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        db = MainApp.appInfo.dbHelper;

        try {
            outcome = db.OutcomeDao().getOutcomeBYID(mwra.getHdssId(), mwra.getSNo(), String.valueOf(++MainApp.prevChildCount));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcome): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sE = Outcome.SE.getData();
        if (sE == null) {
            sE = new Outcome.SE();
            sE.populateMeta();
        }
        sE.setRc01(sE.getRc01().isEmpty() ? String.valueOf(MainApp.prevChildCount) : sE.getRc01());

        bi.setOutcome(sE);

        initUI();

    }

    private void initUI() {

        setDateRanges();

        // Registration
        switch (mwra.getRegRound()) {
            case "1":
                // New outcome registration
                if (outcome.getUid().equals("")) {
                    MainApp.ROUND = mwra.getRound();
                    bi.rc03dob.setText(MainApp.mwra.getSC() == null ? mwra.getSB().getRb21() :
                            MainApp.idType == 1 ? mwra.getSB().getRb21() : mwra.getSC().getRb21());
                    sE.setRc03(MainApp.mwra.getSC() == null ? mwra.getSB().getRb21() :
                            MainApp.idType == 1 ? mwra.getSB().getRb21() : mwra.getSC().getRb21());
                    String date = DateUtils.changeDateFormat(mwra.getSB().getRb21());
                    bi.rc06.setMinDate(date);
                } else {
                    MainApp.ROUND = mwra.getRound();
                    bi.rc03dob.setEnabled(false);
                }
                // Followup
            case "":
                //for unreported outcome in followup
                if (MainApp.mwra.getSC() == null ? mwra.getSB().getRb21().equals("") :
                        MainApp.idType == 1 ? mwra.getSB().getRb21().equals("") : mwra.getSC().getRb15().equals("")) {
                    // new Registration
                    if (outcome.getUid().equals("")) {
                        MainApp.ROUND = mwra.getRound();
                        sE.setRc03(mwra.getSC().getRb21());
                        String date = DateUtils.changeDateFormat(MainApp.mwra.getSC() == null ? mwra.getSB().getRb21() :
                                MainApp.idType == 1 ? mwra.getSB().getRb21() : mwra.getSC().getRb21());
                        bi.rc06.setMinDate(date);
                    } else {
                        MainApp.ROUND = mwra.getRound();
                    }
                } else {
                    if (outcome.getUid().equals("")) {
                        MainApp.ROUND = MainApp.mwra.getSC() == null ? mwra.getRegRound() :
                                MainApp.idType == 1 ? mwra.getRegRound() : MainApp.fpMwra.getFRound();
                        sE.setRc03(MainApp.mwra.getSC() == null ? mwra.getSB().getRb21() :
                                MainApp.idType == 1 ? mwra.getSB().getRb21() : mwra.getSC().getRb15());
                        String date = DateUtils.changeDateFormat(MainApp.mwra.getSC() == null ? mwra.getSB().getRb21() :
                                MainApp.idType == 1 ? mwra.getSB().getRb21() : mwra.getSC().getRb15());
                        bi.rc06.setMinDate(date);
                        bi.rc03dob.setEnabled(false);
                    } else {
                        MainApp.ROUND = mwra.getRound();
                        bi.rc03dob.setEnabled(false);
                    }
                }
        }

        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");

        bi.rc03dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String date = DateUtils.changeDateFormat(bi.rc03dob.getText().toString());
                bi.rc06.setMinDate(date);
            }
        });

        MainApp.imageNames = (!AppConstants.isEmpty(sE.getRc09a()) ? String.format("%s\n", sE.getRc09a()) : _EMPTY_);

        // Set Text watcher on image names string to show/hide 'view' button
        bi.rc09a.addTextChangedListener(new AppTextWatcher(bi.rc09a.getId(), iAppTextWatcher));

        bi.rc09.setOnCheckedChangeListener((radioGroup, i) -> MainApp.imageNames = _EMPTY_);

    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        Outcome.saveMainDataReg(mwra.getHdssId(), mwra.getSNo(), sE.getRc01(), sE);

        if (MainApp.totalChildCount > MainApp.childCount) {

            MainApp.childCount++;
            outcome = new Outcome();
            setResult(RESULT_OK);
            finish();
            startActivity(new Intent(this, SectionEActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

        } else if (mwra.getRegRound().equals("") || mwra.getPrePreg().equals("2")) {
            MainApp.childCount = 1;
            MainApp.totalChildCount = 0;
            setResult(RESULT_OK);
            finish();
            startActivity(new Intent(this, SectionDActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
        } else {
            MainApp.childCount = 1;
            MainApp.totalChildCount = 0;
            setResult(RESULT_OK);
            finish();
        }
    }

    public void btnEnd(View view) {
        MainApp.totalChildCount = 0;
        MainApp.childCount--;
        MainApp.prevChildCount--;
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean formValidation() {
        //setDateRanges();
        if (!Validator.emptyCheckingContainer(this, bi.GrpName))
            return false;

        if (sE.getRc09().equals("1") && AppConstants.isEmpty(sE.getRc09a()))
            return Validator.emptyCustomTextBox(this, bi.rc09a, getString(R.string.image_not_taken));

        return true;
    }


    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(Objects.requireNonNull(sdf.parse(MainApp.mwra.getSC() == null ? mwra.getSB().getRb01a() :
                    MainApp.idType == 1 ? mwra.getSB().getRb01a() : mwra.getSC().getRb01a())));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.MONTH, -3);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +3); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        Intent intent = new Intent(SectionEActivity.this, ImageViewerAC.class);
        // Pass the list of file paths to ImageViewerActivity
        intent.putExtra("image_files", matchingImages);
        intent.putExtra("image_names", MainApp.imageNames.split("\n"));
        startActivity(intent);
    }
}