package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followUpsScheHHList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraStatus;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedMember;
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


        // Set Round Number from followups data
        MainApp.ROUND = MainApp.fpMwra.getFRound();

        try {
            //followups = db.getFollowupsBySno(MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
            mwra = db.mwraDao().getFollowupsBySno(MainApp.households.getUid(), MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(mwra.getRegRound().equals(""))
        {
            mwra.populateMetaFollowups();
        }

        bi.setFollowups(mwra);

        setImmersive(true);

        bi.btnContinue.setText(MainApp.mwra.getUid().equals("") ? "Save" : "Update");

        bi.rb15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.rb1501.isChecked())
                {
                    MainApp.totalChildCount = 1;
                }else if(bi.rb1502.isChecked()){
                    MainApp.totalChildCount = 2;
                }else if (bi.rb1503.isChecked())
                {
                    MainApp.totalChildCount = 3;
                }

            }

        });

        if(!MainApp.households.getVisitNo().equals("") && Integer.parseInt(households.getVisitNo()) == 2)
        {
            bi.rb1008.setEnabled(true);
            bi.rb1004.setEnabled(false);
            bi.rb1004.setChecked(false);
        }else{
            bi.rb1008.setEnabled(false);
            bi.rb1004.setEnabled(true);
        }

        bi.rb10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.rb1004.isChecked())
                {
                    mwraStatus.put(followUpsScheHHList.get(selectedMember).getMuid(), false);
                }else{
                    mwraStatus.remove(followUpsScheHHList.get(selectedMember).getMuid());
                }
   }
        });

        bi.rb17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(bi.rb1701.isChecked())
                {
                    MainApp.totalChildCount = 1;
                }else if(bi.rb1702.isChecked())
                {
                    MainApp.totalChildCount = 2;
                }else if(bi.rb1703.isChecked()){
                    MainApp.totalChildCount = 3;
                }
            }
        });


    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(mwra.getRb01a()));// all done

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
            cal.setTime(sdf.parse(mwra.getRb13()));// all done
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



    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        if (mwra.getUid().equals("") ? insertNewRecord() : updateDB()) {

            if(bi.rb1001.isChecked()) {

                switch (mwra.getRb06()) {
                    // Married in Previous Round
                    case "1":
                        // Pregnant
                        if (mwra.getPrePreg().equals("1")) {
                            // If pregnancy continued
                            if (bi.rb1201.isChecked()) {
                                setResult(RESULT_OK);
                                //finish();
                            } else {  // If Pregnancy ended
                                if (bi.rb1401.isChecked()) // If Live Birth
                                {
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
                        } else if(mwra.getPrePreg().equals("2") && bi.rb1601.isChecked()) {   // Not Pregnant
                            Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);

                        }else if (mwra.getPrePreg().equals("2") && bi.rb1602.isChecked()){
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

                            if (bi.rb1201.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1401.isChecked()) {    // Live Birth
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
                            if(bi.rb1601.isChecked()){
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            }else if (bi.rb1401.isChecked()) {        // Marital status changed
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
                            if (bi.rb1201.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1401.isChecked()) {    // Live Birth
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
                            if (bi.rb0601.isChecked()) {
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                                //finish();
                            } else if(bi.rb1601.isChecked()) {
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            }
                            else {
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

                            if (bi.rb1201.isChecked()) {  // If Pregnancy Continued
                                setResult(RESULT_OK);
                                //finish();
                            } else {     // If Pregnancy ended
                                if (bi.rb1401.isChecked()) {    // Live Birth
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
                            if (bi.rb0601.isChecked()) {
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                                //finish();
                            } else if(bi.rb1601.isChecked())
                            {
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            }
                            else {
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
            }else{
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean insertNewRecord() throws JSONException {
        MainApp.outcome = new Outcome();

        mwra.populateMetaFollowups();


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
            updcount = db.mwraDao().updateMwra(mwra);

            mwra.setDeviceId(mwra.getDeviceId() + "_" + mwra.getDeviceId().substring(mwra.getDeviceId().length() - 1));

            updatedFollowups.setDeviceId(mwra.getDeviceId());
            updatedFollowups.setIStatus(mwra.getIStatus());
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


}