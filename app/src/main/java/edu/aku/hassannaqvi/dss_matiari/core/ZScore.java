package edu.aku.hassannaqvi.dss_matiari.core;

import android.util.Log;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.database.DatabaseHelper;

import static java.lang.Math.abs;

public class ZScore {


    int t;
    double y;
    double Lt;
    double Mt;
    double St;
    int gender;
    String cat;
    double zInd;
    private double SD3pos;
    private double SD3neg;
    private double SD23pos;
    private double SD23neg;
    private double zScore;
    private boolean flag;
    private DatabaseHelper db;
    private String catA;
    private String catB;
    private List<String> lms;
    private List<String> whlms;
    private String height;

    public ZScore(int age, int gender, float measurement, String cat) {

    }

    public ZScore() {

        flag = false;

    }

    public ZScore(int ageindays, int gender) {
        this.t = ageindays;
        this.gender = gender;
    }

    private void populateWHLMS() {

        //TODO: Fetch L,M & S from database
        db = MainApp.appInfo.getDbHelper();


        lms = db.getWHLMS(Double.valueOf(height), gender, catA);
        if (lms.size() > 0) {
            this.Lt = Double.parseDouble(lms.get(0));
            this.Mt = Double.parseDouble(lms.get(1));
            this.St = Double.parseDouble(lms.get(2));
        }
    }

    private void populateLMS() {

        //TODO: Fetch L,M & S from database
        db = MainApp.appInfo.getDbHelper();

        Log.d("TAG", "populateLMS: " + t + " | " + gender + " | " + catA + " | " + catB);
        lms = db.getLMS(t, gender, catA, catB);
        if (lms.size() > 0) {
            this.Lt = Double.parseDouble(lms.get(0));
            this.Mt = Double.parseDouble(lms.get(1));
            this.St = Double.parseDouble(lms.get(2));
        }
       /* this.Lt = -0.0137;
        this.Mt = 12.1548;
        this.St = 0.11427;*/

    }

    public double getZScore_HLAZ(String measurment) {

        this.catA = "HA";
        this.catB = "LA";
        this.y = Double.parseDouble(measurment);

        populateLMS();

    /*    double s1 = y / Mt;
        double s2 = Math.pow(s1, Lt);
        double s3 = s2 - 1;
        double s4 = St * Lt;
        double s5 = s3 / s4;

        Log.d("TAG", "getZScore_HLAZ y: " + y);
        Log.d("TAG", "getZScore_HLAZ mt: " + Mt);
        Log.d("TAG", "getZScore_HLAZ lt: " + Lt);
        Log.d("TAG", "getZScore_HLAZ st: " + St);
        Log.d("TAG", "getZScore_HLAZ mes: " + measurment);
        Log.d("TAG", "getZScore_HLAZ s1: " + s1);
        Log.d("TAG", "getZScore_HLAZ s2: " + s2);
        Log.d("TAG", "getZScore_HLAZ s3: " + s3);
        Log.d("TAG", "getZScore_HLAZ s4: " + s4);
        Log.d("TAG", "getZScore_HLAZ s5: " + s5);*/
        // (power((10.2/m),l)-1)/(s*l)
        zInd = (Math.pow((y / Mt), Lt) - 1) / (St * Lt);
        return FinalZscore();
    }

    public double getZScore_WAZ(String measurment) {

        this.catA = "WA";
        this.catB = "WA";
        this.y = Double.parseDouble(measurment);

        populateLMS();

        double s1 = y / Mt;
        double s2 = Math.pow(s1, Lt);
        double s3 = s2 - 1;
        double s4 = St * Lt;
        double s5 = s3 / s4;

        Log.d("TAG", "getZScore_HLAZ y: " + y);
        Log.d("TAG", "getZScore_HLAZ mt: " + Mt);
        Log.d("TAG", "getZScore_HLAZ lt: " + Lt);
        Log.d("TAG", "getZScore_HLAZ st: " + St);
        Log.d("TAG", "getZScore_HLAZ mes: " + measurment);
        Log.d("TAG", "getZScore_HLAZ s1: " + s1);
        Log.d("TAG", "getZScore_HLAZ s2: " + s2);
        Log.d("TAG", "getZScore_HLAZ s3: " + s3);
        Log.d("TAG", "getZScore_HLAZ s4: " + s4);
        Log.d("TAG", "getZScore_HLAZ s5: " + s5);
        Log.d("TAG", "getZScore_HLAZ WAZ: " + (Math.pow((y / Mt), Lt) - 1) / (St * Lt));
        zInd = (Math.pow((y / Mt), Lt) - 1) / (St * Lt);
        return FinalZscore();

    }

    public double getZScore_WHZ(String weight, String height) {
        this.height = height;
        this.catA = "WH";
        this.y = Double.parseDouble(weight);

        populateWHLMS();

        zInd = (Math.pow((y / Mt), Lt) - 1) / (St * Lt);

        return FinalZscore();
    }

    private double FinalZscore() {
        zScore = 0;
        SD3pos = calcSD(3);
        SD3neg = calcSD(-3);
        SD23pos = calcSD(3) - calcSD(2);
        SD23neg = calcSD(-2) - calcSD(-3);

        if (zInd >= -3 && zInd <= 3) {
            zScore = zInd;
        } else if (zInd > 3) {

            zScore = 3 + ((y - SD3pos) / (SD23pos));


        } else if (zInd < -3) {
            zScore = -3 + ((y - SD3neg) / (SD23neg));

        }

        return zScore;
    }

    private double calcSD(int num) {
        double SD = 0;

        double step1 = 1 + Lt * St * num;

        if (step1 < 0) {
            double step2 = Math.pow(abs(step1), (1 / Lt));
            SD = (Mt * (step2)) * -1;
        } else {
            double step2 = Math.pow(step1, (1 / Lt));
            SD = Mt * (step2);
        }

        return SD;
    }


}
