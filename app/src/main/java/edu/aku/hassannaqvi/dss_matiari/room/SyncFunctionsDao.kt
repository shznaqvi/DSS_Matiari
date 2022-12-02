package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.Households
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import edu.aku.hassannaqvi.dss_matiari.models.Outcome
import org.json.JSONArray
import org.json.JSONException
import java.util.*

//
// Created by gul.sanober on 12/1/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface SyncFunctionsDao {


    // Household Upload Functions

    @Query("SELECT * FROM " + TableContracts.HouseholdTable.TABLE_NAME + " WHERE " + TableContracts.HouseholdTable.COLUMN_SYNCED
            + " is \'\' AND (" + TableContracts.HouseholdTable.COLUMN_ISTATUS  + " = 1 OR "
            + TableContracts.HouseholdTable.COLUMN_VISIT_NO + " > 2) ORDER BY " + TableContracts.HouseholdTable.COLUMN_ID + " ASC")
    fun getUnsyncedHousehols_internal() : List<Households>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedHouseholds() : JSONArray?
    {
        val allForms = getUnsyncedHousehols_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

    }

    // Households

    @Query("SELECT * FROM " + TableContracts.HouseholdTable.TABLE_NAME
            + " WHERE " + TableContracts.HouseholdTable.COLUMN_ID + " LIKE :id ")
    fun updateQueryhhs(id: String?) : Households


    fun updateSyncedhhs(id: String?) : Households
    {
        val synced = updateQueryhhs(id)

        synced.synced = "1"
        synced.syncDate = Date().toString()
        return synced
    }
//************************************************************************************************************

    // MWRA


    @Query("SELECT * FROM " + TableContracts.MWRATable.TABLE_NAME
            + " WHERE " + TableContracts.MWRATable.COLUMN_SYNCED
            + " is \'\' ORDER BY " + TableContracts.MWRATable.COLUMN_ID + " ASC")
    fun getUnsyncedMWRAS_internal() : List<Mwra>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedMwras() : JSONArray?
    {
        val allForms = getUnsyncedMWRAS_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

    }


    @Query("SELECT * FROM " + TableContracts.MWRATable.TABLE_NAME
            + " WHERE " + TableContracts.MWRATable.COLUMN_ID + " LIKE :id ")
    fun updateQueryMWRA(id: String?) : Mwra


    fun updateSyncedMWRA(id: String?) : Mwra
    {
        val synced = updateQueryMWRA(id)

        synced.synced = "1"
        synced.syncDate = Date().toString()
        return synced
    }
//****************************************************************************************************************

    // Outcome

    @Query("SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME
            + " WHERE " + TableContracts.OutcomeTable.COLUMN_SYNCED
            + " is \'\' ORDER BY " + TableContracts.OutcomeTable.COLUMN_ID + " ASC")
    fun getUnsyncedOutcome_internal() : List<Outcome>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedOutcome() : JSONArray?
    {
        val allForms = getUnsyncedOutcome_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

    }


    @Query("SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME
            + " WHERE " + TableContracts.OutcomeTable.COLUMN_ID + " LIKE :id ")
    fun updateQueryOutcome(id: String?) : Outcome


    fun updateSyncedOutcome(id: String?) : Outcome
    {
        val synced = updateQueryOutcome(id)

        synced.synced = "1"
        synced.syncDate = Date().toString()
        return synced
    }





}