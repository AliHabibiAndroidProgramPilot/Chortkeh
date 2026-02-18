package info.alihabibi.ui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Primary,
    contentColor: Color = White,
    text: String = "",
    startIcon: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = contentColor),
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            startIcon?.invoke()

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )

            endIcon?.invoke()

        }

    }

}

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color? = null,
    borderColor: Color = Primary,
    text: String = "",
    startIcon: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
) {

    val buttonColors = if (color != null) {
        ButtonDefaults.outlinedButtonColors(
            containerColor = color,
            contentColor = borderColor
        )
    } else {
        ButtonDefaults.outlinedButtonColors(
            contentColor = borderColor
        )
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        colors = buttonColors,
        border = BorderStroke(width = 2.dp, color = borderColor),
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            startIcon?.invoke()

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )

            endIcon?.invoke()

        }

    }

}

@Preview
@Composable
private fun ButtonsPreview() {

    Column {

        AppButton(onClick = {})

        AppOutlinedButton(onClick = {})

    }

}