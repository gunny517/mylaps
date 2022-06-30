package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.repository.*


@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {

    @Provides
    fun bindsApiRepository(@ApplicationContext context: Context): ApiRepository {
        return ApiRepository(context)
    }

    @Provides
    fun bindsUserAccountRepository(@ApplicationContext context: Context): UserAccountRepository {
        return UserAccountRepository(context)
    }

    @Provides
    fun bindsActivityInfoRepository(@ApplicationContext context: Context): ActivityInfoRepository {
        return ActivityInfoRepository(context)
    }

    @Provides
    fun bindsErrorLogRepository(@ApplicationContext context: Context): ErrorLogRepository {
        return ErrorLogRepository(context)
    }

    @Provides
    fun bindsLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepository(context)
    }

    @Provides
    fun bindsWeatherRepository(): WeatherRepository {
        return WeatherRepository()
    }

    @Provides
    fun bindsPracticeRepository(@ApplicationContext context: Context): PracticeRepository {
        return PracticeRepository(context)
    }

    @Provides
    fun bindsSessionInfoRepository(@ApplicationContext context: Context): SessionInfoRepository {
        return SessionInfoRepository(context)
    }

    @Provides
    fun bindsPracticeTrackRepository(@ApplicationContext context: Context): PracticeTrackRepository {
        return PracticeTrackRepository(context)
    }

}