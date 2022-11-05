package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

@Entity(tableName = TableContracts.OutcomeTable.TABLE_NAME)
public class Outcome extends BaseObservable implements Observable {


    @Ignore
    private final String TAG = "Outcome";
    //Not saving in DB
    @Ignore
    private final LocalDate localDate = null;
    @Ignore
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Ignore
    private boolean exist = false;
    @Ignore
    private boolean expanded;

    // APP VARIABLES
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_PROJECT_NAME)
    private String projectName = MainApp.PROJECT_NAME;

    // APP VARIABLES
    @PrimaryKey @NonNull
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_ID)
    private long id = 0;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_UUID)
    private String uuid = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_MUID)
    private String muid = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_MSNO)
    private String msno = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SNO)
    private String sno = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SE)
    private String sE = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_DEVICETAGID)
    private String deviceTag = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_ISTATUS)
    private String iStatus = StringUtils.EMPTY;

    @Ignore
    private String iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYNCED_DATE)
    private String syncDate = StringUtils.EMPTY;

    @Ignore
    private String round = StringUtils.EMPTY;
    @Ignore
    private String rb02 = StringUtils.EMPTY;
    @Ignore
    private String rb01a = StringUtils.EMPTY;

    @Ignore
    private String rc12ln = StringUtils.EMPTY;
    @Ignore
    private String rc12nm = StringUtils.EMPTY;
    @Ignore
    private String rc12dob = StringUtils.EMPTY;
    @Ignore
    private String rc12 = StringUtils.EMPTY;
    @Ignore
    private String rc13 = StringUtils.EMPTY;
    @Ignore
    private String rc14 = StringUtils.EMPTY;
    @Ignore
    private String rc14a = StringUtils.EMPTY;
    @Ignore
    private String rc16 = StringUtils.EMPTY;


    public Outcome() {


    }


    @Bindable
    public String getRb02() {
        return rb02;
    }

    public void setRb02(String rb02) {
        this.rb02 = rb02;
        notifyPropertyChanged(BR.rb02);
    }

    @Bindable
    public String getRb01a() {
        return rb01a;
    }

    public void setRb01a(String rb01a) {
        this.rb01a = rb01a;
        notifyPropertyChanged(BR.rb01a);
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


    public long getId() {
        return id;
    }

    public String getSE() {
        return sE;
    }

    public void setSE(String sE) {
        this.sE = sE;
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

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }


    public String getMsno() {
        return msno;
    }

    public void setMsno(String msno) {
        this.msno = msno;
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

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    @Bindable
    public String getRc12dob() {
        return rc12dob;
    }

    public void setRc12dob(String rc12dob) {
        this.rc12dob = rc12dob;
        notifyPropertyChanged(BR.rc12dob);
    }

    @Bindable
    public String getRc12ln() {
        return rc12ln;
    }

    public void setRc12ln(String rc12ln) {
        this.rc12ln = rc12ln;
        this.sno = rc12ln;
        notifyPropertyChanged(BR.rc12ln);
    }

    @Bindable
    public String getRc12nm() {
        return rc12nm;
    }

    public void setRc12nm(String rc12nm) {
        this.rc12nm = rc12nm;
        notifyPropertyChanged(BR.rc12nm);
    }

    @Bindable
    public String getRc12() {
        return rc12;
    }

    @Bindable
    public String getRc13() {
        return rc13;
    }


    public void setRc12(String rc12) {
        this.rc12 = rc12;
        notifyPropertyChanged(BR.rc12);
    }

    public void setRc13(String rc13) {
        this.rc13 = rc13;
        setRc14(rc13.equals("1") ? this.rc14 : "");
        notifyPropertyChanged(BR.rc13);
    }

    public void setRc14(String rc14) {
        this.rc14 = rc14;
        notifyPropertyChanged(BR.rc14);
    }

    @Bindable
    public String getRc14() {
        return rc14;
    }

    @Bindable
    public String getRc14a() {
        return rc14a;
    }

    public void setRc14a(String rc14a) {
        this.rc14a = rc14a;
        setRc16(rc14a.equals("1") ? "" : this.rc16);
        notifyPropertyChanged(BR.rc14a);
    }

    @Bindable
    public String getRc16() {
        return rc16;
    }

    public void setRc16(String rc16) {
        this.rc16 = rc16;
        notifyPropertyChanged(BR.rc16);
    }




    public Outcome Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getLong(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_UUID));
        this.muid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_MUID));
        this.msno = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_MSNO));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE));
        this.sno = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_SNO));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_SYNCED_DATE));

        sEHydrate(cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.OutcomeTable.COLUMN_SE)));
        return this;
    }

    public void sEHydrate(String string) throws JSONException {
        Log.d(TAG, "s5Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.round = json.getString("ROUND");
            this.rb02 = json.getString("rb02");
            this.rb01a = json.getString("rb01a");
            this.rc12ln = json.getString("rc12ln");
            this.rc12nm = json.getString("rc12nm");
            this.rc12dob = json.has("rc12dob") ? json.getString("rc12dob") : "";
            this.rc12 = json.getString("rc12");
            this.rc13 = json.getString("rc13");
            this.rc14 = json.getString("rc14");
            this.rc14a = json.getString("rc14a");
            this.rc16 = json.getString("rc16");


        }
    }


    public String sEtoString() throws JSONException {
        JSONObject json = new JSONObject();


        json
                .put("ROUND", round)
                .put("rb02", rb02)
                .put("rb01a", rb01a)
                .put("rc12ln", rc12ln)
                .put("rc12nm", rc12nm)
                .put("rc12dob", rc12dob)
                .put("rc12", rc12)
                .put("rc13", rc13)
                .put("rc14", rc14)
                .put("rc14a", rc14a)
                .put("rc16", rc16);

        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(TableContracts.OutcomeTable.COLUMN_ID, this.id);
        json.put(TableContracts.OutcomeTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(TableContracts.OutcomeTable.COLUMN_UID, this.uid);
        json.put(TableContracts.OutcomeTable.COLUMN_UUID, this.uuid);
        json.put(TableContracts.OutcomeTable.COLUMN_MUID, this.muid);
        json.put(TableContracts.OutcomeTable.COLUMN_MSNO, this.msno);
        json.put(TableContracts.OutcomeTable.COLUMN_USERNAME, this.userName);
        json.put(TableContracts.OutcomeTable.COLUMN_SYSDATE, this.sysDate);
        json.put(TableContracts.OutcomeTable.COLUMN_HDSSID, this.hdssId);
        json.put(TableContracts.OutcomeTable.COLUMN_UC_CODE, this.ucCode);
        json.put(TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(TableContracts.OutcomeTable.COLUMN_SNO, this.sno);
        json.put(TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(TableContracts.OutcomeTable.COLUMN_DEVICEID, this.deviceId);
        json.put(TableContracts.OutcomeTable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(TableContracts.OutcomeTable.COLUMN_ISTATUS, this.iStatus);
        json.put(TableContracts.OutcomeTable.COLUMN_APPVERSION, this.appver);
        //  json.put(MWRATable.COLUMN_SYNCED, this.synced);
        //  json.put(MWRATable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(TableContracts.OutcomeTable.COLUMN_SE, new JSONObject(sEtoString()));
        return json;

    }


    @Bindable
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        notifyPropertyChanged(BR.expanded);
    }

    public void populateMeta() {
        MainApp.outcome.setUuid(MainApp.followups.getUid());
        MainApp.outcome.setMuid(MainApp.followups.getUid().split("_")[0]);
        MainApp.outcome.setMsno(MainApp.followups.getRc01());
        MainApp.outcome.setSysDate(MainApp.followups.getSysDate());
        MainApp.outcome.setRb02(MainApp.followups.getRc02());
        MainApp.outcome.setRb01a(MainApp.followups.getRc01a());
        MainApp.outcome.setRound(MainApp.followups.getFRound());
        MainApp.outcome.setUcCode(MainApp.fpHouseholds.getUcCode());
        MainApp.outcome.setVillageCode(MainApp.fpHouseholds.getVillageCode());
        MainApp.outcome.setHhNo(MainApp.fpHouseholds.getHhNo());
        MainApp.outcome.setSno(String.valueOf(MainApp.childCount));

        // TODO: set MWRA ID from downloaded data
        //   MainApp.followups.setMWRAID(households.getHhNo());
        MainApp.outcome.setUserName(MainApp.user.getUserName());

        MainApp.outcome.setDeviceId(MainApp.deviceid);
        MainApp.outcome.setHdssId(MainApp.fpHouseholds.getHdssId());
        MainApp.outcome.setAppver(MainApp.versionName + "." + MainApp.versionCode);
        //MainApp.outcome.setRb01a(MainApp.fpMwra.getRa01());

    }

 }
