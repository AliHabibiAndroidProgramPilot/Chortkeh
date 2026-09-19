package info.alihabibi.ui.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.NeutralGray

@Composable
fun AppFeatureBotton(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    subtitle: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 10),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        enabled = isEnabled,
        onClick = onClick
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(height = 12.dp))

            Icon(
                modifier = Modifier
                    .size(size = 38.dp)
                    .alpha(alpha = if (!isEnabled) 0.5f else 1f),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha = if (!isEnabled) 0.5f else 1f),
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .alpha(alpha = if (!isEnabled) 0.5f else 1f),
                text = subtitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.labelLarge.copy(color = Gray8, textAlign = TextAlign.Center)
            )

        }

    }

}