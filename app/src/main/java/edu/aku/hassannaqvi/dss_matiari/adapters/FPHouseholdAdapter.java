package edu.aku.hassannaqvi.dss_matiari.adapters;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.hhsList;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedVillage;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPHouseholdActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPMwraActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAFupctivity;


public class FPHouseholdAdapter extends RecyclerView.Adapter<FPHouseholdAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<FollowUpsSche> followUpsScheList;
    private final List<FollowUpsSche> backupItems = new ArrayList<>();
    private final int mExpandedPosition = -1;
    private final int completeCount;
    private final DssRoomDatabase db;
    private Households fpHouseholds;
    private Households households;
    private Households hhs;
    HashMap<Integer, Integer> totalMwraMap = new HashMap<>();
    HashMap<Integer, Integer> totalChildMap = new HashMap<>();

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param fpHouseholds List<FemaleMembersModel> containing the data to populate views to be used by RecyclerView.
     */
    public FPHouseholdAdapter(Context mContext, List<FollowUpsSche> fpHouseholds) {
        this.followUpsScheList = fpHouseholds;
        backupItems.clear();
        backupItems.addAll(fpHouseholds);
        this.mContext = mContext;
        completeCount = 0;
        MainApp.fmComplete = false;

        db = MainApp.appInfo.dbHelper;

        Collections.sort(followUpsScheList, new Comparator<FollowUpsSche>() {
            @Override
            public int compare(FollowUpsSche o1, FollowUpsSche o2) {
                return o1.getHdssid().compareTo(o2.getHdssid());
            }
        });

    }


    // Add filter
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.equals("")) {

            // Show original list
            followUpsScheList.clear();
            followUpsScheList.addAll(backupItems);
            notifyDataSetChanged();
        } else {
            followUpsScheList.clear();
            for (FollowUpsSche followUpsSche : backupItems) {
                if (followUpsSche.getHdssid().contains(query) || followUpsSche.getRa12().toLowerCase().contains(query)) {
                    followUpsScheList.add(followUpsSche);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Log.d(TAG, "Element " + viewHolder.getLayoutPosition() + " set.");
        FollowUpsSche followUpsSche = this.followUpsScheList.get(viewHolder.getBindingAdapterPosition());// Get element from your dataset at this position and replace the contents of the view

        // with that element
        int pos = viewHolder.getBindingAdapterPosition();


        TextView hhNo = viewHolder.hhNo;
        TextView hhHead = viewHolder.hhHead;
        TextView mwraCount = viewHolder.mwraCount;
        TextView secStatus = viewHolder.secStatus;
        TextView prvStatus = viewHolder.prvStatus;
        ImageView imgStatus = viewHolder.imgStatus;

        String pregStatus = followUpsSche.getRb07() + " Pregnant";


        try {
            //MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(viewHolder.getAdapterPosition()).getHdssid());
            this.households = db.householdsDao().getHouseholdByHDSSIDASC(MainApp.followUpsScheHHList.get(viewHolder.getLayoutPosition()).getHdssid());
            //this.hhs = db.householdsDao().getHouseholdByHDSSIDASC(hhsList.get(viewHolder.getAdapterPosition()).getHdssid());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            // These quick fixes are making this code a mess.
            //this.fpHouseholds = db.getHouseholdByHDSSID(followUpsSche.getHdssid());
            this.fpHouseholds = db.householdsDao().getHouseholdByHDSSIDDSC(MainApp.followUpsScheHHList.get(viewHolder.getBindingAdapterPosition()).getHdssid(), viewHolder.getBindingAdapterPosition());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //int tempMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(followUpsSche.getUcCode(), followUpsSche.getVillageCode(), followUpsSche.getHhNo());
        int tempMWRA = db.FollowUpsScheDao().getMWRACountBYHHFromFolloupsSche(followUpsSche.getUcCode(), followUpsSche.getVillageCode(), followUpsSche.getHhNo(), "1");
        int childCount = db.FollowUpsScheDao().getChildCountBYHHFromFolloupsSche(followUpsSche.getUcCode(), followUpsSche.getVillageCode(), followUpsSche.getHhNo());
        totalMwraMap.put(pos, tempMWRA);
        totalChildMap.put(pos, childCount);


        String hhStatus;
        switch (fpHouseholds.getIStatus()) {
            case "1":
                hhStatus = "Complete";
                secStatus.setVisibility(View.VISIBLE);
                break;
            case "2":
                hhStatus = "Locked";
                secStatus.setVisibility(View.VISIBLE);
                break;
            case "3":
                hhStatus = "Refused";
                secStatus.setVisibility(View.VISIBLE);
                break;
            case "4":
                hhStatus = "No WRA";
                secStatus.setVisibility(View.VISIBLE);
                break;
            case "96":
                hhStatus = "Other";
                secStatus.setVisibility(View.VISIBLE);
                break;
            case "":
                hhStatus = "       ";
                secStatus.setVisibility(View.GONE);
                break;
            default:
                hhStatus = "Other";
                break;
        }

        String hhPrvStatus;
        switch (followUpsSche.getiStatus()) {
            case "1":
                hhPrvStatus = "Complete";
                break;
            case "2":
                hhPrvStatus = "Locked";
                break;
            case "3":
                hhPrvStatus = "Refused";
                break;
            case "4":
                hhPrvStatus = "No WRA";
                break;
            case "":
                hhPrvStatus = "       ";
                break;
            default:
                hhPrvStatus = "Other";
                break;
        }


        hhNo.setText(followUpsSche.getVillageCode() + "-" + followUpsSche.getHhNo());

        if (tempMWRA == 0 && childCount == 0) {
            hhHead.setVisibility(View.VISIBLE);
            hhHead.setText(followUpsSche.getRa12());
            imgStatus.setVisibility(View.VISIBLE);
            prvStatus.setText("NO WRA AND NO CHILD");
            prvStatus.setVisibility(View.VISIBLE);
        } else {
            if (followUpsSche.getiStatus().equals("1")) {
                prvStatus.setVisibility(View.GONE);
                hhHead.setText(followUpsSche.getRa12());
                hhHead.setVisibility(View.VISIBLE);
            } else {
                imgStatus.setVisibility(View.VISIBLE);
                hhHead.setVisibility(View.GONE);
                prvStatus.setText(hhPrvStatus);
                prvStatus.setVisibility(View.VISIBLE);
            }
        }

        mwraCount.setText(tempMWRA + " Women | " + pregStatus + " | " + childCount + " Children");
        secStatus.setText(hhStatus);

        if (MainApp.ROUND.equals(fpHouseholds.getRound()) && (fpHouseholds.getIStatus().equals("1") || Integer.parseInt(fpHouseholds.getVisitNo()) > 2)) {
            imgStatus.setVisibility(View.VISIBLE);
            imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_members_done));
            DrawableCompat.setTint(DrawableCompat.wrap(imgStatus.getDrawable()), ContextCompat.getColor(mContext, R.color.green));
        } else if (tempMWRA > 0) {
            imgStatus.setVisibility(View.VISIBLE);
            imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_person_edit));
        }
        //imgStatus.setVisibility(fpHouseholds.getIStatus().equals("1") || Integer.parseInt(fpHouseholds.getVisitNo()) > 2 ? View.VISIBLE : View.GONE);
        secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grayDark));

        if (!fpHouseholds.getIStatus().equals("1")) {
            imgStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(viewHolder.getAdapterPosition()).getHdssid());
                        MainApp.households = db.householdsDao().getSelectedHouseholdByHDSSID(MainApp.followUpsScheHHList.get(viewHolder.getLayoutPosition()).getHdssid(), viewHolder.getLayoutPosition());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i <= hhsList.size(); i++) {
                        if (MainApp.households.getHdssId().equals(hhsList.get(i).getHdssid())) {
                            MainApp.selectHHsHousehold = i;
                            break;
                        }
                    }
                    //if (MainApp.households.getUid().equals("")) {
                    MainApp.households.populateMeta(viewHolder.getLayoutPosition());
                    MainApp.households.updateFMData(MainApp.selectHHsHousehold);

                    Intent intent = new Intent(mContext, SectionAFupctivity.class);
                    intent.putExtra("position", position);
                    MainApp.selectedFpHousehold = position;
                    MainApp.selectedHhNO = MainApp.followUpsScheHHList.get(position).getHhNo();
                    intent.putExtra("position", position);

                    ((Activity) mContext).startActivityForResult(intent, 2);
                }
            });
        }

        if (!fpHouseholds.getIStatus().equals("1")) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current state of the item
                    try {
                        //MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(viewHolder.getAdapterPosition()).getHdssid());
                        MainApp.households = db.householdsDao().getSelectedHouseholdByHDSSID(MainApp.followUpsScheHHList.get(viewHolder.getLayoutPosition()).getHdssid(), viewHolder.getLayoutPosition());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i <= hhsList.size(); i++) {
                        if (MainApp.households.getHdssId().equals(hhsList.get(i).getHdssid())) {
                            MainApp.selectHHsHousehold = i;
                            break;
                        }
                    }
                    //if (MainApp.households.getUid().equals("")) {
                    MainApp.households.populateMeta(viewHolder.getLayoutPosition());
                    MainApp.households.updateFMData(MainApp.selectHHsHousehold);
                    //}

                    if (!MainApp.households.getIStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {

                        int currentMWRA = totalMwraMap.containsKey(pos) ? totalMwraMap.get(pos) : 0;
                        int currentChild = totalChildMap.containsKey(pos) ? totalChildMap.get(pos) : 0;
                        //if (!followUpsSche.getiStatus().equals("4") && (currentMWRA > 0 || currentChild > 0)) {
                        if (currentMWRA > 0 || currentChild > 0) {
                            editHousehold(viewHolder.getLayoutPosition());
                        } else if (!followUpsSche.getiStatus().equals("1") || (currentMWRA == 0 && currentChild == 0)) {
                            try {
                                //MainApp.households = db.getHouseholdByHDSSID(followUpsSche.getHdssid());
                                MainApp.households = db.householdsDao().getHouseholdByHDSSIDASC(followUpsSche.getHdssid());

                                if (MainApp.households == null) {
                                    MainApp.households = new Households();

                                    MainApp.households.setUcCode(selectedUC);
                                    MainApp.households.setVillageCode(selectedVillage);
                                    //MainApp.households.setRa09(followUpsSche.getHhNo());
                                }
                                MainApp.households.getSA().setRa09(followUpsSche.getHhNo());
                                MainApp.selectedHhNO = followUpsSche.getHhNo();
                                MainApp.position = viewHolder.getLayoutPosition();
                                if (!MainApp.households.getIStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {
                                    MainApp.households.populateMeta(viewHolder.getLayoutPosition());
                                    MainApp.households.setRegRound("1");
                                    Intent intent = new Intent(mContext, SectionAActivity.class);
                                    ((FPHouseholdActivity) mContext).MemberInfoLauncher.launch(intent);
                                } else {
                                    Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "JSONException(households):" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }


    private void editHousehold(int position) {
        Intent intent = new Intent(mContext, FPMwraActivity.class);
        intent.putExtra("position", position);
        MainApp.selectedFpHousehold = position;
        //MainApp.households.updateFMData();
        MainApp.selectedHhNO = MainApp.followUpsScheHHList.get(position).getHhNo();
        intent.putExtra("position", position);

        ((Activity) mContext).startActivityForResult(intent, 2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.household_row, viewGroup, false);


        return new ViewHolder(v, mContext);
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
        private final TextView prvStatus;
        private final ImageView imgStatus;


        public ViewHolder(View v, Context c) {
            super(v);
            hhNo = v.findViewById(R.id.ra09);
            hhHead = v.findViewById(R.id.ra12);
            mwraCount = v.findViewById(R.id.mwraCount);
            secStatus = v.findViewById(R.id.secStatus);
            prvStatus = v.findViewById(R.id.prvStatus);
            imgStatus = v.findViewById(R.id.imgStatus);

        }

        public TextView getTextView() {
            return hhHead;
        }
    }

}
