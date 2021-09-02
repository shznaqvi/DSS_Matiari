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

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.MWRA;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionBActivity;


public class MwraAdapter extends RecyclerView.Adapter<MwraAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<MWRA> mwras;
    private final int mExpandedPosition = -1;
    private final int completeCount;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param mwras List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public MwraAdapter(Context mContext, List<MWRA> mwras) {
        this.mwras = mwras;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;


    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position1) {
        int position = viewHolder.getAdapterPosition();
        Log.d(TAG, "Element " + position + " set.");
        MWRA mwra = this.mwras.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView fName = viewHolder.fName;
        TextView fAge = viewHolder.fAge;
        TextView fMaritalStatus = viewHolder.fMatitalStatus;

        String pregStatus = mwra.getRb07().equals("1") ? "Pregnant" : "Not Pregnant";

        MainApp.fmComplete = completeCount == MainApp.mwraCount;

        fName.setText(mwra.getRb02());
        //fAge.setText(mwra.getRb05() + "y | " + mwra.getRb03());
        fAge.setText("w/o " + mwra.getRb03() + " | " + mwra.getRb05() + "y  ");
        String marStatus = "";
        switch (mwra.getRb06()) {
            case "1":
                marStatus = "Married";
                break;
            case "2":
                marStatus = "Divorced";
                break;
            case "3":
                marStatus = "Widow";
                break;
            case "4":
                marStatus = "Unmarried";
                break;
            default:
                marStatus = "Value Unknown";
                break;
        }

        fMaritalStatus.setText(marStatus + " | " + pregStatus);

        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.mwra = MainApp.mwraList.get(position);
            Intent intent = new Intent(mContext, SectionBActivity.class);

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
        private final View indicator;


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
