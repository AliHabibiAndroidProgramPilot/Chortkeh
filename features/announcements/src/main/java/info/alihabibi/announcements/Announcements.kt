package info.alihabibi.announcements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.model.ui_model.announcement.AnnouncementTypeOptionUiModel
import info.alihabibi.model.ui_model.announcement.AnnouncementUiModel
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader

@Composable
fun AnnouncementsDestination(
    onBackPressed: () -> Unit
) {

    var selectedOption by remember {
        mutableStateOf(AnnouncementTypeOptionUiModel.IN_APP)
    }

    AnnouncementsScreen(
        announcements = emptyList(),
        selectedOption = selectedOption,
        onOptionChanged = { selectedOption = it },
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AnnouncementsScreen(
    announcements: List<AnnouncementUiModel>,
    selectedOption: AnnouncementTypeOptionUiModel,
    onOptionChanged: (AnnouncementTypeOptionUiModel) -> Unit,
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.announcements),
                isActionAvailable = false,
                onNavigationClicked = onBackPressed
            )

            Spacer(Modifier.height(height = 8.dp))

            AppToggle(
                toggleItems = AnnouncementTypeOptionUiModel.entries.toList(),
                itemTitle = { stringResource(id = it.labelRes) },
                selectedOption = selectedOption,
                onToggleSelectionChanged = onOptionChanged
            )

            Spacer(Modifier.height(height = 8.dp))

            if (announcements.isEmpty())
                EmptyAnnouncementState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            else
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        items = announcements,
                        key = { it.id }
                    ) { announcement ->
                        // AnnouncementItem(announcement)
                    }
                }

        }

    }

}

@Composable
private fun EmptyAnnouncementState(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {

            Image(
                modifier = Modifier.size(180.dp),
                painter = painterResource(id = R.drawable.empty_mailbox),
                contentDescription = null,
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
        announcements = emptyList(),
        selectedOption = AnnouncementTypeOptionUiModel.IN_APP,
        onOptionChanged = {},
        onBackPressed = {}
    )

}