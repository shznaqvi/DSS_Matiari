package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.followups;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionDBinding;

public class SectionDActivity extends AppCompatActivity {

    private static final String TAG = "SectionCActivity";
    ActivitySectionDBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d);

        bi.setFollowups(followups);

        setTitle(R.string.marriedwomenregistration_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
        bi.btnContinue.setText(followups.getRc10().equals("") ? "Save" : "Update");

    }


    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (updateDB()) {

            if (followups.getRc08().equals("1")) {
                setResult(RESULT_OK);
                finish();
            } else {

                MainApp.totalOutcomes = Integer.parseInt(followups.getRc08());
                MainApp.outcomeCounter = 0;
                setResult(RESULT_OK);
                startActivity(new Intent(this, SectionEActivity.class).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT).putExtra("complete", true));
                finish();

            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean updateDB() {
        int updcount = 0;
        try {
            updcount = db.updatesFollowUpsColumn(TableContracts.FollowupsTable.COLUMN_SD, followups.sDtoString());
        } catch (JSONException e) {
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void btnEnd(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

}