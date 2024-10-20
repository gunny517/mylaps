package jp.ceed.android.mylapslogger.compose

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import jp.ceed.android.mylapslogger.R

@Composable
fun ListDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = colorResource(id = R.color.divider)
    )
}