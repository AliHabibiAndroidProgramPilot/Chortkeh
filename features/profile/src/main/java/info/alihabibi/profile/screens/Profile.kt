package info.alihabibi.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.common.Utils
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.model.ui_model.CurrenciesOptionUiModel
import info.alihabibi.profile.viewmodel.ProfileUiIntent
import info.alihabibi.profile.viewmodel.ProfileUiState
import info.alihabibi.profile.viewmodel.ProfileViewModel
import info.alihabibi.ui.dialogs.AppDialog
import info.alihabibi.ui.dialogs.AppRadioSelectionBottomSheet
import info.alihabibi.ui.items.AppDangerousListItem
import info.alihabibi.ui.items.AppSimpleListItem

@Composable
fun ProfileDestination(
    viewModel: ProfileViewModel,
    onPrivacyAndPolicy: () -> Unit = {},
    onExitOfAccount: () -> Unit = {},
    onUserAccountInfo: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileUiIntent.Init)
    }

    ProfileScreen(
        uiState = uiState,
        onPrivacyAndPolicy = onPrivacyAndPolicy,
        onExitOfAccount = onExitOfAccount,
        onUserAccountInfo = onUserAccountInfo,
        onPreferredCurrencySelection = { currency ->
            viewModel.onEvent(ProfileUiIntent.SavePreferredCurrency(currency))
        }
    )

}

@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    onExitOfAccount: () -> Unit = {},
    onPrivacyAndPolicy: () -> Unit = {},
    onUserAccountInfo: () -> Unit = {},
    onPreferredCurrencySelection: (currency: CurrenciesOptionUiModel) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 230.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.profile_header_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(top = 10.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(26.dp))

                Image(
                    painter = painterResource(id = uiState.userAccountInfo.profileImageRes),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 4.dp,
                        start = 4.dp,
                        end = 4.dp
                    ),
                    text = uiState.userAccountInfo.fullName.ifEmpty { stringResource(id = R.string.chortkeh_user) },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        textDirection = TextDirection.ContentOrRtl
                    )
                )

                if (uiState.userAccountInfo.phone.isNotEmpty())
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = uiState.userAccountInfo.phone,
                        style = MaterialTheme.typography.labelMedium.copy(color = Gray7)
                    )

            }

        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            var showExitDialog by remember { mutableStateOf(false) }
            if (showExitDialog)
                AppDialog(
                    title = stringResource(id = R.string.exit_from_account),
                    message = stringResource(id = R.string.exit_from_account_description),
                    confirmButtonText = stringResource(id = R.string.dismiss),
                    cancelButtonText = stringResource(id = R.string.exit),
                    onDismissRequest = { showExitDialog = false },
                    onCancelClicked = {
                        showExitDialog = false
                        onExitOfAccount()
                    },
                    onConfirmClicked = { showExitDialog = false }
                )

            var showCurrencySelectionModal by remember { mutableStateOf(false) }

            if (showCurrencySelectionModal)
                AppRadioSelectionBottomSheet(
                    title = stringResource(id = R.string.currency),
                    radioOptions = CurrenciesOptionUiModel.entries.toList(),
                    selectedOption = uiState.currency,
                    disabledIndex = 1,
                    optionLabel = { currency -> stringResource(id = currency.labelRes) },
                    onRadioOptionSelected = { userSelectedCurrency ->
                        onPreferredCurrencySelection(userSelectedCurrency)
                    },
                    onConfirmClicked = {
                        showCurrencySelectionModal = false
                    },
                    onDismissRequest = { showCurrencySelectionModal = false }
                )

            AppSimpleListItem(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = onUserAccountInfo,
                title = stringResource(id = R.string.user_account_info),
                startIcon = painterResource(id = R.drawable.profile)
            )

            AppSimpleListItem(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = onPrivacyAndPolicy,
                title = stringResource(id = R.string.privacy_policy),
                startIcon = painterResource(id = R.drawable.lock)
            )

            AppSimpleListItem(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = { showCurrencySelectionModal = true },
                title = stringResource(id = R.string.currency),
                startIcon = painterResource(id = R.drawable.money_currency)
            )

            AppDangerousListItem(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = { showExitDialog = true },
                title = stringResource(id = R.string.exit),
                startIcon = painterResource(id = R.drawable.logout_red),
                isEnabled = false
            )

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val context = LocalContext.current
            val version = remember { Utils.getAppVersionName(context) }

            Text(
                text = "Version $version",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = stringResource(id = R.string.made_with_love),
                style = MaterialTheme.typography.bodySmall.copy(textDirection = TextDirection.Ltr)
            )

        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProfilePreview() {

    ProfileScreen(
        uiState = ProfileUiState()
    )

}
