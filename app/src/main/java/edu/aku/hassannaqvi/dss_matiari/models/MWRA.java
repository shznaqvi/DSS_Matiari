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
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class MWRA extends BaseObservable implements Observable {

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
    private String sB = StringUtils.EMPTY;

    private String round = "";
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

        setRound(MainApp.round);
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setAppver(MainApp.appInfo.getAppVersion());
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

    public String getSB() {
        return sB;
    }

    public void setsB(String sB) {
        this.sB = sB;
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
        setRb05(this.rb04.equals("98") ? "" : this.rb05);
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
        //setRb03(this.rb06.equals("4") ? "" : this.rb03);
        setRb07(this.rb06.equals("4") ? "" : this.rb07);
/*        setRb08(this.rb06.equals("4") ? "" : this.rb08);
        setRb09(this.rb06.equals("4") ? "" : this.rb09);*/
        notifyChange(BR.rb06);
    }

    @Bindable
    public String getRb07() {
        return rb07;
    }

    public void setRb07(String rb07) {
        this.rb07 = rb07;
        setRb08(this.rb07.equals("1") ? this.rb08 : "");
        setRb09(this.rb07.equals("1") ? this.rb09 : "");
        Log.d(TAG, "setRb07: " + this.rb07);
        notifyChange(BR.rb07);
    }

    @Bindable
    public String getRb08() {
        return rb08;
    }

    public void setRb08(String rb08) {
        this.rb08 = rb08;
        if (!this.rb08.equals("")) {
            setRb09(calcEDD());
        } else {
            setRb09("");
        }
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

    public MWRA Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UUID));
        this.userName = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_VILLAGE_CODE));
        this.structureNo = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_STRUCTURE_NO));
        this.hhNo = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_HOUSEHOLD_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCED_DATE));

        sBHydrate(cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB)));
        return this;
    }


    public void sBHydrate(String string) throws JSONException {
        Log.d(TAG, "s2Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb01 = json.getString("rb01");
            this.round = json.getString("round");
            this.rb02 = json.getString("rb02");
            this.rb03 = json.getString("rb03");
            this.rb04 = json.getString("rb04");
            this.rb05 = json.getString("rb05");
            this.rb06 = json.getString("rb06");
            this.rb07 = json.getString("rb07");
            this.rb08 = json.getString("rb08");
            this.rb09 = json.getString("rb09");

        }
    }


    public String sBtoString() throws JSONException {
        JSONObject json = new JSONObject();


        json.put("rb01", rb01)
                .put("round", round)
                .put("rb02", rb02)
                .put("rb03", rb03)
                .put("rb04", rb04)
                .put("rb05", rb05)
                .put("rb06", rb06)
                .put("rb07", rb07)
                .put("rb08", rb08)
                .put("rb09", rb09);

        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(MWRATable.COLUMN_ID, this.id);
        json.put(MWRATable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(MWRATable.COLUMN_UID, this.uid);
        json.put(MWRATable.COLUMN_UUID, this.uuid);
        json.put(MWRATable.COLUMN_USERNAME, this.userName);
        json.put(MWRATable.COLUMN_SYSDATE, this.sysDate);
        json.put(MWRATable.COLUMN_HDSSID, this.hdssId);
        json.put(MWRATable.COLUMN_UC_CODE, this.ucCode);
        json.put(MWRATable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(MWRATable.COLUMN_STRUCTURE_NO, this.structureNo);
        json.put(MWRATable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(MWRATable.COLUMN_DEVICEID, this.deviceId);
        json.put(MWRATable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(MWRATable.COLUMN_ISTATUS, this.iStatus);
        json.put(MWRATable.COLUMN_APPVERSION, this.appver);
        //  json.put(MWRATable.COLUMN_SYNCED, this.synced);
        //  json.put(MWRATable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(MWRATable.COLUMN_SB, new JSONObject(sBtoString()));
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
            cal.setTime(sdf.parse(getRb08()));// all done

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
