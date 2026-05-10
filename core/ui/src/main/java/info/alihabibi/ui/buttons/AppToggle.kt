package info.alihabibi.ui.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun <T> AppToggle(
//    modifier: Modifier = Modifier,
    toggleItems: List<T>,
    itemTitle: (T) -> String,
    onToggleSelectionChanged: (item: T) -> Unit
) {

    var selectedIndex by remember { mutableIntStateOf(toggleItems.indices.first) }

    val shape = RoundedCornerShape(20)
    val selectedColor = Primary
    val unselectedColor = Gray7

    Row(
        modifier = Modifier.height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        toggleItems.forEachIndexed { index, item ->
            Button(
                onClick = {
                    selectedIndex = index
                    onToggleSelectionChanged(item)
                },
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedIndex == index) selectedColor else unselectedColor,
                    contentColor = if (selectedIndex == index) White else unselectedColor
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = itemTitle(item),
                    style = MaterialTheme.typography.labelMedium
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