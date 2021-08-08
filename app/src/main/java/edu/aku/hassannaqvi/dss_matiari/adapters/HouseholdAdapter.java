package edu.aku.hassannaqvi.dss_matiari.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
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

        //String pregStatus = form.getRb07().equals("1") ? "Pregnant" : "Not Pregnant";

        //MainApp.fmComplete = completeCount == MainApp.formCount;

        hhNo.setText(form.getRa07() + "-" + form.getRa10() + "-" + form.getRa09());
        hhHead.setText(form.getRa14());
/*
        String marStatus = "";
        switch (form.getRb06()) {
            case "2":
                marStatus = "Currently Married";
                break;
            case "3":
                marStatus = "Unmarried";
                break;
            default:
                marStatus = "Value Unknown";
                break;
        }

        fMaritalStatus.setText(marStatus + " | " + pregStatus);*/

        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.form = MainApp.householdList.get(position);
            Intent intent = new Intent(mContext, SectionAActivity.class);
            intent.putExtra("position", position);
            MainApp.selectedFemale = position;
            intent.putExtra("position", position);

            ((Activity) mContext).startActivityForResult(intent, 2);


        });

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
/*        private final TextView fMatitalStatus;
        private final TextView secStatus;
        private final ImageView fmRow;
        private final View indicator;*/


        public ViewHolder(View v) {
            super(v);
            hhNo = v.findViewById(R.id.ra09);
            hhHead = v.findViewById(R.id.ra14);
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
