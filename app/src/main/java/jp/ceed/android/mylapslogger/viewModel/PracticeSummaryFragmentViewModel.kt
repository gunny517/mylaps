package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem

class PracticeSummaryFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var recyclerViewItem: MutableLiveData<List<PracticeResultsItem>> = MutableLiveData(mutableListOf())

}