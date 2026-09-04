package info.alihabibi.ui.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.Gray3
import info.alihabibi.designsystem.theme.Gray8

@Composable
fun AppSimpleListItem(
    title: String,
    startIcon: Painter,
    modifier: Modifier = Modifier,
    endIcon: Painter = painterResource(id = R.drawable.short_arrow_left),
    iconsTint: Color = Gray8,
    showDivider: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(12))
            .clickable(onClick = onClick, enabled = isEnabled)
            .alpha(alpha = if (isEnabled) 1f else 0.45f),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.size(20.dp),
            painter = endIcon,
            tint = iconsTint,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier.size(22.dp),
            tint = iconsTint,
            painter = startIcon,
            contentDescription = null
        )

    }

    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            thickness = 1.dp,
            color = Gray3
        )
    }

}

@Composable
fun AppDangerousListItem(
    title: String,
    startIcon: Painter,
    modifier: Modifier = Modifier,
    iconsTint: Color = ErrorRed,
    showDivider: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(12))
            .clickable(onClick = onClick, enabled = isEnabled)
            .alpha(alpha = if (isEnabled) 1f else 0.45f),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(color = ErrorRed),
            maxLines = 1
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier.size(22.dp),
            tint = iconsTint,
            painter = startIcon,
            contentDescription = null
        )

    }

    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            thickness = 1.dp,
            color = Gray3
        )
    }

}

@Composable
fun ListedChannelItem(
    title: String,
    subTitle: String = "",
    @DrawableRes iconResId: Int,
    trailingIcon: @Composable (RowScope.() -> Unit)? = null,
    onClick: () -> Unit
) {

    val shape = RoundedCornerShape(size = 10.dp)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Row(
            modifier = Modifier
                .height(height = 65.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .border(width = 1.dp, color = Gray3, shape = shape)
                .clip(shape = shape)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            trailingIcon?.invoke(this)

            Column(horizontalAlignment = Alignment.End) {

                Text(
                    modifier = Modifier.padding(end = 10.dp, bottom = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = "موجودی: $subTitle",
                    style = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End,
                        color = Gray8
                    )
                )

            }

            Icon(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(size = 28.dp),
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = Color.Unspecified
            )

        }

    }

}

@Preview
@Composable
private fun ItemsPreview() {

    AppSimpleListItem(
        title = "تنظیمات",
        startIcon = painterResource(id = R.drawable.close_circle),
        showDivider = true
    )

}