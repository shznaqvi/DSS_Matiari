package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;

public class SectionBActivity extends AppCompatActivity {

    private static final String TAG = "SectionBActivity";
    ActivitySectionBBinding bi;
    private DssRoomDatabase db;

    private Mwra.SB sB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;

        sB = Mwra.SB.getData();
        sB = sB == null ? new Mwra.SB() : sB;
        if (sB.getRb01().equals("")) {
            sB.setRb01(String.valueOf(mwraCount + 1));
        }
        bi.setMwra(sB);

        initUI();

    }

    public void initUI() {

        String date = DateUtils.changeDateFormat("2023-01-01");
        bi.rb01a.setMinDate(date);

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(MainApp.mwra.getUid().equals("") ? "Save" : "Update");

        bi.rb01a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDateRanges();

            }
        });
        bi.rb04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!sB.getRb04().equalsIgnoreCase("") && !sB.getRb04().equals("98")) {
                    try {
                        Calendar cur = Calendar.getInstance(); // DOV
                        Calendar cal = Calendar.getInstance(); // DOB
                        //cur.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                        cur.setTime(sdf.parse(sB.getRb01a())); // DOV
                        cal.setTime(sdf.parse(sB.getRb04())); // DOB


                        long yearsinMillisec = cur.getTimeInMillis() - cal.getTimeInMillis();
                        String ageInYears = String.valueOf(TimeUnit.MILLISECONDS.toDays(yearsinMillisec) / 365);

                        bi.rb05.setText(ageInYears);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        bi.rb08.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDateRanges();
            }
        });

        bi.rb26.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            bi.rb21a.setText(R.string.rb15);
            bi.rb1901.setEnabled(true);
            bi.rb1903.setEnabled(true);
            bi.rb1902.setEnabled(true);
            if (checkedId == bi.rb2605.getId()) {
                bi.rb1901.setEnabled(false);
                bi.rb1901.setChecked(false);
                bi.rb1903.setEnabled(false);
                bi.rb1903.setChecked(false);
                bi.rb1902.setEnabled(true);
            } else if (checkedId == bi.rb2603.getId()) {
                bi.rb20.setMaxvalue(27);
                bi.rb20.setMinvalue(3);
                bi.rb21a.setText(R.string.rb15_mis);
            }
        });

        bi.rb19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.rb1901.isChecked()) {
                    MainApp.totalChildCount = 1;
                } else if (bi.rb1902.isChecked()) {
                    MainApp.totalChildCount = 2;
                } else {
                    MainApp.totalChildCount = 3;
                }
            }
        });

    }

    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            //cal.setTime(sdf.parse(new Date().toString()));
            cal.setTime(Objects.requireNonNull(sdf.parse(sB.getRb01a())));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -50);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +50); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);

            // Set maxDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -14);
            String maxDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +14); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + maxDob);


            // Set MinLMP date to 2 months back from DOV
            cal.add(Calendar.MONTH, -9);
            String minLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minLMP);
            cal.add(Calendar.MONTH, +8); // Calender reset to DOV
            // Set MaxLMP same as DOV
            String maxLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + maxLMP);

            // Set to DOV
            cal.add(Calendar.MONTH, +1);
            // Set MinEDD same as DOV

            // Set MaxEDD to 9 months from DOV
            cal.add(Calendar.MONTH, +9);
            String maxEDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -9);
            Log.d(TAG, "onCreate: " + maxLMP);
            String minEDD = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minEDD);

            cal.add(Calendar.MONTH, -3);
            String DD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +3);
            String maxDD = sdf.format(cal.getTime());

            // DOB
            bi.rb04.setMaxDate(maxDob);
            bi.rb04.setMinDate(minDob);

            // LMP
            bi.rb08.setMaxDate(maxLMP);
            bi.rb08.setMinDate(minLMP);

            // EDD
            bi.rb09.setMaxDate(maxEDD);
            bi.rb09.setMinDate(minEDD);

            bi.rb21.setMaxDate(maxDD);
            bi.rb21.setMinDate(DD);


            Calendar lmpCal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            lmpCal.setTime(simpleDateFormat.parse(sB.getRb08()));
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String dov = sdf.format(cal.getTime());
            String lmp = sdf.format(lmpCal.getTime());

            bi.rb25.setMinDate(lmp);
            bi.rb25.setMaxDate(dov);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        Mwra.saveMainDataReg(households.getUid(), households.getHdssId(), sB.getRb01(), households.getRegRound());
        mwra.setSNo(sB.getRb01());

        if (!mwra.getUid().contains("_")) {
            mwra.setPregnum("0");
            if (sB.getRb07().equals("1")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
            }

            if (sB.getRb18().equals("1")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
            }

            if (sB.getRb07().equals("2") && sB.getRb18().equals("2")) {
                mwra.setPregnum("0");
            }
        }

        if (bi.rb1801.isChecked()) {
            if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                setResult(RESULT_OK, forwardIntent);
                finish();
                startActivity(forwardIntent);
            } else if (bi.rb2603.isChecked()) {
                finish();
                startActivity(new Intent(this, SectionMActivity.class).putExtra("isSB", true));
//                AppConstants.gotoActivity(this, SectionMActivity.class, true);
            } else {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            setResult(RESULT_OK);
            finish();
        }

        Mwra.SB.saveData(sB);

    }

    public void btnEnd(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }


}