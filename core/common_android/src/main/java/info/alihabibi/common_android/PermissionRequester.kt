package info.alihabibi.common_android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.ui.dialogs.AppDialogs

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    requestPermission: String,
    rationalDialogTitle: String,
    rationalDialogMessage: String,
    rationalDialogConfirmButtonText: String,
    rationalDialogCancelButtonText: String
) {

    val permission = rememberPermissionState(permission = requestPermission)
    if (!permission.status.isGranted) LaunchedEffect(Unit) { permission.launchPermissionRequest() } else return
    var showDialog by remember { mutableStateOf(true) }
    val shouldShowRationale =
        (permission.status as? PermissionStatus.Denied)?.shouldShowRationale == true
    if (shouldShowRationale && showDialog) {
        AppDialogs(
            title = rationalDialogTitle,
            message = rationalDialogMessage,
            confirmButtonText = rationalDialogConfirmButtonText,
            cancelButtonText = rationalDialogCancelButtonText,
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