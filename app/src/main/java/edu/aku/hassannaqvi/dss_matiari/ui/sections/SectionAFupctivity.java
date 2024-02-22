package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionAFupBinding;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPMwraActivity;

public class SectionAFupctivity extends AppCompatActivity {

    private static final String TAG = "SectionAActivity";
    ActivitySectionAFupBinding bi;
    private DssRoomDatabase db;
    private boolean updateFMClicked = false;

    private Households.SA sA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a_fup);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;

        // Init for for the first time
        Households.initMeta();
        sA = new Households.SA();
        bi.setHousehold(sA);

        initUI();

    }

    private void initUI() {

        String date = DateUtils.changeDateFormat("2023-01-01");
        bi.ra01.setMinDate(date);

        setTitle(R.string.demographicinformation_mainheading);
        setImmersive(true);

        if (households.getUid().equals("")) {
            households.populateMeta(MainApp.selectedFpHousehold);
            bi.btnContinue.setVisibility(View.VISIBLE);
        } else {
            if (households.getRegRound().equals("")) {
                households.populateMeta(MainApp.selectedFpHousehold);
                bi.btnContinue.setVisibility(View.GONE);
                bi.btnUpdate.setVisibility(View.VISIBLE);
                sA.updateFMData(MainApp.selectHHsHousehold);
            }
        }


        // Update text for btnContinue
        bi.btnContinue.setText(households.getUid().equals("") ? "Save" : "Update");

        bi.lockedTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFMClicked = true;
                bi.lockedLayout.setVisibility(View.GONE);
                bi.btnUpdate.setText("Update");

                setOnTouchListener(bi.ra17A1);
                setOnTouchListener(bi.ra17B1);
                setOnTouchListener(bi.ra17C1);
                setOnTouchListener(bi.ra17D1);
                setOnTouchListener(bi.ra17A2);
                setOnTouchListener(bi.ra17B2);
                setOnTouchListener(bi.ra17C2);
                setOnTouchListener(bi.ra17D2);
                setOnTouchListener(bi.ra17A3);
                setOnTouchListener(bi.ra17B3);
                setOnTouchListener(bi.ra17C3);
                setOnTouchListener(bi.ra17D3);


            }
        });

    }

    public void btnUpdateHH(View view) throws JSONException {

        if (updateFMClicked) {
            if (!formValidation()) return;

            // New form
            // If 'Edit form' option is selected
            // Check data in db
            Households form = db.householdsDao().getHouseholdByHDSSIDASC(sA.getRa10());
            if (form != null) {
                // wraId found
                households = form;
                setResult(RESULT_OK);
                finish();
                Households.saveMainData(sA.getRa10(), sA);
                Intent intent = new Intent(this, FPMwraActivity.class);
                ((Activity) this).startActivityForResult(intent, 2);

            }
        }

    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;

        // New form
        // If 'Edit form' option is selected
        // Check data in db
        Households form = db.householdsDao().getHouseholdByHDSSIDASC(sA.getRa10());
        if (form != null) {
            // wraId found
            households = form;
            setResult(RESULT_OK);
            finish();
            Households.saveMainData(sA.getRa10(), sA);
            Intent intent = new Intent(this, FPMwraActivity.class);
            ((Activity) this).startActivityForResult(intent, 2);

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
    public void onBackPressed() {
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnTouchListener(TextView editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editText.setText(String.valueOf(Integer.parseInt(editText.getText().toString()) + 1));

                        return true;

                    }

                    if (event.getRawX() >= (editText.getLeft() - editText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        if (!editText.getText().toString().equals("0")) {
                            editText.setText(String.valueOf(Integer.parseInt(editText.getText().toString()) - 1));
                        } else {
                            editText.setText(editText.getText().toString());
                        }

                        return true;

                    }

                }


                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }
}