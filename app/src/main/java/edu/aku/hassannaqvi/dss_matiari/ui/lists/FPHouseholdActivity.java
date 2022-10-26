package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.hdssid;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.position;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFpHousehold;
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
import edu.aku.hassannaqvi.dss_matiari.adapters.FPHouseholdAdapter;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityFphouseholdBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.MainActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class FPHouseholdActivity extends AppCompatActivity {

    private static final String TAG = "FPHouseholdActivity";
    ActivityFphouseholdBinding bi;
    DatabaseHelper db;
    private FPHouseholdAdapter hhAdapter;
    public ActivityResultLauncher<Intent> MemberInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        hhAdapter.notifyItemChanged(position);

                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(FPHouseholdActivity.this, "No household added.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_fphousehold);
        bi.setCallback(this);

        db = MainApp.appInfo.dbHelper;
        MainApp.followUpsScheHHList = new ArrayList<>();


        Log.d(TAG, "onCreate: followUpsScheHHList " + MainApp.followUpsScheHHList.size());


        //MainApp.followUpsScheHHList = db.getFollowUpsScheHHBYVillage(selectedUC, selectedVillage, "");
        MainApp.followUpsScheHHList = DssRoomDatabase.getDbInstance().FollowUpsScheDao().getFollowUpsScheHHBYVillage(selectedUC, selectedVillage, "");

        bi.villageCode.setText("List of " + selectedUC + "-" + selectedVillage);


        hhAdapter = new FPHouseholdAdapter(this, MainApp.followUpsScheHHList);
        bi.rvHouseholds.setAdapter(hhAdapter);
        bi.rvHouseholds.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Add new Household
                addHousehold();

            }
        });


    }


    public void filterForms(View view) {
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();

        //MainApp.followUpsScheHHList = db.getFollowUpsScheHHBYVillage(selectedUC, selectedVillage, bi.hhead.getText().toString());
        MainApp.followUpsScheHHList = DssRoomDatabase.getDbInstance().FollowUpsScheDao().getFollowUpsScheHHBYVillage(selectedUC, selectedVillage, bi.hhead.getText().toString());
        hhAdapter = new FPHouseholdAdapter(this, MainApp.followUpsScheHHList);
        hhAdapter.notifyDataSetChanged();
        bi.rvHouseholds.setAdapter(hhAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();

    }

    private void checkCompleteFm() {
        int compCount = MainApp.followUpsScheHHList.size();

        MainApp.householdCountComplete = compCount;
        bi.btnContinue.setVisibility(MainApp.householdCount > 0 ? View.VISIBLE : View.GONE);

    }

    public void addHousehold() {
        MainApp.households = new Households();

        //int maxHH = db.getMaxHouseholdNo(selectedUC, selectedVillage);      // From Households table on device
        //int maxHHNo = db.getMaxHHNoByVillage(selectedUC, selectedVillage);  // From Max Household numbers fetched from server
        int maxHH = DssRoomDatabase.getDbInstance().householdsDao().getMaxHouseholdNo(selectedUC, selectedVillage, "");     // From Households table on device
        int maxHHNo = DssRoomDatabase.getDbInstance().MaxHHNoDao().getMaxHHNoByVillage(selectedUC, selectedVillage);     // From Max Household numbers fetched from server
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

        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

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
                hhAdapter.notifyItemChanged(selectedFpHousehold);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Information for " + MainApp.followUpsScheHHList.get(selectedFpHousehold).getRa14() + " was not saved.1", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}