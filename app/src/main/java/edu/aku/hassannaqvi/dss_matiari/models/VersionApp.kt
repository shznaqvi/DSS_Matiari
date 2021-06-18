package edu.aku.hassannaqvi.dss_matiari.models

import android.database.Cursor
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.VersionTable
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject

class VersionApp {
    var versioncode: String = StringUtils.EMPTY
    var versionname: String = StringUtils.EMPTY
    var pathname: String = StringUtils.EMPTY

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): VersionApp {
        versioncode = jsonObject.getString(VersionTable.COLUMN_VERSION_CODE)
        pathname = jsonObject.getString(VersionTable.COLUMN_PATH_NAME)
        versionname = jsonObject.getString(VersionTable.COLUMN_VERSION_NAME)
        return this
    }

    fun hydrate(cursor: Cursor): VersionApp {
        versioncode = cursor.getString(cursor.getColumnIndex(VersionTable.COLUMN_VERSION_CODE))
        pathname = cursor.getString(cursor.getColumnIndex(VersionTable.COLUMN_PATH_NAME))
        versionname = cursor.getString(cursor.getColumnIndex(VersionTable.COLUMN_VERSION_NAME))
        return this
    }


}