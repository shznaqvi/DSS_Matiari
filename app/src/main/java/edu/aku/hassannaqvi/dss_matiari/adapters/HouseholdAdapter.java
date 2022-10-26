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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<Households> households;
    private final int mExpandedPosition = -1;
    private final int completeCount;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param households List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public HouseholdAdapter(Context mContext, List<Households> households) {
        this.households = households;
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int posit) {
        Log.d(TAG, "Element " + viewHolder.getAdapterPosition() + " set.");
        Households households = this.households.get(viewHolder.getAdapterPosition());        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView hhNo = viewHolder.hhNo;
        TextView hhHead = viewHolder.hhHead;
        TextView mwraCount = viewHolder.mwraCount;
        TextView secStatus = viewHolder.secStatus;
        ImageView imgStatus = viewHolder.imgStatus;


        String hhStatus = "";
        switch (households.getIStatus()) {
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
            default:
                hhStatus = "Other";
                break;
        }

        DatabaseHelper db = MainApp.appInfo.dbHelper;
        //int totalMWRA = db.getMWRACountBYUUID(households.getUid());
        int totalMWRA = DssRoomDatabase.getDbInstance().mwraDao().getMWRACountBYUUID(households.getUid());

        try {
            hhNo.setText(getStringFromJson(households.getSA(), "ra07") + "-" + getStringFromJson(households.getSA(), "ra09"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hhHead.setText(households.getRa14());
        mwraCount.setText(totalMWRA + " Women");
        secStatus.setText(hhStatus);
        imgStatus.setVisibility(households.getIStatus().equals("1") || Integer.parseInt(households.getVisitNo()) > 2 ? View.VISIBLE : View.GONE);
        secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grayDark));


        viewHolder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            MainApp.households = MainApp.householdList.get(viewHolder.getAdapterPosition());
            if (!MainApp.households.getIStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {

                editHousehold(viewHolder.getAdapterPosition());

            } else {
                Toast.makeText(mContext, "This households has been locked. You cannot edit household for locked households", Toast.LENGTH_LONG).show();
            }


        });

    }

    private void editHousehold(int position) {
        Intent intent = new Intent(mContext, SectionAActivity.class);
        intent.putExtra("position", position);
        MainApp.selectedHousehold = position;
        MainApp.selectedHhNO = MainApp.householdList.get(position).getHhNo();
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
        return households.size();
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


        public ViewHolder(View v) {
            super(v);
            hhNo = v.findViewById(R.id.ra09);
            hhHead = v.findViewById(R.id.ra14);
            mwraCount = v.findViewById(R.id.mwraCount);
            secStatus = v.findViewById(R.id.secStatus);
            imgStatus = v.findViewById(R.id.imgStatus);

        }

        public TextView getTextView() {
            return hhHead;
        }
    }

    public String getStringFromJson(String jsonStr, String parseString) throws JSONException {
        String string = jsonStr;

        JSONObject jsonObject = new JSONObject(jsonStr);
        String outputString = jsonObject.getString(parseString);
        String[] stringParts = outputString.split(" ");
        string = stringParts[0];

        return string;
    }



}
