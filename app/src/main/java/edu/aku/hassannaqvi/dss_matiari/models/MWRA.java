package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class MWRA extends BaseObservable implements Observable {

    private final String TAG = "MWRA";
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;

    // APP VARIABLES
    private String projectName = MainApp.PROJECT_NAME;

    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String uuid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String assessNo = StringUtils.EMPTY;
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
    private String s1 = StringUtils.EMPTY;
    private String s2 = StringUtils.EMPTY;
    private String ra06 = "";
    private String ra07 = "";
    private String ra08 = "";
    private String ra09 = "";
    private String ra10 = "";
    private String rb01 = "";
    private String rb02 = "";
    private String rb03 = "";
    private String rb04 = "";
    private String rb05 = "";
    private String rb06 = "";
    private String rb07 = "";
    private String rb08 = "";
    private String rb09 = "";

    public MWRA() {

    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


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


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getAssessNo() {
        return assessNo;
    }

    public void setAssessNo(String assessNo) {
        this.assessNo = assessNo;
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
    public String getRb01() {
        return rb01;
    }

    public void setRb01(String rb01) {
        this.rb01 = rb01;
        notifyChange(BR.rb01);
    }

    @Bindable
    public String getRb02() {
        return rb02;
    }

    public void setRb02(String rb02) {
        this.rb02 = rb02;
        notifyChange(BR.rb02);
    }

    @Bindable
    public String getRb03() {
        return rb03;
    }

    public void setRb03(String rb03) {
        this.rb03 = rb03;
        notifyChange(BR.rb03);
    }

    @Bindable
    public String getRb04() {
        return rb04;
    }

    public void setRb04(String rb04) {
        this.rb04 = rb04;
        notifyChange(BR.rb04);
    }

    @Bindable
    public String getRb05() {
        return rb05;
    }

    public void setRb05(String rb05) {
        this.rb05 = rb05;
        notifyChange(BR.rb05);
    }

    @Bindable
    public String getRb06() {
        return rb06;
    }

    public void setRb06(String rb06) {
        this.rb06 = rb06;
        Log.d(TAG, "setRb06: " + this.rb06);
        notifyChange(BR.rb06);
    }

    @Bindable
    public String getRb07() {
        return rb07;
    }

    public void setRb07(String rb07) {
        this.rb07 = rb07;
        Log.d(TAG, "setRb07: " + this.rb07);
        notifyChange(BR.rb07);
    }

    @Bindable
    public String getRb08() {
        return rb08;
    }

    public void setRb08(String rb08) {
        this.rb08 = rb08;
        notifyChange(BR.rb08);
    }

    @Bindable
    public String getRb09() {
        return rb09;
    }

    public void setRb09(String rb09) {
        this.rb09 = rb09;
        notifyChange(BR.rb09);
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

    public MWRA Hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UUID));
        this.userName = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_HDSSID));
        this.assessNo = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ASSESMENT_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCED_DATE));

        s1Hydrate(cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_S1)));
        s2Hydrate(cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_S2)));
        return this;
    }


    public void s1Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);
                this.ra07 = json.getString("ra07");
                this.ra06 = json.getString("ra06");
                this.ra08 = json.getString("ra08");
                this.ra09 = json.getString("ra09");
                this.ra10 = json.getString("ra10");
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


    public String s1toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("ra07", ra07)
                    .put("ra06", ra06)
                    .put("ra08", ra08)
                    .put("ra09", ra09)
                    .put("ra10", ra10);
        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s2toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("rb01", rb01)
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
            Log.d(TAG, "s2toString: " + e.getMessage());
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }


    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(MWRATable.COLUMN_ID, this.id);
            json.put(MWRATable.COLUMN_UID, this.uid);
            json.put(MWRATable.COLUMN_UUID, this.uuid);
            json.put(MWRATable.COLUMN_USERNAME, this.userName);
            json.put(MWRATable.COLUMN_SYSDATE, this.sysDate);
            json.put(MWRATable.COLUMN_ASSESMENT_NO, this.hdssId);
            json.put(MWRATable.COLUMN_HDSSID, this.hdssId);
            json.put(MWRATable.COLUMN_ASSESMENT_NO, this.assessNo);
            json.put(MWRATable.COLUMN_DEVICEID, this.deviceId);
            json.put(MWRATable.COLUMN_DEVICETAGID, this.deviceTag);
            json.put(MWRATable.COLUMN_ISTATUS, this.iStatus);
            //  json.put(MWRATable.COLUMN_SYNCED, this.synced);
            //  json.put(MWRATable.COLUMN_SYNCED_DATE, this.syncDate);

            json.put(MWRATable.COLUMN_S1, new JSONObject(s1toString()));
            json.put(MWRATable.COLUMN_S2, new JSONObject(s2toString()));
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "toJSONObject: " + e.getMessage());
            return null;
        }
    }


}
