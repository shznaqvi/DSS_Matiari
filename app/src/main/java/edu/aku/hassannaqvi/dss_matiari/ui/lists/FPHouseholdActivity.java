package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static java.lang.Integer.max;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.hdssid;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFpHousehold;
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
import edu.aku.hassannaqvi.dss_matiari.ui.MainActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class FPHouseholdActivity extends AppCompatActivity {

    private static final String TAG = "FPHouseholdActivity";
    ActivityFphouseholdBinding bi;
    DatabaseHelper db;
    private FPHouseholdAdapter hhAdapter;
    ActivityResultLauncher<Intent> MemberInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        //Intent data = result.getData();
                        Intent data = result.getData();
                      /*  int age = Integer.parseInt(femalemembers.getHh05y());
                        boolean isFemale = femalemembers.getHh03().equals("2");
                        boolean notMarried = femalemembers.getHh06().equals("2");
                        if (
                            // Adolescent: Male + Female - 10 to 19
                                (age >= 10 && age < 20 && notMarried)
                                        ||
                                        // HOUSEHOLD: Married females between 14 to 49
                                        (age >= 14 && age < 50 && !notMarried && isFemale )

                        ) {*/
                        // MainApp.householdList.add(MainApp.households);

                        // MainApp.householdCount++;

                        // hhAdapter.notifyItemInserted(MainApp.householdList.size() - 1);
                        //  Collections.sort(MainApp.fm, new SortByStatus());
                        //fmAdapter.notifyDataSetChanged();

                        //        }

                        //      checkCompleteFm();
                        Toast.makeText(FPHouseholdActivity.this, "New Household added.", Toast.LENGTH_SHORT).show();

                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(FPHouseholdActivity.this, "No household added.", Toast.LENGTH_SHORT).show();
                        //   hhAdapter.notifyDataSetChanged();

                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_household);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_fphousehold);
        bi.setCallback(this);

        db = MainApp.appInfo.dbHelper;
        MainApp.followUpsScheHHList = new ArrayList<>();

        Log.d(TAG, "onCreate: followUpsScheHHList " + MainApp.followUpsScheHHList.size());


        MainApp.followUpsScheHHList = db.getFollowUpsScheHHBYVillage(selectedVillage, "");


        hhAdapter = new FPHouseholdAdapter(this, MainApp.followUpsScheHHList);
        bi.rvHouseholds.setAdapter(hhAdapter);
        bi.rvHouseholds.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Add new Household
                addHousehold();
              /*  if (!MainApp.households.getiStatus().equals("1")) {
                    //     Toast.makeText(HouseholdActivity.this, "Opening Household Households", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(FPHouseholdActivity.this, "This households has been locked. You cannot add new household to locked forms", Toast.LENGTH_LONG).show();
                }*/
            }
        });


    }


    public void filterForms(View view) {
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        //fc = db.getUnclosedForms(dtFilter.getText().toString());
        MainApp.followUpsScheHHList = db.getFollowUpsScheHHBYVillage(selectedVillage, bi.hhead.getText().toString());
        hhAdapter = new FPHouseholdAdapter(this, MainApp.followUpsScheHHList);
        hhAdapter.notifyDataSetChanged();
        bi.rvHouseholds.setAdapter(hhAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        // MainApp.householdCount = Math.round(MainApp.followUpsScheHHList.size());

        //  MainApp.households = new Households();

      /*  if (MainApp.followUpsScheHHList.size() > 0) {
            //MainApp.fm.get(Integer.parseInt(String.valueOf(MainApp.selectedHousehold))).setStatus("1");
            hhAdapter.notifyItemChanged(Integer.parseInt(String.valueOf(selectedHousehold)));
        }

        checkCompleteFm();
*/
        // bi.fab.setClickable(!MainApp.households.getiStatus().equals("1"));
      /* bi.completedmember.setText(followUpsScheHHList.size()+ " HOUSEHOLDs added");
        bi.totalmember.setText(MainApp.householdTotal+ " M completed");*/
        //MainApp.households.resetHousehold();
        int newHouseholds = db.getHouseholdCountByVillage(selectedVillage);


    }

    private void checkCompleteFm() {
        //     if (!MainApp.households.getIStatus().equals("1")) {
        int compCount = MainApp.followUpsScheHHList.size();

        MainApp.householdCountComplete = compCount;
        bi.btnContinue.setVisibility(MainApp.householdCount > 0 ? View.VISIBLE : View.GONE);
        //   bi.btnContinue.setVisibility(compCount == householdCount && !households.getiStatus().equals("1")? View.VISIBLE : View.GONE);
     /*   bi.btnContinue.setVisibility(compCount >= householdCount ? View.VISIBLE : View.GONE);
        bi.btnContinue.setEnabled(bi.btnContinue.getVisibility()==View.VISIBLE);*/

        //  } else {
        //       Toast.makeText(this, "Households has been completed or locked", Toast.LENGTH_LONG).show();
        //   }
    }

    public void addHousehold() {
        MainApp.households = new Households();
        // Copy common variables from existing Households to new Households
        // MainApp.households = new Households(MainApp.households);

        int maxHH = db.getMaxHHNo(selectedVillage);
        int maxFpHH = db.getMaxHHNoFromFolloupsSche(selectedVillage);
        int maxHHFinal = max(maxHH, maxFpHH);
        // Increment Household Number by 1
        MainApp.households.setRa09(String.valueOf(maxHHFinal + 1));

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
        //startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        /*   } else {
               Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show()
           }*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                //   followUpsScheHHList.get(selectedHousehold).setExpanded(false);
                checkCompleteFm();
                hhAdapter.notifyItemChanged(MainApp.selectedFpHousehold);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Information for " + MainApp.followUpsScheHHList.get(selectedFpHousehold).getRa14() + " was not saved.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hhExists() {

        switch (idType) {
            case 1:
                MainApp.households = new Households();
                try {
                    MainApp.households = db.getHouseholdByHDSSID(hdssid);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ProcessStart (JSONException): " + e.getMessage());
                }
                return MainApp.households != null;

            //TODO: Antro & Samples will be multiple. Different logic will be required
        /*    case 2:
                anthro = new Anthro();
                anthro = db.getAnthroByClusterHHNo(bi.h103.getText().toString(), bi.h103.getText().toString());
                return anthro != null;
            case 2:
                samples = new Samples();
                anthro = db.getSamplesByClusterHHNo(bi.h103.getText().toString(), bi.h103.getText().toString());
                return anthro != null;*/
            default:
                return false;

        }
    }
}