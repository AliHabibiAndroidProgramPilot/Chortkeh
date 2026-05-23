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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.common.Utils
import info.alihabibi.common_android.RequestNotificationPermission
import info.alihabibi.common_android.RequestSMSPermission
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
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
    onAnnouncements: () -> Unit = {},
    onExitOfAccount: () -> Unit = {},
    onPrivacyAndPolicy: () -> Unit = {}
) {

    var selectedBottomNavItem by rememberSaveable { mutableStateOf(BottomNavItem.HOME.name) }
    val navItems = remember { BottomNavItem.entries.toList() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeUiIntent.FetchSmsPermissionModalShownState)
    }

    BaseScaffold(
        bottomBar = {
            AppBottomNavigation(
                navItems = navItems,
                selectedNavItem = selectedBottomNavItem,
                onFabClick = { /*TODO(botton sheet)*/ },
                onNavItemClicked = { item -> selectedBottomNavItem = item.name }
            )
        }
    ) { _ ->

        AnimatedContent(
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
                    onSmsModalShowed = {
                        viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
                    },
                    onAnnouncements = onAnnouncements
                )

                BottomNavItem.PROFILE.name -> ProfileScreen(
                    onExitOfAccount = onExitOfAccount,
                    onPrivacyAndPolicy = onPrivacyAndPolicy
                )

                BottomNavItem.REPORTS.name -> ReportsScreen()
                BottomNavItem.REMINDER.name -> ReminderScreen()
            }

        }

    }

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSmsModalShowed: () -> Unit = {},
    onAnnouncements: () -> Unit
) {

    val notificationPermissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val receiveSmsPermission = rememberPermissionState(permission = Manifest.permission.RECEIVE_SMS)
    val readSmsPermission = rememberPermissionState(permission = Manifest.permission.READ_SMS)

    val allGranted = receiveSmsPermission.status.isGranted && readSmsPermission.status.isGranted

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationPermissionState.status.isGranted)
        RequestNotificationPermission()
    if (uiState.isSmsModalShown == false && !allGranted) {
        RequestSMSPermission(onSmsModalShown = onSmsModalShowed)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
    onExitOfAccount: () -> Unit = {},
    onPrivacyAndPolicy: () -> Unit = {}
) {

    Column(
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
                    painter = painterResource(id = R.drawable.men_profile),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    text = "علی حبیبی",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = "09924025474".chunked(4).joinToString(" "),
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

            val context = LocalContext.current
            val currencyOptions = remember {
                listOf(
                    Utils.getStringResources(context = context, id = R.string.toman),
                    Utils.getStringResources(context = context, id = R.string.rial),
                )
            }
            var selectedCurrency by remember { mutableStateOf(currencyOptions.first()) }
            var showCurrencySelectionModal by remember { mutableStateOf(false) }

            if (showCurrencySelectionModal)
                AppRadioSelectionBottomSheet(
                    title = stringResource(id = R.string.currency),
                    radioOptions = currencyOptions,
                    selectedRadioButton = selectedCurrency,
                    disabledIndex = 1,
                    onRadioOptionSelected = { userSelectedCurrency ->
                        selectedCurrency = userSelectedCurrency
                    },
                    onConfirmClicked = {
                        showCurrencySelectionModal = false
                        //TODO save user preferred currency
                    },
                    onDismissRequest = { showCurrencySelectionModal = false }
                )

            AppSimpleListItem(
                modifier = Modifier.padding(vertical = 8.dp),
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
private fun ReportsScreen() {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "Report", fontSize = 32.sp)

}

@Composable
private fun ReminderScreen() {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "reminder", fontSize = 32.sp)

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    ProfileScreen()

}