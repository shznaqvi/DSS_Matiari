package edu.aku.hassannaqvi.dss_matiari.ui;

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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityEndingBinding;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.Section1Activity;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.form;


public class EndingActivity extends AppCompatActivity {

    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setForm(form);
        setSupportActionBar(bi.toolbar);
        setSupportActionBar(bi.toolbar);
        //setTitle(R.string.section1_mainheading);

        db = MainApp.appInfo.dbHelper;
        boolean check = getIntent().getBooleanExtra("complete", false);
        //sectionMainCheck = getIntent().getIntExtra("status", 0);


        bi.istatusa.setEnabled(check && !form.getiStatus().equals("9")); // form is complete and  patient not on hold
        bi.istatusb.setEnabled(!check);
        bi.istatusc.setEnabled(check && form.getiStatus().equals("9")); // form is complete and  patient not on hold


    }

    private void saveDraft() {
        form.setiStatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "9"
                : "-1");
        // form.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void BtnEnd(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {

            cleanupProcess();
            finish();
            Intent i = new Intent(this, Section1Activity.class);
            startActivity(i);
            Toast.makeText(this, "Entry Complete", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error in updating Database.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanupProcess() {
        form = null;
    }

    private boolean UpdateDB() {
        int updcount = db.updatesFormColumn(TableContracts.FormsTable.COLUMN_ISTATUS, form.getiStatus());
        return updcount > 0;
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }

}
