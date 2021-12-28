package jp.ceed.android.mylapslogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.dao.SessionInfoDao
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.SessionInfo

@Database(entities = [ActivityInfo::class, SessionInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityInfoDao(): ActivityInfoDao

    abstract fun sessionInfoDao(): SessionInfoDao

    companion object {

        private const val DATABASE_NAME = "MY_LAPS_LOGGER_DB"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).apply {
                    addMigrations(MIGRATION_1_2)
                }.build()
                INSTANCE = instance
                instance
            }
        }

        const val CREATE_SESSION_INFO = "CREATE TABLE IF NOT EXISTS SessionInfo (session_id INTEGER NOT NULL, temperature TEXT, pressure TEXT, humidity TEXT, description TEXT, PRIMARY KEY(session_id))"

        private val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_SESSION_INFO)
            }
        }
    }


}