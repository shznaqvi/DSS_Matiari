package edu.aku.hassannaqvi.dss_matiari.global;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import edu.aku.hassannaqvi.dss_matiari.R;

public class ConnectionDetector {

    private final Activity activity;

    public ConnectionDetector(Activity activity) {
        this.activity = activity;
    }

    public boolean hasInternetConnection() {
        ConnectivityManager connectivity =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkCapabilities capabilities;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Check wi-fi or mobile data first
//            NetworkInfo activeNetworkInfo = connectivity.getActiveNetworkInfo();
//            if (!activeNetworkInfo.isConnected()) {
//                return false;
//            }

            Network network = connectivity.getActiveNetwork();
            capabilities = connectivity
                    .getNetworkCapabilities(network);
            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        }
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
//            Network[] networks = connectivity.getAllNetworks();
//            NetworkInfo networkInfo;
//            for (Network mNetwork : networks) {
//                networkInfo = connectivity.getNetworkInfo(mNetwork);
//                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
//                    return true;
//                }
//            }
//        }
        else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Show simple no internet alert
    public void showNoInternetDialog() {
        Toast.makeText(activity, activity.getString(R.string.network_error), Toast.LENGTH_LONG).show();

    }

    // Show no internet alert and allows the app to exit
    public void showNoInternetDialogExit() {

        Toast.makeText(activity, activity.getString(R.string.network_error), Toast.LENGTH_LONG).show();
    }

    Callbacks.IAlertCallback callback = (popupId, isOkClick, text) -> {
        System.exit(0);
    };

}
