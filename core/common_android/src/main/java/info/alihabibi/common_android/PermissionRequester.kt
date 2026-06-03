package info.alihabibi.common_android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import info.alihabibi.designsystem.R
import info.alihabibi.ui.dialogs.AppDialog
import info.alihabibi.ui.dialogs.AppSimpleBottomSheet

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission(
    notificationPermission: PermissionState
) {

    LaunchedEffect(notificationPermission.status.isGranted) {
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
        }
    }
    var showDialog by remember { mutableStateOf(true) }
    val shouldShowRationale =
        (notificationPermission.status as? PermissionStatus.Denied)?.shouldShowRationale == true
    if (shouldShowRationale && showDialog) {
        AppDialog(
            title = stringResource(id = R.string.notification_access),
            message = stringResource(id = R.string.notification_access_message),
            confirmButtonText = stringResource(id = R.string.I_give_permission),
            cancelButtonText = stringResource(id = R.string.dismiss),
            onConfirmClicked = {
                notificationPermission.launchPermissionRequest()
                showDialog = false
            },
            onDismissRequest = {
                showDialog = false
            },
            onCancelClicked = {}
        )
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestSMSPermission(
    smsPermissions: MultiplePermissionsState,
    onSmsModalShown: () -> Unit = {},
    shouldShowModalDescription: Boolean = true
) {

    var showModal by remember { mutableStateOf(true) }

    LaunchedEffect(smsPermissions.shouldShowRationale) {
        if (smsPermissions.shouldShowRationale) showModal = true
    }

    if (showModal && shouldShowModalDescription) {
        AppSimpleBottomSheet(
            title = stringResource(id = R.string.sms_access),
            message = stringResource(id = R.string.sms_access_message),
            confirmButtonText = stringResource(id = R.string.I_give_access_permission),
            onDismissRequest = {
                showModal = false
                smsPermissions.launchMultiplePermissionRequest()
                onSmsModalShown()
            },
            onConfirmClicked = {
                showModal = false
                smsPermissions.launchMultiplePermissionRequest()
                onSmsModalShown()
            }
        )
    }

}