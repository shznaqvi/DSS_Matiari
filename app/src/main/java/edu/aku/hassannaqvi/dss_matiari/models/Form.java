package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.PropertyChangeRegistry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FormsTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class Form extends BaseObservable {

    private final String TAG = "Form";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    public String ra01 = "";
    public String ra02 = "";
    public String ra04 = "";
    public String ra03 = "";
    public String ra05 = "";
    public String ra07 = "";
    public String ra06 = "";
    public String ra08 = "";
    public String ra09 = "";
    public String ra10 = "";
    public String ra11 = "";
    public String ra11x = "";
    public String ra12 = "";
    public String ra1296x = "";
    public String ra13 = "";
    public String ra13x = "";
    public String ra14 = "";
    public String ra15 = "";
    public String ra16 = "";
    public String ra17_a = "";
    public String ra17_b = "";
    public String ra17_c = "";
    public String ra17_d = "";
    public String ra18 = "";
    public String rb_uc = "";
    public String rb_vil = "";
    public String rb_hno = "";
    public String rb_sid = "";
    public String rb_ufn = "";
    public String rb01 = "";
    public String rb02 = "";
    public String rb03 = "";
    public String rb04 = "";
    public String rb05 = "";
    public String rb06 = "";
    public String rb07 = "";
    public String rb08 = "";
    public String rb09 = "";

    // APP VARIABLES
    private String projectName = MainApp.PROJECT_NAME;
    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String assessNo = StringUtils.EMPTY;
    private String mrNo = StringUtils.EMPTY;
    private String infantName = StringUtils.EMPTY;

    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String endTime = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES
    private String s1 = StringUtils.EMPTY;
    private String s2 = StringUtils.EMPTY;
    private String s3 = StringUtils.EMPTY;
    private String s4 = StringUtils.EMPTY;
    private String s5 = StringUtils.EMPTY;

    public Form() {

    }

    public String getRa01() {
        return ra01;
    }

    public void setRa01(String ra01) {
        this.ra01 = ra01;
    }

    public String getRa02() {
        return ra02;
    }

    public void setRa02(String ra02) {
        this.ra02 = ra02;
    }

    public String getRa04() {
        return ra04;
    }

    public void setRa04(String ra04) {
        this.ra04 = ra04;
    }

    public String getRa03() {
        return ra03;
    }

    public void setRa03(String ra03) {
        this.ra03 = ra03;
    }

    public String getRa05() {
        return ra05;
    }

    public void setRa05(String ra05) {
        this.ra05 = ra05;
    }

    public String getRa07() {
        return ra07;
    }

    public void setRa07(String ra07) {
        this.ra07 = ra07;
    }

    public String getRa06() {
        return ra06;
    }

    public void setRa06(String ra06) {
        this.ra06 = ra06;
    }

    public String getRa08() {
        return ra08;
    }

    public void setRa08(String ra08) {
        this.ra08 = ra08;
    }

    public String getRa09() {
        return ra09;
    }

    public void setRa09(String ra09) {
        this.ra09 = ra09;
    }

    public String getRa10() {
        return ra10;
    }

    public void setRa10(String ra10) {
        this.ra10 = ra10;
    }

    public String getRa11() {
        return ra11;
    }

    public void setRa11(String ra11) {
        this.ra11 = ra11;
    }

    public String getRa11x() {
        return ra11x;
    }

    public void setRa11x(String ra11x) {
        this.ra11x = ra11x;
    }

    public String getRa12() {
        return ra12;
    }

    public void setRa12(String ra12) {
        this.ra12 = ra12;
    }

    public String getRa1296x() {
        return ra1296x;
    }

    public void setRa1296x(String ra1296x) {
        this.ra1296x = ra1296x;
    }

    public String getRa13() {
        return ra13;
    }

    public void setRa13(String ra13) {
        this.ra13 = ra13;
    }

    public String getRa13x() {
        return ra13x;
    }

    public void setRa13x(String ra13x) {
        this.ra13x = ra13x;
    }

    public String getRa14() {
        return ra14;
    }

    public void setRa14(String ra14) {
        this.ra14 = ra14;
    }

    public String getRa15() {
        return ra15;
    }

    public void setRa15(String ra15) {
        this.ra15 = ra15;
    }

    public String getRa16() {
        return ra16;
    }

    public void setRa16(String ra16) {
        this.ra16 = ra16;
    }

    public String getRa17_a() {
        return ra17_a;
    }

    public void setRa17_a(String ra17_a) {
        this.ra17_a = ra17_a;
    }

    public String getRa17_b() {
        return ra17_b;
    }

    public void setRa17_b(String ra17_b) {
        this.ra17_b = ra17_b;
    }

    public String getRa17_c() {
        return ra17_c;
    }

    public void setRa17_c(String ra17_c) {
        this.ra17_c = ra17_c;
    }

    public String getRa17_d() {
        return ra17_d;
    }

    public void setRa17_d(String ra17_d) {
        this.ra17_d = ra17_d;
    }

    public String getRa18() {
        return ra18;
    }

    public void setRa18(String ra18) {
        this.ra18 = ra18;
    }

    public String getRb_uc() {
        return rb_uc;
    }

    public void setRb_uc(String rb_uc) {
        this.rb_uc = rb_uc;
    }

    public String getRb_vil() {
        return rb_vil;
    }

    public void setRb_vil(String rb_vil) {
        this.rb_vil = rb_vil;
    }

    public String getRb_hno() {
        return rb_hno;
    }

    public void setRb_hno(String rb_hno) {
        this.rb_hno = rb_hno;
    }

    public String getRb_sid() {
        return rb_sid;
    }

    public void setRb_sid(String rb_sid) {
        this.rb_sid = rb_sid;
    }

    public String getRb_ufn() {
        return rb_ufn;
    }

    public void setRb_ufn(String rb_ufn) {
        this.rb_ufn = rb_ufn;
    }

    public String getRb01() {
        return rb01;
    }

    public void setRb01(String rb01) {
        this.rb01 = rb01;
    }

    public String getRb02() {
        return rb02;
    }

    public void setRb02(String rb02) {
        this.rb02 = rb02;
    }

    public String getRb03() {
        return rb03;
    }

    public void setRb03(String rb03) {
        this.rb03 = rb03;
    }

    public String getRb04() {
        return rb04;
    }

    public void setRb04(String rb04) {
        this.rb04 = rb04;
    }

    public String getRb05() {
        return rb05;
    }

    public void setRb05(String rb05) {
        this.rb05 = rb05;
    }

    public String getRb06() {
        return rb06;
    }

    public void setRb06(String rb06) {
        this.rb06 = rb06;
    }

    public String getRb07() {
        return rb07;
    }

    public void setRb07(String rb07) {
        this.rb07 = rb07;
    }

    public String getRb08() {
        return rb08;
    }

    public void setRb08(String rb08) {
        this.rb08 = rb08;
    }

    public String getRb09() {
        return rb09;
    }

    public void setRb09(String rb09) {
        this.rb09 = rb09;
    }

/*
    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }*/

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

/*    public PropertyChangeRegistry getPropertyChangeRegistry() {
        return propertyChangeRegistry;
    }

    public void setPropertyChangeRegistry(PropertyChangeRegistry propertyChangeRegistry) {
        this.propertyChangeRegistry = propertyChangeRegistry;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getAssessNo() {
        return assessNo;
    }

    public void setAssessNo(String assessNo) {
        this.assessNo = assessNo;
    }

    public String getInfantName() {
        return infantName;
    }

    public void setInfantName(String infantName) {
        this.infantName = infantName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getiStatus96x() {
        return iStatus96x;
    }

    public void setiStatus96x(String iStatus96x) {
        this.iStatus96x = iStatus96x;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }


    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    public Form Hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_UID));
        this.userName = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYSDATE));
        this.mrNo = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_MR_NUMBER));
        this.assessNo = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ASSESMENT_NO));
        this.infantName = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_INFANT_NAME));
        this.deviceId = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED_DATE));

        s1Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S1)));
        s2Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S2)));
/*        s3Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S3)));
        s4Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S4)));
        s5Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S5)));*/

        return this;
    }

    public void s1Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);
                this.ra01 = json.getString("ra01");
                this.ra02 = json.getString("ra02");
                this.ra04 = json.getString("ra04");
                this.ra03 = json.getString("ra03");
                this.ra05 = json.getString("ra05");
                this.ra07 = json.getString("ra07");
                this.ra06 = json.getString("ra06");
                this.ra08 = json.getString("ra08");
                this.ra09 = json.getString("ra09");
                this.ra10 = json.getString("ra10");
                this.ra11 = json.getString("ra11");
                this.ra11x = json.getString("ra11x");
                this.ra12 = json.getString("ra12");
                this.ra1296x = json.getString("ra1296x");
                this.ra13 = json.getString("ra13");
                this.ra13x = json.getString("ra13x");
                this.ra14 = json.getString("ra14");
                this.ra15 = json.getString("ra15");
                this.ra16 = json.getString("ra16");
                this.ra17_a = json.getString("ra17_a");
                this.ra17_b = json.getString("ra17_b");
                this.ra17_c = json.getString("ra17_c");
                this.ra17_d = json.getString("ra17_d");
                this.ra18 = json.getString("ra18");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s2Hydrate(String string) {
        Log.d(TAG, "s2Hydrate: " + string);
        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.rb_uc = json.getString("rb_uc");
                this.rb_vil = json.getString("rb_vil");
                this.rb_hno = json.getString("rb_hno");
                this.rb_sid = json.getString("rb_sid");
                this.rb_ufn = json.getString("rb_ufn");
                this.rb01 = json.getString("rb01");
                this.rb02 = json.getString("rb02");
                this.rb03 = json.getString("rb03");
                this.rb04 = json.getString("rb04");
                this.rb05 = json.getString("rb05");
                this.rb06 = json.getString("rb06");
                this.rb07 = json.getString("rb07");
                this.rb08 = json.getString("rb08");
                this.rb09 = json.getString("rb09");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


/*    public void s3Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf301 = json.getString("tsf301");
                this.tsf302 = json.getString("tsf302");
                this.tsf303 = json.getString("tsf303");
                this.tsf304 = json.getString("tsf304");
                //this.tsf305 = json.getString("tsf305"); // commented because moved out of json
                this.tsf306 = json.getString("tsf306");
                this.tsf307 = json.getString("tsf307");
                this.tsf308 = json.getString("tsf308");
                this.tsf309 = json.getString("tsf309");
                this.tsf310 = json.getString("tsf310");
                this.tsf311 = json.getString("tsf311");
                this.tsf312 = json.getString("tsf312");
                this.tsf313 = json.getString("tsf313");
                this.tsf314 = json.getString("tsf314");
                this.tsf315 = json.getString("tsf315");
                this.tsf316 = json.getString("tsf316");
                this.tsf317 = json.getString("tsf317");
                this.tsf318 = json.getString("tsf318");
                this.tsf319 = json.getString("tsf319");
                this.tsf320 = json.getString("tsf320");
                this.tsf321 = json.getString("tsf321");
                this.tsf322 = json.getString("tsf322");
                this.tsf323 = json.getString("tsf323");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s4Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf40101 = json.getString("tsf40101");
                this.tsf40102 = json.getString("tsf40102");
                this.tsf40103 = json.getString("tsf40103");
                this.tsf40104 = json.getString("tsf40104");
                this.tsf40201 = json.getString("tsf40201");
                this.tsf40202 = json.getString("tsf40202");
                this.tsf40203 = json.getString("tsf40203");
                this.tsf40204 = json.getString("tsf40204");
                this.tsf40205 = json.getString("tsf40205");
                this.tsf40206 = json.getString("tsf40206");
                this.tsf40207 = json.getString("tsf40207");
                this.tsf40208 = json.getString("tsf40208");
                this.tsf40209 = json.getString("tsf40209");
                this.tsf40298 = json.getString("tsf40298");
                this.tsf40298x = json.getString("tsf40298x");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s5Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf501 = json.getString("tsf501");
                this.tsf502 = json.getString("tsf502");
                this.tsf503 = json.getString("tsf503");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    public String s1toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("ra01", ra01)
                    .put("ra02", ra02)
                    .put("ra04", ra04)
                    .put("ra03", ra03)
                    .put("ra05", ra05)
                    .put("ra07", ra07)
                    .put("ra06", ra06)
                    .put("ra08", ra08)
                    .put("ra09", ra09)
                    .put("ra10", ra10)
                    .put("ra11", ra11)
                    .put("ra11x", ra11x)
                    .put("ra12", ra12)
                    .put("ra1296x", ra1296x)
                    .put("ra13", ra13)
                    .put("ra13x", ra13x)
                    .put("ra14", ra14)
                    .put("ra15", ra15)
                    .put("ra16", ra16)
                    .put("ra17_a", ra17_a)
                    .put("ra17_b", ra17_b)
                    .put("ra17_c", ra17_c)
                    .put("ra17_d", ra17_d)
                    .put("ra18", ra18);
        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s2toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("rb_uc", rb_uc)
                    .put("rb_vil", rb_vil)
                    .put("rb_hno", rb_hno)
                    .put("rb_sid", rb_sid)
                    .put("rb_ufn", rb_ufn)
                    .put("rb01", rb01)
                    .put("rb02", rb02)
                    .put("rb03", rb03)
                    .put("rb04", rb04)
                    .put("rb05", rb05)
                    .put("rb06", rb06)
                    .put("rb07", rb07)
                    .put("rb08", rb08)
                    .put("rb09", rb09);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

/*    public String s3toString() {
        JSONObject json = new JSONObject();

        try {
            json
                    .put("tsf301", tsf301)
                    .put("tsf302", tsf302)
                    .put("tsf303", tsf303)
                    .put("tsf304", tsf304)
                    // .put("tsf305", tsf305) commented because temp. recheck moved out of json
                    .put("tsf306", tsf306)
                    .put("tsf307", tsf307)
                    .put("tsf308", tsf308)
                    .put("tsf309", tsf309)
                    .put("tsf310", tsf310)
                    .put("tsf311", tsf311)
                    .put("tsf312", tsf312)
                    .put("tsf313", tsf313)
                    .put("tsf314", tsf314)
                    .put("tsf315", tsf315)
                    .put("tsf316", tsf316)
                    .put("tsf317", tsf317)
                    .put("tsf318", tsf318)
                    .put("tsf319", tsf319)
                    .put("tsf320", tsf320)
                    .put("tsf321", tsf321)
                    .put("tsf322", tsf322)
                    .put("tsf323", tsf323);


        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s4toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("tsf40101", tsf40101)
                    .put("tsf40102", tsf40102)
                    .put("tsf40103", tsf40103)
                    .put("tsf40104", tsf40104)
                    .put("tsf40201", tsf40201)
                    .put("tsf40202", tsf40202)
                    .put("tsf40203", tsf40203)
                    .put("tsf40204", tsf40204)
                    .put("tsf40205", tsf40205)
                    .put("tsf40206", tsf40206)
                    .put("tsf40207", tsf40207)
                    .put("tsf40208", tsf40208)
                    .put("tsf40209", tsf40209)
                    .put("tsf40298", tsf40298)
                    .put("tsf40298x", tsf40298x);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s5toString() {
        JSONObject json = new JSONObject();

        try {
            json

                    .put("tsf501", tsf501)
                    .put("tsf502", tsf502)
                    .put("tsf503", tsf503);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }*/

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(FormsTable.COLUMN_ID, this.id);
            json.put(FormsTable.COLUMN_UID, this.uid);
            json.put(FormsTable.COLUMN_USERNAME, this.userName);
            json.put(FormsTable.COLUMN_SYSDATE, this.sysDate);
            json.put(FormsTable.COLUMN_ASSESMENT_NO, this.mrNo);
            json.put(FormsTable.COLUMN_MR_NUMBER, this.mrNo);
            json.put(FormsTable.COLUMN_ASSESMENT_NO, this.assessNo);
            json.put(FormsTable.COLUMN_INFANT_NAME, this.infantName);
//            json.put(FormsTable.COLUMN_TSF305, this.tsf305);
            json.put(FormsTable.COLUMN_DEVICEID, this.deviceId);
            json.put(FormsTable.COLUMN_DEVICETAGID, this.deviceTag);
            json.put(FormsTable.COLUMN_ISTATUS, this.iStatus);
            //  json.put(FormsTable.COLUMN_SYNCED, this.synced);
            //  json.put(FormsTable.COLUMN_SYNCED_DATE, this.syncDate);

            json.put(FormsTable.COLUMN_S1, new JSONObject(s1toString()));
            //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));
            json.put(FormsTable.COLUMN_S2, new JSONObject(s2toString()));
            /*json.put(FormsTable.COLUMN_S3, new JSONObject(s3toString()));
            json.put(FormsTable.COLUMN_S4, new JSONObject(s4toString()));
            json.put(FormsTable.COLUMN_S5, new JSONObject(s5toString()));*/


            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "toJSONObject: " + e.getMessage());
            return null;
        }
    }
}
