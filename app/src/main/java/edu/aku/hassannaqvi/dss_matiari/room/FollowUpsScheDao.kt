
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.jvm.Throws

@Dao
interface FollowUpsScheDao {

    @Throws(JSONException ::class)
    fun syncFollowUpsSche(followUpsScheList: JSONArray) : Int {
        var insertCount =0
        deleteFollowupsScheTable()

        for(i in 0 until followUpsScheList.length()) {
            val jsonObjectFollowUpsSche = followUpsScheList.optJSONObject(i)

            val followUpsSche = FollowUpsSche()
            followUpsSche.Sync(jsonObjectFollowUpsSche)

            val rowId = insertFollowupsSche(followUpsSche)

            if(rowId != 1L)
                insertCount++
        }

        return insertCount
    }

    @Insert
    fun insertFollowupsSche(followUpsSche: FollowUpsSche) : Long

    @Query("DELETE FROM " + TableContracts.TableFollowUpsSche.TABLE_NAME)
    fun deleteFollowupsScheTable()




}