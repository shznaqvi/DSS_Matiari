package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.Mwra
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
        }/*else{
            outcome.sEHydrate(outcome.se)
        }*/
        return outcome
    }

    @Query("SELECT * FROM " + TableContracts.OutcomeTable.TABLE_NAME + " WHERE "
            + TableContracts.OutcomeTable.COLUMN_UUID + " LIKE :uuid AND "
            + TableContracts.OutcomeTable.COLUMN_SNO + " LIKE :rb01 AND "
            + TableContracts.OutcomeTable.COLUMN_MUID + " LIKE :muid AND "
            + TableContracts.OutcomeTable.COLUMN_ROUND + " LIKE :fround AND " +
            TableContracts.OutcomeTable.COLUMN_REGROUND + " LIKE :regRound ORDER BY "
            + TableContracts.OutcomeTable.COLUMN_ID + " ASC"
    )
    fun getOutcomeFollowupsBySno_internal(uuid: String, rb01: String, muid: String, fround: String, regRound: String) : Outcome?


    @Throws(JSONException ::class)
    fun getOutcomeFollowupsBySno(uuid: String, rb01: String, muid: String, fround: String) : Outcome?
    {
        val outcome = getOutcomeFollowupsBySno_internal(uuid, rb01, muid, fround, "")
        if(outcome == null)
        {
            val tempOutcome = Outcome()
            return tempOutcome
        } else {
            outcome.sEHydrate(outcome.se)

        }
        return outcome
    }




}