package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followUpsScheMWRAList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraDone;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraStatus;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.outcomeFollowups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFemale;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.RoomDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.FpMwraAdapter;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityFpmwraBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Followups;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.models.OutcomeFollowups;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.FPEndingActivity;
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


                        followUpsScheMWRAList.add(fupsche);

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
        mwraDone = 0;
        //bi.hdssid.setText(fpHouseholds.getHdssId());
        bi.hdssid.setText(households.getHdssId());

        try {

            //followUpsScheMWRAList = db.getAllfollowupsScheByHH(fpHouseholds.getVillageCode(), fpHouseholds.getUcCode(), fpHouseholds.getHhNo());

            followUpsScheMWRAList = DssRoomDatabase.getDbInstance().FollowUpsScheDao().getAllfollowupsScheByHH(households.getVillageCode(), households.getUcCode(), households.getHhNo());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate (JSONException): " + e.getMessage());
        }

        // Updated status in FollowupsSche for existing followups done
        for (int i = 0; i < followUpsScheMWRAList.size(); i++) {

            String fupStatus = "";
            try {
                //fupStatus = db.getFollowupsBySno(followUpsScheMWRAList.get(i).getRb01(), followUpsScheMWRAList.get(i).getFRound()).getSysDate();

                Mwra tempMwra = DssRoomDatabase.getDbInstance().mwraDao().getFollowupsBySno(households.getUid(), followUpsScheMWRAList.get(i).getRb01(), followUpsScheMWRAList.get(i).getFRound());
                fupStatus = tempMwra.getSysDate();
                followUpsScheMWRAList.get(i).setfpDoneDt(fupStatus);
                if (!fupStatus.equals("")) {
                    mwraDone++;

                }
            } catch (JSONException e) {

                Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }

        fmAdapter = new FpMwraAdapter(this, followUpsScheMWRAList);
        bi.rvMembers.setAdapter(fmAdapter);
        bi.rvMembers.setLayoutManager(new LinearLayoutManager(this));

        // mwraCount = followUpsScheMWRAList.size();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!households.getIStatus().equals("1")) {
                    //     Toast.makeText(MwraActivity.this, "Opening Mwra Households", Toast.LENGTH_LONG).show();
                    addMoreFemale();
                } else {
                    Toast.makeText(FPMwraActivity.this, "This households has been locked. You cannot add new members to locked forms", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (mwraDone >= followUpsScheMWRAList.size()) {
            bi.btnContinue.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        //       mwraCount = Math.ROUND(MainApp.followUpsScheMWRAList.size());

        //followups = new Followups();

        mwra = new Mwra();

        outcomeFollowups = new OutcomeFollowups();

        // Created object of Household from FollowsSche info for adding new MWRA
        //MainApp.households = new Households();
        /*MainApp.households.setRa01(fpHouseholds.getSysDate().substring(0, 10));
        MainApp.households.setUid(fpHouseholds.getUid());
        MainApp.households.setUcCode(fpHouseholds.getUcCode());
        MainApp.households.setVillageCode(fpHouseholds.getVillageCode());
        MainApp.households.setHhNo(fpHouseholds.getHhNo());
        MainApp.households.setSysDate(fpHouseholds.getSysDate());
        MainApp.households.setHdssId(fpHouseholds.getHdssId());
        MainApp.households.setVisitNo(fpHouseholds.getVisitNo());
        *///MainApp.households.setRa18(String.valueOf(followUpsScheMWRAList.size()));

        households.setRa01(households.getSysDate().substring(0, 10));
        MainApp.households.setUcCode(households.getUcCode());
        MainApp.households.setVillageCode(households.getVillageCode());
        MainApp.households.setHhNo(households.getHhNo());
        MainApp.households.setSysDate(households.getSysDate());
        MainApp.households.setHdssId(households.getHdssId());
        MainApp.households.setVisitNo(households.getVisitNo());
        households.setRegRound("0");
        households.setFRound(households.getFRound());
        MainApp.households.setRa18("999");

        //int newMwra = db.getMWRACountBYUUID(fpHouseholds.getUid());

        int newMwra = DssRoomDatabase.getDbInstance().mwraDao().getMWRACountBYUUID(households.getUid(), "1");


        // int newMwra = mwraCount - followUpsScheMWRAList.size();
        if (newMwra > 0) {
            mwraCount = mwraCount + newMwra;
            bi.newMwra.setText(newMwra + " new women added to this household");
            bi.newMwra.setVisibility(View.VISIBLE);
            bi.newMwraList.setVisibility(View.VISIBLE);

        }

    }
    private void addMoreFemale() {

        if (mwraDone < followUpsScheMWRAList.size()) {
            Toast.makeText(FPMwraActivity.this, "Please complete followups before adding new women.", Toast.LENGTH_LONG).show();
            return;
        }
        //  startActivity(new Intent(this, MwraActivity.class));
        //int maxMWRA = db.getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        //int maxFpMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);

        int maxMWRA = DssRoomDatabase.getDbInstance().mwraDao().getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        int maxFpMWRA = DssRoomDatabase.getDbInstance().FollowUpsScheDao().getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        mwraCount = Math.max(maxMWRA, maxFpMWRA);

       /* MainApp.households = new Households();
        MainApp.households.setUid(followups.getUid());
        MainApp.households.setSysDate(followups.getSysDate());*/
        MainApp.households.setRa18("999");


        MainApp.mwra = new Mwra();
        //MainApp.mwra.setRb01(String.valueOf(mwraCount + 1));
        //startActivity(new Intent(this, MwraActivity.class));

      /*  Intent intent = new Intent(FPMwraActivity.this, SectionBActivity.class);
        MemberInfoLauncher.launch(intent);
*/


        startActivityForResult(new Intent(this, SectionBActivity.class), 3);
        // finish();

    }

    public void btnContinue(View view) {
        if (households.getUid().equals("")) {
            Toast.makeText(this, "btnContinue(uid)", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();

        } else {
            proceedSelect();
        }


    }

    public void BtnEnd(View view) {


        setResult(RESULT_CANCELED);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                // Followups fp = followupsList.get(selectedFemale);
                if (followUpsScheMWRAList.get(selectedFemale).getfpDoneDt().equals(""))
                    // followupsList.add(followups);
                    mwraDone++;


                //followUpsScheMWRAList.get(selectedFemale).setfpDoneDt(followups.getSysDate());
                followUpsScheMWRAList.get(selectedFemale).setfpDoneDt(mwra.getSysDate());

                fmAdapter.notifyItemChanged(selectedFemale);


                fpMwra.setfpDoneDt(followUpsScheMWRAList.get(selectedFemale).setfpDoneDt(mwra.getSysDate()));
                DssRoomDatabase.getDbInstance().FollowUpsScheDao().updateFollowupsSche(fpMwra);
                //db.updatesFollowUpsScheColumn(TableContracts.TableFollowUpsSche.COLUMN_DONE_DATE, followups.getSysDate());
                if (mwraDone >= followUpsScheMWRAList.size()) {
                    bi.btnContinue.setVisibility(View.VISIBLE);
                }
                //checkCompleteFm();
                //fmAdapter.notifyItemChanged(selectedFemale);
                Toast.makeText(this, "Followup for " + followUpsScheMWRAList.get(selectedFemale).getRb02() + " has been saved.", Toast.LENGTH_SHORT).show();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Followup for " + followUpsScheMWRAList.get(selectedFemale).getRb02() + " was not saved.2", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {

            }
            if (resultCode == Activity.RESULT_CANCELED) {
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
        Toast.makeText(this, "proceedSelect()", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, EndingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            //if(mwraStatus.containsValue(false))

        i.putExtra("complete", mwraStatus.isEmpty());
            startActivity(i);
            finish();

        //startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    public void toggleNewMwraList(View view) {


        startActivity(new Intent(this, MwraActivity.class));
        // bi.newMwraList.setVisibility(View.VISIBLE);
    }
}