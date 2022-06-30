package edu.aku.hassannaqvi.dss_matiari.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionCxActivity;


public class FpMwraAdapter extends RecyclerView.Adapter<FpMwraAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<FollowUpsSche> followupsSche;
    private final int mExpandedPosition = -1;
    private final int completeCount;
    private final DatabaseHelper db;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param followupsSche List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public FpMwraAdapter(Context mContext, List<FollowUpsSche> followupsSche) {
        this.followupsSche = followupsSche;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;

        db = MainApp.appInfo.dbHelper;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int positio) {
        Log.d(TAG, "Element " + viewHolder.getAdapterPosition() + " set.");
        FollowUpsSche followUpsSche = this.followupsSche.get(viewHolder.getAdapterPosition());        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView fName = viewHolder.fName;
        TextView fAge = viewHolder.fAge;
        TextView fMaritalStatus = viewHolder.fMatitalStatus;
        ImageView indicator = viewHolder.indicator;
        TextView secStatus = viewHolder.secStatus;
        LinearLayout mStatus = viewHolder.mstatus;

        TextView secDob = viewHolder.secDob;
        TextView secGender = viewHolder.secGender;
        String pregStatus = followUpsSche.getRb07().equals("1") ? "PW" : "  ";
        try {
            String curPregStatus = db.getFollowupsBySno(followUpsSche.getRb01(), followUpsSche.getfRound()).getRc07();
            if (!curPregStatus.equals("")) {
                pregStatus = curPregStatus.equals("1") ? "Pregnant" : " Not Pregnant ";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        MainApp.fmComplete = completeCount == MainApp.mwraCount;

        fName.setText(followUpsSche.getRb02());
        //fAge.setText(mwra.getRb05() + "y | " + mwra.getRc03());
        String marStatus = "";
        String wifeOrDaughter = followUpsSche.getRb06().equals("4") ? " d/o " : " w/o ";

        indicator.setBackgroundColor(!followUpsSche.getfpDoneDt().equals("") ? ContextCompat.getColor(mContext, R.color.greenLight) : ContextCompat.getColor(mContext, R.color.colorAccent));

        switch (followUpsSche.getRb06()) {
            case "1":
                marStatus = "Married";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.redLight));
                break;
            case "2":
                marStatus = "Divorced";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_700));

                break;
            case "3":
                marStatus = "Widow";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_200));

                break;
            case "4":
                marStatus = "Unmarried";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightPink));

                break;
            default:
                marStatus = "Value Unknown";
                break;
        }

        fAge.setText(marStatus + " | " + followUpsSche.getRb05() + "y  ");


        secStatus.setBackgroundColor(followUpsSche.getRb07().equals("1") ? ContextCompat.getColor(mContext, R.color.redLight) : ContextCompat.getColor(mContext, R.color.grayLight));
        indicator.setImageDrawable(followUpsSche.getRb07().equals("1") ? ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_pregnant_woman_24) : ContextCompat.getDrawable(mContext, R.drawable.ic_girl));


        fMaritalStatus.setText(wifeOrDaughter + followUpsSche.getRb03());
        if(MainApp.fpMwra.getMemberType().equals("1")) {
            mStatus.setVisibility(View.VISIBLE);
            secStatus.setText(pregStatus);
        }else{
            mStatus.setVisibility(View.VISIBLE);
        }

        secDob.setText(followUpsSche.getRb04());
        secGender.setText(followUpsSche.getRc12());


        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.fpMwra = MainApp.followUpsScheMWRAList.get(viewHolder.getAdapterPosition());
            MainApp.followups.populateMeta();

            Intent intent = new Intent(mContext, SectionCxActivity.class);

            intent.putExtra("position", viewHolder.getAdapterPosition());

            MainApp.selectedFemale = viewHolder.getAdapterPosition();

            intent.putExtra("position", viewHolder.getAdapterPosition());

            ((Activity) mContext).startActivityForResult(intent, 2);


        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.member_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return followupsSche.size();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fName;
        private final TextView fAge;
        private final TextView fMatitalStatus;
        private final TextView secStatus;
        private final TextView secDob;
        private final TextView secGender;
        private final ImageView fmRow;
        private final ImageView indicator;
        private final LinearLayout mstatus;


        public ViewHolder(View v) {
            super(v);
            fName = v.findViewById(R.id.hh02);
            fAge = v.findViewById(R.id.hh05);
            fMatitalStatus = v.findViewById(R.id.hh06);
            secStatus = v.findViewById(R.id.secStatus);
            secDob = v.findViewById(R.id.secDob);
            secGender = v.findViewById(R.id.secGender);
            fmRow = v.findViewById(R.id.fmRow);
            indicator = v.findViewById(R.id.indicator);
            mstatus = v.findViewById(R.id.mstatus);

        }

        public TextView getTextView() {
            return fName;
        }
    }


}
