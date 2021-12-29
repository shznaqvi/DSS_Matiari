package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionEBinding;

public class SectionEActivity extends AppCompatActivity {

    private static final String TAG = "SectionEActivity";
    ActivitySectionEBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;


        try {

            outcome = db.getOutComeBYID(String.valueOf(++MainApp.outcomeCounter));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(OutCome): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        bi.setOutcome(MainApp.outcome);


        // setListener();

        // set default model values if new mwra
        /*if (MainApp.pregnancy.getRb01().equals("")) {
            MainApp.pregnancy.setRb01(String.valueOf(mwraCount + 1));
            MainApp.pregnancy.setUuid(households.getUid());
            MainApp.pregnancy.setUcCode(households.getUcCode());
            MainApp.pregnancy.setVillageCode(households.getVillageCode());
            MainApp.pregnancy.setsNo(households.getsNo());
            MainApp.pregnancy.setHhNo(households.getHhNo());
            // TODO: set MWRA ID from downloaded data
            //   MainApp.followups.setMWRAID(households.getHhNo());
            MainApp.pregnancy.setUserName(MainApp.user.getUserName());
            MainApp.pregnancy.setSysDate(households.getSysDate());
            MainApp.pregnancy.setDeviceId(MainApp.deviceid);
            MainApp.pregnancy.setHdssId(households.getHdssId());
            MainApp.pregnancy.setAppver(MainApp.versionName + "." + MainApp.versionCode);

            MainApp.pregnancy.setRa01(MainApp.households.getRa01());
        }*/

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);


        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");

        // To set min max range of date fields
        // setDateRanges();


    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (outcome.getUid().equals("") ? insertNewRecord() : updateDB()) {

            if (MainApp.outcomeCounter < MainApp.totalOutcomes) {
                setResult(RESULT_OK);
                startActivity(new Intent(this, SectionEActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                finish();
            } else {
                setResult(RESULT_OK);
                finish();
            }
            //  startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean insertNewRecord() {
        db = MainApp.appInfo.getDbHelper();
        outcome.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addOutcome(outcome);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        outcome.setId(String.valueOf(rowId));
        if (rowId > 0) {
            outcome.setUid(outcome.getDeviceId() + outcome.getId());
            db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_UID, outcome.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

/*    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_SE, outcome.sEtoString());
        } catch (JSONException e) {
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/


    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_SE, outcome.sEtoString());
            // Also reset Synced flag and alter UID
            db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_SYNCED, null);
            // concate last char from uid to alter and create new unique uid

            outcome.setDeviceId(outcome.getDeviceId() + "_" + outcome.getDeviceId().substring(outcome.getDeviceId().length() - 1));
            db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_DEVICEID, outcome.getDeviceId());
            int repeatCount = (outcome.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = outcome.getDeviceId().substring(0, 16) + outcome.getId() + "_" + repeatCount;
            outcome.setUid(newUID);
            db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_UID, newUID);


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

}