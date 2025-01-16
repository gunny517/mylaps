package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.repository.MaintenanceLogRepository
import javax.inject.Inject

@HiltViewModel
class MaintenanceLogComposeFragmentViewModel @Inject constructor (
    state: SavedStateHandle,
    repository: MaintenanceLogRepository,
): ViewModel() {

}