/**
 * Created by gul.sanober on 10/06/2022.
 */

package edu.aku.hassannaqvi.dss_matiari.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
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
        Outcome ::class

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


    companion object {
        const val DATABASE_VERSION = 5
        const val DATABASE_NAME = MainApp.PROJECT_NAME + "1.db"

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
                    //.addMigrations(MIGRATION_5_6)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                return dbInstance!!
            }
        }

        private val MIGRATION_4_5 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE 'hhfuplist_view' ADD COLUMN 'child_count' TEXT")
                database.execSQL("ALTER TABLE 'mwras' ADD COLUMN 'child_count' TEXT")
            }
        }




    }


}