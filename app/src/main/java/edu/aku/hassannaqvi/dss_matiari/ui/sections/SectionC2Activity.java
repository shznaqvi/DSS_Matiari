package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpHouseholds;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.dss_matiari.ui.WRAEndingActivity;

public class SectionC2Activity extends AppCompatActivity {

    private static final String TAG = "SectionC1Activity";
    ActivitySectionCBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        db = MainApp.appInfo.dbHelper;


        bi.fldGrp01.setVisibility(View.GONE);
        bi.fldGrp02.setVisibility(View.VISIBLE);    // Current Pregnancy Status

        bi.setFollowups(MainApp.followups);


        setImmersive(true);

        bi.btnContinue.setText(MainApp.followups.getUid().equals("") ? "Save" : "Update");

        // To set min max range of date fields
        setDateRanges();


    }

    private void setDateRanges() {
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(fpHouseholds.getRa01()));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

          /*  // Set MinDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -50);
            String minDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +50); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minDob);

            // Set maxDob date to 50 years back from DOV
            cal.add(Calendar.YEAR, -18);
            String maxDob = sdf.format(cal.getTime());
            cal.add(Calendar.YEAR, +18); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + maxDob);

*/
            // Set MinLMP date to 2 months back from DOV
            cal.add(Calendar.MONTH, -9);
            String minLMP = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, +9); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minLMP);

            // Set MaxLMP same as DOV
            String maxLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + maxLMP);

            // Set MinEDD same as DOV
            String minEDD = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minEDD);

            // Set MaxEDD to 9 months from DOV
            cal.add(Calendar.MONTH, +9);
            String maxEDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -9);
            Log.d(TAG, "onCreate: " + maxLMP);

          /*  // DOB
            bi.rb04.setMaxDate(maxDob);
            bi.rb04.setMinDate(minDob);*/

            // LMP
            bi.rc08.setMaxDate(maxLMP);
            bi.rc08.setMinDate(minLMP);

            // EDD
            bi.rc09.setMaxDate(maxEDD);
            bi.rc09.setMinDate(minEDD);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (updateDB()) {
            setResult(RESULT_OK);
            startActivity(new Intent(this, WRAEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
            finish();
            //  startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_SC, followups.sCtoString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "updateDB (JSONException): " + e.getMessage());
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
        setResult(Activity.RESULT_CANCELED);
        finish();
        if (updateDB()) {

            Toast.makeText(this, "Woman information not recorded.", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(this, WRAEndingActivity.class);
            i.putExtra("complete", false);
            startActivity(i);

        }
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
       /*
       if (!compareTwoDate(bi.rb08, 2,
                "LMP should be within 2 months back from DOV")) return false;
        return compareTwoDate(bi.rb09, 9,
                "EDD should be within 9 months back from DOV");
                */
    }


}