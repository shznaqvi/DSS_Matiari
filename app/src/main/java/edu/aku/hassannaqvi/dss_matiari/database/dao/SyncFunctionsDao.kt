package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase
import edu.aku.hassannaqvi.dss_matiari.models.*
import org.json.JSONArray
import org.json.JSONException

//
// Created by gul.sanober on 12/1/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface SyncFunctionsDao {


    // Household Upload Functions

    @Query("SELECT * FROM hhs WHERE (synced is \'\' OR synced is NULL) AND (istatus = 1 OR visitNo > 2) ORDER BY _id ASC" )
    fun getUnsyncedHousehols_internal() : List<Households>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedHouseholds() : JSONArray?
    {
        val allForms = getUnsyncedHousehols_internal()

        val jsonArray = JSONArray()
        for (i in allForms) {

            jsonArray.put(i)
        }

        return jsonArray

    }

//************************************************************************************************************

    // MWRA

    @Query("SELECT * FROM MWRAs WHERE (synced is '' OR synced is NULL) AND (istatus != 4) ORDER BY _id ASC ")
    fun getUnsyncedMWRAS_internal() : List<Mwra>

    fun getUnsycedAdjustedMWRAS_internal() : List<Mwra> {
        val hhsSync = getUnsyncedHousehols_internal()
        val allMwras = getUnsyncedMWRAS_internal()
        val toSyncMwras = arrayListOf<Mwra>()
        hhsSync.forEach { hhs  ->
            val mwras = allMwras.filter { it.hdssId == hhs.hdssId && it.uuid == hhs.uid}
            toSyncMwras.addAll(mwras)
        }
        return toSyncMwras
    }

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedMwras() : JSONArray?
    {
        val allForms = getUnsycedAdjustedMWRAS_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            jsonArray.put(i)

        }

        return jsonArray

    }

//****************************************************************************************************************

    // Outcome

    @Query("SELECT * FROM outcomes WHERE synced is '' OR synced is NULL ORDER BY _id ASC")
    fun getUnsyncedOutcome_internal() : List<Outcome>

    fun getUnsycedAdjustedOutcomes_internal() : List<Outcome> {
        val mwras = getUnsyncedMWRAS_internal()
        val allOutcomes = getUnsyncedOutcome_internal()
        val toSyncOutcomes = arrayListOf<Outcome>()
        mwras.forEach { mwra  ->
            val mwraOutcomes = allOutcomes.filter { it.hdssId == mwra.hdssId && it.msno == mwra.sNo}
            toSyncOutcomes.addAll(mwraOutcomes)
        }
        return toSyncOutcomes
    }

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedOutcome() : JSONArray?
    {
        val allForms = getUnsycedAdjustedOutcomes_internal()
        val jsonArray = JSONArray()
        for (i in allForms)
        {

            jsonArray.put(i)

        }

        return jsonArray

    }
    /**************** EntryLog********************/

    @Query("SELECT * FROM " + TableContracts.EntryLogTable.TABLE_NAME + " WHERE "
            + TableContracts.EntryLogTable.COLUMN_SYNCED
            + " is \'\' OR synced is NULL ORDER BY  id ASC")
    fun getUnsyncedEntryLog_internal() : List<EntryLog>

    @kotlin.jvm.Throws(JSONException :: class)
    fun getUnsyncedEntryLog() : JSONArray?
    {
        val allForms = getUnsyncedEntryLog_internal()

        val jsonArray = JSONArray()
        for (i in allForms)
        {

            i.Hydrate(i)
            jsonArray.put(i.toJSONObject())

        }

        return jsonArray

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

    @Update(onConflict =  OnConflictStrategy.REPLACE)
    fun updateUser(user: Users): Int

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



    // HHS

    @Throws(JSONException ::class)
    fun synchhs_view(hhsList: JSONArray) : Int {
        var insertCount =0
        deleteHhsTable()

        for(i in 0 until hhsList.length()) {
            val jsonObject = hhsList.getJSONObject(i)

            val obj = Hhs()
            obj.Sync(jsonObject)

            val rowId = insertHhsTable(obj)

            if(rowId != 1L)
                insertCount++
        }

        return insertCount
    }

    @Insert
    fun insertHhsTable(hhs: Hhs) : Long


    @Query("DELETE FROM " + TableContracts.TableHHS.TABLE_NAME)
    fun deleteHhsTable()


    @RawQuery
    fun getUnsyncedDataUIds(query: SupportSQLiteQuery): List<String>


}