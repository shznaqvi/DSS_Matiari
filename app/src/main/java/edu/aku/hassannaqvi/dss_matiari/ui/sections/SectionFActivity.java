package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcomeFollowups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionFBinding;

public class SectionFActivity extends AppCompatActivity {

    private static final String TAG = "OutcomeFollowupActivity";
    ActivitySectionFBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f);
        db = MainApp.appInfo.dbHelper;

        MainApp.ROUND = MainApp.fpMwra.getFRound();

        try {
            outcomeFollowups = db.getOutcomeFollowupsBySno(MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound(), fpMwra.getMuid());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (outcomeFollowups.getUid().equals("")) {
            outcomeFollowups.setRc12ln(MainApp.fpMwra.getRb01());
            outcomeFollowups.setRb02(MainApp.fpMwra.getRb02());
            MainApp.outcomeFollowups.setRb03(MainApp.fpMwra.getRb03());
            outcomeFollowups.setRb04(MainApp.fpMwra.getRb04());
            outcomeFollowups.setRc12(MainApp.fpMwra.getRc12());
            outcomeFollowups.setHdssId(MainApp.fpMwra.getHdssid());
            outcomeFollowups.setHhNo(MainApp.fpMwra.getHhNo());
            outcomeFollowups.setMuid(fpMwra.getMuid());

        }

        bi.setFpHouseholds(MainApp.fpHouseholds);
        bi.setOutcome(outcomeFollowups);

        setImmersive(true);

        bi.btnContinue.setText(outcomeFollowups.getUid().equals("") ? "Save" : "Update");

        if(!MainApp.fpMwra.getRb04().equals("98") && !MainApp.fpMwra.getRb04().equals(""))
        {
            String date = toBlackVisionDate(outcomeFollowups.getRb04());

            bi.rc14.setMinDate(date);
        }


    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(outcomeFollowups.getRc01a()));// all done

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
            cal.setTime(sdf.parse(outcomeFollowups.getRc14()));// all done
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String minDOD = sdf.format(cal.getTime());

            // Single
            /*bi.rc1401.setMinDate(minDOD);
            bi.rc1402.setMinDate(minDOD);
            bi.rc1403.setMinDate(minDOD)*/
            ;


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (outcomeFollowups.getUid().equals("") ? insertNewRecord() : updateDB()) {

            if(updateDB()) {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean insertNewRecord() {
        if (fpHouseholds.getUid().equals("")) {
            insertFpHousehold();
        }

        outcomeFollowups.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addOutcomeFollowup(outcomeFollowups);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        outcomeFollowups.setId(String.valueOf(rowId));
        if (rowId > 0) {
            outcomeFollowups.setUid(outcomeFollowups.getDeviceId() + outcomeFollowups.getId());
            db.updateOutcomeFollouwps(TableContracts.OutcomeFollowupTable.COLUMN_UID, outcomeFollowups.getUid());
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
            updcount = db.updateOutcomeFollouwps(TableContracts.OutcomeFollowupTable.COLUMN_SE, outcomeFollowups.sEtoString());
            // Also reset Synced flag and alter UID
            // db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SYNCED, null);
            // concate last char from uid to alter and create new unique uid

            outcomeFollowups.setDeviceId(outcomeFollowups.getDeviceId() + "_" + outcomeFollowups.getDeviceId().substring(outcomeFollowups.getDeviceId().length() - 1));
            db.updateOutcomeFollouwps(TableContracts.OutcomeFollowupTable.COLUMN_DEVICEID, outcomeFollowups.getDeviceId());
            int repeatCount = (outcomeFollowups.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = outcomeFollowups.getDeviceId().substring(0, 16) + outcomeFollowups.getId() + "_" + repeatCount;
            outcomeFollowups.setUid(newUID);
            db.updateOutcomeFollouwps(TableContracts.OutcomeFollowupTable.COLUMN_UID, newUID);


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

    }

    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2].trim() + "/" + oldDateParts[1].trim() + "/" + oldDateParts[0].trim();
        return newDate;
    }
}