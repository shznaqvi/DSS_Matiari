package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionDBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;


public class SectionDActivity extends AppCompatActivity {

    private static final String TAG = "SectionCx2Activity";
    ActivitySectionDBinding bi;
    private DssRoomDatabase db;

    private Mwra.SD sD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d);
        db = MainApp.appInfo.dbHelper;

        sD = Mwra.SD.getData();
        sD = sD == null ? new Mwra.SD() : sD;
        bi.setFollowups(sD);

        initUI();
    }

    private void initUI() {
        setDateRanges();
        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(mwra.getUid().equals("") ? "Save" : "Update");

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

    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            if (MainApp.idType == 1) {
                cal.setTime(Objects.requireNonNull(sdf.parse(mwra.getSB().getRb01a())));// all done
            } else {
                cal.setTime(Objects.requireNonNull(sdf.parse(mwra.getSC().getRb01a())));// all done
            }

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinLMP date to 2 months back from DOV
            cal.add(Calendar.MONTH, -9);
            String minLMP = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +8); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minLMP);

            // Set MaxLMP same as DOV
            String maxLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + maxLMP);

            // Calender set to DOV
            cal.add(Calendar.MONTH, +1);

            // Set MinEDD same as DOV
            String minEDD = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minEDD);

            // Set MaxEDD to 9 months from DOV
            cal.add(Calendar.MONTH, +9);
            String maxEDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -9);
            Log.d(TAG, "onCreate: " + maxLMP);

            // LMP
            bi.rb08.setMaxDate(maxLMP);
            bi.rb08.setMinDate(minLMP);

            // EDD
            bi.rb09.setMaxDate(maxEDD);
            bi.rb09.setMinDate(minEDD);

            Calendar lmpCal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            if (idType == 1) {
                lmpCal.setTime(Objects.requireNonNull(simpleDateFormat.parse(mwra.getSB().getRb08())));
            }
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String dov = sdf.format(cal.getTime());
            String lmp = sdf.format(lmpCal.getTime());

            bi.rb25.setMinDate(lmp);
            bi.rb25.setMaxDate(dov);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;

        if (!mwra.getUid().contains("_")) {
            if (mwra.getSC().getRb07().equals("1")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
            } else {
                mwra.setPregnum(String.valueOf(MainApp.pregcount));
            }
        }
        Mwra.SD.saveData(sD);
        setResult(RESULT_OK);
        finish();
    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean formValidation() {
        setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}