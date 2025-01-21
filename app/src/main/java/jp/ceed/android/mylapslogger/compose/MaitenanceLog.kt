package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.extensions.toYmdString
import jp.ceed.android.mylapslogger.model.MaintenanceLogList
import jp.ceed.android.mylapslogger.viewModel.MaintenanceLogComposeFragmentViewModel
import kotlinx.coroutines.launch

@Composable
fun MaintenanceLogCompose(
    viewModel: MaintenanceLogComposeFragmentViewModel = viewModel()
) {
    MaintenanceLogCompose(
        maintenanceLogs = viewModel.maintenanceLogs,
        onClickFab = { viewModel.onClickFab() },
        onClickItem = { viewModel.onClickLogItem(it) }
    )
}

@Composable
fun MaintenanceLogCompose(
    maintenanceLogs: MutableState<List<MaintenanceLogList>> = mutableStateOf(mutableListOf()),
    onClickFab: () -> Unit = {},
    onClickItem: (Int) -> Unit = {},
) {
    Surface {
        if (maintenanceLogs.value.isNotEmpty()) {
            TabContent(
                maintenanceLogs = maintenanceLogs,
                onClick = onClickItem
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(32.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            FloatingActionButton(
                onClick = { onClickFab() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    }
}

@Composable
fun TabContent(
    maintenanceLogs: MutableState<List<MaintenanceLogList>>,
    onClick: (Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(0) { maintenanceLogs.value.size }
    val coroutineScope = rememberCoroutineScope()
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = colorResource(id = R.color.bg_lap_list_section)
        ) {
            maintenanceLogs.value.forEachIndexed { index, entity ->
                Tab(
                    modifier = Modifier.height(48.dp),
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                ) {
                    Text(text = entity.name)
                }
            }
        }
        HorizontalPager(state = pagerState) { index ->
            val logList = maintenanceLogs.value[index]
            LazyColumn {
                items(count = logList.logs.size) { index ->
                    MaintenanceLogRow(
                        item = logList.logs[index],
                        onClick = onClick
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun MaintenanceLogRow(
    item: MaintenanceLog,
    onClick: (Int) -> Unit = {},
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.window_back_ground))
            .clickable { onClick(item.id) }
            .padding(8.dp)
    ) {
        Column {
            Text(
                color = colorResource(id = R.color.text_default),
                fontSize = 14.sp,
                text = item.issueDate.toYmdString()
            )
            Text(
                color = colorResource(id = R.color.text_default),
                fontSize = 14.sp,
                text = item.runningTime.toString()
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            color = colorResource(id = R.color.text_default),
            fontSize = 16.sp,
            text = item.descriptionOrEmptyString()
        )
    }
}

@Preview
@Composable
fun MaintenanceLogPreview() {
    MaintenanceLogCompose(
        maintenanceLogs = remember {
            mutableStateOf(
                listOf(
                    MaintenanceLogList(
                        "１号機",
                        listOf(
                            MaintenanceLog(1, 1737331200000L, 10.0F, 1, "ピストンリグ交換"),
                            MaintenanceLog(2, 1737331200000L, 10.0F, 1, "ピストンリグ交換"),
                            MaintenanceLog(3, 1737331200000L, 10.0F, 1, "ピストンリグ交換")
                        )
                    ),
                    MaintenanceLogList(
                        "２号機", listOf(
                            MaintenanceLog(4, 1737331200000L, 9.9F, 2, "カーボン除去"),
                            MaintenanceLog(5, 1737331200000L, 10.0F, 2, "ピストンリグ交換"),
                            MaintenanceLog(6, 1737331200000L, 10.0F, 2, "ピストンリグ交換"),
                            MaintenanceLog(7, 1737331200000L, 10.0F, 2, "ピストンリグ交換")
                        )
                    )
                )
            )
        }
    )
}