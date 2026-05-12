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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.MoonRaker
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.buttons.AppOutlinedButton

@Composable
fun AppDialogs(
    title: String,
    message: String = "",
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirmClicked: () -> Unit,
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

@Composable
private fun DialogContent(
    title: String,
    message: String = "",
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirmClicked: () -> Unit,
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
            Box(modifier = Modifier.padding(20.dp)) {
                IconButton(
                    onClick = onDismissIconClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_circle),
                        tint = Gray9,
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
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
                            onClick = onDismissIconClick,
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
private fun DialogsPreview() {

    Column {

        AppDialogs(
            title = "خروج از برنامه",
            message = "مطمعنی میخوای خارج بشی؟",
            confirmButtonText = "انصراف",
            cancelButtonText = "خروج",
            onConfirmClicked = {},
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