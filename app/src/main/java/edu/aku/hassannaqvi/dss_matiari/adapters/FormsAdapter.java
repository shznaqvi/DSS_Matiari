package edu.aku.hassannaqvi.dss_matiari.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.Collections;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


/**
 * Created by hassan.naqvi on 8/1/2016.
 */
public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.ViewHolder> {
    Context c;
    DatabaseHelper db;
    private List<Households> fc = Collections.emptyList();

    // Provide a suitable constructor (depends on the kind of dataset)
    public FormsAdapter(List<Households> fc, Context c) {
        this.fc = fc;
        this.c = c;
        Log.d("TAG:count", String.valueOf(getItemCount()));
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pendingform_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        db = MainApp.appInfo.dbHelper;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

/*        int childCount = 0;
        childCount = db.getChildrenByUUID(fc.get(position).get_UID());
        int photoChild = 0;
        photoChild = db.getChildrenPhotoCheck(fc.get(position).get_UID());
        int cardChild = 0;
        cardChild = db.getChildrenCardCheck(fc.get(position).get_UID());*/


        String iStatus = "Status  Unknown";
        int iColor = 0;
        switch (fc.get(position).getiStatus()) {
            case "1":
                iStatus = "   Complete    ";
                iColor = Color.GREEN;
                break;
            case "2":
                iStatus = " No Respondent ";
                iColor = Color.RED;
                break;
            case "3":
                iStatus = "    Refused    ";
                iColor = Color.RED;
                break;
            case "4":
                iStatus = "    No WRA     ";
                iColor = Color.RED;
                break;

            case "96":
                iStatus = "  Other Reason ";
                iColor = Color.RED;
                break;
            default:
                iStatus = "Open Households";
                iColor = Color.RED;
                break;

        }

        /*
                TableContracts.HouseholdTable._ID,
                TableContracts.HouseholdTable.COLUMN_UID,
                TableContracts.HouseholdTable.COLUMN_SYSDATE,
                TableContracts.HouseholdTable.COLUMN_USERNAME,
                TableContracts.HouseholdTable.COLUMN_ISTATUS,
                TableContracts.HouseholdTable.COLUMN_SYNCED,
                TableContracts.HouseholdTable.COLUMN_VISIT_NO,
                TableContracts.HouseholdTable.COLUMN_STRUCTURE_NO,
                TableContracts.HouseholdTable.COLUMN_VILLAGE_CODE,
                TableContracts.HouseholdTable.COLUMN_UC_CODE,
                TableContracts.HouseholdTable.COLUMN_HOUSEHOLD_NO,
*/
        String cluster = fc.get(position).getUcCode() + "-" + fc.get(position).getVillageCode() + "-" + fc.get(position).getStructureNo() + "-" + fc.get(position).getHhNo();

        holder.sysdate.setText(fc.get(position).getSysDate());
        holder.cluster.setText(cluster);
        holder.istatus.setText(iStatus);
        // holder.sysdate.setText("  Child Count: " + childCount + " \t\t\t Card Seen: " + cardChild + " \t\t\t Photo Child: " + photoChild);
        holder.istatus.setTextColor(iColor);
        holder.fathername.setText(fc.get(position).getVisitNo() + " visits");

        int totalMWRA = db.getMWRACountBYUUID(fc.get(position).getUid());
        holder.hhno.setText(totalMWRA + " MWRA(s)");

        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            try {
                MainApp.households = db.getHouseholdByUID(MainApp.householdList.get(position).getUid());

                //MainApp.households.setVisitNo(String.valueOf(Integer.parseInt(MainApp.households.getVisitNo())+1));
                if (!MainApp.households.getiStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {

                    editHousehold(position);

                } else {
                    Toast.makeText(c, "This households has been locked. You cannot edit household for locked forms", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(c, "JSONException(Households): " + e.getMessage(), Toast.LENGTH_LONG).show();

            }


        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return fc.size();
    }

    private void editHousehold(int position) {
        Intent intent = new Intent(c, SectionAActivity.class);
        intent.putExtra("position", position);
        MainApp.selectedHousehold = position;
        intent.putExtra("position", position);

        ((Activity) c).startActivityForResult(intent, 2);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rv;
        public TextView sysdate;
        public TextView cluster;
        public TextView hhno;
        public TextView istatus;
        public TextView fathername;
        // each data item is just a string in this case

        public ViewHolder(View v) {
            super(v);
//            rv = v.findViewById(R.id.FormsList);
            sysdate = v.findViewById(R.id.sysdate);
            cluster = v.findViewById(R.id.cluster);
            hhno = v.findViewById(R.id.hhno);
            istatus = v.findViewById(R.id.istatus);
            fathername = v.findViewById(R.id.fathername);

        }


    }
}