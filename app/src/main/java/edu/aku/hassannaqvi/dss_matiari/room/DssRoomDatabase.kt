/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.models.*
import edu.aku.hassannaqvi.dss_matiari.newstruct.dao.GeneralDao
import edu.aku.hassannaqvi.dss_matiari.newstruct.models.SyncModelNew
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.lang.reflect.Type


@Database(
    version = DssRoomDatabase.DATABASE_VERSION,
    exportSchema = true,
    entities = [
        edu.aku.hassannaqvi.dss_matiari.models.Households::class,
        Mwra :: class,
        Users::class,
        Villages ::class,
        FollowUpsSche ::class,
        MaxHhno ::class,
        Outcome ::class,
        Hhs ::class,
        EntryLog ::class
    ]
)
/* NEW STRUCT */
@TypeConverters(SyncModelNew.ResponseDate.DataConverter::class,
    Households.SA.DataConverter::class)

abstract class DssRoomDatabase : RoomDatabase() {

    abstract fun householdsDao(): HouseholdsDao
    abstract fun mwraDao() : MwraDao
    abstract fun usersDao(): UsersDao
    abstract fun VillagesDao(): VillagesDao
    abstract fun FollowUpsScheDao(): FollowUpsScheDao
    abstract fun MaxHHNoDao() : MaxHHNoDao
    abstract fun OutcomeDao() : OutcomeDao
    abstract fun syncFunctionsDao() : SyncFunctionsDao
    abstract fun HhsDao() : HhsDao
    abstract fun EntryLogDao() : EntryLogDao

    /* NEW STRUCT */
    abstract fun GeneralDao() : GeneralDao

    companion object {
        const val DATABASE_VERSION = 12
        const val DATABASE_NAME = MainApp.PROJECT_NAME + "1.db"
        const val DATABASE_COPY = MainApp.PROJECT_NAME + "1_copy.db"

        @Volatile @JvmStatic
        var dbInstance: DssRoomDatabase? = null

        @JvmStatic
        fun init(context: Context, password: String): DssRoomDatabase {
            if (dbInstance != null)
                return dbInstance!!

            synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes(password.toCharArray())
                val factory = SupportFactory(passphrase)

                dbInstance = Room.databaseBuilder(context, DssRoomDatabase::class.java, DATABASE_NAME)
                    //.openHelperFactory(factory)
                    .addMigrations(MIGRATION_4_5)
                    .addMigrations(MIGRATION_5_6)
                    .addMigrations(MIGRATION_6_7)
                    .addMigrations(MIGRATION_7_12)
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                return dbInstance!!
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'child_count' TEXT")
                database.execSQL("ALTER TABLE 'mwras' ADD COLUMN 'child_count' TEXT")
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE 'mwras' ADD COLUMN 'istatus' TEXT")
            }
        }

        private val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'pregnum' TEXT")
                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'rb22' TEXT")
                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'rb23' TEXT")
                database.execSQL("CREATE TABLE IF NOT EXISTS 'hhs_view' ('id' LONG, " +
                        "'ucCode' TEXT, " +
                        "'villageCode' TEXT, " +
                        "'hhNo' TEXT, " +
                        "'hdssid' TEXT, " +
                        "'round' TEXT, " +
                        "'ra01' TEXT, " +
                        "'ra08' TEXT, " +
                        "'ra12' TEXT, " +
                        "'ra05' TEXT, " +
                        "'ra18' TEXT, " +
                        "'ra17_a1' TEXT, " +
                        "'ra17_a2' TEXT, " +
                        "'ra17_a3' TEXT, " +
                        "'ra17_b1' TEXT, " +
                        "'ra17_b2' TEXT, " +
                        "'ra17_b3' TEXT, " +
                        "'ra17_c1' TEXT, " +
                        "'ra17_c2' TEXT, " +
                        "'ra17_c3' TEXT, " +
                        "'ra17_d1' TEXT, " +
                        "'ra17_d2' TEXT, " +
                        "'ra17_d3' TEXT, " +
                        "PRIMARY KEY('id'))");
            }
        }

        private val MIGRATION_7_12 = object : Migration(7, 12) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'hhs' ADD COLUMN 'isError' INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE 'outcomes' ADD COLUMN 'isError' INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE 'MWRAs' ADD COLUMN 'isError' INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'reg_date' TEXT")
                database.execSQL("ALTER TABLE 'outcomes' ADD COLUMN 'istatus' TEXT")
                /*database.execSQL("ALTER TABLE 'users' ADD COLUMN 'passwordEnc' TEXT ")
                database.execSQL("ALTER TABLE 'users' ADD COLUMN 'newUser' TEXT")
                database.execSQL("ALTER TABLE 'users' ADD COLUMN 'fullname' TEXT")
                database.execSQL("ALTER TABLE 'users' ADD COLUMN 'enabled' TEXT")*/
                database.execSQL("DROP TABLE users");
                database.execSQL("CREATE TABLE IF NOT EXISTS `users` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0, `username` TEXT DEFAULT '' NOT NULL, `password` TEXT NOT NULL, `passwordEnc` TEXT NOT NULL, `fullname` TEXT NOT NULL, `enabled` TEXT NOT NULL, `newUser` TEXT NOT NULL, `designation` TEXT NOT NULL)");
                database.execSQL("CREATE TABLE IF NOT EXISTS `EntryLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uid` TEXT, `projectName` TEXT, `uuid` TEXT, `userName` TEXT, `sysDate` TEXT, `entryDate` TEXT, `hhid` TEXT, `appver` TEXT, `iStatus` TEXT, `entryType` TEXT, `deviceId` TEXT, `synced` TEXT, `syncDate` TEXT, `isError` INTEGER NOT NULL DEFAULT 0)");

            }
        }
    }

    /* NEW STRUCT */

    // Type converter used to save JsonObject in a single column
    open class BaseConverter<T>(private val type: Type) {
        private val gson: Gson
        @TypeConverter
        fun fromData(data: T): String {
            return gson.toJson(data, type)
        }

        @TypeConverter
        fun toData(json: String?): T {
            return gson.fromJson(json, type)
        }

        init {
            gson = GsonBuilder().serializeNulls().create()
        }
    }

}