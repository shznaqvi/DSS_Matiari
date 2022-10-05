package edu.aku.hassannaqvi.dss_matiari.models

import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.UsersTable
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 */
@Entity(tableName = UsersTable.TABLE_NAME)
class Users {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = UsersTable.COLUMN_ID,
        defaultValue = "0"
    )
    var userID: Long = 0

    @ColumnInfo(
        name = UsersTable.COLUMN_USERNAME,
        defaultValue = StringUtils.EMPTY
    )
    var userName: String? = StringUtils.EMPTY

    @ColumnInfo(
        name = UsersTable.COLUMN_PASSWORD,
        defaultValue = StringUtils.EMPTY
    )
    var password: String? = StringUtils.EMPTY

    @ColumnInfo(
        name = UsersTable.COLUMN_FULLNAME,
        defaultValue = StringUtils.EMPTY
    )
    var fullname: String? = StringUtils.EMPTY

    @ColumnInfo(
        name = UsersTable.COLUMN_DESIGNATION,
        defaultValue = StringUtils.EMPTY
    )
    var designation: String = StringUtils.EMPTY

    constructor() {
        // Default Constructor
    }

    constructor(username: String, fullname: String) {
        userName = username
        this.fullname = fullname
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Users {
        userName = jsonObject.getString(UsersTable.COLUMN_USERNAME)
        password = jsonObject.getString(UsersTable.COLUMN_PASSWORD)
        fullname = jsonObject.getString(UsersTable.COLUMN_FULLNAME)
        designation = jsonObject.getString(UsersTable.COLUMN_DESIGNATION)
        return this
    }

    fun hydrate(cursor: Cursor): Users {
        userID = cursor.getLong(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ID))
        userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_USERNAME))
        password = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_PASSWORD))
        fullname = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_FULLNAME))
        designation = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_DESIGNATION))
        return this
    }


}