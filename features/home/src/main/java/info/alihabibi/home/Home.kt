package info.alihabibi.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.common.Utils
import info.alihabibi.common_android.RequestNotificationPermission
import info.alihabibi.common_android.RequestSMSPermission
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.model.ui_model.CurrenciesOptionUiModel
import info.alihabibi.ui.dialogs.AppDialog
import info.alihabibi.ui.dialogs.AppRadioSelectionBottomSheet
import info.alihabibi.ui.headrs.HomePageHeader
import info.alihabibi.ui.items.AppDangerousListItem
import info.alihabibi.ui.items.AppSimpleListItem
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.navigation.BottomNavItem
import info.alihabibi.ui.scaffolds.BaseScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
    shouldShowSuccessfulDataSaved: Boolean = false,
    onUserInfoSavedConsumed: () -> Unit = {},
    onAnnouncements: () -> Unit = {},
    onExitOfAccount: () -> Unit = {},
    onPrivacyAndPolicy: () -> Unit = {},
    onUserAccountInfo: () -> Unit = {}
) {

    var selectedBottomNavItem by rememberSaveable { mutableStateOf(BottomNavItem.HOME.name) }
    val navItems = remember { BottomNavItem.entries.toList() }

    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val savedMessage = stringResource(id = R.string.successful_save_data)
    LaunchedEffect(shouldShowSuccessfulDataSaved) {
        if (shouldShowSuccessfulDataSaved) {
            snackBarHostState.showSnackbar(savedMessage)
            onUserInfoSavedConsumed()
        }
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.onEvent(HomeUiIntent.Init)
        }
    }

    BaseScaffold(
        contentWindowInsets = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Horizontal),
        snackBarHostState = snackBarHostState,
        bottomBar = {
            AppBottomNavigation(
                navItems = navItems,
                selectedNavItem = selectedBottomNavItem,
                onFabClick = { /*TODO(botton sheet)*/ },
                onNavItemClicked = { item -> selectedBottomNavItem = item.name }
            )
        }
    ) { innerPadding ->

        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = selectedBottomNavItem,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(220, delayMillis = 90)
                ) + scaleIn(
                    initialScale = 0.98f,
                    animationSpec = tween(220, delayMillis = 90)
                ) togetherWith
                        fadeOut(animationSpec = tween(90))
            }
        ) { bottomNavItem ->

            when (bottomNavItem) {
                BottomNavItem.HOME.name -> HomeScreen(
                    uiState = uiState,
                    contentPadding = innerPadding,
                    onSmsModalShowed = {
                        viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
                    },
                    onAnnouncements = onAnnouncements
                )

                BottomNavItem.PROFILE.name -> ProfileScreen(
                    uiState = uiState,
                    contentPadding = innerPadding,
                    onExitOfAccount = onExitOfAccount,
                    onPrivacyAndPolicy = onPrivacyAndPolicy,
                    onUserAccountInfo = onUserAccountInfo,
                    onPreferredCurrencySelection = { currency ->
                        viewModel.onEvent(HomeUiIntent.SavePreferredCurrency(currency))
                    }
                )

                BottomNavItem.REPORTS.name -> ReportsScreen(
                    contentPadding = innerPadding
                )

                BottomNavItem.REMINDER.name -> ReminderScreen(
                    contentPadding = innerPadding
                )
            }

        }

    }

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    contentPadding: PaddingValues = PaddingValues(),
    onSmsModalShowed: () -> Unit = {},
    onAnnouncements: () -> Unit
) {

    val notificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val smsPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationPermission.status.isGranted)
        RequestNotificationPermission(notificationPermission = notificationPermission)
    if (uiState.isSmsModalShown == false && !smsPermissions.allPermissionsGranted)
        RequestSMSPermission(
            smsPermissions = smsPermissions,
            onSmsModalShown = onSmsModalShowed
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = contentPadding.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = contentPadding.calculateEndPadding(layoutDirection = LocalLayoutDirection.current),
                bottom = contentPadding.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomePageHeader(
            isBadgeAvailable = false,
            onNavigationClick = onAnnouncements
        )

    }

}

@Composable
private fun ProfileScreen(
    uiState: HomeUiState,
    contentPadding: PaddingValues = PaddingValues(),
    onExitOfAccount: () -> Unit = {},
    onPrivacyAndPolicy: () -> Unit = {},
    onUserAccountInfo: () -> Unit = {},
    onPreferredCurrencySelection: (currency: CurrenciesOptionUiModel) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(
                start = contentPadding.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = contentPadding.calculateEndPadding(layoutDirection = LocalLayoutDirection.current),
                bottom = contentPadding.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
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
                        .padding(top = 24.dp),
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = uiState.userAccountInfo.profileImageRes),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    text = uiState.userAccountInfo.fullName.ifEmpty { stringResource(id = R.string.chortkeh_user) },
                    style = MaterialTheme.typography.labelMedium
                )

                if (uiState.userAccountInfo.phone.isNotEmpty())
                    Text(
                        text = uiState.userAccountInfo.phone,
                        style = MaterialTheme.typography.labelSmall.copy(color = Gray7)
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
                startIcon = painterResource(id = R.drawable.logout_red)
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

@Composable
private fun ReportsScreen(
    contentPadding: PaddingValues = PaddingValues(),
) {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "Report", fontSize = 32.sp)

}

@Composable
private fun ReminderScreen(
    contentPadding: PaddingValues = PaddingValues(),
) {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "reminder", fontSize = 32.sp)

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    ProfileScreen(
        uiState = HomeUiState(),
    )

}