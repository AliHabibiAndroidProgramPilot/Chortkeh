package info.alihabibi.ui.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8

@Composable
fun TransactionItem(
    title: String,
    transactionAmount: String,
    subTitle: String = "",
    @DrawableRes iconResId: Int,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {

    val shape = RoundedCornerShape(size = 10.dp)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Row(
            modifier = Modifier
                .height(height = 65.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant, shape = shape)
                .clip(shape = shape)
                .clickable(enabled = clickable, onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 3.dp, alignment = Alignment.CenterHorizontally)
            ) {

                Text(
                    text = stringResource(id = R.string.toman),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Gray8,
                        fontSize = 16.sp
                    )
                )

                Text(
                    text = transactionAmount,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                )

            }

            Spacer(modifier = Modifier.weight(weight = 1f))

            Column(horizontalAlignment = Alignment.End) {

                Text(
                    modifier = Modifier.padding(end = 10.dp, bottom = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = subTitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End,
                        color = Gray8
                    )
                )

            }

            Icon(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(size = 28.dp),
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = Color.Unspecified
            )

        }

    }

}