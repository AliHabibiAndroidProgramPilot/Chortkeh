package info.alihabibi.reminder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.model.ui_model.reminder.ReminderUiModel
import info.alihabibi.reminder.viewmodels.ReminderListUiIntent
import info.alihabibi.reminder.viewmodels.ReminderListViewModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.items.ListedReminderItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RemindersListDestination(
    viewModel: ReminderListViewModel = koinViewModel(),
    onNewReminder: () -> Unit = {},
    onEditReminder: (reminderId: Long) -> Unit = {}
) {

    val reminders by viewModel.reminders.collectAsStateWithLifecycle()

    RemindersListScreen(
        reminders = reminders,
        onNewReminder = onNewReminder,
        onEditReminder = { reminderId ->
            onEditReminder(reminderId)
        },
        onReminderEnabledChanged = { reminderId, value ->
            viewModel.onEvent(ReminderListUiIntent.ReminderEnabledChanged(reminderId, value))
        }
    )

}

@Composable
private fun RemindersListScreen(
    reminders: List<ReminderUiModel>,
    onNewReminder: () -> Unit = {},
    onEditReminder: (reminderId: Long) -> Unit = {},
    onReminderEnabledChanged: (reminderId: Long, value: Boolean) -> Unit = { _, _ -> }
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.reminder),
                isActionAvailable = false,
                isNavigationAvailable = false
            )

            if (reminders.isEmpty())
                EmptyReminderState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            else
                RemindersListContent(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxWidth(),
                    reminders = reminders,
                    onReminderEnabledChanged = onReminderEnabledChanged,
                    onReminderClicked = onEditReminder
                )

            AppButton(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(bottom = 32.dp, top = 8.dp),
                onClick = onNewReminder,
                text = stringResource(id = R.string.add_new_reminder)
            )

        }

    }

}

@Composable
private fun RemindersListContent(
    modifier: Modifier = Modifier,
    reminders: List<ReminderUiModel>,
    onReminderClicked: (reminder: Long) -> Unit = {},
    onReminderEnabledChanged: (reminderId: Long, value: Boolean) -> Unit = { _, _ -> }
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {

        items(
            items = reminders,
            key = { it.id }
        ) { reminder ->

            ListedReminderItem(
                title = reminder.title,
                subTitle = PersianDateFormatter.format(
                    reminder.year,
                    reminder.month,
                    reminder.dayOfWeekName,
                    reminder.day,
                    reminder.time
                ),
                isEnabled = reminder.isEnabled,
                isPassed = reminder.isPassed,
                onCheckedChange = { value ->
                    if (reminder.isPassed == false)
                        onReminderEnabledChanged(reminder.id, value)
                },
                onClick = { onReminderClicked(reminder.id) }
            )

        }

    }

}

@Composable
private fun EmptyReminderState(modifier: Modifier = Modifier) {

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
                painter = painterResource(id = R.drawable.empty_reminder),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = stringResource(id = R.string.empty_reminder_list_message),
                style = MaterialTheme.typography.labelLarge.copy(color = Gray7)
            )

        }

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 48.dp),
            painter = painterResource(id = R.drawable.arrow_shape_down),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

    }

}