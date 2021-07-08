package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.MainActivity;
import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.dss_matiari.models.Form;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.form;

public class SectionAActivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionABinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);

        setTitle(R.string.sectionA_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (updateDB()) {
            finish();
            startActivity(new Intent(this, SectionBActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(TableContracts.FormsTable.COLUMN_S2, form.s2toString());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDraft() {

        MainApp.form = new Form();

        form.setUserName(MainApp.user.getUserName());
        form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        form.setDeviceId(MainApp.deviceid);
        form.setAppver(MainApp.versionName + "." + MainApp.versionCode);

        form.setS1(form.s1toString());

        form.setRa01(bi.ra01.getText().toString());

        form.setRa02(bi.ra02.getText().toString());

        form.setRa04(bi.ra04.getText().toString());

        form.setRa04(bi.ra03.getText().toString());

        form.setRa05(bi.ra05.getText().toString());

        form.setRa06(bi.ra07.getText().toString());

        form.setRa07(bi.ra06.getText().toString());

        form.setRa08(bi.ra08.getText().toString());

        form.setRa09(bi.ra09.getText().toString());

        form.setRa10(bi.ra10.getText().toString());

        form.setRa11(bi.ra11a.isChecked() ? "01"
                : bi.ra11b.isChecked() ? "02"
                : bi.ra11c.isChecked() ? "03"
                : bi.ra11d.isChecked() ? "96"
                : "-1");

        form.setRa11x(bi.ra11x.getText().toString());
        form.setRa12(bi.ra1201.isChecked() ? "01"
                : bi.ra1202.isChecked() ? "02"
                : bi.ra1203.isChecked() ? "03"
                : bi.ra1296.isChecked() ? "96"
                : "-1");

        form.setRa1296x(bi.ra1296x.getText().toString());
        form.setRa13(bi.ra13a.isChecked() ? "01"
                : bi.ra13b.isChecked() ? "02"
                : bi.ra13c.isChecked() ? "03"
                : bi.ra13d.isChecked() ? "96"
                : "-1");

        form.setRa13x(bi.ra13x.getText().toString());
        form.setRa14(bi.ra14.getText().toString());

        form.setRa15(bi.ra15.getText().toString());

        form.setRa16(bi.ra16.getText().toString());

        form.setRa17_a(bi.ra17A.getText().toString());

        form.setRa17_b(bi.ra17B.getText().toString());

        form.setRa17_c(bi.ra17B.getText().toString());

        form.setRa17_d(bi.ra17D.getText().toString());

        form.setRa18(bi.ra18.getText().toString());

        form.setS1(form.s1toString());

    }

    public void btnEnd(View view) {
        //saveDraft();
        /// if ((insertNewRecord())) {
        Toast.makeText(this, "Patient information not recorded.", Toast.LENGTH_SHORT).show();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        //   i.putExtra("complete",false);
        startActivity(i);
        //    }

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}