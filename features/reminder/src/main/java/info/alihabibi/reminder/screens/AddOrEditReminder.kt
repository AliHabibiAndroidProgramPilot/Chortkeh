package info.alihabibi.reminder.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.common.Utils
import info.alihabibi.common_android.snackbar.SnackBarController
import info.alihabibi.common_android.snackbar.SnackBarEvent
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.White
import info.alihabibi.domain.local.coordinators.ReminderUndoManager
import info.alihabibi.reminder.viewmodels.ReminderUiState
import info.alihabibi.reminder.viewmodels.AddOrEditReminderViewModel
import info.alihabibi.reminder.viewmodels.RemindersUiIntent
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.dialogs.TimePickerBottomSheetContent
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledTextField
import ir.mehrafzoon.composedatepicker.core.component.rememberDialogDatePicker
import ir.mehrafzoon.composedatepicker.sheet.DatePickerModalBottomSheet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.time.LocalTime

@Composable
fun AddOrEditReminderDestination(
    viewModel: AddOrEditReminderViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formattedReminderDate by viewModel.formattedReminderDate.collectAsStateWithLifecycle()

    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
    val reminderUndoManager: ReminderUndoManager = koinInject()
    uiState.savedReminderId?.let { id ->
        LaunchedEffect(id) {
            SnackBarController.sendEvent(
                SnackBarEvent(
                    message = Utils.getStringResources(context, R.string.reminder_saved_successfully),
                    actionTitle = Utils.getStringResources(context, R.string.undo),
                    action = {
                        reminderUndoManager.executeUndo(id)
                    }
                )
            )
            currentOnBackPressed()
        }
    }

    AddOrEditReminderScreen(
        uiState = uiState,
        formattedReminderDate = formattedReminderDate,
        onReminderNameChanged = { value ->
            viewModel.onEvent(RemindersUiIntent.ChangeReminderName(value))
        },
        onDateChanged = { year, month, day, triggerTimeStamp ->
            viewModel.onEvent(RemindersUiIntent.ChangeReminderDate(year, month, day, triggerTimeStamp))
        },
        onTimeChange = { hour, minute ->
            viewModel.onEvent(RemindersUiIntent.ChangeReminderTime(hour, minute))
        },
        onSaveOrEditReminder = {
            viewModel.onEvent(RemindersUiIntent.SaveReminder)
        },
        onBackPressed = onBackPressed
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditReminderScreen(
    uiState: ReminderUiState,
    formattedReminderDate: String = "",
    onReminderNameChanged: (value: String) -> Unit = {},
    onDateChanged: (year: Int, month: Int, day: Pair<Int, String>, timeStamp: Long) -> Unit = { _, _, _, _ -> },
    onTimeChange: (hour: Int?, minute: Int?) -> Unit = { _, _ -> },
    onSaveOrEditReminder: () -> Unit = {},
    onBackPressed: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val currentDate = remember { Utils.getCurrentPersianDate() }

    val datePickerController = rememberDialogDatePicker()
    val dateBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (dateBottomSheetState.isVisible)
        DatePickerModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            controller = datePickerController,
            sheetState = dateBottomSheetState,
            useInitialDate = true,
            initialDate = currentDate,
            titleBottomSheet = stringResource(id = R.string.date),
            titleStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            titleModifier = Modifier.fillMaxWidth(),
            font = R.font.iran_yekanx_normal,
            textButtonStyle = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                color = White
            ),
            unSelectedStyle = MaterialTheme.typography.labelMedium.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            selectedStyle = MaterialTheme.typography.labelMedium.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            lineColor = MaterialTheme.colorScheme.primary,
            buttonColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                scope.launch { dateBottomSheetState.hide() }
            },
            onSubmitClick = {
                val year = datePickerController.getPersianYear()
                val month = datePickerController.getPersianMonth()
                val day = datePickerController.getPersianDay()
                val dayOfWeek = datePickerController.getPersianDayOfWeekName()
                val timeStamp = datePickerController.getTimestamp()
                onDateChanged(year, month, Pair(day, dayOfWeek), timeStamp)
            }
        )

    val deviceCurrentTime = remember { LocalTime.now() }
    val timeBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (timeBottomSheetState.isVisible)
        ModalBottomSheet(
            sheetState = timeBottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                scope.launch { timeBottomSheetState.hide() }
            },
            content = {
                TimePickerBottomSheetContent(
                    initialTime = if (uiState.reminderHour != null && uiState.reminderMinute != null)
                        Pair(uiState.reminderHour, uiState.reminderMinute)
                    else
                        Pair(deviceCurrentTime.hour, deviceCurrentTime.minute),
                    onSubmitClick = { hour, minute ->
                        onTimeChange(hour, minute)
                        scope.launch { timeBottomSheetState.hide() }
                    },
                    onTimeValueChange = { hour, minute ->
                        onTimeChange(hour, minute)
                    },
                    onDismissRequest = { confirmedHour, confirmedMinute ->
                        onTimeChange(confirmedHour, confirmedMinute)
                        scope.launch { timeBottomSheetState.hide() }
                    }
                )
            }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppHeader(
                    title = stringResource(id = R.string.register_reminder),
                    onNavigationClicked = onBackPressed,
                    isActionAvailable = false
                )

                Spacer(Modifier.height(height = 8.dp))

                AppTitledTextField(
                    title = stringResource(id = R.string.reminder_title),
                    text = uiState.reminderTitle,
                    onValueChange = onReminderNameChanged,
                    placeHolderText = stringResource(id = R.string.name),
                    error = uiState.reminderTitle.length >= 30,
                    errorMessage = stringResource(id = R.string.reminder_name_error)
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 50.dp)
                        .padding(horizontal = 16.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .clickable {
                            scope.launch {
                                dateBottomSheetState.show()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        modifier = Modifier.padding(start = 18.dp),
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        tint = Gray8
                    )

                    Spacer(modifier = Modifier.weight(weight = 1f))

                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = formattedReminderDate.ifEmpty { stringResource(id = R.string.date) },
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                    )

                }

                Spacer(Modifier.height(height = 12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 50.dp)
                        .padding(horizontal = 16.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .clickable {
                            scope.launch { timeBottomSheetState.show() }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        modifier = Modifier.padding(start = 18.dp),
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        tint = Gray8
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(weight = 1f),
                        text = uiState.formattedReminderTime.ifEmpty { stringResource(id = R.string.clock) },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            textDirection = if (uiState.formattedReminderTime.isEmpty()) TextDirection.Rtl else TextDirection.Ltr,
                            textAlign = if (uiState.formattedReminderTime.isEmpty()) TextAlign.Right else TextAlign.Left
                        )
                    )

                }

            }

            AppButton(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp),
                onClick = onSaveOrEditReminder,
                text = stringResource(id = R.string.register),
                enabled = uiState.isRegisterReminderButtonEnabled
            )

        }

    }

}