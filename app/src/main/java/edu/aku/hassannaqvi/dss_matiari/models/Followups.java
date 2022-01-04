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
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FollowupsTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class Followups extends BaseObservable implements Observable {

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
    private String sNo = StringUtils.EMPTY;
    private String visitNo = StringUtils.EMPTY;


    private String rc01v2 = "";
    private String rc01v3 = "";
    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES

    private String fRound = "";
    private String prePreg = "";
    private String rc01 = "";
    private String rc02 = "";
    private String rc03 = "";
    private String rc06 = "";
    private String rc07 = "";

    // Previous Pregnancy Information
    private String rc08 = "";
    private String rc09 = "";
    private String rc10 = StringUtils.EMPTY;
    private String rc11 = StringUtils.EMPTY;
    private String rc11x = StringUtils.EMPTY;
    private String rc12 = StringUtils.EMPTY;
    private String rc12x = StringUtils.EMPTY;
    private String rc13 = StringUtils.EMPTY;
    private String rc13x = StringUtils.EMPTY;

    public Followups() {

       /* setfRound(MainApp.round);
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        if (MainApp.households != null) {
            setUuid(MainApp.households.getUid());
        }
        setVillageCode(MainApp.selectedVillage);
        setUcCode(MainApp.selectedUC);*/

    }

    public void populateMeta() {

        MainApp.followups.setUuid(MainApp.fpHouseholds.getUid());
        MainApp.followups.setUcCode(MainApp.fpHouseholds.getUcCode());
        MainApp.followups.setVillageCode(MainApp.fpHouseholds.getVillageCode());
        MainApp.followups.setHhNo(MainApp.fpHouseholds.getHhNo());
        MainApp.followups.setfRound(MainApp.round);
        // TODO: set MWRA ID from downloaded data
        //   MainApp.followups.setMWRAID(households.getHhNo());
        MainApp.followups.setUserName(MainApp.user.getUserName());
        MainApp.followups.setSysDate(MainApp.fpHouseholds.getSysDate());
        MainApp.followups.setDeviceId(MainApp.deviceid);
        MainApp.followups.setHdssId(MainApp.fpHouseholds.getHdssId());
        MainApp.followups.setAppver(MainApp.versionName + "." + MainApp.versionCode);
    }

    @Bindable
    public String getfRound() {
        return fRound;
    }

    public void setfRound(String fRound) {
        this.fRound = fRound;
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

    public String getSno() {
        return sNo;
    }

    public void setSno(String sNo) {
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
    public String getRc01() {
        return rc01;
    }

    public void setRc01(String rc01) {
        this.rc01 = rc01;
        setSno(rc01);
        notifyChange(BR.rc01);
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
    public String getRc06() {
        return rc06;
    }

    public void setRc06(String rc06) {
        this.rc06 = rc06;
        Log.d(TAG, "setRc06: " + this.rc06);
        //setRc03(this.rc06.equals("4") ? "" : this.rc03);
        setRc07(this.rc06.equals("4") ? "" : this.rc07);
/*        setRc08(this.rc06.equals("4") ? "" : this.rc08);
        setRc09(this.rc06.equals("4") ? "" : this.rc09);*/
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
        Log.d(TAG, "setRc07: " + this.rc07);
        notifyChange(BR.rc07);
    }

    @Bindable
    public String getRc08() {
        return rc08;
    }

    public void setRc08(String rc08) {
        this.rc08 = rc08;
        if (!this.rc08.equals("")) {
            setRc09(calcEDD());
        } else {
            setRc09("");
        }
        notifyChange(BR.rc08);
    }

    @Bindable
    public String getRc09() {
        return rc09;
    }

    public void setRc09(String rc09) {
        this.rc09 = rc09;
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
    public String getRc12x() {
        return rc12x;
    }

    public void setRc12x(String rc12x) {
        this.rc12x = rc12x;
        notifyChange(BR.rc12x);
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
    public String getRc13x() {
        return rc13x;
    }

    public void setRc13x(String rc13x) {
        this.rc12x = rc13x;
        notifyChange(BR.rc13x);
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
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_UUID));
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
        sDHydrate(cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SD)));
        return this;
    }


    public void sCHydrate(String string) throws JSONException {
        Log.d(TAG, "s3Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);

            this.rc01 = json.getString("rc01");
            this.rc01v2 = json.has("rc01v2") ? json.getString("rc01v2") : "";
            this.rc01v3 = json.has("rc01v3") ? json.getString("rc01v3") : "";
            this.prePreg = json.getString("prePreg");
            this.rc02 = json.getString("rc02");
            this.rc03 = json.getString("rc03");

            this.rc06 = json.getString("rc06");
            this.rc07 = json.getString("rc07");
            this.rc08 = json.getString("rc08");
            this.rc09 = json.getString("rc09");
            this.rc11 = json.getString("rc11");
            this.rc11x = json.getString("rc11x");
            this.rc12 = json.getString("rc12");
            this.rc12x = json.getString("rc12x");
            this.rc13 = json.getString("rc13");
            this.rc13x = json.getString("rc13x");

        }
    }


    public String sCtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("rc01", rc01)
                .put("prePreg", prePreg)
                .put("rc01v2", rc01v2)
                .put("rc01v3", rc01v3)
                .put("rc02", rc02)
                .put("rc03", rc03)

                .put("rc06", rc06)
                .put("rc07", rc07)
                .put("rc08", rc08)
                .put("rc09", rc09)
                .put("rc11", rc11)
                .put("rc11x", rc11x)
                .put("rc12", rc12)
                .put("rc12x", rc12x)
                .put("rc13", rc13)
                .put("rc13x", rc13x)
        ;



        return json.toString();
    }

    public void sDHydrate(String string) throws JSONException {
        Log.d(TAG, "s4Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rc10 = json.getString("rc10");
            this.rc11 = json.getString("rc11");
            this.rc12 = json.getString("rc12");
            this.rc13 = json.getString("rc13");


        }
    }


    public String sDtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("rc10", rc10)
                .put("rc11", rc11)
                .put("rc12", rc12)
                .put("rc13", rc13);

        return json.toString();
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(TableContracts.FollowupsTable.COLUMN_ID, this.id);
        json.put(FollowupsTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(FollowupsTable.COLUMN_UID, this.uid);
        json.put(FollowupsTable.COLUMN_UUID, this.uuid);
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
        //  json.put(FollowupsTable.COLUMN_SYNCED, this.synced);
        //  json.put(FollowupsTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(FollowupsTable.COLUMN_SC, new JSONObject(sCtoString()));
        json.put(FollowupsTable.COLUMN_SD, new JSONObject(sDtoString()));
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
            cal.setTime(sdf.parse(getRc08()));// all done

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
