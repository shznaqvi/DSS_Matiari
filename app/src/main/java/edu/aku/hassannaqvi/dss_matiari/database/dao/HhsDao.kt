
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


    @Query("SELECT villageCode, id, hhNo, hdssid, ra01, ra08, ra12, round, ra05, ra17_a1, ra17_a2, ra17_a3, ra17_b1, ra17_b2, ra17_b3, ra17_c1, ra17_c2, ra17_c3, ra17_d1, ra17_d2, ra17_d3 from hhs_view where ucCode like :uc AND villageCode like :village and ra12 like '%' || :hhead || '%' group by hdssid order by id ASC")
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