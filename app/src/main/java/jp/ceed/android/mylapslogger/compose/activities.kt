package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.viewModel.ActivitiesComposeFragmentViewModel

@Composable
fun ActivitiesCompose (
    viewModel: ActivitiesComposeFragmentViewModel = viewModel()
) {
    Activities(
        activities = viewModel.activities.value,
        onClick = {
            viewModel.onClickItem(it)
        },
        isRefreshing = viewModel.showProgress.value,
        onRefresh = viewModel::checkAccount
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activities (
    activities: List<ActivitiesItem>,
    onClick: (ActivitiesItem) -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = { onRefresh() }) {
        LazyColumn {
            items(count = activities.size) { index ->
                val item = activities[index]
                ActivityRow(
                    item = item,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun ActivityRow (
    item: ActivitiesItem,
    onClick: (ActivitiesItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(item) }
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .align(Alignment.Start),
            fontSize = 20.sp,
            color = colorResource(id = R.color.text_default),
            text = item.displayTimeOrEmpty()
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.End),
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_default),
            text = item.place
        )
    }
    HorizontalDivider(
        thickness = 0.5.dp,
        color = colorResource(id = R.color.divider)
    )
}

@Composable
@Preview(showBackground = true)
fun ActivitiesPreview() {
    Activities(
        activities =
        listOf(
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
        )
    )
}