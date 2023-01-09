package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionDBinding;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionFBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;


public class SectionDActivity extends AppCompatActivity {

    private static final String TAG = "SectionCx2Activity";
    ActivitySectionDBinding bi;
    private DssRoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d);
        db = MainApp.appInfo.dbHelper;

        setDateRanges();


        if(mwra.getUid() != null)
        {
            try {
                mwra.sDHydrate(mwra.getSD());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*if(!mwra.getRb21().equals(""))
        {
            String date = toBlackVisionDate(mwra.getRb21());
            bi.rb08.setMinDate(date);
        }*/



        bi.setFollowups(mwra);

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(mwra.getUid().equals("") ? "Save" : "Update");

    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(mwra.getRb01a()));// all done

            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);


                // Set MinLMP date to 2 months back from DOV
                cal.add(Calendar.MONTH, -9);
                String minLMP = sdf.format(cal.getTime());
                cal.add(Calendar.MONTH, +7); // Calender reset to DOV
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
            bi.rb08.setMaxDate(maxLMP);
            bi.rb08.setMinDate(minLMP);

            // EDD
            bi.rb09.setMaxDate(maxEDD);
            bi.rb09.setMinDate(minEDD);

            // Date of Death from Date of Deliver(RC10)
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(mwra.getRb12()));// all done
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String minDOD = sdf.format(cal.getTime());

            // Single
            /*bi.rc1401.setMinDate(minDOD);
            bi.rc1402.setMinDate(minDOD);
            bi.rc1403.setMinDate(minDOD)*/;


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        if(updateDB()){
            setResult(RESULT_OK);
            //startActivity(new Intent(this, FPEndingActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", MainApp.mwraFlag));
            finish();

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean updateDB() {
        int updcount = 0;
        try {

            //updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_SC, followups.sCtoString())
             Mwra updatedFollowups = mwra;
                        updatedFollowups.setSD(mwra.SDtoString());
                        updcount = db.mwraDao().updateMwra(mwra);



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
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        setDateRanges();
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

    public static String toBlackVisionDate(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2] + "/" + oldDateParts[1] + "/" + oldDateParts[0];
        return newDate;
    }


}