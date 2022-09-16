package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;

import android.database.Cursor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class FPHouseholds extends BaseObservable implements Observable {

    private final String TAG = "FPHouseholds";

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;
    // SECTION VARIABLES
    private final String sA = StringUtils.EMPTY;
    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    private String ucCode = StringUtils.EMPTY;
    private String villageCode = StringUtils.EMPTY;
    private String hhNo = StringUtils.EMPTY;
    // to be populated before first insert
    private String hdssId = StringUtils.EMPTY;

    private String visitNo = "0";
    private String fround = "";
    private String muid = "";
    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String endTime = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;
    private String structureNo = StringUtils.EMPTY;
    private String ra01 = "";  // Date of Visit
    private String ra01v2 = StringUtils.EMPTY;
    private String ra01v3 = StringUtils.EMPTY;
    private String ra11 = StringUtils.EMPTY;  //
    private String ra11x = StringUtils.EMPTY;
    private String ra12 = StringUtils.EMPTY;
    private String ra12x = StringUtils.EMPTY;
    private String ra13 = StringUtils.EMPTY;
    private String ra13x = StringUtils.EMPTY;

    public FPHouseholds() {

/*        setFround(MainApp.ROUND);
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setAppver(MainApp.appInfo.getAppVersion());
        //setRa06(MainApp.selectedUC);
        //setRa07(MainApp.selectedVillage);
        //setRa04(MainApp.leaderCode);
        //setRa05(MainApp.leaderCode);*/

    }

    public FPHouseholds(FPHouseholds households) {


        setUserName(households.getUserName());
        setDeviceId(households.getDeviceId());
        setAppver(households.getAppver());
        //setfRound(households.getfRound());


    }

    public void populateMeta(int position) {

        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        //   setUuid(MainApp.form.getUid());  // not applicable in Form table
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
        setHdssId(MainApp.followUpsScheHHList.get(position).getHdssid());
        setUcCode(MainApp.followUpsScheHHList.get(position).getUcCode());
        setVillageCode(MainApp.followUpsScheHHList.get(position).getVillageCode());
        setHhNo(MainApp.followUpsScheHHList.get(position).getHhNo());
        setFround(MainApp.followUpsScheHHList.get(position).getfRound());
        setMuid(MainApp.followUpsScheHHList.get(position).getMuid());



    }

    @Bindable
    public String getRa01() {
        return ra01;
    }

    public void setRa01(String ra01) {
        this.ra01 = ra01;
    }


    @Bindable
    public String getRa01v2() {
        return ra01v2;
    }

    public void setRa01v2(String ra01v2) {
        this.ra01v2 = ra01v2;
    }

    @Bindable
    public String getRa01v3() {
        return ra01v3;
    }

    public void setRa01v3(String ra01v3) {
        this.ra01v3 = ra01v3;
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

    public String getRa12x() {
        return ra12x;
    }

    public void setRa12x(String ra12x) {
        this.ra12x = ra12x;
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

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
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



    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {

        this.ucCode = ucCode;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getHhNo() {
        return hhNo;
    }

    public void setHhNo(String hhNo) {
        String[] hdssidSplit = hdssId.split("-");
        String newhhno = String.format("%04d", Integer.parseInt(hhNo));
        this.hhNo = newhhno;
        setHdssId(getUcCode() + "-" + getVillageCode() + "-" + getHhNo());
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
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


    public String getFround() {
        return fround;
    }

    public void setFround(String fround) {
        this.fround = fround;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    @Bindable
    public String getHdssId() {
        return hdssId;
    }

    public void setHdssId(String hdssId) {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String[] hdssidSplit = hdssId.split("-");
        String newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", Integer.parseInt(hdssidSplit[2]));

        this.hdssId = newHDSSID;

        //setRa22(hdssId);
        //notifyChange(BR.ra22);
    }

    public String getsA() {
        return sA;
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

    public FPHouseholds Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_UID));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_VILLAGE_CODE));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_HOUSEHOLD_NO));
        this.structureNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_STRUCTURE_NO));
        this.visitNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_VISIT_NO));
        this.fround = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_FP_ROUND));
        this.muid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_MUID));

        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.FPHouseholdTable.COLUMN_SYNCED_DATE));

        s1Hydrate(cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.HouseholdTable.COLUMN_SA)));





        return this;
    }


    public void s1Hydrate(String string) throws JSONException {

        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);

            this.ra01v2 = json.has("ra01v2") ? json.getString("ra01v2") : "";
            this.ra01v3 = json.has("ra01v3") ? json.getString("ra01v3") : "";

        }
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(TableContracts.FPHouseholdTable.COLUMN_ID, this.id);
        json.put(TableContracts.FPHouseholdTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(TableContracts.FPHouseholdTable.COLUMN_UID, this.uid);
        json.put(TableContracts.FPHouseholdTable.COLUMN_USERNAME, this.userName);
        json.put(TableContracts.FPHouseholdTable.COLUMN_SYSDATE, this.sysDate);
        json.put(TableContracts.FPHouseholdTable.COLUMN_HDSSID, this.hdssId);
        json.put(TableContracts.FPHouseholdTable.COLUMN_UC_CODE, this.ucCode);
        json.put(TableContracts.FPHouseholdTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(TableContracts.FPHouseholdTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(TableContracts.FPHouseholdTable.COLUMN_STRUCTURE_NO, this.structureNo);
        json.put(TableContracts.FPHouseholdTable.COLUMN_VISIT_NO, this.visitNo);
        json.put(TableContracts.FPHouseholdTable.COLUMN_FP_ROUND, this.fround);
        json.put(TableContracts.FPHouseholdTable.COLUMN_DEVICEID, this.deviceId);
        json.put(TableContracts.FPHouseholdTable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(TableContracts.FPHouseholdTable.COLUMN_ISTATUS, this.iStatus);
        json.put(TableContracts.FPHouseholdTable.COLUMN_APPVERSION, this.appver);
        json.put(TableContracts.FPHouseholdTable.COLUMN_MUID, this.muid);
        //  json.put(HouseholdTable.COLUMN_SYNCED, this.synced);
        //  json.put(HouseholdTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(TableContracts.FPHouseholdTable.COLUMN_SA, new JSONObject(sAtoString()));
        //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));


        return json;

    }

    public String sAtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json
                .put("ra01v3", ra01v3)
                .put("ra01v2", ra01v2);

        return json.toString();
    }


    public FPHouseholds Sync(JSONObject jsonObject) throws JSONException {
        this.ucCode = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_UC_CODE);
        this.uid = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_UC_CODE);
        this.userName = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_USERNAME);
        this.sysDate = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_SYSDATE);
        this.structureNo = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_STRUCTURE_NO);
        this.visitNo = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_VISIT_NO);
        this.villageCode = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_VILLAGE_CODE);
        this.hhNo = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_HOUSEHOLD_NO);
        this.hdssId = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_HDSSID);
        this.fround = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_FP_ROUND);
        this.muid = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_MUID);
        this.deviceId = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_DEVICEID);
        this.deviceTag = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_DEVICETAGID);
        this.appver = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_APPVERSION);
        this.iStatus = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_ISTATUS);

        return this;
    }


}
