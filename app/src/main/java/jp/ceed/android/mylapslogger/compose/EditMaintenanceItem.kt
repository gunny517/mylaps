package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceItemComposeViewModel

@Composable
fun EditMaintenanceItemCompose(
    viewModel: EditMaintenanceItemComposeViewModel = viewModel()
) {
    EditMaintenanceItem(
        entity = viewModel.entity.value,
        onClickSave = { viewModel.saveItem() }
    )
}

@Composable
fun EditMaintenanceItem(
    entity: MaintenanceItem,
    onClickSave: () -> Unit = {}
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = entity.name,
            onValueChange = { entity.name = it }
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onClickSave() }
            ) {
                Text(text = "保存")
            }
        }
    }

}

@Preview
@Composable
fun EditMaintenanceItemPreview() {
    EditMaintenanceItem(entity = MaintenanceItem(0, "エンジン１号機"))
}