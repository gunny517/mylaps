package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.PracticeRepository
import jp.ceed.android.mylapslogger.repository.TrackRepository


@Module
@InstallIn(ServiceComponent::class)
object RepositoryForServiceModule {

    @Provides
    fun bindsApiRepository(@ApplicationContext context: Context): ApiRepository {
        return ApiRepository(context)
    }

    @Provides
    fun bindsPracticeRepository(@ApplicationContext context: Context): PracticeRepository {
        return PracticeRepository(context)
    }

    @Provides
    fun bindTrackRepository(@ApplicationContext context: Context): TrackRepository {
        return TrackRepository(context)
    }

}