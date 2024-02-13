package edu.aku.hassannaqvi.dss_matiari.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.Callbacks;

public class AlertPopup {

    // Simple Alert Dialog
    public static void alert(Activity activity, String title, String message, int type) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.view_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        TextView titleTV = dialogView.findViewById(R.id.titleTV);
        titleTV.setText(title);

        setDialogViewByType(activity, titleTV, type);

        TextView dialogMessageTV = dialogView.findViewById(R.id.messageTV);
        dialogMessageTV.setText(message);

        Button positiveBtn = dialogView.findViewById(R.id.posBtn);
        positiveBtn.setVisibility(View.VISIBLE);

        Button negativeBtn = dialogView.findViewById(R.id.negBtn);
        negativeBtn.setVisibility(View.GONE);

        // To prevent error on show dialog after activity is finishing or destroyed
        if (activity.isFinishing() || activity.isDestroyed()) return;

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positiveBtn.setOnClickListener(view -> {
            dialog.cancel();
        });

        dialog.show();
    }

    // Simple Alert Dialog with Single Custom Action
    public static void alert(int popupId, Activity activity, String title, String message,
                             int type, String positiveBtnText,
                             Callbacks.IAlertCallback iAlertCallback) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.view_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView titleTV = dialogView.findViewById(R.id.titleTV);
        titleTV.setText(title);

        setDialogViewByType(activity, titleTV, type);

        TextView dialogMessageTV = dialogView.findViewById(R.id.messageTV);
        dialogMessageTV.setText(message);

        Button positiveBtn = dialogView.findViewById(R.id.posBtn);
        positiveBtn.setText(positiveBtnText);
        positiveBtn.setVisibility(View.VISIBLE);

        Button negativeBtn = dialogView.findViewById(R.id.negBtn);
        negativeBtn.setVisibility(View.GONE);

        // To prevent error on show dialog after activity is finishing or destroyed
        if (activity.isFinishing() || activity.isDestroyed()) return;

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positiveBtn.setOnClickListener(view -> {
            dialog.cancel();
            iAlertCallback.onClick(popupId, true, null);
        });

        dialog.show();
    }

    // Simple Alert Dialog with Multiple Choice
    public static void alert(int popupId, Activity activity, String title, String message,
                             int type, String positiveBtnText, String negativeBtnText,
                             Callbacks.IAlertCallback iAlertCallback) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.view_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView titleTV = dialogView.findViewById(R.id.titleTV);
        titleTV.setText(title);

        setDialogViewByType(activity, titleTV, type);

        TextView dialogMessageTV = dialogView.findViewById(R.id.messageTV);
        dialogMessageTV.setText(message);

        Button positiveBtn = dialogView.findViewById(R.id.posBtn);
        positiveBtn.setText(positiveBtnText);
        positiveBtn.setVisibility(View.VISIBLE);

        Button negativeBtn = dialogView.findViewById(R.id.negBtn);
        negativeBtn.setText(negativeBtnText);
        negativeBtn.setVisibility(View.VISIBLE);

        // To prevent error on show dialog after activity is finishing or destroyed
        if (activity.isFinishing() || activity.isDestroyed()) return;

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positiveBtn.setOnClickListener(view -> {
            dialog.cancel();
            iAlertCallback.onClick(popupId, true, null);
        });

        negativeBtn.setOnClickListener(view -> {
            dialog.cancel();
            iAlertCallback.onClick(popupId, false, null);
        });

        dialog.show();
    }

    // Set Alert dialog UI based on the type
    private static void setDialogViewByType(Activity activity, TextView titleTV, int type) {
        if (type == AppConstants.TYPE_SUCCESS)
            titleTV.setTextColor(ContextCompat.getColor(activity, R.color.success_color));
        else if (type == AppConstants.TYPE_ERROR)
            titleTV.setTextColor(ContextCompat.getColor(activity, R.color.error_color));
        else if (type == AppConstants.TYPE_WARNING)
            titleTV.setTextColor(ContextCompat.getColor(activity, R.color.warning_color));
        else
            titleTV.setTextColor(ContextCompat.getColor(activity, R.color.info_color));
    }

}
