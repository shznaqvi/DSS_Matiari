package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.EntryLogTable;

@Entity(tableName = EntryLogTable.TABLE_NAME)
public class EntryLog extends BaseObservable implements Observable {

    //private final String TAG = "Form";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @PrimaryKey(autoGenerate = true) @NonNull
    @SerializedName("_id")
    private long id;

    @SerializedName("_uid")
    private String uid = StringUtils.EMPTY;
    // APP VARIABLES
    private String projectName = PROJECT_NAME;

    @SerializedName("_uuid")
    private String uuid = StringUtils.EMPTY;

    @SerializedName("username")
    private String userName = StringUtils.EMPTY;

    @SerializedName("sysdate")
    private String sysDate = StringUtils.EMPTY;

    private String entryDate = StringUtils.EMPTY;

    private String hhid = StringUtils.EMPTY;

    @SerializedName("appversion")
    private String appver = StringUtils.EMPTY;            //

    @SerializedName("istatus")
    private String iStatus = StringUtils.EMPTY;

    private transient String iStatus96x = StringUtils.EMPTY;        //

    @SerializedName("entry_type")
    private String entryType = StringUtils.EMPTY;

    @SerializedName("deviceid")
    private String deviceId = StringUtils.EMPTY;

    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // For local use
    // This is used for resolving data while posting
    @ColumnInfo(defaultValue = "0")
    private transient boolean isError;

    public EntryLog() {


    }


    public void populateMeta() {

        setProjectName(PROJECT_NAME);
        setUuid(MainApp.households.getUid());  // not applicable in Form table
        setUserName(MainApp.user.getUsername());
        setSysDate(MainApp.households.getSysDate());
        setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
//        setPsuCode(MainApp.form.getPsuCode());
//        setHhid(MainApp.form.getHhid());
        setIStatus(MainApp.households.getIStatus());
        setIStatus96x(MainApp.households.getIStatus96x());
        setAppver(MainApp.appInfo.getAppVersion());
//        setEntryType(MainApp.form.getEntryType());
        setDeviceId(MainApp.deviceid);

    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Bindable
    public String getHhid() {
        return hhid;
    }

    public void setHhid(String hhid) {
        this.hhid = hhid;
        notifyPropertyChanged(BR.hhid);
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
/*

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
*/

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getIStatus() {
        return iStatus;
    }

    public void setIStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getIStatus96x() {
        return iStatus96x;
    }

    public void setIStatus96x(String iStatus96x) {
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

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public EntryLog Hydrate(EntryLog entryLog) throws JSONException {
        this.id = entryLog.id;
        this.uid = entryLog.uid;
        this.uuid = entryLog.uuid;
        this.projectName = entryLog.projectName;
        this.hhid = entryLog.hhid;
        this.userName = entryLog.userName;
        this.sysDate = entryLog.sysDate;
        this.entryDate = entryLog.entryDate;
        this.entryType = entryLog.entryType;
        this.deviceId = entryLog.deviceId;
        this.appver = entryLog.appver;
        this.iStatus = entryLog.iStatus;
        this.iStatus96x = entryLog.iStatus96x;
        this.synced = entryLog.synced;
        this.syncDate = entryLog.syncDate;

        return this;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(EntryLogTable.COLUMN_ID, this.id);
        json.put(EntryLogTable.COLUMN_UID, this.uid);
        json.put(EntryLogTable.COLUMN_UUID, this.uuid);
        json.put(EntryLogTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(EntryLogTable.COLUMN_HHID, this.hhid);
        json.put(EntryLogTable.COLUMN_USERNAME, this.userName);
        json.put(EntryLogTable.COLUMN_SYSDATE, this.sysDate);
        json.put(EntryLogTable.COLUMN_ENTRY_DATE, this.entryDate);
        json.put(EntryLogTable.COLUMN_ENTRY_TYPE, this.entryType);
        json.put(EntryLogTable.COLUMN_DEVICEID, this.deviceId);
        json.put(EntryLogTable.COLUMN_ISTATUS, this.iStatus);
        json.put(EntryLogTable.COLUMN_ISTATUS96x, this.iStatus96x);
        json.put(EntryLogTable.COLUMN_SYNCED, this.synced);
        json.put(EntryLogTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(EntryLogTable.COLUMN_APPVERSION, this.appver);
        return json;
    }


}
