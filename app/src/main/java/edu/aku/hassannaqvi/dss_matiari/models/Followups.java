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
    private String fmuid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String hdssId = StringUtils.EMPTY;
    private String ucCode = StringUtils.EMPTY;
    private String villageCode = StringUtils.EMPTY;
    private String hhNo = StringUtils.EMPTY;
    private String sNo = StringUtils.EMPTY;
    private String visitNo = StringUtils.EMPTY;


    private String rc01a = "";
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
    private String rc1201ln = StringUtils.EMPTY;
    private String rc1201nm = StringUtils.EMPTY;
    private final String rc1201 = StringUtils.EMPTY;
    private final String rc1202 = StringUtils.EMPTY;
    private String rc1202ln = StringUtils.EMPTY;
    private String rc1202nm = StringUtils.EMPTY;
    private final String rc1203 = StringUtils.EMPTY;
    private String rc1203ln = StringUtils.EMPTY;
    private String rc1203nm = StringUtils.EMPTY;
    private String rc12 = StringUtils.EMPTY;
    private final String rc12x = StringUtils.EMPTY;
    private final String rc1301 = StringUtils.EMPTY;
    private final String rc1302 = StringUtils.EMPTY;
    private final String rc1303 = StringUtils.EMPTY;
    private String rc13 = StringUtils.EMPTY;
    private final String rc13x = StringUtils.EMPTY;
    private String rc14 = StringUtils.EMPTY;
    private final String rc1401 = StringUtils.EMPTY;
    private final String rc1402 = StringUtils.EMPTY;
    private final String rc1403 = StringUtils.EMPTY;
    private String rc15 = StringUtils.EMPTY;
    private String rc16 = StringUtils.EMPTY;
    private String rc17 = StringUtils.EMPTY;

    public Followups() {

       /* setfRound(MainApp.ROUND);
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
        MainApp.followups.setfRound(MainApp.fpMwra.getfRound());
        MainApp.followups.setSno(MainApp.fpMwra.getRb01());
        MainApp.followups.setHdssId(MainApp.fpMwra.getHdssid());
        MainApp.followups.setRb06(MainApp.fpMwra.getRb06());
        MainApp.followups.setPrePreg(MainApp.fpMwra.getRb07());



    }

    @Bindable
    public String getfRound() {
        return fRound;
    }

    public void setfRound(String fRound) {
        this.fRound = fRound;
        notifyChange(BR.round);
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
        setSno(rc01);
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
    public String getRc1201ln() {
        return rc1201ln;
    }

    @Bindable
    public String getRc1201nm() {
        return rc1201nm;
    }

    public void setRc1201ln(String rc1201ln) {
        this.rc1201ln = rc1201ln;
        notifyPropertyChanged(BR.rc1201ln);
    }

    public void setRc1201nm(String rc1201nm) {
        this.rc1201nm = rc1201nm;
        notifyPropertyChanged(BR.rc1201nm);
    }

    @Bindable
    public String getRc1202ln() {
        return rc1202ln;
    }

    public void setRc1202ln(String rc1202ln) {
        this.rc1202ln = rc1202ln;
        notifyPropertyChanged(BR.rc1202ln);
    }

    @Bindable
    public String getRc1202nm() {
        return rc1202nm;
    }

    public void setRc1202nm(String rc1202nm) {
        this.rc1202nm = rc1202nm;
        notifyPropertyChanged(BR.rc1202nm);
    }

    @Bindable
    public String getRc1203ln() {
        return rc1203ln;
    }

    public void setRc1203ln(String rc1203ln) {
        this.rc1203ln = rc1203ln;
        notifyPropertyChanged(BR.rc1203ln);
    }

    @Bindable
    public String getRc1203nm() {
        return rc1203nm;
    }

    public void setRc1203nm(String rc1203nm) {
        this.rc1203nm = rc1203nm;
        notifyPropertyChanged(BR.rc1203nm);
    }

    @Bindable
    public String getRc04() {
        return rc04;
    }

    public void setRc04(String rc04) {
        this.rc04 = rc04;
        setRc05(rc04.equals("1") ? "1" : rc04.equals("7") ? "2" : "");
        //setRc06(rc04.equals("1") ? this.rc06 : "");
        setRc08(rc04.equals("1") ? this.rc08 : "");
        setRc09(rc04.equals("1") ? this.rc09 : "");
        setRc10(rc04.equals("1") ? this.rc10 : "");
        setRc11(rc04.equals("1") ? this.rc11 : "");

        /*setRc1201(rc04.equals("1") ? this.rc1201 : "");
        setRc1201ln(rc04.equals("1") ? this.rc1201ln : "");
        setRc1201nm(rc04.equals("1") ? this.rc1201nm : "");
        setRc1301(rc04.equals("1") ? this.rc1301 : "");
        setRc1401(rc04.equals("1") ? this.rc1401 : "");

        setRc1202(rc04.equals("1") ? this.rc1202 : "");
        setRc1202ln(rc04.equals("1") ? this.rc1202ln : "");
        setRc1202nm(rc04.equals("1") ? this.rc1202nm : "");
        setRc1302(rc04.equals("1") ? this.rc1302 : "");
        setRc1402(rc04.equals("1") ? this.rc1402 : "");

        setRc1203(rc04.equals("1") ? this.rc1203 : "");
        setRc1203ln(rc04.equals("1") ? this.rc1203ln : "");
        setRc1203nm(rc04.equals("1") ? this.rc1203nm : "");
        setRc1303(rc04.equals("1") ? this.rc1303 : "");
        setRc1403(rc04.equals("1") ? this.rc1403 : "");

        */setRc15(rc04.equals("1") ? this.rc15 : "");
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
        // setRc06(rc05.equals("1") ? this.rc06 : "");
        setRc05a(rc05.equals("1") ? "" : this.rc05a);
        setRc05b(rc05.equals("1") ? "" : this.rc05b);
        setRc08(rc05.equals("1") ? this.rc08 : "");
        setRc09(rc05.equals("1") ? this.rc09 : "");
        setRc10(rc05.equals("1") ? this.rc10 : "");
        setRc11(rc05.equals("1") ? this.rc11 : "");

        /*setRc1201(rc05.equals("1") ? this.rc1201 : "");
        setRc1201ln(rc05.equals("1") ? this.rc1201ln : "");
        setRc1201nm(rc05.equals("1") ? this.rc1201nm : "");
        setRc1301(rc05.equals("1") ? this.rc1301 : "");
        setRc1401(rc05.equals("1") ? this.rc1401 : "");

        setRc1202(rc05.equals("1") ? this.rc1202 : "");
        setRc1202ln(rc05.equals("1") ? this.rc1202ln : "");
        setRc1202nm(rc05.equals("1") ? this.rc1202nm : "");
        setRc1302(rc05.equals("1") ? this.rc1302 : "");
        setRc1402(rc05.equals("1") ? this.rc1402 : "");

        setRc1203(rc05.equals("1") ? this.rc1203 : "");
        setRc1203ln(rc05.equals("1") ? this.rc1203ln : "");
        setRc1203nm(rc05.equals("1") ? this.rc1203nm : "");
        setRc1303(rc05.equals("1") ? this.rc1303 : "");
        setRc1403(rc05.equals("1") ? this.rc1403 : "");
*/
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
        /*setRc12(rc06.equals("4") ? "" : this.rc12);
        setRc13(rc06.equals("4") ? "" : this.rc13);
        setRc14(rc06.equals("4") ? "" : this.rc14);
        */setRc15(rc06.equals("4") ? "" : this.rc15);
        setRc16(rc06.equals("4") ? "" : this.rc16);
        setRc17(rc06.equals("4") ? "" : this.rc17);
        //setRc03(this.rc06.equals("4") ? "" : this.rc03);
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
        setRc10(this.rc07.equals("1") ? this.rc10 : "");
        setRc11(this.rc07.equals("1") ? this.rc11 : "");
        /*setRc1201(this.rc07.equals("1") ? this.rc1201 : "");
        setRc1201ln(this.rc07.equals("1") ? this.rc1201ln : "");
        setRc1201nm(this.rc07.equals("1") ? this.rc1201nm : "");
        setRc1301(this.rc07.equals("1") ? this.rc1301 : "");
        setRc1401(this.rc07.equals("1") ? this.rc1401 : "");

        setRc1202(this.rc07.equals("1") ? this.rc1202 : "");
        setRc1202ln(this.rc07.equals("1") ? this.rc1202ln : "");
        setRc1202nm(this.rc07.equals("1") ? this.rc1202nm : "");
        setRc1302(this.rc07.equals("1") ? this.rc1302 : "");
        setRc1402(this.rc07.equals("1") ? this.rc1402 : "");

        setRc1203(this.rc07.equals("1") ? this.rc1203 : "");
        setRc1202ln(this.rc07.equals("1") ? this.rc1202ln : "");
        setRc1202nm(this.rc07.equals("1") ? this.rc1202nm : "");
        setRc1303(this.rc07.equals("1") ? this.rc1303 : "");
        setRc1403(this.rc07.equals("1") ? this.rc1403 : "");
*/

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
        /*setRc1201(this.rc08.equals("1") ? "" : this.rc1201);
        setRc1201ln(this.rc08.equals("1") ? this.rc1201ln : "");
        setRc1201nm(this.rc08.equals("1") ? this.rc1201nm : "");
        setRc1301(this.rc08.equals("1") ? "" : this.rc1301);
        setRc1401(this.rc08.equals("1") ? "" : this.rc1401);

        setRc1202(this.rc08.equals("1") ? "" : this.rc1202);
        setRc1202ln(this.rc08.equals("1") ? this.rc1202ln : "");
        setRc1202nm(this.rc08.equals("1") ? this.rc1202nm : "");
        setRc1302(this.rc08.equals("1") ? "" : this.rc1302);
        setRc1402(this.rc08.equals("1") ? "" : this.rc1402);

        setRc1203(this.rc08.equals("1") ? "" : this.rc1203);
        setRc1203ln(this.rc08.equals("1") ? this.rc1203ln : "");
        setRc1203nm(this.rc08.equals("1") ? this.rc1203nm : "");
        setRc1303(this.rc08.equals("1") ? "" : this.rc1303);
        setRc1403(this.rc08.equals("1") ? "" : this.rc1403);
*/
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
        /*setRc12(this.rc09.equals("3") || this.rc09.equals("4") ? "" : this.rc12);
        setRc13(this.rc09.equals("3") || this.rc09.equals("4") ? "" : this.rc13);
        setRc14(this.rc09.equals("3") || this.rc09.equals("4") ? "" : this.rc14);*/
        notifyChange(BR.rc09);
    }

    @Bindable
    public String getRc10() {
        return rc10;
    }

    public void setRc10(String rc10) {
        this.rc10 = rc10;

        /*setRc11(this.rc10.equals("1") || this.rc10.equals("3") ? "" : this.rc11);
        setRc12(this.rc10.equals("1") || this.rc10.equals("3") ? "" : this.rc12);
        setRc13(this.rc10.equals("1") || this.rc10.equals("3") ? "" : this.rc13);*/
        notifyChange(BR.rc10);
    }

    @Bindable
    public String getRc11() {
        return rc11;
    }

    public void setRc11(String rc11) {
        this.rc11 = rc11;
        /*setRc1202(!this.rc11.equals("1") ? this.rc1202 : "");
        setRc1302(!this.rc11.equals("1") ? this.rc1302 : "");
        setRc1402(!this.rc11.equals("1") ? this.rc1402 : "");
        setRc1203(this.rc11.equals("3") ? this.rc1203 : "");
        setRc1303(this.rc11.equals("3") ? this.rc1303 : "");
        setRc1403(this.rc11.equals("3") ? this.rc1403 : "");*/

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


   /* @Bindable
    public String getRc12x() {
        return rc12x;
    }

    public void setRc12x(String rc12x) {
        this.rc12x = rc12x;
        notifyChange(BR.rc12x);
    }*/


    /*@Bindable
    public String getRc1201() {
        return rc1201;
    }

    public void setRc1201(String rc1201) {
        this.rc1201 = rc1201;
        notifyChange(BR.rc1201);
    }

    @Bindable
    public String getRc1301() {
        return rc1301;
    }

    public void setRc1301(String rc1301) {
        this.rc1301 = rc1301;
        setRc1401(this.rc1301.equals("1") ? "" : this.rc1401);
        notifyChange(BR.rc1301);
    }

    @Bindable
    public String getRc13x() {
        return rc13x;
    }

    public void setRc13x(String rc13x) {
        this.rc13x = rc13x;
        notifyChange(BR.rc13x);
    }

    @Bindable
    public String getRc1401() {
        return rc1401;
    }

    public void setRc1401(String rc1401) {
        this.rc1401 = rc1401;
        notifyChange(BR.rc1401);
    }

    @Bindable
    public String getRc1202() {
        return rc1202;
    }

    public void setRc1202(String rc1202) {
        this.rc1202 = rc1202;
        notifyChange(BR.rc1202);
    }


    @Bindable
    public String getRc1302() {
        return rc1302;
    }

    public void setRc1302(String rc1302) {
        this.rc1302 = rc1302;
        setRc1402(this.rc1302.equals("1") ? "" : this.rc1402);
        notifyChange(BR.rc1302);
    }


    @Bindable
    public String getRc1402() {
        return rc1402;
    }

    public void setRc1402(String rc1402) {
        this.rc1402 = rc1402;
        notifyChange(BR.rc1402);
    }

    @Bindable
    public String getRc1203() {
        return rc1203;
    }

    public void setRc1203(String rc1203) {
        this.rc1203 = rc1203;
        notifyChange(BR.rc1203);
    }


    @Bindable
    public String getRc1303() {
        return rc1303;
    }

    public void setRc1303(String rc1303) {
        this.rc1303 = rc1303;
        setRc1403(this.rc1303.equals("1") ? "" : this.rc1403);
        notifyChange(BR.rc1303);
    }


    @Bindable
    public String getRc1403() {
        return rc1403;
    }

    public void setRc1403(String rc1403) {
        this.rc1403 = rc1403;
        notifyChange(BR.rc1403);
    }
*/
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
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_ID));
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
        /*        sDHydrate(cursor.getString(cursor.getColumnIndexOrThrow(FollowupsTable.COLUMN_SD)));*/
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
            /*this.rc1201 = json.getString("rc1201");
            this.rc1201ln = json.getString("rc1201ln");
            this.rc1201nm = json.getString("rc1201nm");
            this.rc12x = json.getString("rc12x");
            this.rc1301 = json.getString("rc1301");
            this.rc13x = json.getString("rc13x");
            this.rc1401 = json.getString("rc1401");
            this.rc1202 = json.getString("rc1202");
            this.rc1202ln = json.getString("rc1202ln");
            this.rc1201nm = json.getString("rc1201nm");
            this.rc1302 = json.getString("rc1302");
            this.rc1402 = json.getString("rc1402");

            this.rc1203 = json.getString("rc1203");
            this.rc1203ln = json.getString("rc1203ln");
            this.rc1203nm = json.getString("rc1203nm");
            this.rc1303 = json.getString("rc1303");
            this.rc1403 = json.getString("rc1403");
*/
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
                /*.put("rc1201", rc1201)
                .put("rc1201ln", rc1201ln)
                .put("rc12x", rc12x)
                .put("rc1301", rc1301)
                .put("rc13x", rc13x)
                .put("rc1401", rc1401)
                .put("rc1202", rc1202)
                .put("rc1202ln", rc1202ln)
                .put("rc1202nm", rc1202nm)
                .put("rc1302", rc1302)
                .put("rc1402", rc1402)
                .put("rc1203", rc1203)
                .put("rc1203ln", rc1203)
                .put("rc1303", rc1303)
                .put("rc1403", rc1403)*/
                .put("rc15", rc15)
                .put("rc16", rc16)
                .put("rc17", rc17)

        ;



        return json.toString();
    }

/*    public void sDHydrate(String string) throws JSONException {
        Log.d(TAG, "s4Hydrate: " + string);
        if (string != null) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rc10 = json.getString("rc10");
            this.rc11 = json.getString("rc11");
            this.rc12 = json.getString("rc12");
            this.rc13 = json.getString("rc13");
            this.rc14 = json.getString("rc14");
            this.rc15 = json.getString("rc15");
            this.rc16 = json.getString("rc16");
            this.rc17 = json.getString("rc17");


        }
    }*/


/*    public String sDtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("rc10", rc10)
                .put("rc11", rc11)
                .put("rc12", rc12)
                .put("rc13", rc13)
                .put("rc14", rc14)
                .put("rc15", rc15)
                .put("rc16", rc16)
                .put("rc17", rc17);

        return json.toString();
    }*/

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
        //  json.put(FollowupsTable.COLUMN_SYNCED, this.synced);
        //  json.put(FollowupsTable.COLUMN_SYNCED_DATE, this.syncDate);

        json.put(FollowupsTable.COLUMN_SC, new JSONObject(sCtoString()));
        /*        json.put(FollowupsTable.COLUMN_SD, new JSONObject(sDtoString()));*/
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
