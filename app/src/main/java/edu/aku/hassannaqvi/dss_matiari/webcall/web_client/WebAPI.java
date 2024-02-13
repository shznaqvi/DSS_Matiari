package edu.aku.hassannaqvi.dss_matiari.webcall.web_client;

import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebAPI {

    // For app version checking
    String VERSION_OUTPUT_JSON_FILE_PATH = "../app";

    // Download encrypted data
    @POST(AppConstants.API_NAME + "/api2/getDataGCM.php")
    Call<String> downloadEncData(@Body String json);

    // Download unencrypted data
    @POST(AppConstants.API_NAME + "/api2/getData.php")
    Call<String> downloadData(@Body String json);

    // Upload encrypted data
    @POST(AppConstants.API_NAME + "/api2/syncGCM.php")
    Call<String> uploadEncData(@Body String json);

    // Upload unencrypted data
    @POST(AppConstants.API_NAME + "/api2/syncData.php")
    Call<String> uploadData(@Body String json);

    // Reset password
    @POST(AppConstants.API_NAME + "/api2/resetpassword.php")
    Call<String> resetPassword(@Body String json);

    // Upload Images
    @Multipart
    @POST(AppConstants.API_NAME + "/api2/uploads.php")
    Call<String> uploadPhotos(@Part("tagname") RequestBody requestBody,
                              @Part MultipartBody.Part image);

}
