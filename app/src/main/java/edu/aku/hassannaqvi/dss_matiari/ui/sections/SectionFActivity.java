package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.allMwraRefusedOrMigrated;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;


import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionFBinding;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;

public class SectionFActivity extends AppCompatActivity {

    private static final String TAG = "OutcomeFollowupActivity";
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

        bi.rb10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                boolean isMigratedOrRefused = false;
                // Put status of Migrated or Refused in its HashMap

                if (bi.rb1002.isChecked() || bi.rb1003.isChecked()) {
                    for (String[] arr : allMwraRefusedOrMigrated.keySet()) {
                        if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                            isMigratedOrRefused = true;
                            break;
                        }
                    }
                    if (!isMigratedOrRefused) {
                        allMwraRefusedOrMigrated.put(new String[]{fpMwra.getMuid(), fpMwra.getHdssid()}, false);
                    }
                } else {
                    if (!allMwraRefusedOrMigrated.isEmpty()) {
                        for (String[] arr : allMwraRefusedOrMigrated.keySet()) {
                            if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                                allMwraRefusedOrMigrated.remove(arr);
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
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }
}