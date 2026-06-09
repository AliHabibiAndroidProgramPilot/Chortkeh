package info.alihabibi.user_account_info

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.model.ui_model.GenderOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.dialogs.AppRadioSelectionBottomSheet
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppTitledPhoneTextField
import info.alihabibi.ui.inputs.AppTitledTextField
import info.alihabibi.ui.scaffolds.BaseScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserAccountInfoDestination(
    viewModel: UserAccountInfoViewModel = koinViewModel(),
    onBackPressed: (userSavedData: Boolean) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(UserAccountInfoUiIntent.Init)
    }

    BaseScaffold { innerPadding ->

        UserAccountInfoScreen(
            uiState = uiState,
            contentPadding = innerPadding,
            onFullNameChanged = { newValue ->
                viewModel.onEvent(UserAccountInfoUiIntent.OnFullNameChanged(newValue))
            },
            onPhoneChanged = { newValue ->
                viewModel.onEvent(UserAccountInfoUiIntent.OnPhoneChanged(newValue))
            },
            onGenderChanged = { gender ->
                viewModel.onEvent(UserAccountInfoUiIntent.OnGenderChanged(gender))
            },
            onSaveUserInfo = {
                viewModel.onEvent(UserAccountInfoUiIntent.OnSaveValues)
                onBackPressed(true)
            },
            onBackPressed = { onBackPressed(false) }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserAccountInfoScreen(
    uiState: UserAccountInfoUiState,
    contentPadding: PaddingValues = PaddingValues(),
    onFullNameChanged: (fullName: String) -> Unit = {},
    onPhoneChanged: (phone: String) -> Unit = {},
    onGenderChanged: (gender: GenderOptionUiModel) -> Unit = {},
    onSaveUserInfo: () -> Unit = {},
    onBackPressed: () -> Unit
) {

    val layoutDirection = LocalLayoutDirection.current

    var showGenderSelectionModal by remember { mutableStateOf(false) }
    if (showGenderSelectionModal)
        AppRadioSelectionBottomSheet(
            title = stringResource(id = R.string.gender),
            radioOptions = GenderOptionUiModel.entries.toList(),
            selectedOption = uiState.userGender,
            optionLabel = { gender -> stringResource(id = gender.labelRes) },
            onRadioOptionSelected = { selectedGender ->
                onGenderChanged(selectedGender)
            },
            onDismissRequest = { showGenderSelectionModal = false },
            onConfirmClicked = { showGenderSelectionModal = false }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = contentPadding.calculateStartPadding(layoutDirection),
                end = contentPadding.calculateEndPadding(layoutDirection),
                bottom = contentPadding.calculateBottomPadding()
            )
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
                title = stringResource(id = R.string.user_account_info),
                windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Top),
                isMenuAvailable = false,
                onNavigationClick = onBackPressed
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            AppTitledTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = uiState.fullName,
                onValueChange = onFullNameChanged,
                title = stringResource(id = R.string.name_and_family_name),
                placeHolderText = stringResource(id = R.string.sample_name)
            )

            Spacer(modifier = Modifier.height(height = 14.dp))

            AppTitledPhoneTextField(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = uiState.userPhone,
                onValueChange = onPhoneChanged,
                title = stringResource(id = R.string.phone_number),
                placeHolderText = stringResource(id = R.string.sample_phone)
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable { showGenderSelectionModal = true },
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
                    text = stringResource(id = uiState.userGender.labelRes),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

        }

        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp, start = 24.dp),
            onClick = onSaveUserInfo,
            text = stringResource(id = R.string.save_data)
        )

    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun UserAccountInfoPreview() {

    UserAccountInfoScreen(
        uiState = UserAccountInfoUiState(),
        onBackPressed = {}
    )

}