package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ceed.android.mylapslogger.R

@Composable
fun ListDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = colorResource(id = R.color.divider)
    )
}

@Composable
fun WaterMark () {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, end = 20.dp),
        textAlign = TextAlign.End,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        color = colorResource(id = R.color.text_water_mark),
        text = "Compose."
    )
}

@Composable
fun outLineTextFieldColors(): TextFieldColors {
    val focused = colorResource(id = R.color.text_default)
    val unFocused = colorResource(id = R.color.text_un_focused)
    return OutlinedTextFieldDefaults.colors(
        unfocusedLabelColor = unFocused,
        focusedLabelColor = focused,
        unfocusedBorderColor = unFocused,
        focusedBorderColor = focused,
        disabledLabelColor = unFocused,
        disabledBorderColor = unFocused,
        disabledTextColor = unFocused,
        unfocusedTextColor = unFocused,
        focusedTextColor = focused
    )
}

@Composable
fun CommonButton(
    label: String = "",
    enabled: Boolean = true,
    width: Int = 200,
    onClick: () -> Unit = {  }
) {
    Button(
        modifier = Modifier.width(width.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary)),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Text(text = label)
    }
}

@Composable
@Preview
fun CommonComposePreview() {
    WaterMark()
}