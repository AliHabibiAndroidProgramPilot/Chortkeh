package info.alihabibi.new_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import info.alihabibi.ui.scaffolds.BaseScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewTransactionDestination(
    viewModel: NewTransactionViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScaffold { innerPadding ->

        NewTransactionScreen(
            uiState = uiState,
            contentPadding = innerPadding,
            onPriceChanged = { price ->
                viewModel.onEvent(NewTransactionUiIntent.OnPriceChanged(price))
            },
            onTransactionTypeChanged = { type ->
                viewModel.onEvent(NewTransactionUiIntent.OnTransactionTypeChanged(type))
            },
            onBackPressed = onBackPressed
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewTransactionScreen(
    uiState: NewTransactionUiState,
    contentPadding: PaddingValues = PaddingValues(),
    onPriceChanged: (price: String) -> Unit = {},
    onTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

    val layoutDirection = LocalLayoutDirection.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = contentPadding.calculateStartPadding(layoutDirection),
                end = contentPadding.calculateEndPadding(layoutDirection)
            ),
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
            placeHolderText = when(uiState.transactionType) {
                TransactionTypeOptionUiModel.OUTCOME -> stringResource(id = R.string.price_outcome)
                TransactionTypeOptionUiModel.INCOME -> stringResource(id = R.string.price_income)
            }
        )

    }

}