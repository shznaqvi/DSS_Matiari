package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import edu.aku.hassannaqvi.dss_matiari.models.Outcome
import org.json.JSONException
import kotlin.jvm.Throws

//
// Created by gul.sanober on 11/5/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface OutcomeDao {

    @Throws(JSONException ::class)
    @Insert
    fun addOutcome(outcome: Outcome) : Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOutcome(outcome: Outcome) : Int


}