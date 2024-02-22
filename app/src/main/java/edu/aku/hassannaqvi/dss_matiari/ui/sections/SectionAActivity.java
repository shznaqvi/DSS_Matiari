package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.MwraActivity;

public class SectionAActivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionABinding bi;
    private DssRoomDatabase db;
    private Households.SA sA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;

        sA = Households.SA.getData();
        sA = sA == null ? new Households.SA() : sA;
        bi.setSA(sA);

        initUI();

    }

    public void initUI() {

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        String date = DateUtils.changeDateFormat("2023-01-01");
        bi.ra01.setMinDate(date);

        // Update text for btnContinue
        bi.btnContinue.setText(households.getUid().equals("") ? "Save" : "Update");

        // Add same as previous check box for Mohalla
        if (MainApp.previousAddress.trim().equals("") || !households.getSA().getRa08().equals(""))
            bi.rb08check.setVisibility(View.GONE);


        bi.ra15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ra1501.isChecked()) {
                    bi.ra17C2.setMinvalue(1);
                    bi.ra17C2.setMaxvalue(20);
                } else if (bi.ra1502.isChecked()) {
                    bi.ra17C2.setMinvalue(0);
                    bi.ra17C2.setMaxvalue(0);
                }
            }

        });

        bi.rb08check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.ra08.setText(MainApp.previousAddress);
                } else {
                    bi.ra08.setText("");

                }
            }
        });

    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;

        // New form
        // If 'Edit form' option is selected
        // Check data in db
        Households.saveMainData(households.getHdssId(), sA);
        Households form = db.householdsDao().getHouseholdByHDSSIDASC(households.getHdssId());
        if (form != null) {
            // wraId found
            households = form;
            if (sA.getRa15().equals("1")) {
                finish();
                Households.SA.saveData(sA);
                startActivity(new Intent(this, MwraActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT));
            } else {
                finish();
                Households.SA.saveData(sA);
                startActivity(new Intent(this, EndingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        .putExtra("noWRA", true));
            }
        }

    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}