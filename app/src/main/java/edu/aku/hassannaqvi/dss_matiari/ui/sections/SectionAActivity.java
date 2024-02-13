package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.MwraActivity;

public class SectionAActivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionABinding bi;
    private DssRoomDatabase db;

    private Households.SA sA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);

        String date = toBlackVisionDate("2023-01-01");
        bi.ra01.setMinDate(date);


        sA = Households.SA.getData();
        sA = sA == null ? new Households.SA() : sA;
        bi.setSA(sA);

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;

        // Update text for btnContinue
        bi.btnContinue.setText(households.getUid().equals("") ? "Save" : "Update");

        // Add same as previous check box for Mohalla
        if (MainApp.previousAddress.trim().equals("") || !households.getSA().getRa08().equals(""))
            bi.rb08check.setVisibility(View.GONE);


        bi.ra15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ra1501.isChecked()) {
                    bi.ra17C2.setMinvalue(1);
                    bi.ra17C2.setMaxvalue(20);
                }
                else if (bi.ra1502.isChecked()) {
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

    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;

        // New form
        // If 'Edit form' option is selected
            // Check data in db
        Households.saveMainData(households.getHdssId(), sA);
            Households form = db.householdsDao().getHouseholdByHDSSIDASC(households.getHdssId());
            if (form != null) {
                // wraId found
                households = form;
                if (sA.getRa15().equals("1")) {
                    finish();
                    //Households.saveMainData(households.getHdssId(), sA);
                    Households.SA.saveData(sA);
                    startActivity(new Intent(this, MwraActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT));
                } else {
                    finish();
                    //Households.saveMainData(households.getHdssId(), sA);
                    Households.SA.saveData(sA);
                    startActivity(new Intent(this, EndingActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                            .putExtra("noWRA", true));
                }
        }

    }

/*    public void btnContinue(View view) {

        if (view.getId() == bi.btnLocked.getId()) {
            bi.ra12.setTag("-1");
            bi.ra13.setTag("-1");
            bi.ra14.setTag("-1");
            bi.ra15.setTag("-1");
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

        if (updateDB()) {

            setResult(RESULT_OK);
            if (households.getSA().getRa15().equals("1")) {
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
    }*/

    /*private boolean insertNewRecord() {

        if (!MainApp.households.getUid().equals("")) return true;
        MainApp.households.populateMeta();
        long rowId = 0;
        try {
            //rowId = db.addHousehold(households);
            rowId = db.householdsDao().addHousehold(households);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        households.setId(rowId);
        if (rowId > 0) {
            households.setUid(households.getDeviceId() + households.getId());
            db.householdsDao().updateHousehold(households);
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean updateDB() {
        //DssRoomDatabase db = MainApp.appInfo.getDbHelper();
        int updcount = 0;
        try {
//            updcount = db.updatesHouseholdColumn(TableContracts.HouseholdTable.COLUMN_SA, households.sAtoString());
            Households updatedHousehold = households;
            updatedHousehold.setSA(households.sAtoString());
            updcount = db.householdsDao().updateHousehold(updatedHousehold);
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
    }*/

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2] + "/" + oldDateParts[1] + "/" + oldDateParts[0];
        return newDate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}