package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySection1Binding;
import edu.aku.hassannaqvi.dss_matiari.models.Form;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.form;


public class Section1Activity extends AppCompatActivity {

    private static final String TAG = "Section1Activity";
    ActivitySection1Binding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section1);
        bi.setCallback(this);
        // MainApp.form = new Form(); Set on OPEN Form Button in MainActivity
        bi.setForm(MainApp.form);
        setSupportActionBar(bi.toolbar);
        setTitle(R.string.section1_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;


        // Enables Always-on
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if ((insertNewRecord())) {
            Toast.makeText(this, "Patient Added.", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(this, Section2Activity.class);
            startActivity(i);
        }
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
        Log.d(TAG, "formValidation: " + db.getFormByAssessNo(bi.tsf101.getText().toString(), " (1,9) "));
        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) {
            return false;
        } else if (db.getFormByAssessNo(bi.tsf101.getText().toString(), " (1,9) ") != null) {

            Validator.emptyCustomTextBox(this, bi.tsf101, "Assessment No. already exists!");
            return false;
        } else return true;

    }


    private void saveDraft() {

        // IMPORTANT: MainApp.form = new Form(); is only initialised at first section
        MainApp.form = new Form();

        form.setUserName(MainApp.user.getUserName());
        form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        form.setDeviceId(MainApp.deviceid);
        form.setAppver(MainApp.versionName + "." + MainApp.versionCode);

        /*form.setTsf101(bi.tsf101.getText().toString());
        form.setTsf102(bi.tsf102.getText().toString());
        form.setTsf103(bi.tsf103.getText().toString());
        form.setTsf104(bi.tsf104.getText().toString());
        form.setTsf105(bi.tsf10501.isChecked() ? "1"
                : bi.tsf10502.isChecked() ? "2"
                : bi.tsf10503.isChecked() ? "3"
                : "-1");
        form.setTsf10596x(bi.tsf10596x.getText().toString());

        form.setTsf106(bi.tsf106.getText().toString());
        form.setTsf107(bi.tsf107.getText().toString());
        form.setTsf108(bi.tsf108.getText().toString());
        form.setTsf109(bi.tsf10901.isChecked() ? "1"
                : bi.tsf10902.isChecked() ? "2"
                : "-1");
        form.setTsf110(bi.tsf110.getText().toString());
        form.setTsf111(bi.tsf11101.isChecked() ? "1"
                : bi.tsf11102.isChecked() ? "2"
                : bi.tsf11103.isChecked() ? "3"
                : "-1");
        form.setTsf112(bi.tsf112.getText().toString());*/

        // Don't forget to popuate Section variable with JSON String...it will be used in UpdateDB()
        form.setS1(form.s1toString());
    }

    private boolean insertNewRecord() {
        // THIS FUNCTION IS NOT SAME AS UPDATEDB() IN OTHER SECTIONS

        long rowid = db.addForm(MainApp.form);

        // Chech if Form inserted into the database
        if (rowid > 0) {

            // UPDATE ID and UID field in form object.
            form.setId(String.valueOf(rowid));
            form.setUid(form.getDeviceId() + form.getId());

            // UPDATE newly created UID in Database.
            long count = db.updatesFormColumn(TableContracts.FormsTable.COLUMN_UID, form.getUid());
            if (count > 0) return true;
            else {
                // Error message in case when UID fails to update the row (check logcat for error messages)
                Toast.makeText(this, "SORRY! Failed to update UID", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {

            // Error message in case when new record in not inserted (check logcat for error messages)
            Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

}