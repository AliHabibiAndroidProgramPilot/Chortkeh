package info.alihabibi.announcements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.announcement.usecase.AnnouncementUseCases
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.announcement.AnnouncementUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val announcementUseCases: AnnouncementUseCases,
    private val datastoreUseCases: DatastoreUseCases
) : ViewModel() {

    val bankSMSAnnouncements: StateFlow<List<AnnouncementUiModel>>
        field = MutableStateFlow(emptyList())

    init {
        announcementUseCases.getAllSmsAnnouncements.invoke()
            .onEach { announcements ->
                bankSMSAnnouncements.update { announcements.map(Announcement::toUiModel) }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: AnnouncementUiIntent) {
        when (event) {

            is AnnouncementUiIntent.ClearRedBadge -> clearRedBadge()

            is AnnouncementUiIntent.DeleteAnnouncement -> deleteAnnouncement(event.id)

        }
    }

    private fun clearRedBadge() {
        viewModelScope.launch {
            val isOn = datastoreUseCases.getAnnouncementRedBadgeEnabled.invoke().firstOrNull()
            if (isOn == true)
                datastoreUseCases.saveHomeAnnouncementRedBadge.invoke(false)
        }
    }

    private fun deleteAnnouncement(id: Long) {
        viewModelScope.launch {
            announcementUseCases.deleteAnnouncementByIdUseCase.invoke(id)
        }
    }

}

sealed interface AnnouncementUiIntent {

    data object ClearRedBadge : AnnouncementUiIntent

    data class DeleteAnnouncement(val id: Long) : AnnouncementUiIntent

}