package info.alihabibi.common_android

import android.Manifest
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
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.designsystem.R
import info.alihabibi.ui.dialogs.AppDialogs
import info.alihabibi.ui.dialogs.AppSimpleBottomSheet

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission() {

    val permission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(permission.status.isGranted) {
        if (!permission.status.isGranted) {
            permission.launchPermissionRequest()
        }
    }
    var showDialog by remember { mutableStateOf(true) }
    val shouldShowRationale =
        (permission.status as? PermissionStatus.Denied)?.shouldShowRationale == true
    if (shouldShowRationale && showDialog) {
        AppDialogs(
            title = stringResource(id = R.string.notification_access),
            message = stringResource(id = R.string.notification_access_message),
            confirmButtonText = stringResource(id = R.string.I_give_permission),
            cancelButtonText = stringResource(id = R.string.dismiss),
            onConfirmClicked = {
                permission.launchPermissionRequest()
                showDialog = false
            },
            onDismissRequest = {
                showDialog = false
            }
        )
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestSMSPermission(
    onSmsModalShown: () -> Unit = {},
    shouldShowModalDescription: Boolean = true
) {

    val permission = rememberPermissionState(permission = Manifest.permission.RECEIVE_SMS)
    var showModal by remember { mutableStateOf(true) }
    val shouldShowRationale =
        (permission.status as? PermissionStatus.Denied)?.shouldShowRationale == true

    LaunchedEffect(shouldShowRationale) {
        if (shouldShowRationale) showModal = true
    }

    if (showModal && shouldShowModalDescription) {
        AppSimpleBottomSheet(
            title = stringResource(id = R.string.sms_access),
            message = stringResource(id = R.string.sms_access_message),
            confirmButtonText = stringResource(id = R.string.I_give_access_permission),
            onDismissRequest = {
                showModal = false
                onSmsModalShown()
            },
            onConfirmClicked = {
                showModal = false
                permission.launchPermissionRequest()
                onSmsModalShown()
            }
        )
    }

}