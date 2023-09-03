package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.allMwraRefusedOrMigrated;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityEndingBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPHouseholdActivity;


public class EndingActivity extends AppCompatActivity {

    private static final String TAG = "EndingActivity";
    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DssRoomDatabase db;
    int visitCount;
    //Households updatedHousehold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainApp.households.setVisitNo(String.valueOf(Integer.parseInt(MainApp.households.getVisitNo())+1));

        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setHousehold(MainApp.households);
        setSupportActionBar(bi.toolbar);

        db = MainApp.appInfo.dbHelper;
        visitCount = Integer.parseInt(MainApp.households.getVisitNo());
        boolean complete = getIntent().getBooleanExtra("complete", false);
        boolean refusedOrMigrated = getIntent().getBooleanExtra("refused", false);
        boolean noWRA = getIntent().getBooleanExtra("noWRA", false);

        bi.istatusa.setEnabled(complete);
        bi.istatusb.setEnabled(!complete);
        bi.istatusc.setEnabled(!complete);
        if(!complete && refusedOrMigrated) {
            bi.istatusf.setEnabled(true);
            bi.istatush.setEnabled(true);
        }else{
            bi.istatusf.setEnabled(false);
            bi.istatush.setEnabled(false);
        }
        bi.istatusg.setEnabled(!complete);

        bi.istatuse.setEnabled(noWRA);
        bi.istatusd.setEnabled(true); // Always TRUE


        //visit date
        Calendar cal = Calendar.getInstance();

        String now = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());


        if (visitCount == 1) {
            MainApp.households.setRa01v2(now);

        } else if (visitCount > 1) {
            MainApp.households.setRa01v3(now);


        }
    }

    private void saveDraft() throws JSONException {

        visitCount++;

        MainApp.households.setVisitNo(String.valueOf(visitCount));

        switch (visitCount) {
            case 1:
                MainApp.households.setRa19a(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusf.isChecked() ? "5" :
                                                                bi.istatusg.isChecked() ? "6" :
                                                                        bi.istatush.isChecked() ? "7" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"
                );
                MainApp.households.setRa19ax(bi.istatusdx.getText().toString());
                break;
            case 2:
                MainApp.households.setRa19b(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusf.isChecked() ? "5" :
                                                                bi.istatusg.isChecked() ? "6" :
                                                                        bi.istatush.isChecked() ? "7" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                MainApp.households.setRa19bx(bi.istatusdx.getText().toString());
                MainApp.households.setRa01v2(bi.ra01v2.getText().toString());
                break;
            case 3:
                MainApp.households.setRa19c(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusf.isChecked() ? "5" :
                                                                bi.istatusg.isChecked() ? "6" :
                                                                        bi.istatush.isChecked() ? "7" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                MainApp.households.setRa19cx(bi.istatusdx.getText().toString());
                MainApp.households.setRa01v3(bi.ra01v3.getText().toString());
                break;



        }


        // households.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void btnEnd(View view) throws JSONException {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {
            setResult(RESULT_OK);
            finish();
            allMwraRefusedOrMigrated.clear();
            //Intent i = new Intent(this, FPHouseholdActivity.class);
            //startActivity(i);
            Toast.makeText(this, "Entry Complete", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error in updating Database.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        int updcount = 0;

        try {
            Households updatedHousehold = households;
            Outcome updatedOutcome = outcome;
            Mwra updatedMwra = MainApp.mwra;

            if(visitCount == 1)
            {
                updatedHousehold.setRa19a(households.getRa19a());
                updatedHousehold.setRa19ax(households.getRa19ax());
            }else if(visitCount == 2)
            {
                updatedHousehold.setRa19b(households.getRa19b());
                updatedHousehold.setRa19bx(households.getRa19bx());
            }else if(visitCount == 3)
            {
                updatedHousehold.setRa19c(households.getRa19c());
                updatedHousehold.setRa19cx(households.getRa19cx());
            }
            updatedHousehold.setIStatus(households.getIStatus());
            updatedOutcome.setIStatus(households.getIStatus());

            updatedHousehold.setVisitNo(households.getVisitNo());
            updatedHousehold.setIStatus96x(households.getIStatus96x());
            updatedHousehold.setSA(households.sAtoString());

            db.OutcomeDao().updateOutcome(updatedOutcome);
            updcount = db.householdsDao().updateHousehold(updatedHousehold);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Households): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "UpdateDB (JSONException): " + e.getMessage());
            return false;
        }

        return updcount > 0;
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpCVstatus);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}
