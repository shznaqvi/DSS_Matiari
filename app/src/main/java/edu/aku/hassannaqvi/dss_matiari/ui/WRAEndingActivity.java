package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;

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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityFpEndingBinding;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityMwraBinding;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityWraEndingBinding;


public class WRAEndingActivity extends AppCompatActivity {

    private static final String TAG = "FPEndingActivity";
    ActivityWraEndingBinding bi;
    int sectionMainCheck;
    private DatabaseHelper db;
    int visitCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainApp.households.setVisitNo(String.valueOf(Integer.parseInt(MainApp.households.getVisitNo())+1));

        bi = DataBindingUtil.setContentView(this, R.layout.activity_wra_ending);
        bi.setFollowup(followups);
        //bi.setHousehold(MainApp.households);
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
        bi.istatusd.setEnabled(!complete);
        bi.istatuse.setEnabled(!complete);
        bi.istatusf.setEnabled(!complete);


        //visit date
        Calendar cal = Calendar.getInstance();

        String now = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());


        if (visitCount == 1) {
            followups.setRc01v2(now);
            //  bi.fldGrpCVra01v2.setVisibility(View.VISIBLE);
        } else if (visitCount > 1) {
            followups.setRc01v3(now);
            // bi.fldGrpCVra01v3.setVisibility(View.VISIBLE);

        }
/*
        bi.fldGrpCVra01v2.setVisibility(visitCount == 1 ? View.VISIBLE : View.GONE);
        bi.fldGrpCVra01v3.setVisibility(visitCount > 1 ? View.VISIBLE : View.GONE);*/
    }

    private void saveDraft() {

        followups.setiStatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "3"
                : bi.istatusd.isChecked() ? "4"
                : bi.istatuse.isChecked() ? "5"
                : bi.istatusf.isChecked() ? "6"
                : "-1");
        //fpHouseholds.setiStatus96x(bi.istatusdx.getText().toString());

        visitCount++;

        // Only increment visit count if Refused or Locked AND NOT FIRST VISIT
/*        if (bi.istatusb.isChecked() ||
                bi.istatusc.isChecked()) {
            // Do not increment if saving First Visit
            if(!MainApp.households.getiStatus().equals(""))
            MainApp.households.setVisitNo(String.valueOf(visitCount));
        }*/
        followups.setVisitNo(String.valueOf(visitCount));

        /*switch (visitCount) {
            case 1:
                followups.setRc11(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"
                );
                fpHouseholds.setRa11x(bi.istatusdx.getText().toString());
                break;
            case 2:
                fpHouseholds.setRa12(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                fpHouseholds.setRa12x(bi.istatusdx.getText().toString());
                fpHouseholds.setRa01v2(bi.ra01v2.getText().toString());
                break;
            case 3:
                fpHouseholds.setRa13(
                        bi.istatusa.isChecked() ? "1" :
                                bi.istatusb.isChecked() ? "2" :
                                        bi.istatusc.isChecked() ? "3" :
                                                bi.istatuse.isChecked() ? "4" :
                                                        bi.istatusd.isChecked() ? "96" :
                                                                "-1"

                );
                fpHouseholds.setRa13x(bi.istatusdx.getText().toString());
                fpHouseholds.setRa01v3(bi.ra01v3.getText().toString());
                break;

        }
*/        // households.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
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
        int updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_ISTATUS, followups.getiStatus());
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
