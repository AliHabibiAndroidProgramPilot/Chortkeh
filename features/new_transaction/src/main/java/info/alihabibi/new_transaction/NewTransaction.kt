package info.alihabibi.new_transaction

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Black
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.dialogs.ListedBottomSheet
import info.alihabibi.ui.dialogs.TimePickerBottomSheetContent
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import ir.mehrafzoon.composedatepicker.core.component.rememberDialogDatePicker
import ir.mehrafzoon.composedatepicker.sheet.DatePickerModalBottomSheet
import ir.mehrafzoon.composedatepicker.utils.MaxYear
import ir.mehrafzoon.composedatepicker.utils.MinYear
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime

@Composable
fun NewTransactionDestination(
    viewModel: NewTransactionViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formattedTransactionDate by viewModel.formattedTransactionDate.collectAsStateWithLifecycle()

    NewTransactionScreen(
        uiState = uiState,
        formattedTransactionDate = formattedTransactionDate,
        onPriceChanged = { price ->
            viewModel.onEvent(NewTransactionUiIntent.OnPriceChanged(price))
        },
        onDateChanged = { year, month, day ->
            viewModel.onEvent(NewTransactionUiIntent.OnDateChanged(year, month, day))
        },
        onTimeChange = { hour, minute ->
            viewModel.onEvent(NewTransactionUiIntent.OnTimeChanged(hour, minute))
        },
        onSaveTransaction = {
            // TODO save transaction | call view model here, then navigate back
            onBackPressed()
        },
        onTransactionTypeChanged = { type ->
            viewModel.onEvent(NewTransactionUiIntent.OnTransactionTypeChanged(type))
        },
        onBackPressed = onBackPressed
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewTransactionScreen(
    uiState: NewTransactionUiState,
    formattedTransactionDate: String = "",
    onPriceChanged: (price: String) -> Unit = {},
    onDateChanged: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> },
    onTimeChange: (hour: Int, minute: Int) -> Unit = { _, _ -> },
    onSaveTransaction: () -> Unit = {},
    onTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val datePickerController = rememberDialogDatePicker()
    val dateBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val timeBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val deviceCurrentTime = remember { LocalTime.now() }

    if (dateBottomSheetState.isVisible)
        DatePickerModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            controller = datePickerController,
            sheetState = dateBottomSheetState,
            minYear = MinYear.On(1400),
            maxYear = MaxYear.On(1425),
            titleBottomSheet = stringResource(id = R.string.date),
            titleStyle = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center),
            titleModifier = Modifier.fillMaxWidth(),
            font = R.font.iran_yekanx_normal,
            textButtonStyle = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                color = Primary
            ),
            unSelectedStyle = MaterialTheme.typography.labelMedium.copy(color = Gray7),
            selectedStyle = MaterialTheme.typography.labelMedium.copy(color = Black),
            onDismissRequest = {
                scope.launch { dateBottomSheetState.hide() }
            },
            onDateChanged = { year, month, day ->
                onDateChanged(year, month, day)
            },
            onSubmitClick = {
                val year = datePickerController.getPersianYear()
                val month = datePickerController.getPersianMonth()
                val day = datePickerController.getPersianDay()
                onDateChanged(year, month, day)
            }
        )
    if (timeBottomSheetState.isVisible)
        ModalBottomSheet(
            sheetState = timeBottomSheetState,
            containerColor = White,
            onDismissRequest = {
                scope.launch { timeBottomSheetState.hide() }
            },
            content = {
                TimePickerBottomSheetContent(
                    initialTime = if (uiState.transactionHour != null && uiState.transactionMinute != null)
                        Pair(uiState.transactionHour, uiState.transactionMinute)
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

    var showCategoryBottomSheet by remember { mutableStateOf(false) }
    if (showCategoryBottomSheet)
        ListedBottomSheet(
            items = emptyList<Int>(),
            itemTitle = { "" },
            itemIcon = { R.drawable.header_app_logo },
            itemKey = { 0 },
            onSelectItem = {},
            onDismissRequest = { showCategoryBottomSheet = false },
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .weight(weight = 1f)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.register_transaction),
                windowInsets = TopAppBarDefaults.windowInsets.only(sides = WindowInsetsSides.Top),
                isMenuAvailable = false,
                onNavigationClick = onBackPressed
            )

            Spacer(Modifier.height(height = 8.dp))

            AppToggle(
                toggleItems = TransactionTypeOptionUiModel.entries.toList(),
                itemTitle = { transactionType -> stringResource(id = transactionType.labelRes) },
                onToggleSelectionChanged = { selected ->
                    onTransactionTypeChanged(selected)
                }
            )

            Spacer(Modifier.height(height = 8.dp))

            AppTitledPriceTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = uiState.transactionPrice,
                onValueChange = { newValue ->
                    onPriceChanged(newValue)
                },
                title = stringResource(id = R.string.price),
                placeHolderText = when (uiState.transactionType) {
                    TransactionTypeOptionUiModel.OUTCOME -> stringResource(id = R.string.price_outcome)
                    TransactionTypeOptionUiModel.INCOME -> stringResource(id = R.string.price_income)
                }
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable {},
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.padding(start = 18.dp),
                    painter = painterResource(id = R.drawable.short_arrow_down),
                    contentDescription = null,
                    tint = Gray8
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = when (uiState.transactionType) {
                        TransactionTypeOptionUiModel.OUTCOME -> stringResource(id = R.string.withdraw_from)
                        TransactionTypeOptionUiModel.INCOME -> stringResource(id = R.string.deposit_to)
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

            }

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .clickable { showCategoryBottomSheet = true },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.padding(start = 18.dp),
                    painter = painterResource(id = R.drawable.short_arrow_down),
                    contentDescription = null,
                    tint = Gray8
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.category),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

            }

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(size = 12.dp))
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .clickable {
                        scope.launch { dateBottomSheetState.show() }
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
                    text = formattedTransactionDate.ifEmpty { stringResource(id = R.string.date) },
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

            }

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(size = 12.dp))
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
                    text = uiState.formattedTransactionTime.ifEmpty { stringResource(id = R.string.clock) },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        textDirection = if (uiState.formattedTransactionTime.isEmpty()) TextDirection.Rtl else TextDirection.Ltr,
                        textAlign = if (uiState.formattedTransactionTime.isEmpty()) TextAlign.Right else TextAlign.Left
                    )
                )

            }
        }

        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp, start = 24.dp),
            onClick = onSaveTransaction,
            text = when (uiState.transactionType) {
                TransactionTypeOptionUiModel.OUTCOME -> stringResource(id = R.string.register_outcome_transaction)
                TransactionTypeOptionUiModel.INCOME -> stringResource(id = R.string.register_income_transaction)
            }
        )

    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun NewTransactionPreview() {

    NewTransactionScreen(
        uiState = NewTransactionUiState(),
        onBackPressed = {}
    )

}