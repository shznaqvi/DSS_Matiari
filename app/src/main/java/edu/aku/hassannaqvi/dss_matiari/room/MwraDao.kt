package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableFollowUpsSche
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import org.json.JSONException
import org.json.JSONObject
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
            + MWRATable.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " + "( " +
            MWRATable.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo" + ") " +
            " GROUP BY " + MWRATable.COLUMN_HOUSEHOLD_NO )
    fun getMaxMWRSNoBYHH(ucCode : String, vCode : String, hhNo : String) : Int

    @Query(
        "SELECT " +
                "MAX(" + TableFollowUpsSche.COLUMN_RB01 + ") AS "
                + TableFollowUpsSche.COLUMN_RB01 +
                " FROM " + TableFollowUpsSche.TABLE_NAME +
                " WHERE " + TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :vCode AND ( " +
                TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo " + ") " +
                " GROUP BY " + TableFollowUpsSche.COLUMN_HOUSEHOLD_NO
    )
    fun getMaxMWRANoBYHHFromFolloupsSche(uc : String, vCode : String, hhNo: String) : Int


    @Throws(JSONException ::class)
    @Query("SELECT * FROM " + MWRATable.TABLE_NAME + " WHERE " +
        MWRATable.COLUMN_UC_CODE + " LIKE :uc AND "
                + MWRATable.COLUMN_VILLAGE_CODE + " LIKE :village AND "
                + MWRATable.COLUMN_STRUCTURE_NO + " LIKE :structure AND (  "
                + MWRATable.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo ) "
    )
    fun getAllMWRAByHH(uc : String, village : String, structure : String, hhNo: String) : List<Mwra>


    @Query("SELECT Count(*) AS mwraCount" +
            " FROM " + MWRATable.TABLE_NAME +
            " WHERE " + MWRATable.COLUMN_UUID + " LIKE :uid "  )
    fun getMWRACountBYUUID(uid: String) : Int




}