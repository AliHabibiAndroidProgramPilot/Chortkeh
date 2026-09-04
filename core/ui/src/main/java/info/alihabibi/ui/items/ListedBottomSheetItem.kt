package info.alihabibi.ui.items

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.theme.Gray3
import info.alihabibi.designsystem.theme.Primary

@Composable
fun ListedBottomSheetItem(
    title: String,
    @DrawableRes iconResId: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Primary else Gray3,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        )
    )

    val shape = RoundedCornerShape(size = 12.dp)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Row(
            modifier = Modifier
                .height(height = 55.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .border(width = 1.dp, color = borderColor, shape = shape)
                .clip(shape = shape)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
            )

            Icon(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = Color.Unspecified
            )

        }

    }

}