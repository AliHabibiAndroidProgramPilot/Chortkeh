package info.alihabibi.ui.items

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8
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
        targetValue = if (isSelected) Primary else MaterialTheme.colorScheme.outlineVariant,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        )
    )

    val shape = RoundedCornerShape(size = 12.dp)

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

@Composable
fun SmsListItem(
    title: String,
    description: String,
    onItemClicked: (selected: String) -> Unit
) {

    Row(
        modifier = Modifier
            .height(height = 65.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant, shape = RoundedCornerShape(size = 12.dp))
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .clickable {
                onItemClicked(title)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Icon(
            modifier = Modifier.padding(start = 12.dp),
            painter = painterResource(id = R.drawable.sms_incoming),
            contentDescription = null,
            tint = Gray8
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(weight = 1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textDirection = TextDirection.Ltr
                )
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge.copy(
                    textAlign = TextAlign.Left
                )
            )

        }

    }

}