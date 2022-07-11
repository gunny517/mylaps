package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.repository.*


@Module
@InstallIn(FragmentComponent::class)
object RepositoryForActivityModule {

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

    @Provides
    fun bindsTrackRepository(@ApplicationContext context: Context): TrackRepository {
        return TrackRepository(context)
    }

    @Provides
    fun bindsResourceRepository(@ActivityContext context: Context): ResourceRepository {
        return ResourceRepository(context)
    }

    @Provides
    fun bindsActivityInfoTrackRepository(@ActivityContext context: Context): ActivityInfoTrackRepository {
        return ActivityInfoTrackRepository(context)
    }
}