package info.alihabibi.ui.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun <T> AppToggle(
    modifier: Modifier = Modifier,
    toggleItems: List<T>,
    itemTitle: @Composable (T) -> String,
    selectedOption: T,
    onToggleSelectionChanged: (item: T) -> Unit
) {

    val selectedIndex = toggleItems.indexOf(selectedOption).coerceAtLeast(0)
    val shape = RoundedCornerShape(percent = 25)

    BoxWithConstraints(
        modifier = modifier
            .height(height = 45.dp)
            .padding(horizontal = 12.dp)
            .border(width = 1.2.dp, color = Gray7, shape = shape)
    ) {

        val totalWidth = maxWidth
        val itemCount = toggleItems.size.coerceAtLeast(1)
        val itemWidth = totalWidth / itemCount

        val targetOffset = itemWidth * selectedIndex

        val animatedOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = tween(durationMillis = 180),
            label = "indicatorOffset"
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(x = animatedOffset.roundToPx(), y = 0) }
                .width(itemWidth)
                .fillMaxHeight()
                .clip(shape)
                .background(Primary)
        )

        Row(modifier = Modifier.fillMaxSize()) {

            toggleItems.forEach { item ->
                val isSelected = selectedOption == item
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) White else Gray7,
                    animationSpec = tween(durationMillis = 180),
                    label = "textColor"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(shape)
                        .clickable { onToggleSelectionChanged(item) },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = itemTitle(item),
                        style = MaterialTheme.typography.labelMedium,
                        color = contentColor
                    )

                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppToggle2ItemsPreview() {
    AppToggle(
        toggleItems = listOf("بانکی", "چرتکه"),
        itemTitle = { it },
        selectedOption = "بانکی",
        onToggleSelectionChanged = {}
    )
}
