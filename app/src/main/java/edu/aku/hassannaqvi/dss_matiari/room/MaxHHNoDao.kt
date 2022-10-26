package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.MaxHhno
import org.json.JSONArray
import org.json.JSONException
import kotlin.jvm.Throws
import kotlin.math.max

//
// Created by gul.sanober on 10/6/2022.
// Copyright (c) 2022 Aga Khan University. All rights reserved.
//

@Dao
interface MaxHHNoDao {

    @Throws(JSONException::class)
    fun syncMaxHHNo(maxhhNoList : JSONArray) : Int{
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

    @Query("SELECT " + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO + " FROM "
            + TableContracts.MaxHhnoTable.TABLE_NAME +
            " WHERE " + TableContracts.MaxHhnoTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + TableContracts.MaxHhnoTable.COLUMN_VILLAGE_CODE + " LIKE :vCode ORDER BY "
            + TableContracts.MaxHhnoTable.COLUMN_ID + " ASC")
    fun getMaxHHNoByVillage(ucCode : String, vCode : String) : Int
}