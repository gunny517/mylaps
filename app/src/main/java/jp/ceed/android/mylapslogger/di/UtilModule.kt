package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.dao.ErrorLogDao
import jp.ceed.android.mylapslogger.repository.AppInfoRepository
import jp.ceed.android.mylapslogger.repository.ErrorLogRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.util.ExceptionUtil

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun bindsErrorLogRepository(
        errorLogDao: ErrorLogDao,
    ): ErrorLogRepository {
        return ErrorLogRepository(errorLogDao)
    }

    @Provides
    fun bindsExceptionUtil(errorLogRepository: ErrorLogRepository): ExceptionUtil {
        return ExceptionUtil(errorLogRepository)
    }

    @Provides
    fun bindsAppSettings(@ApplicationContext context: Context): AppSettings {
        return AppSettings(context)
    }

    @Provides
    fun bindsAppInfoRepository(@ApplicationContext context: Context): AppInfoRepository {
        return AppInfoRepository(context)
    }
}