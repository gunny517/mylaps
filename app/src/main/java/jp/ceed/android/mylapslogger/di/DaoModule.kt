package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.dao.*
import jp.ceed.android.mylapslogger.database.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun bindsActivityInfoDao(@ApplicationContext context: Context): ActivityInfoDao {
        return AppDatabase.getInstance(context).activityInfoDao()
    }

    @Provides
    fun bindsActivityInfoTrackDao(@ApplicationContext context: Context): ActivityInfoTrackDao {
        return AppDatabase.getInstance(context).activityInfoTrackDao()
    }

    @Provides
    fun bindsErrorLogDao(@ApplicationContext context: Context): ErrorLogDao {
        return AppDatabase.getInstance(context).errorLogDao()
    }

    @Provides
    fun bindsPracticeDao(@ApplicationContext context: Context): PracticeDao {
        return AppDatabase.getInstance(context).practiceDao()
    }

    @Provides
    fun bindsPracticeTrackDao(@ApplicationContext context: Context): PracticeTrackDao {
        return AppDatabase.getInstance(context).practiceTrackDao()
    }

    @Provides
    fun bindPreferenceDao(@ApplicationContext context: Context): PreferenceDao {
        return PreferenceDao(context)
    }

    @Provides
    fun bindsSessionInfoDao(@ApplicationContext context: Context): SessionInfoDao {
        return AppDatabase.getInstance(context).sessionInfoDao()
    }

    @Provides
    fun bindsTrackDao(@ApplicationContext context: Context): TrackDao {
        return AppDatabase.getInstance(context).trackDao()
    }
}