package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcomeFollowups;
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

import java.util.regex.Pattern;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;

import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SectionEActivity extends AppCompatActivity {

    private static final String TAG = "SectionOutcomeActivity";
    ActivitySectionEBinding bi;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        db = MainApp.appInfo.dbHelper;

        try {
            //outcome = db.getOutComeBYID(String.valueOf(++MainApp.childCount));
            String[] muid = mwra.getUid().split("_");
            outcome = DssRoomDatabase.getDbInstance().OutcomeDao().getOutcomeBYID(muid[0], String.valueOf(++MainApp.childCount));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcome): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        MainApp.ROUND = MainApp.fpMwra.getFRound();


        outcome.setRc03(mwra.getRb13());
        outcome.setRc01(outcome.getRc01().isEmpty() ? String.valueOf(MainApp.childCount) : outcome.getRc01());

        bi.setOutcome(outcome);
        //setImmersive(true);



          /*  // Followups data
            outcomeFollowups.setUuid(followups.getUid());
            outcomeFollowups.setMuid(outcome.getMuid());
            outcomeFollowups.setUcCode(followups.getUcCode());
            outcomeFollowups.setVillageCode(followups.getVillageCode());
            outcomeFollowups.setMuid(followups.getFmuid());
            //MainApp.followups.setStructureNo(households.getStructureNo());
            outcomeFollowups.setSno(outcome.getSno());
            outcomeFollowups.setRound("0");
            outcomeFollowups.setHhNo(followups.getHhNo());
            outcomeFollowups.setUserName(MainApp.user.getUserName());
            outcomeFollowups.setSysDate(followups.getSysDate());
            outcomeFollowups.setDeviceId(MainApp.deviceid);
            outcomeFollowups.setHdssId(followups.getHdssId());
            outcomeFollowups.setAppver(MainApp.versionName + "." + MainApp.versionCode);
*/

        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");


        String date = toBlackVisionDate(mwra.getRb13());

        bi.rc06.setMinDate(date);


    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        //if(outcome.getUid().equals("") ? !insertNewRecord()) return;
        //if(!insertNewFollowupRecord()) return;

        if(outcome.getUid().equals("") ? insertNewRecord() : updateDB())
        {
                if (MainApp.totalChildCount > MainApp.childCount) {

                    outcome = new Outcome();
                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionEActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

                } else {
                    MainApp.childCount = 0;
                    MainApp.totalChildCount = 0;
                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionDActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }


    }

    private boolean insertNewRecord() throws JSONException {
           outcome.populateMeta();

        long rowId = 0;
        try {
            //rowId = db.addOutcome(outcome);
            rowId = DssRoomDatabase.getDbInstance().OutcomeDao().addOutcome(outcome);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcomes): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        outcome.setId(rowId);
        if (rowId > 0) {
            outcome.setUid(outcome.getDeviceId() + outcome.getId());
            outcome.setSE(outcome.sEtoString());
            DssRoomDatabase.getDbInstance().OutcomeDao().updateOutcome(outcome);
            //db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_UID, outcome.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    private boolean updateDB() {
        int updcount = 0;
        try {
            //updcount = db.updatesOutcomeColumn(TableContracts.OutcomeTable.COLUMN_SE, outcome.sEtoString());
            Outcome updatedOutcome = outcome;
            updatedOutcome.setSE(outcome.sEtoString());
            updcount = DssRoomDatabase.getDbInstance().OutcomeDao().updateOutcome(outcome);

            outcome.setDeviceId(outcome.getDeviceId() + "_" + outcome.getDeviceId().substring(outcome.getDeviceId().length() - 1));
            updatedOutcome.setDeviceId(outcome.getDeviceId());
            updatedOutcome.setIStatus(outcome.getIStatus());
            DssRoomDatabase.getDbInstance().OutcomeDao().updateOutcome(updatedOutcome);


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
        MainApp.totalChildCount =0;
        MainApp.childCount --;
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        //setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }



    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2] + "/" + oldDateParts[1] + "/" + oldDateParts[0];
        return newDate;
    }



}