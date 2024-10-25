package jp.ceed.android.mylapslogger.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
@Preview
fun CommonComposePreview() {
    WaterMark()
}