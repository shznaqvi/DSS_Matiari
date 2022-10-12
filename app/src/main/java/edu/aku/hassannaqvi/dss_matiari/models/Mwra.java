package edu.aku.hassannaqvi.dss_matiari.models;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

@Entity(tableName = MWRATable.TABLE_NAME)
public class Mwra extends BaseObservable implements Observable {

    public String TAG = "MWRA";
    //Not saving in DB
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private boolean exist = false;
    private boolean expanded;

    // APP VARIABLES
    @ColumnInfo(name = MWRATable.COLUMN_PROJECT_NAME)
    private String projectName = PROJECT_NAME;

    // APP VARIABLES

    @PrimaryKey(autoGenerate = true) @NonNull
    @ColumnInfo(name = MWRATable.COLUMN_ID)
    long id = 0;

    @ColumnInfo(name = MWRATable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_UUID)
    private String uuid = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_STRUCTURE_NO)
    private String structureNo = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_SNO)
    private String sNo = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_DEVICETAGID)
    private String deviceTag = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_ISTATUS)
    private String iStatus = StringUtils.EMPTY;

    private String iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_SYNCED_DATE)
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES
    @ColumnInfo(name = MWRATable.COLUMN_SB)
    private String sB = StringUtils.EMPTY;

    private String round = StringUtils.EMPTY;;
    private String rb01a = StringUtils.EMPTY;;

    private String rb01 = StringUtils.EMPTY;;
    private String rb02 = StringUtils.EMPTY;;
    private String rb03 = StringUtils.EMPTY;;
    private String rb04 = StringUtils.EMPTY;;
    private String rb05 = StringUtils.EMPTY;;
    private String rb06 = StringUtils.EMPTY;;
    private String rb07 = StringUtils.EMPTY;;
    private String rb08 = StringUtils.EMPTY;;
    private String rb09 = StringUtils.EMPTY;;
    private long ageInMonths;


    public Mwra() {

        setRound(MainApp.ROUND);
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

    public void populateMeta() {

        setSysDate(MainApp.households.getSysDate());
        setUuid(MainApp.households.getUid());  // not applicable in Form table
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
        setRound(MainApp.ROUND);
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

    public String getSB() {
        return sB;
    }

    public void setSB(String sB) {
        this.sB = sB;
    }


    public PropertyChangeRegistry getPropertyChangeRegistry() {
        return propertyChangeRegistry;
    }

    public void setPropertyChangeRegistry(PropertyChangeRegistry propertyChangeRegistry) {
        this.propertyChangeRegistry = propertyChangeRegistry;
    }


    public long getAgeInMonths() {
        return ageInMonths;
    }

    public void setAgeInMonths(long ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    @Bindable
    public String getRb01() {
        return rb01;
    }

    public void setRb01(String rb01) {
        this.rb01 = rb01;
        this.sNo = rb01;
        notifyChange(BR.rb01);
    }

    @Bindable
    public String getRb01a() {
        return rb01a;
    }

    public void setRb01a(String ra01) {
        this.rb01a = ra01;
        notifyChange(BR.ra01);
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
        if (!this.rb04.equals("98")) {
            setRb05(this.rb05);
            CaluculateAge();
        } else {
            setRb05("");

        }
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
        Log.d(TAG, "setRc06: " + this.rb06);
        //setRc03(this.rb06.equals("4") ? "" : this.rb03);
        setRb07(this.rb06.equals("4") ? "" : this.rb07);
/*        setRc08(this.rb06.equals("4") ? "" : this.rb08);
        setRc09(this.rb06.equals("4") ? "" : this.rb09);*/
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
        Log.d(TAG, "setRc07: " + this.rb07);
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

    public Mwra Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getLong(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_UUID));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_SYSDATE));
        this.hdssId = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_HDSSID));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_VILLAGE_CODE));
        this.structureNo = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_STRUCTURE_NO));
        this.sNo = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_SNO));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_HOUSEHOLD_NO));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_SYNCED_DATE));

        sBHydrate(cursor.getString(cursor.getColumnIndexOrThrow(MWRATable.COLUMN_SB)));
        return this;
    }


    public void sBHydrate(String string) throws JSONException {
        Log.d(TAG, "s2Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb01 = json.getString("rb01");
            this.rb01a = json.getString("rb01a");
            this.round = json.getString("ROUND");
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
                .put("rb01a", rb01a)
                .put("ROUND", round)
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
        json.put(MWRATable.COLUMN_SNO, this.sNo);
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


    private void CaluculateAge() {


        setRb05("");


        try {
            Calendar cal = Calendar.getInstance();
            Calendar cur = Calendar.getInstance();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(mwra.getRb01a()));// all done


            //long millis = System.currentTimeMillis() - cal.getTimeInMillis();
            long millis = cur.getTimeInMillis() - cal.getTimeInMillis();
            cal.setTimeInMillis(millis);


            this.ageInMonths = MILLISECONDS.toDays(millis) / 30;
            long tYear = MILLISECONDS.toDays(millis) / 365;
            long tMonth = (MILLISECONDS.toDays(millis) - (tYear * 365)) / 30;
            long tDay = MILLISECONDS.toDays(millis) - ((tYear * 365) + (tMonth * 30));

            Log.d(TAG, "CaluculateAge: Y-" + tYear + " M-" + tMonth + " D-" + tDay);
               /* setH231d(String.valueOf(tDay));
                setH231m(String.valueOf(tMonth));*/

            setRb05(String.valueOf(tYear));


        } catch (ParseException e) {
            Log.d(TAG, "CaluculateAge: " + e.getMessage());
            e.printStackTrace();

        }
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
