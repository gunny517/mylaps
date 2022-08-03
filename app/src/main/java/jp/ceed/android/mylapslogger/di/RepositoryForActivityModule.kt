package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.*
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.LoginDataSource
import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.repository.*
import jp.ceed.android.mylapslogger.util.SessionDataCreator


@Module
@InstallIn(FragmentComponent::class)
object RepositoryForActivityModule {

    @Provides
    fun bindsApiRepository(
        @ApplicationContext context: Context,
        activitiesApiDataSource: ActivitiesApiDataSource,
        sessionsApiDataSource: SessionsApiDataSource,
        sessionDataCreator: SessionDataCreator,
    ): ApiRepository {
        return ApiRepository(
            context = context,
            activitiesApiDataSource = activitiesApiDataSource,
            sessionsApiDataSource = sessionsApiDataSource,
            sessionDataCreator = sessionDataCreator,
        )
    }

    @Provides
    fun bindsUserAccountRepository(
        @ApplicationContext context: Context,
        preferenceDao: PreferenceDao,
        loginDataSource: LoginDataSource,
    ): UserAccountRepository {
        return UserAccountRepository(context, preferenceDao, loginDataSource)
    }

    @Provides
    fun bindsActivityInfoRepository(
        activityInfoDao: ActivityInfoDao,
    ): ActivityInfoRepository {
        return ActivityInfoRepository(activityInfoDao)
    }

    @Provides
    fun bindsLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepository(context)
    }

    @Provides
    fun bindsWeatherRepository(jsonApiKtorClientCreator: JsonApiKtorClientCreator): WeatherRepository {
        return WeatherRepository(jsonApiKtorClientCreator)
    }

    @Provides
    fun bindsPracticeRepository(
        practiceDao: PracticeDao
    ): PracticeRepository {
        return PracticeRepository(practiceDao)
    }

    @Provides
    fun bindsSessionInfoRepository(sessionInfoDao: SessionInfoDao): SessionInfoRepository {
        return SessionInfoRepository(sessionInfoDao)
    }

    @Provides
    fun bindsPracticeTrackRepository(
        practiceTrackDao: PracticeTrackDao,
        trackDao: TrackDao,
    ): PracticeTrackRepository {
        return PracticeTrackRepository(practiceTrackDao, trackDao)
    }

    @Provides
    fun bindsTrackRepository(trackDao: TrackDao): TrackRepository {
        return TrackRepository(trackDao)
    }

    @Provides
    fun bindsResourceRepository(@ActivityContext context: Context): ResourceRepository {
        return ResourceRepository(context)
    }

    @Provides
    fun bindsActivityInfoTrackRepository(
        activityInfoTrackDao: ActivityInfoTrackDao
    ): ActivityInfoTrackRepository {
        return ActivityInfoTrackRepository(activityInfoTrackDao)
    }

    @Provides
    fun bindsPracticeResultsRepository(
        sessionsApiDataSource: SessionsApiDataSource,
        sessionDataCreator: SessionDataCreator
    ): PracticeResultsRepository {
        return PracticeResultsRepository(
            sessionsApiDataSource = sessionsApiDataSource,
            sessionDataCreator = sessionDataCreator,
        )
    }
}