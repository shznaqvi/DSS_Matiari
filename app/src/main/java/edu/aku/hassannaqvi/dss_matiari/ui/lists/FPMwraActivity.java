package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMWRAList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFemale;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.FpMwraAdapter;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityFpmwraBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Followups;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.MWRA;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;


public class FPMwraActivity extends AppCompatActivity {

    private static final String TAG = "FPMwraActivity";
    ActivityFpmwraBinding bi;
    DatabaseHelper db;
    private FpMwraAdapter fmAdapter;
   /* ActivityResultLauncher<Intent> MemberInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        //Intent data = result.getData();
                        Intent data = result.getData();
                        int age = Integer.parseInt(femalemembers.getHh05y());
                        boolean isFemale = femalemembers.getHh03().equals("2");
                        boolean notMarried = femalemembers.getHh06().equals("2");
                        if (
                            // Adolescent: Male + Female - 10 to 19
                                (age >= 10 && age < 20 && notMarried)
                                        ||
                                        // MWRA: Married females between 14 to 49
                                        (age >= 14 && age < 50 && !notMarried && isFemale )

                        ) {
                        FollowUpsSche fupsche = new FollowUpsSche();
                        fupsche.setHdssid(MainApp.mwra.getHdssId());
                        fupsche.setHdssid(MainApp.mwra.getHdssId());
                        fupsche.setHdssid(MainApp.mwra.getHdssId());
                        fupsche.setHdssid(MainApp.mwra.getHdssId());


                        fpMWRAList.add(fupsche);

                        mwraCount++;

                        fmAdapter.notifyItemInserted(mwraList.size() - 1);
                        //  Collections.sort(MainApp.fm, new SortByStatus());
                        //fmAdapter.notifyDataSetChanged();

                        //        }

                        checkCompleteFm();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(FPMwraActivity.this, "No family member added.", Toast.LENGTH_SHORT).show();
                    }

                }
            });*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mwra);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_fpmwra);
        bi.setCallback(this);

        db = MainApp.appInfo.dbHelper;
        // MainApp.fpMWRAList = new ArrayList<>();
     /*   Log.d(TAG, "onCreate: mwralist " + MainApp.fpMWRAList.size());
        Log.d(TAG, "onCreate: mwralist " + MainApp.fpHouseholds.getVillageCode());*/
        try {
            fpMWRAList = db.getAllfollowupsScheByHH(MainApp.fpHouseholds.getVillageCode(), MainApp.fpHouseholds.getUcCode(), MainApp.fpHouseholds.getHhNo());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate (JSONException): " + e.getMessage());
        }

        fmAdapter = new FpMwraAdapter(this, fpMWRAList);
        bi.rvMembers.setAdapter(fmAdapter);
        bi.rvMembers.setLayoutManager(new LinearLayoutManager(this));

        mwraCount = fpMWRAList.size();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!MainApp.fpHouseholds.getiStatus().equals("1")) {
                    //     Toast.makeText(MwraActivity.this, "Opening Mwra Households", Toast.LENGTH_LONG).show();
                    addMoreFemale();
                } else {
                    Toast.makeText(FPMwraActivity.this, "This households has been locked. You cannot add new members to locked forms", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        //       mwraCount = Math.round(MainApp.fpMWRAList.size());

        followups = new Followups();
    /*    if (MainApp.fpMWRAList.size() > 0) {
            //MainApp.fm.get(Integer.parseInt(String.valueOf(MainApp.selectedFemale))).setStatus("1");
            fmAdapter.notifyItemChanged(Integer.parseInt(String.valueOf(selectedFemale)));
        }*/


        //     checkCompleteFm();

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
        MainApp.households = new Households();
        MainApp.households.setRa01(MainApp.fpHouseholds.getSysDate().substring(0, 10));
        MainApp.households.setUid(MainApp.fpHouseholds.getUid());
        MainApp.households.setUcCode(MainApp.fpHouseholds.getUcCode());
        MainApp.households.setVillageCode(MainApp.fpHouseholds.getVillageCode());
        MainApp.households.setHhNo(MainApp.fpHouseholds.getHhNo());
        MainApp.households.setSysDate(MainApp.fpHouseholds.getSysDate());
        MainApp.households.setHdssId(MainApp.fpHouseholds.getHdssId());
        MainApp.households.setRa18(String.valueOf(fpMWRAList.size()));
        //  startActivity(new Intent(this, MwraActivity.class));

        MainApp.mwra = new MWRA();
        //MainApp.mwra.setRb01(String.valueOf(mwraCount + 1));
        //startActivity(new Intent(this, MwraActivity.class));

      /*  Intent intent = new Intent(FPMwraActivity.this, SectionBActivity.class);
        MemberInfoLauncher.launch(intent);
*/
        startActivityForResult(new Intent(this, SectionBActivity.class), 3);
        finish();

    }

    public void btnContinue(View view) {
        if (MainApp.mwraList.size() < Integer.parseInt(MainApp.households.getRa18())) {
            displayProceedDialog();
        } else {
            proceedSelect();
        }


    }

    public void BtnEnd(View view) {

        Intent i = new Intent(this, EndingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        i.putExtra("complete", false);
        startActivity(i);
        finish();
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

                // TODO: Mark followup done

                fpMWRAList.get(selectedFemale).setfpDoneDt(followups.getSysDate());
                fmAdapter.notifyItemChanged(selectedFemale);
                db.updatesFollowUpsScheColumn(TableContracts.TableFollowUpsSche.COLUMN_DONE_DATE, followups.getSysDate());
                //checkCompleteFm();
                //fmAdapter.notifyItemChanged(selectedFemale);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
//                Toast.makeText(this, "Information for " + mwraList.get(selectedFemale).getRb02() + " was not saved.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {

                mwraCount++;
                int newMwra = mwraCount - fpMWRAList.size();
                bi.newMwra.setText(newMwra + " new women added to this household");
                bi.newMwra.setVisibility(View.VISIBLE);
                //   mwraList.get(selectedFemale).setExpanded(false);

                // TODO: Mark followup done
             /*   checkCompleteFm();
                fmAdapter.notifyItemChanged(selectedFemale);*/
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
//                Toast.makeText(this, "Information for " + mwraList.get(selectedFemale).getRb02() + " was not saved.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void displayAddMoreDialog() {
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

    private void displayProceedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_wra_dialog_remain)
                .setMessage(String.format(getString(R.string.message_wra_dialog_proceeed), MainApp.mwraList.size() + "", MainApp.households.getRa18()))

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
        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        i.putExtra("complete", true);
        startActivity(i);
        finish();
        //startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    public void toggleNewMwraList(View view) {

        startActivity(new Intent(this, MwraActivity.class));
        // bi.newMwraList.setVisibility(View.VISIBLE);
    }
}