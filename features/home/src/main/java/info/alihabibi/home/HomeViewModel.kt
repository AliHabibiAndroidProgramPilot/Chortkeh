package info.alihabibi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.channel.ChannelUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreUseCases: DatastoreUseCases,
    channelsUseCase: ChannelUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        combine(
            dataStoreUseCases.getIsSmsModalShownUseCase.invoke(),
            channelsUseCase.getChannelsUseCase.invoke()
        ) { isSmsModalShown, channels ->
            HomeUiState(
                isSmsModalShown = isSmsModalShown,
                channels = channels.map(Channel::toUiModel)
            )
        }
            .onEach { state -> _uiState.update { state } }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: HomeUiIntent) {
        when (event) {

            is HomeUiIntent.SaveSmsPermissionModalShownState -> saveSmsPermissionModalShownState(event.value)

        }
    }

    private fun saveSmsPermissionModalShownState(value: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSmsModalShown = value) }
            dataStoreUseCases.saveSmsModalShownStateUseCase.invoke(value)
        }
    }

}

sealed interface HomeUiIntent {

    data class SaveSmsPermissionModalShownState(val value: Boolean) : HomeUiIntent

}

data class HomeUiState(
    val isSmsModalShown: Boolean = false,
    val channels: List<ChannelUiModel> = emptyList()
)