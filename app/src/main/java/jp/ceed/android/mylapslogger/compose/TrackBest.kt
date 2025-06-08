package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.viewModel.TrackBestComposeFragmentViewModel

@Composable
fun TrackBestCompose(
    viewModel: TrackBestComposeFragmentViewModel
) {
    TrackBest(
        items = viewModel.trackBestList.value,
        isRefreshing = viewModel.isRefreshing.value,
        onRefresh = { viewModel.loadTrackBest() },
        onClick = { viewModel.onClickItem(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackBest(
    items: List<PracticeTrack>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onClick: (PracticeTrack) -> Unit = {}
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = onRefresh) {
            LazyColumn {
                items(count = items.size) { index ->
                    TrackBestRow(
                        item = items[index],
                        onClick = onClick
                    )
                }
            }
        }
        WaterMark()
    }
}

@Composable
fun TrackBestRow(
    item: PracticeTrack,
    onClick: (PracticeTrack) -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.window_back_ground))
            .clickable { onClick(item) }
            .padding(8.dp)
    ) {
        Text(
            color = colorResource(id = R.color.text_default),
            fontSize = 16.sp,
            text = item.trackName
        )
        Row (
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = colorResource(id = R.color.text_default),
                fontSize = 16.sp,
                text = item.displayTime ?: ""
            )
            Text(
                modifier = Modifier.weight(1.0f),
                textAlign = TextAlign.End,
                color = colorResource(id = R.color.text_default),
                fontSize = 20.sp,
                text = item.bestLap
            )
        }
    }
    ListDivider()
}

@Preview
@Composable
fun TrackBestPreview() {
    TrackBest(items = listOf(
        PracticeTrack(
            activityId = 1L,
            trackId = 1,
            trackName = "オートパラダイス御殿場小山町大御神サーキット",
            lapCount = 25,
            bestLap = "42.328",
            startTime = "",
            displayTime = "2021-06-12(Sun)",
            endTime = "",
            totalTrainingTime = "",
            trackLength = 1003
        )
    ))
}