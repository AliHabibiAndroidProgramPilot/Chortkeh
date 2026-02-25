package info.alihabibi.ui.headrs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ChortkehTheme
import info.alihabibi.designsystem.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageHeader(
    isBadgeAvailable: Boolean,
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(id = R.drawable.message),
    onNavigationClick: () -> Unit = {}
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        title = {},
        navigationIcon = {
            Box(contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = onNavigationClick
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
                if (isBadgeAvailable) {
                    Box(
                        modifier = Modifier.size(26.dp).zIndex(10f),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, shape = CircleShape)
                        )
                    }
                }
            }
        },
        actions = {
            Icon(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(id = R.drawable.header_app_logo),
                tint = Primary,
                contentDescription = null
            )
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    modifier: Modifier= Modifier,
    title: String,
    onNavigationClick: () -> Unit = {}
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(id = R.drawable.kebab_menu),
                contentDescription = null
            )
        },
        actions = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = null
                )
            }
        }
    )

}

@Preview
@Composable
fun HeadersPreview() {

    ChortkehTheme {

        Column {

            HomePageHeader(isBadgeAvailable = true)

            Spacer(modifier = Modifier.height(30.dp))

            AppHeader(title = "خرید موبایل")

        }

    }

}
