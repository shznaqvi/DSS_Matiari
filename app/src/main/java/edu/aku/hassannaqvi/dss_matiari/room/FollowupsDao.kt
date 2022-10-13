package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.FollowupsTable
import edu.aku.hassannaqvi.dss_matiari.models.Followups
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
import org.json.JSONException
import kotlin.jvm.Throws

//
// Created by gul.sanober on 10/13/2022.
// Copyright (c) 2022 COE. All rights reserved.
//

@Dao
interface FollowupsDao {

    @Throws(JSONException :: class)
    @Insert
    fun addFollowup(followup : Followups) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFollowup(followup: Followups) : Int





}