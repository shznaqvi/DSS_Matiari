package edu.aku.hassannaqvi.dss_matiari.adapters;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedHhNO;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedVillage;

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
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;
import edu.aku.hassannaqvi.dss_matiari.models.FPHouseholds;
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.models.Households;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPHouseholdActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.lists.FPMwraActivity;
import edu.aku.hassannaqvi.dss_matiari.ui.sections.SectionAActivity;


public class FPHouseholdAdapter extends RecyclerView.Adapter<FPHouseholdAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final Context mContext;
    private final List<FollowUpsSche> followUpsScheList;
    private final int mExpandedPosition = -1;
    private final int completeCount;
    private final DatabaseHelper db;
    private FPHouseholds fpHouseholds;
    private Households households;
    int totalMWRA = 0;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {

        Log.d(TAG, "Element " + viewHolder.getAdapterPosition() + " set.");
        FollowUpsSche followUpsSche = this.followUpsScheList.get(viewHolder.getAdapterPosition());        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView hhNo = viewHolder.hhNo;
        TextView hhHead = viewHolder.hhHead;
        TextView mwraCount = viewHolder.mwraCount;
        TextView secStatus = viewHolder.secStatus;
        TextView prvStatus = viewHolder.prvStatus;
        ImageView imgStatus = viewHolder.imgStatus;

        String pregStatus = followUpsSche.getRb07() + " Pregnant";

        //MainApp.fmComplete = completeCount == MainApp.formCount;
        try {
            fpHouseholds = db.getFPHouseholdBYHdssid(followUpsSche.getHdssid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // These quick fixes are making this code a mess.
            this.households = db.getHouseholdByHDSSID(followUpsSche.getHdssid());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.totalMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(followUpsSche.getUcCode(), followUpsSche.getVillageCode(), followUpsSche.getHhNo());


/*if (!followUpsSche.getiStatus().equals("1"))
    fpHouseholds.setiStatus(followUpsSche.getiStatus());*/

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
                secStatus.setVisibility(View.GONE);
                break;
            default:
                hhStatus = "Other";
                break;
        }

        String hhPrvStatus = "";
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

/*
{"ra01":"2021-08-23","ra02":"","ra04":"","ra03":"","ra05":"","ra07":"9001","ra06":"9","ra08":"asd","ra09":"2","ra10":"1","ra11":"96","ra11x":"ghg","ra12":"96","ra12x":"vgv","ra13":"","ra13x":"","ra14":"head","ra15":"resp","ra16":"2","ra17_a":"1","ra17_b":"1","ra17_c":"1","ra17_d":"1","ra18":"1"}
        fMaritalStatus.setText(marStatus + " | " + pregStatus);*/
        //int totalMWRA = Integer.parseInt(followUpsSche.getRb01().equals("") || followUpsSche.getRb01().equals("null") ? "0" : String.valueOf(totalMwra));
        //int totalMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);

        /*maxMWRA = db.getMaxMWRSNoBYHH(selectedUC, selectedVillage, selectedHhNO);
        maxFpMWRA = db.getMaxMWRANoBYHHFromFolloupsSche(selectedUC, selectedVillage, selectedHhNO);
        totalMWRA= Math.max(maxMWRA, maxFpMWRA);*/


        hhNo.setText(followUpsSche.getVillageCode() + "-" + followUpsSche.getHhNo());

        if(totalMWRA == 0)
        {
            hhHead.setVisibility(View.GONE);
            prvStatus.setText("NO WRA");
            prvStatus.setVisibility(View.VISIBLE);
        }else{
            if(followUpsSche.getiStatus().equals("1")){
                prvStatus.setVisibility(View.GONE);
                hhHead.setText(followUpsSche.getRa14());
                hhHead.setVisibility(View.VISIBLE);
            }else{
                hhHead.setVisibility(View.GONE);
                prvStatus.setText(hhPrvStatus);
                prvStatus.setVisibility(View.VISIBLE);
            }
        }

        /*if (followUpsSche.getiStatus().equals("1")) {
            prvStatus.setVisibility(View.GONE);
            hhHead.setText(followUpsSche.getRa14());
            hhHead.setVisibility(View.VISIBLE);
        } else if(!followUpsSche.getiStatus().equals("1") ) {

            hhHead.setVisibility(View.GONE);
            prvStatus.setText(hhPrvStatus);
            prvStatus.setVisibility(View.VISIBLE);
        }else if(totalMWRA == 0){
            hhHead.setVisibility(View.GONE);
            prvStatus.setText("NO WRA");
            prvStatus.setVisibility(View.VISIBLE);
        }*/


        mwraCount.setText(totalMWRA + " Women | " + pregStatus);
        secStatus.setText(hhStatus);
        imgStatus.setVisibility(fpHouseholds.getiStatus().equals("1") || Integer.parseInt(fpHouseholds.getVisitNo()) > 2 ? View.VISIBLE : View.GONE);
        secStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grayDark));

        if (!fpHouseholds.getiStatus().equals("1")) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current state of the item

                    try {
                        MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(viewHolder.getAdapterPosition()).getHdssid());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (MainApp.fpHouseholds.getUid().equals(""))
                        MainApp.fpHouseholds.populateMeta(viewHolder.getAdapterPosition());

           /*     MainApp.fpHouseholds.setVisitNo(String.valueOf(Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) + 1));
                mContext.startActivity(new Intent(mContext, FPMwraActivity.class));*/

                    if (!MainApp.fpHouseholds.getiStatus().equals("1") && Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) < 3) {

                        if (followUpsSche.getiStatus().equals("1") && totalMWRA > 0) {
                            editHousehold(viewHolder.getAdapterPosition());
                        } else if(!followUpsSche.getiStatus().equals("1") || totalMWRA == 0) {
                            try {
                                MainApp.households = db.getHouseholdByHDSSID(followUpsSche.getHdssid());

                                if (MainApp.households == null) {
                                    MainApp.households = new Households();

                                    MainApp.households.setUcCode(selectedUC);
                                    MainApp.households.setVillageCode(selectedVillage);
                                    MainApp.households.setRa09(followUpsSche.getHhNo());
                                }
                                MainApp.selectedHhNO = followUpsSche.getHhNo();
                                MainApp.position = viewHolder.getAdapterPosition();
                                if (!MainApp.households.getiStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {

                                    Intent intent = new Intent(mContext, SectionAActivity.class);
                                    ((FPHouseholdActivity) mContext).MemberInfoLauncher.launch(intent);
                                } else {
                                    Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                                }


                                //  mContext.startActivity(new Intent(mContext, SectionAActivity.class));
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
            //viewHolder.itemView.setOnClickListener(v -> {
                /*// Get the current state of the item

                try {
                    MainApp.fpHouseholds = db.getFPHouseholdBYHdssid(MainApp.followUpsScheHHList.get(viewHolder.getAdapterPosition()).getHdssid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (MainApp.fpHouseholds.getUid().equals(""))
                    MainApp.fpHouseholds.populateMeta(viewHolder.getAdapterPosition());

           *//*     MainApp.fpHouseholds.setVisitNo(String.valueOf(Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) + 1));
                mContext.startActivity(new Intent(mContext, FPMwraActivity.class));*//*

                if (!MainApp.fpHouseholds.getiStatus().equals("1") && Integer.parseInt(MainApp.fpHouseholds.getVisitNo()) < 3) {

                    if (followUpsSche.getiStatus().equals("1") && totalMWRA > 0) {
                        editHousehold(viewHolder.getAdapterPosition());
                    } else if(!followUpsSche.getiStatus().equals("1") || totalMWRA == 0) {
                        try {
                            MainApp.households = db.getHouseholdByHDSSID(followUpsSche.getHdssid());

                            if (MainApp.households == null) {
                                MainApp.households = new Households();

                                MainApp.households.setUcCode(selectedUC);
                                MainApp.households.setVillageCode(selectedVillage);
                                MainApp.households.setRa09(followUpsSche.getHhNo());
                            }
                            MainApp.selectedHhNO = followUpsSche.getHhNo();
                            MainApp.position = viewHolder.getAdapterPosition();
                            if (!MainApp.households.getiStatus().equals("1") && Integer.parseInt(MainApp.households.getVisitNo()) < 3) {

                                Intent intent = new Intent(mContext, SectionAActivity.class);
                                ((FPHouseholdActivity) mContext).MemberInfoLauncher.launch(intent);
                            } else {
                                Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                            }


                            //  mContext.startActivity(new Intent(mContext, SectionAActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "JSONException(households):" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(mContext, "Follow-Up for this household has been locked", Toast.LENGTH_LONG).show();
                }*/
            //});
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
            hhHead = v.findViewById(R.id.ra14);
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
