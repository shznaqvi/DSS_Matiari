package edu.aku.hassannaqvi.dss_matiari.models;

import android.database.Cursor;

import androidx.databinding.Observable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableHHS;

/**
 * Author: gul.sanober
 * Created on: 04-05-2023

 */

@Entity(tableName = TableHHS.TABLE_NAME)
public class Hhs implements Observable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TableHHS.COLUMN_ID)
    long id;

    @ColumnInfo(name = TableHHS.COLUMN_UC_CODE)
    private String ucCode = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_VILLAGE_CODE)
    private String villageCode = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_HOUSEHOLD_NO)
    private String hhNo = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_HDSSID)
    private String hdssid = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA01)
    private SyncModelNew.ResponseDate ra01; // Date of First Visit

    @ColumnInfo(name = TableHHS.COLUMN_RA08)
    private String ra08 = StringUtils.EMPTY; // Mohalla

    @ColumnInfo(name = TableHHS.COLUMN_RA12)
    private String ra12 = StringUtils.EMPTY; // Head of Household

    @ColumnInfo(name = TableHHS.COLUMN_RA18)
    private String ra18 = StringUtils.EMPTY; // No. of MWRA in the household

    @ColumnInfo(name = TableHHS.COLUMN_ROUND)
    private String round = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_A1)
    private String ra17_a1 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_A2)
    private String ra17_a2 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_A3)
    private String ra17_a3 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_B1)
    private String ra17_b1 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_B2)
    private String ra17_b2 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_B3)
    private String ra17_b3 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_C1)
    private String ra17_c1 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_C2)
    private String ra17_c2 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_C3)
    private String ra17_c3 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_D1)
    private String ra17_d1 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_D2)
    private String ra17_d2 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA17_D3)
    private String ra17_d3 = StringUtils.EMPTY;

    @ColumnInfo(name = TableHHS.COLUMN_RA05)
    private String ra05 = StringUtils.EMPTY;



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

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getRa17_a1() {
        return ra17_a1;
    }

    public void setRa17_a1(String ra17_a1) {
        this.ra17_a1 = ra17_a1;
    }

    public String getRa17_a2() {
        return ra17_a2;
    }

    public void setRa17_a2(String ra17_a2) {
        this.ra17_a2 = ra17_a2;
    }

    public String getRa17_a3() {
        return ra17_a3;
    }

    public void setRa17_a3(String ra17_a3) {
        this.ra17_a3 = ra17_a3;
    }

    public String getRa17_b1() {
        return ra17_b1;
    }

    public void setRa17_b1(String ra17_b1) {
        this.ra17_b1 = ra17_b1;
    }

    public String getRa17_b2() {
        return ra17_b2;
    }

    public void setRa17_b2(String ra17_b2) {
        this.ra17_b2 = ra17_b2;
    }

    public String getRa17_b3() {
        return ra17_b3;
    }

    public void setRa17_b3(String ra17_b3) {
        this.ra17_b3 = ra17_b3;
    }

    public String getRa17_c1() {
        return ra17_c1;
    }

    public void setRa17_c1(String ra17_c1) {
        this.ra17_c1 = ra17_c1;
    }

    public String getRa17_c2() {
        return ra17_c2;
    }

    public void setRa17_c2(String ra17_c2) {
        this.ra17_c2 = ra17_c2;
    }

    public String getRa17_c3() {
        return ra17_c3;
    }

    public void setRa17_c3(String ra17_c3) {
        this.ra17_c3 = ra17_c3;
    }

    public String getRa17_d1() {
        return ra17_d1;
    }

    public void setRa17_d1(String ra17_d1) {
        this.ra17_d1 = ra17_d1;
    }

    public String getRa17_d2() {
        return ra17_d2;
    }

    public void setRa17_d2(String ra17_d2) {
        this.ra17_d2 = ra17_d2;
    }

    public String getRa17_d3() {
        return ra17_d3;
    }

    public void setRa17_d3(String ra17_d3) {
        this.ra17_d3 = ra17_d3;
    }

    public String getRa05() {
        return ra05;
    }

    public void setRa05(String ra05) {
        this.ra05 = ra05;
    }

    public Hhs Sync(JSONObject jsonObject) throws JSONException {
        this.ucCode = jsonObject.getString(TableHHS.COLUMN_UC_CODE);
        this.villageCode = jsonObject.getString(TableHHS.COLUMN_VILLAGE_CODE);
        this.hhNo = jsonObject.getString(TableHHS.COLUMN_HOUSEHOLD_NO);
        this.hdssid = jsonObject.getString(TableHHS.COLUMN_HDSSID);
        //this.ra01 = jsonObject.getString(TableHHS.COLUMN_RA01);
        this.ra08 = jsonObject.getString(TableHHS.COLUMN_RA08);
        this.ra12 = jsonObject.getString(TableHHS.COLUMN_RA12);
        this.ra18 = jsonObject.getString(TableHHS.COLUMN_RA18);
        this.ra05 = jsonObject.getString(TableHHS.COLUMN_RA05);
        this.round = jsonObject.getString(TableHHS.COLUMN_ROUND);
        this.ra17_a1 = jsonObject.getString(TableHHS.COLUMN_RA17_A1);
        this.ra17_a2 = jsonObject.getString(TableHHS.COLUMN_RA17_A2);
        this.ra17_a3 = jsonObject.getString(TableHHS.COLUMN_RA17_A3);
        this.ra17_b1 = jsonObject.getString(TableHHS.COLUMN_RA17_B1);
        this.ra17_b2 = jsonObject.getString(TableHHS.COLUMN_RA17_B2);
        this.ra17_b3 = jsonObject.getString(TableHHS.COLUMN_RA17_B3);
        this.ra17_c1 = jsonObject.getString(TableHHS.COLUMN_RA17_C1);
        this.ra17_c2 = jsonObject.getString(TableHHS.COLUMN_RA17_C2);
        this.ra17_c3 = jsonObject.getString(TableHHS.COLUMN_RA17_C3);
        this.ra17_d1 = jsonObject.getString(TableHHS.COLUMN_RA17_D1);
        this.ra17_d2 = jsonObject.getString(TableHHS.COLUMN_RA17_D2);
        this.ra17_d3 = jsonObject.getString(TableHHS.COLUMN_RA17_D3);


        return this;
    }

    public Hhs Hydrate(Cursor cursor) {
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_VILLAGE_CODE));
        this.hhNo = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_HOUSEHOLD_NO));
        this.hdssid = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_HDSSID));
        //this.ra01 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA01));
        this.ra08 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA08));
        this.ra12 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA12));
        this.ra18 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA18));
        this.ra05 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA05));
        this.round = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_ROUND));
        this.ra17_a1 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_A1));
        this.ra17_a2 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_A2));
        this.ra17_a3 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_A3));
        this.ra17_b1 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_B1));
        this.ra17_b2 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_B2));
        this.ra17_b3 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_B3));
        this.ra17_c1 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_C1));
        this.ra17_c2 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_C2));
        this.ra17_c3 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_C3));
        this.ra17_d1 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_D1));
        this.ra17_d2 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_D2));
        this.ra17_d3 = cursor.getString(cursor.getColumnIndexOrThrow(TableHHS.COLUMN_RA17_D3));

        return this;
    }
}