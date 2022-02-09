package jp.ceed.android.mylapslogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import jp.ceed.android.mylapslogger.dao.*
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.entity.Track

@Database(entities = [
    ActivityInfo::class,
    SessionInfo::class,
    Track::class,
    Practice::class], version = 6)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityInfoDao(): ActivityInfoDao

    abstract fun sessionInfoDao(): SessionInfoDao

    abstract fun trackDao(): TrackDao

    abstract fun practiceDao(): PracticeDao

    abstract fun practiceTrackDao(): PracticeTrackDao

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
                    addMigrations(MIGRATION_2_3)
                    addMigrations(MIGRATION_3_4)
                    addMigrations(MIGRATION_4_5)
                }.build()
                INSTANCE = instance
                instance
            }
        }

        const val CREATE_SESSION_INFO = "CREATE TABLE IF NOT EXISTS SessionInfo " +
                "(session_id INTEGER NOT NULL, " +
                "temperature TEXT, " +
                "pressure TEXT, " +
                "humidity TEXT, " +
                "description TEXT, " +
                "PRIMARY KEY(session_id))"

        private val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_SESSION_INFO)
            }
        }
        
        const val CREATE_TRACK = "CREATE TABLE IF NOT EXISTS Track " +
                "(id INTEGER NOT NULL, " +
                "name TEXT NOT NULL, " +
                "length INTEGER NOT NULL, " +
                "created INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"
        
        const val CREATE_PRACTICE = "CREATE TABLE IF NOT EXISTS Practice " +
                "(id INTEGER NOT NULL, " +
                "track_id INTEGER NOT NULL, " +
                "lap_count INTEGER NOT NULL, " +
                "best_lap TEXT NOT NULL, " +
                "start_time TEXT NOT NULL, " +
                "end_time TEXT NOT NULL, " +
                "display_time TEXT, " +
                "total_training_time TEXT NOT NULL, " +
                "active_training_time TEXT NOT NULL, " +
                "activity_id INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"

        private val MIGRATION_2_3 = object : Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_TRACK)
                database.execSQL(CREATE_PRACTICE)
            }
        }

        const val DROP_PRACTICE = "DROP TABLE Practice"

        private val MIGRATION_3_4 = object : Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(DROP_PRACTICE)
                database.execSQL(CREATE_PRACTICE)
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(DROP_PRACTICE)
                database.execSQL(CREATE_PRACTICE)
            }
        }
    }
}