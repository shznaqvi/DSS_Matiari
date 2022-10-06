package edu.aku.hassannaqvi.dss_matiari.models;


import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts;
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableVillage;

@Entity(tableName = TableVillage.TABLE_NAME)
public class Villages {

    private static final String TAG = "Villages_CONTRACT";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TableVillage._ID)
    Long id;

    @ColumnInfo(name = TableVillage.COLUMN_UCNAME, defaultValue = StringUtils.EMPTY)
    String ucname= StringUtils.EMPTY;

    @ColumnInfo(name = TableVillage.COLUMN_VILLAGE_NAME, defaultValue = StringUtils.EMPTY)
    String villagename= StringUtils.EMPTY;

    @ColumnInfo(name = TableVillage.COLUMN_VILLAGE_CODE, defaultValue = StringUtils.EMPTY)
    String villagecode= StringUtils.EMPTY;

    @ColumnInfo(name = TableVillage.COLUMN_UC_CODE, defaultValue = StringUtils.EMPTY)
    String uccode= StringUtils.EMPTY;

    public Villages() {
        // Default Constructor
    }

    public Villages(String villageName, String villageCode)
    {
        villageName = this.villagename;
        villageCode = this.villagecode;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUcname() {
        return ucname;
    }

    public void setUcname(String ucname) {
        this.ucname = ucname;
    }


    public String getVillagename() {
        return villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }


    public String getVillagecode() {
        return villagecode;
    }

    public void setVillagecode(String villagecode) {
        this.villagecode = villagecode;
    }


    public String getUccode() {
        return uccode;
    }

    public void setUccode(String uccode) {
        this.uccode = uccode;
    }




    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(TableVillage._ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(TableVillage.COLUMN_UCNAME, this.ucname == null ? JSONObject.NULL : this.ucname);
        json.put(TableVillage.COLUMN_VILLAGE_NAME, this.villagename == null ? JSONObject.NULL : this.villagename);
        json.put(TableVillage.COLUMN_VILLAGE_CODE, this.villagecode == null ? JSONObject.NULL : this.villagecode);
        json.put(TableVillage.COLUMN_UC_CODE, this.villagecode == null ? JSONObject.NULL : this.villagecode);
        return json;

    }

    public Villages Sync(JSONObject jsonObject) throws JSONException {
        this.ucname = jsonObject.getString(TableVillage.COLUMN_UCNAME);
        this.villagename = jsonObject.getString(TableVillage.COLUMN_VILLAGE_NAME);
        String seemVID = jsonObject.getString(TableVillage.COLUMN_VILLAGE_CODE);
        this.villagecode = seemVID.substring(seemVID.length() - 3);
        this.uccode = "0" + seemVID.charAt(0);
        return this;
    }

    public Villages HydrateUc(Cursor cursor) {
        this.ucname = cursor.getString(cursor.getColumnIndexOrThrow(TableVillage.COLUMN_UCNAME));
        this.villagecode = cursor.getString(cursor.getColumnIndexOrThrow(TableVillage.COLUMN_VILLAGE_CODE));
        this.uccode = cursor.getString(cursor.getColumnIndexOrThrow(TableVillage.COLUMN_UC_CODE));
        return this;
    }

    public Villages HydrateVil(Cursor cursor) {
        this.villagename = cursor.getString(cursor.getColumnIndexOrThrow(TableVillage.COLUMN_VILLAGE_NAME));
        this.villagecode = cursor.getString(cursor.getColumnIndexOrThrow(TableVillage.COLUMN_VILLAGE_CODE));
        return this;
    }
}