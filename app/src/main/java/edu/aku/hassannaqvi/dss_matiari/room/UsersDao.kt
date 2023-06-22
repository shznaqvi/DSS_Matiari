/**
 * Created by gul.sanober on 10/06/2022.
 */
package edu.aku.hassannaqvi.dss_matiari.room

import android.content.ContentValues
import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.core.UserAuth
import edu.aku.hassannaqvi.dss_matiari.models.Users

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE " + TableContracts.UsersTable.COLUMN_DESIGNATION + " like '%team%' ")
    fun getTeamLeaders() : List<Users>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String?): Users?

    @Query("SELECT * FROM " + TableContracts.UsersTable.TABLE_NAME +
            " WHERE " + TableContracts.UsersTable.COLUMN_USERNAME + " LIKE :username AND + "
            + TableContracts.UsersTable.COLUMN_PASSWORD + " LIKE :password ORDER BY "
            + TableContracts.UsersTable.COLUMN_ID + " ASC")
    fun getUserByUsername_internal(username: String, password: String) : Users?

    fun doLogin(username: String, password: String) : Boolean
    {
        val db: DssRoomDatabase = MainApp.appInfo.dbHelper
        val user: Users? = db.usersDao().getUserByUsername(username)
        if (user != null) {
            if (UserAuth.checkPassword(password, user.passwordEnc)) {
                MainApp.user = user
                return true
            }
        }
        return false

    }

    /*fun updatePassword(hashedPassword: String): Int{
        val db: DssRoomDatabase = MainApp.appInfo.dbHelper
        db.usersDao().updateUser()
    }
    */
        /*val db: DssRoomDatabase = MainApp.appInfo.dbHelper
        val values = ContentValues()
        values.put(TableContracts.UsersTable.COLUMN_PASSWORD, hashedPassword)
        values.put(TableContracts.UsersTable.COLUMN_ISNEW_USER, "")
        val selection: String = TableContracts.UsersTable.COLUMN_USERNAME + " =? "
        val selectionArgs = arrayOf<String>(MainApp.user.userName)
        return db.usersDao().updateUser(
            TableContracts.UsersTable.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )*/


}