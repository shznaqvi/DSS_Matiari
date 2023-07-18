package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;

import androidx.databinding.Observable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableFollowUpsSche;
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew;

/**
 * Author: Hassan.naqvi
 * Created by: ModelGenerator on 21-12-2021
 * Edited by: gul.sanober on 06-10-2022

 */

@Entity(tableName = TableFollowUpsSche.TABLE_NAME)
public class FollowUpsSche implements Observable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TableFollowUpsSche.COLUMN_ID)
    long id;

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @SerializedName("hhno")
    @ColumnInfo(name = TableFollowUpsSche.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_HDSSID)
    private String hdssid = StringUtils.EMPTY;

    @SerializedName("_muid")
    @ColumnInfo(name = TableFollowUpsSche.COLUMN_MUID)
    private String muid = StringUtils.EMPTY;

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RA01)
    private SyncModelNew.ResponseDate ra01; // Date of First Visit

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RA08)
    private String ra08 = StringUtils.EMPTY; // Mohalla

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RA12)
    private String ra12 = StringUtils.EMPTY; // Head of Household

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RA18)
    private String ra18 = StringUtils.EMPTY; // No. of MWRA in the household

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_FROUND)
    private String fRound = StringUtils.EMPTY;

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB01)
    private String rb01 = StringUtils.EMPTY; // MWRA Sno

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB02)
    private String rb02 = StringUtils.EMPTY; // MWRA Name

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB03)
    private String rb03 = StringUtils.EMPTY; // Husband / Father

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB04)
    private String rb04 = StringUtils.EMPTY; // DOB

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RC04)
    private String rc04 = StringUtils.EMPTY; // Gender Will be rc04 in new View


    @ColumnInfo(name = TableFollowUpsSche.COLUMN_MSNO)
    private String msno = StringUtils.EMPTY; // Gender Will be rc04 in new View

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_CHILD_COUNT)
    private String child_count = StringUtils.EMPTY; // Gender Will be rc04 in new View

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB05)
    private String rb05 = StringUtils.EMPTY;  // Age in years

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB07)
    private String rb07 = StringUtils.EMPTY;  // Previous pregnancy status will be is prePregnancy

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB06)
    private String rb06 = StringUtils.EMPTY; // Marital Status

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_MEMBERTYPE)
    private String memberType = StringUtils.EMPTY; // Member Type (Mother or child)

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_ISTATUS)
    private String istatus = StringUtils.EMPTY; // Interview Status

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_DONE_DATE)
    private String fpDoneDt = StringUtils.EMPTY ;  // followup-done date

    @SerializedName("pregnum")
    @ColumnInfo(name = TableFollowUpsSche.COLUMN_PREG_COUNT)
    private String pregCount = StringUtils.EMPTY ;  // Pregnancies Count

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB22)
    private String rb22 = StringUtils.EMPTY ;  // Pregnancies Count

    @ColumnInfo(name = TableFollowUpsSche.COLUMN_RB23)
    private String rb23 = StringUtils.EMPTY ;  // Pregnancies Count

    @SerializedName("reg_date")
    private String reg_date = StringUtils.EMPTY;


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
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

    public String getHdssid() {
        return hdssid;
    }

    public void setHdssid(String hdssid) {
        this.hdssid = hdssid;
    }

    public SyncModelNew.ResponseDate getRa01() {
        return ra01;
    }

    public void setRa01(SyncModelNew.ResponseDate ra01) {
        this.ra01 = ra01;
    }

    public String getRa08() {
        return ra08;
    }

    public void setRa08(String ra08) {
        this.ra08 = ra08;
    }

    public String getRa12() {
        return ra12;
    }

    public void setRa12(String ra12) {
        this.ra12 = ra12;
    }

    public String getRa18() {
        return ra18;
    }

    public void setRa18(String ra18) {
        this.ra18 = ra18;
    }

    public String getfpDoneDt() {
        return fpDoneDt;
    }

    public String setfpDoneDt(String fpDoneDt) {
        this.fpDoneDt = fpDoneDt;
        return fpDoneDt;
    }

    public String getiStatus() {
        return istatus;
    }

    public void setiStatus(String istatus) {
        this.istatus = istatus;
    }

    public String getRb01() {
        return rb01;
    }

    public void setRb01(String rb01) {
        this.rb01 = rb01;
    }

    public String getRb02() {
        return rb02;
    }

    public void setRb02(String rb02) {
        this.rb02 = rb02;
    }

    public String getRb07() {
        return rb07;
    }

    public void setRb07(String rb07) {
        this.rb07 = rb07;
    }

    public String getRb06() {
        return rb06;
    }

    public void setRb06(String rb06) {
        this.rb06 = rb06;
    }


    public String getRb03() {
        return rb03;
    }

    public void setRb03(String rb03) {
        this.rb03 = rb03;
    }

    public String getRb05() {
        return rb05;
    }

    public void setRb05(String rb05) {
        this.rb05 = rb05;
    }

    public String getRb04() {
        return rb04;
    }

    public void setRb04(String rb04) {
        this.rb04 = rb04;
    }

    public String getRc04() {
        return rc04;
    }

    public void setRc04(String rc04) {
        this.rc04 = rc04;
    }

    public String getMsno() {
        return msno;
    }

    public void setMsno(String msno) {
        this.msno = msno;
    }

    public String getChild_count() {
        return child_count;
    }

    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }


    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }

    public String getFpDoneDt() {
        return fpDoneDt;
    }

    public void setFpDoneDt(String fpDoneDt) {
        this.fpDoneDt = fpDoneDt;
    }

    public String getFRound() {
        return fRound;
    }

    public void setFRound(String fRound) {
        this.fRound = fRound;
    }

    public String getPregCount() {
        return pregCount;
    }

    public void setPregCount(String pregCount) {
        this.pregCount = pregCount;
    }

    public String getRb22() {
        return rb22;
    }

    public void setRb22(String rb22) {
        this.rb22 = rb22;
    }

    public String getRb23() {
        return rb23;
    }

    public void setRb23(String rb23) {
        this.rb23 = rb23;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public FollowUpsSche Sync(JSONObject jsonObject) throws JSONException {
        this.ucCode = jsonObject.getString(TableFollowUpsSche.COLUMN_UC_CODE);
        this.villageCode = jsonObject.getString(TableFollowUpsSche.COLUMN_VILLAGE_CODE);
        this.muid = jsonObject.getString(TableFollowUpsSche.COLUMN_MUID);
        this.hhNo = jsonObject.getString(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO);
        this.hdssid = jsonObject.getString(TableFollowUpsSche.COLUMN_HDSSID);
        //this.ra01 = jsonObject.getJSONObject(TableFollowUpsSche.COLUMN_RA01);
        this.ra08 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA08);
        this.ra12 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA12);
        this.ra18 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA18);
        this.fRound = jsonObject.getString(TableFollowUpsSche.COLUMN_FROUND);
        this.istatus = jsonObject.getString(TableFollowUpsSche.COLUMN_ISTATUS);
        this.rb01 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB01);
        this.rb02 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB02);
        this.rb03 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB03);
        this.rb04 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB04);
        this.rc04 = jsonObject.getString(TableFollowUpsSche.COLUMN_RC04);
        this.msno = jsonObject.getString(TableFollowUpsSche.COLUMN_MSNO);
        this.child_count = jsonObject.getString(TableFollowUpsSche.COLUMN_CHILD_COUNT);
        this.rb05 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB05);
        this.rb07 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB07);
        this.rb06 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB06);
        this.memberType = jsonObject.getString(TableFollowUpsSche.COLUMN_MEMBERTYPE);
        this.pregCount = jsonObject.getString(TableFollowUpsSche.COLUMN_PREG_COUNT);
        this.rb22 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB22);
        this.rb23 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB23);
        this.reg_date = jsonObject.getString(TableFollowUpsSche.COLUMN_REG_DATE);


        return this;
    }

    public FollowUpsSche Hydrate(Cursor cursor) {
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_VILLAGE_CODE));
        this.muid = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_MUID));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO));
        this.hdssid = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_HDSSID));
        //this.ra01 = cursor.get(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA01));
        this.ra08 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA08));
        this.ra12 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA12));
        this.ra18 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA18));
        this.fRound = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_FROUND));
        this.fpDoneDt = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_DONE_DATE));
        this.istatus = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_ISTATUS));
        this.rb01 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB01));
        this.rb02 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB02));
        this.rb03 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB03));
        this.rb04 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB04));
        this.rc04 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RC04));
        this.msno = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_MSNO));
        this.child_count = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_CHILD_COUNT));
        this.rb05 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB05));
        this.rb07 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB07));
        this.rb06 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB06));
        this.memberType = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_MEMBERTYPE));
        this.pregCount = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_PREG_COUNT));
        this.rb22 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB22));
        this.rb23 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB23));
        this.reg_date = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_REG_DATE));

        return this;
    }
}