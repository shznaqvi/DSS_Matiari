package edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.ConnectionDetector;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebCall {

    private final Activity activity;
    private final ConnectionDetector connDetector;
    private final IWebCallback iWebCallback;

    private boolean isPopupShow;

    public WebCall(Activity activity, IWebCallback iWebCallback) {
        this.activity = activity;
        connDetector = new ConnectionDetector(activity);
        this.iWebCallback = iWebCallback;
    }

    // Overloading
    public void call(Call<String> call, int syncType, String tag, int index, int total, boolean isEncrypted) {
        call(call, syncType, tag, index, total, null, isEncrypted);
    }

    // 'Tag' is used to differentiate between multiple calls
    // GET Web Call
    public void call(Call<String> call, int syncType, String tag, int index, int total, List<String> list, boolean isEncrypted) {
        if (connDetector.hasInternetConnection()) {
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.body() != null) {

                        // Check if device date is correct
                        // We need this to only show date error while downloading data.
                        // Uploading data and uploading photos won't get affected
                        List<String> datesList = DateUtils.checkServerAndDeviceDate(response.headers().get("Date"), AppConstants.SERVER_DATE_TIME_FORMAT);
                        if (!datesList.isEmpty() && syncType == AppConstants.DOWNLOAD_DATA) {
                            // Device date is incorrect
                            // '0' index = Server Date
                            // '1' index = System Date
                            String dateError = String.format(activity.getString(R.string.incorrect_device_date),
                                    datesList.get(0), datesList.get(1));
                            iWebCallback.onFailure(tag, dateError, index, total, list);
                            return;
                        }

                        String jsonResponse;
                        if (isEncrypted)
                            jsonResponse = CryptoUtil.decrypt(response.body());
                        else jsonResponse = response.body();
                        Log.e("RESPONSE", response.body());
                        String errorMessage = checkForError(jsonResponse, response.body());
                        if (AppConstants.isEmpty(errorMessage))
                            iWebCallback.onSuccess(tag, jsonResponse, index, total, list);
                        else
                            iWebCallback.onFailure(tag, errorMessage, index, total, list);
                    } else
                        // This is the case when call code is 200 and response body is null but call is not successful
                        iWebCallback.onFailure(tag, call.request().url() + ": " + response.message(), index, total, list);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    iWebCallback.onFailure(tag, t.getMessage(), index, total, list);
                }
            });
        } else {
            if (!isPopupShow) {
                isPopupShow = true;
                connDetector.showNoInternetDialog();
            }
            iWebCallback.onFailure(tag, activity.getString(R.string.network_error), index, total, list);
        }
    }

    /*For checking if response is valid*/
    private String checkForError(String jsonResponse, String rawResponse) {
        Gson gson = new Gson();
        if (!AppConstants.isEmpty(jsonResponse)) {
            try {
                if (AppConstants.isJSONArrayValid(jsonResponse)) {
                    SyncModelNew.WebResponse[] responses = gson.fromJson(jsonResponse, SyncModelNew.WebResponse[].class);
                    StringBuilder errors = new StringBuilder();
                    for (SyncModelNew.WebResponse response : responses) {
                        if (response.getError() != 0) {
                            // Response is NOT a success
                            errors.append("\n").append(response.getMessage());
                        }
                    }
                    return errors.toString();
                } else if (AppConstants.isJSONObjectValid(jsonResponse)) {
                    SyncModelNew.WebResponse response = gson.fromJson(jsonResponse, SyncModelNew.WebResponse.class);
                    if (response.getError() != 0)
                        // Response is NOT a success
                        return response.getMessage();
                }
            } catch (Exception e) {
                return /*activity.getString(R.string.somethings_not_right);*/rawResponse;
            }
        } else
            // Response is NOT a success
            return /*activity.getString(R.string.somethings_not_right);*/rawResponse;
        // Response is a success
        return null;
    }

    // Web Service callback - Abstraction

    /**
     * Parameters in callback:
     * <p>
     * tag = Name of the table
     * responseBody = Decrypted json response
     * index = The position of item in sync adapter to update on response
     * total = Total count of the response array - Used for POST
     * list = The list of those UIds that have been sent to server - It will be needed if the upload response
     * is a failure then we will mark those records as error for respective table to update it later - Used for POST
     */
    public interface IWebCallback {
        // index is used to update sync data list adapter item
        void onSuccess(String tag, String responseBody, int index, int total, List<String> list);

        void onFailure(String tag, String errorMessage, int index, int total, List<String> list);
    }

}
