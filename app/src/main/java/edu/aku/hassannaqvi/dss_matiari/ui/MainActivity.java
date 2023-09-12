package edu.aku.hassannaqvi.dss_matiari.ui;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.io.File;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.newstruct.activity.SyncNewAC;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
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
                startActivity(new Intent(this, SectionAActivity.class));
                break;
            case R.id.secb:
                MainApp.households = new Households();
                startActivity(new Intent(this, SectionBActivity.class));
                break;
            case R.id.openFollowup:
                MainApp.idType = 2;
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
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.onSync) {
            // IS_LOGIN = To differentiate before and after login download
            // For after login sync data download
            AppConstants.IS_LOGIN = true;
            AppConstants.gotoActivity(this, SyncNewAC.class, false);
       } else if (menuItemId == R.id.checkOpenForms) {
            AppConstants.gotoActivity(this, FormsReportCluster.class, false);
        } else if (menuItemId == R.id.sendDB) {
            sendEmail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        menu.findItem(R.id.sendDB).setVisible(MainApp.admin);
        return super.onCreateOptionsMenu(menu);
    }

    // Email database to specified email address as attachment
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"omar.shoaib@aku.edu", "hussain.siddiqui@aku.edu", "gul.sanober@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kiran.sajid@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DSS Matiari Database - For Issue Monitoring");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "DSS Matiari database upload from the device which has issues while uploading the data." +
                "This is just for testing/checking purpose.");
        File file = LoginActivity.dbBackup(MainActivity.this);
        if (file == null || !file.exists() || !file.canRead()) {
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, "edu.aku.hassannaqvi.dss_matiari.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(emailIntent, "Pick an email provider"));
    }

}