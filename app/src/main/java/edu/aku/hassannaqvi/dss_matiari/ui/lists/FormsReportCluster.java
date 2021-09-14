package edu.aku.hassannaqvi.dss_matiari.ui.lists;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.adapters.FormsAdapter;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;


public class FormsReportCluster extends AppCompatActivity {
    DatabaseHelper db;
    String sysdateToday = new SimpleDateFormat("dd-MM-yy").format(new Date());
    TextView dtFilter;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter formsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_report_cluster);
        recyclerView = findViewById(R.id.fc_recycler_view);
        toolbar = findViewById(R.id.toolbar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //dtFilter = findViewById(R.id.dtFilter);
        db = MainApp.appInfo.dbHelper;

        // specify an adapter (see also next example)
        MainApp.householdList = db.getUnclosedHouseholds();
        formsAdapter = new FormsAdapter(MainApp.householdList, this);
        recyclerView.setAdapter(formsAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        MainApp.householdList = db.getUnclosedHouseholds();
        formsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Activity Resumed", Toast.LENGTH_SHORT).show();

    }

    public void filterForms(View view) {
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        //fc = db.getUnclosedForms(dtFilter.getText().toString());
        MainApp.householdList = db.getUnclosedHouseholds();
        formsAdapter = new FormsAdapter(MainApp.householdList, this);
        formsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(formsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                MainApp.householdList = db.getUnclosedHouseholds();
                formsAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Results updated", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                //   Toast.makeText(this, "Information for " + MainApp.householdList.get(selectedHousehold).getRa14() + " was not saved.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}