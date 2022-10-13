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
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FollowupsTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

@Entity(tableName = FollowupsTable.TABLE_NAME)

public class Followups extends BaseObservable implements Observable {

    public final String TAG = "MWRA";
    //Not saving in DB
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;
    private boolean expanded;

    // APP VARIABLES
    @ColumnInfo(name = FollowupsTable.COLUMN_PROJECT_NAME)
    private String projectName = MainApp.PROJECT_NAME;

    // APP VARIABLES

    @PrimaryKey(autoGenerate = true) @NonNull
    @ColumnInfo(name = FollowupsTable.COLUMN_ID)
    private long id = 0;

    @ColumnInfo(name = FollowupsTable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_UUID)
    private String uuid = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_FMUID)
    private String fmuid = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_SNO)
    private String sNo = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_VISIT_NO)
    private String visitNo = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_DEVICETAGID)
    private String deviceTag = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_ISTATUS)
    private String iStatus = StringUtils.EMPTY;

    private String iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @ColumnInfo(name = FollowupsTable.COLUMN_SYNCED_DATE)
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES

    @ColumnInfo(name =FollowupsTable.COLUMN_SC)
    private String SC = StringUtils.EMPTY;
    private String rc01a = "";
    private String rc01v2 = "";
    private String rc01v3 = "";

    private String fRound = "";
    private String prePreg = "";
    private String rb06 = "";
    private String rc01 = "";
    private String rc02 = "";
    private String rc03 = "";
    private String rc03a = "";
    private String rc03b = "";
    private String rc04 = "";
    private String rc05 = "";
    private String rc05a = "";
    private String rc05b = "";
    private String rc06 = "";
    private String rc07 = "";

    // Previous Pregnancy Information
    private String rc08 = "";
    private String rc09 = "";
    private String rc10 = StringUtils.EMPTY;
    private String rc11 = StringUtils.EMPTY;
    private String rc11x = StringUtils.EMPTY;

    private String rc12 = StringUtils.EMPTY;
    private String rc13 = StringUtils.EMPTY;

    private String rc14 = StringUtils.EMPTY;
    private String rc15 = StringUtils.EMPTY;
    private String rc16 = StringUtils.EMPTY;
    private String rc17 = StringUtils.EMPTY;

    public Followups() {

    }

    public void populateMeta() {

        // From MainApp
        MainApp.followups.setUserName(MainApp.user.getUserName());
        MainApp.followups.setDeviceId(MainApp.deviceid);
        MainApp.followups.setAppver(MainApp.versionName + "." + MainApp.versionCode);

        // From fpHouseholds
        MainApp.followups.setSysDate(MainApp.fpHouseholds.getSysDate());
        MainApp.followups.setUuid(MainApp.fpHouseholds.getUid());

        // From FollowupsSche - MWRA
        MainApp.followups.setUcCode(MainApp.fpMwra.getUcCode());
        MainApp.followups.setVillageCode(MainApp.fpMwra.getVillageCode());
        MainApp.followups.setFmuid(MainApp.fpMwra.getMuid());
        MainApp.followups.setHhNo(MainApp.fpMwra.getHhNo());
        MainApp.followups.setFRound(MainApp.fpMwra.getFRound());
        MainApp.followups.setSNo(MainApp.fpMwra.getRb01());
        MainApp.followups.setHdssId(MainApp.fpMwra.getHdssid());
        MainApp.followups.setRb06(MainApp.fpMwra.getRb06());
        MainApp.followups.setPrePreg(MainApp.fpMwra.getRb07());



    }

    @Bindable
    public String getFRound() {
        return fRound;
    }

    public void setFRound(String fRound) {
        this.fRound = fRound;
        notifyChange(BR.round);
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


    public String getFmuid() {
        return fmuid;
    }

    public void setFmuid(String fmuid) {
        this.fmuid = fmuid;
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

    public String getSNo() {
        return sNo;
    }

    public void setSNo(String sNo) {
        this.sNo = sNo;
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

    public String getPrePreg() {
        return prePreg;
    }

    public void setPrePreg(String prePreg) {
        this.prePreg = prePreg;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public PropertyChangeRegistry getPropertyChangeRegistry() {
        return propertyChangeRegistry;
    }

    public void setPropertyChangeRegistry(PropertyChangeRegistry propertyChangeRegistry) {
        this.propertyChangeRegistry = propertyChangeRegistry;
    }

    public String getSC() {
        return SC;
    }

    public void setSC(String SC) {
        this.SC = SC;
    }

    @Bindable
    public String getRc03a() {
        return rc03a;
    }

    public void setRc03a(String rc03a) {
        this.rc03a = rc03a;
        notifyChange(BR.rc03a);
    }

    @Bindable
    public String getRc03b() {
        return rc03b;
    }

    public void setRc03b(String rc03b) {
        this.rc03b = rc03b;
        notifyChange(BR.rc03b);
    }

    @Bindable
    public String getRc05a() {
        return rc05a;
    }

    public void setRc05a(String rc05a) {
        this.rc05a = rc05a;
        notifyChange(BR.rc05a);
    }

    @Bindable
    public String getRc05b() {
        return rc05b;
    }

    public void setRc05b(String rc05b) {
        this.rc05b = rc05b;
        notifyChange(BR.rc05b);
    }

    @Bindable
    public String getRb06() {
        return rb06;
    }

    public void setRb06(String rb06) {
        this.rb06 = rb06;
        notifyChange(BR.rb06);
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
        notifyChange(BR.rc01v2);
    }

    public void setRc01v3(String rc01v3) {
        this.rc01v3 = rc01v3;
        notifyChange(BR.rc01v3);
    }

    @Bindable
    public String getRc01a() {
        return rc01a;
    }

    public void setRc01a(String ra01) {
        this.rc01a = ra01;
    }

    @Bindable
    public String getRc01() {
        return rc01;
    }

    public void setRc01(String rc01) {
        this.rc01 = rc01;
        setSNo(rc01);
        notifyChange(BR.rc01);
    }


    @Bindable
    public String getRc12() {
        return rc12;
    }

    public void setRc12(String rc12) {
        this.rc12 = rc12;

        notifyChange(BR.rc12);
    }

    @Bindable
    public String getRc13() {
        return rc13;
    }

    public void setRc13(String rc13) {
        this.rc13 = rc13;

        notifyChange(BR.rc13);
    }

    @Bindable
    public String getRc14() {
        return rc14;
    }

    public void setRc14(String rc14) {
        this.rc14 = rc14;

        notifyChange(BR.rc14);
    }


    @Bindable
    public String getRc02() {
        return rc02;
    }

    public void setRc02(String rc02) {
        this.rc02 = rc02;
        notifyChange(BR.rc02);
    }

    @Bindable
    public String getRc03() {
        return rc03;
    }

    public void setRc03(String rc03) {
        this.rc03 = rc03;
        notifyChange(BR.rc03);
    }



    @Bindable
    public String getRc04() {
        return rc04;
    }

    public void setRc04(String rc04) {
        this.rc04 = rc04;
        setRc05(rc04.equals("1") ? "1" : rc04.equals("7") ? "2" : "");
        setRc08(rc04.equals("1") ? this.rc08 : "");
        setRc09(rc04.equals("1") ? this.rc09 : "");
        setRc10(rc04.equals("1") ? this.rc10 : "");
        setRc11(rc04.equals("1") ? this.rc11 : "");
        setRc15(rc04.equals("1") ? this.rc15 : "");
        setRc16(rc04.equals("1") ? this.rc16 : "");
        setRc17(rc04.equals("1") ? this.rc17 : "");
        notifyChange(BR.rc04);
    }


    @Bindable
    public String getRc05() {
        return rc05;
    }

    public void setRc05(String rc05) {
        this.rc05 = rc05;
        setRc05a(rc05.equals("1") ? "" : this.rc05a);
        setRc05b(rc05.equals("1") ? "" : this.rc05b);
        setRc08(rc05.equals("1") ? this.rc08 : "");
        setRc09(rc05.equals("1") ? this.rc09 : "");
        setRc10(rc05.equals("1") ? this.rc10 : "");
        setRc11(rc05.equals("1") ? this.rc11 : "");
        setRc15(rc05.equals("1") ? this.rc15 : "");
        setRc16(rc05.equals("1") ? this.rc16 : "");
        setRc17(rc05.equals("1") ? this.rc17 : "");

        notifyChange(BR.rc05);
    }

    @Bindable
    public String getRc06() {
        return rc06;
    }

    public void setRc06(String rc06) {
        this.rc06 = rc06;
        Log.d(TAG, "setRc06: " + this.rc06);
        setRc07(rc06.equals("4") ? "" : this.rc07);
        setRc08(rc06.equals("4") ? "" : this.rc08);
        setRc09(rc06.equals("4") ? "" : this.rc09);
        setRc10(rc06.equals("4") ? "" : this.rc10);
        setRc11(rc06.equals("4") ? "" : this.rc11);
        setRc15(rc06.equals("4") ? "" : this.rc15);
        setRc16(rc06.equals("4") ? "" : this.rc16);
        setRc17(rc06.equals("4") ? "" : this.rc17);

        notifyChange(BR.rc06);
    }

    @Bindable
    public String getRc07() {
        return rc07;
    }

    public void setRc07(String rc07) {
        this.rc07 = rc07;
        setRc08(this.rc07.equals("1") ? this.rc08 : "");
        setRc09(this.rc07.equals("1") ? this.rc09 : "");
        setRc10(this.rc07.equals("1") ? this.rc10 : "");
        setRc11(this.rc07.equals("1") ? this.rc11 : "");

        Log.d(TAG, "setRc07: " + this.rc07);
        notifyChange(BR.rc07);
    }

    @Bindable
    public String getRc08() {
        return rc08;
    }

    public void setRc08(String rc08) {
        this.rc08 = rc08;
        setRc09(this.rc08.equals("1") ? "" : this.rc09);
        setRc10(this.rc08.equals("1") ? "" : this.rc10);
        setRc11(this.rc08.equals("1") ? "" : this.rc11);
        setRc15(this.rc08.equals("1") ? "" : this.rc15);
        setRc16(this.rc08.equals("1") ? "" : this.rc16);
        setRc17(this.rc08.equals("1") ? "" : this.rc17);

        notifyChange(BR.rc08);
    }

    @Bindable
    public String getRc09() {
        return rc09;
    }

    public void setRc09(String rc09) {
        this.rc09 = rc09;
        setRc10(this.rc09.equals("3") || this.rc09.equals("4") ? "" : this.rc10);
        setRc11(this.rc09.equals("3") || this.rc09.equals("4") ? "" : this.rc11);
        notifyChange(BR.rc09);
    }

    @Bindable
    public String getRc10() {
        return rc10;
    }

    public void setRc10(String rc10) {
        this.rc10 = rc10;

        notifyChange(BR.rc10);
    }

    @Bindable
    public String getRc11() {
        return rc11;
    }

    public void setRc11(String rc11) {
        this.rc11 = rc11;
        notifyChange(BR.rc11);
    }

    @Bindable
    public String getRc11x() {
        return rc11x;
    }

    public void setRc11x(String rc11x) {
        this.rc11x = rc11x;
        notifyChange(BR.rc11x);
    }

    @Bindable
    public String getRc15() {
        return rc15;
    }

    public void setRc15(String rc15) {
        this.rc15 = rc15;

        setRc16(this.rc15.equals("1") ? this.rc16 : "");
        setRc17(this.rc15.equals("1") ? this.rc17 : "");


        notifyChange(BR.rc15);
    }

    @Bindable
    public String getRc16() {
        return rc16;
    }

    public void setRc16(String rc16) {
        this.rc16 = rc16;
        if (!this.rc16.equals("")) {
            setRc17(calcEDD());
        } else {
            setRc17("");
        }
        notifyChange(BR.rc16);
    }

    @Bindable
    public String getRc17() {
        return rc17;
    }

    public void setRc17(String rc17) {
        this.rc17 = rc17;
        notifyChange(BR.rc17);
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

    public Followups Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getLong(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_UUID));
        this.fmuid = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_FMUID));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_VILLAGE_CODE));
        this.visitNo = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_VISIT_NO));
        this.sNo = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SNO));
        this.fRound = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_FP_ROUND));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_HOUSEHOLD_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SYNCED_DATE));

        sCHydrate(cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SC)));
        return this;
    }


    public void sCHydrate(String string) throws JSONException {
        Log.d(TAG, "s3Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);

            this.rc01 = json.getString("rc01");
            this.rc01a = json.getString("rc01a");
            this.rc01v2 = json.has("rc01v2") ? json.getString("rc01v2") : "";
            this.rc01v3 = json.has("rc01v3") ? json.getString("rc01v3") : "";
            this.prePreg = json.getString("prePreg");
            this.rb06 = json.getString("rb06");
            this.rc02 = json.getString("rc02");
            this.rc03 = json.getString("rc03");
            this.rc03a = json.getString("rc03a");
            this.rc03b = json.has("rc03b") ? json.getString("rc03b") : "";
            this.rc04 = json.getString("rc04");
            this.rc05 = json.getString("rc05");
            this.rc05a = json.getString("rc05a");
            this.rc05b = json.getString("rc05b");
            this.rc06 = json.getString("rc06");
            this.rc07 = json.getString("rc07");
            this.rc08 = json.getString("rc08");
            this.rc09 = json.getString("rc09");
            this.rc10 = json.has("rc10") ? json.getString("rc10") : "";
            this.rc11 = json.getString("rc11");
            this.rc11x = json.getString("rc11x");
            this.rc15 = json.getString("rc15");
            this.rc16 = json.getString("rc16");
            this.rc17 = json.getString("rc17");

        }
    }


    public String sCtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json
                .put("rc01a", rc01a)
                .put("rc01", rc01)
                .put("prePreg", prePreg)
                .put("rb06", rb06)
                .put("rc01v2", rc01v2)
                .put("rc01v3", rc01v3)
                .put("rc02", rc02)
                .put("rc03", rc03)
                .put("rc03a", rc03a)
                .put("rc03b", rc03b)
                .put("rc04", rc04)
                .put("rc05", rc05)
                .put("rc05a", rc05a)
                .put("rc05b", rc05b)
                .put("rc06", rc06)
                .put("rc07", rc07)
                .put("rc08", rc08)
                .put("rc09", rc09)
                .put("rc10", rc10)
                .put("rc11", rc11)
                .put("rc11x", rc11x)
                .put("rc15", rc15)
                .put("rc16", rc16)
                .put("rc17", rc17)

        ;



        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(TableContracts.FollowupsTable.COLUMN_ID, this.id);
        json.put(FollowupsTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(FollowupsTable.COLUMN_UID, this.uid);
        json.put(FollowupsTable.COLUMN_UUID, this.uuid);
        json.put(FollowupsTable.COLUMN_FMUID, this.fmuid);
        json.put(FollowupsTable.COLUMN_USERNAME, this.userName);
        json.put(FollowupsTable.COLUMN_SYSDATE, this.sysDate);
        json.put(FollowupsTable.COLUMN_HDSSID, this.hdssId);
        json.put(FollowupsTable.COLUMN_UC_CODE, this.ucCode);
        json.put(FollowupsTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(FollowupsTable.COLUMN_FP_ROUND, this.fRound);
        json.put(FollowupsTable.COLUMN_SNO, this.sNo);
        json.put(FollowupsTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(FollowupsTable.COLUMN_VISIT_NO, this.visitNo);
        json.put(FollowupsTable.COLUMN_DEVICEID, this.deviceId);
        json.put(FollowupsTable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(FollowupsTable.COLUMN_ISTATUS, this.iStatus);
        json.put(FollowupsTable.COLUMN_APPVERSION, this.appver);
        json.put(FollowupsTable.COLUMN_SC, new JSONObject(sCtoString()));

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

    public String calcEDD() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            cal.setTime(sdf.parse(getRc16()));// all done

            // Set EDD by default
            cal.add(Calendar.DAY_OF_YEAR, 7);
            cal.add(Calendar.MONTH, 9);

            return sdf.format(cal.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
