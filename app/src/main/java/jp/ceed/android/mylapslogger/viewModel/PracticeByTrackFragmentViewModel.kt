package jp.ceed.android.mylapslogger.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.PracticeByTrackFragmentArgs
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PracticeByTrackFragmentViewModel(args: PracticeByTrackFragmentArgs, context: Context) : ViewModel() {

    @Inject lateinit var practiceTrackRepository: PracticeTrackRepository

    val practiceTrackList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()


    init {
        viewModelScope.launch {
            practiceTrackList.value = practiceTrackRepository.getPracticeListByTrack(args.trackId)
        }
    }

    class Factory(val args: PracticeByTrackFragmentArgs, val context: Context): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeByTrackFragmentViewModel(args, context) as T
        }

    }
}