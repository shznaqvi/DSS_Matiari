package edu.aku.hassannaqvi.dss_matiari.ui.sections;


import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
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


        if (mwra.getUid() != null || mwra.getUid().equals("")) {
            try {
                mwra.sDHydrate(mwra.getSD());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        bi.setFollowups(mwra);

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        bi.btnContinue.setText(mwra.getUid().equals("") ? "Save" : "Update");


        bi.rb08.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                setDateRanges();
            }
        });
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
            cal.add(Calendar.MONTH, +8); // Calender reset to DOV
            Log.d(TAG, "onCreate: " + minLMP);

            // Set MaxLMP same as DOV
            String maxLMP = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + maxLMP);

            // Calender set to DOV
            cal.add(Calendar.MONTH, +1);

            // Set MinEDD same as DOV
            String minEDD = sdf.format(cal.getTime());
            Log.d(TAG, "onCreate: " + minEDD);

            // Set MaxEDD to 9 months from DOV
            cal.add(Calendar.MONTH, +9);
            String maxEDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -9);
            Log.d(TAG, "onCreate: " + maxLMP);

            // LMP
            bi.rb08.setMaxDate(maxLMP);
            bi.rb08.setMinDate(minLMP);

            // EDD
            bi.rb09.setMaxDate(maxEDD);
            bi.rb09.setMinDate(minEDD);

            Calendar lmpCal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            lmpCal.setTime(simpleDateFormat.parse(mwra.getRb08()));
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String dov = sdf.format(cal.getTime());
            String lmp = sdf.format(lmpCal.getTime());

            bi.rb25.setMinDate(lmp);
            bi.rb25.setMaxDate(dov);



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;

        if (mwra.getRb07().equals("1")) {
            mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
        }else{
            mwra.setPregnum(String.valueOf(MainApp.pregcount));
        }
        if (updateDB()) {
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
            updatedFollowups.setSC(mwra.sCtoString());
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


    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}