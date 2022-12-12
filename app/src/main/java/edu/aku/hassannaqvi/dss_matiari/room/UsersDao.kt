/**
 * Created by gul.sanober on 10/06/2022.
 */
package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.Users
import org.json.JSONArray
import org.json.JSONException

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE " + TableContracts.UsersTable.COLUMN_DESIGNATION + " like '%team%' ")
    fun getTeamLeaders() : List<Users>

    @Query("SELECT * FROM " + TableContracts.UsersTable.TABLE_NAME +
            " WHERE " + TableContracts.UsersTable.COLUMN_USERNAME + " LIKE :username AND + "
            + TableContracts.UsersTable.COLUMN_PASSWORD + " LIKE :password ORDER BY "
            + TableContracts.UsersTable.COLUMN_ID + " ASC")
    fun getUserByUsername_internal(username: String, password: String) : Users?

    fun doLogin(username: String, password: String) : Boolean
    {
        val loggedInUser = getUserByUsername_internal(username, password)
        if (loggedInUser != null) {

                return true
            }
        return false
    }
}