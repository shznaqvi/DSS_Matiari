package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.MwraActivity;

public class SectionAActivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionABinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);
        bi.setHousehold(MainApp.households);

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;

        // Update text for btnContinue
        bi.btnContinue.setText(MainApp.households.getUid().equals("") ? "Save" : "Update");

        // Add same as previous check box for Mohalla
        if (MainApp.previousAddress.trim().equals("") || !MainApp.households.getRa08().equals(""))
            bi.rb08check.setVisibility(View.GONE);

        // Set Visit data same as previous household of the same day.
        /*    if (!MainApp.dateOfVisit.trim().equals(""))
         */

        bi.rb08check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //MainApp.households.setRa01(MainApp.dateOfVisit);
                    bi.ra08.setText(MainApp.previousAddress);
                } else {
                    bi.ra08.setText("");

                }
            }
        });
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (!insertNewRecord()) return;
        if (updateDB()) {


            if (households.getRa20().equals("1")) {
                startActivity(new Intent(this, MwraActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT));
            } else {
                startActivity(new Intent(this, EndingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        .putExtra("noWRA", true));
            }


            finish();

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean insertNewRecord() {
        if (!MainApp.households.getUid().equals("")) return true;
        saveDraft();

        // Updating date at the time of Insert instead of SaveDraft()
        //    MainApp.households.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        long rowId = 0;
        try {
            rowId = db.addHousehold(households);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        households.setId(String.valueOf(rowId));
        if (rowId > 0) {
            households.setUid(households.getDeviceId() + households.getId());
            db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_UID, households.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = 0;
        try {
            updcount = db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_SA, households.sAtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ProcessStart (JSONException): " + e.getMessage());
            return false;
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDraft() {

        //  MainApp.households = new Households();

        /*households.setUserName(MainApp.user.getUserName());
        households.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        households.setDeviceId(MainApp.deviceid);
        households.setRa06(MainApp.selectedUC);
        households.setRa07(MainApp.selectedVillage);
        //households.setHdssId(getUcCode() + "-" + getVillageCode() + "-" + getHhNo());
        households.setDeviceId(MainApp.deviceid);
        households.setAppver(MainApp.versionName + "." + MainApp.versionCode);
        */
        MainApp.previousAddress = bi.ra08.getText().toString();
        MainApp.dateOfVisit = bi.ra01.getText().toString();

/*
        households.setRa14(bi.ra14.getText().toString());

        households.setRa15(bi.ra15.getText().toString());

        households.setRa16(bi.ra16.getText().toString());

        households.setRa17_a(bi.ra17A.getText().toString());

        households.setRa17_b(bi.ra17B.getText().toString());

        households.setRa17_c(bi.ra17C.getText().toString());

        households.setRa17_d(bi.ra17D.getText().toString());

        households.setRa18(bi.ra18.getText().toString());
*/


    }

    public void btnEnd(View view) {
        //saveDraft();
        /// if ((insertNewRecord())) {
        //Toast.makeText(this, "Household information not recorded.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_CANCELED);
        finish();
        // Intent i = new Intent(this, MainActivity.class);
        //   i.putExtra("complete",false);
        // startActivity(i);
        //    }

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        //startActivity(new Intent(this, MainActivity.class));
    }


}