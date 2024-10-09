package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel

@Composable
fun ActivitiesCompose (
    viewModel: ActivitiesFragmentViewModel = viewModel()
) {
    Activities(
        activities = viewModel.activities.value,
        onClick = {
            viewModel.onClickItem(it)
        }
    )
}

@Composable
fun Activities (
    activities: List<ActivitiesItem>,
    onClick: (ActivitiesItem) -> Unit = {},
) {
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
            modifier = Modifier.align(Alignment.Start),
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_default),
            text = item.displayTimeOrEmpty()
        )
        Text(
            modifier = Modifier.align(Alignment.Start),
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_default),
            text = item.place
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    Activities(
        activities =
        listOf(
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
            ActivitiesItem(0, 0, "2024-01-01（月）", "", "", "オートパラダイス御殿場", 1000),
        )
    )
}