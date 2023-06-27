package edu.aku.hassannaqvi.dss_matiari.newstruct.global;

import android.app.Activity;
import android.app.Dialog;

import edu.aku.hassannaqvi.dss_matiari.R;

public class Loading {
    Dialog dialog;
    Activity activity;

    public Loading(Activity activity, boolean isCancelable) {
        this.activity = activity;
        dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.view_loading);
    }

    public void showLoading() {
        dialog.show();
    }

    public void hideLoading() {
        dialog.dismiss();
    }

    public Dialog getLoadingDialog() {
        return dialog;
    }
}
