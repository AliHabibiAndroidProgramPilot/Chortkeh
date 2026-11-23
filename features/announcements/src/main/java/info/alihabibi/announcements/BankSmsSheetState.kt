package info.alihabibi.announcements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import info.alihabibi.model.ui_model.announcement.AnnouncementUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Stable
internal class BankSmsSheetState(
    val sheetState: SheetState,
    private val scope: CoroutineScope
) {

    var isSheetVisible by mutableStateOf(false)
        private set

    var selectedAnnouncement by mutableStateOf<AnnouncementUiModel?>(null)
        private set

    fun showSheet(announcement: AnnouncementUiModel) {
        selectedAnnouncement = announcement
        isSheetVisible = true
    }

    fun hideSheet() {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismissed()
        }
    }

    fun onDismissed() {
        isSheetVisible = false
        selectedAnnouncement = null
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberBankSmsSheetState(): BankSmsSheetState {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    return remember(sheetState, scope) {
        BankSmsSheetState(
            sheetState = sheetState,
            scope = scope
        )
    }
}