
/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import androidx.room.*
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts
import edu.aku.hassannaqvi.dss_matiari.models.FollowUpsSche
import edu.aku.hassannaqvi.dss_matiari.models.Hhs
import org.json.JSONArray
import org.json.JSONException
import kotlin.jvm.Throws

@Dao
interface HhsDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHhs(hhs: Hhs): Int

}