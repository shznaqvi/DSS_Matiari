package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.selectedUC;

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
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.database.dao.HouseholdsDao;

@Entity(tableName = HouseholdTable.TABLE_NAME)
public class Households extends BaseObservable implements Observable {

    private String round = StringUtils.EMPTY;
    // For local use
    // This is used for resolving data while posting
    @ColumnInfo(defaultValue = "0")
    private transient boolean isError;

    @Ignore
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Ignore
    private transient boolean exist = false;

    @ColumnInfo(name = HouseholdTable.COLUMN_REGROUND)
    private String regRound = StringUtils.EMPTY;

    // APP VARIABLES
    @ColumnInfo(name = HouseholdTable.COLUMN_PROJECT_NAME)
    private String projectName = MainApp.PROJECT_NAME;

    @PrimaryKey(autoGenerate = true) @NonNull
    @SerializedName("_id")
    @ColumnInfo(name = HouseholdTable.COLUMN_ID)
    long id = 0;

    @SerializedName("_uid")
    @ColumnInfo(name = HouseholdTable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @SerializedName("username")
    @ColumnInfo(name = HouseholdTable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @SerializedName("sysdate")
    @ColumnInfo(name = HouseholdTable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @SerializedName("hdssid")
    @ColumnInfo(name = HouseholdTable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;


    @ColumnInfo(name = HouseholdTable.COLUMN_STRUCTURE_NO)
    private transient String structureNo = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_VISIT_NO)
    private String visitNo = "0";

    @SerializedName("deviceid")
    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICETAGID)
    private transient String deviceTag = StringUtils.EMPTY;

    @SerializedName("appversion")
    @ColumnInfo(name = HouseholdTable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;

    private transient String endTime = StringUtils.EMPTY;

    @SerializedName("istatus")
    @ColumnInfo(name = HouseholdTable.COLUMN_ISTATUS)
    String iStatus = StringUtils.EMPTY;

    @Ignore
    transient String  iStatus96x = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED_DATE)
    private transient String syncDate = StringUtils.EMPTY;

    //private String round = StringUtils.EMPTY;

    @SerializedName("s1")
    private SA sA;

    public Households() {
    }

    public static void initMeta() {

        households = new Households();
        households.setRound(MainApp.ROUND);
        households.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        households.setUserName(MainApp.user.getUsername());
        households.setDeviceId(MainApp.deviceid);
        households.setAppver(MainApp.appInfo.getAppVersion());
        households.setUcCode(MainApp.selectedUC);
        households.setVillageCode(MainApp.selectedVillage);
        households.setRegRound("1");
    }


    public void populateMeta(int position) {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUsername());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
        setHdssId(MainApp.followUpsScheHHList.get(position).getHdssid());
        setUcCode(MainApp.followUpsScheHHList.get(position).getUcCode());
        setVillageCode(MainApp.followUpsScheHHList.get(position).getVillageCode());
        setHhNo(MainApp.followUpsScheHHList.get(position).getHhNo());
        setRound(MainApp.followUpsScheHHList.get(position).getFRound());    // Round
        setRegRound("");
    }



    /*FOR IDENTIFICATION INFORMATION - CLUSTER-WISE*/
    // Save data in db
    public static void saveMainData(String hdssId, String round, SA sa) throws JSONException {
        HouseholdsDao householdsDao = Objects.requireNonNull(DssRoomDatabase.getDbInstance()).householdsDao();
        Households form = householdsDao.getHouseholdByHDSSIDASC(hdssId, round);
        if (form != null) {
            households = form;
        } else {
            households.setUid(AppConstants.generateUid());
            households.setId(householdsDao.addHousehold(households));
            Households.SA.saveData(sa);
        }
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

    public SA getSA() {
        return sA;
    }

    public void setSA(SA sA) {
        this.sA = sA;
    }


    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Bindable
    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
        notifyPropertyChanged(BR.round);
    }


    public String getHdssId() {
        return hdssId;
    }

    public void setHdssId(String hdssId) {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        String[] hdssidSplit = hdssId.split("-");
        String newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", Integer.parseInt(hdssidSplit[2]));

        this.hdssId = newHDSSID;
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

    /**
     * SECTION A - IDENTIFICATION INFORMATION - CLUSTER-WISE
     */

    public static class SA extends BaseObservable{

        private String ra01 = StringUtils.EMPTY;
        private String ra01v2 = StringUtils.EMPTY;
        private String ra01v3 = StringUtils.EMPTY;
        private String ra02 = StringUtils.EMPTY;
        private String ra04 = StringUtils.EMPTY;
        private String ra03 = StringUtils.EMPTY;
        private String ra05 = StringUtils.EMPTY;
        private String ra07 = StringUtils.EMPTY;
        private String ra06 = StringUtils.EMPTY;
        private String ra08 = StringUtils.EMPTY;
        private String ra09 = StringUtils.EMPTY;
        private String ra10 = StringUtils.EMPTY;
        private String ra11 = StringUtils.EMPTY;
        private transient final String ra11x = StringUtils.EMPTY;
        private String ra12 = StringUtils.EMPTY;
        private final transient String ra12x = StringUtils.EMPTY;
        private String ra13 = StringUtils.EMPTY;
        private final transient String ra13x = StringUtils.EMPTY;
        private String ra14 = StringUtils.EMPTY;
        private String ra15 = StringUtils.EMPTY;
        private final transient String ra16 = StringUtils.EMPTY;
        private String ra17_a1 = StringUtils.EMPTY;
        private String ra17_b1 = StringUtils.EMPTY;
        private String ra17_c1 = StringUtils.EMPTY;
        private String ra17_d1 = StringUtils.EMPTY;
        private String ra17_a2 = StringUtils.EMPTY;
        private String ra17_b2 = StringUtils.EMPTY;
        private String ra17_c2 = StringUtils.EMPTY;
        private String ra17_d2 = StringUtils.EMPTY;
        private String ra17_a3 = StringUtils.EMPTY;
        private String ra17_b3 = StringUtils.EMPTY;
        private String ra17_c3 = StringUtils.EMPTY;
        private String ra17_d3 = StringUtils.EMPTY;
        private String ra18 = StringUtils.EMPTY;
        private String ra19a = StringUtils.EMPTY;
        private String ra19ax = StringUtils.EMPTY;
        private String ra19b = StringUtils.EMPTY;
        private String ra19bx = StringUtils.EMPTY;
        private String ra19c = StringUtils.EMPTY;
        private String ra19cx = StringUtils.EMPTY;
        private transient String ra20 = StringUtils.EMPTY;
        private transient String ra21 = StringUtils.EMPTY;
        private transient final String ra22 = StringUtils.EMPTY;

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<SA> {
            public DataConverter() {
                super(new TypeToken<SA>() {
                }.getType());
            }
        }

        // Save section object as json object in db
        public static int saveData(SA data) {
            households.setSA(data);
            return Objects.requireNonNull(DssRoomDatabase.getDbInstance()).householdsDao().updateHousehold(households);
        }

        public void populateMeta() {
            setRa01(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
            setRa04(MainApp.leaderCode);
            setRa05(MainApp.leaderCode);
            setRa06(MainApp.selectedUC);
            setRa07(MainApp.selectedVillage);
        }

        public void populateMetaFollowups() {
            setRa01(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
            setRa04(MainApp.leaderCode);
            setRa05(MainApp.leaderCode);
            setRa06(MainApp.selectedUC);
            setRa07(MainApp.selectedVillage);
            setRa08(MainApp.fpMwra.getRa08());
            setRa09(fpMwra.getHhNo());
            setRa10(fpMwra.getHdssid());
            setRa12(fpMwra.getRa12());

        }

        public void updateFMData(int position){
            setRa08(MainApp.hhsList.get(position).getRa08());
            setRa09(households.getHhNo());
            setRa10(households.getHdssId());
            setRa12(MainApp.hhsList.get(position).getRa12());
            setRa17_a1(MainApp.hhsList.get(position).getRa17_a1().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_a1());
            setRa17_b1(MainApp.hhsList.get(position).getRa17_b1().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_b1());
            setRa17_c1(MainApp.hhsList.get(position).getRa17_c1().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_c1());
            setRa17_d1(MainApp.hhsList.get(position).getRa17_d1().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_d1());
            setRa17_a2(MainApp.hhsList.get(position).getRa17_a2().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_a2());
            setRa17_b2(MainApp.hhsList.get(position).getRa17_b2().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_b2());
            setRa17_c2(MainApp.hhsList.get(position).getRa17_c2().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_c2());
            setRa17_d2(MainApp.hhsList.get(position).getRa17_d2().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_d2());
            setRa17_a3(MainApp.hhsList.get(position).getRa17_a3().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_a3());
            setRa17_b3(MainApp.hhsList.get(position).getRa17_b3().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_b3());
            setRa17_c3(MainApp.hhsList.get(position).getRa17_c3().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_c3());
            setRa17_d3(MainApp.hhsList.get(position).getRa17_d3().equals("null") ? "0" : MainApp.hhsList.get(position).getRa17_d3());
            setRa18(MainApp.followUpsScheHHList.get(MainApp.selectedFpHousehold).getRa18());

            if (Integer.parseInt(getRa18()) > 0) {
                setRa15("1");
            } else {
                setRa15("2");
            }
        }

        // Get section object by parsing json
        public static SA getData() {
            return households.getSA();
        }

        @Bindable
        public String getRa01() {
            return ra01;
        }

        public void setRa01(String ra01) {
            this.ra01 = ra01;
            notifyPropertyChanged(BR.ra01);
        }

        @Bindable
        public String getRa01v2() {
            return ra01v2;
        }

        public void setRa01v2(String ra01v2) {
            this.ra01v2 = ra01v2;
            notifyPropertyChanged(BR.ra01v2);
        }

        @Bindable
        public String getRa01v3() {
            return ra01v3;
        }

        public void setRa01v3(String ra01v3) {
            this.ra01v3 = ra01v3;
            notifyPropertyChanged(BR.ra01v3);
        }

        @Bindable
        public String getRa02() {
            return ra02;
        }

        public void setRa02(String ra02) {
            this.ra02 = ra02;
            notifyPropertyChanged(BR.ra02);
        }

        @Bindable
        public String getRa04() {
            return ra04;
        }

        public void setRa04(String ra04) {
            this.ra04 = MainApp.leaderCode;
            notifyPropertyChanged(BR.ra04);
        }

        @Bindable
        public String getRa03() {
            return ra03;
        }

        public void setRa03(String ra03) {
            this.ra03 = ra03;
            notifyPropertyChanged(BR.ra03);
        }

        @Bindable
        public String getRa05() {
            return ra05;
        }

        public void setRa05(String ra05) {
            this.ra05 = ra05;
            notifyPropertyChanged(BR.ra05);
        }

        @Bindable
        public String getRa07() {
            return ra07;
        }

        public void setRa07(String ra07) {
            //this.ra07 = ra07;
            this.ra07 = ra07;
            notifyPropertyChanged(BR.ra07);
        }

        @Bindable
        public String getRa06() {
            return ra06;
        }

        public void setRa06(String ra06) {
            this.ra06 = String.format("%02d", Integer.parseInt(selectedUC));
            notifyPropertyChanged(BR.ra06);
        }

        @Bindable
        public String getRa08() {
            return ra08;
        }

        public void setRa08(String ra08) {
            this.ra08 = ra08;
            notifyPropertyChanged(BR.ra08);
        }

        @Bindable
        public String getRa09() {
            return ra09;
        }

        public void setRa09(String ra09) {
            this.ra09 = ra09;
            //households.setHhNo(this.ra09);
            notifyPropertyChanged(BR.ra09);
        }

        @Bindable
        public String getRa10() {
            return ra10;
        }

        public void setRa10(String ra10) {
            //this.ra10 = ra10;
            this.ra10 = ra10;
            notifyPropertyChanged(BR.ra10);
        }

        @Bindable
        public String getRa11() {
            return ra11;
        }

        public void setRa11(String ra11) {
            this.ra11 = ra11;
            notifyPropertyChanged(BR.ra11);
        }

        @Bindable
        public String getRa12() {
            return ra12;
        }

        public void setRa12(String ra12) {
            this.ra12 = ra12;
            notifyPropertyChanged(BR.ra12);
        }

        @Bindable
        public String getRa13() {
            return ra13;
        }

        public void setRa13(String ra13) {
            this.ra13 = ra13;
            notifyPropertyChanged(BR.ra13);
        }

        @Bindable
        public String getRa14() {
            return ra14;
        }

        public void setRa14(String ra14) {
            this.ra14 = ra14;
            notifyPropertyChanged(BR.ra14);
        }

        @Bindable
        public String getRa15() {
            return ra15;
        }

        public void setRa15(String ra15) {
            this.ra15 = ra15;
            notifyPropertyChanged(BR.ra15);
        }

        @Bindable
        public String getRa17_a1() {
            return ra17_a1;
        }

        public void setRa17_a1(String ra17_a1) {
            this.ra17_a1 = ra17_a1;
            notifyPropertyChanged(BR.ra17_a1);
        }

        @Bindable
        public String getRa17_b1() {
            return ra17_b1;
        }

        public void setRa17_b1(String ra17_b1) {
            this.ra17_b1 = ra17_b1;
            notifyPropertyChanged(BR.ra17_b1);
        }

        @Bindable
        public String getRa17_c1() {
            return ra17_c1;
        }

        public void setRa17_c1(String ra17_c1) {
            this.ra17_c1 = ra17_c1;
            notifyPropertyChanged(BR.ra17_c1);
        }

        @Bindable
        public String getRa17_d1() {
            return ra17_d1;
        }

        public void setRa17_d1(String ra17_d1) {
            this.ra17_d1 = ra17_d1;
            notifyPropertyChanged(BR.ra17_d1);
        }

        @Bindable
        public String getRa17_a2() {
            return ra17_a2;
        }

        public void setRa17_a2(String ra17_a2) {
            this.ra17_a2 = ra17_a2;
            notifyPropertyChanged(BR.ra17_a2);
        }

        @Bindable
        public String getRa17_b2() {
            return ra17_b2;
        }

        public void setRa17_b2(String ra17_b2) {
            this.ra17_b2 = ra17_b2;
            notifyPropertyChanged(BR.ra17_b2);
        }

        @Bindable
        public String getRa17_c2() {
            return ra17_c2;
        }

        public void setRa17_c2(String ra17_c2) {
            this.ra17_c2 = ra17_c2;
            setRa18(this.ra17_c2);
            notifyPropertyChanged(BR.ra17_c2);
        }

        @Bindable
        public String getRa17_d2() {
            return ra17_d2;
        }

        public void setRa17_d2(String ra17_d2) {
            this.ra17_d2 = ra17_d2;
            notifyPropertyChanged(BR.ra17_d2);
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
            notifyPropertyChanged(BR.ra18);
        }

        @Bindable
        public String getRa19a() {
            return ra19a;
        }

        public void setRa19a(String ra19a) {
            this.ra19a = ra19a;
            households.setIStatus(ra19a);
            notifyPropertyChanged(BR.ra19a);
        }

        @Bindable
        public String getRa19ax() {
            return ra19ax;
        }

        public void setRa19ax(String ra19ax) {
            this.ra19ax = ra19ax;
            households.setIStatus96x(ra19ax);
            notifyPropertyChanged(BR.ra19ax);
        }

        @Bindable
        public String getRa19b() {
            return ra19b;
        }

        public void setRa19b(String ra19b) {
            this.ra19b = ra19b;
            households.setIStatus(ra19b);
            notifyPropertyChanged(BR.ra19b);
        }

        @Bindable
        public String getRa19bx() {
            return ra19bx;
        }

        public void setRa19bx(String ra19bx) {
            this.ra19bx = ra19bx;
            households.setIStatus96x(ra19bx);
            notifyPropertyChanged(BR.ra19bx);
        }

        @Bindable
        public String getRa19c() {
            return ra19c;
        }

        public void setRa19c(String ra19c) {
            this.ra19c = ra19c;
            households.setIStatus(ra19c);
            notifyPropertyChanged(BR.ra19c);
        }

        @Bindable
        public String getRa19cx() {
            return ra19cx;
        }

        public void setRa19cx(String ra19cx) {
            this.ra19cx = ra19cx;
            households.setIStatus96x(ra19cx);
            notifyPropertyChanged(BR.ra19cx);
        }

        @Bindable
        public String getRa20() {
            return ra20;
        }

        public void setRa20(String ra20) {
            this.ra20 = ra20;
            setRa18(ra20.equals("1") ? this.ra18 : "");
            notifyPropertyChanged(BR.ra20);
        }

        @Bindable
        public String getRa21() {
            return ra21;
        }

        public void setRa21(String ra21) {
            this.ra21 = ra21;
            notifyPropertyChanged(BR.ra21);
        }
    }


}
