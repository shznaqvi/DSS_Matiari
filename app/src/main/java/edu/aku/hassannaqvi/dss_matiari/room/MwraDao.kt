package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
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
            + "MAX(" + MWRATable.COLUMN_SNO + ") AS " + MWRATable.COLUMN_SNO +
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
            it.sBHydrate(it.sb)
        }
        return mwra
    }

    @Query("SELECT Count(*) AS mwraCount" +
            " FROM " + MWRATable.TABLE_NAME +
            " WHERE " + MWRATable.COLUMN_UUID + " LIKE :uid AND " + MWRATable.COLUMN_REGROUND + " LIKE :regRound"  )
    fun getMWRACountBYUUID(uid: String, regRound: String) : Int


    @Query("SELECT * FROM " + MWRATable.TABLE_NAME + " WHERE "
            + MWRATable.COLUMN_UUID + " LIKE :uuid AND "
            + MWRATable.COLUMN_SNO + " LIKE :rb01 AND "
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
            return tempMwra
        } /*else {
            mwra.sCHydrate(mwra.sc)
            mwra.sDHydrate(mwra.sd)
        }*/
        return mwra
    }




}