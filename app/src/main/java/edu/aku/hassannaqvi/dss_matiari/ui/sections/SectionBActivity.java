package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.dss_matiari.utils.DateUtilsKt;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;

public class SectionBActivity extends AppCompatActivity {

    private static final String TAG = "SectionBActivity";
    ActivitySectionBBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);
        bi.setMwra(mwra);
        setListener();

        mwra.setRb01(String.valueOf(mwraCount + 1));

        Log.d(TAG, "onCreate: 6 " + form.getRb06());
        Log.d(TAG, "onCreate: 7 " + form.getRb07());

        setTitle(R.string.sectionB_mainheading);
        setImmersive(true);

        db = MainApp.appInfo.dbHelper;
    }

    private void setListener() {
        bi.rb04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mwra.getRb04().equalsIgnoreCase("")) {
//                    String[] arrStr = mwra.getRb04().split("-");
//                    int day, month, year;
//                    year = arrStr.length > 0 ? Integer.valueOf(arrStr[0]) : 0;
//                    month = arrStr.length > 1 ? Integer.valueOf(arrStr[1]) : 0;
//                    day = arrStr.length > 2 ? Integer.valueOf(arrStr[2]) : 0;
//                    if (year == 0 || month == 0 || day ==0) {
//                        return;
//                    }
//                    bi.rb05.setText(DateUtilsKt.ageInYears(day, month ,year ,year));
                    String[] arrStr = mwra.getRb04().split("-");
                    String day, month, year;
                    year = arrStr.length > 0 ? arrStr[0] : "0";
                    month = arrStr.length > 1 ? arrStr[1] : "0";
                    day = arrStr.length > 2 ? arrStr[2] : "0";
                    if (year.equalsIgnoreCase("0") || month.equalsIgnoreCase("0") || day.equalsIgnoreCase("0")) {
                        return;
                    }
                    bi.rb05.setText(DateUtilsKt.getAge(year, month, day, false));
                }
            }
        });
    }

    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (MainApp.mwra.getUid().equals("") ? insertNewRecord() : updateDB()) {
            setResult(RESULT_OK);
            finish();
            //  startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDraft() {

        //mwra = new MWRA();

        MainApp.mwra.setUserName(MainApp.user.getUserName());
        MainApp.mwra.setSysDate(form.getSysDate());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setHdssId(form.getHdssId());
        MainApp.mwra.setAppver(MainApp.versionName + "." + MainApp.versionCode);


        mwra.setRb01(bi.rb01.getText().toString());
        mwra.setRb02(bi.rb02.getText().toString());
        mwra.setRb03(bi.rb03.getText().toString());
        mwra.setRb04(bi.rb04.getText().toString());
        mwra.setRb05(bi.rb05.getText().toString());
        mwra.setRb06(bi.rb0602.isChecked() ? "2"
                : bi.rb0603.isChecked() ? "3"
                : "-1");
        mwra.setRb07(bi.rb0701.isChecked() ? "1"
                : bi.rb0702.isChecked() ? "2"
                : "-1");
        mwra.setRb08(bi.rb08.getText().toString());
        mwra.setRb09(bi.rb09.getText().toString());
        mwra.setS2(mwra.s2toString());
    }

/*    private boolean insertNewRecord() {
        if (MainApp.form.isExist()) return true;
        db = MainApp.appInfo.getDbHelper();
        long rowId = db.addForm(form);
        form.setId(String.valueOf(rowId));
        if (rowId > 0) {
            form.setUid(form.getDeviceId() + form.getId());
            db.updatesFormColumn(TableContracts.FormsTable.COLUMN_UID, form.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/

    private boolean insertNewRecord() {
        db = MainApp.appInfo.getDbHelper();
        long rowId = db.addMWRA(mwra);
        mwra.setId(String.valueOf(rowId));
        if (rowId > 0) {
            mwra.setUid(mwra.getDeviceId() + mwra.getId());
            db.updatesMWRAColumn(TableContracts.MWRATable.COLUMN_UID, mwra.getUid());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        setResult(Activity.RESULT_CANCELED);
        finish();
        /*saveDraft();
        if (updateDB()) {

            Toast.makeText(this, "Patient information not recorded.", Toast.LENGTH_SHORT).show();
            finish();
        *//*    Intent i = new Intent(this, EndingActivity.class);
            i.putExtra("complete", false);
            startActivity(i);*//*
        }*/

    }

    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) return false;
        if (!compareTwoDate(bi.rb08, 2,
                "LMP should be within 2 months back from DOV")) return false;
        if (!compareTwoDate(bi.rb09, 9,
                "EDD should be within 9 months back from DOV")) return false;
        return true;
    }

    private boolean compareTwoDate(EditText et, int month, String msg) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDOV = sdf.parse(form.getRa01());
            Calendar calendarDOV1 = Calendar.getInstance();
            Calendar calendarDOV2 = Calendar.getInstance();
            calendarDOV1.setTime(dateDOV);
            calendarDOV2.setTime(dateDOV);
            calendarDOV2.add(Calendar.MONTH, month);
            Date dateTwo = sdf.parse(et.getText().toString().trim());
            if (dateTwo.before(calendarDOV1.getTime()) || dateTwo.after(calendarDOV2.getTime())) {
                Validator.emptyCustomTextBox(this, et, msg);
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}