package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SectionEActivity extends AppCompatActivity {

    private static final String TAG = "SectionOutcomeActivity";
    ActivitySectionEBinding bi;
    private DssRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        db = MainApp.appInfo.dbHelper;

        setDateRanges();

       //int maxchild = db.OutcomeDao().getMaxChildredBYMother(mwra.getUcCode(), mwra.getUcCode(), mwra.getHhNo(), mwra.getSNo());
       //int maxchildFp = db.FollowUpsScheDao().getMaxChildrenNoBYMotherFromFolloupsSche(fpMwra.getUcCode(), fpMwra.getVillageCode(), fpMwra.getHhNo(), fpMwra.getMsno());
        /*if(fpMwra.getChild_count() != null) {
            MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
        }
*/
        try {
            //outcome = db.getOutComeBYID(String.valueOf(++MainApp.childCount));
            String[] muid = mwra.getUid().split("_");
            outcome = db.OutcomeDao().getOutcomeBYID(mwra.getHdssId(), muid[0], mwra.getSNo(), String.valueOf(++MainApp.prevChildCount));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Outcome): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(outcome.getUid() != null || !outcome.getUid().equals(""))
        {
            try {
                outcome.sEHydrate(outcome.getSE());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Registration
        switch (mwra.getRegRound())
        {
            case "1":
                // New outcome registration
                if(outcome.getUid().equals(""))
                {
                    MainApp.ROUND = mwra.getRound();
                    bi.rc03dob.setText(mwra.getSC().getRb21());
                    outcome.setRc03(mwra.getSC().getRb21());
                    String date = toBlackVisionDate(mwra.getSB().getRb21());
                    bi.rc06.setMinDate(date);
                }else{
                    MainApp.ROUND = mwra.getRound();
                    bi.rc03dob.setEnabled(false);
                }
                // Followup
            case "":
                //for unreported outcome in followup
                if(mwra.getSC().getRb15().equals(""))
                {
                    // new Registration
                    if(outcome.getUid().equals(""))
                    {
                        MainApp.ROUND = mwra.getRound();
                        outcome.setRc03(mwra.getSC().getRb21());
                        String date = toBlackVisionDate(mwra.getSC().getRb21());
                        bi.rc06.setMinDate(date);
                    }else{
                        MainApp.ROUND = mwra.getRound();

                    }

                }else{
                    if(outcome.getUid().equals(""))
                    {
                        MainApp.ROUND = MainApp.fpMwra.getFRound();
                        outcome.setRc03(mwra.getSC().getRb15());
                        String date = toBlackVisionDate(mwra.getSC().getRb15());
                        bi.rc06.setMinDate(date);
                        bi.rc03dob.setEnabled(false);
                    }else{
                        MainApp.ROUND = mwra.getRound();
                        bi.rc03dob.setEnabled(false);
                    }

                }

        }

        outcome.setRc01(outcome.getRc01().isEmpty() ? String.valueOf(MainApp.prevChildCount) : outcome.getRc01());

        bi.setOutcome(outcome);
        bi.btnContinue.setText(outcome.getUid().equals("") ? "Save" : "Update");


        bi.rc03dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String date = toBlackVisionDate(bi.rc03dob.getText().toString());
                bi.rc06.setMinDate(date);

            }
        });



    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        if(outcome.getUid().equals("") ? insertNewRecord() : updateDB())
        {
                if (MainApp.totalChildCount > MainApp.childCount) {

                    MainApp.childCount ++;
                    outcome = new Outcome();
                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionEActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

                } else if(mwra.getRegRound().equals("") || mwra.getPrePreg().equals("2")) {
                    MainApp.childCount = 1;
                    MainApp.totalChildCount = 0;
                    setResult(RESULT_OK);
                    finish();
                    startActivity(new Intent(this, SectionDActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));

                }else{
                    MainApp.childCount = 1;
                    MainApp.totalChildCount = 0;
                    setResult(RESULT_OK);
                    finish();

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
            rowId = db.OutcomeDao().addOutcome(outcome);
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
            db.OutcomeDao().updateOutcome(outcome);
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
            updcount = db.OutcomeDao().updateOutcome(outcome);

            outcome.setDeviceId(outcome.getDeviceId() + "_" + outcome.getDeviceId().substring(outcome.getDeviceId().length() - 1));
            updatedOutcome.setDeviceId(outcome.getDeviceId());
            //updatedOutcome.setIStatus(outcome.getIStatus());
            db.OutcomeDao().updateOutcome(updatedOutcome);


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
        MainApp.prevChildCount--;
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


    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(Objects.requireNonNull(sdf.parse(mwra.getSC().getRb01a())));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.MONTH, -3);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +3); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }
}