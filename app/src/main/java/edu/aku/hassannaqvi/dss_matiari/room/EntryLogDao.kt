
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableHHS
import edu.aku.hassannaqvi.dss_matiari.models.EntryLog
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche
import edu.aku.hassannaqvi.dss_matiari.models.Hhs
import edu.aku.hassannaqvi.dss_matiari.models.Households
import org.json.JSONArray
import org.json.JSONException
import kotlin.jvm.Throws

@Dao
interface EntryLogDao {

    @kotlin.jvm.Throws(JSONException::class)
    @Insert
    fun addEntryLog(entryLog: EntryLog): Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntryLog(entryLog: EntryLog): Int



}