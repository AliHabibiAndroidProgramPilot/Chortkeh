package info.alihabibi.ui.snackbars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.White

@Composable
fun AppSnackBar(
    description: String,
    onUndo: () -> Unit = {}
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Snackbar(
            modifier = Modifier.padding(8.dp),
            actionContentColor = White,
            contentColor = White,
            action = {
                Row(
                    modifier = Modifier
                        .clickable { onUndo() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        painter = painterResource(id = R.drawable.undo),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        text = stringResource(id = R.string.undo),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

@Preview
@Composable
fun SnackBarPreview() {

    AppSnackBar(description = "تراکنش ثبت شد")

}