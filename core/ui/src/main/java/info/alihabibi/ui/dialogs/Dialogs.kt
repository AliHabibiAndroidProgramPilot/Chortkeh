package info.alihabibi.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ErrorExtraRedLight
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.Gray1
import info.alihabibi.designsystem.theme.Gray3
import info.alihabibi.designsystem.theme.Gray6
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.MoonRaker
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.buttons.AppOutlinedButton

@Composable
fun AppDialog(
    title: String,
    message: String = "",
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirmClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {


    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            DialogContent(
                title = title,
                message = message,
                confirmButtonText = confirmButtonText,
                cancelButtonText = cancelButtonText,
                onConfirmClicked = onConfirmClicked,
                onCancelClicked = onCancelClicked,
                onDismissIconClick = onDismissRequest
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSimpleBottomSheet(
    title: String,
    message: String = "",
    confirmButtonText: String,
    onConfirmClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        tonalElevation = 10.dp,
        containerColor = Gray1,
        onDismissRequest = onDismissRequest
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(200.dp)
                .padding(horizontal = 24.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Justify)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                onClick = onConfirmClicked,
                contentColor = Primary,
                text = confirmButtonText,
                color = MoonRaker
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppRadioSelectionBottomSheet(
    title: String = "",
    radioOptions: List<T>,
    selectedOption: T?,
    disabledIndex: Int? = null,
    optionLabel: @Composable (T) -> String,
    onRadioOptionSelected: (T) -> Unit,
    onConfirmClicked: () -> Unit = {},
    onDismissRequest: () -> Unit
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Gray1,
            onDismissRequest = onDismissRequest
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            )

            LazyColumn {

                itemsIndexed(radioOptions) { index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = selectedOption == item,
                            onClick = { onRadioOptionSelected(item) },
                            enabled = index != disabledIndex,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Primary,
                                unselectedColor = Gray6
                            )
                        )

                        Text(
                            modifier = Modifier.alpha(alpha = if (index != disabledIndex) 1f else 0.45f),
                            text = optionLabel(item),
                            style = MaterialTheme.typography.labelLarge
                        )

                    }

                    if (index != radioOptions.lastIndex) {
                        HorizontalDivider(color = Gray3)
                    }

                }

            }

            AppButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                onClick = onConfirmClicked,
                color = MoonRaker,
                contentColor = Primary,
                text = stringResource(id = R.string.confirm)
            )

        }

    }

}


@Composable
private fun DialogContent(
    title: String,
    message: String = "",
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirmClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onDismissIconClick: () -> Unit
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .widthIn(max = 360.dp),
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 20.dp,
            color = White
        ) {

            Box(
                modifier = Modifier.padding(20.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            IconButton(
                                onClick = onDismissIconClick,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterStart)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.close_circle),
                                    tint = Gray9,
                                    contentDescription = null
                                )

                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AppOutlinedButton(
                            modifier = Modifier.width(140.dp),
                            onClick = onConfirmClicked,
                            text = confirmButtonText,
                            borderColor = Primary
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        AppOutlinedButton(
                            modifier = Modifier.width(140.dp),
                            onClick = onCancelClicked,
                            text = cancelButtonText,
                            color = ErrorExtraRedLight,
                            borderColor = ErrorRed
                        )

                    }

                }

            }

        }

    }

}

@Preview
@Composable
private fun DialogPreview() {

    Column {

        AppDialog(
            title = "خروج از برنامه",
            message = "مطمعنی میخوای خارج بشی؟",
            confirmButtonText = "انصراف",
            cancelButtonText = "خروج",
            onConfirmClicked = {},
            onCancelClicked = {},
            onDismissRequest = {}
        )

        Spacer(Modifier.height(30.dp))

        AppSimpleBottomSheet(
            title = "",
            message = "",
            confirmButtonText = "",
            onConfirmClicked = {},
            onDismissRequest = {}
        )

    }

}