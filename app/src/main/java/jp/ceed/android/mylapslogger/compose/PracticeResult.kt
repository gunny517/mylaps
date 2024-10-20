package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.dto.Status
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.viewModel.PracticeResultComposeFragmentViewModel
import java.util.Locale

@Composable
fun PracticeResultsCompose (
    viewModel: PracticeResultComposeFragmentViewModel = viewModel()
) {
    viewModel.practiceResult.value?.let {
        PracticeResults(
            practiceResult = it,
            isRefreshing = viewModel.showProgress.value,
            onRefresh = { viewModel.loadPracticeResult() },
            onClick = { section -> viewModel.onClickSection(section) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeResults (
    practiceResult: PracticeResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onClick: (PracticeResultsItem.Section) -> Unit = {}
) {
    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = { onRefresh() }) {
        LazyColumn {
            items (count = practiceResult.sessionData.size) { index ->
                PracticeRow(
                    item = practiceResult.sessionData[index],
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun PracticeRow (
    item: PracticeResultsItem,
    onClick: (PracticeResultsItem.Section) -> Unit
) {
    when (item) {
        is PracticeResultsItem.Section -> SectionRow(item = item, onClick = onClick)
        is PracticeResultsItem.Lap -> LapRow(item = item)
        else -> {}
    }
}

@Composable
fun SectionRow(
    item: PracticeResultsItem.Section,
    onClick: (PracticeResultsItem.Section) -> Unit
) {
    val title = String.format(Locale.JAPANESE, stringResource(id = R.string.format_session_title), item.sectionTitle)
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) }
            .background(colorResource(id = R.color.bg_lap_list_section))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            fontSize = 18.sp,
            text = title
        )
        Text(
            modifier = Modifier.padding(start = 30.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.End,
            text = item.sessionTime
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                fontSize = 16.sp,
                text = stringResource(id = R.string.label_practice_result_section_session_info)
            )
        }
    }
}

@Composable
fun LapRow(item: PracticeResultsItem.Lap) {
    Row (
        modifier = Modifier
            .background(colorResource(id = item.cellBgColor))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(48.dp),
            color = colorResource(id = R.color.text_default),
            fontSize = 16.sp,
            text = item.number
        )
        Text(
            color = colorResource(id = R.color.text_default),
            fontSize = 28.sp,
            text = item.duration
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                color = colorResource(id = item.diffTextColor),
                fontSize = 20.sp,
                text = item.diffPrevLap ?: ""
            )
        }
    }
    HorizontalDivider(
        thickness = 0.5.dp,
        color = colorResource(id = R.color.divider)
    )
}

@Composable
@Preview(showBackground = true)
fun PracticeResultsPreview () {
    PracticeResults(
        practiceResult = PracticeResult(
            totalLap = "",
            bestLap = "",
            totalTime = "",
            totalDistance = "",
            dateStartTime = "",
            sessionData = listOf(
                PracticeResultsItem.Section(
                    sessionId = 1,
                    sectionTitle = "1",
                    sessionTime = "08:34",
                    medianDuration = "",
                    averageDuration = ""
                ),
                PracticeResultsItem.Lap(
                    sessionId = 1,
                    number = "1",
                    duration = "53.567",
                    status = Status.NONE,
                    diffPrevLap = "-0.11",
                    cellBgColor = R.color.window_back_ground,
                    diffTextColor = R.color.text_faster
                )
            ),
            sessionSummary = listOf()
        )
    )
}
