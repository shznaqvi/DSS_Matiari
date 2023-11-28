package edu.aku.hassannaqvi.dss_matiari.models;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.BR;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.room.MwraDao;

@Entity(tableName = MWRATable.TABLE_NAME)
public class Mwra extends BaseObservable implements Observable {

    // APP VARIABLES
    @ColumnInfo(name = MWRATable.COLUMN_PROJECT_NAME)
    private String projectName = PROJECT_NAME;

    @SerializedName("_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = MWRATable.COLUMN_ID)
    long id = 0;

    @SerializedName("_uid")
    @ColumnInfo(name = MWRATable.COLUMN_UID)
    private String uid = StringUtils.EMPTY;

    @SerializedName("_uuid")
    @ColumnInfo(name = MWRATable.COLUMN_UUID)
    private String uuid = StringUtils.EMPTY;

    @SerializedName("username")
    @ColumnInfo(name = MWRATable.COLUMN_USERNAME)
    private String userName = StringUtils.EMPTY;

    @SerializedName("sysdate")
    @ColumnInfo(name = MWRATable.COLUMN_SYSDATE)
    private String sysDate = StringUtils.EMPTY;

    @SerializedName("hdssid")
    @ColumnInfo(name = MWRATable.COLUMN_HDSSID)
    private String hdssId = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_STRUCTURE_NO)
    private transient String structureNo = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_SNO)
    private String sNo = StringUtils.EMPTY;

    @SerializedName("deviceid")
    @ColumnInfo(name = MWRATable.COLUMN_DEVICEID)
    private String deviceId = StringUtils.EMPTY;

    @SerializedName("devicetagid")
    @ColumnInfo(name = MWRATable.COLUMN_DEVICETAGID)
    private transient String deviceTag = StringUtils.EMPTY;

    @SerializedName("appversion")
    @ColumnInfo(name = MWRATable.COLUMN_APPVERSION)
    private String appver = StringUtils.EMPTY;


    @ColumnInfo(name = MWRATable.COLUMN_SYNCED)
    private String synced = StringUtils.EMPTY;

    @SerializedName("synced_date")
    @ColumnInfo(name = MWRATable.COLUMN_SYNCED_DATE)
    private transient String syncDate = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_REGROUND)
    private String regRound = StringUtils.EMPTY;

    @ColumnInfo(name = MWRATable.COLUMN_CHILD_COUNT)
    private transient String child_count = StringUtils.EMPTY;

    @SerializedName("istatus")
    @ColumnInfo(name = MWRATable.COLUMN_ISTATUS)
    private transient String istatus = StringUtils.EMPTY;


    private String round = StringUtils.EMPTY;

    @SerializedName("s2")
    private SB sB;

    @SerializedName("s3")
    private SC sC;

    @SerializedName("s4")
    private SD sD;

    @Ignore
    private String pregnum = StringUtils.EMPTY;


    @Ignore
    private String prePreg = StringUtils.EMPTY;

    @Ignore
    private transient String preMaritalStaus = StringUtils.EMPTY;

    // For local use
    // This is used for resolving data while posting
    @ColumnInfo(defaultValue = "0")
    private transient boolean isError;

    public Mwra() {

    }

    public static void init() {
        mwra = new Mwra();
        MainApp.mwra.setRound(MainApp.ROUND);
        MainApp.mwra.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        MainApp.mwra.setUserName(MainApp.user.getUsername());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setAppver(MainApp.appInfo.getAppVersion());
        if (MainApp.households != null) {
            MainApp.mwra.setUuid(MainApp.households.getUid());
        }
        MainApp.mwra.setVillageCode(MainApp.selectedVillage);
        MainApp.mwra.setUcCode(MainApp.selectedUC);

        MainApp.mwra.setProjectName(PROJECT_NAME);
        MainApp.mwra.setRound(MainApp.ROUND);
        MainApp.mwra.setRegRound("1");
        MainApp.mwra.setStructureNo(households.getStructureNo());
        MainApp.mwra.setHhNo(households.getHhNo());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setHdssId(households.getHdssId());

    }

    /*public static void populateMeta() {

        MainApp.mwra.setSysDate(MainApp.households.getSysDate());
        MainApp.mwra.setUuid(MainApp.households.getUid());  // not applicable in Form table
        MainApp.mwra.setUserName(MainApp.user.getUsername());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setAppver(MainApp.appInfo.getAppVersion());
        MainApp.mwra.setProjectName(PROJECT_NAME);
        MainApp.mwra.setRound(MainApp.ROUND);
        MainApp.mwra.setRegRound("1");
        MainApp.mwra.setUcCode(households.getUcCode());
        MainApp.mwra.setVillageCode(households.getVillageCode());
        MainApp.mwra.setStructureNo(households.getStructureNo());
        MainApp.mwra.setHhNo(households.getHhNo());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setHdssId(households.getHdssId());
        MainApp.mwra.setAppver(MainApp.versionName + "." + MainApp.versionCode);
        //setPregnum("0");

    }
*/

    public static void populateMetaFollowups() {

        MainApp.mwra.setUserName(MainApp.user.getUsername());
        MainApp.mwra.setDeviceId(MainApp.deviceid);
        MainApp.mwra.setAppver(MainApp.appInfo.getAppVersion());

        MainApp.mwra.setSysDate(MainApp.households.getSysDate());
        MainApp.mwra.setUuid(MainApp.households.getUid());  // not applicable in Form table
        MainApp.mwra.setProjectName(PROJECT_NAME);
        MainApp.mwra.setRegRound("");

        // From FollowupsSche - MWRA

        mwra.setHdssId(MainApp.fpMwra.getHdssid());
        mwra.setUcCode(MainApp.fpMwra.getUcCode());
        mwra.setVillageCode(MainApp.fpMwra.getVillageCode());
        mwra.setHhNo(MainApp.fpMwra.getHhNo());
        mwra.setRound(MainApp.fpMwra.getFRound());
        mwra.setSNo(MainApp.fpMwra.getRb01());
        mwra.setChild_count(MainApp.fpMwra.getChild_count());
        mwra.getSC().setRb01(MainApp.fpMwra.getRb01());  // Line number of MWRA
        mwra.getSC().setRb02(MainApp.fpMwra.getRb02());  // Name of MWRA
        mwra.getSC().setRb03(MainApp.fpMwra.getRb03()); // Husband / Father Name
        mwra.getSC().setRb04(MainApp.fpMwra.getRb04()); // DOB
        mwra.setPrePreg(MainApp.fpMwra.getRb07());
        mwra.getSC().setRb06(MainApp.fpMwra.getRb06());
        mwra.setPreMaritalStaus(MainApp.fpMwra.getRb06());
        mwra.setPregnum(MainApp.fpMwra.getPregCount());
        mwra.getSC().setRb22(MainApp.fpMwra.getRb22());
        mwra.getSC().setRb23(MainApp.fpMwra.getRb23());

        long daysdiff = CalculateAge(MainApp.fpMwra.getRa01().getDate());
        long years = daysdiff / 365;
        long actualAge = Integer.parseInt(MainApp.fpMwra.getRb05()) + years;
        mwra.getSC().setRb05(String.valueOf(actualAge));     // Age in Years
    }


    /*FOR IDENTIFICATION INFORMATION - CLUSTER-WISE*/
    // Save data in db
    public static void saveMainDataReg(String uuid, String hdssId, String sNo, String regRound) throws JSONException {
        MwraDao mwraDao = Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao();
        Mwra form = mwraDao.getMwraByUUId(uuid, hdssId, sNo, regRound);
        if (form != null) {
            mwra = form;
            mwra.setDeviceId(mwra.getDeviceId() + "_" + mwra.getDeviceId().substring(mwra.getDeviceId().length() - 1));
            int repeatCount = (mwra.getDeviceId().length() - 16) / 2;
            // new UID
            String newUID = mwra.getDeviceId().substring(0, 16) + mwra.getId() + "_" + repeatCount;
            mwra.setUid(newUID);
            Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao().updateMwra(mwra);
        } else {
            init();
            mwra.setUid(AppConstants.generateUid());
            mwra.setId(mwraDao.addMwra(mwra));

        }
    }

    /*FOR IDENTIFICATION INFORMATION - CLUSTER-WISE*/
    // Save data in db
    public static void saveMainDataFup(String uuid, String hdssId, String sNo, String regRound, SC sC) throws JSONException {
        MwraDao mwraDao = Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao();
        Mwra form = mwraDao.getMwraByUUId(uuid, hdssId, sNo, regRound);
        if (form != null) {
            mwra = form;
        } else {
            populateMetaFollowups();
            mwra.setUid(AppConstants.generateUid());
            mwra.setId(mwraDao.addMwra(mwra));
            Mwra.SC.saveData(sC);
        }
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

    public String getRegRound() {
        return regRound;
    }

    public void setRegRound(String regRound) {
        this.regRound = regRound;
    }

    public String getPreMaritalStaus() {
        return preMaritalStaus;
    }

    public void setPreMaritalStaus(String preMaritalStaus) {
        this.preMaritalStaus = preMaritalStaus;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
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

    public SB getSB() {
        return sB;
    }

    public void setSB(SB sB) {
        this.sB = sB;
    }

    public SC getSC() {
        return sC;
    }

    public void setSC(SC sC) {
        this.sC = sC;
    }

    public SD getSD() {
        return sD;
    }

    public void setSD(SD sD) {
        this.sD = sD;
    }

    public String getChild_count() {
        return child_count;
    }

    @Bindable
    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }


    public String getPregnum() {
        return pregnum;
    }

    public void setPregnum(String pregnum) {
        this.pregnum = pregnum;
    }

    @Bindable
    public String getPrePreg() {
        return prePreg;
    }

    public void setPrePreg(String prePreg) {
        this.prePreg = prePreg;
        notifyPropertyChanged(BR.prePreg);
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }


    /**
     * SECTION B - MWRA Registration
     */

    public static class SB extends BaseObservable {

        private String rb01a = StringUtils.EMPTY;
        private String rb01 = StringUtils.EMPTY;
        private String rb02 = StringUtils.EMPTY;
        private String rb22 = StringUtils.EMPTY;
        private String rb03 = StringUtils.EMPTY;
        private String rb23 = StringUtils.EMPTY;
        private String rb03a = StringUtils.EMPTY;
        private String rb03b = StringUtils.EMPTY;
        private String rb04 = StringUtils.EMPTY;
        private String rb05 = StringUtils.EMPTY;
        private String rb06 = StringUtils.EMPTY;
        private String rb07 = StringUtils.EMPTY;
        private String rb08 = StringUtils.EMPTY;
        private String rb09 = StringUtils.EMPTY;
        private String rb18 = StringUtils.EMPTY;
        private String rb19 = StringUtils.EMPTY;
        private String rb20 = StringUtils.EMPTY;
        private String rb21 = StringUtils.EMPTY;
        private String rb24 = StringUtils.EMPTY;
        private String rb25 = StringUtils.EMPTY;
        private String pregnum = StringUtils.EMPTY;
        private long ageInMonths;

        // Save section object as json object in db
        public static void saveData(Mwra.SB data) {
            mwra.setSB(data);
            Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao().updateMwra(mwra);
        }

        // Get section object by parsing json
        public static Mwra.SB getData() {
            return mwra.getSB();
        }

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<Mwra.SB> {
            public DataConverter() {
                super(new TypeToken<Mwra.SB>() {
                }.getType());
            }
        }

        // Getters & Setters

        @Bindable
        public String getRb01() {
            return rb01;
        }

        public void setRb01(String rb01) {
            this.rb01 = rb01;
            //this.sNo = rb01;
            notifyPropertyChanged(BR.rb01);
        }

        @Bindable
        public String getRb01a() {
            return rb01a;
        }

        public void setRb01a(String ra01) {
            this.rb01a = ra01;
            notifyPropertyChanged(BR.ra01);
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
        public String getRb22() {
            return rb22;
        }

        public void setRb22(String rb22) {
            this.rb22 = rb22;
            notifyPropertyChanged(BR.rb22);
        }

        @Bindable
        public String getRb03() {
            return rb03;
        }

        public void setRb03(String rb03) {
            this.rb03 = rb03;
            notifyPropertyChanged(BR.rb03);
        }

        @Bindable
        public String getRb23() {
            return rb23;
        }

        public void setRb23(String rb23) {
            this.rb23 = rb23;
            notifyPropertyChanged(BR.rb23);
        }

        @Bindable
        public String getRb03a() {
            return rb03a;
        }

        public void setRb03a(String rb03a) {
            this.rb03a = rb03a;
            notifyPropertyChanged(BR.rb03a);
        }

        @Bindable
        public String getRb03b() {
            return rb03b;
        }

        public void setRb03b(String rb03b) {
            this.rb03b = rb03b;
            notifyPropertyChanged(BR.rb03b);
        }

        @Bindable
        public String getRb04() {
            return rb04;
        }

        public void setRb04(String rb04) {
            this.rb04 = rb04;
            if (mwra.regRound.equals("1")) {
                if (!this.rb04.equals("98")) {
                    setRb05(this.rb05);
                    CaluculateAge();
                } else {
                    setRb05("");
                }
            }
            notifyPropertyChanged(BR.rb04);
        }

        @Bindable
        public String getRb05() {
            return rb05;
        }

        public void setRb05(String rb05) {
            this.rb05 = rb05;
            notifyPropertyChanged(BR.rb05);
        }

        @Bindable
        public String getRb06() {
            return rb06;
        }

        public void setRb06(String rb06) {
            this.rb06 = rb06;
            //setRb15(this.rb06.equals("4") ? "" : this.rb15);
            setRb20(this.rb06.equals("4") ? "" : this.rb20);
            setRb21(this.rb06.equals("4") ? "" : this.rb21);

            notifyPropertyChanged(BR.rb06);
        }

        @Bindable
        public String getRb07() {
            return rb07;
        }

        public void setRb07(String rb07) {
            this.rb07 = rb07;
            setRb08(this.rb07.equals("1") ? this.rb08 : "");
            setRb09(this.rb07.equals("1") ? this.rb09 : "");
            setRb24(this.rb07.equals("1") ? this.rb24 : "");
            notifyPropertyChanged(BR.rb07);
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
            notifyPropertyChanged(BR.rb08);
        }

        @Bindable
        public String getRb09() {
            return rb09;
        }

        public void setRb09(String rb09) {
            this.rb09 = rb09;
            notifyPropertyChanged(BR.rb09);
        }

        @Bindable
        public String getRb18() {
            return rb18;
        }

        public void setRb18(String rb18) {
            this.rb18 = rb18;
            setRb19(rb18.equals("1") ? this.rb19 : "");
            setRb20(rb18.equals("1") ? this.rb20 : "");
            setRb21(rb18.equals("1") ? this.rb21 : "");
            notifyPropertyChanged(BR.rb18);
        }

        @Bindable
        public String getRb19() {
            return rb19;
        }

        public void setRb19(String rb19) {
            this.rb19 = rb19;
            notifyPropertyChanged(BR.rb19);
        }

        @Bindable
        public String getRb20() {
            return rb20;
        }

        public void setRb20(String rb20) {
            this.rb20 = rb20;
            notifyPropertyChanged(BR.rb20);
        }

        @Bindable
        public String getRb21() {
            return rb21;
        }

        public void setRb21(String rb21) {
            this.rb21 = rb21;
            notifyPropertyChanged(BR.rb21);
        }

        @Bindable
        public String getRb24() {
            return rb24;
        }

        public void setRb24(String rb24) {
            this.rb24 = rb24;
            setRb25(rb24.equals("1") ? this.rb25 : "");
            notifyPropertyChanged(BR.rb24);
        }

        @Bindable
        public String getRb25() {
            return rb25;
        }

        public void setRb25(String rb25) {
            this.rb25 = rb25;
            notifyPropertyChanged(BR.rb25);
        }

        private void CaluculateAge() {


            setRb05("");


            try {
                Calendar cal = Calendar.getInstance();
                Calendar cur = Calendar.getInstance();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                cal.setTime(Objects.requireNonNull(sdf.parse(getRb01a())));// all done


                //long millis = System.currentTimeMillis() - cal.getTimeInMillis();
                long millis = cur.getTimeInMillis() - cal.getTimeInMillis();
                cal.setTimeInMillis(millis);


                this.ageInMonths = MILLISECONDS.toDays(millis) / 30;
                long tYear = MILLISECONDS.toDays(millis) / 365;
                long tMonth = (MILLISECONDS.toDays(millis) - (tYear * 365)) / 30;
                long tDay = MILLISECONDS.toDays(millis) - ((tYear * 365) + (tMonth * 30));

                setRb05(String.valueOf(tYear));


            } catch (ParseException e) {
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

    /**
     * SECTION C - MWRA Followup
     */

    public static class SC extends BaseObservable {

        private String rb01a = StringUtils.EMPTY;
        private String rb01 = StringUtils.EMPTY;
        private String rb02 = StringUtils.EMPTY;
        private String rb22 = StringUtils.EMPTY;
        private String rb03 = StringUtils.EMPTY;
        private String rb23 = StringUtils.EMPTY;
        private String rb03a = StringUtils.EMPTY;
        private String rb03b = StringUtils.EMPTY;
        private String rb04 = StringUtils.EMPTY;
        private String rb05 = StringUtils.EMPTY;
        private String rb06 = StringUtils.EMPTY;
        private String rb07 = StringUtils.EMPTY;
        private String rb10 = StringUtils.EMPTY;
        private String rb11 = StringUtils.EMPTY;
        private String rb12 = StringUtils.EMPTY;
        private String rb13 = StringUtils.EMPTY;
        private String rb14 = StringUtils.EMPTY;
        private String rb15 = StringUtils.EMPTY;
        private String rb16 = StringUtils.EMPTY;
        private String rb17 = StringUtils.EMPTY;
        private String rb18 = StringUtils.EMPTY;
        private String rb19 = StringUtils.EMPTY;
        private String rb20 = StringUtils.EMPTY;
        private String rb21 = StringUtils.EMPTY;
        private long ageInMonths;

        // Save section object as json object in db
        public static void saveData(Mwra.SC data) {
            mwra.setSC(data);
            Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao().updateMwra(mwra);
        }

        // Get section object by parsing json
        public static Mwra.SC getData() {
            return mwra.getSC();
        }

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<Mwra.SC> {
            public DataConverter() {
                super(new TypeToken<Mwra.SC>() {
                }.getType());
            }
        }


        // Getters & Setters

        @Bindable
        public String getRb01() {
            return rb01;
        }

        public void setRb01(String rb01) {
            this.rb01 = rb01;
            //this.sNo = rb01;
            notifyPropertyChanged(BR.rb01);
        }

        @Bindable
        public String getRb01a() {
            return rb01a;
        }

        public void setRb01a(String ra01) {
            this.rb01a = ra01;
            notifyPropertyChanged(BR.ra01);
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
        public String getRb22() {
            return rb22;
        }

        public void setRb22(String rb22) {
            this.rb22 = rb22;
            notifyPropertyChanged(BR.rb22);
        }

        @Bindable
        public String getRb03() {
            return rb03;
        }

        public void setRb03(String rb03) {
            this.rb03 = rb03;
            notifyPropertyChanged(BR.rb03);
        }

        @Bindable
        public String getRb23() {
            return rb23;
        }

        public void setRb23(String rb23) {
            this.rb23 = rb23;
            notifyPropertyChanged(BR.rb23);
        }

        @Bindable
        public String getRb03a() {
            return rb03a;
        }

        public void setRb03a(String rb03a) {
            this.rb03a = rb03a;
            notifyPropertyChanged(BR.rb03a);
        }

        @Bindable
        public String getRb03b() {
            return rb03b;
        }

        public void setRb03b(String rb03b) {
            this.rb03b = rb03b;
            notifyPropertyChanged(BR.rb03b);
        }

        @Bindable
        public String getRb04() {
            return rb04;
        }

        public void setRb04(String rb04) {
            this.rb04 = rb04;
            if (mwra.regRound.equals("1")) {
                if (!this.rb04.equals("98")) {
                    setRb05(this.rb05);
                    CaluculateAge();
                } else {
                    setRb05("");
                }
            }
            notifyPropertyChanged(BR.rb04);
        }

        @Bindable
        public String getRb05() {
            return rb05;
        }

        public void setRb05(String rb05) {
            this.rb05 = rb05;
            notifyPropertyChanged(BR.rb05);
        }

        @Bindable
        public String getRb06() {
            return rb06;
        }

        public void setRb06(String rb06) {
            this.rb06 = rb06;
            setRb15(this.rb06.equals("4") ? "" : this.rb15);
            setRb20(this.rb06.equals("4") ? "" : this.rb20);
            setRb21(this.rb06.equals("4") ? "" : this.rb21);

            notifyPropertyChanged(BR.rb06);
        }


        @Bindable
        public String getRb07() {
            return rb07;
        }

        public void setRb07(String rb07) {
            this.rb07 = rb07;
            notifyPropertyChanged(BR.rb07);
        }


        @Bindable
        public String getRb10() {
            return rb10;
        }

        public void setRb10(String rb10) {
            this.rb10 = rb10;
            setRb11(rb10.equals("7") ? "2" : "1");
            setRb14(rb10.equals("1") ? this.rb14 : "");
            setRb15(rb10.equals("1") ? this.rb15 : "");
            setRb16(rb10.equals("1") ? this.rb16 : "");
            setRb17(rb10.equals("1") ? this.rb17 : "");

            mwra.setIstatus(rb10);
            notifyPropertyChanged(BR.rb10);
        }

        @Bindable
        public String getRb11() {
            return rb11;
        }

        public void setRb11(String rb11) {
            this.rb11 = rb11;
            setRb12(rb11.equals("1") ? this.rb12 : "");
            setRb13(rb11.equals("1") ? this.rb13 : "");

            notifyPropertyChanged(BR.rb11);
        }


        @Bindable
        public String getRb12() {
            return rb12;
        }

        public void setRb12(String rb12) {
            this.rb12 = rb12;
            notifyPropertyChanged(BR.rb12);
        }

        @Bindable
        public String getRb13() {
            return rb13;
        }

        public void setRb13(String rb13) {
            this.rb13 = rb13;
            notifyPropertyChanged(BR.rb13);
        }

        @Bindable
        public String getRb14() {
            return rb14;
        }

        public void setRb14(String rb14) {
            this.rb14 = rb14;
            notifyPropertyChanged(BR.rb14);
        }

        @Bindable
        public String getRb15() {
            return rb15;
        }

        public void setRb15(String rb15) {
            this.rb15 = rb15;
            notifyPropertyChanged(BR.rb15);
        }

        @Bindable
        public String getRb16() {
            return rb16;
        }

        public void setRb16(String rb16) {
            this.rb16 = rb16;
            setRb15(rb16.equals("4") ? "" : this.rb15);
            setRb17(rb16.equals("5") ? "2" : this.rb17);
            notifyPropertyChanged(BR.rb16);
        }

        @Bindable
        public String getRb17() {
            return rb17;
        }

        public void setRb17(String rb17) {
            this.rb17 = rb17;
            notifyPropertyChanged(BR.rb17);
        }


        @Bindable
        public String getRb18() {
            return rb18;
        }

        public void setRb18(String rb18) {
            this.rb18 = rb18;
            setRb19(rb18.equals("1") ? this.rb19 : "");
            setRb20(rb18.equals("1") ? this.rb20 : "");
            setRb21(rb18.equals("1") ? this.rb21 : "");
            notifyPropertyChanged(BR.rb18);
        }

        @Bindable
        public String getRb19() {
            return rb19;
        }

        public void setRb19(String rb19) {
            this.rb19 = rb19;
            notifyPropertyChanged(BR.rb19);
        }

        @Bindable
        public String getRb20() {
            return rb20;
        }

        public void setRb20(String rb20) {
            this.rb20 = rb20;
            notifyPropertyChanged(BR.rb20);
        }

        @Bindable
        public String getRb21() {
            return rb21;
        }

        public void setRb21(String rb21) {
            this.rb21 = rb21;
            notifyPropertyChanged(BR.rb21);
        }

        private void CaluculateAge() {


            setRb05("");


            try {
                Calendar cal = Calendar.getInstance();
                Calendar cur = Calendar.getInstance();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                cal.setTime(Objects.requireNonNull(sdf.parse(getRb01a())));// all done


                //long millis = System.currentTimeMillis() - cal.getTimeInMillis();
                long millis = cur.getTimeInMillis() - cal.getTimeInMillis();
                cal.setTimeInMillis(millis);


                this.ageInMonths = MILLISECONDS.toDays(millis) / 30;
                long tYear = MILLISECONDS.toDays(millis) / 365;
                long tMonth = (MILLISECONDS.toDays(millis) - (tYear * 365)) / 30;
                long tDay = MILLISECONDS.toDays(millis) - ((tYear * 365) + (tMonth * 30));

                setRb05(String.valueOf(tYear));


            } catch (ParseException e) {
                e.printStackTrace();

            }
        }


    }

    /**
     * SECTION D - Pregnancy
     */

    public static class SD extends BaseObservable {

        private String rb07 = StringUtils.EMPTY;
        private String rb08 = StringUtils.EMPTY;
        private String rb09 = StringUtils.EMPTY;
        private String rb24 = StringUtils.EMPTY;
        private String rb25 = StringUtils.EMPTY;

        // Save section object as json object in db
        public static void saveData(Mwra.SD data) {
            mwra.setSD(data);
            Objects.requireNonNull(DssRoomDatabase.getDbInstance()).mwraDao().updateMwra(mwra);
        }

        // Get section object by parsing json
        public static Mwra.SD getData() {
            return mwra.getSD();
        }

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<Mwra.SD> {
            public DataConverter() {
                super(new TypeToken<Mwra.SD>() {
                }.getType());
            }
        }

        // Getters & Setters

        @Bindable
        public String getRb07() {
            return rb07;
        }

        public void setRb07(String rb07) {
            this.rb07 = rb07;
            setRb08(this.rb07.equals("1") ? this.rb08 : "");
            setRb09(this.rb07.equals("1") ? this.rb09 : "");
            setRb24(this.rb07.equals("1") ? this.rb24 : "");
            notifyPropertyChanged(BR.rb07);
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
            notifyPropertyChanged(BR.rb08);
        }

        @Bindable
        public String getRb09() {
            return rb09;
        }

        public void setRb09(String rb09) {
            this.rb09 = rb09;
            notifyPropertyChanged(BR.rb09);
        }

        @Bindable
        public String getRb24() {
            return rb24;
        }

        public void setRb24(String rb24) {
            this.rb24 = rb24;
            setRb25(rb24.equals("1") ? this.rb25 : "");
            notifyPropertyChanged(BR.rb24);
        }

        @Bindable
        public String getRb25() {
            return rb25;
        }

        public void setRb25(String rb25) {
            this.rb25 = rb25;
            notifyPropertyChanged(BR.rb25);
        }

        public String calcEDD() {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                cal.setTime(Objects.requireNonNull(sdf.parse(getRb08())));// all done

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

    /*@Bindable
    public String getRb24() {
        return rb24;
    }

    public void setRb24(String rb24) {
        this.rb24 = rb24;
        setRb25(rb24.equals("1") ? this.rb25 : "");
        notifyChange(BR.rb24);
    }

    @Bindable
    public String getRb25() {
        return rb25;
    }

    public void setRb25(String rb25) {
        this.rb25 = rb25;
        notifyChange(BR.rb25);
    }



    @Bindable
    public String getRb03a() {
        return rb03a;
    }

    public void setRb03a(String rb03a) {
        this.rb03a = rb03a;
        notifyChange(BR.rb03a);
    }

    @Bindable
    public String getRb03b() {
        return rb03b;
    }

    public void setRb03b(String rb03b) {
        this.rb03b = rb03b;
        notifyChange(BR.rb03b);
    }

    @Bindable
    public String getRb10() {
        return rb10;
    }

    public void setRb10(String rb10) {
        this.rb10 = rb10;
        setRb11(rb10.equals("7") ? "2" : "1");
        setRb14(rb10.equals("1") ? this.rb14 : "");
        setRb15(rb10.equals("1") ? this.rb15 : "");
        setRb16(rb10.equals("1") ? this.rb16 : "");
        setRb17(rb10.equals("1") ? this.rb17 : "");

        setIstatus(rb10);
        notifyChange(BR.rb10);
    }

    @Bindable
    public String getRb11() {
        return rb11;
    }

    public void setRb11(String rb11) {
        this.rb11 = rb11;
        setRb12(rb11.equals("1") ? this.rb12 : "");
        setRb13(rb11.equals("1") ? this.rb13 : "");

        notifyChange(BR.rb11);
    }


    @Bindable
    public String getRb12() {
        return rb12;
    }

    public void setRb12(String rb12) {
        this.rb12 = rb12;
        notifyChange(BR.rb12);
    }

    @Bindable
    public String getRb13() {
        return rb13;
    }

    public void setRb13(String rb13) {
        this.rb13 = rb13;
        notifyChange(BR.rb13);
    }

    @Bindable
    public String getRb14() {
        return rb14;
    }

    public void setRb14(String rb14) {
        this.rb14 = rb14;
        notifyChange(BR.rb14);
    }

    @Bindable
    public String getRb15() {
        return rb15;
    }

    public void setRb15(String rb15) {
        this.rb15 = rb15;
        notifyChange(BR.rb15);
    }

    @Bindable
    public String getRb16() {
        return rb16;
    }

    public void setRb16(String rb16) {
        this.rb16 = rb16;
        setRb17(rb16.equals("5") ? "2" : this.rb17);
        notifyChange(BR.rb16);
    }
    @Bindable
    public String getRb17() {
        return rb17;
    }

    public void setRb17(String rb17) {
        this.rb17 = rb17;
        notifyChange(BR.rb17);
    }


    @Bindable
    public String getRb18() {
        return rb18;
    }

    public void setRb18(String rb18) {
        this.rb18 = rb18;
        setRb19(rb18.equals("1") ? this.rb19 : "");
        setRb20(rb18.equals("1") ? this.rb20 : "");
        setRb21(rb18.equals("1") ? this.rb21 : "");
        notifyChange(BR.rb18);
    }

    @Bindable
    public String getRb19() {
        return rb19;
    }

    public void setRb19(String rb19) {
        this.rb19 = rb19;
        notifyChange(BR.rb19);
    }

    @Bindable
    public String getRb20() {
        return rb20;
    }

    public void setRb20(String rb20) {
        this.rb20 = rb20;
        notifyChange(BR.rb20);
    }

    @Bindable
    public String getRb21() {
        return rb21;
    }

    public void setRb21(String rb21) {
        this.rb21 = rb21;
        notifyChange(BR.rb21);
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
    }*/

    /*public Mwra Hydrate(Mwra mwra) throws JSONException {
        this.id = mwra.id;
        this.uid = mwra.uid;
        this.uuid = mwra.uuid;
        this.userName = mwra.userName;
        this.sysDate = mwra.sysDate;
        this.hdssId = mwra.hdssId;
        this.ucCode = mwra.ucCode;
        this.villageCode = mwra.villageCode;
        this.structureNo = mwra.structureNo;
        this.regRound = mwra.regRound;
        this.sNo = mwra.sNo;
        this.hhNo = mwra.hhNo;
        this.child_count = mwra.child_count;
        this.deviceId = mwra.deviceId;
        this.deviceTag = mwra.deviceTag;
        this.appver = mwra.appver;
        this.istatus = mwra.istatus;
        this.synced = mwra.synced;
        this.syncDate = mwra.syncDate;

        //sBHydrate(mwra.sB);
        sCHydrate(mwra.sC);
        sDHydrate(mwra.sD);

        return this;
    }*/


    /*public void sBHydrate(String string) throws JSONException {
        Log.d(TAG, "s2Hydrate: " + string);
        if (string != null && !string.equals("")) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb01 = json.getString("rb01");
            this.rb01a = json.getString("rb01a");
            this.rb02 = json.getString("rb02");
            this.rb22 = json.has("rb22") ? json.getString("rb22") : "";
            this.rb03 = json.getString("rb03");
            this.rb23 = json.has("rb23") ? json.getString("rb23") : "";
            this.rb03a = json.getString("rb03a");
            this.rb03b = json.getString("rb03b");
            this.rb04 = json.getString("rb04");
            this.rb05 = json.getString("rb05");
            this.rb06 = json.getString("rb06");
            this.rb07 = json.getString("rb07");
            this.rb08 = json.getString("rb08");
            this.rb09 = json.getString("rb09");
            this.rb18 = json.getString("rb18");
            this.rb19 = json.getString("rb19");
            this.rb20 = json.getString("rb20");
            this.rb21 = json.getString("rb21");
            this.rb24 = json.getString(("rb24"));
            this.rb25 = json.getString(("rb25"));
            this.pregnum = json.getString("pregnum");
        }
    }*/

    /*public void sCHydrate(String string) throws JSONException {
        Log.d(TAG, "s3Hydrate: " + string);
        if (string != null && !string.equals("")) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb01 = json.getString("rb01");
            this.rb01a = json.getString("rb01a");
            this.rb02 = json.getString("rb02");
            this.rb22 = json.has("rb22") ? json.getString("rb22") : "";
            this.rb03 = json.getString("rb03");
            this.rb23 = json.has("rb23") ? json.getString("rb23") : "";
            this.rb04 = json.getString("rb04");
            this.rb05 = json.getString("rb05");
            this.prePreg = json.getString("prePreg");
            this.rb06 = json.getString("rb06");
            this.rb07 = json.getString("prePreg");
            this.rb03a = json.getString("rb03a");
            this.rb03b = json.getString("rb03b");
            this.rb10 = json.getString("rb10");
            this.rb11 = json.getString("rb11");
            this.rb12 = json.getString("rb12");
            this.rb13 = json.getString("rb13");
            this.rb14 = json.getString("rb14");
            this.rb15 = json.getString("rb15");
            this.rb16 = json.getString("rb16");
            this.rb17 = json.getString("rb17");
            this.rb18 = json.getString("rb18");
            this.rb19 = json.getString("rb19");
            this.rb20 = json.getString("rb20");
            this.rb21 = json.getString("rb21");
            this.pregnum = json.getString("pregnum");
        }
    }*/


    /*public String sBtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("rb01", rb01)
                .put("rb01a", rb01a)
//                .put("ROUND", round)
                .put("rb02", rb02)
                .put("rb22", rb22)
                .put("rb03", rb03)
                .put("rb23", rb23)
                .put("rb03a", rb03a)
                .put("rb03b", rb03b)
                .put("rb04", rb04)
                .put("rb05", rb05)
                .put("rb06", rb06)
                .put("rb07", rb07)
                .put("rb08", rb08)
                .put("rb09", rb09)
                .put("rb18", rb18)
                .put("rb19", rb19)
                .put("rb20", rb20)
                .put("rb21", rb21)
                .put("rb24", rb24)
                .put("rb25", rb25)
                .put("pregnum", pregnum);

        return json.toString();
    }

    public String sCtoString() throws JSONException {
        JSONObject json = new JSONObject();


        json.put("rb01", rb01)
                .put("rb01a", rb01a)
//                .put("ROUND", round)
                .put("rb02", rb02)
                .put("rb22", rb22)
                .put("rb03", rb03)
                .put("rb23", rb23)
                .put("rb03a",rb03a)
                .put("rb03b",rb03b)
                .put("rb04", rb04)
                .put("rb05", rb05)
                .put("rb06", rb06)
                .put("rb07", prePreg)
                .put("prePreg", prePreg)
                //.put("preMaritalStatus", preMaritalStaus)
                .put("rb10",rb10)
                .put("rb11",rb11)
                .put("rb12",rb12)
                .put("rb13",rb13)
                .put("rb14",rb14)
                .put("rb15",rb15)
                .put("rb16",rb16)
                .put("rb17",rb17)
                .put("rb18",rb18)
                .put("rb19",rb19)
                .put("rb20",rb20)
                .put("rb21", rb21)
                .put("pregnum", pregnum);

        ;

        return json.toString();
    }

    public String SDtoString() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("rb07", rb07)
                .put("rb08", rb08)
                .put("rb09", rb09)
                .put("rb24", rb24)
                .put("rb25", rb25)
                .put("pregnum", pregnum)
        ;

        return json.toString();
    }

    public void sDHydrate(String string) throws JSONException {
        Log.d(TAG, "s3Hydrate: " + string);
        if (string != null && !string.equals("")) {

            JSONObject json = null;
            json = new JSONObject(string);
            this.rb07 = json.getString("rb07");
            this.rb08 = json.getString("rb08");
            this.rb09 = json.getString("rb09");
            this.rb24 = json.getString("rb24");
            this.rb25 = json.getString("rb25");
            this.pregnum = json.getString("pregnum");


        }
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
        json.put(MWRATable.COLUMN_ROUND, this.round);
        json.put(MWRATable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(MWRATable.COLUMN_SNO, this.sNo);
        //json.put(MWRATable.COLUMN_CHILD_COUNT, this.child_count);
        json.put(MWRATable.COLUMN_REGROUND, this.regRound);
        json.put(MWRATable.COLUMN_HOUSEHOLD_NO, this.hhNo);
        json.put(MWRATable.COLUMN_DEVICEID, this.deviceId);
        //json.put(MWRATable.COLUMN_DEVICETAGID, this.deviceTag);
        //json.put(MWRATable.COLUMN_ISTATUS, this.istatus);
        json.put(MWRATable.COLUMN_APPVERSION, this.appver);
        json.put(MWRATable.COLUMN_SB, new JSONObject(sBtoString()));
        json.put(MWRATable.COLUMN_SC, new JSONObject(sCtoString()));
        json.put(MWRATable.COLUMN_SD, new JSONObject(SDtoString()));
        return json;

    }


    @Bindable
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        notifyChange(BR.expanded);
    }*/

    /*private void CaluculateAge() {


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

            //Log.d(TAG, "CaluculateAge: Y-" + tYear + " M-" + tMonth + " D-" + tDay);
               *//* setH231d(String.valueOf(tDay));
                setH231m(String.valueOf(tMonth));*//*

            setRb05(String.valueOf(tYear));


        } catch (ParseException e) {
            //Log.d(TAG, "CaluculateAge: " + e.getMessage());
            e.printStackTrace();

        }
    }*/

    public static long CalculateAge(String dateOfVisit) {

        long noOfDays = 0;
        String dateofReg = "";


        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            //String dateOfReg = dateOfVisit;

            if (dateOfVisit.length() > 10) {
                dateofReg = dateOfVisit.substring(9, 19);
            } else {
                dateofReg = dateOfVisit;
            }

            // set current Date
            Calendar cur = Calendar.getInstance();
            cur.setTime(cur.getTime());// all done

            // Registration Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(dateofReg));


            // set Date of birth
            long millis = cur.getTimeInMillis() - calendar.getTimeInMillis();
            calendar.setTimeInMillis(millis);


            Calendar c = Calendar.getInstance();

            c.setTimeInMillis(millis);
            long tYear = MILLISECONDS.toDays(millis) / 365;
            long tMonth = (MILLISECONDS.toDays(millis) - (tYear * 365)) / 30;
            long tDay = MILLISECONDS.toDays(millis);

            noOfDays = tDay;


            //Log.d(TAG, "CaluculateAge: Y-" + tYear + " M-" + tMonth + " D-" + tDay);


        } catch (ParseException e) {
            //Log.d(TAG, "CaluculateAge: " + e.getMessage());
            e.printStackTrace();

        }

        return noOfDays;
    }

    /*public String calcEDD() {

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

    }*/
}
