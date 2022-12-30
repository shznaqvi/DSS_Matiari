/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import android.os.Build
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.models.Households
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

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

    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :uc AND "
            + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :village AND " + HouseholdTable.COLUMN_REGROUND + " = :regRound ORDER BY "
            + HouseholdTable.COLUMN_ID + " ASC")
    fun getHouseholdBYVillage_internal(uc: String, village: String, regRound: String): List<Households>

    fun getHouseholdBYVillage(uc: String, village: String, regRound: String): List<Households> {
        val householdsList = getHouseholdBYVillage_internal(uc, village, regRound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            householdsList.forEach {
                it.s1Hydrate(it.sa)
            }
        }
        return householdsList
    }

    @Query("SELECT " + "MAX( CAST(" + HouseholdTable.COLUMN_HOUSEHOLD_NO + " AS INT)) AS " + HouseholdTable.COLUMN_HOUSEHOLD_NO +
            " FROM " + HouseholdTable.TABLE_NAME +
            " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :ucCode AND "
            + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :vCode AND " + HouseholdTable.COLUMN_REGROUND + " LIKE :regRound " +
            "GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE)
    fun getMaxHouseholdNo(ucCode : String, vCode : String, regRound: String) : Int

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

        val household = getHouseholdByHDSSIDASC_internal(hdssid, newHDSSID)
        household?.s1Hydrate(household.sa)
        return household
    }

    @Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " WHERE " + HouseholdTable.COLUMN_HDSSID + " LIKE :hdssid OR "
            + HouseholdTable.COLUMN_HDSSID + " LIKE :newHDSSID ORDER BY " + HouseholdTable.COLUMN_ID + " DESC")
    fun getHouseholdByHDSSIDDSC_internal(hdssid: String, newHDSSID: String): Households?

    @Throws(JSONException ::class)
    fun getHouseholdByHDSSIDDSC(hdssid : String, position: Int = 0) : Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", hdssidSplit[2].toInt())

        //return getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
       val household = getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        if(household == null)
        {
            val tempHouseholds = Households()
            return tempHouseholds
        }

        return household
    }

    @Throws(JSONException ::class)
    fun getSelectedHouseholdByHDSSID(hdssid : String, position: Int = 0) : Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format("%04d", hdssidSplit[2].toInt())

        val household = getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        if(household == null) {
            val tempHousehold = Households()
            tempHousehold.populateMeta(position)
            //tempHousehold.hdssId = hdssid
            val id = addHousehold(tempHousehold)
            tempHousehold.id = id
            tempHousehold.uid = MainApp.deviceid + tempHousehold.id
            updateHousehold(tempHousehold)

            return tempHousehold
        }else{
            return household
        }

    }




@Query("SELECT * FROM " + HouseholdTable.TABLE_NAME + " ORDER BY "
+ HouseholdTable.COLUMN_ID + " DESC")
fun getAllHouseholds(): List<Households>


@Query("SELECT * FROM " + HouseholdTable.TABLE_NAME +" WHERE "
+ HouseholdTable.COLUMN_UID + " LIKE :uid ORDER BY "
+ HouseholdTable.COLUMN_ID + " ASC")
fun getHouseholdByUID_internal(uid: String) : Households?


@kotlin.jvm.Throws(JSONException::class)
fun getHouseholdByUID(uid: String) : Households?
{
val newHousehols = getHouseholdByUID_internal(uid)
if(newHousehols == null)
{
val temHouseholds = Households()
temHouseholds.Hydrate(newHousehols)
}
return newHousehols
}


@Query("SELECT " + HouseholdTable.COLUMN_ID + ", "
+ HouseholdTable.COLUMN_UID + ", "
+ HouseholdTable.COLUMN_SYSDATE + ", "
+ HouseholdTable.COLUMN_USERNAME + ", "
+ HouseholdTable.COLUMN_ISTATUS + ", "
+ HouseholdTable.COLUMN_SYNCED + ", "
+ HouseholdTable.COLUMN_VISIT_NO + ", "
+ HouseholdTable.COLUMN_STRUCTURE_NO + ", "
+ HouseholdTable.COLUMN_VILLAGE_CODE + ", "
+ HouseholdTable.COLUMN_UC_CODE + ", "
+ HouseholdTable.COLUMN_HOUSEHOLD_NO
+ " FROM " + HouseholdTable.TABLE_NAME + " WHERE "
+ HouseholdTable.COLUMN_ISTATUS + " = '1' AND "
+ HouseholdTable.COLUMN_VISIT_NO + " < 3 ORDER BY "
+ HouseholdTable.COLUMN_ID + " ASC"
)
fun getUnclosedHouseholds() : List<Households>




}
