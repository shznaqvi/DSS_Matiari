/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.database.dao

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.models.Villages

@Dao
interface VillagesDao {

    @Query("SELECT * FROM villages")
    fun getAllVillages(): List<Villages>

    @Delete
    fun deleteVillage(villages: Villages)

    @Query("DELETE FROM villages")
    fun deleteVillagesTable()

    @Query(
        "SELECT villagename, villageCode from villages where uccode like :ucCode order by villagename asc "
                /*+ TableContracts.TableVillage.COLUMN_VILLAGE_NAME + "," + TableContracts.TableVillage.COLUMN_VILLAGE_CODE
                + " FROM " + TableContracts.TableVillage.TABLE_NAME
                + " WHERE " + TableContracts.TableVillage.COLUMN_UC_CODE
                + " LIKE :ucCode" + " order by "
                + TableContracts.TableVillage.COLUMN_VILLAGE_NAME + " ASC"*/
    )
    fun getVillageByUc(ucCode: String): List<Villages>


    @Query(
        "SELECT DISTINCT ucname, uccode, villagename, villagecode from villages group by ucname order by ucname asc "
                /*+ TableContracts.TableVillage.COLUMN_UCNAME + ","
                + TableContracts.TableVillage.COLUMN_UC_CODE + " ,"
                + TableContracts.TableVillage.COLUMN_VILLAGE_NAME + " ,"
                + TableContracts.TableVillage.COLUMN_VILLAGE_CODE
                + " FROM " + TableContracts.TableVillage.TABLE_NAME
                + " GROUP BY " + TableContracts.TableVillage.COLUMN_UCNAME
                + " order by " + TableContracts.TableVillage.COLUMN_UCNAME + " ASC"*/
    )
    fun getVillageUc(): List<Villages>

    /* NEW STRUCT */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllData(list: Array<Villages?>?)

    @Query("DELETE FROM Villages")
    fun deleteAll()

    @Transaction
    fun reinsert(list: Array<Villages?>?) {
        deleteAll()
        addAllData(list)
    }

}