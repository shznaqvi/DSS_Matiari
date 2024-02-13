/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche

@Dao
interface FollowUpsScheDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFollowupsSche(followUpsSche: FollowUpsSche): Int

    @Query(
        "SELECT MAX( CAST(rb01 AS INT)) AS rb01 FROM hhfuplist_view WHERE ucCode LIKE :uc AND villageCode LIKE :vCode AND hhno LIKE :hhNo GROUP BY hhno" /*+
                "MAX( CAST(" + TableContracts.TableFollowUpsSche.COLUMN_RB01 + " AS INT)) AS "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 +
                " FROM " + TableContracts.TableFollowUpsSche.TABLE_NAME +
                " WHERE " + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :vCode AND ( " +
                TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo " + ") " +
                " GROUP BY " + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO*/
    )
    fun getMaxMWRANoBYHHFromFolloupsSche(uc: String, vCode: String, hhNo: String): Int


    @Query(
        "SELECT MAX( CAST(rb01 AS INT)) AS rb01 FROM hhfuplist_view WHERE ucCode LIKE :uc AND villageCode LIKE :vCode AND hhno LIKE :hhNo AND msno LIKE :msno GROUP BY hhno"

                /* + "MAX( CAST(" + TableContracts.TableFollowUpsSche.COLUMN_RB01 + " AS INT)) AS "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 +
                " FROM " + TableContracts.TableFollowUpsSche.TABLE_NAME +
                " WHERE " + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :vCode AND ( " +
                TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo " + ")  AND " +
                TableContracts.TableFollowUpsSche.COLUMN_MSNO + " LIKE :msno " +
                " GROUP BY " + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO*/
    )
    fun getMaxChildrenNoBYMotherFromFolloupsSche(
        uc: String,
        vCode: String,
        hhNo: String,
        msno: String
    ): Int

    @Query(
        "SELECT villageCode, id, ucCode, hhno, hdssid, _muid, ra01, ra08, ra12, ra18, fRound, fup_targetDt, istatus, rb01, rb02, rb03, rb04, rc04, rb05, rb06, memberType, SUM(CASE WHEN rb07 = '1' THEN 1 else 0 END) AS rb07 FROM hhfuplist_view WHERE ucCode LIKE :uc AND villageCode LIKE :village AND ra12 LIKE '%' || :hhead || '%' GROUP BY hdssid ORDER BY id ASC "
                /*+ TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + ", " +
                TableContracts.TableFollowUpsSche.COLUMN_ID + ", " +
                TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_HDSSID + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_MUID + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RA01 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RA08 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RA12 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RA18 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_FROUND + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_DONE_DATE + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_ISTATUS + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB02 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB03 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB04 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RC04 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB05 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_RB06 + ", "
                + TableContracts.TableFollowUpsSche.COLUMN_MEMBERTYPE + ", "
                + " SUM(CASE WHEN " + TableContracts.TableFollowUpsSche.COLUMN_RB07 + "= '1' THEN 1 else 0 END) AS " + TableContracts.TableFollowUpsSche.COLUMN_RB07 +
                " FROM " + TableContracts.TableFollowUpsSche.TABLE_NAME
                + " WHERE " + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :village AND " +
                TableContracts.TableFollowUpsSche.COLUMN_RA12 + " LIKE '%' || :hhead || '%' GROUP BY "
                + TableContracts.TableFollowUpsSche.COLUMN_HDSSID
                + " ORDER BY " + TableContracts.TableFollowUpsSche.COLUMN_ID + " ASC "*/
    )
    fun getFollowUpsScheHHBYVillage(uc: String, village: String, hhead: String): List<FollowUpsSche>


    @Query("SELECT * FROM hhfuplist_view WHERE villageCode LIKE :village AND ucCode LIKE :ucCode AND rb01 != 'null' AND hhno LIKE :hhNo ORDER By id ASC"
            /*+ TableContracts.TableFollowUpsSche.TABLE_NAME + " WHERE "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :village AND "
                + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :ucCode AND "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 + " != 'null' AND "
                + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo ORDER BY "
                + TableContracts.TableFollowUpsSche.COLUMN_ID + " ASC"*/
    )
    fun getAllfollowupsScheByHH(village: String, ucCode: String, hhNo: String): List<FollowUpsSche>


    @Query("SELECT Count(*) AS mwraCount FROM hhfuplist_view WHERE rb01 != 'null' AND ucCode LIKE :uc AND villageCode like :vCode AND memberType like :memberType AND hhno like :hhNo GROUP BY hhno "
           /* + TableContracts.TableFollowUpsSche.TABLE_NAME + " WHERE "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 + " != 'null' AND "
                + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " +
                TableContracts.TableFollowUpsSche.COLUMN_MEMBERTYPE + " LIKE :memberType AND ( " +
                TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo " + ") " +
                " GROUP BY " + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO*/
    )
    fun getMWRACountBYHHFromFolloupsSche(
        uc: String,
        vCode: String,
        hhNo: String,
        memberType: String
    ): Int

    @Query(
        "SELECT Count(*) AS childCount FROM hhfuplist_view WHERE rb01 != 'null' AND ucCode like :uc and villageCode like :vCode and hhno like :hhNo and memberType = 2 group by hhno"
                /*+ TableContracts.TableFollowUpsSche.TABLE_NAME + " WHERE "
                + TableContracts.TableFollowUpsSche.COLUMN_RB01 + " != 'null' AND "
                + TableContracts.TableFollowUpsSche.COLUMN_UC_CODE + " LIKE :uc AND "
                + TableContracts.TableFollowUpsSche.COLUMN_VILLAGE_CODE + " LIKE :vCode AND ( " +
                TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO + " LIKE :hhNo " + ") AND " +
                TableContracts.TableFollowUpsSche.COLUMN_MEMBERTYPE + " = 2 " +
                " GROUP BY " + TableContracts.TableFollowUpsSche.COLUMN_HOUSEHOLD_NO*/
    )
    fun getChildCountBYHHFromFolloupsSche(uc: String, vCode: String, hhNo: String): Int

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<FollowUpsSche>)

    @Query("DELETE FROM hhfuplist_view")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<FollowUpsSche>) {
        deleteAll()
        addAllData(list)
    }


}