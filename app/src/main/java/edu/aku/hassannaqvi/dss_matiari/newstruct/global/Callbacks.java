package edu.aku.hassannaqvi.dss_matiari.newstruct.global;

import android.widget.AutoCompleteTextView;

import androidx.recyclerview.widget.RecyclerView;

public class Callbacks {

    // RecyclerView Item Click Callback
    public interface IRVOnItemClickListener {
        void onItemClick(RecyclerView recyclerView, Object obj, int index);
    }

    // ListView Item Click Callback
    public interface ILVOnItemClickListener {
        //        void onItemClick(ListView listView, Object obj, int index);
        void onItemClick(AutoCompleteTextView autoCompleteTextView, Object obj, int index);
    }

    // Alert dialog button click callback
    public interface IAlertCallback {
        void onClick(int popupId, boolean isOkClick, String text);
    }

    // Permissions callback
    public interface IAppPermissions {
        void onPermissionsSuccess(int requestCode);

        void onPermissionFailure(int requestCode);
    }

    /*// QR Code Scanner
    public interface ICodeScannerCallback {
        void scanCode(ScanOptions scanOptions);
    }*/

}
