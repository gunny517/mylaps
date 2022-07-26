package jp.ceed.android.mylapslogger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.MylapsApiPathCreator
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun bindsActivitiesApiDataSource(
        apiPathCreator: MylapsApiPathCreator,
        jsonApiKtorClientCreator: JsonApiKtorClientCreator
    ): ActivitiesApiDataSource {
        return ActivitiesApiDataSource(apiPathCreator, jsonApiKtorClientCreator
        )
    }
}