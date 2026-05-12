package info.alihabibi.announcements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader

@Composable
fun AnnouncementsDestination(
    onBackPressed: () -> Unit
) {

    val toggleItems: List<String> = listOf(
        stringResource(id = R.string.inApp),
        stringResource(id = R.string.bank)
    )
    AnnouncementsScreen(
        toggleItems = toggleItems,
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AnnouncementsScreen(
    toggleItems: List<String>,
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader(
            onNavigationClick = onBackPressed,
            isMenuAvailable = false,
            title = stringResource(id = R.string.announcements)
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))

        AppToggle(
            toggleItems = toggleItems,
            itemTitle = { it },
            onToggleSelectionChanged = {}
        )

        Spacer(modifier = Modifier.padding(top = 130.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier.size(180.dp),
                painter = painterResource(id = R.drawable.empty_mailbox),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )

            Text(
                text = stringResource(id = R.string.empty_mail_box_message),
                style = MaterialTheme.typography.labelLarge.copy(color = Gray8)
            )

        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun AnnouncementsPreview() {

    AnnouncementsScreen(
        toggleItems = listOf("درون برنامه", "بانک"),
        onBackPressed = {}
    )

}