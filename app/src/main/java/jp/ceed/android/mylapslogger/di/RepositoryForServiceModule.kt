package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.PracticeDao
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.PracticeRepository
import jp.ceed.android.mylapslogger.repository.TrackRepository


@Module
@InstallIn(ServiceComponent::class)
object RepositoryForServiceModule {

    @Provides
    fun bindsApiRepository(
        @ApplicationContext context: Context,
        preferenceDao: PreferenceDao,
        activitiesApiDataSource: ActivitiesApiDataSource
    ): ApiRepository {
        return ApiRepository(context, preferenceDao, activitiesApiDataSource)
    }

    @Provides
    fun bindsPracticeRepository(practiceDao: PracticeDao): PracticeRepository {
        return PracticeRepository(practiceDao)
    }

    @Provides
    fun bindTrackRepository(trackDao: TrackDao): TrackRepository {
        return TrackRepository(trackDao)
    }

}