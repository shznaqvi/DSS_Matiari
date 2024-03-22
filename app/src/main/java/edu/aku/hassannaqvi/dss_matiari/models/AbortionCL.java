package edu.aku.hassannaqvi.dss_matiari.models;

import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.database.dao.AbortionCLDao;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;

@Entity(tableName = AbortionCL.TABLE_NAME)
public class AbortionCL {

    public static final String TABLE_NAME = "abortionCL";

    private String round = StringUtils.EMPTY;

    // For local use
    // This is used for resolving data while posting
    @ColumnInfo(defaultValue = "0")
    private transient boolean isError;

    @Ignore
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Ignore
    private transient boolean exist = false;

    @SerializedName("regRound")
    private String regRound = StringUtils.EMPTY;

    // APP VARIABLES
    @SerializedName("projectName")
    private String projectName = MainApp.PROJECT_NAME;

    @PrimaryKey(autoGenerate = true)
    @SerializedName("_id")
    private long id;

    @SerializedName("_uid")
    private String uid = _EMPTY_;

    @SerializedName("_wid")
    private String wid = _EMPTY_;

    private String sNo = StringUtils.EMPTY;

    @SerializedName("username")
    private String userName = _EMPTY_;

    @SerializedName("sysdate")
    private String sysDate = _EMPTY_;

    @SerializedName("hdssid")
    private String hdssId = StringUtils.EMPTY;

    @SerializedName("ucCode")
    private String ucCode = StringUtils.EMPTY;

    @SerializedName("villageCode")
    private String villageCode = StringUtils.EMPTY;

    @SerializedName("hhNo")
    private String hhNo = StringUtils.EMPTY;

    @SerializedName("structureNo")
    private transient String structureNo = StringUtils.EMPTY;

    @SerializedName("visitNo")
    private String visitNo = "0";

    @SerializedName("deviceid")
    private String deviceId = StringUtils.EMPTY;

    @SerializedName("appversion")
    private String appver = StringUtils.EMPTY;

    private transient String endTime = StringUtils.EMPTY;

    @SerializedName("istatus")
    String iStatus = StringUtils.EMPTY;

    @Ignore
    transient String iStatus96x = StringUtils.EMPTY;

    @SerializedName("synced")
    private String synced = StringUtils.EMPTY;

    @SerializedName("synced_date")
    private transient String syncDate = StringUtils.EMPTY;

    //private String round = StringUtils.EMPTY;

    @SerializedName("sM")
    private SM sM;

    public AbortionCL() {
    }

    public static void initMeta() {
        MainApp.abortionCL = new AbortionCL();
        MainApp.abortionCL.setRound(MainApp.ROUND);
        MainApp.abortionCL.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        MainApp.abortionCL.setUserName(MainApp.user.getUsername());
        MainApp.abortionCL.setDeviceId(MainApp.deviceid);
        MainApp.abortionCL.setAppver(MainApp.appInfo.getAppVersion());
        MainApp.abortionCL.setUcCode(MainApp.selectedUC);
        MainApp.abortionCL.setVillageCode(MainApp.selectedVillage);
        MainApp.abortionCL.setRegRound("1");
        MainApp.abortionCL.setHdssId(MainApp.mwra.getHdssId());
        MainApp.abortionCL.setSNo(MainApp.mwra.getSNo());
        MainApp.abortionCL.setHhNo(MainApp.mwra.getHhNo());
    }

    /*FOR IDENTIFICATION INFORMATION - CLUSTER-WISE*/
    // Save data in db
    public static void saveMainData(String hdssId, String sNo, String round, SM sM) {
        AbortionCLDao abortionCLDao = DssRoomDatabase.getDbInstance().abortionCLDao();
        AbortionCL abortionCL = abortionCLDao.getAbortionCLByHDSSID(hdssId, sNo, round);
        if (abortionCL != null) {
            MainApp.abortionCL = abortionCL;
        } else {
            MainApp.abortionCL.setUid(AppConstants.generateUid());
            MainApp.abortionCL.setId(abortionCLDao.add(MainApp.abortionCL));
            AbortionCL.SM.saveData(sM);
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

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getSNo() {
        return sNo;
    }

    public void setSNo(String sNo) {
        this.sNo = sNo;
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
        this.hhNo = hhNo;
        /*String[] hdssidSplit = hdssId.split("-");
        String newhhno = String.format("%04d", Integer.parseInt(hhNo));
        this.hhNo = newhhno;
        setHdssId(getUcCode() + "-" + getVillageCode() + "-" + getHhNo());*/
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

    public SM getSM() {
        return sM;
    }

    public void setSM(SM sM) {
        this.sM = sM;
    }


    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
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

    /**
     * SECTION A - IDENTIFICATION INFORMATION - CLUSTER-WISE
     */

    public static class SM extends BaseObservable {
        private String m1 = StringUtils.EMPTY;
        private String m2 = StringUtils.EMPTY;
        private String m3 = StringUtils.EMPTY;
        private String m4 = StringUtils.EMPTY;
        private String m5 = StringUtils.EMPTY;
        private String m6 = StringUtils.EMPTY;
        private String m7 = StringUtils.EMPTY;
        private String m8 = StringUtils.EMPTY;
        private String m9 = StringUtils.EMPTY;
        private String m10 = StringUtils.EMPTY;
        private String m11 = StringUtils.EMPTY;
        private String m12 = StringUtils.EMPTY;
        private String m13 = StringUtils.EMPTY;
        private String m14 = StringUtils.EMPTY;
        private String m15 = StringUtils.EMPTY;
        private String m16 = StringUtils.EMPTY;
        private String m17 = StringUtils.EMPTY;
        private String m18 = StringUtils.EMPTY;
        private String m1901 = StringUtils.EMPTY;
        private String m1902 = StringUtils.EMPTY;
        private String m1996 = StringUtils.EMPTY;
        private String m1996x = StringUtils.EMPTY;
        private String m20 = StringUtils.EMPTY;
        private String m21 = StringUtils.EMPTY;
        private String m22 = StringUtils.EMPTY;
        private String m23 = StringUtils.EMPTY;
        private String m24 = StringUtils.EMPTY;
        private String m2501 = StringUtils.EMPTY;
        private String m2502 = StringUtils.EMPTY;
        private String m2503 = StringUtils.EMPTY;
        private String m26 = StringUtils.EMPTY;
        private String m27 = StringUtils.EMPTY;
        private String m28 = StringUtils.EMPTY;

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<SM> {
            public DataConverter() {
                super(new TypeToken<SM>() {
                }.getType());
            }
        }

        // Save section object as json object in db
        public static int saveData(SM data) {
            MainApp.abortionCL.setSM(data);
            return Objects.requireNonNull(DssRoomDatabase.getDbInstance()).abortionCLDao().update(MainApp.abortionCL);
        }

        public void populateMeta() {
        }

        // Get section object by parsing json
        public static SM getData() {
            return MainApp.abortionCL.getSM();
        }

        @Bindable
        public String getM1() {
            return m1;
        }

        public void setM1(String m1) {
            this.m1 = m1;
            notifyPropertyChanged(BR.m1);
        }

        @Bindable
        public String getM2() {
            return m2;
        }

        public void setM2(String m2) {
            this.m2 = m2;
            notifyPropertyChanged(BR.m2);
        }

        @Bindable
        public String getM3() {
            return m3;
        }

        public void setM3(String m3) {
            this.m3 = m3;
            notifyPropertyChanged(BR.m3);
        }

        @Bindable
        public String getM4() {
            return m4;
        }

        public void setM4(String m4) {
            this.m4 = m4;
            notifyPropertyChanged(BR.m4);
        }

        @Bindable
        public String getM5() {
            return m5;
        }

        public void setM5(String m5) {
            this.m5 = m5;
            notifyPropertyChanged(BR.m5);
        }

        @Bindable
        public String getM6() {
            return m6;
        }

        public void setM6(String m6) {
            this.m6 = m6;
            notifyPropertyChanged(BR.m6);
        }

        @Bindable
        public String getM7() {
            return m7;
        }

        public void setM7(String m7) {
            this.m7 = m7;
            if (!this.m7.equals("")) {
                setM8(calcEDD());
            } else {
                setM8("");
            }
            notifyPropertyChanged(BR.m7);
        }

        @Bindable
        public String getM8() {
            return m8;
        }

        public void setM8(String m8) {
            this.m8 = m8;
            notifyPropertyChanged(BR.m8);
        }

        @Bindable
        public String getM9() {
            return m9;
        }

        public void setM9(String m9) {
            this.m9 = m9;
            notifyPropertyChanged(BR.m9);
        }

        @Bindable
        public String getM10() {
            return m10;
        }

        public void setM10(String m10) {
            this.m10 = m10;
            notifyPropertyChanged(BR.m10);
        }

        @Bindable
        public String getM11() {
            return m11;
        }

        public void setM11(String m11) {
            this.m11 = m11;
            notifyPropertyChanged(BR.m11);
        }

        @Bindable
        public String getM12() {
            return m12;
        }

        public void setM12(String m12) {
            this.m12 = m12;
            notifyPropertyChanged(BR.m12);
        }

        @Bindable
        public String getM13() {
            return m13;
        }

        public void setM13(String m13) {
            this.m13 = m13;
            notifyPropertyChanged(BR.m13);
        }

        @Bindable
        public String getM14() {
            return m14;
        }

        public void setM14(String m14) {
            this.m14 = m14;
            notifyPropertyChanged(BR.m14);
        }

        @Bindable
        public String getM15() {
            return m15;
        }

        public void setM15(String m15) {
            this.m15 = m15;
            notifyPropertyChanged(BR.m15);
        }

        @Bindable
        public String getM16() {
            return m16;
        }

        public void setM16(String m16) {
            this.m16 = m16;
            notifyPropertyChanged(BR.m16);
        }

        @Bindable
        public String getM17() {
            return m17;
        }

        public void setM17(String m17) {
            this.m17 = m17;
            notifyPropertyChanged(BR.m17);
        }

        @Bindable
        public String getM18() {
            return m18;
        }

        public void setM18(String m18) {
            this.m18 = m18;
            setM1901(m18.equals("2") ? this.m1901 : _EMPTY_);
            setM1902(m18.equals("2") ? this.m1902 : _EMPTY_);
            setM1996(m18.equals("2") ? this.m1996 : _EMPTY_);
            setM20(m18.equals("2") ? this.m20 : _EMPTY_);
            notifyPropertyChanged(BR.m18);
        }

        @Bindable
        public String getM1901() {
            return m1901;
        }

        public void setM1901(String m1901) {
            this.m1901 = m1901;
            notifyPropertyChanged(BR.m1901);
        }

        @Bindable
        public String getM1902() {
            return m1902;
        }

        public void setM1902(String m1902) {
            this.m1902 = m1902;
            notifyPropertyChanged(BR.m1902);
        }

        @Bindable
        public String getM1996() {
            return m1996;
        }

        public void setM1996(String m1996) {
            this.m1996 = m1996;
            setM1996x(m1996.equals("96") ? this.m1996x : _EMPTY_);
            notifyPropertyChanged(BR.m1996);
        }

        @Bindable
        public String getM1996x() {
            return m1996x;
        }

        public void setM1996x(String m1996x) {
            this.m1996x = m1996x;
            notifyPropertyChanged(BR.m1996x);
        }

        @Bindable
        public String getM20() {
            return m20;
        }

        public void setM20(String m20) {
            this.m20 = m20;
            notifyPropertyChanged(BR.m20);
        }

        @Bindable
        public String getM21() {
            return m21;
        }

        public void setM21(String m21) {
            this.m21 = m21;
            notifyPropertyChanged(BR.m21);
        }

        @Bindable
        public String getM22() {
            return m22;
        }

        public void setM22(String m22) {
            this.m22 = m22;
            setM23(m22.equals("1") ? this.m23 : _EMPTY_);
            notifyPropertyChanged(BR.m22);
        }

        @Bindable
        public String getM23() {
            return m23;
        }

        public void setM23(String m23) {
            this.m23 = m23;
            notifyPropertyChanged(BR.m23);
        }

        @Bindable
        public String getM24() {
            return m24;
        }

        public void setM24(String m24) {
            this.m24 = m24;
            notifyPropertyChanged(BR.m24);
        }

        @Bindable
        public String getM2501() {
            return m2501;
        }

        public void setM2501(String m2501) {
            this.m2501 = m2501;
            notifyPropertyChanged(BR.m2501);
        }

        @Bindable
        public String getM2502() {
            return m2502;
        }

        public void setM2502(String m2502) {
            this.m2502 = m2502;
            notifyPropertyChanged(BR.m2502);
        }

        @Bindable
        public String getM2503() {
            return m2503;
        }

        public void setM2503(String m2503) {
            this.m2503 = m2503;
            notifyPropertyChanged(BR.m2503);
        }

        @Bindable
        public String getM26() {
            return m26;
        }

        public void setM26(String m26) {
            this.m26 = m26;
            notifyPropertyChanged(BR.m26);
        }

        @Bindable
        public String getM27() {
            return m27;
        }

        public void setM27(String m27) {
            this.m27 = m27;
            notifyPropertyChanged(BR.m27);
        }

        @Bindable
        public String getM28() {
            return m28;
        }

        public void setM28(String m28) {
            this.m28 = m28;
            notifyPropertyChanged(BR.m28);
        }

        public String calcEDD() {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                cal.setTime(Objects.requireNonNull(sdf.parse(getM7())));// all done

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


}
