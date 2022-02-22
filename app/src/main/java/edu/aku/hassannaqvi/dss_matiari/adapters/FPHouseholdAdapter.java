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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.FPHouseholds;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPMwraActivity;


public class FPHouseholdAdapter extends RecyclerView.Adapter<FPHouseholdAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<FollowUpsSche> followUpsScheList;
    private final int mExpandedPosition = -1;
    private final int completeCount;
    private final DatabaseHelper db;
    private FPHouseholds fpHouseholds;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param fpHouseholds List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public FPHouseholdAdapter(Context mContext, List<FollowUpsSche> fpHouseholds) {
        this.followUpsScheList = fpHouseholds;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;

        db = MainApp.appInfo.dbHelper;

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        FollowUpsSche followUpsSche = this.followUpsScheList.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView hhNo = viewHolder.hhNo;
        TextView hhHead = viewHolder.hhHead;
        TextView mwraCount = viewHolder.mwraCount;
        TextView secStatus = viewHolder.secStatus;
        ImageView imgStatus = viewHolder.imgStatus;

        //String pregStatus = followUpsScheList.getRc07().equals("1") ? "Pregnant" : "Not Pregnant";

        //MainApp.fmComplete = completeCount == MainApp.formCount;
        try {
            fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(position).getHdssid());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String hhStatus = "";
        switch (fpHouseholds.getiStatus()) {
            case "1":
                hhStatus = "Complete";
                break;
            case "2":
                hhStatus = "Locked";
                break;
            case "3":
                hhStatus = "Refused";
                break;
            case "4":
                hhStatus = "No WRA";
                break;
            case "":
                hhStatus = "       ";
                break;
            default:
                hhStatus = "Other";
                break;
        }

/*
{"ra01":"2021-08-23","ra02":"","ra04":"","ra03":"","ra05":"","ra07":"9001","ra06":"9","ra08":"asd","ra09":"2","ra10":"1","ra11":"96","ra11x":"ghg","ra12":"96","ra12x":"vgv","ra13":"","ra13x":"","ra14":"head","ra15":"resp","ra16":"2","ra17_a":"1","ra17_b":"1","ra17_c":"1","ra17_d":"1","ra18":"1"}
        fMaritalStatus.setText(marStatus + " | " + pregStatus);*/
        int totalMWRA = Integer.parseInt(followUpsSche.getRa18().equals("") ? "0" : followUpsSche.getRa18());

        hhNo.setText(followUpsSche.getHhNo());
        hhHead.setText(followUpsSche.getRa14());
        mwraCount.setText(totalMWRA + " Women");
        secStatus.setText(hhStatus);
        imgStatus.setVisibility(fpHouseholds.getiStatus().equals("1") || Integer.parseInt(fpHouseholds.getVisitNo()) > 2 ? View.VISIBLE : View.GONE);
        secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grayDark));

        if (!fpHouseholds.getiStatus().equals("1")) {
            viewHolder.itemView.setOnClickListener(v -> {
                // Get the current state of the item

                try {
                    MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(position).getHdssid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (MainApp.fpHouseholds.getUid().equals(""))
                MainApp.fpHouseholds.populateMeta(position);


            MainApp.fpHouseholds.setVisitNo(String.valueOf(Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) + 1));
            mContext.startActivity(new Intent(mContext, FPMwraActivity.class));

                if (!MainApp.fpHouseholds.getiStatus().equals("1") && Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) < 3) {

                    editHousehold(position);

                } else {
                    Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                }


            });
        }

    }

    private void editHousehold(int position) {
        Intent intent = new Intent(mContext, FPMwraActivity.class);
        intent.putExtra("position", position);
        MainApp.selectedFpHousehold = position;
        MainApp.selectedHhNO = MainApp.followUpsScheHHList.get(position).getHhNo();
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
        return followUpsScheList.size();
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
