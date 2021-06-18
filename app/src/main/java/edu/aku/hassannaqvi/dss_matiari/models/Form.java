package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.PropertyChangeRegistry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FormsTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;

public class Form extends BaseObservable {

    private final String TAG = "Form";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    public String tsf101 = "";
    public String tsf102 = "";
    public String tsf103 = "";
    public String tsf104 = "";
    public String tsf105 = "";
    public String tsf10596x = "";
    public String tsf106 = "";
    public String tsf107 = "";
    public String tsf108 = "";
    public String tsf109 = "";
    public String tsf110 = "";
    public String tsf111 = "";
    public String tsf112 = "";
    // public String tsf20503= "";
    public String tsf201 = "";
    public String tsf202 = "";
    public String tsf203 = "";
    public String tsf204 = "";
    public String tsf205 = "";
    public String tsf206c1 = "";
    public String tsf206d1 = "";
    public String tsf206c2 = "";
    public String tsf206d2 = "";
    public String tsf206c3 = "";
    public String tsf206d3 = "";
    public String tsf206c4 = "";
    public String tsf206d4 = "";
    public String tsf301 = "";
    public String tsf302 = "";
    public String tsf303 = "";
    public String tsf304 = "";
    public String tsf305 = "";
    public String tsf306 = "";
    public String tsf307 = "";
    public String tsf308 = "";
    public String tsf309 = "";
    public String tsf310 = "";
    public String tsf311 = "";
    public String tsf312 = "";
    public String tsf313 = "";
    public String tsf314 = "";
    public String tsf315 = "";
    public String tsf316 = "";
    public String tsf317 = "";
    public String tsf318 = "";
    public String tsf319 = "";
    public String tsf320 = "";
    public String tsf321 = "";
    public String tsf322 = "";
    public String tsf323 = "";
    public String tsf401 = "";
    public String tsf40101 = "";
    public String tsf40102 = "";
    public String tsf40103 = "";
    public String tsf40104 = "";
    public String tsf402 = "";
    public String tsf40201 = "";
    public String tsf40202 = "";
    public String tsf40203 = "";
    public String tsf40204 = "";
    public String tsf40205 = "";
    public String tsf40206 = "";
    public String tsf40207 = "";
    public String tsf40208 = "";
    public String tsf40209 = "";
    public String tsf40298 = "";
    public String tsf40298x = "";
    public String tsf501 = "";
    public String tsf502 = "";
    public String tsf503 = "";
    // APP VARIABLES
    private String projectName = MainApp.PROJECT_NAME;
    // APP VARIABLES
    private String id = StringUtils.EMPTY;
    private String uid = StringUtils.EMPTY;
    private String userName = StringUtils.EMPTY;
    private String sysDate = StringUtils.EMPTY;
    private String assessNo = StringUtils.EMPTY;
    private String mrNo = StringUtils.EMPTY;
    private String infantName = StringUtils.EMPTY;

    private String deviceId = StringUtils.EMPTY;
    private String deviceTag = StringUtils.EMPTY;
    private String appver = StringUtils.EMPTY;
    private String endTime = StringUtils.EMPTY;
    private String iStatus = StringUtils.EMPTY;
    private String iStatus96x = StringUtils.EMPTY;
    private String synced = StringUtils.EMPTY;
    private String syncDate = StringUtils.EMPTY;

    // SECTION VARIABLES
    private String s1 = StringUtils.EMPTY;
    private String s2 = StringUtils.EMPTY;
    private String s3 = StringUtils.EMPTY;
    private String s4 = StringUtils.EMPTY;
    private String s5 = StringUtils.EMPTY;

    public Form() {

    }

    @Bindable
    public String getTsf101() {
        return tsf101;
    }

    public void setTsf101(String tsf101) {
        this.tsf101 = tsf101;
        this.assessNo = tsf101;
        notifyPropertyChanged(BR.tsf101);
    }

    @Bindable
    public String getTsf102() {
        return tsf102;
    }

    public void setTsf102(String tsf102) {
        this.tsf102 = tsf102;
        this.mrNo = tsf102;  // Set MR NO
        notifyPropertyChanged(BR.tsf102);
    }

    @Bindable
    public String getTsf103() {
        return tsf103;
    }

    public void setTsf103(String tsf103) {
        this.tsf103 = tsf103;
        notifyPropertyChanged(BR.tsf103);
    }

    @Bindable
    public String getTsf104() {
        return tsf104;
    }

    public void setTsf104(String tsf104) {
        this.tsf104 = tsf104;
        this.infantName = tsf104; // Set Infant Name
        notifyPropertyChanged(BR.tsf104);
    }

    @Bindable
    public String getTsf105() {
        return tsf105;
    }

    public void setTsf105(String tsf105) {
        this.tsf105 = tsf105;
        notifyPropertyChanged(BR.tsf105);
    }

    @Bindable
    public String getTsf10596x() {
        return tsf10596x;
    }

    public void setTsf10596x(String tsf10596x) {
        this.tsf10596x = tsf10596x;
        notifyPropertyChanged(BR.tsf10596x);
    }

    @Bindable
    public String getTsf106() {
        return tsf106;
    }

    public void setTsf106(String tsf106) {
        this.tsf106 = tsf106;
        notifyPropertyChanged(BR.tsf106);
    }

    @Bindable
    public String getTsf107() {
        return tsf107;
    }

    public void setTsf107(String tsf107) {
        this.tsf107 = tsf107;
        notifyPropertyChanged(BR.tsf107);
    }

    @Bindable
    public String getTsf108() {
        return tsf108;
    }

    public void setTsf108(String tsf108) {
        this.tsf108 = tsf108;
        notifyPropertyChanged(BR.tsf108);
    }

    @Bindable
    public String getTsf109() {
        return tsf109;
    }

    public void setTsf109(String tsf109) {
        this.tsf109 = tsf109;
        notifyPropertyChanged(BR.tsf109);
    }

    @Bindable
    public String getTsf110() {
        return tsf110;
    }

    public void setTsf110(String tsf110) {
        this.tsf110 = tsf110;
        notifyPropertyChanged(BR.tsf110);
    }

    @Bindable
    public String getTsf111() {
        return tsf111;
    }

    public void setTsf111(String tsf111) {
        this.tsf111 = tsf111;
        notifyPropertyChanged(BR.tsf111);
    }

    @Bindable
    public String getTsf112() {
        return tsf112;
    }

    public void setTsf112(String tsf112) {
        this.tsf112 = tsf112;
        notifyPropertyChanged(BR.tsf112);
    }

/*    @Bindable
    public String getTsf20503() {
        return tsf20503;
    }

    public void setTsf20503(String tsf20503) {
        this.tsf20503 = tsf20503;
        notifyPropertyChanged(BR.tsf20503);
    }*/

    @Bindable
    public String getTsf201() {
        return tsf201;
    }

    public void setTsf201(String tsf201) {
        this.tsf201 = tsf201;
        notifyPropertyChanged(BR.tsf201);
    }

    @Bindable
    public String getTsf202() {
        return tsf202;
    }

    public void setTsf202(String tsf202) {
        this.tsf202 = tsf202;
        notifyPropertyChanged(BR.tsf202);
    }

    @Bindable
    public String getTsf203() {
        return tsf203;
    }

    public void setTsf203(String tsf203) {
        this.tsf203 = tsf203;
        notifyPropertyChanged(BR.tsf203);
    }

    @Bindable
    public String getTsf204() {
        return tsf204;
    }

    public void setTsf204(String tsf204) {
        this.tsf204 = tsf204;
        notifyPropertyChanged(BR.tsf204);
    }

    @Bindable
    public String getTsf205() {
        return tsf205;
    }

    public void setTsf205(String tsf205) {
        this.tsf205 = tsf205;
        notifyPropertyChanged(BR.tsf205);
    }

    @Bindable
    public String getTsf206c1() {
        return tsf206c1;
    }

    public void setTsf206c1(String tsf206c1) {
        this.tsf206c1 = tsf206c1;
        notifyPropertyChanged(BR.tsf206c1);
    }

    @Bindable
    public String getTsf206d1() {
        return tsf206d1;
    }

    public void setTsf206d1(String tsf206d1) {
        this.tsf206d1 = tsf206d1;
        notifyPropertyChanged(BR.tsf206d1);
    }

    @Bindable
    public String getTsf206c2() {
        return tsf206c2;
    }

    public void setTsf206c2(String tsf206c2) {
        this.tsf206c2 = tsf206c2;
        notifyPropertyChanged(BR.tsf206c2);
    }

    @Bindable
    public String getTsf206d2() {
        return tsf206d2;
    }

    public void setTsf206d2(String tsf206d2) {
        this.tsf206d2 = tsf206d2;
        notifyPropertyChanged(BR.tsf206d2);
    }

    @Bindable
    public String getTsf206c3() {
        return tsf206c3;
    }

    public void setTsf206c3(String tsf206c3) {
        this.tsf206c3 = tsf206c3;
        notifyPropertyChanged(BR.tsf206c3);
    }

    @Bindable
    public String getTsf206d3() {
        return tsf206d3;
    }

    public void setTsf206d3(String tsf206d3) {
        this.tsf206d3 = tsf206d3;
        notifyPropertyChanged(BR.tsf206d3);
    }

    @Bindable
    public String getTsf206c4() {
        return tsf206c4;
    }

    public void setTsf206c4(String tsf206c4) {
        this.tsf206c4 = tsf206c4;
        notifyPropertyChanged(BR.tsf206c4);
    }

    @Bindable
    public String getTsf206d4() {
        return tsf206d4;
    }

    public void setTsf206d4(String tsf206d4) {
        this.tsf206d4 = tsf206d4;
        notifyPropertyChanged(BR.tsf206d4);
    }

    @Bindable
    public String getTsf301() {
        return tsf301;
    }

    public void setTsf301(String tsf301) {
        this.tsf301 = tsf301;
        //   Log.d(TAG, "setTsf301: "+Integer.valueOf(tsf301));
        notifyPropertyChanged(BR.tsf301);
    }

    @Bindable
    public String getTsf302() {
        return tsf302;
    }

    public void setTsf302(String tsf302) {
        this.tsf302 = tsf302;
        notifyPropertyChanged(BR.tsf302);
    }

    @Bindable
    public String getTsf303() {
        return tsf303;
    }

    public void setTsf303(String tsf303) {
        this.tsf303 = tsf303;
        notifyPropertyChanged(BR.tsf303);
    }

    @Bindable
    public String getTsf304() {
        return tsf304;
    }

    public void setTsf304(String tsf304) {
        this.tsf304 = tsf304;
        notifyPropertyChanged(BR.tsf304);
    }

    @Bindable
    public String getTsf305() {
        return tsf305;
    }

    public void setTsf305(String tsf305) {
        this.tsf305 = tsf305;
        notifyPropertyChanged(BR.tsf305);
    }

    @Bindable
    public String getTsf306() {
        return tsf306;
    }

    public void setTsf306(String tsf306) {
        this.tsf306 = tsf306;
        notifyPropertyChanged(BR.tsf306);
    }

    @Bindable
    public String getTsf307() {
        return tsf307;
    }

    public void setTsf307(String tsf307) {
        this.tsf307 = tsf307;
        notifyPropertyChanged(BR.tsf307);
    }

    @Bindable
    public String getTsf308() {
        return tsf308;
    }

    public void setTsf308(String tsf308) {
        this.tsf308 = tsf308;
        notifyPropertyChanged(BR.tsf308);
    }

    @Bindable
    public String getTsf309() {
        return tsf309;
    }

    public void setTsf309(String tsf309) {
        this.tsf309 = tsf309;
        notifyPropertyChanged(BR.tsf309);
    }

    @Bindable
    public String getTsf310() {
        return tsf310;
    }

    public void setTsf310(String tsf310) {
        this.tsf310 = tsf310;
        notifyPropertyChanged(BR.tsf310);
    }

    @Bindable
    public String getTsf311() {
        return tsf311;
    }

    public void setTsf311(String tsf311) {
        this.tsf311 = tsf311;
        notifyPropertyChanged(BR.tsf311);
    }

    @Bindable
    public String getTsf312() {
        return tsf312;
    }

    public void setTsf312(String tsf312) {
        this.tsf312 = tsf312;
        notifyPropertyChanged(BR.tsf312);
    }

    @Bindable
    public String getTsf313() {
        return tsf313;
    }

    public void setTsf313(String tsf313) {
        this.tsf313 = tsf313;
        notifyPropertyChanged(BR.tsf313);
    }

    @Bindable
    public String getTsf314() {
        return tsf314;
    }

    public void setTsf314(String tsf314) {
        this.tsf314 = tsf314;
        notifyPropertyChanged(BR.tsf314);
    }

    @Bindable
    public String getTsf315() {
        return tsf315;
    }

    public void setTsf315(String tsf315) {
        this.tsf315 = tsf315;
        notifyPropertyChanged(BR.tsf315);
    }

    @Bindable
    public String getTsf316() {
        return tsf316;
    }

    public void setTsf316(String tsf316) {
        this.tsf316 = tsf316;
        notifyPropertyChanged(BR.tsf316);
    }

    @Bindable
    public String getTsf317() {
        return tsf317;
    }

    public void setTsf317(String tsf317) {
        this.tsf317 = tsf317;
        notifyPropertyChanged(BR.tsf317);
    }

    @Bindable
    public String getTsf318() {
        return tsf318;
    }

    public void setTsf318(String tsf318) {
        this.tsf318 = tsf318;
        notifyPropertyChanged(BR.tsf318);
    }

    @Bindable
    public String getTsf319() {
        return tsf319;
    }

    public void setTsf319(String tsf319) {
        this.tsf319 = tsf319;
        notifyPropertyChanged(BR.tsf319);
    }

    @Bindable
    public String getTsf320() {
        return tsf320;
    }

    public void setTsf320(String tsf320) {
        this.tsf320 = tsf320;
        notifyPropertyChanged(BR.tsf320);
    }

    @Bindable
    public String getTsf321() {
        return tsf321;
    }

    public void setTsf321(String tsf321) {
        this.tsf321 = tsf321;
        notifyPropertyChanged(BR.tsf321);
    }

    @Bindable
    public String getTsf322() {
        return tsf322;
    }

    public void setTsf322(String tsf322) {
        this.tsf322 = tsf322;
        notifyPropertyChanged(BR.tsf322);
    }

    @Bindable
    public String getTsf323() {
        return tsf323;
    }

    public void setTsf323(String tsf323) {
        this.tsf323 = tsf323;
        notifyPropertyChanged(BR.tsf323);
    }

    @Bindable
    public String getTsf401() {
        return tsf401;
    }

    public void setTsf401(String tsf401) {
        this.tsf401 = tsf401;
        notifyPropertyChanged(BR.tsf401);
    }

    @Bindable
    public String getTsf40101() {
        return tsf40101;
    }

    public void setTsf40101(String tsf40101) {
        this.tsf40101 = tsf40101;
        notifyPropertyChanged(BR.tsf40101);
    }

    @Bindable
    public String getTsf40102() {
        return tsf40102;
    }

    public void setTsf40102(String tsf40102) {
        this.tsf40102 = tsf40102;
        notifyPropertyChanged(BR.tsf40102);
    }

    @Bindable
    public String getTsf40103() {
        return tsf40103;
    }

    public void setTsf40103(String tsf40103) {
        this.tsf40103 = tsf40103;
        notifyPropertyChanged(BR.tsf40103);
    }

    @Bindable
    public String getTsf40104() {
        return tsf40104;
    }

    public void setTsf40104(String tsf40104) {
        this.tsf40104 = tsf40104;
        notifyPropertyChanged(BR.tsf40104);
    }

    @Bindable
    public String getTsf402() {
        return tsf402;
    }

    public void setTsf402(String tsf402) {
        this.tsf402 = tsf402;
        notifyPropertyChanged(BR.tsf402);
    }

    @Bindable
    public String getTsf40201() {
        return tsf40201;
    }

    public void setTsf40201(String tsf40201) {
        this.tsf40201 = tsf40201;
        notifyPropertyChanged(BR.tsf40201);
    }

    @Bindable
    public String getTsf40202() {
        return tsf40202;
    }

    public void setTsf40202(String tsf40202) {
        this.tsf40202 = tsf40202;
        notifyPropertyChanged(BR.tsf40202);
    }

    @Bindable
    public String getTsf40203() {
        return tsf40203;
    }

    public void setTsf40203(String tsf40203) {
        this.tsf40203 = tsf40203;
        notifyPropertyChanged(BR.tsf40203);
    }

    @Bindable
    public String getTsf40204() {
        return tsf40204;
    }

    public void setTsf40204(String tsf40204) {
        this.tsf40204 = tsf40204;
        notifyPropertyChanged(BR.tsf40204);
    }

    @Bindable
    public String getTsf40205() {
        return tsf40205;
    }

    public void setTsf40205(String tsf40205) {
        this.tsf40205 = tsf40205;
        notifyPropertyChanged(BR.tsf40205);
    }

    @Bindable
    public String getTsf40206() {
        return tsf40206;
    }

    public void setTsf40206(String tsf40206) {
        this.tsf40206 = tsf40206;
        notifyPropertyChanged(BR.tsf40206);
    }

    @Bindable
    public String getTsf40207() {
        return tsf40207;
    }

    public void setTsf40207(String tsf40207) {
        this.tsf40207 = tsf40207;
        notifyPropertyChanged(BR.tsf40207);
    }

    @Bindable
    public String getTsf40208() {
        return tsf40208;
    }

    public void setTsf40208(String tsf40208) {
        this.tsf40208 = tsf40208;
        notifyPropertyChanged(BR.tsf40208);
    }

    @Bindable
    public String getTsf40209() {
        return tsf40209;
    }

    public void setTsf40209(String tsf40209) {
        this.tsf40209 = tsf40209;
        notifyPropertyChanged(BR.tsf40209);
    }

    @Bindable
    public String getTsf40298() {
        return tsf40298;
    }

    public void setTsf40298(String tsf40298) {
        this.tsf40298 = tsf40298;
        notifyPropertyChanged(BR.tsf40298);
    }

    @Bindable
    public String getTsf40298x() {
        return tsf40298x;
    }

    public void setTsf40298x(String tsf40298x) {
        this.tsf40298x = tsf40298x;
        notifyPropertyChanged(BR.tsf40298x);
    }

    @Bindable
    public String getTsf501() {
        return tsf501;
    }

    public void setTsf501(String tsf501) {
        this.tsf501 = tsf501;
        notifyPropertyChanged(BR.tsf501);
    }

    @Bindable
    public String getTsf502() {
        return tsf502;
    }

    public void setTsf502(String tsf502) {
        this.tsf502 = tsf502;
        notifyPropertyChanged(BR.tsf502);
    }

    @Bindable
    public String getTsf503() {
        return tsf503;
    }

    public void setTsf503(String tsf503) {
        this.tsf503 = tsf503;
        notifyPropertyChanged(BR.tsf503);
    }
/*
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
    }*/

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

/*    public PropertyChangeRegistry getPropertyChangeRegistry() {
        return propertyChangeRegistry;
    }

    public void setPropertyChangeRegistry(PropertyChangeRegistry propertyChangeRegistry) {
        this.propertyChangeRegistry = propertyChangeRegistry;
    }*/

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

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getAssessNo() {
        return assessNo;
    }

    public void setAssessNo(String assessNo) {
        this.assessNo = assessNo;
    }

    public String getInfantName() {
        return infantName;
    }

    public void setInfantName(String infantName) {
        this.infantName = infantName;
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


    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    public Form Hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_UID));
        this.userName = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYSDATE));
        this.mrNo = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_MR_NUMBER));
        this.assessNo = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ASSESMENT_NO));
        this.infantName = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_INFANT_NAME));
        this.tsf305 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_TSF305));
        this.deviceId = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICETAGID));
        this.appver = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYNCED_DATE));

        s1Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S1)));
        s2Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S2)));
        s3Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S3)));
        s4Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S4)));
        s5Hydrate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_S5)));

        return this;
    }

    public void s1Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);
                this.tsf101 = json.getString("tsf101");
                this.tsf102 = json.getString("tsf102");
                this.tsf103 = json.getString("tsf103");
                this.tsf104 = json.getString("tsf104");
                this.tsf105 = json.getString("tsf105");
                this.tsf10596x = json.getString("tsf10596x");
                this.tsf106 = json.getString("tsf106");
                this.tsf107 = json.getString("tsf107");
                this.tsf108 = json.getString("tsf108");
                this.tsf109 = json.getString("tsf109");
                this.tsf110 = json.getString("tsf110");
                this.tsf111 = json.getString("tsf111");
                this.tsf112 = json.getString("tsf112");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s2Hydrate(String string) {
        Log.d(TAG, "s2Hydrate: " + string);
        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                //   this.tsf20503 = json.getString("tsf20503");
                this.tsf201 = json.getString("tsf201");
                this.tsf202 = json.getString("tsf202");
                this.tsf203 = json.getString("tsf203");
                this.tsf204 = json.getString("tsf204");
                this.tsf205 = json.getString("tsf205");
                this.tsf206c1 = json.getString("tsf206c1");
                this.tsf206d1 = json.getString("tsf206d1");
                this.tsf206c2 = json.getString("tsf206c2");
                this.tsf206d2 = json.getString("tsf206d2");
                this.tsf206c3 = json.getString("tsf206c3");
                this.tsf206d3 = json.getString("tsf206d3");
                this.tsf206c4 = json.getString("tsf206c4");
                this.tsf206d4 = json.getString("tsf206d4");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void s3Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf301 = json.getString("tsf301");
                this.tsf302 = json.getString("tsf302");
                this.tsf303 = json.getString("tsf303");
                this.tsf304 = json.getString("tsf304");
                //this.tsf305 = json.getString("tsf305"); // commented because moved out of json
                this.tsf306 = json.getString("tsf306");
                this.tsf307 = json.getString("tsf307");
                this.tsf308 = json.getString("tsf308");
                this.tsf309 = json.getString("tsf309");
                this.tsf310 = json.getString("tsf310");
                this.tsf311 = json.getString("tsf311");
                this.tsf312 = json.getString("tsf312");
                this.tsf313 = json.getString("tsf313");
                this.tsf314 = json.getString("tsf314");
                this.tsf315 = json.getString("tsf315");
                this.tsf316 = json.getString("tsf316");
                this.tsf317 = json.getString("tsf317");
                this.tsf318 = json.getString("tsf318");
                this.tsf319 = json.getString("tsf319");
                this.tsf320 = json.getString("tsf320");
                this.tsf321 = json.getString("tsf321");
                this.tsf322 = json.getString("tsf322");
                this.tsf323 = json.getString("tsf323");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s4Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf40101 = json.getString("tsf40101");
                this.tsf40102 = json.getString("tsf40102");
                this.tsf40103 = json.getString("tsf40103");
                this.tsf40104 = json.getString("tsf40104");
                this.tsf40201 = json.getString("tsf40201");
                this.tsf40202 = json.getString("tsf40202");
                this.tsf40203 = json.getString("tsf40203");
                this.tsf40204 = json.getString("tsf40204");
                this.tsf40205 = json.getString("tsf40205");
                this.tsf40206 = json.getString("tsf40206");
                this.tsf40207 = json.getString("tsf40207");
                this.tsf40208 = json.getString("tsf40208");
                this.tsf40209 = json.getString("tsf40209");
                this.tsf40298 = json.getString("tsf40298");
                this.tsf40298x = json.getString("tsf40298x");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void s5Hydrate(String string) {

        if (string != null) {

            try {
                JSONObject json = null;
                json = new JSONObject(string);

                this.tsf501 = json.getString("tsf501");
                this.tsf502 = json.getString("tsf502");
                this.tsf503 = json.getString("tsf503");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String s1toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("tsf101", tsf101)
                    .put("tsf102", tsf102)
                    .put("tsf103", tsf103)
                    .put("tsf104", tsf104)
                    .put("tsf105", tsf105)
                    .put("tsf10596x", tsf10596x)
                    .put("tsf106", tsf106)
                    .put("tsf107", tsf107)
                    .put("tsf108", tsf108)
                    .put("tsf109", tsf109)
                    .put("tsf110", tsf110)
                    .put("tsf111", tsf111)
                    .put("tsf112", tsf112);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s2toString() {
        JSONObject json = new JSONObject();

        try {
            json
                    //           .put("tsf20503", tsf20503)
                    .put("tsf201", tsf201)
                    .put("tsf202", tsf202)
                    .put("tsf203", tsf203)
                    .put("tsf204", tsf204)
                    .put("tsf205", tsf205)
                    .put("tsf206c1", tsf206c1)
                    .put("tsf206d1", tsf206d1)
                    .put("tsf206c2", tsf206c2)
                    .put("tsf206d2", tsf206d2)
                    .put("tsf206c3", tsf206c3)
                    .put("tsf206d3", tsf206d3)
                    .put("tsf206c4", tsf206c4)
                    .put("tsf206d4", tsf206d4);


        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s3toString() {
        JSONObject json = new JSONObject();

        try {
            json
                    .put("tsf301", tsf301)
                    .put("tsf302", tsf302)
                    .put("tsf303", tsf303)
                    .put("tsf304", tsf304)
                    // .put("tsf305", tsf305) commented because temp. recheck moved out of json
                    .put("tsf306", tsf306)
                    .put("tsf307", tsf307)
                    .put("tsf308", tsf308)
                    .put("tsf309", tsf309)
                    .put("tsf310", tsf310)
                    .put("tsf311", tsf311)
                    .put("tsf312", tsf312)
                    .put("tsf313", tsf313)
                    .put("tsf314", tsf314)
                    .put("tsf315", tsf315)
                    .put("tsf316", tsf316)
                    .put("tsf317", tsf317)
                    .put("tsf318", tsf318)
                    .put("tsf319", tsf319)
                    .put("tsf320", tsf320)
                    .put("tsf321", tsf321)
                    .put("tsf322", tsf322)
                    .put("tsf323", tsf323);


        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s4toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("tsf40101", tsf40101)
                    .put("tsf40102", tsf40102)
                    .put("tsf40103", tsf40103)
                    .put("tsf40104", tsf40104)
                    .put("tsf40201", tsf40201)
                    .put("tsf40202", tsf40202)
                    .put("tsf40203", tsf40203)
                    .put("tsf40204", tsf40204)
                    .put("tsf40205", tsf40205)
                    .put("tsf40206", tsf40206)
                    .put("tsf40207", tsf40207)
                    .put("tsf40208", tsf40208)
                    .put("tsf40209", tsf40209)
                    .put("tsf40298", tsf40298)
                    .put("tsf40298x", tsf40298x);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public String s5toString() {
        JSONObject json = new JSONObject();

        try {
            json

                    .put("tsf501", tsf501)
                    .put("tsf502", tsf502)
                    .put("tsf503", tsf503);

        } catch (JSONException e) {
            e.printStackTrace();
            return "\"error\":, \"" + e.getMessage() + "\"";
        }
        return json.toString();
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(FormsTable.COLUMN_ID, this.id);
            json.put(FormsTable.COLUMN_UID, this.uid);
            json.put(FormsTable.COLUMN_USERNAME, this.userName);
            json.put(FormsTable.COLUMN_SYSDATE, this.sysDate);
            json.put(FormsTable.COLUMN_ASSESMENT_NO, this.mrNo);
            json.put(FormsTable.COLUMN_MR_NUMBER, this.mrNo);
            json.put(FormsTable.COLUMN_ASSESMENT_NO, this.assessNo);
            json.put(FormsTable.COLUMN_INFANT_NAME, this.infantName);
            json.put(FormsTable.COLUMN_TSF305, this.tsf305);
            json.put(FormsTable.COLUMN_DEVICEID, this.deviceId);
            json.put(FormsTable.COLUMN_DEVICETAGID, this.deviceTag);
            json.put(FormsTable.COLUMN_ISTATUS, this.iStatus);
            //  json.put(FormsTable.COLUMN_SYNCED, this.synced);
            //  json.put(FormsTable.COLUMN_SYNCED_DATE, this.syncDate);

            json.put(FormsTable.COLUMN_S1, new JSONObject(s1toString()));
            //Log.d(TAG, "toJSONObject: "+new JSONObject(s2toString()));
            json.put(FormsTable.COLUMN_S2, new JSONObject(s2toString()));
            json.put(FormsTable.COLUMN_S3, new JSONObject(s3toString()));
            json.put(FormsTable.COLUMN_S4, new JSONObject(s4toString()));
            json.put(FormsTable.COLUMN_S5, new JSONObject(s5toString()));


            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "toJSONObject: " + e.getMessage());
            return null;
        }
    }
}
