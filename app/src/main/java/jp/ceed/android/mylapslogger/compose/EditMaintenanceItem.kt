package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        items = viewModel.items,
        onClickSave = { inputValue -> viewModel.saveItem(inputValue) },
        onClickDelete = { entity -> viewModel.deleteItem(entity) }
    )
}

@Composable
fun EditMaintenanceItemContent(
    items: MutableState<List<MaintenanceItem>>,
    onClickSave: (MaintenanceItem) -> Unit = {},
    onClickDelete: (MaintenanceItem) -> Unit = {},
) {
    Column {
        items.value.forEach {
            MaintenanceItemRow(
                maintenanceItem = it,
                onClickSave = onClickSave,
                onClickDelete = onClickDelete
            )
        }
        MaintenanceItemRow(
            maintenanceItem = MaintenanceItem() ,
            onClickSave = { entity -> onClickSave(entity) },
            onClickDelete = onClickDelete
        )
    }
}

@Composable
fun MaintenanceItemRow (
    maintenanceItem: MaintenanceItem,
    onClickSave: (MaintenanceItem) -> Unit,
    onClickDelete: (MaintenanceItem) -> Unit = {},
) {
    val inputValue = remember {
        mutableStateOf(maintenanceItem.name)
    }
    val isItemCreate = maintenanceItem.id == 0
    val saveButtonLabel = if (isItemCreate) {
        stringResource(id = R.string.label_create)
    } else {
        stringResource(id = R.string.label_save)
    }
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.maintenance_item)) },
            modifier = Modifier
                .width(200.dp)
                .padding(all = 8.dp),
            placeholder = { Text(text = stringResource(id = R.string.maintenance_item)) },
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            colors = outLineTextFieldColors()
        )
        CommonButton(
            label = saveButtonLabel,
            width = 100,
            onClick = {
                maintenanceItem.name = inputValue.value
                onClickSave(maintenanceItem)
            }
        )
        if (!isItemCreate) {
            Spacer(modifier = Modifier.width(8.dp))
            CommonButton(
                label = stringResource(id = R.string.label_delete),
                width = 100,
                onClick = { onClickDelete(maintenanceItem) }
            )
        }
    }
}

@Preview
@Composable
fun EditMaintenanceItemPreview() {
    val items = listOf(
        MaintenanceItem(1, "エンジン１号機"),
        MaintenanceItem(2, "エンジン２号機"),
        MaintenanceItem(3, "エンジン３号機"),
    )
    EditMaintenanceItemContent(items = remember {
        mutableStateOf(items)
    })
}