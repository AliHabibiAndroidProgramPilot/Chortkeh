package info.alihabibi.announcements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    announcements: List<String> = emptyList(),
    toggleItems: List<String>,
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader(
            title = stringResource(id = R.string.announcements),
            isMenuAvailable = false,
            onNavigationClick = onBackPressed
        )

        Spacer(Modifier.height(height = 8.dp))

        AppToggle(
            toggleItems = toggleItems,
            itemTitle = { it },
            selectedOption = toggleItems.first(),
            onToggleSelectionChanged = {}
        )

        Spacer(Modifier.height(height = 8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (announcements.isEmpty()) {
                item {
                    EmptyAnnouncementsPlaceholder()
                }
            } else {
                items(
                    items = announcements,
                    key = { it }
                ) { announcement ->
                    // AnnouncementItem(announcement)
                }
            }

        }

    }

}

@Composable
private fun EmptyAnnouncementsPlaceholder() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(id = R.drawable.empty_mailbox),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.empty_mail_box_message),
            style = MaterialTheme.typography.labelLarge.copy(color = Gray8)
        )

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