package jp.ceed.android.mylapslogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.dao.ActivityInfoTrackDao
import jp.ceed.android.mylapslogger.dao.ErrorLogDao
import jp.ceed.android.mylapslogger.dao.PracticeDao
import jp.ceed.android.mylapslogger.dao.PracticeTrackDao
import jp.ceed.android.mylapslogger.dao.SessionInfoDao
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.ErrorLog
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.entity.Track

const val DATABASE_VERSION = 11

@Database(entities = [
    ActivityInfo::class,
    SessionInfo::class,
    Track::class,
    Practice::class,
    ErrorLog::class ],
    version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityInfoDao(): ActivityInfoDao

    abstract fun sessionInfoDao(): SessionInfoDao

    abstract fun trackDao(): TrackDao

    abstract fun practiceDao(): PracticeDao

    abstract fun practiceTrackDao(): PracticeTrackDao

    abstract fun errorLogDao(): ErrorLogDao

    abstract fun activityInfoTrackDao(): ActivityInfoTrackDao

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
                    addMigrations(MIGRATION_5_6)
                    addMigrations(MIGRATION_6_7)
                    addMigrations(MIGRATION_7_8)
                    addMigrations(MIGRATION_8_9)
                    addMigrations(MIGRATION_9_10)
                    addMigrations(MIGRATION_10_11)
                }.build()
                INSTANCE = instance
                instance
            }
        }

        const val CREATE_SESSION_INFO = "CREATE TABLE IF NOT EXISTS SessionInfo (" +
                "session_id INTEGER NOT NULL, " +
                "temperature TEXT, " +
                "pressure TEXT, " +
                "humidity TEXT, " +
                "description TEXT, " +
                "PRIMARY KEY(session_id)" +
                ")"
        
        const val CREATE_TRACK = "CREATE TABLE IF NOT EXISTS Track (" +
                "id INTEGER NOT NULL, " +
                "name TEXT NOT NULL, " +
                "length INTEGER NOT NULL, " +
                "created INTEGER NOT NULL, " +
                "PRIMARY KEY(id)" +
                ")"
        
        const val CREATE_PRACTICE = "CREATE TABLE IF NOT EXISTS Practice (" +
                "activity_id INTEGER NOT NULL, " +
                "track_id INTEGER NOT NULL, " +
                "lap_count INTEGER NOT NULL, " +
                "best_lap TEXT NOT NULL, " +
                "start_time TEXT NOT NULL, " +
                "end_time TEXT NOT NULL, " +
                "display_time TEXT, " +
                "total_training_time TEXT NOT NULL, " +
                "active_training_time TEXT NOT NULL, " +
                "PRIMARY KEY(`activity_id`)" +
                ")"

        const val CRATE_ERROR_LOG = "CREATE TABLE IF NOT EXISTS ErrorLog (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "stack_trace TEXT NOT NULL, " +
                "created INTEGER NOT NULL" +
                ")"

        const val ALTER_ACTIVITY_INFO_ADD_FUEL_CONSUMPTION = "ALTER TABLE ActivityInfo " +
                "ADD COLUMN fuel_consumption REAL "

        const val ALTER_ACTIVITY_INFO_ADD_TRACK_ID = "ALTER TABLE ActivityInfo " +
                "ADD COLUMN " +
                "track_id INTEGER NOT NULL DEFAULT 0"

        const val ALTER_ACTIVITY_INFO_ADD_DATETIME = "ALTER TABLE ActivityInfo " +
                "ADD COLUMN " +
                "date_time TEXT NOT NULL DEFAULT ''"

        const val TRUNCATE_PRACTICE = "DELETE FROM Practice"

        private val MIGRATION_2_3 = object : Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_TRACK)
                database.execSQL(CREATE_PRACTICE)
            }
        }

        const val DROP_PRACTICE = "DROP TABLE Practice"

        private val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_SESSION_INFO)
            }
        }

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

        private val MIGRATION_5_6 = object : Migration(5, 6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(DROP_PRACTICE)
                database.execSQL(CREATE_PRACTICE)
            }
        }

        private val MIGRATION_6_7 = object : Migration(6, 7){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CRATE_ERROR_LOG)
            }
        }

        private val MIGRATION_7_8 = object : Migration(7, 8){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(ALTER_ACTIVITY_INFO_ADD_FUEL_CONSUMPTION)
            }
        }

        private val MIGRATION_8_9 = object : Migration(8, 9){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(ALTER_ACTIVITY_INFO_ADD_TRACK_ID)
                database.execSQL(ALTER_ACTIVITY_INFO_ADD_DATETIME)
            }
        }

        private val MIGRATION_9_10 = object : Migration(9, 10){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(TRUNCATE_PRACTICE)
            }
        }

        private val MIGRATION_10_11 = object : Migration(10, 11){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(DROP_PRACTICE)
                database.execSQL(CREATE_PRACTICE)
            }
        }
    }
}