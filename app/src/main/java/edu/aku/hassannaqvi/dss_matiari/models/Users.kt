package edu.aku.hassannaqvi.dss_matiari.models

import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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
    var userName: String = StringUtils.EMPTY
    var password: String = StringUtils.EMPTY
    var passwordEnc: String = StringUtils.EMPTY
    var fullname: String = StringUtils.EMPTY
    var enabled: String = StringUtils.EMPTY
    var pwdExpiry: String = StringUtils.EMPTY
    var newUser: String = StringUtils.EMPTY
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
        passwordEnc = jsonObject.getString(UsersTable.COLUMN_PASSWORD_ENC)
        fullname = jsonObject.getString(UsersTable.COLUMN_FULLNAME)
        designation = jsonObject.getString(UsersTable.COLUMN_DESIGNATION)
        enabled = jsonObject.getString(UsersTable.COLUMN_ENABLED)
        pwdExpiry = jsonObject.getString(UsersTable.COLUMN_PWD_EXPIRY)
        newUser = jsonObject.getString(UsersTable.COLUMN_ISNEW_USER)

        return this
    }

    fun hydrate(cursor: Cursor): Users {
        userID = cursor.getLong(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ID))
        userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_USERNAME))
        password = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_PASSWORD))
        passwordEnc = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_PASSWORD_ENC))
        fullname = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_FULLNAME))
        designation = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_DESIGNATION))
        enabled = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ENABLED))
        pwdExpiry = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_PWD_EXPIRY))
        newUser = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ISNEW_USER))
        return this
    }


}