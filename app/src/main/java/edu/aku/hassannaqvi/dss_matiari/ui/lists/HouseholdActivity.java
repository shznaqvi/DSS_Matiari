package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.hdssid;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedHousehold;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedVillage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.HouseholdAdapter;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityHouseholdBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class HouseholdActivity extends AppCompatActivity {

    private static final String TAG = "HouseholdActivity";
    ActivityHouseholdBinding bi;
    DatabaseHelper db;
    private HouseholdAdapter hhAdapter;
    ActivityResultLauncher<Intent> MemberInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        //Intent data = result.getData();
                        Intent data = result.getData();
                        MainApp.householdList.add(MainApp.households);

                        MainApp.householdCount++;

                        hhAdapter.notifyItemInserted(MainApp.householdList.size() - 1);
                        checkCompleteFm();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(HouseholdActivity.this, "No household added.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_household);
        bi.setCallback(this);

        db = MainApp.appInfo.dbHelper;
        MainApp.householdList = new ArrayList<>();

        Log.d(TAG, "onCreate: householdlist " + MainApp.householdList.size());
        try {

//            MainApp.householdList = db.getHouseholdBYVillage(selectedUC, MainApp.selectedVillage);
            MainApp.householdList = DssRoomDatabase.getDbInstance().householdsDao().getHouseholdBYVillage(selectedUC, selectedVillage);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate (JSONException): " + e.getMessage());
        }

        hhAdapter = new HouseholdAdapter(this, MainApp.householdList);
        bi.rvHouseholds.setAdapter(hhAdapter);
        bi.rvHouseholds.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHousehold();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        MainApp.householdCount = Math.round(MainApp.householdList.size());

        MainApp.households = new Households();

        if (MainApp.householdList.size() > 0) {
            hhAdapter.notifyItemChanged(Integer.parseInt(String.valueOf(selectedHousehold)));
        }

        checkCompleteFm();
    }

    private void checkCompleteFm() {
        int compCount = MainApp.householdList.size();

        MainApp.householdCountComplete = compCount;
        bi.btnContinue.setVisibility(MainApp.householdCount > 0 ? View.VISIBLE : View.GONE);

    }

    public void addHousehold() {
        MainApp.households = new Households();


        // Increment Household Number by 1 (New Method)
        //int maxHH = db.getMaxHouseholdNo(selectedUC, selectedVillage);      // From Households table on device
        //int maxHHNo = db.getMaxHHNoByVillage(selectedUC, selectedVillage);  // From Max Household numbers fetched from server
        int maxHH = DssRoomDatabase.getDbInstance().householdsDao().getMaxHouseholdNo(selectedUC, selectedVillage);
        int maxHHNo = DssRoomDatabase.getDbInstance().householdsDao().getMaxHHNoByVillage(selectedUC, selectedVillage);
        int maxHHFinal = Math.max(maxHH, maxHHNo);
        MainApp.households.setUcCode(selectedUC);
        MainApp.households.setVillageCode(selectedVillage);
        MainApp.households.setRa09(String.valueOf(maxHHFinal + 1));
        MainApp.selectedHhNO = String.valueOf(maxHHFinal + 1);

        // Launch activity for results.
        Intent intent = new Intent(this, SectionAActivity.class);
        MemberInfoLauncher.launch(intent);
    }

    public void btnContinue(View view) {
        finish();

    }

    public void BtnEnd(View view) {

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                checkCompleteFm();
                hhAdapter.notifyItemChanged(selectedHousehold);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Information for " + MainApp.householdList.get(selectedHousehold).getRa14() + " was not saved.3", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hhExists() {

        switch (idType) {
            case 1:
                MainApp.households = new Households();
                try {
                    //MainApp.households = db.getHouseholdByHDSSID(hdssid);
                    MainApp.households = DssRoomDatabase.getDbInstance().householdsDao().getHouseholdByHDSSIDASC(hdssid);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ProcessStart (JSONException): " + e.getMessage());
                }
                return MainApp.households != null;

            default:
                return false;

        }
    }
}