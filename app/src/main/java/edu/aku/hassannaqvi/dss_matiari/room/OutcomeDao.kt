package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import edu.aku.hassannaqvi.dss_matiari.models.Outcome
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew
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

    /*@Query("SELECT "
            + "MAX(" + TableContracts.OutcomeTable.COLUMN_SNO + ") AS " + TableContracts.OutcomeTable.COLUMN_SNO +
            " FROM " + TableContracts.OutcomeTable.TABLE_NAME +
            " WHERE " + TableContracts.OutcomeTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + TableContracts.OutcomeTable.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " +
            TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo AND " +
            TableContracts.OutcomeTable.COLUMN_MSNO + " LIKE :msno" +
            " GROUP BY " + TableContracts.OutcomeTable.COLUMN_HOUSEHOLD_NO )
    fun getMaxChildredBYMother(ucCode : String, vCode : String, hhNo : String, msno: String) : Int
*/


    @Query(
        "SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME + " WHERE hdssid LIKE :hdssid AND "
                + TableContracts.OutcomeTable.COLUMN_MSNO + " LIKE :msno AND "
                + TableContracts.OutcomeTable.COLUMN_SNO + " LIKE :sno ORDER BY "
                + TableContracts.OutcomeTable.COLUMN_ID + " ASC"
    )
    fun getOutcomeByID_internal(hdssid : String, msno: String, sno: String): Outcome?


    @Throws(JSONException::class)
    fun getOutcomeBYID(hdssid: String,  msno: String, sno: String): Outcome? {
        val outcome = getOutcomeByID_internal(hdssid,  msno, sno)
        if (outcome == null) {
            val tempOutcome = Outcome()
            return tempOutcome
        }/*else{
            outcome.sEHydrate(outcome.se)
        }*/
        return outcome
    }

    @Query(
        "SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME + " WHERE "
                + TableContracts.OutcomeTable.COLUMN_UUID + " LIKE :uuid AND "
                + TableContracts.OutcomeTable.COLUMN_SNO + " LIKE :rb01 AND "
                + TableContracts.OutcomeTable.COLUMN_MUID + " LIKE :muid AND "
                + TableContracts.OutcomeTable.COLUMN_ROUND + " LIKE :fround AND " +
                TableContracts.OutcomeTable.COLUMN_REGROUND + " LIKE :regRound ORDER BY "
                + TableContracts.OutcomeTable.COLUMN_ID + " ASC"
    )
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
        } /*else {
            outcome.sEHydrate(outcome.se)

        }*/
        return outcome
    }

    /* NEW STRUCT */

    @Query("SELECT * FROM outcomes WHERE _uuid IN (:uIds)")
    abstract fun getAllUnSyncedDataByUIds(uIds: List<String>): MutableList<Outcome>

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