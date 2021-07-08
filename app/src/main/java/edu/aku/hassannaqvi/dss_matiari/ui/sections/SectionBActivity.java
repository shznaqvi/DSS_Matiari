package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;

public class SectionBActivity extends AppCompatActivity {

    private static final String TAG = "SectionBActivity";
    ActivitySectionBBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);

        setTitle(R.string.sectionB_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (updateDB()) {
            Toast.makeText(this, "Patient Added.", Toast.LENGTH_SHORT).show();
            finish();
           /* Intent i = new Intent(this, Section3Activity.class);
            startActivity(i);*/
        }
    }

    private void saveDraft() {
    }

    private boolean updateDB() {
        // THIS FUNCTION IS NOT SAME AS INSERTNEWRECORD() in FIRST SECTION
        db = MainApp.appInfo.dbHelper;
        long updCount = db.updatesFormColumn(TableContracts.FormsTable.COLUMN_S2, MainApp.form.getS2());

        // Chech if Form inserted into the database
        if (updCount != -1) {

            return true;
        } else {

            // Error message in case when new record in not inserted (check logcat for error messages)
            Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void btnEnd(View view) {
        saveDraft();
        if (updateDB()) {
            Toast.makeText(this, "Patient information not recorded.", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(this, EndingActivity.class);
            i.putExtra("complete", false);
            startActivity(i);
        }

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }
}