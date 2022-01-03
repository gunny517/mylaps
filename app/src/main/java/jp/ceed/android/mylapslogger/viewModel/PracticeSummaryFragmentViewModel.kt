package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.ceed.android.mylapslogger.dto.LapDto

class PracticeSummaryFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var recyclerViewItem: MutableLiveData<List<LapDto>> = MutableLiveData(mutableListOf())

}