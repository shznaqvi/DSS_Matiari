package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
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


    @Query("SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME + " WHERE "
            + TableContracts.OutcomeTable.COLUMN_MUID + " LIKE :muid AND "
            + TableContracts.OutcomeTable.COLUMN_SNO + " LIKE :sno ORDER BY "
            + TableContracts.OutcomeTable.COLUMN_ID + " ASC" )
    fun getOutcomeByID_internal(muid : String, sno : String) : Outcome?


    @Throws(JSONException ::class)
    fun getOutcomeBYID(muid: String, sno: String) : Outcome?{
        val outcome = getOutcomeByID_internal(muid, sno)
        if(outcome == null)
        {
            val tempOutcome = Outcome()
            return tempOutcome
        }else{
            outcome.sEHydrate(outcome.se)
        }
        return outcome
    }




}