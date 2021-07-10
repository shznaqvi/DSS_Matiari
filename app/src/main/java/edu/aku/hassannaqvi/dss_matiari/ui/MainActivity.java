package edu.aku.hassannaqvi.dss_matiari.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.dss_matiari.models.Form;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.IdentificationActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        bi.adminView.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);
        bi.username.setText("Welcome, " + MainApp.user.getFullname() + (MainApp.admin ? " (Admin)" : "") + "!");
    }

    public void sectionPress(View view) {

        switch (view.getId()) {
            case R.id.openForm:
                MainApp.idType = 1;
                break;
            default:
                MainApp.idType = 0;
        }


        switch (view.getId()) {

            case R.id.openForm:
            case R.id.ident:
                MainApp.idType = 1;
                MainApp.form = new Form();
                startActivity(new Intent(this, IdentificationActivity.class));
                break;
            case R.id.seca:
                MainApp.form = new Form();
                startActivity(new Intent(this, SectionAActivity.class));
                break;
            case R.id.secb:
                MainApp.form = new Form();
                startActivity(new Intent(this, SectionBActivity.class));
                break;
        }
    }
}