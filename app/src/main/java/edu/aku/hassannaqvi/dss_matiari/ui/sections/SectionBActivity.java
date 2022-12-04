package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SectionBActivity extends AppCompatActivity {

    private static final String TAG = "SectionBActivity";
    ActivitySectionBBinding bi;
    private DssRoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);
        bi.setMwra(mwra);

        setListener();

        // set default model values if new mwra
        if (mwra.getRb01().equals("")) {
            mwra.setRb01(String.valueOf(mwraCount + 1));
            MainApp.mwra.setUuid(households.getUid());
            MainApp.mwra.setUcCode(households.getUcCode());
            MainApp.mwra.setVillageCode(households.getVillageCode());
            MainApp.mwra.setStructureNo(households.getStructureNo());
            MainApp.mwra.setHhNo(households.getHhNo());
            MainApp.mwra.setUserName(MainApp.user.getUserName());
            MainApp.mwra.setSysDate(households.getSysDate());
            MainApp.mwra.setDeviceId(MainApp.deviceid);
            MainApp.mwra.setHdssId(households.getHdssId());
            MainApp.mwra.setAppver(MainApp.versionName + "." + MainApp.versionCode);


            // Followups data
            /*MainApp.followups.setUuid(households.getUid());
            MainApp.followups.setUcCode(households.getUcCode());
            MainApp.followups.setVillageCode(households.getVillageCode());
            //MainApp.followups.setStructureNo(households.getStructureNo());
            followups.setSNo(mwra.getRb01());
            followups.setFRound("0");
            MainApp.followups.setHhNo(households.getHhNo());
            MainApp.followups.setUserName(MainApp.user.getUserName());
            MainApp.followups.setSysDate(households.getSysDate());
            MainApp.followups.setDeviceId(MainApp.deviceid);
            MainApp.followups.setHdssId(households.getHdssId());
            MainApp.followups.setAppver(MainApp.versionName + "." + MainApp.versionCode);*/


        }

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
        bi.btnContinue.setText(MainApp.mwra.getUid().equals("") ? "Save" : "Update");



    }

    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(mwra.getRb01a()));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -50);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +50); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);

            // Set maxDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -18);
            String maxDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +18); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + maxDob);


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

            // DOB
            bi.rb04.setMaxDate(maxDob);
            bi.rb04.setMinDate(minDob);

            // LMP
            bi.rb08.setMaxDate(maxLMP);
            bi.rb08.setMinDate(minLMP);

            // EDD
            bi.rb09.setMaxDate(maxEDD);
            bi.rb09.setMinDate(minEDD);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
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
                if (!mwra.getRb04().equalsIgnoreCase("") && !mwra.getRb04().equals("98")) {
                    try {
                        Calendar cur = Calendar.getInstance(); // DOV
                        Calendar cal = Calendar.getInstance(); // DOB
                        //cur.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                        cur.setTime(sdf.parse(mwra.getRb01a())); // DOV
                        cal.setTime(sdf.parse(mwra.getRb04())); // DOB


                        long yearsinMillisec = cur.getTimeInMillis() - cal.getTimeInMillis();
                        String ageInYears = String.valueOf(TimeUnit.MILLISECONDS.toDays(yearsinMillisec) / 365);

                        bi.rb05.setText(ageInYears);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        //if (MainApp.mwra.getUid().equals("") ? insertNewRecord() && insertNewFollowupRecord() : updateDB()) {
        if (MainApp.mwra.getUid().equals("") ? insertNewRecord() : updateDB()) {
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean insertNewRecord() throws JSONException {
        db = MainApp.appInfo.getDbHelper();
       /* if (MainApp.households.getRa18().equals("999") && fpHouseholds.getUid().equals("")) {
            insertFpHousehold();
        }*/
        MainApp.mwra.populateMeta();
        long rowId = 0;
        try {
            //rowId = db.addMWRA(mwra);
            rowId = DssRoomDatabase.getDbInstance().mwraDao().addMwra(mwra);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        mwra.setId(rowId);
        if (rowId > 0) {
            mwra.setUid(mwra.getDeviceId() + mwra.getId());
            mwra.setSB(mwra.sBtoString());
            DssRoomDatabase.getDbInstance().mwraDao().updateMwra(mwra);
            //db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_UID, mwra.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /*private boolean insertNewFollowupRecord() {
        db = MainApp.appInfo.getDbHelper();
        //followups.populateMeta();
        long rowId = 0;
        try {
            rowId = db.addFollowup(followups);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FPHouseholds): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        followups.setId(rowId);
        if (rowId > 0) {
            followups.setUid(followups.getDeviceId() + followups.getId());

            // This not a mistake. It is done on purpose
            //households.setUid(fpHouseholds.getDeviceId() + fpHouseholds.getId());

            db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, followups.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/




    private boolean updateDB() {
        int updcount = 0;
        try {
            Mwra updateMwra = mwra;
            updateMwra.setSB(mwra.sBtoString());
            // Also reset Synced flag and alter UID
            updateMwra.setSynced(null);
            updcount = DssRoomDatabase.getDbInstance().mwraDao().updateMwra(updateMwra);

            //updcount = db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SB, mwra.sBtoString());
            //db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SYNCED, null);

            // concate last char from uid to alter and create new unique uid

            mwra.setDeviceId(mwra.getDeviceId() + "_" + mwra.getDeviceId().substring(mwra.getDeviceId().length() - 1));
            DssRoomDatabase.getDbInstance().mwraDao().updateMwra(updateMwra);
            //db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_DEVICEID, mwra.getDeviceId());
            int repeatCount = (mwra.getDeviceId().length() - 16) / 2;

            // new UID
            String newUID = mwra.getDeviceId().substring(0, 16) + mwra.getId() + "_" + repeatCount;
            mwra.setUid(newUID);
            updateMwra.setUid(newUID);

            DssRoomDatabase.getDbInstance().mwraDao().updateMwra(updateMwra);
            //db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_UID, newUID);


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
        setResult(Activity.RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

    private boolean compareTwoDate(EditText et, int month, String msg) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDOV = sdf.parse(households.getRa01());
            Calendar calendarDOV1 = Calendar.getInstance();
            Calendar calendarDOV2 = Calendar.getInstance();
            calendarDOV1.setTime(dateDOV);
            calendarDOV2.setTime(dateDOV);
            calendarDOV2.add(Calendar.MONTH, month);
            Date dateTwo = sdf.parse(et.getText().toString().trim());
            if (dateTwo.before(calendarDOV1.getTime()) || dateTwo.after(calendarDOV2.getTime())) {
                Validator.emptyCustomTextBox(this, et, msg);
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }


}