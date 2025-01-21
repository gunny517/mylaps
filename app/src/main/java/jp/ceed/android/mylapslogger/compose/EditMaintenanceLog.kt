package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Surface
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.extensions.toYmdString
import jp.ceed.android.mylapslogger.viewModel.EditMaintenanceLogComposeFragmentViewModel

@Composable
fun EditMaintenanceLogCompose(
    viewModel: EditMaintenanceLogComposeFragmentViewModel = viewModel()
) {
    EditMaintenanceLogCompose(
        issueDate = viewModel.issueDate,
        runningTime = viewModel.runningTime,
        itemId = viewModel.itemId,
        description = viewModel.description,
        itemName = viewModel.itemName,
        logItems = viewModel.logItems,
        onClickSave = { viewModel.onClickSave() }
    )
}

@Composable
fun EditMaintenanceLogCompose(
    issueDate: MutableState<Long> = mutableLongStateOf(0L),
    runningTime: MutableState<String> = mutableStateOf(""),
    itemId: MutableState<Int> = mutableIntStateOf(0),
    description: MutableState<String> = mutableStateOf(""),
    itemName: MutableState<String> = mutableStateOf(""),
    logItems: MutableState<List<MaintenanceItem>> = mutableStateOf(listOf()),
    onClickSave: () -> Unit = {  },
) {
    val datePickerState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val dropDownExpanded: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val isValidValues: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    fun validateValues() {
        var isValid = true
        try {
            runningTime.value.toFloat()
        } catch (e: NumberFormatException) {
            isValid = false
        }
        isValidValues.value = isValid
    }

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = colorResource(id = R.color.window_back_ground)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // 実施日
            ItemInputBox(
                label = stringResource(id = R.string.maintenance_log_issue_date),
                value = issueDate.value.toYmdString(),
                enabled = false,
                onClick = { datePickerState.value = true }
            )
            // ランニングタイム
            ItemInputBox(
                label = stringResource(id = R.string.running_time),
                value = runningTime.value,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    runningTime.value = it
                    validateValues()
                }
            )
            // アイテム
            ItemInputBox(
                value = itemName.value,
                enabled = false,
                label = stringResource(id = R.string.maintenance_item),
                onClick = { dropDownExpanded.value = true }
            )
            // 詳細
            ItemInputBox(
                label = stringResource(id = R.string.maintenance_log_description),
                value = description.value ?: "",
                minLines = 4,
                onValueChange = { description.value = it }
            )
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary)),
                    enabled = isValidValues.value,
                    onClick = {
                        onClickSave()
                    }
                ) {
                    Text(text = stringResource(id = R.string.label_save))
                }
            }
            // ドロップダウンメニュー
            DropdownMenu(
                expanded = dropDownExpanded.value,
                onDismissRequest = { dropDownExpanded.value = false }
            ) {
                logItems.value.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            itemName.value = item.name
                            itemId.value = item.id
                            dropDownExpanded.value = false
                        }
                    )
                }
            }
        }
        // デートピッカー
        if (datePickerState.value) {
            CommonDatePickerDialog(
                selectedDateMillis = System.currentTimeMillis(),
                onDateSelected = { issueDate.value = it ?: 0 },
                onDismiss = { datePickerState.value = false }
            )
        }
    }
}

@Composable
fun ItemInputBox(
    label: String,
    value: String,
    enabled: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    Row (
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            label = { Text(text = label) },
            enabled = enabled,
            value = value,
            minLines = minLines,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(0.dp)
                .clickable { onClick() },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = colorResource(id = R.color.text_default),
                disabledTextColor = colorResource(id = R.color.text_disabled)
            )
        )
    }
}

@Preview
@Composable
fun EditMaintenanceLogPreview() {
    EditMaintenanceLogCompose()
}