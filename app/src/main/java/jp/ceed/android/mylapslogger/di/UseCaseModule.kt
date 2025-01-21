package jp.ceed.android.mylapslogger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import jp.ceed.android.mylapslogger.repository.MaintenanceItemRepository
import jp.ceed.android.mylapslogger.repository.MaintenanceLogRepository
import jp.ceed.android.mylapslogger.usecase.EditMaintenanceLogUseCase
import jp.ceed.android.mylapslogger.usecase.MaintenanceLogUseCase

@Module
@InstallIn(FragmentComponent::class)
object UseCaseModule {

    @Provides
    fun bindsMaintenanceLogUseCase(
        maintenanceItemRepository: MaintenanceItemRepository,
        maintenanceLogRepository: MaintenanceLogRepository
    ): MaintenanceLogUseCase {
        return MaintenanceLogUseCase(maintenanceItemRepository, maintenanceLogRepository)
    }

    @Provides
    fun bindsEditMaintenanceLogUseCase(
        maintenanceItemRepository: MaintenanceItemRepository,
        maintenanceLogRepository: MaintenanceLogRepository,
    ): EditMaintenanceLogUseCase {
        return EditMaintenanceLogUseCase(maintenanceItemRepository, maintenanceLogRepository)
    }
}