package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;

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

import com.google.gson.annotations.SerializedName;

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
    private transient final String TAG = "Outcome";
    //Not saving in DB
    @Ignore
    private transient final LocalDate localDate = null;
    @Ignore
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Ignore
    private transient boolean exist = false;
    @Ignore
    private transient boolean expanded;

    // APP VARIABLES
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_PROJECT_NAME)
    private String projectName = MainApp.PROJECT_NAME;

    // APP VARIABLES
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = true) @NonNull
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_ID)
    long id = 0;

    @SerializedName("_uid")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @SerializedName("_uuid")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_UUID)
    private String uuid = StringUtils.EMPTY;

    @SerializedName("_muid")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_MUID)
    private String muid = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_MSNO)
    private String msno = StringUtils.EMPTY;

    @SerializedName("username")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @SerializedName("sysdate")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @SerializedName("hdssid")
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

    @SerializedName("s5")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SE)
    private String sE = StringUtils.EMPTY;

    @SerializedName("deviceid")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_DEVICETAGID)
    private transient String deviceTag = StringUtils.EMPTY;

    @SerializedName("appversion")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    @SerializedName("istatus")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_ISTATUS)
    private transient String iStatus = StringUtils.EMPTY;

    @Ignore
    private transient String iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @SerializedName("synced_date")
    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_SYNCED_DATE)
    private transient String syncDate = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_ROUND)
    private String round = StringUtils.EMPTY;

    @ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_REGROUND)
    private String regRound = StringUtils.EMPTY;

    /*@ColumnInfo(name = TableContracts.OutcomeTable.COLUMN_FROUND)
    private String fRound = StringUtils.EMPTY;
*/
    @Ignore
    private String rb02 = StringUtils.EMPTY;
    /*@Ignore
    private String rb03 = StringUtils.EMPTY;
    */@Ignore
    private String rb01a = StringUtils.EMPTY;

    @Ignore
    private String rc01 = StringUtils.EMPTY;
    @Ignore
    private String rc02 = StringUtils.EMPTY;
    @Ignore
    private String rc03 = StringUtils.EMPTY;
    @Ignore
    private String rc04 = StringUtils.EMPTY;
    @Ignore
    private String rc05 = StringUtils.EMPTY;
    @Ignore
    private String rc06 = StringUtils.EMPTY;
    @Ignore
    private String rc07 = StringUtils.EMPTY;
    @Ignore
    private String rc08 = StringUtils.EMPTY;

    // For local use
    // This is used for resolving data while posting
    @ColumnInfo(defaultValue = "0")
    private transient boolean isError;

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

    @Bindable
    public String getIStatus() {
        return iStatus;
    }

    public void setIStatus(String iStatus) {
        this.iStatus = iStatus;
        notifyPropertyChanged(BR.iStatus);
    }

    @Bindable
    public String getiStatus96x() {
        return iStatus96x;
    }

    public void setiStatus96x(String iStatus96x) {
        this.iStatus96x = iStatus96x;
        notifyPropertyChanged(BR.iStatus96x);
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

    public String getRegRound() {
        return regRound;
    }

    public void setRegRound(String regRound) {
        this.regRound = regRound;
    }

   /* public String getFRound() {
        return fRound;
    }

    public void setFRound(String fRound) {
        this.fRound = fRound;
    }
*/
    @Bindable
    public String getRc03() {
        return rc03;
    }

    public void setRc03(String rc03) {
        this.rc03 = rc03;
        notifyPropertyChanged(BR.rc03);
    }

    @Bindable
    public String getRc01() {
        return rc01;
    }

    public void setRc01(String rc01) {
        this.rc01 = rc01;
        this.sno = rc01;
        notifyPropertyChanged(BR.rc01);
    }



    @Bindable
    public String getRc02() {
        return rc02;
    }

    public void setRc02(String rc02) {
        this.rc02 = rc02;
        notifyPropertyChanged(BR.rc02);
    }

    @Bindable
    public String getRc04() {
        return rc04;
    }

    @Bindable
    public String getRc05() {
        return rc05;
    }


    public void setRc04(String rc04) {
        this.rc04 = rc04;
        notifyPropertyChanged(BR.rc04);
    }

    public void setRc05(String rc05) {
        this.rc05 = rc05;
        setRc06(rc05.equals("1") ? this.rc06 : "");
        notifyPropertyChanged(BR.rc05);
    }

    public void setRc06(String rc06) {
        this.rc06 = rc06;
        notifyPropertyChanged(BR.rc06);
    }

    @Bindable
    public String getRc06() {
        return rc06;
    }

    @Bindable
    public String getRc07() {
        return rc07;
    }

    public void setRc07(String rc07) {
        this.rc07 = rc07;
        notifyPropertyChanged(BR.rc07);
    }

    @Bindable
    public String getRc08() {
        return rc08;
    }

    public void setRc08(String rc08) {
        this.rc08 = rc08;
        setRc05(rc08.equals("1") ? this.rc05 : "");
        setRc06(rc08.equals("1") ? this.rc06 : "");
        setRc07(rc08.equals("1") ? this.rc07 : "");
        setIStatus(rc08);
        notifyPropertyChanged(BR.rc08);
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Outcome Hydrate(Outcome outcome) throws JSONException {
        this.id = outcome.id;
        this.uid = outcome.uid;
        this.uuid = outcome.uuid;
        this.muid = outcome.muid;
        this.msno = outcome.msno;
        this.userName = outcome.userName;
        this.sysDate = outcome.sysDate;
        this.hdssId = outcome.hdssId;
        this.ucCode = outcome.ucCode;
        this.villageCode = outcome.villageCode;
        this.regRound = outcome.regRound;
        this.round = outcome.round;
        this.sno = outcome.sno;
        this.hhNo = outcome.hhNo;
        this.deviceId = outcome.deviceId;
        this.deviceTag = outcome.deviceTag;
        this.appver = outcome.appver;
        //this.iStatus = outcome.msno;
        this.synced = outcome.synced;
        this.syncDate = outcome.syncDate;

        sEHydrate(outcome.sE);
        return this;
    }

    public void sEHydrate(String string) throws JSONException {
        Log.d(TAG, "s5Hydrate: " + string);
        if (string != null && !string.equals("")) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb02 = json.getString("rb02");
            this.rb01a = json.getString("rc01a");
            this.rc01 = json.getString("rc01");
            this.rc02 = json.getString("rc02");
            this.rc03 = json.has("rc03") ? json.getString("rc03") : "";
            this.rc04 = json.getString("rc04");
            this.rc05 = json.getString("rc05");
            this.rc06 = json.getString("rc06");
            this.rc07 = json.getString("rc07");
            this.rc08 = json.has("rc08") ? json.getString("rc08") : "";


        }
    }


    public String sEtoString() throws JSONException {
        JSONObject json = new JSONObject();


        json
                .put("rb02", rb02)
                .put("rc01a", rb01a)
                .put("rc01", rc01)
                .put("rc02", rc02)
                .put("rc03", rc03)
                .put("rc04", rc04)
                .put("rc05", rc05)
                .put("rc06", rc06)
                .put("rc07", rc07)
                .put("rc08", rc08)
        ;


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
        json.put(TableContracts.OutcomeTable.COLUMN_REGROUND, this.regRound);
        //json.put(TableContracts.OutcomeTable.COLUMN_FROUND, this.fRound);
        json.put(TableContracts.OutcomeTable.COLUMN_ROUND, this.round);
        json.put(TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(TableContracts.OutcomeTable.COLUMN_SNO, this.sno);
        json.put(TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(TableContracts.OutcomeTable.COLUMN_DEVICEID, this.deviceId);
        //json.put(TableContracts.OutcomeTable.COLUMN_DEVICETAGID, this.deviceTag);
        //json.put(TableContracts.OutcomeTable.COLUMN_ISTATUS, this.iStatus);
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
        MainApp.outcome.setUuid(MainApp.mwra.getUid());
        MainApp.outcome.setMuid(MainApp.mwra.getUid().split("_")[0]);
        MainApp.outcome.setMsno(MainApp.mwra.getRb01());
        MainApp.outcome.setSysDate(MainApp.mwra.getSysDate());
        MainApp.outcome.setRb02(MainApp.mwra.getRb02());
        MainApp.outcome.setRb01a(MainApp.mwra.getRb01a());
        MainApp.outcome.setRound(MainApp.mwra.getRound());
        MainApp.outcome.setUcCode(MainApp.households.getUcCode());
        MainApp.outcome.setVillageCode(MainApp.households.getVillageCode());
        MainApp.outcome.setHhNo(MainApp.households.getHhNo());
        MainApp.outcome.setSno(String.valueOf(MainApp.prevChildCount));
        MainApp.outcome.setUserName(MainApp.user.getUsername());
        MainApp.outcome.setDeviceId(MainApp.deviceid);
        MainApp.outcome.setHdssId(MainApp.households.getHdssId());
        MainApp.outcome.setAppver(MainApp.versionName + "." + MainApp.versionCode);
        MainApp.outcome.setRegRound("1");
        //MainApp.outcome.setFRound("0");
        //MainApp.outcome.setRb01a(MainApp.fpMwra.getRa01());

    }


    public void populateMetaFollowups() {
        MainApp.outcome.setUserName(MainApp.user.getUsername());
        MainApp.outcome.setDeviceId(MainApp.deviceid);
        MainApp.outcome.setAppver(MainApp.versionName + "." + MainApp.versionCode);

        // From Households
        MainApp.outcome.setSysDate(MainApp.households.getSysDate());
        MainApp.outcome.setUuid(MainApp.households.getUid());


        // From FollowupsSche - Outcome
        MainApp.outcome.setHdssId(MainApp.fpMwra.getHdssid());
        MainApp.outcome.setUcCode(MainApp.fpMwra.getUcCode());
        MainApp.outcome.setVillageCode(MainApp.fpMwra.getVillageCode());
        MainApp.outcome.setHhNo(MainApp.fpMwra.getHhNo());
        MainApp.outcome.setRound(MainApp.fpMwra.getFRound());
        MainApp.outcome.setSno(MainApp.fpMwra.getRb01());
        MainApp.outcome.setRc01(MainApp.fpMwra.getRb01());  // Line number of child
        MainApp.outcome.setRc02(MainApp.fpMwra.getRb02());  // Name of child
        MainApp.outcome.setRb02(MainApp.fpMwra.getRb03());  // Name of mother
        MainApp.outcome.setMuid(MainApp.fpMwra.getMuid().split("_")[0]);
        MainApp.outcome.setRc03(MainApp.fpMwra.getRb04());  // Date of Birth of child
        MainApp.outcome.setMsno(MainApp.fpMwra.getMsno());
        MainApp.outcome.setRc04(MainApp.fpMwra.getRc04());
        setRegRound("");

    }
 }
