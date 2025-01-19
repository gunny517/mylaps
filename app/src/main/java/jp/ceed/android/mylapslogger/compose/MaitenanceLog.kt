package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.model.MaintenanceLogList
import jp.ceed.android.mylapslogger.viewModel.MaintenanceLogComposeFragmentViewModel

@Composable
fun MaintenanceLogCompose(
    viewModel: MaintenanceLogComposeFragmentViewModel = viewModel()
) {
    MaintenanceLogCompose(
        maintenanceLogs = viewModel.maintenanceLogs,
        onClickFab = { viewModel.onClickFab() }
    )
}

@Composable
fun MaintenanceLogCompose(
    maintenanceLogs: MutableState<List<MaintenanceLogList>> = mutableStateOf(mutableListOf()),
    onClickFab: () -> Unit = {},
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Surface {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
//            TabRow(
//                selectedTabIndex = selectedTabIndex,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                maintenanceLogs.value.forEachIndexed { index, entity ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index },
//                    ) {
//                        Text(text = entity.name)
//                    }
//                }
//            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(32.dp),
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
                            MaintenanceLog(1, 1L, 10.0F, 1, "")
                        )
                    ),
                    MaintenanceLogList(
                        "２号機", listOf(
                            MaintenanceLog(1, 1L, 9.9F, 1, "")
                        )
                    )
                )
            )
        }
    )
}