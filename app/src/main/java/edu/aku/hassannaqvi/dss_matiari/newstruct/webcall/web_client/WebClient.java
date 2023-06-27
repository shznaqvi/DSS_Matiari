package edu.aku.hassannaqvi.dss_matiari.newstruct.webcall.web_client;

import android.app.Activity;

import java.util.concurrent.TimeUnit;

import edu.aku.hassannaqvi.dss_matiari.BuildConfig;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebClient {

    private static WebClient instance;
    private final WebAPI webAPI;

    private WebClient(Activity activity) {
        // This logic of initializing BASE_URL is to generalize the code and control the
        // toggling from AppConstants. Also, it will initialized only once because we
        // use Singleton pattern.
        String HOST_NAME;
        String BASE_URL;
        if (AppConstants.IS_PRODUCTION_SERVER) {
            // Production Base Url
            HOST_NAME = "pedres2.aku.edu";
            BASE_URL = "https://" + HOST_NAME + "/";
        } else {
            // Testing Base Url
            HOST_NAME = "cls-pae-fp51764";
            BASE_URL = "http://" + HOST_NAME + "/";
        }

        // For logging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        /*// For SSL
        httpBuilder.sslSocketFactory(Objects.requireNonNull(CryptoUtil.getSSLContext(activity)).getSocketFactory(),
                CryptoUtil.getX509TrustManager());*/

        // For certificate pinning
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(HOST_NAME, BuildConfig.CERT_KEY)
                .add(HOST_NAME, BuildConfig.CERT_KEY_BACKUP)
                .build();
        httpBuilder.certificatePinner(certificatePinner);
        // For certificate validation
        httpBuilder.hostnameVerifier((hostname, session) -> CryptoUtil.checkCertValidity(activity, session));
        httpBuilder.addInterceptor(loggingInterceptor);
        // For adding user agent
//        httpBuilder.addInterceptor(new UserAgentInterceptor(USER_AGENT));
        // For connection timeout
        httpBuilder.connectTimeout(AppConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        // For read timeout
        httpBuilder.readTimeout(AppConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webAPI = retrofit.create(WebAPI.class);
    }

    public static synchronized WebClient getInstance(Activity activity) {
        if (instance == null) {
            instance = new WebClient(activity);
        }
        return instance;
    }

    public WebAPI getWebAPI() {
        return webAPI;
    }

    /*This interceptor adds a custom User-Agent*/
    /*public static class UserAgentInterceptor implements Interceptor {

        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }*/

}
