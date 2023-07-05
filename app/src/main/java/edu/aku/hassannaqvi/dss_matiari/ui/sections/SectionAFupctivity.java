package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionAFupBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPMwraActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.MwraActivity;

public class SectionAFupctivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionAFupBinding bi;
    private DssRoomDatabase db;
    private boolean updateFMClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a_fup);
        bi.setCallback(this);

        String date = toBlackVisionDate("2023-01-01");
        bi.ra01.setMinDate(date);

        if (households.getUid().equals(""))
        {
            households.populateMeta();
            bi.btnContinue.setVisibility(View.VISIBLE);
        }else{
            if(households.getRegRound().equals("")) {
                households.populateMeta(MainApp.selectedFpHousehold);
                bi.btnContinue.setVisibility(View.GONE);
                bi.btnUpdate.setVisibility(View.VISIBLE);
                households.updateFMData(MainApp.selectHHsHousehold);
            }
        }

        bi.setHousehold(households);

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;

        // Update text for btnContinue
        bi.btnContinue.setText(households.getUid().equals("") ? "Save" : "Update");

        bi.lockedTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFMClicked = true;
                bi.lockedLayout.setVisibility(View.GONE);
                bi.btnUpdate.setText("Update");

                setOnTouchListener(bi.ra17A1);
                setOnTouchListener(bi.ra17B1);
                setOnTouchListener(bi.ra17C1);
                setOnTouchListener(bi.ra17D1);
                setOnTouchListener(bi.ra17A2);
                setOnTouchListener(bi.ra17B2);
                setOnTouchListener(bi.ra17C2);
                setOnTouchListener(bi.ra17D2);
                setOnTouchListener(bi.ra17A3);
                setOnTouchListener(bi.ra17B3);
                setOnTouchListener(bi.ra17C3);
                setOnTouchListener(bi.ra17D3);


            }
        });


    }

    public void btnUpdateHH(View view){

        if(updateFMClicked) {
            if (!formValidation()) return;
        }
        if (!insertNewRecord()) return;

        if (updateDB()) {

            setResult(RESULT_OK);
            Intent intent = new Intent(this, FPMwraActivity.class);
            ((Activity) this).startActivityForResult(intent, 2);

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }

    }


    public void btnContinue(View view) {

        if (view.getId() == bi.btnLocked.getId()) {
            //bi.ra12.setTag("-1");
            //bi.ra13.setTag("-1");
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
            if (households.getRa15().equals("1")) {
                finish();
                startActivity(new Intent(this, FPMwraActivity.class)
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
    }


    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2] + "/" + oldDateParts[1] + "/" + oldDateParts[0];
        return newDate;
    }

    public void setOnTouchListener(TextView editText){
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        editText.setText(String.valueOf(Integer.parseInt(editText.getText().toString()) + 1));

                        return true;

                    }

                    if(event.getRawX() >= (editText.getLeft() - editText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())){
                        if(!editText.getText().toString().equals("0")) {
                            editText.setText(String.valueOf(Integer.parseInt(editText.getText().toString()) - 1));
                        }else{
                            editText.setText(editText.getText().toString());
                        }

                        return true;

                    }

                }


                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }


}