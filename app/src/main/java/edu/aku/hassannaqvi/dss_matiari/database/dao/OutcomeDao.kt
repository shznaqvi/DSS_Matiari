package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.Outcome
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew
import org.json.JSONException

//
// Created by gul.sanober on 11/5/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface OutcomeDao {

    @Throws(JSONException::class)
    @Insert
    fun addOutcome(outcome: Outcome): Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOutcome(outcome: Outcome): Int


    @Query("SELECT * FROM outcomes where hdssid like :hdssid and msno like :msno and sno like :sno order by _id ASC ")
    fun getOutcomeByID_internal(hdssid : String, msno: String, sno: String): Outcome?


    @Throws(JSONException::class)
    fun getOutcomeBYID(hdssid: String,  msno: String, sno: String): Outcome? {
        val outcome = getOutcomeByID_internal(hdssid,  msno, sno)
        if (outcome == null) {
            val tempOutcome = Outcome()
            return tempOutcome
        }
        return outcome
    }

    @Query("SELECT * FROM outcomes where _uuid like :uuid and sno like :rb01 and _muid like :muid and round like :fround and regRound like :regRound order by _id ASC ")
    fun getOutcomeFollowupsBySno_internal(
        uuid: String,
        rb01: String,
        muid: String,
        fround: String,
        regRound: String
    ): Outcome?


    @Throws(JSONException::class)
    fun getOutcomeFollowupsBySno(
        uuid: String,
        rb01: String,
        muid: String,
        fround: String
    ): Outcome? {
        val outcome = getOutcomeFollowupsBySno_internal(uuid, rb01, muid, fround, "")
        if (outcome == null) {
            val tempOutcome = Outcome()
            return tempOutcome
        }
        return outcome
    }

    /* NEW STRUCT */

    @Query("SELECT * FROM outcomes WHERE _uuid IN (:uIds)")
    abstract fun getAllUnSyncedDataByUIds(uIds: List<String>): List<Outcome>

    // This query is only used for updating sync list
    // id = rowId
    @Query("SELECT * FROM outcomes WHERE _id = :id")
    fun getDataById(id: Int): Outcome

    // Update sync status as success
    fun updateSyncSuccess(responses: List<SyncModelNew.WebResponse>) {
        if (responses.size > 0 && responses[0].error == 0) {
            val syncedDate: String = DateUtils.getCurrentDateTime()
            val synced = "1"
            for (i in responses.indices) {
                val forms: Outcome = getDataById(responses[i].id)
                forms.syncDate = syncedDate
                forms.synced = synced
                forms.isError = false
                updateOutcome(forms)
            }
        }
    }

    // Update error status while uploading
    fun updateSyncError(list: List<Outcome>) {
        for (i in list.indices) {
            val obj: Outcome = list[i]
            obj.isError = true
            updateOutcome(obj)
        }
    }

}