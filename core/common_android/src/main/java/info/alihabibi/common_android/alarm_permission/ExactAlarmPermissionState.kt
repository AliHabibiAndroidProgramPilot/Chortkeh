package info.alihabibi.common_android.alarm_permission

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import info.alihabibi.common_android.AndroidUtils

@Stable
class ExactAlarmPermissionState internal constructor(private val context: Context) {

    var isGranted by mutableStateOf(AndroidUtils.canScheduleExactAlarms(context))

    internal fun refreshIsGrantedState() {
        isGranted = AndroidUtils.canScheduleExactAlarms(context)
    }

    fun launchSetting() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
        val packageUri = Uri.fromParts("package", context.packageName, null)
        try {
            context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, packageUri))
        } catch (_: ActivityNotFoundException) {
            context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri))
        }
    }

}