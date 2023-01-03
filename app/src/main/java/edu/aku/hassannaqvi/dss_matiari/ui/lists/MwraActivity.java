package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedMember;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedHhNO;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedVillage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.MwraAdapter;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityMwraBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;


public class MwraActivity extends AppCompatActivity {

    private static final String TAG = "MwraActivity";
    ActivityMwraBinding bi;
    DssRoomDatabase db;
    private MwraAdapter fmAdapter;
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
                                        // MWRA: Married females between 14 to 49
                                        (age >= 14 && age < 50 && !notMarried && isFemale )

                        ) {*/
                        mwraList.add(mwra);

                        mwraCount++;

                        fmAdapter.notifyItemInserted(mwraList.size() - 1);
                        //  Collections.sort(MainApp.fm, new SortByStatus());
                        //fmAdapter.notifyDataSetChanged();

                        //        }

                        checkCompleteFm();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(MwraActivity.this, "No family member added.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mwra);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_mwra);
        bi.setCallback(this);

        db = MainApp.appInfo.dbHelper;
        MainApp.mwraList = new ArrayList<>();
        Log.d(TAG, "onCreate: mwralist " + mwraList.size());
//        try {

            //MainApp.mwraList = db.getAllMWRAByHH(selectedUC, MainApp.households.getVillageCode(), MainApp.households.getStructureNo(), MainApp.households.getHhNo());

            mwraList = db.mwraDao().getAllMWRAByHH(selectedUC, MainApp.households.getVillageCode(), MainApp.households.getStructureNo(), MainApp.households.getHhNo(), "1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "onCreate (JSONException): " + e.getMessage());
//        }



        fmAdapter = new MwraAdapter(this, MainApp.mwraList);
        bi.rvMembers.setAdapter(fmAdapter);
        bi.rvMembers.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!MainApp.households.getIStatus().equals("1")) {
                    //     Toast.makeText(MwraActivity.this, "Opening Mwra Households", Toast.LENGTH_LONG).show();
                    addFemale();
                } else {
                    Toast.makeText(MwraActivity.this, "This households has been locked. You cannot add new members to locked forms", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (!MainApp.households.getRa18().equals("999")) {
            bi.btnContinue.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        mwraCount = Math.round(MainApp.mwraList.size());

        MainApp.mwra = new Mwra();
        mwra.init();
        if (MainApp.mwraList.size() > 0) {
            //MainApp.fm.get(Integer.parseInt(String.valueOf(MainApp.selectedFemale))).setStatus("1");
            fmAdapter.notifyItemChanged(Integer.parseInt(String.valueOf(selectedMember)));
        }


        checkCompleteFm();

        // bi.fab.setClickable(!MainApp.households.getiStatus().equals("1"));
      /* bi.completedmember.setText(mwraList.size()+ " MWRAs added");
        bi.totalmember.setText(MainApp.mwraTotal+ " M completed");*/
    }

    private void checkCompleteFm() {
        //     if (!MainApp.households.getIStatus().equals("1")) {
        int compCount = mwraList.size();

        MainApp.mwraCountComplete = compCount;
        bi.btnContinue.setVisibility(mwraCount > 0 ? View.VISIBLE : View.GONE);
        //   bi.btnContinue.setVisibility(compCount == mwraCount && !households.getiStatus().equals("1")? View.VISIBLE : View.GONE);
     /*   bi.btnContinue.setVisibility(compCount >= mwraCount ? View.VISIBLE : View.GONE);
        bi.btnContinue.setEnabled(bi.btnContinue.getVisibility()==View.VISIBLE);*/

        //  } else {
        //       Toast.makeText(this, "Households has been completed or locked", Toast.LENGTH_LONG).show();
        //   }
    }

    public void addFemale() {


        if (MainApp.mwraList.size() >= Integer.parseInt(MainApp.households.getRa18())) {
            displayAddMoreDialog();
        } else {
            addMoreFemale();

        }


    }

    private void addMoreFemale() {
        MainApp.mwra = new Mwra();
        mwra.init();

        //   finish();

        //int maxMWRA = db.getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        int maxMWRA = db.mwraDao().getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        //int maxFpMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        int maxFpMWRA = db.FollowUpsScheDao().getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        mwraCount = Math.max(maxMWRA, maxFpMWRA);

        Intent intent = new Intent(this, SectionBActivity.class);
        MemberInfoLauncher.launch(intent);
    }

    public void btnContinue(View view) {
        if (MainApp.mwraList.size() < Integer.parseInt(MainApp.households.getRa17_c2())) {
            displayProceedDialog();
        } else {
            proceedSelect();
        }


    }

    public void BtnEnd(View view) {
        if (!MainApp.households.getRa18().equals("999")) {

            Intent i = new Intent(this, EndingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            i.putExtra("complete", false);
            startActivity(i);
            finish();
        } else {
            setResult(RESULT_OK);
            finish();
        }

        //startActivity(new Intent(this, MainActivity.class));
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
                //   mwraList.get(selectedFemale).setExpanded(false);
                checkCompleteFm();
                fmAdapter.notifyItemChanged(selectedMember);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Information for " + mwraList.get(selectedMember).getRb02() + " was not saved.4", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayAddMoreDialog() {
        if(mwraList.size() < Integer.parseInt(MainApp.households.getRa17_c2()))
        {
            addMoreFemale();

        }else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.title_wra_dialog_complete)
                    .setMessage(String.format(getString(R.string.message_wra_dialog_addmore), MainApp.households.getRa18()))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(R.string.ra20_a, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            addMoreFemale();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(R.string.ra20_b, null)
                    .setIcon(R.drawable.ic_warning_24)
                    .show();
        }

    }

    private void displayProceedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_wra_dialog_remain)
                .setMessage(String.format(getString(R.string.message_wra_dialog_proceeed), MainApp.mwraList.size() + "", MainApp.households.getRa17_c2()))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.ra20_a, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        proceedSelect();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.ra20_b, null)
                .setIcon(R.drawable.ic_alert_24)
                .show();

    }

    private void proceedSelect() {
        Intent i = new Intent(this, EndingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT | FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("complete", true);
        finish();
        startActivity(i);
        //startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

}