
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.Hhs

@Dao
interface HhsDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHhs(hhs: Hhs): Int


    @Query("SELECT villageCode, id, hhNo, hdssid, ra01, ra08, ra12, round, ra05, ra17_a1, ra17_a2, ra17_a3, ra17_b1, ra17_b2, ra17_b3, ra17_c1, ra17_c2, ra17_c3, ra17_d1, ra17_d2, ra17_d3 from hhs_view where ucCode like :uc AND villageCode like :village and ra12 like '%' || :hhead || '%' group by hdssid order by id ASC"
            /*+ TableHHS.COLUMN_VILLAGE_CODE + ", " +
            TableHHS.COLUMN_ID + ", " +
            TableHHS.COLUMN_UC_CODE + ", "
            + TableHHS.COLUMN_HOUSEHOLD_NO + ", "
            + TableHHS.COLUMN_HDSSID + ", "
            + TableHHS.COLUMN_RA01 + ", "
            + TableHHS.COLUMN_RA08 + ", "
            + TableHHS.COLUMN_RA12 + ", "
            + TableHHS.COLUMN_RA18 + ", "
            + TableHHS.COLUMN_ROUND + ", "
            + TableHHS.COLUMN_RA05 + ", "
            + TableHHS.COLUMN_RA17_A1 + ", "
            + TableHHS.COLUMN_RA17_A2 + ", "
            + TableHHS.COLUMN_RA17_A3 + ", "
            + TableHHS.COLUMN_RA17_B1 + ", "
            + TableHHS.COLUMN_RA17_B2 + ", "
            + TableHHS.COLUMN_RA17_B3 + ", "
            + TableHHS.COLUMN_RA17_C1 + ", "
            + TableHHS.COLUMN_RA17_C2 + ", "
            + TableHHS.COLUMN_RA17_C3 + ", "
            + TableHHS.COLUMN_RA17_D1 + ", "
            + TableHHS.COLUMN_RA17_D2 + ", "
            + TableHHS.COLUMN_RA17_D3
            + " FROM " + TableHHS.TABLE_NAME
            + " WHERE " + TableHHS.COLUMN_UC_CODE + " LIKE :uc AND "
            + TableHHS.COLUMN_VILLAGE_CODE + " LIKE :village AND " +
            TableHHS.COLUMN_RA12 + " LIKE '%' || :hhead || '%' GROUP BY "
            + TableHHS.COLUMN_HDSSID
            + " ORDER BY " + TableHHS.COLUMN_ID + " ASC"*/)
    fun getHhsBYVillage(uc : String, village: String, hhead : String) : List<Hhs>

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<Hhs>)

    @Query("DELETE FROM hhs_view")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<Hhs>) {
        deleteAll()
        addAllData(list)
    }



}