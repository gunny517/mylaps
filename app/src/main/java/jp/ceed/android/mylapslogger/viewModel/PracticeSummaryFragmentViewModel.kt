package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import javax.inject.Inject

@HiltViewModel
class PracticeSummaryFragmentViewModel @Inject constructor () : ViewModel() {

    var recyclerViewItem: MutableLiveData<List<PracticeResultsItem>> = MutableLiveData(mutableListOf())

}