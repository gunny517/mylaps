package jp.ceed.android.mylapslogger.compose

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.ceed.android.mylapslogger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDatePickerDialog(
    selectedDateMillis: Long? = null,
    onDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val datePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.label_ok))
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DatePickerPreview() {
    CommonDatePickerDialog()
}