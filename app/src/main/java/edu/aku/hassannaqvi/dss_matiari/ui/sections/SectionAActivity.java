package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
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
        bi.setHousehold(households);

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;

        // Update text for btnContinue
        bi.btnContinue.setText(households.getUid().equals("") ? "Save" : "Update");

        // Add same as previous check box for Mohalla
        if (MainApp.previousAddress.trim().equals("") || !households.getRa08().equals(""))
            bi.rb08check.setVisibility(View.GONE);


        bi.ra20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ra2001.isChecked()) {
                    bi.ra17C2.setMinvalue(1);
                    bi.ra17C2.setMaxvalue(20);
                }
                else if (bi.ra2002.isChecked()) {
                    bi.ra17C2.setMinvalue(0);
                    bi.ra17C2.setMaxvalue(0);
                }
            }

        });

        bi.rb08check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.ra08.setText(MainApp.previousAddress);
                } else {
                    bi.ra08.setText("");

                }
            }
        });

        if(fpHouseholds != null) {
            // FP Households Data
            fpHouseholds.setHhNo(households.getHhNo());
            fpHouseholds.setUcCode(households.getUcCode());
            fpHouseholds.setVillageCode(households.getVillageCode());
            fpHouseholds.setDeviceId(MainApp.deviceid);
            fpHouseholds.setUserName(MainApp.user.getUserName());
            fpHouseholds.setFround("0");

            fpHouseholds.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
            fpHouseholds.setHdssId(households.getUcCode() + "-" + households.getVillageCode() + "-" + households.getHhNo());
            fpHouseholds.setStructureNo(households.getRa10());
        }

    }

    public void btnContinue(View view) {

        if (view.getId() == bi.btnLocked.getId()) {
            bi.ra14.setTag("-1");
            bi.ra19.setTag("-1");
            bi.ra15.setTag("-1");
            bi.ra20.setTag("-1");
            bi.ra17A1.setTag("-1");
            bi.ra17B1.setTag("-1");
            bi.ra17C1.setTag("-1");
            bi.ra17D1.setTag("-1");
            bi.ra17A2.setTag("-1");
            bi.ra17B2.setTag("-1");
            bi.ra17C2.setTag("-1");
            bi.ra17D2.setTag("-1");
            bi.ra18.setTag("-1");
        }
        if (!formValidation()) return;
        if (!insertNewRecord()) return;


        //if (!insertFpHousehold()) return;
        if (updateDB()) {

            setResult(RESULT_OK);
            if (households.getRa20().equals("1")) {
                finish();
                startActivity(new Intent(this, MwraActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT));
            } else {
                finish();
                startActivity(new Intent(this, EndingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        .putExtra("noWRA", true));
            }

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean insertNewRecord() {

        if (!MainApp.households.getUid().equals("")) return true;
        MainApp.households.populateMeta();
        long rowId = 0;
        try {
            //rowId = db.addHousehold(households);
            rowId = DssRoomDatabase.getDbInstance().householdsDao().addHousehold(households);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        households.setId(rowId);
        if (rowId > 0) {
            households.setUid(households.getDeviceId() + households.getId());
            DssRoomDatabase.getDbInstance().householdsDao().updateHousehold(households);
//            db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_UID, households.getUid());
            if (fpHouseholds != null && fpHouseholds.getUid().equals("")) {
                insertFpHousehold();
            }
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean insertFpHousehold() {
        db = MainApp.appInfo.getDbHelper();
        if (!MainApp.fpHouseholds.getUid().equals("")) return true;

        long rowId = 0;
        try {
            rowId = db.addFpHousehold(fpHouseholds);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FPHouseholds): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertNewRecord (JSONException): " + e.getMessage());
            return false;
        }
        fpHouseholds.setId(String.valueOf(rowId));
        if (rowId > 0) {
            fpHouseholds.setUid(fpHouseholds.getDeviceId() + fpHouseholds.getId());
            db.updatesFPHouseholdsColumn(TableContracts.FPHouseholdTable.COLUMN_UID, fpHouseholds.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }




    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        DssRoomDatabase dssdb = DssRoomDatabase.getDbInstance();
        int updcount = 0;
        try {
//            updcount = db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_SA, households.sAtoString());
            Households updatedHousehold = households;
            updatedHousehold.setSA(households.sAtoString());
            updcount = dssdb.householdsDao().updateHousehold(updatedHousehold);
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