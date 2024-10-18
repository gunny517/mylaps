package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.viewModel.PracticeResultComposeFragmentViewModel

@Composable
fun PracticeResultsCompose (
    viewModel: PracticeResultComposeFragmentViewModel = viewModel()
) {
    viewModel.practiceResult.value?.let {
        PracticeResults(
            practiceResult = it
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeResults (
    practiceResult: PracticeResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = { onRefresh() }) {
        LazyColumn {
            items (count = practiceResult.sessionData.size) { index ->
                PracticeRow(item = practiceResult.sessionData[index])
            }
        }
    }
}

@Composable
fun PracticeRow (
    item: PracticeResultsItem
) {
    when (item) {
        is PracticeResultsItem.Section -> SectionRow(item = item)
        is PracticeResultsItem.Lap -> LapRow(item = item)
        else -> {}
    }
}

@Composable
fun SectionRow(item: PracticeResultsItem.Section) {
    Row {

    }
}

@Composable
fun LapRow(item: PracticeResultsItem.Lap) {

}


