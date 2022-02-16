package edu.aku.hassannaqvi.dss_matiari.models

import android.database.Cursor
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MaxHhnoTable
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 */
class MaxHhno {
    var ID: Long = 0
    var uccode: String = ""
    var maxHhno: String = ""
    var villageCode: String = ""


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