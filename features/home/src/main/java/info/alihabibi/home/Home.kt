package info.alihabibi.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import info.alihabibi.designsystem.theme.Gray1
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.ui.buttons.AppOutlinedButton
import info.alihabibi.ui.charts.GaugeChart
import info.alihabibi.ui.charts.GaugeChartData
import info.alihabibi.ui.dialogs.ChannelListedBottomSheet
import info.alihabibi.ui.headrs.HomePageHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
    onAnnouncements: () -> Unit = {},
    onNewChannel: () -> Unit = {},
    onChannels: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSmsModalShowed = {
            viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
        },
        onAnnouncements = onAnnouncements,
        onNewChannel = onNewChannel,
        onChannels = onChannels
    )

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSmsModalShowed: () -> Unit = {},
    onAnnouncements: () -> Unit = {},
    onNewChannel: () -> Unit = {},
    onChannels: () -> Unit = {},
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
            onAddNewItem = onNewChannel,
            onChannelsEdit = onChannels,
            onSelectItem = {
                // change selected channel and update home screen
            }
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

        Spacer(modifier = Modifier.height(height = 16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(topStartPercent = 6, topEndPercent = 6),
            border = BorderStroke(width = 1.4.dp, color = Color(0xFFECECEC)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {

            GaugeChart(
                modifier = Modifier
                    .size(size = 230.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 12.dp),
                progress = 175f,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                data = GaugeChartData(
                    totalIncome = "0",
                    remainedBalance = "0",
                    bottomMessage = stringResource(id = R.string.empty_balance_state),
                    iconResId = R.drawable.warnign_red_2
                )
            )

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(bottomStartPercent = 8, bottomEndPercent = 8),
            border = BorderStroke(width = 1.4.dp, color = Color(0xFFECECEC)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(weight = 1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(id = R.string.outcome2),
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            painter = painterResource(id = R.drawable.card_send),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(id = R.string.toman),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Gray8, fontSize = 16.sp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                        Text(
                            text = "0",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                        )

                    }

                }

                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    thickness = 1.dp,
                    color = Color(0xFFECECEC)
                )

                Column(
                    modifier = Modifier.weight(weight = 1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(id = R.string.income2),
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            painter = painterResource(id = R.drawable.card_receive),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(id = R.string.toman),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Gray8, fontSize = 16.sp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                        Text(
                            text = "0",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
                        )

                    }

                }

            }

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    HomeScreen(uiState = HomeUiState())

}