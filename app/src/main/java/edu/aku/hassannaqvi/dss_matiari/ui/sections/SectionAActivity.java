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
        if ((insertNewRecord())) {
            Toast.makeText(this, "Patient Added.", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(this, Section2Activity.class);
            startActivity(i);
        }
    }

    private void saveDraft() {

        MainApp.form = new Form();

        form.setUserName(MainApp.user.getUserName());
        form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        form.setDeviceId(MainApp.deviceid);
        form.setAppver(MainApp.versionName + "." + MainApp.versionCode);

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