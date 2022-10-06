package edu.aku.hassannaqvi.dss_matiari.models

import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MaxHhnoTable
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 * Edited by gul.sanober on 10-06-2022
 */

@Entity(tableName = MaxHhnoTable.TABLE_NAME)
class MaxHhno {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = MaxHhnoTable.COLUMN_ID,
        defaultValue = "0")
    var ID: Long = 0

    @ColumnInfo(
        name = MaxHhnoTable.COLUMN_UC_CODE,
        defaultValue = StringUtils.EMPTY
    )
    var uccode: String? = StringUtils.EMPTY

    @ColumnInfo(
        name = MaxHhnoTable.COLUMN_MAX_HHNO,
        defaultValue = StringUtils.EMPTY
    )
    var maxHhno: String? = StringUtils.EMPTY

    @ColumnInfo(
        name = MaxHhnoTable.COLUMN_VILLAGE_CODE,
        defaultValue = StringUtils.EMPTY
    )
    var villageCode: String? = StringUtils.EMPTY


    constructor() {
        // Default Constructor
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): MaxHhno {
        uccode = jsonObject.getString(MaxHhnoTable.COLUMN_UC_CODE)
        maxHhno = jsonObject.getString(MaxHhnoTable.COLUMN_MAX_HHNO)
        villageCode = jsonObject.getString(MaxHhnoTable.COLUMN_VILLAGE_CODE)

        return this
    }

    fun hydrate(cursor: Cursor): MaxHhno {
        ID = cursor.getLong(cursor.getColumnIndexOrThrow(MaxHhnoTable.COLUMN_ID))

        uccode =
            cursor.getString(cursor.getColumnIndexOrThrow(MaxHhnoTable.COLUMN_UC_CODE))

        maxHhno = cursor.getString(cursor.getColumnIndexOrThrow(MaxHhnoTable.COLUMN_MAX_HHNO))

        villageCode =
            cursor.getString(cursor.getColumnIndexOrThrow(MaxHhnoTable.COLUMN_VILLAGE_CODE))


        return this
    }


}