package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.wajahatkarim3.roomexplorer.RoomExplorer;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.AndroidManager;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Followups;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.models.Outcome;
import edu.aku.hassannaqvi.dss_matiari.models.OutcomeFollowups;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FormsReportCluster;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.IdentificationActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    SharedPreferences sp;
    private String downloaded, uploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        setSupportActionBar(bi.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.app_icon);
        bi.adminView.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);bi.username.setText("Welcome, " + MainApp.user.getFullname() + (MainApp.admin ? " (Admin)" : "") + "!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloaded = sharedPref.getString("downloaded", "never");
        uploaded = sharedPref.getString("uploaded", "never");
        bi.syncStatus.setText("Last Downloaded: " + downloaded + "\nLast Uploaded: " + uploaded);

    }

    public void sectionPress(View view) {

        switch (view.getId()) {
            case R.id.openForm:
                MainApp.idType = 1;
                break;
            case R.id.openFollowup:
                MainApp.idType = 2;
                break;

        }


        switch (view.getId()) {

            case R.id.openForm:
            case R.id.ident:
                MainApp.idType = 1;
                MainApp.households = new Households();
                startActivity(new Intent(this, IdentificationActivity.class));
                break;


            case R.id.seca:
                MainApp.households = new Households();
                MainApp.followups = new Followups();
                startActivity(new Intent(this, SectionAActivity.class));
                break;
            case R.id.secb:
                MainApp.households = new Households();
                MainApp.followups = new Followups();
                startActivity(new Intent(this, SectionBActivity.class));
                break;
            case R.id.openFollowup:
                MainApp.idType = 2;
                MainApp.followups = new Followups();

                startActivity(new Intent(this, IdentificationActivity.class));

                break;
            case R.id.dbm:
                RoomExplorer.show(this, DssRoomDatabase.class, DssRoomDatabase.DATABASE_NAME);
//                startActivity(new Intent(this, AndroidManager.class));
                break;
            default:
                MainApp.idType = 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.onSync:
                intent = new Intent(MainActivity.this, SyncActivity.class);
                break;
 /*            case R.id.checkOpenForms:
                intent = new Intent(MainActivity.this, PendingFormsActivity.class);
                break;
            case R.id.formsReportDate:
                intent = new Intent(MainActivity.this, FormsReportDate.class);
                break;*/
            case R.id.checkOpenForms:
                intent = new Intent(MainActivity.this, FormsReportCluster.class);
                break;
            case R.id.dbm:
                startActivity(new Intent(this, AndroidManager.class));
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}