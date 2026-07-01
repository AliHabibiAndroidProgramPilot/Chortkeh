package info.alihabibi.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.alihabibi.ui.items.ListedBottomSheetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListedBottomSheet(
    items: List<T>,
    itemTitle: (T) -> String,
    itemIcon: (T) -> Int,
    itemKey: (T) -> Any,
    bottomSheetTitle: String = "",
    onAddNewItem: () -> Unit = {},
    onDismissRequest: () -> Unit,
    onSelectItem: (item: T) -> Unit
) {

    ModalBottomSheet(onDismissRequest = onDismissRequest) {

        Surface(modifier = Modifier.fillMaxWidth()) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = bottomSheetTitle,
                    style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(height = 40.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    items(
                        items = items,
                        key = { item -> itemKey(item) }
                    ) { item ->

                        ListedBottomSheetItem(
                            title = itemTitle(item),
                            iconResId = itemIcon(item),
                            onClick = { onSelectItem(item) }
                        )

                    }

                    item(key = "ADD_NEW_ITEM") {
                        //todo add new item composable here!!!! + onCLick
                    }

                }

            }

        }

    }

}