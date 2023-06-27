
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.TableHHS
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche
import edu.aku.hassannaqvi.dss_matiari.models.Hhs
import org.json.JSONArray
import org.json.JSONException
import kotlin.jvm.Throws

@Dao
interface HhsDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHhs(hhs: Hhs): Int


    @Query("SELECT " + TableHHS.COLUMN_VILLAGE_CODE + ", " +
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
            + " ORDER BY " + TableHHS.COLUMN_ID + " ASC")
    fun getHhsBYVillage(uc : String, village: String, hhead : String) : List<Hhs>

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<Hhs?>?)

    @Query("DELETE FROM hhs_view")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<Hhs?>?) {
        deleteAll()
        addAllData(list)
    }



}