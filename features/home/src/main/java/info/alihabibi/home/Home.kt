package info.alihabibi.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.common.Utils
import info.alihabibi.common_android.RequestNotificationPermission
import info.alihabibi.common_android.RequestSMSPermission
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.ui.buttons.AppOutlinedButton
import info.alihabibi.ui.dialogs.ChannelListedBottomSheet
import info.alihabibi.ui.headrs.HomePageHeader
import info.alihabibi.ui.items.ListedBottomSheetItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
    onAnnouncements: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSmsModalShowed = {
            viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
        },
        onAnnouncements = onAnnouncements
    )

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
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

    val currentMonth = remember { Utils.getCurrentPersianMonth() }

    var showChannelsBottomSheet by remember { mutableStateOf(false) }
    if (showChannelsBottomSheet)
        ChannelListedBottomSheet(
            items = uiState.channels,
            itemTitle = { it.channelName },
            itemSubTitle = { it.channelBalance },
            itemIcon = { it.icon.iconResId },
            itemKey = { it.id },
            onDismissRequest = { showChannelsBottomSheet = false },
            onSelectItem = {}
        )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomePageHeader(
            isBadgeAvailable = false,
            onNavigationClick = onAnnouncements
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AppOutlinedButton(
                modifier = Modifier.width(width = 135.dp),
                onClick = { showChannelsBottomSheet = true },
                text = stringResource(id = R.string.all_accounts),
                startIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.short_arrow_down),
                        contentDescription = null,
                        tint = Primary
                    )
                }
            )

            Text(
                text = "${stringResource(id = R.string.bookkeeping)} $currentMonth",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    HomeScreen(uiState = HomeUiState())

}