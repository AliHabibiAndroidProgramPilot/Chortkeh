package info.alihabibi.new_transaction.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.new_transaction.NewTransactionUiIntent
import info.alihabibi.new_transaction.NewTransactionUiState
import info.alihabibi.new_transaction.NewTransactionViewModel
import info.alihabibi.ui.dialogs.AppRadioSelectionBottomSheet
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddCategoryDestination(
    viewModel: NewTransactionViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddCategoryScreen(
        uiState = uiState,
        onCategoryNameChanged = { categoryName ->
            viewModel.onEvent(NewTransactionUiIntent.OnCategoryNameChanged(categoryName))
        },
        onCategoryTypeChanged = { categoryType ->
            viewModel.onEvent(NewTransactionUiIntent.OnCategoryTypeChanged(categoryType))
        },
        onBackPressed = onBackPressed
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCategoryScreen(
    uiState: NewTransactionUiState,
    onCategoryNameChanged: (categoryName: String) -> Unit = {},
    onCategoryTypeChanged: (categoryType: CategoryTypeOptionUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

    var showCategoryTypeSelectionModel by remember { mutableStateOf(false) }
    if (showCategoryTypeSelectionModel)
        AppRadioSelectionBottomSheet(
            title = stringResource(id = R.string.category_type),
            radioOptions = CategoryTypeOptionUiModel.entries.toList(),
            selectedOption = uiState.categoryType,
            optionLabel = { type -> stringResource(id = type.labelRes) },
            onRadioOptionSelected = { selectedCategoryType ->
                onCategoryTypeChanged(selectedCategoryType)
            },
            onDismissRequest = { showCategoryTypeSelectionModel = false },
            onConfirmClicked = { showCategoryTypeSelectionModel = false }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader(
            title = stringResource(id = R.string.new_category),
            windowInsets = TopAppBarDefaults.windowInsets.only(sides = WindowInsetsSides.Top),
            isMenuAvailable = false,
            onNavigationClick = onBackPressed
        )

        Spacer(Modifier.height(height = 8.dp))

        AppTitledTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            title = stringResource(id = R.string.name),
            text = uiState.categoryName,
            placeHolderText = stringResource(id = R.string.category_name),
            error = uiState.categoryName.length >= 30,
            errorMessage = stringResource(id = R.string.category_name_error),
            onValueChange = onCategoryNameChanged
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .padding(horizontal = 16.dp)
                .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .clickable { showCategoryTypeSelectionModel = true },
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
                text = when {
                    uiState.categoryType != null -> stringResource(id = uiState.categoryType.labelRes)
                    else -> stringResource(id = R.string.category_type)
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
            )

        }

    }

}