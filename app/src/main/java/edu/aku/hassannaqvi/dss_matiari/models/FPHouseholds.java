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
    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String endTime = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;
    private String structureNo = StringUtils.EMPTY;
    private String rc01v2 = StringUtils.EMPTY;
    private String rc01v3 = StringUtils.EMPTY;


    public FPHouseholds() {

/*        setFround(MainApp.round);
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
        setHdssId(MainApp.fpHouseholdList.get(position).getHdssid());
        setUcCode(MainApp.fpHouseholdList.get(position).getUcCode());
        setVillageCode(MainApp.fpHouseholdList.get(position).getVillageCode());
        setHhNo(MainApp.fpHouseholdList.get(position).getHhNo());
        setFround(MainApp.fpHouseholdList.get(position).getfRound());


    }

    @Bindable
    public String getRc01v2() {
        return rc01v2;
    }

    @Bindable
    public String getRc01v3() {
        return rc01v3;
    }

    public void setRc01v2(String rc01v2) {
        this.rc01v2 = rc01v2;
    }

    public void setRc01v3(String rc01v3) {
        this.rc01v3 = rc01v3;
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
        this.hhNo = hhNo;
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

    @Bindable
    public String getHdssId() {
        return hdssId;
    }

    public void setHdssId(String hdssId) {
        this.hdssId = hdssId;
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

            this.rc01v2 = json.has("rc01v2") ? json.getString("rc01v2") : "";
            this.rc01v3 = json.has("rc01v3") ? json.getString("rc01v3") : "";

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
        //  json.put(HouseholdTable.COLUMN_SYNCED, this.synced);
        //  json.put(HouseholdTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(TableContracts.FPHouseholdTable.COLUMN_SA, new JSONObject(sAtoString()));
        //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));


        return json;

    }

    public String sAtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json
                .put("rc01v3", rc01v3)
                .put("rc01v2", rc01v2);

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

        this.deviceId = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_DEVICEID);
        this.deviceTag = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_DEVICETAGID);
        this.appver = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_APPVERSION);
        this.iStatus = jsonObject.getString(TableContracts.FPHouseholdTable.COLUMN_ISTATUS);

        return this;
    }


}
