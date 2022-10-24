/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.MWRATable
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraCount
import edu.aku.hassannaqvi.dss_matiari.models.Households
import org.json.JSONException

@Dao
interface HouseholdsDao {

    @kotlin.jvm.Throws(JSONException::class)
    @Insert
    fun addHousehold(households: Households) : Long

    @Update(onConflict = REPLACE)
    fun updateHousehold(households: Households): Int

    @Query("SELECT " + "MAX(" + HouseholdTable.COLUMN_STRUCTURE_NO + ") AS"
     + HouseholdTable.COLUMN_STRUCTURE_NO +
            " FROM " + HouseholdTable.TABLE_NAME +
            " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :uc AND "
            + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :vCode " +
            " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE)
    fun getMaxStructure(uc : String, vCode : String) : Int

    @Throws(JSONException::class)
    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :uc AND "
            + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :village AND " + HouseholdTable.COLUMN_REGROUND + " = :regRound ORDER BY "
            + HouseholdTable.COLUMN_ID + " ASC")
    fun getHouseholdBYVillage(uc: String, village: String, regRound: String): List<Households>

    @Query("SELECT " + "MAX(" + HouseholdTable.COLUMN_HOUSEHOLD_NO + ") AS " + HouseholdTable.COLUMN_HOUSEHOLD_NO +
            " FROM " + HouseholdTable.TABLE_NAME +
            " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " + HouseholdTable.COLUMN_REGROUND + " LIKE :regRound " +
            "GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE)
    fun getMaxHouseholdNo(ucCode : String, vCode : String, regRound: String) : Int

    @Query("SELECT " + TableContracts.MaxHhnoTable.COLUMN_MAX_HHNO + " FROM "
            + TableContracts.MaxHhnoTable.TABLE_NAME +
            " WHERE " + TableContracts.MaxHhnoTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + TableContracts.MaxHhnoTable.COLUMN_VILLAGE_CODE + " LIKE :vCode ORDER BY "
            + TableContracts.MaxHhnoTable.COLUMN_ID + " ASC")
    fun getMaxHHNoByVillage(ucCode : String, vCode : String) : Int

    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " WHERE " + HouseholdTable.COLUMN_HDSSID + " LIKE :hdssid OR "
            + HouseholdTable.COLUMN_HDSSID + " LIKE :newHDSSID ORDER BY " + HouseholdTable.COLUMN_ID + " ASC")
    fun getHouseholdByHDSSIDASC_internal(hdssid: String, newHDSSID: String): Households?

    @Throws(JSONException ::class)
    fun getHouseholdByHDSSIDASC(hdssid : String) : Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format(
            "%04d",
            hdssidSplit[2].toInt())


        return getHouseholdByHDSSIDASC_internal(hdssid, newHDSSID)
    }

    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " WHERE " + HouseholdTable.COLUMN_HDSSID + " LIKE :hdssid OR "
            + HouseholdTable.COLUMN_HDSSID + " LIKE :newHDSSID ORDER BY " + HouseholdTable.COLUMN_ID + " DESC")
    fun getHouseholdByHDSSIDDSC_internal(hdssid: String, newHDSSID: String): Households?

    @Throws(JSONException ::class)
    fun getHouseholdByHDSSIDDSC(hdssid : String, position: Int = 0) : Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format(
            "%04d",
            hdssidSplit[2].toInt()
        )

        //return getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
       val household = getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        if (household == null) {
            val tempHousehold = Households()
            tempHousehold.populateMeta(position)
            //tempHousehold.hdssId = hdssid
            val id = addHousehold(tempHousehold)
            tempHousehold.id = id
            tempHousehold.uid = MainApp.deviceid + tempHousehold.id
            updateHousehold(tempHousehold)

            return tempHousehold
        }
        return household
    }

    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " ORDER BY " + HouseholdTable.COLUMN_ID + " DESC")
    fun getAllHouseholds(): List<Households>

}
