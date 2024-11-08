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

    /**************** Household ********************/

    @Query("SELECT * FROM hhs WHERE (synced is \'\' OR synced is NULL) AND (istatus = 1 OR visitNo > 2) ORDER BY _id ASC")
    fun getUnsyncedHousehols(): List<Households>

    /**************** MWRA ********************/

    @Query("SELECT * FROM MWRAs WHERE (synced is '' OR synced is NULL) AND (istatus != 4 and istatus != '') ORDER BY _id ASC ")
    fun getUnsyncedMWRAS_internal(): List<Mwra>

    /*@Query("SELECT child.* FROM MWRAs child LEFT JOIN hhs parent ON child._uuid = parent._uid WHERE ((child._uuid IN (:uuid)) OR (child.synced = '' AND parent.synced != '')) AND child.istatus = 1 OR parent.visitNo > 2 OR child.isError IS 1 ORDER BY _id ASC")
    fun getAllUnSyncedMWRAsByUIds(uuid: List<String?>?): List<Mwra?>?*/

    @Query("SELECT child.* FROM MWRAs child LEFT JOIN hhs parent ON child._uuid = parent._uid WHERE ((child._uuid IN (:uuid) OR (child.synced = '' AND parent.synced != '')) AND child.istatus != 4 AND child.istatus != '') OR child.isError IS 1 ORDER BY _id ASC")
    fun getAllUnSyncedMWRAsByUIds(uuid: List<String?>?): List<Mwra>

    @Query("SELECT child.* FROM outcomes child LEFT JOIN hhs parent ON child._uuid = parent._uid WHERE ((child._uuid IN (:uuid)) OR (child.synced = '' AND parent.synced != '')) AND child.istatus = 1 OR parent.visitNo > 2 OR child.isError IS 1 ORDER BY _id ASC")
    fun getAllUnSyncedOutcomesByUIds(uuid: List<String?>?): List<Mwra?>?

    fun getUnsycedMWRAS(): List<Mwra> {
        val hhsSync = getUnsyncedHousehols()
        //        val allMwras = getUnsyncedMWRAS_internal()
        val uidList = hhsSync.map { it.uid }
        val allMwras = getAllUnSyncedMWRAsByUIds(uidList)
        /*val toSyncMwras = arrayListOf<Mwra>()
        hhsSync.forEach { hhs ->
            val mwras = allMwras.filter { (it.hdssId == hhs.hdssId && it.uuid == hhs.uid) || (it.synced.isEmpty()) }
            toSyncMwras.addAll(mwras)
        }
        return toSyncMwras*/
        return allMwras
    }

    /**************** Outcome ********************/

    @Query("SELECT * FROM outcomes WHERE synced is '' OR synced is NULL ORDER BY _id ASC")
    fun getUnsyncedOutcome_internal(): List<Outcome>

   /* fun getUnsycedOutcomes(): List<Outcome> {
        val mwras = getUnsyncedMWRAS_internal()
        val allOutcomes = getUnsyncedOutcome_internal()
        val toSyncOutcomes = arrayListOf<Outcome>()
        mwras.forEach { mwra ->
            val mwraOutcomes =
                allOutcomes.filter { it.hdssId == mwra.hdssId && it.msno == mwra.sNo }
            toSyncOutcomes.addAll(mwraOutcomes)
        }
        return toSyncOutcomes
    }*/

    fun getUnsycedOutcomes(): List<Outcome> {
        val hhsSync = getUnsyncedHousehols()
        val allOutcomes = getUnsyncedOutcome_internal()
        val toSyncOutcomes = arrayListOf<Outcome>()
        hhsSync.forEach { hhSync ->
            val mwraOutcomes =
                allOutcomes.filter { it.hdssId == hhSync.hdssId}
            toSyncOutcomes.addAll(mwraOutcomes)
        }
        return toSyncOutcomes
    }

    /**************** AbortionCL ********************/

    @Query("SELECT * FROM abortionCL WHERE synced is '' OR synced is NULL ORDER BY id ASC")
    fun getUnsyncedAbortion_internal(): List<AbortionCL>

    fun getUnsycedAbortion(): List<AbortionCL> {
        val mwras = getUnsyncedMWRAS_internal()
        val allAbortionCL = getUnsyncedAbortion_internal()
        val toSyncAbortionCL = arrayListOf<AbortionCL>()
        mwras.forEach { abortion ->
            val abortionCL = allAbortionCL.filter { it.hdssId == abortion.hdssId }
            toSyncAbortionCL.addAll(abortionCL)
        }
        return toSyncAbortionCL
    }

    /**************** EntryLog ********************/

    @Query(
        "SELECT * FROM " + TableContracts.EntryLogTable.TABLE_NAME + " WHERE "
                + TableContracts.EntryLogTable.COLUMN_SYNCED
                + " is \'\' OR synced is NULL ORDER BY  id ASC"
    )
    fun getUnsyncedEntryLog(): List<EntryLog>

    /******************* DOWNLOAD DATA FUNCTIONS******************************************* */

    @Throws(JSONException::class)
    fun syncvillages(villagesList: JSONArray): Int {
        var insertCount = 0
        deleteVillages()
        for (i in 0 until villagesList.length()) {
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
    fun insertVillages(village: Villages): Long

    @Query("DELETE FROM " + TableContracts.TableVillage.TABLE_NAME)
    fun deleteVillages()

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
        for (i in 0 until usersList.length()) {
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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: Users): Int

    // Followupsche

    @Throws(JSONException::class)
    fun synchhfuplist_view(followUpsScheList: JSONArray): Int {
        var insertCount = 0
        deleteFollowupsScheTable()

        for (i in 0 until followUpsScheList.length()) {
            val jsonObjectFollowUpsSche = followUpsScheList.getJSONObject(i)
            val followUpsSche = FollowUpsSche()
            followUpsSche.Sync(jsonObjectFollowUpsSche)
            val rowId = insertFollowupsSche(followUpsSche)

            if (rowId != 1L)
                insertCount++
        }
        return insertCount
    }

    @Insert
    fun insertFollowupsSche(followUpsSche: FollowUpsSche): Long


    @Query("DELETE FROM " + TableContracts.TableFollowUpsSche.TABLE_NAME)
    fun deleteFollowupsScheTable()

    // Sync Max Household

    @Throws(JSONException::class)
    fun syncmaxhhno(maxhhNoList: JSONArray): Int {
        var insertCount = 0
        deleteMaxHHNoTable()

        for (i in 0 until maxhhNoList.length()) {
            val jsonObjectMaxHhno = maxhhNoList.optJSONObject(i)
            val maxHhno = MaxHhno()
            maxHhno.sync(jsonObjectMaxHhno)
            val rowId = insertMaxHHNo(maxHhno)
            if (rowId != -1L)
                insertCount++
        }
        return insertCount
    }

    @Insert
    fun insertMaxHHNo(maxHhno: MaxHhno): Long

    @Query("DELETE FROM " + TableContracts.MaxHhnoTable.TABLE_NAME)
    fun deleteMaxHHNoTable()

    // HHS

    @Throws(JSONException::class)
    fun synchhs_view(hhsList: JSONArray): Int {
        var insertCount = 0
        deleteHhsTable()

        for (i in 0 until hhsList.length()) {
            val jsonObject = hhsList.getJSONObject(i)
            val obj = Hhs()
            obj.Sync(jsonObject)
            val rowId = insertHhsTable(obj)

            if (rowId != 1L)
                insertCount++
        }

        return insertCount
    }

    @Insert
    fun insertHhsTable(hhs: Hhs): Long

    @Query("DELETE FROM " + TableContracts.TableHHS.TABLE_NAME)
    fun deleteHhsTable()

    @RawQuery
    fun getUnsyncedDataUIds(query: SupportSQLiteQuery): List<String>
}