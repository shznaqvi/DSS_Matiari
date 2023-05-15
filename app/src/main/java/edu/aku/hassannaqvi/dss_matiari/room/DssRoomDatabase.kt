/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.models.*
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(
    version = DssRoomDatabase.DATABASE_VERSION,
    entities = [
        edu.aku.hassannaqvi.dss_matiari.models.Households::class,
        Mwra :: class,
        Users::class,
        Villages ::class,
        FollowUpsSche ::class,
        MaxHhno ::class,
        Outcome ::class,
        Hhs ::class
    ]
)


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


    companion object {
        const val DATABASE_VERSION = 7
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
                    .fallbackToDestructiveMigration()
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






    }


}