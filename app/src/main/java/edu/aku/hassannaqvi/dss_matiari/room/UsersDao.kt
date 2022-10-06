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

    @Throws(JSONException::class)
    fun syncUser(usersList: JSONArray): Int {
        var insertCount = 0
        deleteUsersTable()
        for(i in 0 until usersList.length()) {
            val jsonObjectUser = usersList.optJSONObject(i)

            val user = Users()
            user.sync(jsonObjectUser)

            val rowId = insertUser(user)
            if (rowId != -1L)
                insertCount++
        }
        return insertCount
    }

    @Insert
    fun insertUser(user: Users): Long

    @Query("DELETE FROM " + TableContracts.UsersTable.TABLE_NAME)
    fun deleteUsersTable()

    @Query("SELECT * FROM users WHERE " + TableContracts.UsersTable.COLUMN_DESIGNATION + " like '%team%' ")
    fun getTeamLeaders() : List<Users>
}