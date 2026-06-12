package info.alihabibi.common_android.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object SnackBarController {
    private val _event = Channel<SnackBarEvent>()
    val event: Flow<SnackBarEvent> = _event.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _event.send(event)
    }
}