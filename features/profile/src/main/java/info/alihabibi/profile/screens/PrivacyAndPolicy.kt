package info.alihabibi.profile.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.ui.dialogs.AppDialog
import info.alihabibi.ui.headrs.AppHeader

@Composable
fun PrivacyAndPolicyDestination(
    onBackPressed: () -> Unit
) {

    PrivacyAndPolicyScreen(onBackPressed = onBackPressed)

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PrivacyAndPolicyScreen(
    onBackPressed: () -> Unit
) {

    val smsPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )
    )
    val context = LocalContext.current
    var showSmsPermissionDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showSmsPermissionDialog)
        AppDialog(
            title = stringResource(id = R.string.sms_access),
            message =
                if (!smsPermissionsState.allPermissionsGranted)
                    stringResource(id = R.string.grant_sms_permission)
                else
                    stringResource(id = R.string.revoke_sms_permission_message),
            confirmButtonText = stringResource(id = R.string.confirm),
            cancelButtonText = stringResource(id = R.string.dismiss),
            onDismissRequest = {
                showSmsPermissionDialog = false
            },
            onCancelClicked = {
                showSmsPermissionDialog = false
            },
            onConfirmClicked = {
                when {
                    smsPermissionsState.shouldShowRationale -> {
                        smsPermissionsState.launchMultiplePermissionRequest()
                    }

                    else -> {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                }
                showSmsPermissionDialog = false
            }
        )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader(
            title = stringResource(id = R.string.privacy_policy),
            isMenuAvailable = false,
            onNavigationClick = onBackPressed
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(shape = RoundedCornerShape(20))
                    .clickable {
                        showSmsPermissionDialog = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Switch(
                    checked = smsPermissionsState.allPermissionsGranted,
                    onCheckedChange = {
                        showSmsPermissionDialog = true
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        uncheckedThumbColor = White,
                        checkedTrackColor = Primary,
                        uncheckedTrackColor = Gray5,
                        uncheckedBorderColor = Gray5
                    )
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, end = 8.dp),
                    text = stringResource(id = R.string.sms_access),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.sms),
                    contentDescription = null,
                    tint = Gray9
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(shape = RoundedCornerShape(20))
                    .clickable(enabled = false) {}
                    .alpha(alpha = 0.45f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Switch(
                    checked = false,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        uncheckedThumbColor = White,
                        checkedTrackColor = Primary,
                        uncheckedTrackColor = Gray5,
                        uncheckedBorderColor = Gray5
                    )
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, end = 8.dp),
                    text = stringResource(id = R.string.enter_with_biometrics),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.finger_print),
                    contentDescription = null,
                    tint = Gray9
                )

            }

        }

    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PrivacyAndPolicyPreview() {

    PrivacyAndPolicyScreen(
        onBackPressed = {}
    )

}