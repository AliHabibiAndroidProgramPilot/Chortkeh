package info.alihabibi.ui.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun <T> AppToggle(
    toggleItems: List<T>,
    itemTitle: (T) -> String,
    onToggleSelectionChanged: (item: T) -> Unit
) {

    var selectedIndex by remember { mutableIntStateOf(toggleItems.indices.first) }

    val shape = RoundedCornerShape(20)
    val selectedColor = Primary
    val unselectedColor = Gray7

    Row(
        modifier = Modifier.height(height = 45.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        toggleItems.forEachIndexed { index, item ->

            val containerColor by animateColorAsState(
                targetValue = if (selectedIndex == index) selectedColor else Color.Transparent,
                animationSpec = tween(easing = FastOutLinearInEasing, durationMillis = 180)
            )

            val contentColor by animateColorAsState(
                targetValue = if (selectedIndex == index) White else unselectedColor,
                animationSpec = tween(easing = FastOutLinearInEasing, durationMillis = 180)
            )

            val borderColor by animateColorAsState(
                targetValue = if (selectedIndex == index) Color.Transparent else unselectedColor,
                animationSpec = tween(easing = FastOutLinearInEasing, durationMillis = 180)
            )

            val boxOffset = when (index) {
                0 -> 15.dp / 2
                else -> -(15.dp / 2)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .offset(x = boxOffset)
                    .zIndex(zIndex = if (selectedIndex == index) 1f else 0f)
                    .padding(
                        start = if (index == 0) 14.dp else 0.dp,
                        end = if (index == toggleItems.lastIndex) 14.dp else 0.dp
                    )
                    .border(
                        width = 1.5.dp,
                        color = borderColor,
                        shape = shape
                    )
                    .clip(shape)
                    .background(color = containerColor)
                    .clickable {
                        selectedIndex = index
                        onToggleSelectionChanged(item)
                    },
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


@Preview
@Composable
private fun AppTogglePreview() {

    AppToggle(
        toggleItems = listOf("بانکی", "چرتکه"),
        itemTitle = { it },
        onToggleSelectionChanged = {}
    )

}