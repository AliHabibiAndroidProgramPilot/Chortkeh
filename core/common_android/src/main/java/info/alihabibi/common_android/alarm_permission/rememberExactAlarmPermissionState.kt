package info.alihabibi.common_android.alarm_permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

@Composable
fun rememberExactAlarmPermissionState(): ExactAlarmPermissionState {
    val context = LocalContext.current
    val state = remember(key1 = context) { ExactAlarmPermissionState(context) }
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) { state.refreshIsGrantedState() }
    return state
}