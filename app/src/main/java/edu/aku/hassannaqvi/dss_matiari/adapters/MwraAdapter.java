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
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;


public class MwraAdapter extends RecyclerView.Adapter<MwraAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<Mwra> mwras;
    private final int mExpandedPosition = -1;
    private final int completeCount;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param mwras List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public MwraAdapter(Context mContext, List<Mwra> mwras) {

        this.mwras = mwras;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position1) {
        int position = viewHolder.getAdapterPosition();
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view with that element
        Mwra mwra = this.mwras.get(position);

        TextView fName = viewHolder.fName;
        TextView fAge = viewHolder.fAge;
        TextView fMaritalStatus = viewHolder.fMatitalStatus;
        ImageView indicator = viewHolder.indicator;
        TextView secStatus = viewHolder.secStatus;
        TextView secDob = viewHolder.secDob;
        TextView secGender = viewHolder.secGender;

        MainApp.fmComplete = completeCount == MainApp.mwraCount;

        // Set values in item
        String pregStatus = "";
        String marStatus = "";
        String wifeOrDaughter = "";

        switch (mwra.getSB().getRb06()) {
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
        }

        if (mwra.getSB().getRb07().equals("1")) {
            pregStatus = "Pregnant";
            secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.redLight));
            indicator.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_pregnant_woman_24));
        }else {
            pregStatus = "Not Pregnant";
        }

        fName.setText(mwra.getSB().getRb02());
        fAge.setText(wifeOrDaughter + mwra.getSB().getRb03());
        secDob.setText(mwra.getSB().getRb05() + " Y");
        secGender.setText("Female");
        fMaritalStatus.setText(marStatus);
        secStatus.setText(pregStatus);


        // On Item Click
        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.mwra = MainApp.mwraList.get(position);
            Intent intent = new Intent(mContext, SectionBActivity.class);

            intent.putExtra("position", position);

            MainApp.selectedMember = position;

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
        return mwras.size();
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
        private final TextView secDob;
        private final TextView secGender;


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

        }

        public TextView getTextView() {
            return fName;
        }
    }




}
