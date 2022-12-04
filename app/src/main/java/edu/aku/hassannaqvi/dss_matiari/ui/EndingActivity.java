package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;

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
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;


public class EndingActivity extends AppCompatActivity {

    private static final String TAG = "EndingActivity";
    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DssRoomDatabase db;
    int visitCount;

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
        boolean noWRA = getIntent().getBooleanExtra("noWRA", false);

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

        } else if (visitCount > 1) {
            MainApp.households.setRa01v3(now);


        }
    }

    private void saveDraft() {

        visitCount++;

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

        //db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_ISTATUS, MainApp.households.getIStatus());
        //db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_VISIT_NO, MainApp.households.getVisitNo());
        try {
            Households updatedHousehold = households;
            updatedHousehold.setIStatus(households.getIStatus());
            updatedHousehold.setVisitNo(households.getVisitNo());
            updatedHousehold.setIStatus96x(households.getIStatus96x());
            updatedHousehold.setSA(households.sAtoString());

            updcount = DssRoomDatabase.getDbInstance().householdsDao().updateHousehold(households);
            //updcount = db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_SA, MainApp.households.sAtoString());


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Households): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "UpdateDB (JSONException): " + e.getMessage());
            return false;
        }
        /*try {
            MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.households.getHdssId());

            if (!MainApp.fpHouseholds.getUid().equals("")) {
                MainApp.fpHouseholds.setiStatus(MainApp.households.getIStatus());
                MainApp.fpHouseholds.setVisitNo(MainApp.households.getVisitNo());
                MainApp.fpHouseholds.setRa01v2(MainApp.households.getRa01v2());
                MainApp.fpHouseholds.setRa01v3(MainApp.households.getRa01v3());
                db.updatesFPHouseholdsColumn(TableContracts.FPHouseholdTable.COLUMN_ISTATUS, MainApp.fpHouseholds.getiStatus());
                db.updatesFPHouseholdsColumn(TableContracts.FPHouseholdTable.COLUMN_VISIT_NO, MainApp.fpHouseholds.getVisitNo());
                db.updatesFPHouseholdsColumn(TableContracts.FPHouseholdTable.COLUMN_SA, MainApp.fpHouseholds.sAtoString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FPHouseholds): " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }*/
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
