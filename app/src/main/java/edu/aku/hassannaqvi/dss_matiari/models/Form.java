package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FormsTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class Form extends BaseObservable implements Observable {

    private final String TAG = "Form";
    private String ra01 = "";
    private String ra02 = "";
    private String ra04 = "";
    private String ra03 = "";
    private String ra05 = "";
    private String ra07 = "";
    private String ra06 = "";
    private String ra08 = "";
    private String ra09 = "";
    private String ra10 = "";
    private String ra11 = "";
    private String ra11x = "";
/*    private String ra12 = "";
    private String ra1296x = "";
    private String ra13 = "";
    private String ra13x = "";*/
    private String ra14 = "";
    private String ra15 = "";
    private String ra16 = "";
    private String ra17_a = "";
    private String ra17_b = "";
    private String ra17_c = "";
    private String ra17_d = "";
    private String ra18 = "";

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;
    // APP VARIABLES
    private String projectName = MainApp.PROJECT_NAME;
    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String hdssId = StringUtils.EMPTY;


    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String endTime = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES
    private String sA = StringUtils.EMPTY;


    public Form() {

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

    public String getHdssId() {
        return hdssId;
    }

    public void setHdssId(String hdssId) {
        this.hdssId = hdssId;
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

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getsA() {
        return sA;
    }

    public void setsA(String sA) {
        this.sA = sA;
    }


    @Bindable
    public String getRa01() {
        return ra01;
    }

    public void setRa01(String ra01) {
        this.ra01 = ra01;
        notifyChange(BR.ra01);
    }

    @Bindable
    public String getRa02() {
        return ra02;
    }

    public void setRa02(String ra02) {
        this.ra02 = ra02;
        notifyChange(BR.ra02);
    }

    @Bindable
    public String getRa04() {
        return ra04;
    }

    public void setRa04(String ra04) {
        this.ra04 = ra04;
        notifyChange(BR.ra04);
    }

    @Bindable
    public String getRa03() {
        return ra03;
    }

    public void setRa03(String ra03) {
        this.ra03 = ra03;
        notifyChange(BR.ra03);
    }

    @Bindable
    public String getRa05() {
        return ra05;
    }

    public void setRa05(String ra05) {
        this.ra05 = ra05;
        notifyChange(BR.ra05);
    }

    @Bindable
    public String getRa07() {
        return ra07;
    }

    public void setRa07(String ra07) {
        this.ra07 = ra07;
        notifyChange(BR.ra07);
    }

    @Bindable
    public String getRa06() {
        return ra06;
    }

    public void setRa06(String ra06) {
        this.ra06 = ra06;
        notifyChange(BR.ra06);
    }

    @Bindable
    public String getRa08() {
        return ra08;
    }

    public void setRa08(String ra08) {
        this.ra08 = ra08;
        notifyChange(BR.ra08);
    }

    @Bindable
    public String getRa09() {
        return ra09;
    }

    public void setRa09(String ra09) {
        this.ra09 = ra09;
        notifyChange(BR.ra09);
    }

    @Bindable
    public String getRa10() {
        return ra10;
    }

    public void setRa10(String ra10) {
        this.ra10 = ra10;
        notifyChange(BR.ra10);
    }

    @Bindable
    public String getRa11() {
        return ra11;
    }

    public void setRa11(String ra11) {
        this.ra11 = ra11;
        setiStatus(ra11);
        notifyChange(BR.ra11);
    }

    @Bindable
    public String getRa11x() {
        return ra11x;
    }

    public void setRa11x(String ra11x) {
        this.ra11x = ra11x;
        notifyChange(BR.ra11x);
    }

    /* @Bindable
     public String getRa12() {
         return ra12;
     }

     public void setRa12(String ra12) {
         this.ra12 = ra12;
         notifyChange(BR.ra12);
     }

     @Bindable
     public String getRa1296x() {
         return ra1296x;
     }

     public void setRa1296x(String ra1296x) {
         this.ra1296x = ra1296x;
         notifyChange(BR.ra1296x);
     }

     @Bindable
     public String getRa13() {
         return ra13;
     }

     public void setRa13(String ra13) {
         this.ra13 = ra13;
         notifyChange(BR.ra13);
     }

     @Bindable
     public String getRa13x() {
         return ra13x;
     }

     public void setRa13x(String ra13x) {
         this.ra13x = ra13x;
         notifyChange(BR.ra13x);
     }
 */
    @Bindable
    public String getRa14() {
        return ra14;
    }

    public void setRa14(String ra14) {
        this.ra14 = ra14;
        notifyChange(BR.ra14);
    }

    @Bindable
    public String getRa15() {
        return ra15;
    }

    public void setRa15(String ra15) {
        this.ra15 = ra15;
        notifyChange(BR.ra15);
    }

    @Bindable
    public String getRa16() {
        return ra16;
    }

    public void setRa16(String ra16) {
        this.ra16 = ra16;
        notifyChange(BR.ra16);
    }

    @Bindable
    public String getRa17_a() {
        return ra17_a;
    }

    public void setRa17_a(String ra17_a) {
        this.ra17_a = ra17_a;
        notifyChange(BR.ra17_a);
    }

    @Bindable
    public String getRa17_b() {
        return ra17_b;
    }

    public void setRa17_b(String ra17_b) {
        this.ra17_b = ra17_b;
        notifyChange(BR.ra17_b);
    }

    @Bindable
    public String getRa17_c() {
        return ra17_c;
    }

    public void setRa17_c(String ra17_c) {
        this.ra17_c = ra17_c;
        notifyChange(BR.ra17_c);
    }

    @Bindable
    public String getRa17_d() {
        return ra17_d;
    }

    public void setRa17_d(String ra17_d) {
        this.ra17_d = ra17_d;
        notifyChange(BR.ra17_d);
    }

    @Bindable
    public String getRa18() {
        return ra18;
    }

    public void setRa18(String ra18) {
        this.ra18 = ra18;
        notifyChange(BR.ra18);
    }


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
    }

    public Form Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_UID));
        this.userName = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HDSSID));

        this.deviceId = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED_DATE));

        s1Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SA)));


        return this;
    }

    public void s1Hydrate(String string) throws JSONException {

        if (string != null) {

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
          /*      this.ra12 = json.getString("ra12");
                this.ra1296x = json.getString("ra1296x");
                this.ra13 = json.getString("ra13");
                this.ra13x = json.getString("ra13x");*/
                this.ra14 = json.getString("ra14");
                this.ra15 = json.getString("ra15");
                this.ra16 = json.getString("ra16");
                this.ra17_a = json.getString("ra17_a");
                this.ra17_b = json.getString("ra17_b");
                this.ra17_c = json.getString("ra17_c");
                this.ra17_d = json.getString("ra17_d");
                this.ra18 = json.getString("ra18");


        }
    }


    public String sAtoString() throws JSONException {
        JSONObject json = new JSONObject();

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
                /*    .put("ra12", ra12)
                    .put("ra1296x", ra1296x)
                    .put("ra13", ra13)
                    .put("ra13x", ra13x)*/
                    .put("ra14", ra14)
                    .put("ra15", ra15)
                    .put("ra16", ra16)
                    .put("ra17_a", ra17_a)
                    .put("ra17_b", ra17_b)
                    .put("ra17_c", ra17_c)
                    .put("ra17_d", ra17_d)
                    .put("ra18", ra18);

        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(FormsTable.COLUMN_ID, this.id);
        json.put(FormsTable.COLUMN_UID, this.uid);
        json.put(FormsTable.COLUMN_USERNAME, this.userName);
        json.put(FormsTable.COLUMN_SYSDATE, this.sysDate);
        json.put(FormsTable.COLUMN_HDSSID, this.hdssId);
        json.put(FormsTable.COLUMN_DEVICEID, this.deviceId);
            json.put(FormsTable.COLUMN_DEVICETAGID, this.deviceTag);
            json.put(FormsTable.COLUMN_ISTATUS, this.iStatus);
            //  json.put(FormsTable.COLUMN_SYNCED, this.synced);
            //  json.put(FormsTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(FormsTable.COLUMN_SA, new JSONObject(sAtoString()));
            //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));


        return json;

    }


}
