package info.alihabibi.ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.ui.items.ListedBottomSheetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListedBottomSheet(
    items: List<T>,
    itemTitle: (T) -> String,
    itemIcon: (T) -> Int,
    itemKey: (T) -> Any,
    bottomSheetTitle: String = "",
    addNewItemTitle: String = stringResource(id = R.string.add_new_category),
    onAddNewItem: () -> Unit = {},
    onDeleteItems: (itemsToDelete: List<T>) -> Unit = {},
    onEditItem: (item: T) -> Unit = {},
    onDismissRequest: () -> Unit,
    onSelectItem: (item: T) -> Unit
) {

    var selectionModeEnabled by remember { mutableStateOf(false) }
    val selectedKeys = remember { mutableStateSetOf<Any>() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = White,
        onDismissRequest = onDismissRequest
    ) {

        Surface(modifier = Modifier.fillMaxWidth()) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Center),
                        text = bottomSheetTitle,
                        style = MaterialTheme.typography.labelLarge.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AnimatedVisibility(
                            visible = selectionModeEnabled,
                            enter = fadeIn(animationSpec = tween(durationMillis = 200)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 200))
                        ) {

                            IconButton(
                                modifier = Modifier.size(size = 24.dp),
                                onClick = {
                                    val itemsToDelete = items.filter { itemKey(it) in selectedKeys }
                                    onDeleteItems(itemsToDelete)
                                    selectedKeys.clear()
                                    selectionModeEnabled = false
                                }
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = null,
                                    tint = Gray9
                                )

                            }

                        }

                        Spacer(modifier = Modifier.width(width = 12.dp))

                        AnimatedVisibility(
                            visible = selectedKeys.size == 1,
                            enter = fadeIn(animationSpec = tween(durationMillis = 200)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 200))
                        ) {

                            IconButton(
                                modifier = Modifier.size(size = 24.dp),
                                onClick = {
                                    onEditItem(items.first())
                                    selectedKeys.clear()
                                    selectionModeEnabled = false
                                }
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = null,
                                    tint = Gray9
                                )

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(height = 12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    items(
                        items = items,
                        key = { item -> itemKey(item) }
                    ) { item ->

                        val key = itemKey(item)
                        val isSelected = key in selectedKeys

                        ListedBottomSheetItem(
                            title = itemTitle(item),
                            iconResId = itemIcon(item),
                            isSelected = isSelected,
                            onClick = {
                                if (selectionModeEnabled) {
                                    if (isSelected)
                                        selectedKeys.remove(key)
                                    else
                                        selectedKeys.add(key)
                                    if (selectedKeys.isEmpty()) selectionModeEnabled = false
                                } else {
                                    onSelectItem(item)
                                }
                            },
                            onLongClick = {
                                selectionModeEnabled = true
                                selectedKeys.add(key)
                            }
                        )

                    }

                    item(key = "ADD_NEW") {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 50.dp)
                                .padding(horizontal = 16.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .clickable(onClick = onAddNewItem),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {

                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = addNewItemTitle,
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp)
                            )

                            Icon(
                                modifier = Modifier.padding(end = 12.dp),
                                painter = painterResource(id = R.drawable.add_square),
                                contentDescription = null,
                                tint = Primary
                            )

                        }

                    }

                }

            }

        }

    }

}