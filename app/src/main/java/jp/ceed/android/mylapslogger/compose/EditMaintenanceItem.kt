package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceItemComposeViewModel

@Composable
fun EditMaintenanceItemCompose(
    viewModel: EditMaintenanceItemComposeViewModel = viewModel()
) {
    EditMaintenanceItem(
        entity = viewModel.entity.value,
        onClickSave = { inputValue -> viewModel.saveItem(inputValue) }
    )
}

@Composable
fun EditMaintenanceItem(
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
            modifier = Modifier.padding(all = 8.dp),
            placeholder = { Text(text = "エンジン") },
            value = inputValue,
            onValueChange = { inputValue = it },
            colors = OutlinedTextFieldDefaults.colors(

            )
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                modifier = Modifier.width(100.dp),
                onClick = { onClickSave(inputValue) }
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