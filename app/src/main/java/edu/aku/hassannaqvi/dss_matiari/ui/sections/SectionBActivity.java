package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.form;

public class SectionBActivity extends AppCompatActivity {

    private static final String TAG = "SectionBActivity";
    ActivitySectionBBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);
        bi.setForm(form);
        Log.d(TAG, "onCreate: 6 " + form.getRb06());
        Log.d(TAG, "onCreate: 7 " + form.getRb07());
        setTitle(R.string.sectionB_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (updateDB()) {
            finish();
            startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDraft() {

/*        form.setRb_uc(bi.rbUc.getText().toString());

        form.setRb_vil(bi.rbVil.getText().toString());

        form.setRb_hno(bi.rbHno.getText().toString());

        form.setRb_sid(bi.rbSid.getText().toString());

        form.setRb_ufn(bi.rbUfn.getText().toString());*/

        form.setRb01(bi.rb01.getText().toString());

        form.setRb02(bi.rb02.getText().toString());

        form.setRb03(bi.rb03.getText().toString());

        form.setRb04(bi.rb04.getText().toString());

        form.setRb05(bi.rb05.getText().toString());

        form.setRb06(bi.rb0602.isChecked() ? "2"
                : bi.rb0603.isChecked() ? "3"
                : "-1");

        form.setRb07(bi.rb0701.isChecked() ? "1"
                : bi.rb0702.isChecked() ? "2"
                : "-1");

        form.setRb08(bi.rb08.getText().toString());

        form.setRb09(bi.rb09.getText().toString());

        form.setS2(form.s2toString());
    }

    private boolean updateDB() {
        int updcount = db.updatesFormColumn(TableContracts.FormsTable.COLUMN_S2, form.s2toString());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
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