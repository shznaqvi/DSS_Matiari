package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedHhNO;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedVillage;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCxBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.utils.DateUtilsKt;

public class SectionCxActivity extends AppCompatActivity {

    private static final String TAG = "SectionCxActivity";
    ActivitySectionCxBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_cx);
        db = MainApp.appInfo.dbHelper;


        // Set Round Number from followups data
        MainApp.ROUND = MainApp.fpMwra.getfRound();

        try {
            followups = db.getFollowupsBySno(MainApp.fpMwra.getRb01(), MainApp.fpMwra.getfRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (MainApp.followups.getUid().equals("")) {
            MainApp.followups.setRc01(MainApp.fpMwra.getRb01());
            MainApp.followups.setRc02(MainApp.fpMwra.getRb02());
            MainApp.followups.setRc03(MainApp.fpMwra.getRb03());
            MainApp.followups.setRb06(MainApp.fpMwra.getRb06());
            MainApp.followups.setRc06(MainApp.fpMwra.getRb06());
            MainApp.followups.setPrePreg(MainApp.fpMwra.getRb07());
            MainApp.followups.setRc07(MainApp.fpMwra.getRb07());
            MainApp.followups.setHdssId(MainApp.fpMwra.getHdssid());
            MainApp.followups.setHhNo(MainApp.fpMwra.getHhNo());

        }

        bi.setFpHouseholds(MainApp.fpHouseholds);
        bi.setFollowups(MainApp.followups);

        setImmersive(true);

        bi.btnContinue.setText(MainApp.followups.getUid().equals("") ? "Save" : "Update");

        bi.rc11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.rc1101.isChecked())
                {
                    MainApp.totalChildCount = 1;
                }else if(bi.rc1102.isChecked()){
                    MainApp.totalChildCount = 2;
                }else if (bi.rc1103.isChecked())
                {
                    MainApp.totalChildCount = 3;
                }

            }

        });

        if(!MainApp.fpHouseholds.getVisitNo().equals("") && Integer.parseInt(fpHouseholds.getVisitNo()) == 2)
        {
            bi.rc0408.setEnabled(true);
            bi.rc0404.setEnabled(false);
        }else{
            bi.rc0408.setEnabled(false);
            bi.rc0404.setEnabled(true);
        }

        bi.rc04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MainApp.mwraFlag = !bi.rc0404.isChecked();
            }
        });

        /*int maxMWRA = db.getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        int maxFpMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        mwraCount = Math.max(maxMWRA, maxFpMWRA);


        bi.rc04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.rc0402.isChecked() || bi.rc0407.isChecked())
                {
                    mwraCount--;
                }
            }
        });*/


    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(followups.getRc01a()));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

          /*  // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -50);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +50); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);

            // Set maxDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -18);
            String maxDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +18); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + maxDob);

*/
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

          /*  // DOB
            bi.rb04.setMaxDate(maxDob);
            bi.rb04.setMinDate(minDob);*/

            // LMP
            /*bi.rc16.setMaxDate(maxLMP);
            bi.rc16.setMinDate(minLMP);

            // EDD
            bi.rc17.setMaxDate(maxEDD);
            bi.rc17.setMinDate(minEDD);*/

            // Date of Death from Date of Deliver(RC10)
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(followups.getRc10()));// all done
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String minDOD = sdf.format(cal.getTime());

            // Single
            /*bi.rc1401.setMinDate(minDOD);
            bi.rc1402.setMinDate(minDOD);
            bi.rc1403.setMinDate(minDOD)*/;


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (MainApp.followups.getUid().equals("") ? insertNewRecord() : updateDB()) {

            if(bi.rc0401.isChecked()) {

                if (followups.getPrePreg().equals("1") && bi.rc0901.isChecked()) {

                    Intent forwardIntent = new Intent(this, SectionOutcomeActivity.class).putExtra("complete", true);
                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    setResult(RESULT_OK, forwardIntent);
                    startActivity(forwardIntent);
                    finish();
                } else if (!bi.rc0604.isChecked() && bi.rc0802.isChecked() || followups.getPrePreg().equals("2")) {
                    Intent forwardIntent = new Intent(this, SectionCx2Activity.class).putExtra("complete", true);
                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    setResult(RESULT_OK, forwardIntent);
                    startActivity(forwardIntent);
                    finish();
                } else if (bi.rc0604.isChecked() || bi.rc0801.isChecked()) {
                    setResult(RESULT_OK);
                    finish();
                }
            }else{
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean insertNewRecord() {
        MainApp.outcome = new Outcome();
        if (fpHouseholds.getUid().equals("")) {
            insertFpHousehold();
        }

        followups.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addFollowup(followups);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        followups.setId(String.valueOf(rowId));
        if (rowId > 0) {
            followups.setUid(followups.getDeviceId() + followups.getId());
            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, followups.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean insertFpHousehold() {
        long rowId = 0;
        try {
            rowId = db.addFpHousehold(fpHouseholds);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FPHouseholds): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        fpHouseholds.setId(String.valueOf(rowId));
        if (rowId > 0) {
            fpHouseholds.setUid(fpHouseholds.getDeviceId() + fpHouseholds.getId());
            db.updatesFPHouseholdsColumn(TableContracts.FPHouseholdTable.COLUMN_UID, fpHouseholds.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_SC, followups.sCtoString());

            followups.setDeviceId(followups.getDeviceId() + "_" + followups.getDeviceId().substring(followups.getDeviceId().length() - 1));
            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_DEVICEID, followups.getDeviceId());
            int repeatCount = (followups.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = followups.getDeviceId().substring(0, 16) + followups.getId() + "_" + repeatCount;
            followups.setUid(newUID);
            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, newUID);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "updateDB (JSONException): " + e.getMessage());
            return false;
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }


}