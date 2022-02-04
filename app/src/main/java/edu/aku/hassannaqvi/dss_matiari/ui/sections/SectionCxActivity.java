package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        MainApp.round = MainApp.fpMwra.getfRound();

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
            // TODO: Marital Status not received from server
            MainApp.followups.setRc06(MainApp.fpMwra.getRb06());
            MainApp.followups.setPrePreg(MainApp.fpMwra.getRb07());
            MainApp.followups.setRc07(MainApp.fpMwra.getRb07());

        }
        //bi.fldGrp01.setVisibility(View.VISIBLE);
        //bi.fldGrp02.setVisibility(MainApp.followups.getPrePreg().equals("1") ? View.VISIBLE : View.GONE);    // Current Pregnancy Status

        bi.setFpHouseholds(MainApp.fpHouseholds);
        bi.setFollowups(MainApp.followups);


        // setListener(); // Age calculation not required in followups

        // set default model values if new mwra


        // setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(MainApp.followups.getUid().equals("") ? "Save" : "Update");

        // To set min max range of date fields
        //setDateRanges();


    }

    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(followups.getRa01()));// all done

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
            bi.rc16.setMaxDate(maxLMP);
            bi.rc16.setMinDate(minLMP);

            // EDD
            bi.rc17.setMaxDate(maxEDD);
            bi.rc17.setMinDate(minEDD);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

   /* private void setListener() {
        bi.rb04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mwra.getRb04().equalsIgnoreCase("") && !mwra.getRb04().equals("98")) {
//                    String[] arrStr = mwra.getRb04().split("-");
//                    int day, month, year;
//                    year = arrStr.length > 0 ? Integer.valueOf(arrStr[0]) : 0;
//                    month = arrStr.length > 1 ? Integer.valueOf(arrStr[1]) : 0;
//                    day = arrStr.length > 2 ? Integer.valueOf(arrStr[2]) : 0;
//                    if (year == 0 || month == 0 || day ==0) {
//                        return;
//                    }
//                    bi.rb05.setText(DateUtilsKt.ageInYears(day, month ,year ,year));
                    try {
                        Calendar cur = Calendar.getInstance(); // DOV
                        Calendar cal = Calendar.getInstance(); // DOB
                        //cur.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                        cur.setTime(sdf.parse(households.getRa01())); // DOV
                        cal.setTime(sdf.parse(mwra.getRb04())); // DOB


                        long yearsinMillisec = cur.getTimeInMillis() - cal.getTimeInMillis();
                        String ageInYears = String.valueOf(TimeUnit.MILLISECONDS.toDays(yearsinMillisec) / 365);

                        bi.rb05.setText(ageInYears);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


*//*
                    String[] arrStr = mwra.getRb04().split("-");
                    String day, month, year;
                    year = arrStr.length > 0 ? arrStr[0] : "0";
                    month = arrStr.length > 1 ? arrStr[1] : "0";
                    day = arrStr.length > 2 ? arrStr[2] : "0";
                    if (year.equalsIgnoreCase("0") || month.equalsIgnoreCase("0") || day.equalsIgnoreCase("0")) {
                        return;
                    }
                    bi.rb05.setText(DateUtilsKt.getAge(year, month, day, false));
*//*
                }
            }
        });
    }*/

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (MainApp.followups.getUid().equals("") ? insertNewRecord() : updateDB()) {
            setResult(RESULT_OK);
//             startActivity(new Intent(this, WRAEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
            finish();

         /*   if (MainApp.followups.getPrePreg().equals("1") && bi.rc0501.isChecked()) {
                setResult(RESULT_OK);
                startActivity(new Intent(this, SectionDActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                finish();
            } else {
                setResult(RESULT_OK);
                startActivity(new Intent(this, WRAEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                finish();
            }*/
            //  startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDraft() {

        //mwra = new MWRA();

/*        MainApp.mwra.setUuid(households.getUid());
        MainApp.mwra.setUcCode(households.getUcCode());
        MainApp.mwra.setVillageCode(households.getVillageCode());
        MainApp.mwra.setsNo(households.getsNo());
        MainApp.mwra.setHhNo(households.getHhNo());
        MainApp.mwra.setUserName(MainApp.user.getUserName());
        MainApp.mwra.setSysDate(households.getSysDate());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setHdssId(households.getHdssId());
        MainApp.mwra.setAppver(MainApp.versionName + "." + MainApp.versionCode);*/


/*        mwra.setRc01(bi.rb01.getText().toString());
        mwra.setRc02(bi.rb02.getText().toString());
        mwra.setRc03(bi.rb03.getText().toString());
        mwra.setRb04(bi.rb04.getText().toString());
        mwra.setRb05(bi.rb05.getText().toString());
        mwra.setRc06(bi.rb0602.isChecked() ? "2"
                : bi.rb0603.isChecked() ? "3"
                : "-1");
        mwra.setRc07(bi.rb0701.isChecked() ? "1"
                : bi.rb0702.isChecked() ? "2"
                : "-1");
        mwra.setRc08(bi.rb08.getText().toString());
        mwra.setRc09(bi.rb09.getText().toString());*/

    }

/*    private boolean insertNewRecord() {
        if (MainApp.households.isExist()) return true;
        db = MainApp.appInfo.getDbHelper();
        long rowId = db.addForm(households);
        households.setId(String.valueOf(rowId));
        if (rowId > 0) {
            households.setUid(households.getDeviceId() + households.getId());
            db.updatesFormColumn(TableContracts.HouseholdTable.COLUMN_UID, households.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/

    private boolean insertNewRecord() {
        db = MainApp.appInfo.getDbHelper();
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
        db = MainApp.appInfo.getDbHelper();

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
            db.updatesFPHouseholdsColumn(TableContracts.FollowupsTable.COLUMN_UID, fpHouseholds.getUid());
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
            // Also reset Synced flag and alter UID
            // db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SYNCED, null);
            // concate last char from uid to alter and create new unique uid

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
/*        if (followups.getRc05().equals("2") || followups.getRc05().equals("3")) {
            if (!formValidation()) return;
            setResult(RESULT_OK);
            insertNewRecord();
            updateDB();
            startActivity(new Intent(this, WRAEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", false));
        }*/
        finish();

    }

    private boolean formValidation() {
        setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);
       /*
       if (!compareTwoDate(bi.rb08, 2,
                "LMP should be within 2 months back from DOV")) return false;
        return compareTwoDate(bi.rb09, 9,
                "EDD should be within 9 months back from DOV");
                */
    }


}