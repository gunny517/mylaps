package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.viewModel.SessionListComposeFragmentViewModel

@Composable
fun SessionListCompose(
    viewModel: SessionListComposeFragmentViewModel
) {
    SessionList(
        sessionList = viewModel.sessionItemList.value,
        onClick = { viewModel.onClickSession(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionList(
    sessionList: List<SessionListItem>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onClick: (SessionListItem) -> Unit = {}
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = onRefresh) {
            LazyColumn {
                items(count = sessionList.size) { index ->
                    SessionRow(item = sessionList[index], onClick = onClick)
                }
            }
        }
        WaterMark()
    }
}

@Composable
fun SessionRow (
    item: SessionListItem,
    onClick: (SessionListItem) -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text (
            modifier = Modifier.width(40.dp),
            color = colorResource(id = R.color.text_default),
            fontSize = 24.sp,
            text = item.no
        )
        Text(
            modifier = Modifier.weight(1.0f),
            fontSize = 24.sp,
            color = colorResource(id = R.color.text_default),
            text = item.startTime
        )
        Column (
            modifier = Modifier.width(80.dp)
        ) {
            Text (
                fontSize = 20.sp,
                color = colorResource(id = R.color.text_sub_item),
                text = stringResource(id = R.string.label_session_list_title_best_lap)
            )
            Text (
                fontSize = 20.sp,
                color = colorResource(id = R.color.text_sub_item),
                text = stringResource(id = R.string.label_session_list_title_lap_count)
            )
        }
        Column (
            modifier = Modifier.width(80.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = colorResource(id = item.bestLapTextColor),
                textAlign = TextAlign.End,
                text = item.bestLap
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = colorResource(id = R.color.text_default),
                textAlign = TextAlign.End,
                text = item.lapCount
            )
        }
    }
    ListDivider()
}


@Preview
@Composable
fun SessionListPreview() {
    SessionList(sessionList = listOf(
        SessionListItem(
            no = "1",
            startTime = "08:00",
            bestLap = "51.200",
            lapCount = "20",
            bestLapTextColor = R.color.text_session_list_best_lap
        )
    ))
}