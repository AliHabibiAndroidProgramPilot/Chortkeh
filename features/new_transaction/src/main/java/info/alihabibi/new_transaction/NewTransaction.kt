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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewTransactionDestination(
    viewModel: NewTransactionViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewTransactionScreen(
        uiState = uiState,
        onPriceChanged = { price ->
            viewModel.onEvent(NewTransactionUiIntent.OnPriceChanged(price))
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
    onPriceChanged: (price: String) -> Unit = {},
    onSaveTransaction: () -> Unit = {},
    onTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

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
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable {},
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
                    text = stringResource(id = R.string.date),
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
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable {},
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.padding(start = 18.dp),
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = null,
                    tint = Gray8
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.clock),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
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