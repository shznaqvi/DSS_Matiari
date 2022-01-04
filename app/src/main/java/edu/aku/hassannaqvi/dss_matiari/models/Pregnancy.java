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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class Pregnancy extends BaseObservable implements Observable {

    private final String TAG = "MWRA";
    //Not saving in DB
    private final LocalDate localDate = null;
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;
    private boolean expanded;

    // APP VARIABLES
    private String projectName = MainApp.PROJECT_NAME;

    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String uuid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String hdssId = StringUtils.EMPTY;
    private String ucCode = StringUtils.EMPTY;
    private String villageCode = StringUtils.EMPTY;
    private String hhNo = StringUtils.EMPTY;
    private String structureNo = StringUtils.EMPTY;

    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES
    private String sD = StringUtils.EMPTY;

    private String round = StringUtils.EMPTY;;
    private String rc08 = StringUtils.EMPTY;;

    private String rc10 = StringUtils.EMPTY;;
    private String rc09 = StringUtils.EMPTY;;
    private String rc11 = StringUtils.EMPTY;;

    public Pregnancy() {

        setRound(MainApp.round);
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        if (MainApp.households != null) {
            setUuid(MainApp.households.getUid());
        }
        setVillageCode(MainApp.selectedVillage);
        setUcCode(MainApp.selectedUC);

    }

    @Bindable
    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
        notifyPropertyChanged(BR.round);
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

    public String getSD() {
        return sD;
    }

    public void setsD(String sC) {
        this.sD = sD;
    }


    @Bindable
    public String getRc08() {
        return rc08;
    }

    @Bindable
    public String getRc10() {
        return rc10;
    }

    @Bindable
    public String getRc09() {
        return rc09;
    }

    @Bindable
    public String getRc11() {
        return rc11;
    }

    public void setRc08(String rc08) {
        this.rc08 = rc08;
        setRc10(rc08.equals("1") ? "" : this.rc10);
        setRc09(rc08.equals("1") ? "" : this.rc09);
        setRc11(rc08.equals("1") ? "" : this.rc11);
        notifyPropertyChanged(BR.rc08);
    }

    public void setRc10(String rc10) {
        this.rc10 = rc10;
        notifyPropertyChanged(BR.rc10);
    }

    public void setRc09(String rc09) {
        this.rc09 = rc09;
        notifyPropertyChanged(BR.rc09);
    }

    public void setRc11(String rc11) {
        this.rc11 = rc11;
        notifyPropertyChanged(BR.rc11);
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

    public Pregnancy Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_UUID));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_VILLAGE_CODE));
        this.structureNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_STRUCTURE_NO));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_HOUSEHOLD_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_SYNCED_DATE));

        sDHydrate(cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.PregnancyTable.COLUMN_SD)));
        return this;
    }


    public void sDHydrate(String string) throws JSONException {
        Log.d(TAG, "s4Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rc08 = json.getString("rb08");
            this.rc09 = json.getString("ra09");
            this.round = json.getString("round");
            this.rc10 = json.getString("rb10");
            this.rc11 = json.getString("rb11");


        }
    }


    public String sDtoString() throws JSONException {
        JSONObject json = new JSONObject();


        json.put("rc08", rc08)
                .put("rc09", rc09)
                .put("rc10", rc10)
                .put("rc11", rc11);

        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(TableContracts.PregnancyTable.COLUMN_ID, this.id);
        json.put(TableContracts.PregnancyTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(TableContracts.PregnancyTable.COLUMN_UID, this.uid);
        json.put(TableContracts.PregnancyTable.COLUMN_UUID, this.uuid);
        json.put(TableContracts.PregnancyTable.COLUMN_USERNAME, this.userName);
        json.put(TableContracts.PregnancyTable.COLUMN_SYSDATE, this.sysDate);
        json.put(TableContracts.PregnancyTable.COLUMN_HDSSID, this.hdssId);
        json.put(TableContracts.PregnancyTable.COLUMN_UC_CODE, this.ucCode);
        json.put(TableContracts.PregnancyTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(TableContracts.PregnancyTable.COLUMN_STRUCTURE_NO, this.structureNo);
        json.put(TableContracts.PregnancyTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(TableContracts.PregnancyTable.COLUMN_DEVICEID, this.deviceId);
        json.put(TableContracts.PregnancyTable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(TableContracts.PregnancyTable.COLUMN_ISTATUS, this.iStatus);
        json.put(TableContracts.PregnancyTable.COLUMN_APPVERSION, this.appver);
        //  json.put(MWRATable.COLUMN_SYNCED, this.synced);
        //  json.put(MWRATable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(TableContracts.PregnancyTable.COLUMN_SD, new JSONObject(sDtoString()));
        return json;

    }


    @Bindable
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        notifyChange(BR.expanded);
    }

   /* public String calcEDD() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            cal.setTime(sdf.parse(getRb08()));// all done

            // Set EDD by default
            cal.add(Calendar.DAY_OF_YEAR, 7);
            cal.add(Calendar.MONTH, 9);

            return sdf.format(cal.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }*/
}
