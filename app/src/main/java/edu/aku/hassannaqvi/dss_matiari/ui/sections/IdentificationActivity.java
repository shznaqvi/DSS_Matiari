package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityIdentificationBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Form;
import edu.aku.hassannaqvi.dss_matiari.models.Villages;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.hdssid;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.idType;

public class IdentificationActivity extends AppCompatActivity {

    ActivityIdentificationBinding bi;
    private Intent openIntent;
    private DatabaseHelper db;
    private ArrayList<String> ucNames;
    private ArrayList<String> ucCodes;
    private ArrayList<String> villageNames;
    private ArrayList<String> villageCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = MainApp.appInfo.dbHelper;
        bi = DataBindingUtil.setContentView(this, R.layout.activity_identification);
        // setContentView(R.layout.activity_identification);
        bi.setCallback(this);

        populateSpinner();

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

    private void populateSpinner() {

        Collection<Villages> uc = db.getVillageUc();
        ucNames = new ArrayList<>();
        ucCodes = new ArrayList<>();

        ucNames.add("...");
        ucCodes.add("...");
        for (Villages u : uc) {
            ucNames.add(u.getUcname());
            ucCodes.add(u.getUccode());
        }

        // Apply the adapter to the spinner
        bi.ra06.setAdapter(new ArrayAdapter(IdentificationActivity.this, R.layout.custom_spinner, ucNames));

        bi.ra06.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bi.ra07.setAdapter(null);
                bi.ra08.setText(null);
                bi.ra09.setText(null);
                bi.ra10.setText(null);
                bi.ra08.setEnabled(false);
                bi.ra09.setEnabled(false);
                bi.ra10.setEnabled(false);
                bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.gray));
                bi.btnContinue.setEnabled(false);
                //  bi.checkHousehold.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.colorAccent));
                //  bi.checkHousehold.setEnabled(true);

                if (position == 0) return;
                Collection<Villages> village = db.getVillageByUc(ucCodes.get(position));
                villageNames = new ArrayList<>();
                villageCodes = new ArrayList<>();
                villageNames.add("...");
                villageCodes.add("...");
                for (Villages v : village) {
                    villageNames.add(v.getVillagename());
                    villageCodes.add(v.getVillagecode());
                }

                // Apply the adapter to the spinner
                bi.ra07.setAdapter(new ArrayAdapter(IdentificationActivity.this, R.layout.custom_spinner, villageNames));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        bi.ra07.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bi.ra08.setText(null);
                bi.ra09.setText(null);
                bi.ra10.setText(null);

                bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.colorAccent));
                bi.btnContinue.setEnabled(true);
           /*     bi.checkHousehold.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.colorAccent));
                bi.checkHousehold.setEnabled(true);*/
                bi.ra08.setEnabled(false);
                bi.ra09.setEnabled(false);
                if (position == 0) return;
                bi.ra08.setEnabled(true);
                bi.ra09.setEnabled(true);
                bi.ra10.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    public void btnContinue(View view) {

        hdssid = villageCodes.get(bi.ra07.getSelectedItemPosition()) +
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

        MainApp.form.setRa06(ucCodes.get(bi.ra06.getSelectedItemPosition()));
        MainApp.form.setRa07(villageCodes.get(bi.ra07.getSelectedItemPosition()));
        MainApp.form.setRa08(bi.ra08.getText().toString());
        MainApp.form.setRa09(bi.ra09.getText().toString());
        MainApp.form.setRa10(bi.ra10.getText().toString());

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