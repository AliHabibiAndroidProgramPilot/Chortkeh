package info.alihabibi.announcements

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.model.ui_model.announcement.AnnouncementTypeOptionUiModel
import info.alihabibi.model.ui_model.announcement.AnnouncementUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.items.ListedAnnouncementItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnnouncementsDestination(
    viewModel: AnnouncementViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val bankSMSAnnouncements by viewModel.bankSMSAnnouncements.collectAsStateWithLifecycle()
    SideEffect(Unit) {
        viewModel.onEvent(AnnouncementUiIntent.ClearRedBadge)
    }

    var selectedOption by remember {
        mutableStateOf(AnnouncementTypeOptionUiModel.BANK_SMS)
    }

    AnnouncementsScreen(
        inAppAnnouncements = emptyList(),
        bankSMSAnnouncements = bankSMSAnnouncements,
        selectedOption = selectedOption,
        onOptionChanged = { selectedOption = it },
        onDeleteAnnouncement = { id ->
            viewModel.onEvent(AnnouncementUiIntent.DeleteAnnouncement(id))
        },
        onRegisterAnnouncement = { announcementsModule ->

        },
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AnnouncementsScreen(
    inAppAnnouncements: List<AnnouncementUiModel>,
    bankSMSAnnouncements: List<AnnouncementUiModel>,
    selectedOption: AnnouncementTypeOptionUiModel,
    onOptionChanged: (AnnouncementTypeOptionUiModel) -> Unit,
    onDeleteAnnouncement: (announcementId: Long) -> Unit = {},
    onRegisterAnnouncement: (announcement: AnnouncementUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = selectedOption.ordinal,
        pageCount = { AnnouncementTypeOptionUiModel.entries.size }
    )

    LaunchedEffect(selectedOption) {
        if (pagerState.currentPage != selectedOption.ordinal) {
            pagerState.animateScrollToPage(selectedOption.ordinal)
        }
    }

    val smsSheetState = rememberBankSmsSheetState()
    if (smsSheetState.isSheetVisible)
        BankSmsActionBottomSheet(
            state = smsSheetState,
            onRegisterClick = {
                smsSheetState.selectedAnnouncement?.let { onRegisterAnnouncement(it) }
            },
            onDeleteClick = {
                smsSheetState.selectedAnnouncement?.let { onDeleteAnnouncement(it.id) }
            }
        )

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

            HorizontalPager(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth(),
                state = pagerState,
                userScrollEnabled = false,
                key = { it }
            ) { page ->

                AnnouncementsPage(
                    onAnnouncementClick = smsSheetState::showSheet,
                    announcements = if (page == AnnouncementTypeOptionUiModel.IN_APP.ordinal)
                        inAppAnnouncements
                    else
                        bankSMSAnnouncements
                )

            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnnouncementsPage(
    modifier: Modifier = Modifier,
    announcements: List<AnnouncementUiModel>,
    onAnnouncementClick: (announcement: AnnouncementUiModel) -> Unit = {}
) {

    if (announcements.isEmpty())
        EmptyAnnouncementState(modifier = modifier.fillMaxSize())
    else
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = announcements,
                key = { it.id }
            ) { announcement ->
                ListedAnnouncementItem(
                    title = announcement.title,
                    subTitle = announcement.description,
                    iconResId = announcement.transaction?.channel?.icon?.iconResId
                        ?: R.drawable.channel_ic_default,
                    onClick = {
                        onAnnouncementClick(announcement)
                    }
                )
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
            modifier = Modifier.align(Alignment.Center),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BankSmsActionBottomSheet(
    state: BankSmsSheetState,
    onRegisterClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = state::onDismissed,
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = state.sheetState
    ) {

        Surface(modifier = Modifier.fillMaxWidth()) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.bank_sms),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                Row(
                    modifier = Modifier
                        .height(height = 55.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .clickable {
                            onRegisterClick()
                            state.hideSheet()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = stringResource(id = R.string.register_transaction),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.End,
                            fontSize = 14.sp
                        )
                    )

                    Icon(
                        modifier = Modifier.padding(end = 12.dp),
                        painter = painterResource(id = R.drawable.add_square),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                }

                Spacer(modifier = Modifier.height(height = 4.dp))

                Row(
                    modifier = Modifier
                        .height(height = 55.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .clickable {
                            onDeleteClick()
                            state.hideSheet()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = stringResource(id = R.string.delete_sms_announcement),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.End,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    )

                    Icon(
                        modifier = Modifier.padding(end = 12.dp),
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )

                }

                Spacer(modifier = Modifier.height(height = 16.dp))

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = state::hideSheet,
                    text = stringResource(id = R.string.dismiss),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            }

        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun AnnouncementsPreview() {

    AnnouncementsScreen(
        inAppAnnouncements = emptyList(),
        bankSMSAnnouncements = emptyList(),
        selectedOption = AnnouncementTypeOptionUiModel.IN_APP,
        onOptionChanged = {},
        onBackPressed = {}
    )

}