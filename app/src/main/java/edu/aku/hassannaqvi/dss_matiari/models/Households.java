package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

@Entity(tableName = HouseholdTable.TABLE_NAME)
public class Households extends BaseObservable implements Observable {

    @Ignore
    private String round = "";
    @Ignore
    private String ra01 = "";
    @Ignore
    private String ra01v2 = "";
    @Ignore

    private String ra01v3 = "";
    @Ignore
    private String ra02 = "";
    @Ignore
    private String ra04 = "";
    @Ignore
    private String ra03 = "";
    @Ignore
    private String ra05 = "";
    @Ignore
    private String ra07 = "";
    @Ignore
    private String ra06 = "";
    @Ignore
    private String ra08 = "";
    @Ignore
    private String ra09 = "";
    @Ignore
    private String ra10 = "";
    @Ignore
    private String ra11 = "";
    @Ignore
    private String ra11x = "";
    @Ignore
    private String ra12 = "";
    @Ignore
    private String ra12x = "";
    @Ignore
    private String ra13 = "";
    @Ignore
    private String ra13x = "";
    @Ignore
    private String ra14 = "";
    @Ignore
    private String ra15 = "";
    @Ignore
    private String ra16 = "";
    @Ignore
    private String ra17_a1 = "";
    @Ignore
    private String ra17_b1 = "";
    @Ignore
    private String ra17_c1 = "";
    @Ignore
    private String ra17_d1 = "";
    @Ignore
    private String ra17_a2 = "";
    @Ignore
    private String ra17_b2 = "";
    @Ignore
    private String ra17_c2 = "";
    @Ignore
    private String ra17_d2 = "";
    @Ignore
    private String ra17_a3 = "";
    @Ignore
    private String ra17_b3 = "";
    @Ignore
    private String ra17_c3 = "";
    @Ignore
    private String ra17_d3 = "";
    @Ignore
    private String ra18 = "";
    @Ignore
    private String ra19a = "";
    @Ignore
    private String ra19ax = "";
    @Ignore
    private String ra19b = "";
    @Ignore
    private String ra19bx = "";
    @Ignore
    private String ra19c = "";
    @Ignore
    private String ra19cx = "";
    @Ignore
    private String ra20 = "";
    @Ignore
    private String ra21 = "";
    @Ignore
    private String ra22 = "";

    /*@ColumnInfo(name = HouseholdTable.COLUMN_MUID)
    private String muid = StringUtils.EMPTY;*/

    @Ignore
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Ignore
    private boolean exist = false;

    @ColumnInfo(name = HouseholdTable.COLUMN_REGROUND)
    private String regRound = StringUtils.EMPTY;


    // APP VARIABLES
    @ColumnInfo(name = HouseholdTable.COLUMN_PROJECT_NAME)
    private String projectName = MainApp.PROJECT_NAME;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = HouseholdTable.COLUMN_ID)
    long id = 0;

    @ColumnInfo(name = HouseholdTable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;


    @ColumnInfo(name = HouseholdTable.COLUMN_STRUCTURE_NO)
    private String structureNo = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_VISIT_NO)
    private String visitNo = "0";

    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICETAGID)
    private String deviceTag = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    private String endTime = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_ISTATUS)
    String iStatus = StringUtils.EMPTY;

    @Ignore
    String iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED_DATE)
    private String syncDate = StringUtils.EMPTY;


    // SECTION VARIABLES
    @ColumnInfo(name = HouseholdTable.COLUMN_SA)
    String sA = StringUtils.EMPTY;


    public Households() {

    }

    public void populateMeta() {

        setRound(MainApp.ROUND);
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setAppver(MainApp.appInfo.getAppVersion());
        setUcCode(MainApp.selectedUC);
        setRa06(MainApp.selectedUC);
        setRa07(MainApp.selectedVillage);
        setRa04(MainApp.leaderCode);
        setRa05(MainApp.leaderCode);
        setRegRound("1");
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
        setRa09(MainApp.followUpsScheHHList.get(position).getHhNo());       // Household Number
        setRa08(MainApp.followUpsScheHHList.get(position).getRa08());       // Muhalla
        setRa10(MainApp.followUpsScheHHList.get(position).getHdssid());     //  Household ID
        setRa12(MainApp.followUpsScheHHList.get(position).getRa12());       // Name of head of household
        setRound(MainApp.followUpsScheHHList.get(position).getFRound());    // Round
        setRa04(MainApp.leaderCode);
        setRa05(MainApp.leaderCode);
        setRa06(MainApp.selectedUC);
        setRa07(MainApp.selectedVillage);
        setRa01(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setRegRound("null");


    }


    public Households(Households households) {

        setUserName(households.getUserName());
        setDeviceId(households.getDeviceId());
        setAppver(households.getAppver());
        setUcCode(MainApp.selectedUC);
        setRa06(households.getRa06());
        setRa07(households.getRa07());
        setRa08(households.getRa08());
        setRa10(households.getRa10());
        setRound(households.getRound());
        setRegRound("1");

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

    public String getRegRound() {
        return regRound;
    }

    public void setRegRound(String regRound) {
        this.regRound = regRound;
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
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        //if(this.hhNo.equals("")) return;
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

    public String getSA() {
        return sA;
    }

    public void setSA(String sA) {
        this.sA = sA;
    }

    @Bindable
    public String getRa19a() {
        return ra19a;
    }

    public void setRa19a(String ra19a) {
        this.ra19a = ra19a;
        setIStatus(ra19a);
        notifyPropertyChanged(BR.ra19a);
    }

    @Bindable
    public String getRa19ax() {
        return ra19ax;
    }

    public void setRa19ax(String ra19ax) {
        this.ra19ax = ra19ax;
        setIStatus96x(ra19ax);
        notifyPropertyChanged(BR.ra19ax);
    }

    @Bindable
    public String getRa19b() {
        return ra19b;
    }

    public void setRa19b(String ra19b) {
        this.ra19b = ra19b;
        setIStatus(ra19b);
        notifyPropertyChanged(BR.ra19b);
    }

    @Bindable
    public String getRa19bx() {
        return ra19bx;
    }

    public void setRa19bx(String ra19bx) {
        this.ra19bx = ra19bx;
        setIStatus96x(ra19bx);
        notifyPropertyChanged(BR.ra19bx);
    }

    @Bindable
    public String getRa19c() {
        return ra19c;
    }

    public void setRa19c(String ra19c) {
        this.ra19c = ra19c;
        setIStatus(ra19c);
        notifyPropertyChanged(BR.ra19c);
    }

    @Bindable
    public String getRa19cx() {
        return ra19cx;
    }

    public void setRa19cx(String ra19cx) {
        this.ra19cx = ra19cx;
        setIStatus96x(ra19cx);
        notifyPropertyChanged(BR.ra19cx);
    }



    @Bindable
    public String getRa01() {
        return ra01;
    }

    public void setRa01(String ra01) {
        this.ra01 = ra01;
        notifyChange(BR.ra01);
    }

    @Bindable
    public String getRa01v2() {
        return ra01v2;
    }

    public void setRa01v2(String ra01v2) {
        this.ra01v2 = ra01v2;
        notifyChange(BR.ra01v2);
    }

    @Bindable
    public String getRa01v3() {
        return ra01v3;
    }

    public void setRa01v3(String ra01v3) {
        this.ra01v3 = ra01v3;
        notifyChange(BR.ra01v3);
    }


    @Bindable
    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
        notifyPropertyChanged(BR.round);
    }

    @Bindable
    public String getRa02() {
        return ra02;
    }

    public void setRa02(String ra02) {
        this.ra02 = ra02;
        notifyChange(BR.ra02);
    }

    @Bindable
    public String getRa04() {
        return ra04;
    }

    public void setRa04(String ra04) {
        this.ra04 = ra04;
        notifyChange(BR.ra04);
    }

    @Bindable
    public String getRa03() {
        return ra03;
    }

    public void setRa03(String ra03) {
        this.ra03 = ra03;
        notifyChange(BR.ra03);
    }

    @Bindable
    public String getRa05() {
        return ra05;
    }

    public void setRa05(String ra05) {
        this.ra05 = ra05;
        notifyChange(BR.ra05);
    }

    @Bindable
    public String getRa07() {
        return ra07;
    }

    public void setRa07(String ra07) {
        this.ra07 = ra07;
        setVillageCode(this.ra07);
        notifyChange(BR.ra07);
    }

    @Bindable
    public String getRa06() {
        return ra06;
    }

    public void setRa06(String ra06) {
        //if(this.ra06.equals("")) return;
        this.ra06 = String.format("%02d", Integer.parseInt(selectedUC));
        //setUcCode(this.ra06);
        notifyChange(BR.ra06);
    }

    @Bindable
    public String getRa08() {
        return ra08;
    }

    public void setRa08(String ra08) {
        this.ra08 = ra08;
        notifyChange(BR.ra08);
    }

    @Bindable
    public String getRa09() {
        return ra09;
    }

    public void setRa09(String ra09) {
        this.ra09 = String.format("%04d", Integer.parseInt(ra09));
        setHhNo(this.ra09);
        notifyChange(BR.ra09);
    }

    @Bindable
    public String getRa10() {
        return ra10;
    }

    public void setRa10(String ra10) {
        this.ra10 = ra10;
        notifyChange(BR.ra10);
    }

    @Bindable
    public String getRa11() {
        return ra11;
    }

    public void setRa11(String ra11) {
        this.ra11 = ra11;
        //setIStatus(ra11);
        notifyChange(BR.ra11);
    }


    @Bindable
    public String getRa12() {
        return ra12;
    }

    public void setRa12(String ra12) {
        this.ra12 = ra12;
        //setIStatus(ra12);
        notifyChange(BR.ra12);
    }


    @Bindable
    public String getRa13() {
        return ra13;
    }

    public void setRa13(String ra13) {
        this.ra13 = ra13;
        //setIStatus(ra13);
        notifyChange(BR.ra13);
    }


    @Bindable
    public String getRa14() {
        return ra14;
    }

    public void setRa14(String ra14) {
        this.ra14 = ra14;
        notifyChange(BR.ra14);
    }

    @Bindable
    public String getRa15() {
        return ra15;
    }

    public void setRa15(String ra15) {
        this.ra15 = ra15;
        notifyChange(BR.ra15);
    }



    @Bindable
    public String getRa17_a1() {
        return ra17_a1;
    }

    public void setRa17_a1(String ra17_a1) {
        this.ra17_a1 = ra17_a1;
        notifyChange(BR.ra17_a1);
    }

    @Bindable
    public String getRa17_b1() {
        return ra17_b1;
    }

    public void setRa17_b1(String ra17_b1) {
        this.ra17_b1 = ra17_b1;
        notifyChange(BR.ra17_b1);
    }

    @Bindable
    public String getRa17_c1() {
        return ra17_c1;
    }

    public void setRa17_c1(String ra17_c1) {
        this.ra17_c1 = ra17_c1;
        notifyChange(BR.ra17_c1);
    }

    @Bindable
    public String getRa17_d1() {
        return ra17_d1;
    }

    public void setRa17_d1(String ra17_d1) {
        this.ra17_d1 = ra17_d1;
        notifyChange(BR.ra17_d1);
    }

    @Bindable
    public String getRa17_a2() {
        return ra17_a2;
    }

    public void setRa17_a2(String ra17_a2) {
        this.ra17_a2 = ra17_a2;
        notifyChange(BR.ra17_a2);
    }

    @Bindable
    public String getRa17_b2() {
        return ra17_b2;
    }

    public void setRa17_b2(String ra17_b2) {
        this.ra17_b2 = ra17_b2;
        notifyChange(BR.ra17_b2);
    }

    @Bindable
    public String getRa17_c2() {
        return ra17_c2;
    }

    public void setRa17_c2(String ra17_c2) {
        this.ra17_c2 = ra17_c2;
        setRa18(this.ra17_c2);
        notifyChange(BR.ra17_c2);
    }

    @Bindable
    public String getRa17_d2() {
        return ra17_d2;
    }

    public void setRa17_d2(String ra17_d2) {
        this.ra17_d2 = ra17_d2;
        notifyChange(BR.ra17_d2);
    }

    @Bindable
    public String getRa17_a3() {
        return ra17_a3;
    }

    public void setRa17_a3(String ra17_a3) {
        this.ra17_a3 = ra17_a3;
        notifyPropertyChanged(BR.ra17_a3);
    }

    @Bindable
    public String getRa17_b3() {
        return ra17_b3;
    }

    public void setRa17_b3(String ra17_b3) {
        this.ra17_b3 = ra17_b3;
        notifyPropertyChanged(BR.ra17_b3);
    }

    @Bindable
    public String getRa17_c3() {
        return ra17_c3;
    }

    public void setRa17_c3(String ra17_c3) {
        this.ra17_c3 = ra17_c3;
        notifyPropertyChanged(BR.ra17_c3);
    }

    @Bindable
    public String getRa17_d3() {
        return ra17_d3;
    }

    public void setRa17_d3(String ra17_d3) {
        this.ra17_d3 = ra17_d3;
        notifyPropertyChanged(BR.ra17_d3);
    }

    @Bindable
    public String getRa18() {
        return ra18;
    }

    public void setRa18(String ra18) {
        this.ra18 = ra18;
        notifyChange(BR.ra18);
    }

    @Bindable
    public String getRa20() {
        return ra20;
    }

    public void setRa20(String ra20) {
        this.ra20 = ra20;
        setRa18(ra20.equals("1") ? this.ra18 : "");
        notifyChange(BR.ra20);
    }

    @Bindable
    public String getRa21() {
        return ra21;
    }

    public void setRa21(String ra21) {
        this.ra21 = ra21;
        notifyChange(BR.ra21);
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
        setRa10(hdssId);
        //notifyChange(BR.ra22);
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

    public Households Hydrate(Households households) throws JSONException {

        this.id = households.id;
        this.uid = households.uid;
        this.userName = households.userName;
        this.sysDate = households.sysDate;
        this.hdssId = households.hdssId;
        this.ucCode = households.ucCode;
        this.villageCode = households.villageCode;
        this.hhNo = households.hhNo;
        this.structureNo = households.structureNo;
        this.visitNo = households.visitNo;
        this.regRound = households.regRound;
        this.deviceId = households.deviceId;
        this.deviceTag =households.deviceTag;
        this.appver = households.appver;
        this.iStatus =households.iStatus;
        this.synced = households.synced;
        this.syncDate = households.syncDate;

        s1Hydrate(households.sA);

        return this;
    }

    public void s1Hydrate(String string) throws JSONException {

        if (string != null && !string.equals("")) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.round = json.getString("ROUND");
            this.ra01 = json.getString("ra01");
            this.ra01v2 = json.has("ra01v2") ? json.getString("ra01v2") : "";
            this.ra01v3 = json.has("ra01v3") ? json.getString("ra01v3") : "";
            //this.ra02 = json.getString("ra02");
            this.ra04 = json.getString("ra04");
//            this.ra03 = json.getString("ra03");
            this.ra05 = json.getString("ra05");
            this.ra07 = json.getString("ra07");
            this.ra06 = json.getString("ra06");
            this.ra08 = json.getString("ra08");
            this.ra09 = json.getString("ra09");
            this.ra10 = json.getString("ra10");
            this.ra11 = json.getString("ra11");
            this.ra12 = json.getString("ra12");
            this.ra13 = json.getString("ra13");
            this.ra14 = json.getString("ra14");
            this.ra15 = json.getString("ra15");
            //this.ra16 = json.getString("ra16");
            this.ra17_a1 = json.getString("ra17_a1");
            this.ra17_b1 = json.getString("ra17_b1");
            this.ra17_c1 = json.getString("ra17_c1");
            this.ra17_d1 = json.getString("ra17_d1");
            this.ra17_a2 = json.getString("ra17_a2");
            this.ra17_b2 = json.getString("ra17_b2");
            this.ra17_c2 = json.getString("ra17_c2");
            this.ra17_d2 = json.getString("ra17_d2");
            this.ra17_a3 = json.getString("ra17_a3");
            this.ra17_b3 = json.getString("ra17_b3");
            this.ra17_c3 = json.getString("ra17_c3");
            this.ra17_d3 = json.getString("ra17_d3");

            this.ra18 = json.getString("ra18");

            this.ra19a = json.getString("ra19a");
            this.ra19ax = json.getString("ra19ax");
            this.ra19b = json.getString("ra19b");
            this.ra19bx = json.getString("ra19bx");
            this.ra19c = json.getString("ra19c");
            this.ra19cx = json.getString("ra19cx");
            //this.ra22 = json.getString("ra22");


        }
    }


    public String sAtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("ra01", ra01)
                .put("ra01v3", ra01v3)
                .put("ra01v2", ra01v2)
                .put("ROUND", round)
                .put("ra04", ra04)
                .put("ra05", ra05)
                .put("ra07", ra07)
                .put("ra06", ra06)
                .put("ra08", ra08)
                .put("ra09", ra09)
                .put("ra10", ra10)
                .put("ra11", ra11)
                .put("ra12", ra12)
                .put("ra13", ra13)
                .put("ra14", ra14)
                .put("ra15", ra15)
                //.put("ra16", ra16)
                .put("ra17_a1", ra17_a1)
                .put("ra17_b1", ra17_b1)
                .put("ra17_c1", ra17_c1)
                .put("ra17_d1", ra17_d1)
                .put("ra17_a2", ra17_a2)
                .put("ra17_b2", ra17_b2)
                .put("ra17_c2", ra17_c2)
                .put("ra17_d2", ra17_d2)
                .put("ra17_a3", ra17_a3)
                .put("ra17_b3", ra17_b3)
                .put("ra17_c3", ra17_c3)
                .put("ra17_d3", ra17_d3)
                .put("ra18", ra18)
                .put("ra19a", ra19a)
                .put("ra19ax", ra19ax)
                .put("ra19b", ra19b)
                .put("ra19bx", ra19bx)
                .put("ra19c", ra19c)
                .put("ra19cx", ra19cx)

        ;

        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();


        json.put(HouseholdTable.COLUMN_ID, this.id);
        json.put(HouseholdTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(HouseholdTable.COLUMN_UID, this.uid);
        json.put(HouseholdTable.COLUMN_USERNAME, this.userName);
        json.put(HouseholdTable.COLUMN_SYSDATE, this.sysDate);
        json.put(HouseholdTable.COLUMN_HDSSID, this.hdssId);
        json.put(HouseholdTable.COLUMN_UC_CODE, this.ucCode);
        json.put(HouseholdTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(HouseholdTable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        //json.put(HouseholdTable.COLUMN_STRUCTURE_NO, this.structureNo);
        json.put(HouseholdTable.COLUMN_VISIT_NO, this.visitNo);
        json.put(HouseholdTable.COLUMN_DEVICEID, this.deviceId);
        //json.put(HouseholdTable.COLUMN_DEVICETAGID, this.deviceTag);
        json.put(HouseholdTable.COLUMN_ISTATUS, this.iStatus);
        json.put(HouseholdTable.COLUMN_APPVERSION, this.appver);
        json.put(HouseholdTable.COLUMN_REGROUND, this.regRound);
        //  json.put(HouseholdTable.COLUMN_SYNCED, this.synced);
        //  json.put(HouseholdTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(HouseholdTable.COLUMN_SA, new JSONObject(sAtoString()));
        //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));


        return json;

    }




}
