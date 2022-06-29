package jp.ceed.android.mylapslogger.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository


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

}