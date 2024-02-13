package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.MaxHhno

//
// Created by gul.sanober on 10/6/2022.
// Copyright (c) 2022 Aga Khan University. All rights reserved.
//

@Dao
interface MaxHHNoDao {

    @Query("SELECT MAX( CAST(maxhhno AS INT)) AS mmaxhhno from maxhhno where ucCode like :ucCode and villageCode like :vCode order by _id ASC  "
            /*+ "MAX( CAST(" + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO + " AS INT)) AS " + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO +
            " FROM " + TableContracts.MaxHhnoTable.TABLE_NAME +
            " WHERE " + TableContracts.MaxHhnoTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + TableContracts.MaxHhnoTable.COLUMN_VILLAGE_CODE + " LIKE :vCode "  +
            "ORDER BY " + TableContracts.MaxHhnoTable.COLUMN_ID + " ASC"*/)
    fun getMaxHHNoByVillage(ucCode : String, vCode : String) : Int

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<MaxHhno>)

    @Query("DELETE FROM maxhhno")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<MaxHhno>) {
        deleteAll()
        addAllData(list)
    }

}