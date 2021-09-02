package edu.aku.hassannaqvi.dss_matiari.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.Form;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<Form> forms;
    private final int mExpandedPosition = -1;
    private final int completeCount;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param forms List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public HouseholdAdapter(Context mContext, List<Form> forms) {
        this.forms = forms;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;


    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        Form form = this.forms.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView hhNo = viewHolder.hhNo;
        TextView hhHead = viewHolder.hhHead;
        TextView mwraCount = viewHolder.mwraCount;
        TextView secStatus = viewHolder.secStatus;
        ImageView imgStatus = viewHolder.imgStatus;

        //String pregStatus = form.getRb07().equals("1") ? "Pregnant" : "Not Pregnant";

        //MainApp.fmComplete = completeCount == MainApp.formCount;


        String hhStatus = "";
        switch (form.getiStatus()) {
            case "1":
                hhStatus = "Complete";
                break;
            case "2":
                hhStatus = "Locked";
                break;
            case "3":
                hhStatus = "Refused";
                break;
            default:
                hhStatus = "Other";
                break;
        }

/*
{"ra01":"2021-08-23","ra02":"","ra04":"","ra03":"","ra05":"","ra07":"9001","ra06":"9","ra08":"asd","ra09":"2","ra10":"1","ra11":"96","ra11x":"ghg","ra12":"96","ra12x":"vgv","ra13":"","ra13x":"","ra14":"head","ra15":"resp","ra16":"2","ra17_a":"1","ra17_b":"1","ra17_c":"1","ra17_d":"1","ra18":"1"}
        fMaritalStatus.setText(marStatus + " | " + pregStatus);*/
        DatabaseHelper db = MainApp.appInfo.dbHelper;
        int totalMWRA = db.getMWRACountBYUUID(form.getUid());

        hhNo.setText(form.getRa07() + "-" + form.getRa10() + "-" + form.getRa09());
        hhHead.setText(form.getRa14());
        mwraCount.setText(totalMWRA + " MWRA(s)");
        secStatus.setText(hhStatus);
        imgStatus.setVisibility(form.getiStatus().equals("1") || Integer.parseInt(form.getVisitNo()) > 2 ? View.VISIBLE : View.GONE);

        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.form = MainApp.householdList.get(position);
            //MainApp.form.setVisitNo(String.valueOf(Integer.parseInt(MainApp.form.getVisitNo())+1));
            if (!MainApp.form.getiStatus().equals("1")) {

                editHousehold(position);

            } else {
                Toast.makeText(mContext, "This form has been locked. You cannot edit household for locked forms", Toast.LENGTH_LONG).show();
            }


        });

    }

    private void editHousehold(int position) {
        Intent intent = new Intent(mContext, SectionAActivity.class);
        intent.putExtra("position", position);
        MainApp.selectedHousehold = position;
        intent.putExtra("position", position);

        ((Activity) mContext).startActivityForResult(intent, 2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.household_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView hhNo;
        private final TextView hhHead;
        private final TextView mwraCount;
        private final TextView secStatus;
        private final ImageView imgStatus;
/*        private final TextView fMatitalStatus;

        private final ImageView fmRow;
        private final View indicator;*/


        public ViewHolder(View v) {
            super(v);
            hhNo = v.findViewById(R.id.ra09);
            hhHead = v.findViewById(R.id.ra14);
            mwraCount = v.findViewById(R.id.mwraCount);
            secStatus = v.findViewById(R.id.secStatus);
            imgStatus = v.findViewById(R.id.imgStatus);
/*            fMatitalStatus = v.findViewById(R.id.hh06);
            secStatus = v.findViewById(R.id.secStatus);
            fmRow = v.findViewById(R.id.fmRow);
            indicator = v.findViewById(R.id.indicator);*/

        }

        public TextView getTextView() {
            return hhHead;
        }
    }


}
