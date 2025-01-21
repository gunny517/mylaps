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
fun OutLineTextFieldColors(): TextFieldColors {
    val defaultTextColor = colorResource(id = R.color.text_default)
    return OutlinedTextFieldDefaults.colors(
        unfocusedLabelColor = defaultTextColor,
        focusedLabelColor = defaultTextColor,
        disabledLabelColor = defaultTextColor,
        disabledBorderColor = defaultTextColor,
        disabledTextColor = defaultTextColor,
        unfocusedTextColor = defaultTextColor,
        focusedTextColor = defaultTextColor
    )
}

@Composable
fun CommonButton(
    label: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit = {  }
) {
    Button(
        modifier = Modifier.width(200.dp),
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