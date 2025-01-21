package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceItemComposeViewModel

@Composable
fun EditMaintenanceItemCompose(
    viewModel: EditMaintenanceItemComposeViewModel = viewModel()
) {
    EditMaintenanceItemContent(
        entity = viewModel.entity.value,
        onClickSave = { inputValue -> viewModel.saveItem(inputValue) }
    )
}

@Composable
fun EditMaintenanceItemContent(
    entity: MaintenanceItem,
    onClickSave: (String) -> Unit = {}
) {
    var inputValue by remember {
        mutableStateOf(entity.name)
    }
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.maintenance_item)) },
            modifier = Modifier.padding(all = 8.dp),
            placeholder = { Text(text = stringResource(id = R.string.maintenance_item)) },
            value = inputValue,
            onValueChange = { inputValue = it },
            colors = outLineTextFieldColors()
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CommonButton(
                label = stringResource(id = R.string.label_save),
                onClick = { onClickSave(inputValue) }
            )
        }
    }

}

@Preview
@Composable
fun EditMaintenanceItemPreview() {
    EditMaintenanceItemContent(entity = MaintenanceItem(0, "エンジン１号機"))
}