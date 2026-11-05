package info.alihabibi.channels.screens

import android.Manifest
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import info.alihabibi.common_android.AndroidUtils
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.ui.dialogs.AppDialog
import info.alihabibi.ui.dialogs.AppIconSelectionBottomSheet
import info.alihabibi.ui.inputs.AppCardNumberTextField
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import info.alihabibi.ui.inputs.AppTitledTextField

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun BankAccountChannelContent(
    modifier: Modifier = Modifier,
    channelName: String,
    cardNumber: String,
    balance: String,
    @DrawableRes iconResId: Int,
    selectedSmsSource: String? = null,
    isEditing: Boolean = false,
    onCardNumberChange: (value: String) -> Unit = {},
    onInitialBalanceChange: (value: String) -> Unit = {},
    onChannelNameChange: (value: String) -> Unit = {},
    onFetchSmsSenders: () -> Unit = {},
    onRemoveSmsSource: () -> Unit = {}
) {

    val context = LocalContext.current

    val smsPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )
    )
    var hasRequestedSmsPermissions by rememberSaveable { mutableStateOf(false) }
    var showSMSRequestDialog by remember { mutableStateOf(false) }
    if (showSMSRequestDialog)
        AppDialog(
            title = stringResource(id = R.string.sms_access),
            message = stringResource(id = R.string.need_sms_permission),
            confirmButtonText = stringResource(id = R.string.I_give_permission),
            cancelButtonText = stringResource(id = R.string.dismiss),
            onConfirmClicked = {
                when {
                    hasRequestedSmsPermissions && !smsPermissions.shouldShowRationale -> {
                        AndroidUtils.openAppSetting(context)
                    }
                    else -> {
                        hasRequestedSmsPermissions = true
                        smsPermissions.launchMultiplePermissionRequest()
                    }
                }
            },
            onCancelClicked = {
                showSMSRequestDialog = false
            },
            onDismissRequest = {
                showSMSRequestDialog = false
            }
        )

    var showSmsSourceRemovingDialog by remember { mutableStateOf(false) }
    if (showSmsSourceRemovingDialog && isEditing)
        AppDialog(
            title = stringResource(id = R.string.sms_remove_title),
            message = stringResource(id = R.string.sms_remove_description),
            confirmButtonText = stringResource(id = R.string.dismiss),
            cancelButtonText = stringResource(id = R.string.delete),
            onCancelClicked = {
                onRemoveSmsSource()
                showSmsSourceRemovingDialog = false
            },
            onConfirmClicked = {
                showSmsSourceRemovingDialog = false
            },
            onDismissRequest = {
                showSmsSourceRemovingDialog = false
            }
        )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppCardNumberTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = cardNumber,
            onValueChange = onCardNumberChange,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(size = 28.dp),
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            title = stringResource(id = R.string.card_number)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledPriceTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = balance,
            onValueChange = onInitialBalanceChange,
            title = stringResource(id = R.string.balance),
            placeHolderText = stringResource(id = R.string.toman_0)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = channelName,
            onValueChange = onChannelNameChange,
            title = stringResource(id = R.string.source_name)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .clickable {
                    if (isEditing && selectedSmsSource != null) {
                        showSmsSourceRemovingDialog = true
                    } else if (smsPermissions.allPermissionsGranted) {
                        onFetchSmsSenders()
                    } else {
                        showSMSRequestDialog = true
                    }
                }
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Switch(
                checked = selectedSmsSource != null,
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    uncheckedThumbColor = White,
                    checkedTrackColor = Primary,
                    uncheckedTrackColor = Gray5,
                    uncheckedBorderColor = Gray5
                )
            )

            Column(
                modifier = Modifier.weight(weight = 1f),
                verticalArrangement = Arrangement.spacedBy(space = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.read_sms),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Right
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.read_sms_description),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Gray7,
                        textAlign = TextAlign.Right,
                        textDirection = TextDirection.Rtl
                    ),
                    overflow = TextOverflow.Ellipsis
                )

            }

        }

        if (selectedSmsSource != null) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.padding(start = 18.dp),
                    painter = painterResource(id = R.drawable.sms),
                    contentDescription = null,
                    tint = Gray8
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .weight(weight = 1f),
                    text = selectedSmsSource,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        textDirection = TextDirection.Ltr
                    )
                )

            }
        }

    }

}

@Composable
internal fun OtherChannelContent(
    modifier: Modifier = Modifier,
    channelName: String,
    initialBalance: String,
    channelSelectedIcon: ChannelIconOptionUiModel? = null,
    onInitialBalanceChange: (value: String) -> Unit = {},
    onChannelNameChange: (value: String) -> Unit = {},
    onChannelIconChanged: (value: ChannelIconOptionUiModel) -> Unit = {},
) {

    var showChannelIconSelectionModel by remember { mutableStateOf(false) }
    if(showChannelIconSelectionModel)
        AppIconSelectionBottomSheet(
            title = stringResource(id = R.string.channel_icon),
            options = ChannelIconOptionUiModel.entries.toList(),
            iconsResId = { it.iconResId },
            onOptionSelected = { selectedIcon ->
                onChannelIconChanged(selectedIcon)
                showChannelIconSelectionModel = false
            },
            onDismissRequest = { showChannelIconSelectionModel = false }
        )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppTitledTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = channelName,
            error = channelName.length >= 30,
            errorMessage = stringResource(id = R.string.channel_name_error),
            onValueChange = onChannelNameChange,
            title = stringResource(id = R.string.source_name)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .padding(horizontal = 16.dp)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .clickable { showChannelIconSelectionModel = true },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.padding(start = 18.dp),
                painter = painterResource(id = R.drawable.short_arrow_down),
                contentDescription = null,
                tint = Gray8
            )

            Spacer(modifier = Modifier.weight(weight = 1f))

            if (channelSelectedIcon != null)
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(size = 28.dp),
                    painter = painterResource(id = channelSelectedIcon.iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            else
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.icon),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

        }

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledPriceTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = initialBalance,
            onValueChange = onInitialBalanceChange,
            title = stringResource(id = R.string.initial_source_balance),
            placeHolderText = stringResource(id = R.string.toman_0)
        )

    }

}