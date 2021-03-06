package edu.aku.hassannaqvi.dss_matiari.ui;

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


public class EndingActivity extends AppCompatActivity {

    private static final String TAG = "EndingActivity";
    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DatabaseHelper db;
    int visitCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainApp.households.setVisitNo(String.valueOf(Integer.parseInt(MainApp.households.getVisitNo())+1));

        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setHousehold(MainApp.households);
        setSupportActionBar(bi.toolbar);
        //setTitle(R.string.section1_mainheading);
        //bi.hhStatus.setText(bi.hhStatus.getText()+" "+(Integer.parseInt(MainApp.households.getVisitNo())+1));

        db = MainApp.appInfo.dbHelper;
        visitCount = Integer.parseInt(MainApp.households.getVisitNo());
        boolean complete = getIntent().getBooleanExtra("complete", false);
        boolean noWRA = getIntent().getBooleanExtra("noWRA", false);
/*        boolean refused = getIntent().getBooleanExtra("refused", false);
        boolean locked = getIntent().getBooleanExtra("locked", false);*/

        bi.istatusa.setEnabled(complete);
        bi.istatusb.setEnabled(!complete);
        bi.istatusc.setEnabled(!complete);
        bi.istatuse.setEnabled(noWRA);
        bi.istatusd.setEnabled(true); // Always TRUE


        //visit date
        Calendar cal = Calendar.getInstance();

        String now = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());


        if (visitCount == 1) {
            MainApp.households.setRa01v2(now);
            //  bi.fldGrpCVra01v2.setVisibility(View.VISIBLE);
        } else if (visitCount > 1) {
            MainApp.households.setRa01v3(now);
            // bi.fldGrpCVra01v3.setVisibility(View.VISIBLE);

        }
/*
        bi.fldGrpCVra01v2.setVisibility(visitCount == 1 ? View.VISIBLE : View.GONE);
        bi.fldGrpCVra01v3.setVisibility(visitCount > 1 ? View.VISIBLE : View.GONE);*/
    }

    private void saveDraft() {

        visitCount++;

        // Only increment visit count if Refused or Locked AND NOT FIRST VISIT
/*        if (bi.istatusb.isChecked() ||
                bi.istatusc.isChecked()) {
            // Do not increment if saving First Visit
            if(!MainApp.households.getiStatus().equals(""))
            MainApp.households.setVisitNo(String.valueOf(visitCount));
        }*/
        MainApp.households.setVisitNo(String.valueOf(visitCount));

        switch (visitCount) {
            case 1:
                MainApp.households.setRa11(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"
                );
                MainApp.households.setRa11x(bi.istatusdx.getText().toString());
                break;
            case 2:
                MainApp.households.setRa12(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                MainApp.households.setRa12x(bi.istatusdx.getText().toString());
                MainApp.households.setRa01v2(bi.ra01v2.getText().toString());
                break;
            case 3:
                MainApp.households.setRa13(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                MainApp.households.setRa13x(bi.istatusdx.getText().toString());
                MainApp.households.setRa01v3(bi.ra01v3.getText().toString());
                break;

        }
        // households.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void btnEnd(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {
            setResult(RESULT_OK);
            finish();
            //Intent i = new Intent(this, MainActivity.class);
            // startActivity(i);
            Toast.makeText(this, "Entry Complete", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error in updating Database.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        int updcount = 0;
        db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_ISTATUS, MainApp.households.getiStatus());
        db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_VISIT_NO, MainApp.households.getVisitNo());
        try {
            updcount = db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_SA, MainApp.households.sAtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

}
