/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.Villages
import org.json.JSONArray
import org.json.JSONException
import kotlin.jvm.Throws

@Dao
interface VillagesDao {

    @Throws(JSONException ::class)
    fun syncVillages(villagesList: JSONArray) : Int{
        var insertCount =0
        deleteVillagesTable()

        for(i in 0 until villagesList.length()){
            val jsonObjectVillages = villagesList.optJSONObject(i)

            val village = Villages()
            village.Sync(jsonObjectVillages)

            val rowId = insertVillages(village)
            if(rowId != -1L)
                insertCount++

        }

        return insertCount

    }

    @Insert
    fun insertVillages(village: Villages) : Long

    @Query("DELETE FROM " + TableContracts.TableVillage.TABLE_NAME)
    fun deleteVillagesTable()

    @Query("SELECT " + TableContracts.TableVillage.COLUMN_VILLAGE_NAME + "," + TableContracts.TableVillage.COLUMN_VILLAGE_CODE
            + " FROM " + TableContracts.TableVillage.TABLE_NAME
            + " WHERE " + TableContracts.TableVillage.COLUMN_UC_CODE
            + " LIKE :ucCode" + " order by "
            + TableContracts.TableVillage.COLUMN_VILLAGE_NAME + " ASC")
    fun getVillageByUc(ucCode: String) : List<Villages>


    @Query("SELECT DISTINCT " + TableContracts.TableVillage.COLUMN_UCNAME + ","
            + TableContracts.TableVillage.COLUMN_UC_CODE + " ,"
            + TableContracts.TableVillage.COLUMN_VILLAGE_NAME + " ,"
            + TableContracts.TableVillage.COLUMN_VILLAGE_CODE
            + " FROM " + TableContracts.TableVillage.TABLE_NAME
            + " GROUP BY " + TableContracts.TableVillage.COLUMN_UCNAME
            + " order by " + TableContracts.TableVillage.COLUMN_UCNAME + " ASC"
    )
    fun getVillageUc() : List<Villages>

}