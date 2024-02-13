/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.database.dao

import android.os.Build
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable
import edu.aku.hassannaqvi.dss_matiari.models.Households
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew
import org.json.JSONException

@Dao
interface HouseholdsDao {

    @kotlin.jvm.Throws(JSONException::class)
    @Insert
    fun addHousehold(households: Households): Long

    @Update(onConflict = REPLACE)
    fun updateHousehold(households: Households): Int

    @Query(
        "SELECT " + "MAX(" + HouseholdTable.COLUMN_STRUCTURE_NO + ") AS"
                + HouseholdTable.COLUMN_STRUCTURE_NO +
                " FROM " + HouseholdTable.TABLE_NAME +
                " WHERE " + HouseholdTable.COLUMN_UC_CODE + " LIKE :uc AND "
                + HouseholdTable.COLUMN_VILLAGE_CODE + " LIKE :vCode " +
                " GROUP BY " + HouseholdTable.COLUMN_VILLAGE_CODE
    )
    fun getMaxStructure(uc: String, vCode: String): Int

    @Query("SELECT * FROM hhs WHERE ucCode LIKE :uc AND villageCode LIKE :village AND regRound LIKE :regRound ORDER BY _id ASC ")
    fun getHouseholdBYVillage_internal(
        uc: String,
        village: String,
        regRound: String
    ): List<Households>

    fun getHouseholdBYVillage(uc: String, village: String, regRound: String): List<Households> {
        val householdsList = getHouseholdBYVillage_internal(uc, village, regRound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            householdsList.forEach {
                //it.s1Hydrate(it.sa)
            }
        }
        return householdsList
    }

    @Query("SELECT MAX( CAST(hhNo AS INT)) AS hhNO FROM hhs WHERE ucCode LIKE :ucCode AND villageCode LIKE :vCode AND regRound LIKE :regRound GROUP BY villageCode"
    )
    fun getMaxHouseholdNo(ucCode: String, vCode: String, regRound: String): Int

    @Query("SELECT * FROM hhs WHERE hdssid LIKE :hdssid OR hdssid LIKE :newHDSSID ORDER BY _id ASC")
    fun getHouseholdByHDSSIDASC_internal(hdssid: String, newHDSSID: String): Households?

    @Throws(JSONException::class)
    fun getHouseholdByHDSSIDASC(hdssid: String): Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format(
            "%04d",
            hdssidSplit[2].toInt()
        )

        val household = getHouseholdByHDSSIDASC_internal(hdssid, newHDSSID)
        //household?.s1Hydrate(household.sa)
        return household
    }

    @Query("SELECT * FROM hhs WHERE hdssid LIKe :hdssid OR hdssid LIKE :newHDSSID ORDER BY _id DESC")
    fun getHouseholdByHDSSIDDSC_internal(hdssid: String, newHDSSID: String): Households?

    @Throws(JSONException::class)
    fun getHouseholdByHDSSIDDSC(hdssid: String, position: Int = 0): Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format(
            "%04d",
            hdssidSplit[2].toInt())

        //return getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        val household = getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        if (household == null) {
            val tempHouseholds = Households()
            return tempHouseholds
        }

        return household
    }

    @Throws(JSONException::class)
    fun getSelectedHouseholdByHDSSID(hdssid: String, position: Int = 0): Households? {
        // Household number in DSSID was changed to 4-digits to capture more than 999 households
        val hdssidSplit = hdssid.split("-").toTypedArray()
        val newHDSSID = hdssidSplit[0] + "-" + hdssidSplit[1] + "-" + String.format(
            "%04d",
            hdssidSplit[2].toInt()
        )
        val household = getHouseholdByHDSSIDDSC_internal(hdssid, newHDSSID)
        if (household == null) {
            val tempHousehold = Households()
            tempHousehold.populateMeta(position)
           //val id = addHousehold(tempHousehold)
            //tempHousehold.id = id
            //tempHousehold.uid = MainApp.deviceid + tempHousehold.id
            //tempHousehold.sAtoString()
            //updateHousehold(tempHousehold)
            return tempHousehold
        }else{
            //household.s1Hydrate(household.sa)
            return household
        }

    }


    @Query("SELECT * FROM hhs ORDER BY _id DESC ")
    fun getAllHouseholds(): List<Households>


    @Query("SELECT * FROM hhs WHERE _uid LIKE :uid ORDER BY _id ASC")
    fun getHouseholdByUID_internal(uid: String): Households?


    @kotlin.jvm.Throws(JSONException::class)
    fun getHouseholdByUID(uid: String): Households? {
        val newHousehols = getHouseholdByUID_internal(uid)
        /*if (newHousehols == null) {
            val temHouseholds = Households()
            //temHouseholds.Hydrate(newHousehols)
        }*/
        return newHousehols
    }


    @Query("SELECT _id, _uid, sysDate, username, istatus, synced, visitNo, structureNo, villageCode, ucCode, hhNo, isError FROM hhs WHERE istatus = '1' AND visitNo < 3 ORDER By _id ASC ")
    fun getUnclosedHouseholds(): List<Households>

    /* NEW STRUCT */

    @Query("SELECT * FROM hhs WHERE _uid IN (:uIds)")
    abstract fun getAllUnSyncedDataByUIds(uIds: List<String?>?): MutableList<Households?>?

    // This query is only used for updating sync list
    // id = rowId
    @Query("SELECT * FROM hhs WHERE _id = :id")
    fun getDataById(id: Int): Households

    // Update sync status as success
    fun updateSyncSuccess(responses: List<SyncModelNew.WebResponse>?) {
        if (responses != null && responses.size > 0 && responses[0].error === 0) {
            val syncedDate: String = DateUtils.getCurrentDateTime()
            val synced = "1"
            for (i in responses.indices) {
                val forms: Households = getDataById(responses[i].getId())
                forms.syncDate = syncedDate
                forms.synced = synced
                forms.isError = false
                updateHousehold(forms)
            }
        }
    }

    // Update error status while uploading
    fun updateSyncError(list: List<Households>) {
        for (i in list.indices) {
            val obj: Households = list[i]
            obj.isError = true
            updateHousehold(obj)
        }
    }

}
