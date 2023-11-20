package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.allMwraRefusedOrMigrated;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fmComplete;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followUpsScheMWRAList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraDone;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraStatus;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedFpHousehold;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedHhNO;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedMember;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.FpMwraAdapter;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityFpmwraBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;


public class FPMwraActivity extends AppCompatActivity {

    private static final String TAG = "FPMwraActivity";
    ActivityFpmwraBinding bi;
    DssRoomDatabase db;
    private FpMwraAdapter fmAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_fpmwra);
        bi.setCallback(this);


        db = MainApp.appInfo.dbHelper;
        mwraDone = 0;
        MainApp.prevChildCount = 0;
        MainApp.pregcount = 0;
        bi.hdssid.setText(households.getHdssId());

        try {

            //followUpsScheMWRAList = db.getAllfollowupsScheByHH(fpHouseholds.getVillageCode(), fpHouseholds.getUcCode(), fpHouseholds.getHhNo());
            followUpsScheMWRAList = db.FollowUpsScheDao().getAllfollowupsScheByHH(households.getVillageCode(), households.getUcCode(), households.getHhNo());

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

                if (followUpsScheMWRAList.get(i).getRb01() != null) {

                    Mwra tempMwra = db.mwraDao().getFollowupsBySno(households.getUid(), followUpsScheMWRAList.get(i).getRb01(), followUpsScheMWRAList.get(i).getFRound());
                    fupStatus = tempMwra.getSysDate();
                    followUpsScheMWRAList.get(i).setfpDoneDt(fupStatus);
                    if (!fupStatus.equals("")) {
                        mwraDone++;

                    }
                }
            } catch (JSONException e) {

                Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }

        fmAdapter = new FpMwraAdapter(this, followUpsScheMWRAList);
        bi.rvMembers.setAdapter(fmAdapter);
        bi.rvMembers.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!households.getIStatus().equals("1")) {
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
        MainApp.lockScreen(this);
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();

        mwra = new Mwra();
        MainApp.prevChildCount = 0;

        if (mwraDone >= followUpsScheMWRAList.size()) {
            bi.btnContinue.setVisibility(View.VISIBLE);
        }

        households.getSA().setRa01(households.getSysDate().substring(0, 10));
        MainApp.households.setUcCode(households.getUcCode());
        MainApp.households.setVillageCode(households.getVillageCode());
        MainApp.households.setHhNo(households.getHhNo());
        MainApp.households.setSysDate(households.getSysDate());
        MainApp.households.setHdssId(households.getHdssId());
        MainApp.households.setVisitNo(households.getVisitNo());
        households.setRegRound("");
        //MainApp.households.setRa18(households.getRa18());

        //int newMwra = db.getMWRACountBYUUID(fpHouseholds.getUid());

        int newMwra = db.mwraDao().getMWRACountBYUUID(households.getUid(), "1");
        int maxMWRA = db.mwraDao().getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        int maxFpMWRA = db.FollowUpsScheDao().getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        mwraCount = Math.max(maxMWRA, maxFpMWRA);

        if (newMwra > 0) {
            mwraCount = mwraCount + newMwra;
            bi.newMwra.setText(newMwra + " new women added to this household");
            bi.newMwra.setVisibility(View.VISIBLE);
            bi.newMwraList.setVisibility(View.VISIBLE);

        }

        households.getSA().setRa18(String.valueOf(mwraCount));

    }

    private void addMoreFemale() {

        if (mwraDone < followUpsScheMWRAList.size()) {
            Toast.makeText(FPMwraActivity.this, "Please complete followups before adding new women.", Toast.LENGTH_LONG).show();
            return;
        }
        //int maxMWRA = db.getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        //int maxFpMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);

        int maxMWRA = db.mwraDao().getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        int maxFpMWRA = db.FollowUpsScheDao().getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        mwraCount = Math.max(maxMWRA, maxFpMWRA);
        MainApp.households.getSA().setRa18(String.valueOf(mwraCount));


        MainApp.mwra = new Mwra();

        startActivityForResult(new Intent(this, SectionBActivity.class), 3);


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
        startActivity(new Intent(this, FPHouseholdActivity.class));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                if (followUpsScheMWRAList.get(selectedMember).getfpDoneDt().equals(""))
                    mwraDone++;

                followUpsScheMWRAList.get(selectedMember).setfpDoneDt(mwra.getSysDate());
                fmAdapter.notifyItemChanged(selectedMember);


                fpMwra.setfpDoneDt(followUpsScheMWRAList.get(selectedMember).setfpDoneDt(mwra.getSysDate()));
                db.FollowUpsScheDao().updateFollowupsSche(fpMwra);
                //db.updatesFollowUpsScheColumn(TableContracts.TableFollowUpsSche.COLUMN_DONE_DATE, followups.getSysDate());
                if (mwraDone >= followUpsScheMWRAList.size()) {
                    bi.btnContinue.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "Followup for " + followUpsScheMWRAList.get(selectedMember).getRb02() + " has been saved.", Toast.LENGTH_SHORT).show();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Followup for " + followUpsScheMWRAList.get(selectedMember).getRb02() + " was not saved.2", Toast.LENGTH_SHORT).show();
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
                .setMessage(String.format(getString(R.string.message_wra_dialog_addmore), MainApp.households.getSA().getRa18()))

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
                .setMessage(String.format(getString(R.string.message_wra_dialog_proceeed), MainApp.mwraList.size() + "", MainApp.households.getSA().getRa18()))

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
        Boolean flag = false;
        Boolean refusedOrMigrated = false;
        if ((mwraStatus.size() == 0 || mwraStatus.isEmpty()) && (allMwraRefusedOrMigrated.size() == 0 || allMwraRefusedOrMigrated.isEmpty())) {
            flag = true;
            refusedOrMigrated = false;
        } else if (mwraStatus.size() > 0) {
            mwraStatus.size();
            String houseId = MainApp.followUpsScheHHList.get(selectedFpHousehold).getHdssid();
            for (String[] arr : mwraStatus.keySet()) {
                if (arr[1].equals(houseId)) {
                    flag = false;
                    break;
                } else
                    flag = true;
            }
        } else if(allMwraRefusedOrMigrated.size() == mwraDone) {
            refusedOrMigrated = true;
        }


        if(!refusedOrMigrated && mwraStatus.size() > 0)
        {
            i.putExtra("complete", flag);
        }else if(refusedOrMigrated){
            i.putExtra("refused", refusedOrMigrated);
            i.putExtra("complete", false);
        }else if(!refusedOrMigrated && mwraStatus.size() == 0)
        {
            i.putExtra("complete", true);
        }

        finish();
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    public void toggleNewMwraList(View view) {
        startActivity(new Intent(this, MwraActivity.class));
    }
}