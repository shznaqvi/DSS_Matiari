package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCxBinding;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionOutcomeBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;

public class SectionOutcomeActivity extends AppCompatActivity {

    private static final String TAG = "SectionOutcomeActivity";
    ActivitySectionOutcomeBinding bi;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_outcome);
        db = MainApp.appInfo.dbHelper;

        try {
            outcome = db.getOutComeBYID(String.valueOf(++MainApp.childCount));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcome): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        outcome.setRc12dob(followups.getRc10());

        outcome.setRc12ln(String.valueOf(MainApp.childCount));
        //bi.rc12ln.setText(MainApp.childCount);
        // Set Round Number from followups data
        MainApp.ROUND = MainApp.fpMwra.getfRound();

        try {
            followups = db.getFollowupsBySno(MainApp.fpMwra.getRb01(), MainApp.fpMwra.getfRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bi.setOutcome(outcome);
        setImmersive(true);

        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");


    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        if(!insertNewRecord()) return;

        if(updateDB())
        {
                if (MainApp.totalChildCount > MainApp.childCount) {

                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionOutcomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

                } else {
                    MainApp.childCount = 0;
                    MainApp.totalChildCount = 0;
                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionCx2Activity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }


    }

    private boolean insertNewRecord() {
           outcome.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addOutcome(outcome);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcomes): " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_SE, outcome.sEtoString());
            // Also reset Synced flag and alter UID
            // db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_SYNCED, null);
            // concate last char from uid to alter and create new unique uid

            outcome.setDeviceId(outcome.getDeviceId() + "_" + outcome.getDeviceId().substring(outcome.getDeviceId().length() - 1));
            db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_DEVICEID, outcome.getDeviceId());
            /*int repeatCount = (outcome.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = followups.getDeviceId().substring(0, 16) + followups.getId() + "_" + repeatCount;
            followups.setUid(newUID);*/
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
        //setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);
       /*
       if (!compareTwoDate(bi.rb08, 2,
                "LMP should be within 2 months back from DOV")) return false;
        return compareTwoDate(bi.rb09, 9,
                "EDD should be within 9 months back from DOV");
                */
    }


}