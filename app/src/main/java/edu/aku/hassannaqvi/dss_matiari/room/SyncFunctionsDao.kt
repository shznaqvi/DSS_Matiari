package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.*
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

            i.Hydrate(i)
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
        DssRoomDatabase.dbInstance?.householdsDao()?.updateHousehold(synced)

        return synced
    }
//************************************************************************************************************

    // MWRA


    @Query("SELECT * FROM mwras, hhs WHERE mwras.hdssid LIKE hhs.hdssid AND mwras.synced is \'\' " +
            "AND hhs.istatus = 1 ORDER BY mwras._id ASC ")
    fun getUnsyncedMWRAS_internal() : List<Mwra>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedMwras() : JSONArray?
    {
        val allForms = getUnsyncedMWRAS_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            i.Hydrate(i)
            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

    }


    @Query("SELECT * FROM " + TableContracts.MWRATable.TABLE_NAME
            + " WHERE " + TableContracts.MWRATable.COLUMN_ID + " LIKE :id ")
    fun updateQueryMWRA(id: String?) : Mwra


    fun updateSyncedMWRAs(id: String?) : Mwra
    {
        val synced = updateQueryMWRA(id)

        synced.synced = "1"
        synced.syncDate = Date().toString()
        DssRoomDatabase.dbInstance?.mwraDao()?.updateMwra(synced)
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

            i.Hydrate(i)
            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

    }


    @Query("SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME
            + " WHERE " + TableContracts.OutcomeTable.COLUMN_ID + " LIKE :id ")
    fun updateQueryOutcome(id: String?) : Outcome


    fun updateSyncedoutcomes(id: String?) : Outcome
    {
        val synced = updateQueryOutcome(id)

        synced.synced = "1"
        synced.syncDate = Date().toString()
        DssRoomDatabase.dbInstance?.OutcomeDao()?.updateOutcome(synced)
        return synced
    }







    /******************* DOWNLOAD DATA FUNCTIONS******************************************* */

    @Throws(JSONException ::class)
    fun syncvillages(villagesList: JSONArray) : Int {
        var insertCount = 0
        deleteVillages()
        for(i in 0 until villagesList.length()) {
            val jsonObjectUser = villagesList.getJSONObject(i)

            var village = Villages()
            village.id = i.toLong()
            village = village.Sync(jsonObjectUser)

            val rowId = insertVillages(village)
            if (rowId != -1L)
                insertCount++
        }
        return insertCount
    }

    @Insert
    fun insertVillages(village: Villages) : Long

    @Query("DELETE FROM " + TableContracts.TableVillage.TABLE_NAME)
    fun deleteVillages()

    //@Query("DELETE FROM " + TableContracts.TableVillage.TABLE_NAME)
    fun deleteVillagesTable() {
        DssRoomDatabase.dbInstance?.VillagesDao()?.let { villageDao ->
            val villagesList = villageDao.getAllVillages()
            villagesList.forEach {
                villageDao.deleteVillage(it)
            }
            villageDao.deleteVillagesTable()
        }
    }



    // Users

    @Throws(JSONException::class)
    fun syncusers(usersList: JSONArray): Int {
        var insertCount = 0
        deleteUsersTable()
        for(i in 0 until usersList.length()) {
            val jsonObjectUser = usersList.getJSONObject(i)

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

    // Followupsche

    @Throws(JSONException ::class)
    fun synchhfuplist_view(followUpsScheList: JSONArray) : Int {
        var insertCount =0
        deleteFollowupsScheTable()

        for(i in 0 until followUpsScheList.length()) {
            val jsonObjectFollowUpsSche = followUpsScheList.getJSONObject(i)

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

    // Sync Max Household

    @Throws(JSONException::class)
    fun syncmaxhhno(maxhhNoList : JSONArray) : Int{
        var insertCount =0
        deleteMaxHHNoTable()

        for (i in 0 until maxhhNoList.length())
        {
            val jsonObjectMaxHhno = maxhhNoList.optJSONObject(i)

            val maxHhno = MaxHhno()
            maxHhno.sync(jsonObjectMaxHhno)

            val rowId = insertMaxHHNo(maxHhno)
            if(rowId != -1L)
                insertCount++
        }


        return insertCount
    }

    @Insert
    fun insertMaxHHNo(maxHhno: MaxHhno) : Long

    @Query("DELETE FROM " + TableContracts.MaxHhnoTable.TABLE_NAME)
    fun deleteMaxHHNoTable()






}