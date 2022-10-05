package edu.aku.hassannaqvi.dss_matiari.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.aku.hassannaqvi.dss_matiari.core.MainApp
import edu.aku.hassannaqvi.dss_matiari.models.Households
import edu.aku.hassannaqvi.dss_matiari.models.Users
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(
    version = DssRoomDatabase.DATABASE_VERSION,
    entities = [
        Households::class,
        Users::class
    ]
)
abstract class DssRoomDatabase : RoomDatabase() {

    abstract fun householdsDao(): HouseholdsDao

    companion object {
        const val DATABASE_VERSION = 5
        const val DATABASE_NAME = MainApp.PROJECT_NAME + ".db"

        @Volatile
        private var dbInstance: DssRoomDatabase? = null

        @JvmStatic
        fun init(context: Context, password: String): DssRoomDatabase {
            if (dbInstance != null)
                return dbInstance!!

            synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes(password.toCharArray())
                val factory = SupportFactory(passphrase)

                dbInstance = Room.databaseBuilder(context, DssRoomDatabase::class.java, DATABASE_NAME + "__1+")
                    .openHelperFactory(factory)
                    .addMigrations(MIGRATION_4_5)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                return dbInstance!!
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn’t alter the table, there’s nothing else
                // to do here.
            }
        }
    }
}