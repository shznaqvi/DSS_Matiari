package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.MaxHhno

//
// Created by gul.sanober on 10/6/2022.
// Copyright (c) 2022 Aga Khan University. All rights reserved.
//

@Dao
interface MaxHHNoDao {

    @Query("SELECT MAX( CAST(maxhhno AS INT)) AS mmaxhhno from maxhhno where ucCode like :ucCode and villageCode like :vCode order by _id ASC  ")
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