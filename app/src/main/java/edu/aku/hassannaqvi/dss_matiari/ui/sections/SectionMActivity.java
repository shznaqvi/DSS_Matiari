package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionMBinding;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.AbortionCL;

public class SectionMActivity extends AppCompatActivity {

    private static final String TAG = "SectionMActivity";
    ActivitySectionMBinding bi;
    private DssRoomDatabase db;

    private AbortionCL.SM sM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_m);
        db = MainApp.appInfo.dbHelper;

        MainApp.abortionCL = db.abortionCLDao().getAbortionCLByHDSSID(mwra.getHdssId(), mwra.getSNo(), mwra.getRound());
        if (MainApp.abortionCL == null) AbortionCL.initMeta();

//        AbortionCL.initMeta();
        sM = AbortionCL.SM.getData();
        sM = sM == null ? new AbortionCL.SM() : sM;
        bi.setAbortionCL(sM);
        bi.setCallback(this);

        initUI();
    }

    private void initUI() {
        bi.m2.setText(mwra.getHdssId());
        sM.setM2(mwra.getHdssId());
        bi.m3.setText(mwra.getSC().getRb02());
        sM.setM3(mwra.getSC().getRb02());
    }

    public void m1OnTextChanged(CharSequence s, int start, int before, int count) {
        String dov = s.toString();
        String minLMP = DateUtils.getFormattedDateTime(DateUtils.addSubMonths(dov, -9),
                "yyyy-MM-dd", "dd/MM/yyyy");
        // Set MinLMP date to 1 month back from DOV
        String maxLMP = DateUtils.getFormattedDateTime(DateUtils.addSubMonths(dov, -1),
                "yyyy-MM-dd", "dd/MM/yyyy");
        bi.m7.setMinDate(minLMP);
        bi.m7.setMaxDate(maxLMP);
    }

    public void m7OnTextChanged(CharSequence s, int start, int before, int count) {
        String dov = DateUtils.getFormattedDateTime(
                Objects.requireNonNull(bi.m1.getText()).toString(),
                "yyyy-MM-dd", "dd/MM/yyyy");
        String lmp = DateUtils.getFormattedDateTime(s.toString(),
                "yyyy-MM-dd", "dd/MM/yyyy");
        bi.m9.setMaxDate(dov);
        bi.m9.setMinDate(lmp);
    }

    /*private void setDateRanges() {
//        try {
        // Set time from M1
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//
//            cal.setTime(Objects.requireNonNull(sdf.parse(bi.m1.getText().toString())));
//
//            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        String dov = Objects.requireNonNull(bi.m1.getText()).toString();

        // Set MinLMP date to 2 months back from DOV
//        String minLMP = DateUtils.addSubMonths(dov, -9);
//            cal.add(Calendar.MONTH, -9);
//            String minLMP = sdf.format(cal.getTime());
//            cal.add(Calendar.MONTH, +8); // Calender reset to DOV
//            Log.d(TAG, "onCreate: " + minLMP);

        // Set MaxLMP same as DOV
//        String maxLMP = DateUtils.addSubMonths(dov, -1);
//            String maxLMP = sdf.format(cal.getTime());
//            Log.d(TAG, "onCreate: " + maxLMP);

        // Calender set to DOV
//            cal.add(Calendar.MONTH, +1);

        // Set MinEDD same as DOV
        String minEDD = dov;
//            String minEDD = sdf.format(cal.getTime());
//            Log.d(TAG, "onCreate: " + minEDD);

        // Set MaxEDD to 9 months from DOV
        String maxEDD = DateUtils.addSubMonths(dov, 9);
//            cal.add(Calendar.MONTH, +9);
//            String maxEDD = sdf.format(cal.getTime());
//            cal.add(Calendar.MONTH, -9);
//            Log.d(TAG, "onCreate: " + maxLMP);

        // LMP
//        bi.m7.setMaxDate(maxLMP);
//        bi.m7.setMinDate(minLMP);

        // EDD
        bi.m8.setMaxDate(maxEDD);
        bi.m8.setMinDate(minEDD);

        String lmp = DateUtils.getFormattedDateTime(
                Objects.requireNonNull(bi.m7.getText()).toString(),
                "yyyy-MM-dd", "dd/MM/yyyy");
        dov = DateUtils.getFormattedDateTime(
                dov, "yyyy-MM-dd", "dd/MM/yyyy");

//            Calendar lmpCal = Calendar.getInstance();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//            lmpCal.setTime(Objects.requireNonNull(simpleDateFormat.parse(bi.m7.getText().toString())));
//            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
////            String dov = sdf.format(cal.getTime());
//            String lmp = sdf.format(lmpCal.getTime());

        // Abortion
        bi.m9.setMaxDate(dov);
        bi.m9.setMinDate(lmp);

//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }*/

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        AbortionCL.saveMainData(mwra.getHdssId(), mwra.getUid(), mwra.getRound(), sM);
        MainApp.abortionCL.setWid(mwra.getUid());
        AbortionCL.SM.saveData(sM);
        setResult(RESULT_OK);
        finish();
    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }
}