package info.alihabibi.ui.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11

@Composable
fun ListedBottomSheetItem(
    title: String,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .height(height = 48.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End)
        )

        Icon(
            modifier = Modifier.padding(horizontal = 12.dp),
            painter = painterResource(iconResId),
            contentDescription = null
        )

    }

}

@Preview
@Composable
private fun ListedBottomSheetItemPreview() {

    ListedBottomSheetItem(
        title = "خوش گذرونی",
        iconResId = R.drawable.header_app_logo,
        onClick = {}
    )

}