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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
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
            int daysdiff  = MainApp.mwra.CalculateAge(followUpsSche.getRa01());
            int years = daysdiff /365;

            if(!followUpsSche.getRb05().equals("")) {
                int actualAge = Integer.parseInt(followUpsSche.getRb05()) + years;
                fAge.setText(marStatus + " | " + actualAge  + "y  ");
            }



            secStatus.setBackgroundColor(followUpsSche.getRb07().equals("1") ? ContextCompat.getColor(mContext, R.color.redLight) : ContextCompat.getColor(mContext, R.color.grayLight));
            indicator.setImageDrawable(followUpsSche.getRb07().equals("1") ? ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_pregnant_woman_24) : ContextCompat.getDrawable(mContext, R.drawable.ic_girl));

        }else{
            // Calculate age from DOB of child
            indicator.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baby));

            int days = MainApp.mwra.CalculateAge(followUpsSche.getRb04());

            double months = days/30.41;

            fAge.setText(months + "m");


            /*try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Date date = sdf.parse(followUpsSche.getRb04());

                // set current Date
                Calendar cur = Calendar.getInstance();
                cur.setTime(cur.getTime());// all done

                // set DOB
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                long millis = cur.getTimeInMillis() - cal.getTimeInMillis();
                cal.setTimeInMillis(millis);

                Calendar c = Calendar.getInstance();

                c.setTimeInMillis(millis);
                int tYear = c.get(Calendar.YEAR) - 1970;
                int tMonth = c.get(Calendar.MONTH);
                int months = tMonth+tYear*12;
                //int tDay = c.get(Calendar.DAY_OF_MONTH);

                fAge.setText(months +"m");


        } catch (ParseException e) {
            Log.d(TAG, "CaluculateAge: " + e.getMessage());
            e.printStackTrace();

        }*/

        }
        fMaritalStatus.setText(wifeOrDaughter + followUpsSche.getRb03());
        secStatus.setText(pregStatus);
        secDob.setText(followUpsSche.getRb04());
        secGender.setText(followUpsSche.getRc12().equals("2") ? " Female" : "male");


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
