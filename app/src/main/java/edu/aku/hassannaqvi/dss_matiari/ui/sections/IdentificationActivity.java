package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.util.ArrayList;
import java.util.Collection;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityIdentificationBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.models.Villages;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.EndingActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPHouseholdActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.HouseholdActivity;

public class IdentificationActivity extends AppCompatActivity {

    private static final String TAG = "IdentificationActivity";
    ActivityIdentificationBinding bi;
    private Intent openIntent;
    private DssRoomDatabase db;
    private ArrayList<String> ucNames;
    private ArrayList<String> ucCodes;
    private ArrayList<String> villageNames;
    private ArrayList<String> villageCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        db = MainApp.appInfo.dbHelper;
        bi = DataBindingUtil.setContentView(this, R.layout.activity_identification);

        bi.setCallback(this);

        populateSpinner();
        MainApp.previousAddress = "";

        openIntent = new Intent();


        switch (MainApp.idType) {
            case 1:
                bi.btnContinue.setText("Open Household List");
                MainApp.households = new Households();
                openIntent = new Intent(this,  HouseholdActivity.class);
                break;
            case 2:
                bi.btnContinue.setText("Open Followups List");
                MainApp.households = new Households();
                MainApp.mwra = new Mwra();
                MainApp.outcome = new Outcome();
                openIntent = new Intent(this, FPHouseholdActivity.class);
                break;


        }

    }

    private void populateSpinner() {

//        Collection<Villages> uc = db.getVillageUc();
        Collection<Villages> uc = db.VillagesDao().getVillageUc();
        ucNames = new ArrayList<>();
        ucCodes = new ArrayList<>();

        ucNames.add("...");
        ucCodes.add("...");

        for (Villages u : uc) {
            ucNames.add(u.getUcname());
            ucCodes.add(u.getUccode());
        }
        if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu")) {
            ucNames.add("Test UC 9");
            ucNames.add("Test UC 8");
            ucNames.add("Test UC 7");
            ucCodes.add("9");
            ucCodes.add("8");
            ucCodes.add("7");
        }
        // Apply the adapter to the spinner
        bi.ra06.setAdapter(new ArrayAdapter(IdentificationActivity.this, R.layout.custom_spinner, ucNames));

        bi.ra06.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bi.ra07.setAdapter(null);
                bi.ra10.setText(null);
                bi.ra10.setEnabled(false);
                bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.gray));
                bi.btnContinue.setEnabled(false);

                if (position == 0) return;
                //Collection<Villages> village = db.getVillageByUc(ucCodes.get(position));
                Collection<Villages> village = db.VillagesDao().getVillageByUc(ucCodes.get(position));
                villageNames = new ArrayList<>();
                villageCodes = new ArrayList<>();
                villageNames.add("...");
                villageCodes.add("...");

                for (Villages v : village) {
                    villageNames.add(v.getVillagename());
                    villageCodes.add(v.getVillagecode());
                }
                if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu")) {

                    villageNames.add("Test Village 1 " + ucNames.get(position));
                    villageNames.add("Test Village 1 " + ucNames.get(position));
                    villageNames.add("Test Village 1 " + ucNames.get(position));
                    villageCodes.add(ucCodes.get(position) + "0901");
                    villageCodes.add(ucCodes.get(position) + "0902");
                    villageCodes.add(ucCodes.get(position) + "0903");
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

                bi.ra10.setText(null);
                if (position != 0) {
                    String vCode = villageCodes.get(bi.ra07.getSelectedItemPosition());

                    //int maxHHno = db.getMaxStructure(selectedUC, vCode) + 1;

                    int maxHHno = db.householdsDao().getMaxStructure(selectedUC, vCode) +1;

//                    bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.colorAccent));
                    bi.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(IdentificationActivity.this, R.color.colorPrimary));
                    bi.btnContinue.setEnabled(true);

                    bi.ra10.setText(String.valueOf(maxHHno));
                    if (position == 0) return;
                    bi.ra10.setEnabled(true);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    public void btnContinue(View view) {


        if (!formValidation()) return;

        MainApp.selectedUC = ucCodes.get(bi.ra06.getSelectedItemPosition());
                MainApp.selectedVillage = villageCodes.get(bi.ra07.getSelectedItemPosition());


        finish();
        startActivity(openIntent);

    }

    public void btnEnd(View view) {
        finish();

        switch (MainApp.idType) {
            case 1:
                openIntent = new Intent(this, EndingActivity.class).putExtra("complete", false);
                break;
            case 2:
                openIntent = new Intent(this, EndingActivity.class).putExtra("complete", false);



        }


    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

}