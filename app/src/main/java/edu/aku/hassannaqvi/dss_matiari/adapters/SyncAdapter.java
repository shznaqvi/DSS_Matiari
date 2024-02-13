package edu.aku.hassannaqvi.dss_matiari.adapters;

import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.databinding.ItemSyncBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.Callbacks;
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew;

public class SyncAdapter extends RecyclerView.Adapter<SyncAdapter.ViewHolder> {
    private final Activity activity;
    private List<SyncModelNew> mainList;
    private final Callbacks.IRVOnItemClickListener onItemClickListener;

    public SyncAdapter(Activity activity, List<SyncModelNew> mainList, Callbacks.IRVOnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.mainList = mainList;
        this.onItemClickListener = onItemClickListener;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSyncBinding binding;

        public ViewHolder(ItemSyncBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSyncBinding itemView = ItemSyncBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemSyncBinding bi = holder.binding;
        SyncModelNew syncData = mainList.get(position);
        int statusId = syncData.getStatusId();
        String tableName = syncData.getTable().toUpperCase();
        String sectionName = syncData.getTableSections();

        bi.loading.setVisibility(View.VISIBLE);
        setStatus(statusId, bi.statusColorView, bi.messageTV, bi.loading);
        bi.tableNameTV.setText(tableName);
        bi.statusTV.setText(syncData.getStatus());
        bi.messageTV.setText(syncData.getMessage());
        bi.sectionTV.setText(AppConstants.isEmpty(sectionName) ? _EMPTY_ : sectionName);
    }

    private void setStatus(int statusId, View statusColorView, TextView messageTV, ProgressBar loading) {
        if (statusId == 1) {
            // Success
            statusColorView.setBackgroundColor(Color.GREEN);
            messageTV.setText(activity.getString(R.string.successful));
            loading.setVisibility(View.GONE);
        } else if (statusId == 2) {
            // Error
            statusColorView.setBackgroundColor(Color.RED);
            messageTV.setText(activity.getString(R.string.process_failed));
            loading.setVisibility(View.GONE);
        } else {
            // Not Processed
            statusColorView.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

}