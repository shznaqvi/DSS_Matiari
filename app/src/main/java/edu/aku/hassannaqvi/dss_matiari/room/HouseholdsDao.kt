/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable
import edu.aku.hassannaqvi.dss_matiari.models.Households
import edu.aku.hassannaqvi.dss_matiari.models.Villages

@Dao
interface HouseholdsDao {

    @Insert
    fun insertHouseholds(households: Households) : Long






@Query("SELECT " + "MAX(" + HouseholdTable.COLUMN_STRUCTURE_NO + ") AS"
 + HouseholdTable.COLUMN_STRUCTURE_NO +
        " FROM " + HouseholdTable.TABLE_NAME +
        " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :uc AND "
        + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :vCode " +
        " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE)
fun getMaxStructure(uc : String, vCode : String) : Int
}
