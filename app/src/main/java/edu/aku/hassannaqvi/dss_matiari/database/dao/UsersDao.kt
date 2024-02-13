/**
 * Created by gul.sanober on 10/06/2022.
 */
package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.core.UserAuth
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase
import edu.aku.hassannaqvi.dss_matiari.models.Users

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE " + TableContracts.UsersTable.COLUMN_DESIGNATION + " like '%Team Leader%' ")
    fun getTeamLeaders() : List<Users>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String?): Users?

    @Query("SELECT * FROM users where username like :username and password like :password order by _id asc "
            /*+ TableContracts.UsersTable.TABLE_NAME +
            " WHERE " + TableContracts.UsersTable.COLUMN_USERNAME + " LIKE :username AND + "
            + TableContracts.UsersTable.COLUMN_PASSWORD + " LIKE :password ORDER BY "
            + TableContracts.UsersTable.COLUMN_ID + " ASC"*/)
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

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<Users>)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<Users>) {
        deleteAll()
        addAllData(list)
    }



}