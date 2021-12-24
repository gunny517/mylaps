package jp.ceed.android.mylapslogger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.entity.ActivityInfo

@Database(entities = [ActivityInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityInfoDao(): ActivityInfoDao

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
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}