package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.dao.ActivityInfoTrackDao
import jp.ceed.android.mylapslogger.dao.PracticeDao
import jp.ceed.android.mylapslogger.dao.PracticeTrackDao
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dao.SessionInfoDao
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.LoginDataSource
import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import jp.ceed.android.mylapslogger.repository.ActivityInfoTrackRepository
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.InputValueRepository
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.MixtureRepository
import jp.ceed.android.mylapslogger.repository.PracticeRepository
import jp.ceed.android.mylapslogger.repository.PracticeResultsRepository
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import jp.ceed.android.mylapslogger.repository.ResourceRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.TrackRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
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

    @Provides
    fun bindInputValuesRepository(@ActivityContext context: Context): InputValueRepository {
        return InputValueRepository(context)
    }

    @Provides
    fun bindMixtureRepository(): MixtureRepository {
        return MixtureRepository()
    }
}