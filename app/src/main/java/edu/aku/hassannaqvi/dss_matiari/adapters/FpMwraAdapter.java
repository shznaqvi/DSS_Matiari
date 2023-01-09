package edu.aku.hassannaqvi.dss_matiari.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionFActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionCActivity;


public class FpMwraAdapter extends RecyclerView.Adapter<FpMwraAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<FollowUpsSche> followupsSche;
    private final int mExpandedPosition = -1;
    private final int completeCount;
    private final DssRoomDatabase db;

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
            //String curPregStatus = db.getFollowupsBySno(followUpsSche.getRb01(), followUpsSche.getFRound()).getRc07();
            String curPregStatus = db.mwraDao().getFollowupsBySno(MainApp.households.getUid(), followUpsSche.getRb01(), followUpsSche.getFRound()).getRb07();
            if (!curPregStatus.equals("")) {
                pregStatus = curPregStatus.equals("1") ? "Pregnant" : " Not Pregnant ";

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        // MWRA Count
        MainApp.fmComplete = completeCount == MainApp.mwraCount;

        // Name
        fName.setText(followUpsSche.getRb02());
        String marStatus = "";
        String wifeOrDaughter = "";
        if(followUpsSche.getMemberType().equals("1")) {
            wifeOrDaughter =followUpsSche.getRb06().equals("4") ? " d/o " : " w/o ";

        }else{
           wifeOrDaughter = " c/o ";
        }

        indicator.setBackgroundColor(!followUpsSche.getfpDoneDt().equals("") ? ContextCompat.getColor(mContext, R.color.greenLight) : ContextCompat.getColor(mContext, R.color.colorAccent));

        // Marital Status
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
                indicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.purple_200));
                break;
        }

        // Age according to system date

        if(followUpsSche.getMemberType().equals("1")) {

            // Age
            long daysdiff  = MainApp.mwra.CalculateAge(followUpsSche.getRa01());
            long years = daysdiff/365;
            long actualAge = 0;

            if(!followUpsSche.getRb05().equals("")) {
                actualAge = Long.parseLong(followUpsSche.getRb05()) + years;
                fAge.setText(marStatus + " | " + actualAge  + "y  ");
            }

            if(actualAge > 49) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#AF1B12"));
                viewHolder.secStatus.setText("OverAge");
            }else{
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            secStatus.setBackgroundColor(followUpsSche.getRb07().equals("1") ? ContextCompat.getColor(mContext, R.color.redLight) : ContextCompat.getColor(mContext, R.color.grayLight));
            indicator.setImageDrawable(followUpsSche.getRb07().equals("1") ? ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_pregnant_woman_24) : ContextCompat.getDrawable(mContext, R.drawable.ic_girl));

        }else{
            // Calculate age from DOB of child
            indicator.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baby));

            if(!followUpsSche.getRb04().equals("")) {
                long days = MainApp.mwra.CalculateAge(followUpsSche.getRb04());

                long months = days/30;

                fAge.setText(months + "m");
            }


        }
        fMaritalStatus.setText(wifeOrDaughter + followUpsSche.getRb03());
        secStatus.setText(pregStatus);
        secDob.setText(followUpsSche.getRb04());
        secGender.setText(followUpsSche.getRc04().equals("2") ? " Female" : "male");


        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item


            if(followUpsSche.getMemberType().equals("1")) {
                MainApp.fpMwra = MainApp.followUpsScheMWRAList.get(viewHolder.getAdapterPosition());
                MainApp.mwra.populateMetaFollowups();

                Intent intent = new Intent(mContext, SectionCActivity.class);

                intent.putExtra("position", viewHolder.getAdapterPosition());

                MainApp.selectedMember = viewHolder.getAdapterPosition();

                intent.putExtra("position", viewHolder.getAdapterPosition());


                ((Activity) mContext).startActivityForResult(intent, 2);
            }else{
                MainApp.fpMwra = MainApp.followUpsScheMWRAList.get(viewHolder.getAdapterPosition());
                MainApp.outcome.populateMetaFollowups();

                Intent intent = new Intent(mContext, SectionFActivity.class);

                intent.putExtra("position", viewHolder.getAdapterPosition());

                MainApp.selectedMember = viewHolder.getAdapterPosition();

                intent.putExtra("position", viewHolder.getAdapterPosition());


                ((Activity) mContext).startActivityForResult(intent, 2);

            }


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
