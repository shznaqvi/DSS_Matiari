
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.EntryLog
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew
import org.json.JSONException

@Dao
interface EntryLogDao {

    @kotlin.jvm.Throws(JSONException::class)
    @Insert
    fun addEntryLog(entryLog: EntryLog): Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntryLog(entryLog: EntryLog): Int

    @Query("SELECT * FROM EntryLog WHERE ((synced IS '' OR synced IS null) AND (syncDate IS '' OR syncDate IS null)) OR isError IS 1")
    fun getAllUnSyncedData(): List<EntryLog?>?

    // This query is only used for updating sync list
    // id = rowId
    @Query("SELECT * FROM EntryLog WHERE id = :id")
    fun getDataById(id: Int): EntryLog

    // Update sync status as success
    fun updateSyncSuccess(responses: List<SyncModelNew.WebResponse>?) {
        if (responses != null && responses.size > 0 && responses[0].error === 0) {
            val syncedDate: String = DateUtils.getCurrentDateTime()
            val synced = "1"
            for (i in responses.indices) {
                val forms: EntryLog = getDataById(responses[i].getId())
                forms.syncDate = syncedDate
                forms.synced = synced
                forms.isError = false
                updateEntryLog(forms)
            }
        }
    }

    // Update error status while uploading
    fun updateSyncError(list: List<EntryLog>) {
        for (i in list.indices) {
            val obj: EntryLog = list[i]
            obj.isError = true
            updateEntryLog(obj)
        }
    }


}