package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew
import org.json.JSONException

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

    @Query("SELECT MAX( CAST(sNo AS INT)) SNO from mwras where ucCode like :ucCode and villageCode like :vCode and hhNo like :hhNo group by hhNo")
    fun getMaxMWRSNoBYHH(ucCode : String, vCode : String, hhNo : String) : Int


    @Query("SELECT * FROM mwras where ucCode like :uc and villageCode like :village and structureNo like :structure and hhNo like :hhNo and regRound like :regRound")
    fun getAllMWRAByHH_internal(uc : String, village : String, structure : String, hhNo: String, regRound : String) : List<Mwra>


    fun getAllMWRAByHH(uc: String, village: String, structure: String, hhNo: String, regRound: String) : List<Mwra>
    {
        val mwra = getAllMWRAByHH_internal(uc, village, structure, hhNo, regRound)
        mwra.forEach {
        }
        return mwra
    }
    @Query("SELECT * FROM mwras where _uuid = :uuid AND hdssid = :hdssid AND sNo = :sNo AND regRound = :regRound ORDER BY _id ASC")
    fun getMwraByUUId(uuid : String, hdssid : String, sNo : String, regRound: String) : Mwra



    @Query("SELECT Count(*) AS mwraCount from mwras where _uuid like :uid and regRound like :regRound")
    fun getMWRACountBYUUID(uid: String, regRound: String) : Int


    @Query("SELECT * FROM mwras where _uuid like :uuid and sNo like :rb01 and sNo != 'null' and round like :fround and regRound like :regRound order by _id ASC ")
    fun getFollowupsBySno_internal(uuid: String, rb01: String, fround: String, regRound: String) : Mwra?


    @Throws(JSONException ::class)
    fun getFollowupsBySno(uuid: String, rb01: String, fround: String) : Mwra?
    {
        val mwra = getFollowupsBySno_internal(uuid, rb01, fround, "")
        if(mwra == null)
        {
            val tempMwra = Mwra()
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
    fun getAllUnSyncedDataByUIds(uIds: List<String>): List<Mwra>


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