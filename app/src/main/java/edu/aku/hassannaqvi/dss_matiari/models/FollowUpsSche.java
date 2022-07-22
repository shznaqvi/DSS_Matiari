package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;

import androidx.databinding.Observable;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableFollowUpsSche;

/**
 * Author: Hassan.naqvi
 * Created by: ModelGenerator on 21-12-2021
 */
public class FollowUpsSche implements Observable {
    long id;
    private String ucCode;
    private String villageCode;
    private String hhNo;
    private String hdssid;
    private String fmuid;
    private String ra01; // Date of First Visit
    private String ra08; // Para
    private String ra14; // Head of Household
    private String ra18; // No. of MWRA in the household
    private String fRound;
    private String rb01; // MWRA Sno
    private String rb02; // MWRA Name
    private String rb03; // Husband / Father
    private String rb04; // DOB
    private String rc12; // Gender
    private String rc15; // Pregnancy status current
    private String rb05;  // Age in years
    private String rb07;  // Pregnancy Status
    private String rb06; // Marital Status
    private String memberType; // Member Type (Mother or child)
    private String istatus; // Interview Status
    private String fpDoneDt = "";  // followup-done date


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

    public String getFmuid() {
        return fmuid;
    }

    public void setFmuid(String fmuid) {
        this.fmuid = fmuid;
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

    public String getRa01() {
        return ra01;
    }

    public void setRa01(String ra01) {
        this.ra01 = ra01;
    }

    public String getRa08() {
        return ra08;
    }

    public void setRa08(String ra08) {
        this.ra08 = ra08;
    }

    public String getRa14() {
        return ra14;
    }

    public void setRa14(String ra14) {
        this.ra14 = ra14;
    }

    public String getRa18() {
        return ra18;
    }

    public void setRa18(String ra18) {
        this.ra18 = ra18;
    }

    public String getfRound() {
        return fRound;
    }

    public void setfRound(String fRound) {
        this.fRound = fRound;
    }

    public String getfpDoneDt() {
        return fpDoneDt;
    }

    public void setfpDoneDt(String fpDoneDt) {
        this.fpDoneDt = fpDoneDt;
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

    public String getRc12() {
        return rc12;
    }

    public void setRc12(String rc12) {
        this.rc12 = rc12;
    }

    public String getRc15() {
        return rc15;
    }

    public void setRc15(String rc15) {
        this.rc15 = rc15;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public FollowUpsSche Sync(JSONObject jsonObject) throws JSONException {
        this.ucCode = jsonObject.getString(TableFollowUpsSche.COLUMN_UC_CODE);
        this.villageCode = jsonObject.getString(TableFollowUpsSche.COLUMN_VILLAGE_CODE);
        this.fmuid = jsonObject.getString(TableFollowUpsSche.COLUMN_FMUID);
        this.hhNo = jsonObject.getString(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO);
        this.hdssid = jsonObject.getString(TableFollowUpsSche.COLUMN_HDSSID);
        this.ra01 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA01);
        this.ra08 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA08);
        this.ra14 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA14);
        this.ra18 = jsonObject.getString(TableFollowUpsSche.COLUMN_RA18);
        this.fRound = jsonObject.getString(TableFollowUpsSche.COLUMN_FROUND);
        this.istatus = jsonObject.getString(TableFollowUpsSche.COLUMN_ISTATUS);
        this.rb01 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB01);
        this.rb02 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB02);
        this.rb03 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB03);
        this.rb04 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB04);
        this.rc12 = jsonObject.getString(TableFollowUpsSche.COLUMN_RC12);
        this.rc15 = jsonObject.getString(TableFollowUpsSche.COLUMN_RC15);
        this.rb05 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB05);
        this.rb07 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB07);
        this.rb06 = jsonObject.getString(TableFollowUpsSche.COLUMN_RB06);
        this.memberType = jsonObject.getString(TableFollowUpsSche.COLUMN_MEMBERTYPE);

        return this;
    }

    public FollowUpsSche Hydrate(Cursor cursor) {
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_VILLAGE_CODE));
        this.fmuid = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_FMUID));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_HOUSEHOLD_NO));
        this.hdssid = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_HDSSID));
        this.ra01 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA01));
        this.ra08 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA08));
        this.ra14 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA14));
        this.ra18 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RA18));
        this.fRound = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_FROUND));
        this.fpDoneDt = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_DONE_DATE));
        this.istatus = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_ISTATUS));
        this.rb01 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB01));
        this.rb02 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB02));
        this.rb03 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB03));
        this.rb04 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB04));
        this.rc12 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RC12));
        this.rc15 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RC15));
        this.rb05 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB05));
        this.rb07 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB07));
        this.rb06 = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_RB06));
        this.memberType = cursor.getString(cursor.getColumnIndexOrThrow(TableFollowUpsSche.COLUMN_MEMBERTYPE));


        return this;
    }
}