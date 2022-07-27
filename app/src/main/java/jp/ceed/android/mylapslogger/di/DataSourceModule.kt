package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.LoginDataSource
import jp.ceed.android.mylapslogger.datasource.MylapsApiPathCreator
import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun bindsActivitiesApiDataSource(
        apiPathCreator: MylapsApiPathCreator,
        jsonApiKtorClientCreator: JsonApiKtorClientCreator
    ): ActivitiesApiDataSource {
        return ActivitiesApiDataSource(
            apiPathCreator = apiPathCreator,
            jsonApiKtorClientCreator = jsonApiKtorClientCreator,
        )
    }

    @Provides
    fun bindsSessionApiDataSource(
        apiPathCreator: MylapsApiPathCreator,
        jsonApiKtorClientCreator: JsonApiKtorClientCreator
    ): SessionsApiDataSource {
        return SessionsApiDataSource(
            apiPathCreator = apiPathCreator,
            jsonApiKtorClientCreator = jsonApiKtorClientCreator,
        )
    }

    @Provides
    fun bindsLoginDataSource(
        @ApplicationContext context: Context,
        jsonApiKtorClientCreator: JsonApiKtorClientCreator,
    ): LoginDataSource {
        return LoginDataSource(
            context = context,
            jsonApiKtorClientCreator = jsonApiKtorClientCreator
        )
    }
}