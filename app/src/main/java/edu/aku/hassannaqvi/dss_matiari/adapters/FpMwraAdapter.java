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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionC1Activity;


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
    public void onBindViewHolder(ViewHolder viewHolder, int position1) {
        int position = viewHolder.getAdapterPosition();
        Log.d(TAG, "Element " + position + " set.");
        FollowUpsSche followUpsSche = this.followupsSche.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView fName = viewHolder.fName;
        TextView fAge = viewHolder.fAge;
        TextView fMaritalStatus = viewHolder.fMatitalStatus;
        ImageView indicator = viewHolder.indicator;
        TextView secStatus = viewHolder.secStatus;

        String pregStatus = followUpsSche.getRb07().equals("1") ? "Pregnant" : "Not Pregnant";

        MainApp.fmComplete = completeCount == MainApp.mwraCount;

        fName.setText(followUpsSche.getRb02());
        //fAge.setText(mwra.getRb05() + "y | " + mwra.getRc03());
        String marStatus = "";
        String wifeOrDaughter = "";


        if (!followUpsSche.getfpDoneDt().equals("")) {
            indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_700));
        }
/*        switch (followUpsSche.getRb06()) {
            case "1":
                marStatus = "Married";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.redDark));
                wifeOrDaughter = "w/o ";
                break;
            case "2":
                marStatus = "Divorced";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_700));
                wifeOrDaughter = "w/o ";

                break;
            case "3":
                marStatus = "Widow";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_200));
                wifeOrDaughter = "w/o ";

                break;
            case "4":
                marStatus = "Unmarried";
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightPink));
                wifeOrDaughter = "d/o ";

                break;
            default:
                marStatus = "Value Unknown";
                break;
        }*/

        fAge.setText(wifeOrDaughter + followUpsSche.getRb03() + " | " + followUpsSche.getRb05() + "y  ");

        if (followUpsSche.getRb07().equals("1")) {
            secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.redLight));
            indicator.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_pregnant_woman_24));

        }
        fMaritalStatus.setText(marStatus);
        secStatus.setText(pregStatus);


        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.fpMwra = MainApp.followUpsScheMWRAList.get(position);
            MainApp.followups.populateMeta();

            Intent intent = new Intent(mContext, SectionC1Activity.class);

            intent.putExtra("position", position);

            MainApp.selectedFemale = position;

            intent.putExtra("position", position);

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
        private final ImageView fmRow;
        private final ImageView indicator;


        public ViewHolder(View v) {
            super(v);
            fName = v.findViewById(R.id.hh02);
            fAge = v.findViewById(R.id.hh05);
            fMatitalStatus = v.findViewById(R.id.hh06);
            secStatus = v.findViewById(R.id.secStatus);
            fmRow = v.findViewById(R.id.fmRow);
            indicator = v.findViewById(R.id.indicator);

        }

        public TextView getTextView() {
            return fName;
        }
    }


}