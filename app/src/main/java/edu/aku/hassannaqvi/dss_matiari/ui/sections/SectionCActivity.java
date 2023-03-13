package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followUpsScheHHList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followUpsScheMWRAList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraStatus;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFpHousehold;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedMember;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SectionCActivity extends AppCompatActivity {

    private static final String TAG = "SectionCxActivity";
    ActivitySectionCBinding bi;
    private DssRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        db = MainApp.appInfo.dbHelper;

        String date = toBlackVisionDate("2023-01-01");
        bi.rb01a.setMinDate(date);

        // Set Round Number from followups data
        MainApp.ROUND = MainApp.fpMwra.getFRound();

        try {
            //followups = db.getFollowupsBySno(MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
            mwra = db.mwraDao().getFollowupsBySno(MainApp.households.getUid(), MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (mwra.getUid().equals("")) {
            mwra.setUserName(MainApp.user.getUserName());
            mwra.setDeviceId(MainApp.deviceid);
            mwra.setAppver(MainApp.appInfo.getAppVersion());
            mwra.setSysDate(MainApp.households.getSysDate());
            mwra.setUuid(MainApp.households.getUid());  // not applicable in Form table
            mwra.setProjectName(PROJECT_NAME);
            mwra.setRegRound("");

            mwra.setHdssId(MainApp.fpMwra.getHdssid());
            mwra.setHhNo(MainApp.fpMwra.getHhNo());
            mwra.setUcCode(MainApp.fpMwra.getUcCode());
            mwra.setVillageCode(MainApp.fpMwra.getVillageCode());
            mwra.setRound(MainApp.fpMwra.getFRound());
            mwra.setSNo(MainApp.fpMwra.getRb01());
            mwra.setRb01(MainApp.fpMwra.getRb01());
            mwra.setRb02(MainApp.fpMwra.getRb02());
            mwra.setRb03(MainApp.fpMwra.getRb03());
            mwra.setRb06(MainApp.fpMwra.getRb06());
            mwra.setPreMaritalStaus(fpMwra.getRb06());
            mwra.setPrePreg(MainApp.fpMwra.getRb07());
            mwra.setChild_count(fpMwra.getChild_count());
            //mwra.setRb07(MainApp.fpMwra.getRb07());

            long daysdiff = mwra.CalculateAge(MainApp.fpMwra.getRa01());
            long years = daysdiff / 365;
            long actualAge = Integer.parseInt(MainApp.fpMwra.getRb05()) + years;
            mwra.setRb05(String.valueOf(actualAge));     // Age in Years

        }
        // For edit mode
        if (!mwra.getUid().equals("")) {
            try {
                mwra.sCHydrate(mwra.getSC());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        long daysdiff = MainApp.mwra.CalculateAge(fpMwra.getRa01());
        long years = daysdiff / 365;
        long actualAge = 0;

        if (!fpMwra.getRb05().equals("")) {
            actualAge = Long.parseLong(fpMwra.getRb05()) + years;
            bi.rb05.setText(String.valueOf(actualAge));
        }

        if (actualAge > 49) {
            bi.rb1009.setEnabled(true);
            bi.rb1001.setEnabled(false);
            bi.rb1002.setEnabled(false);
            bi.rb1003.setEnabled(false);
            //bi.rb1004.setEnabled(false);
            bi.rb1005.setEnabled(false);
            bi.rb1006.setEnabled(false);
            bi.rb1007.setEnabled(false);
            //bi.rb1008.setEnabled(false);
        } else {
            bi.rb1009.setEnabled(false);
            bi.rb1001.setEnabled(true);
            bi.rb1002.setEnabled(true);
            bi.rb1003.setEnabled(true);
            //bi.rb1004.setEnabled(true);
            bi.rb1005.setEnabled(true);
            bi.rb1006.setEnabled(true);
            bi.rb1007.setEnabled(true);
            //bi.rb1008.setEnabled(true);
        }

        if (!MainApp.households.getVisitNo().equals("") && Integer.parseInt(households.getVisitNo()) >= 2) {
           mwra.setRb10("");
            bi.rb1008.setEnabled(true);
            bi.rb1004.setEnabled(false);
            bi.rb1004.setChecked(false);
        } else {
            bi.rb1008.setEnabled(false);
            bi.rb1004.setEnabled(true);
        }

        setImmersive(true);
        bi.setFollowups(mwra);

        bi.btnContinue.setText(MainApp.mwra.getUid().equals("") ? "Save" : "Update");

        /********************************On Click Listeners******************************/

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

        bi.rb10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.rb1004.isChecked()) {
                    boolean isAvailable = false;
                    for(String[] arr : mwraStatus.keySet()) {
                        if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                            isAvailable = true;
                        }
                    }
                    if (!isAvailable) {
                        mwraStatus.put(new String[]{fpMwra.getMuid(), fpMwra.getHdssid()}, false);
                    }
                    mwra.setRb07(fpMwra.getRb07());
                    mwra.setRb06(fpMwra.getRb06());
                    mwra.setRb04(fpMwra.getRb04());
                } else {
                    mwra.setRb07("");
                    mwra.setRb06(mwra.getRb06());
                    mwra.setRb04(fpMwra.getRb04());
                    if (!mwraStatus.isEmpty()) {
                        for(String[] arr : mwraStatus.keySet()) {
                            if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                                mwraStatus.remove(arr);
                                break;


























                            }
                        }
                    }
                    int a = 0;
                    a++;
                    a++;
                }
            }
        });


        bi.rb19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.rb1901.isChecked()) {
                    MainApp.totalChildCount = 1;
                } else if (bi.rb1902.isChecked()) {
                    MainApp.totalChildCount = 2;
                } else if (bi.rb1903.isChecked()) {
                    MainApp.totalChildCount = 3;
                }
            }
        });

        bi.rb17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.rb1701.isChecked() || bi.rb1605.isChecked()) {
                    MainApp.totalChildCount = 1;
                } else if (bi.rb1702.isChecked() && !bi.rb1605.isChecked()) {
                    MainApp.totalChildCount = 2;
                } else if (bi.rb1703.isChecked()) {
                    MainApp.totalChildCount = 3;
                }

            }

        });

        bi.rb1605.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.rb1701.setEnabled(false);
                    bi.rb1701.setChecked(false);
                    bi.rb1703.setEnabled(false);
                    bi.rb1703.setChecked(false);
                    bi.rb1702.setEnabled(true);
                } else {
                    bi.rb1701.setEnabled(true);
                    bi.rb1703.setEnabled(true);
                    bi.rb1702.setEnabled(true);
                }
            }
        });


    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            Calendar cal2 = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            cal.setTime(sdf.parse(mwra.getRb01a()));// all done

            cal2.setTime(sdf.parse(fpMwra.getRa01().substring(9, 19)));

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinEDD to 9 months from DOV
            cal.add(Calendar.MONTH, -9);
            String minEDD = sdf.format(cal.getTime());

            // Set to DOV
            cal.add(Calendar.MONTH, +9);
            String maxDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -3);
            String DD = sdf.format(cal2.getTime());


            bi.rb15.setMinDate(minEDD);
            bi.rb21.setMinDate(DD);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        if (mwra.getUid().equals("") ? insertNewRecord() : updateDB()) {

            if (bi.rb1001.isChecked()) {

                switch (fpMwra.getRb06()) {
                    // Married in Previous Round
                    case "1":
                        // Pregnant
                        if (mwra.getPrePreg().equals("1")) {
                            // If pregnancy continued
                            if (bi.rb1401.isChecked()) {
                                setResult(RESULT_OK);
                                //finish();
                            } else {  // If Pregnancy ended
                                if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) // If Live Birth
                                {
                                 if(!fpMwra.getChild_count().equals("null")){
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                 }else{
                                     MainApp.prevChildCount = 0;
                                 }
                                    Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    startActivity(forwardIntent);
                                    //finish();
                                } else { // If not live birth
                                    Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    startActivity(forwardIntent);
                                    //finish();
                                }
                            }
                        } else if (mwra.getPrePreg().equals("2") && bi.rb1801.isChecked()) {   // Not Pregnant
                            if(!fpMwra.getChild_count().equals("null")){
                                MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                            }else{
                                MainApp.prevChildCount = 0;
                            }
                            Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);

                        } else if (mwra.getPrePreg().equals("2") && bi.rb1802.isChecked()) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);

                        }

                        break;
                    //finish();
                    // Divorced
                    case "2":
                        // Pregnant
                        if (mwra.getPrePreg().equals("1")) {

                            if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                    if(!fpMwra.getChild_count().equals("null")){
                                        MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                    }else{
                                        MainApp.prevChildCount = 0;
                                    }
                                    Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    startActivity(forwardIntent);
                                    //finish();
                                } else {
                                    setResult(RESULT_OK);
                                    //finish();
                                }
                            }
                        } else {      // Not Pregnant
                            if (bi.rb1801.isChecked()) {
                                if(!fpMwra.getChild_count().equals("null")){
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                }else{
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !mwra.getPrePreg().equals("2")) {        // Marital status changed
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                                //finish();
                            } else {
                                setResult(RESULT_OK);
                                //finish();
                            }
                        }
                        break;

                    //finish();

                    // Widow
                    case "3":
                        // Pregnant
                        if (mwra.getPrePreg().equals("1")) {
                            if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                    if(!fpMwra.getChild_count().equals("null")){
                                        MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                    }else{
                                        MainApp.prevChildCount = 0;
                                    }
                                    Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    startActivity(forwardIntent);
                                    //finish();
                                } else {
                                    setResult(RESULT_OK);
                                    //finish();
                                }
                            }
                        } else {      // Not Pregnant
                            // Marital status changed
                            if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !mwra.getPrePreg().equals("2")) {
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                                //finish();
                            } else if (bi.rb1801.isChecked()) {
                                if(!fpMwra.getChild_count().equals("null")){
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                }else{
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else {
                                setResult(RESULT_OK);
                                //finish();
                            }
                        }
                        break;

                    //finish();
                    // Separated
                    case "5":
                        // Pregnant
                        if (mwra.getPrePreg().equals("1")) {

                            if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                    if(!fpMwra.getChild_count().equals("null")){
                                        MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                    }else{
                                        MainApp.prevChildCount = 0;
                                    }
                                    Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    startActivity(forwardIntent);
                                    finish();
                                } else {
                                    setResult(RESULT_OK);
                                    //finish();
                                }
                            }
                        } else {      // Not Pregnant
                            // Marital status changed
                            if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !mwra.getPrePreg().equals("2")) {
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                                //finish();
                            } else if (bi.rb1801.isChecked()) {
                                if(!fpMwra.getChild_count().equals("null")){
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                }else{
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else {
                                setResult(RESULT_OK);
                                //finish();
                            }
                        }
                        break;

                    //finish();
                    case "4": // Unmarried in previous round
                        // if get married in current round
                        if (!bi.rb0604.isChecked()) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                            //finish();

                            // if still unmarried
                        } else if (bi.rb0604.isChecked()) {
                            setResult(RESULT_OK);
                            //finish();
                        }

                        break;
                }
                finish();
            } else {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean insertNewRecord() throws JSONException {
        MainApp.outcome = new Outcome();

        //mwra.populateMetaFollowups();


        long rowId = 0;
        try {
            //rowId = db.addFollowup(followups);
            rowId = db.mwraDao().addMwra(mwra);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        mwra.setId(rowId);
        if (rowId > 0) {
            mwra.setUid(mwra.getDeviceId() + mwra.getId());
            mwra.setSC(mwra.sCtoString());
            db.mwraDao().updateMwra(mwra);
            households.setSA(households.sAtoString());
            db.householdsDao().updateHousehold(households);
            //db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, followups.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean updateDB() {
        int updcount = 0;
        try {
            //updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_SC, followups.sCtoString());

            Mwra updatedFollowups = mwra;
            updatedFollowups.setSC(mwra.sCtoString());
            updcount = db.mwraDao().updateMwra(updatedFollowups);

            mwra.setDeviceId(mwra.getDeviceId() + "_" + mwra.getDeviceId().substring(mwra.getDeviceId().length() - 1));

            updatedFollowups.setDeviceId(mwra.getDeviceId());
            households.setSA(households.sAtoString());
            db.householdsDao().updateHousehold(households);
            //updatedFollowups.setIStatus(mwra.getIStatus());
            //db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_DEVICEID, followups.getDeviceId());
            //db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_ISTATUS, followups.getRc04());
            db.mwraDao().updateMwra(updatedFollowups);
            int repeatCount = (mwra.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = mwra.getDeviceId().substring(0, 16) + mwra.getId() + "_" + repeatCount;
            mwra.setUid(newUID);
            updatedFollowups.setUid(newUID);
            db.mwraDao().updateMwra(updatedFollowups);
            //db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_UID, newUID);


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

    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2] + "/" + oldDateParts[1] + "/" + oldDateParts[0];
        return newDate;
    }


}