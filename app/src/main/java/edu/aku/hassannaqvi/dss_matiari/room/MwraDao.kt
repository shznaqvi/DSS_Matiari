package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable
import edu.aku.hassannaqvi.dss_matiari.models.Households
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import edu.aku.hassannaqvi.dss_matiari.newstruct.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew
import org.json.JSONException
import kotlin.jvm.Throws

//
// Created by gul.sanober on 10/12/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface MwraDao {

    @Throws(JSONException ::class)
    @Insert
    fun addMwra(mwra: Mwra) : Long

    @Update(onConflict =  OnConflictStrategy.REPLACE)
    fun updateMwra(mwra: Mwra) : Int


    @Query("SELECT "
            + "MAX( CAST(" + MWRATable.COLUMN_SNO + " AS INT)) AS " + MWRATable.COLUMN_SNO +
            " FROM " + MWRATable.TABLE_NAME +
            " WHERE " + MWRATable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + MWRATable.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " +
            MWRATable.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo" +
            " GROUP BY " + MWRATable.COLUMN_HOUSEHOLD_NO )
    fun getMaxMWRSNoBYHH(ucCode : String, vCode : String, hhNo : String) : Int


    @Query("SELECT * FROM " + MWRATable.TABLE_NAME + " WHERE " +
        MWRATable.COLUMN_UC_CODE + " LIKE :uc AND "
                + MWRATable.COLUMN_VILLAGE_CODE + " LIKE :village AND "
                + MWRATable.COLUMN_STRUCTURE_NO + " LIKE :structure AND (  "
                + MWRATable.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo ) AND "
                + MWRATable.COLUMN_REGROUND + " LIKE :regRound "
    )
    fun getAllMWRAByHH_internal(uc : String, village : String, structure : String, hhNo: String, regRound : String) : List<Mwra>


    fun getAllMWRAByHH(uc: String, village: String, structure: String, hhNo: String, regRound: String) : List<Mwra>
    {
        val mwra = getAllMWRAByHH_internal(uc, village, structure, hhNo, regRound)
        mwra.forEach {
            //it.sBHydrate(it.sb)
        }
        return mwra
    }
    @Query("SELECT * FROM mwras where _uuid = :uuid AND hdssid = :hdssid AND sNo = :sNo AND regRound = :regRound ORDER BY _id ASC")
    fun getMwraByUUId(uuid : String, hdssid : String, sNo : String, regRound: String) : Mwra



    @Query("SELECT Count(*) AS mwraCount" +
            " FROM " + MWRATable.TABLE_NAME +
            " WHERE " + MWRATable.COLUMN_UUID + " LIKE :uid AND " + MWRATable.COLUMN_REGROUND + " LIKE :regRound"  )
    fun getMWRACountBYUUID(uid: String, regRound: String) : Int


    @Query("SELECT * FROM " + MWRATable.TABLE_NAME + " WHERE "
            + MWRATable.COLUMN_UUID + " LIKE :uuid AND "
            + MWRATable.COLUMN_SNO + " LIKE :rb01 AND " + MWRATable.COLUMN_SNO + " != 'null' AND "
            + MWRATable.COLUMN_ROUND + " LIKE :fround AND " +
            MWRATable.COLUMN_REGROUND + " LIKE :regRound ORDER BY "
            + MWRATable.COLUMN_ID + " ASC"
    )
    fun getFollowupsBySno_internal(uuid: String, rb01: String, fround: String, regRound: String) : Mwra?


    @Throws(JSONException ::class)
    fun getFollowupsBySno(uuid: String, rb01: String, fround: String) : Mwra?
    {
        val mwra = getFollowupsBySno_internal(uuid, rb01, fround, "")
        if(mwra == null)
        {
            val tempMwra = Mwra()
            //tempMwra.Hydrate(mwra)
            return tempMwra
        }else{
            return mwra
        }

    }

    @Throws(JSONException ::class)
    fun getFollowupsBySnoEmpty(uuid: String, rb01: String, fround: String) : Mwra?
    {
        val mwra = getFollowupsBySno_internal(uuid, rb01, fround, "null")
        if(mwra == null)
        {
            val tempMwra = Mwra()
            return tempMwra
        }
        return mwra
    }

    /* NEW STRUCT */

    @Query("SELECT * FROM MWRAs WHERE _uuid IN (:uIds)")
    abstract fun getAllUnSyncedDataByUIds(uIds: List<String>): MutableList<Mwra>

    // This query is only used for updating sync list
    // id = rowId
    @Query("SELECT * FROM MWRAs WHERE _id = :id")
    fun getDataById(id: Int): Mwra

    // Update sync status as success
    fun updateSyncSuccess(responses: List<SyncModelNew.WebResponse>?) {
        if (responses != null && responses.size > 0 && responses[0].error === 0) {
            val syncedDate: String = DateUtils.getCurrentDateTime()
            val synced = "1"
            for (i in responses.indices) {
                val forms: Mwra = getDataById(responses[i].getId())
                forms.syncDate = syncedDate
                forms.synced = synced
                forms.isError = false
                updateMwra(forms)
            }
        }
    }

    // Update error status while uploading
    fun updateSyncError(list: List<Mwra>) {
        for (i in list.indices) {
            val obj: Mwra = list[i]
            obj.isError = true
            updateMwra(obj)
        }
    }

}