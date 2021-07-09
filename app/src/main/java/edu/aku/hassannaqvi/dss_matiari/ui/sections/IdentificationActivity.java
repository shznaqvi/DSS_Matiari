package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityIdentificationBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Form;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;

public class IdentificationActivity extends AppCompatActivity {

    ActivityIdentificationBinding bi;
    private Intent openIntent;
    private DatabaseHelper db;
    private String hdssid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = MainApp.appInfo.dbHelper;
        bi = DataBindingUtil.setContentView(this, R.layout.activity_identification);
        // setContentView(R.layout.activity_identification);
        bi.setCallback(this);
        openIntent = new Intent();
        openIntent = new Intent();
        switch (MainApp.idType) {
            case 1:
                bi.btnContinue.setText("Open Household Form");
                MainApp.form = new Form();
                openIntent = new Intent(this, SectionAActivity.class);
                break;
           /* case 2:
                bi.btnContinue.setText("Open Anthro Form");
                anthro = new Anthro();
                openIntent = new Intent(this, SectionAnthroActivity.class);
                break;
            case 3:
                bi.btnContinue.setText("Open Blood Form");
                //     MainApp.sample = new Sample();
                openIntent = new Intent(this, SectionSamplesActivity.class);
                openIntent.putExtra("type", "1"); // BLOOD - 1
                break;
            case 4:
                bi.btnContinue.setText("Open Stool Form");
                //    MainApp.sample = new Sample();
                openIntent = new Intent(this, SectionSamplesActivity.class);
                openIntent.putExtra("type", "2"); // STOOL - 2
                break;*/

        }

    }


    public void btnContinue(View view) {

        hdssid = bi.ra07.getText().toString() +
                bi.ra06.getText().toString() +
                bi.ra08.getText().toString() +
                bi.ra09.getText().toString() +
                bi.ra10.getText().toString();


        if (!formValidation()) return;
        switch (idType) {
            case 1:
                if (!hhExists()) {
                    saveDraftForm();
                } else {
                    MainApp.form.setExist(true);
                }
                break;
            case 2:
                if (!hhExists())
                    saveDraftAnthro();
                break;
            case 3:
            case 4:
                if (!hhExists())
                    saveDraftSamples();
                break;

        }
        finish();
        startActivity(openIntent);

    }

    private void saveDraftForm() {
        MainApp.form = new Form();

        MainApp.form.setUserName(MainApp.user.getUserName());
        MainApp.form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        MainApp.form.setDeviceId(MainApp.deviceid);
        MainApp.form.setHdssId(hdssid);
        MainApp.form.setAppver(MainApp.versionName + "." + MainApp.versionCode);

    }

    private void saveDraftAnthro() {
/*
        anthro = new Anthro();

        anthro.setUserName(MainApp.user.getUserName());
        anthro.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        anthro.setDeviceId(MainApp.deviceid);
        anthro.setAppver(MainApp.versionName + "." + MainApp.versionCode);
*/

    }

    private void saveDraftSamples() {

        //TODO:
     /*   MainApp.samples = new Samples();

        samples.setUserName(MainApp.user.getUserName());
        samples.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        samples.setDeviceId(MainApp.deviceid);
        samples.setAppver(MainApp.versionName + "." + MainApp.versionCode);*/

    }


    public void btnEnd(View view) {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

   /* public void checkHousehold(View view) {
        Random hhFound = db.checkHousehold(bi.h103.getText().toString(), bi.h104.getText().toString());
        if (hhFound != null) {
            bi.hhhead.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            bi.btnContinue.setEnabled(true);
            bi.checkHousehold.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
            bi.checkHousehold.setEnabled(false);

            bi.hhhead.setText(hhFound.getHeadhh());
            Toast.makeText(this, hhFound.getHeadhh(), Toast.LENGTH_SHORT).show();


        } else {
            bi.hhhead.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            bi.hhhead.setText("Household not Found");
            bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
            bi.btnContinue.setEnabled(false);


        }
    }*/

    private boolean hhExists() {

        switch (idType) {
            case 1:
                MainApp.form = new Form();
                MainApp.form = db.getFormByHDSSID(hdssid);
                return MainApp.form != null;

            //TODO: Antro & Samples will be multiple. Different logic will be required
        /*    case 2:
                anthro = new Anthro();
                anthro = db.getAnthroByClusterHHNo(bi.h103.getText().toString(), bi.h103.getText().toString());
                return anthro != null;
            case 2:
                samples = new Samples();
                anthro = db.getSamplesByClusterHHNo(bi.h103.getText().toString(), bi.h103.getText().toString());
                return anthro != null;*/
            default:
                return false;

        }
    }
}