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

    @Query("SELECT " + "MAX( CAST(" + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO + " AS INT)) AS " + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO +
            " FROM " + TableContracts.MaxHhnoTable.TABLE_NAME +
            " WHERE " + TableContracts.MaxHhnoTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + TableContracts.MaxHhnoTable.COLUMN_VILLAGE_CODE + " LIKE :vCode "  +
            "ORDER BY " + TableContracts.MaxHhnoTable.COLUMN_ID + " ASC")
    fun getMaxHHNoByVillage(ucCode : String, vCode : String) : Int
}