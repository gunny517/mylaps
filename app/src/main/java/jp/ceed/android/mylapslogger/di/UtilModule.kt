package jp.ceed.android.mylapslogger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.android.mylapslogger.util.ExceptionUtil

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun bindsExceptionUtil(@ActivityContext context: Context): ExceptionUtil {
        return ExceptionUtil(context)
    }
}