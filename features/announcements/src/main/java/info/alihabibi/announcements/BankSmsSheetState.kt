package info.alihabibi.announcements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Stable
internal class BankSmsSheetState {

    var isSheetVisible by mutableStateOf(false)
        private set

    var selectedAnnouncementId by mutableLongStateOf(-1)
        private set

    fun showSheet(announcementId: Long) {
        selectedAnnouncementId = announcementId
        isSheetVisible = true
    }

    fun onDismissed() {
        isSheetVisible = false
        selectedAnnouncementId = -1
    }

}