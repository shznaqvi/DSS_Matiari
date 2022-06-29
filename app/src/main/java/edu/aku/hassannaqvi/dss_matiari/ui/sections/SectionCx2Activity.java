package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCx2Binding;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCxBinding;

public class SectionCx2Activity extends AppCompatActivity {

    private static final String TAG = "SectionCx2Activity";
    ActivitySectionCx2Binding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_cx_2);
        db = MainApp.appInfo.dbHelper;

        bi.setFollowups(followups);


        /*// Set Round Number from followups data
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
        //bi.fldGrp01.setVisibility(View.VISIBLE);
        //bi.fldGrp02.setVisibility(MainApp.followups.getPrePreg().equals("1") ? View.VISIBLE : View.GONE);    // Current Pregnancy Status

        bi.setFpHouseholds(MainApp.fpHouseholds);
        bi.setFollowups(MainApp.followups);
*/

        // setListener(); // Age calculation not required in followups

        // set default model values if new mwra


        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(MainApp.followups.getUid().equals("") ? "Save" : "Update");

        // To set min max range of date fields
        //setDateRanges();
        /*bi.rc10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bi.rc1401.setText(null);
                bi.rc1402.setText(null);
                bi.rc1403.setText(null);
                setDateRanges();
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
            bi.rc16.setMaxDate(maxLMP);
            bi.rc16.setMinDate(minLMP);

            // EDD
            bi.rc17.setMaxDate(maxEDD);
            bi.rc17.setMinDate(minEDD);

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

                        cur.setTime(sdf.parse(households.getRb01a())); // DOV
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

        if(updateDB()){
                setResult(RESULT_OK);
                finish();

//             startActivity(new Intent(this, WRAEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

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


    private boolean insertNewRecord() {
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
            // Also reset Synced flag and alter UID
            // db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SYNCED, null);
            // concate last char from uid to alter and create new unique uid

           /* followups.setDeviceId(followups.getDeviceId() + "_" + followups.getDeviceId().substring(followups.getDeviceId().length() - 1));
            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_DEVICEID, followups.getDeviceId());
            int repeatCount = (followups.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = followups.getDeviceId().substring(0, 16) + followups.getId() + "_" + repeatCount;
            followups.setUid(newUID);
            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, newUID);*/


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