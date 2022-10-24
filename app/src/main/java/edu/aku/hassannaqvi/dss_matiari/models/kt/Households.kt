package edu.aku.hassannaqvi.dss_matiari.models.kt

import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.aku.hassannaqvi.dss_matiari.contracts.TableContracts.HouseholdTable
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import org.apache.commons.lang3.StringUtils

@Entity(tableName = HouseholdTable.TABLE_NAME)
data class Households(
    @ColumnInfo(name = HouseholdTable.COLUMN_PROJECT_NAME)
    private var projectName: String? = MainApp.PROJECT_NAME,

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = HouseholdTable.COLUMN_ID)
    var id: Long = 0,

    @ColumnInfo(name = HouseholdTable.COLUMN_UID)
    private var uid: String? = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_USERNAME)
    private var userName: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_SYSDATE)
    private var sysDate: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_HDSSID)
    private var hdssId: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_UC_CODE)
    private var ucCode: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_VILLAGE_CODE)
    private var villageCode: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_HOUSEHOLD_NO)
    private var hhNo: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_STRUCTURE_NO)
    private var structureNo: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_VISIT_NO)
    private var visitNo: String = "0",

    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICEID)
    private var deviceId: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_DEVICETAGID)
    private var deviceTag: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_APPVERSION)
    private var appver: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_MUID)
    private var muid: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_ISTATUS)
    var iStatus: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED)
    private var synced: String = StringUtils.EMPTY,

    @ColumnInfo(name = HouseholdTable.COLUMN_SYNCED_DATE)
    private var syncDate: String = StringUtils.EMPTY,

    // SECTION VARIABLES
    @ColumnInfo(name = HouseholdTable.COLUMN_SA)
    var sA: String = StringUtils.EMPTY
): BaseObservable() {

    private var round = ""
    private var ra01 = ""
    private var ra01v2 = ""
    private var ra01v3 = ""
    private var ra02 = ""
    private var ra04 = ""
    private var ra03 = ""
    private var ra05 = ""
    private var ra07 = ""
    private var ra06 = ""
    private var ra08 = ""
    private var ra09 = ""
    private var ra10 = ""
    private var ra11 = ""
    private var ra11x = ""
    private var ra12 = ""
    private var ra12x = ""
    private var ra13 = ""
    private var ra13x = ""
    private var ra14 = ""
    private var ra15 = ""
    private var ra16 = ""
    private var ra17_a1 = ""
    private var ra17_b1 = ""
    private var ra17_c1 = ""
    private var ra17_d1 = ""
    private var ra17_a2 = ""
    private var ra17_b2 = ""
    private var ra17_c2 = ""
    private var ra17_d2 = ""
    private var ra17_a3 = ""
    private var ra17_b3 = ""
    private var ra17_c3 = ""
    private var ra17_d3 = ""
    private var ra18 = ""
    private var ra19 = ""
    private var ra20 = ""
    private var ra21 = ""
    private var ra22 = ""


}