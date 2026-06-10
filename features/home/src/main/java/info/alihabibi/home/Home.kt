package info.alihabibi.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.common_android.RequestNotificationPermission
import info.alihabibi.common_android.RequestSMSPermission
import info.alihabibi.ui.headrs.HomePageHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
    onAnnouncements: () -> Unit = {},
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.onEvent(HomeUiIntent.Init)
        }
    }

    HomeScreen(
        uiState = uiState,
        onSmsModalShowed = {
            viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
        },
        onAnnouncements = onAnnouncements
    )

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSmsModalShowed: () -> Unit = {},
    onAnnouncements: () -> Unit = {}
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
    if (!uiState.isSmsModalShown && !smsPermissions.allPermissionsGranted)
        RequestSMSPermission(
            smsPermissions = smsPermissions,
            onSmsModalShown = onSmsModalShowed
        )

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    HomeScreen(uiState = HomeUiState())

}