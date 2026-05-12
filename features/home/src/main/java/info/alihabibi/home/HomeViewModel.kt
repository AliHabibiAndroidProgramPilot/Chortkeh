package info.alihabibi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreUseCases: DatastoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiIntent) {
        when (event) {

            is HomeUiIntent.FetchSmsPermissionModalShownState -> fetchSmsPermissionModalShownState()

            is HomeUiIntent.SaveSmsPermissionModalShownState -> saveSmsPermissionModalShownState(event.value)

        }
    }

    private fun fetchSmsPermissionModalShownState() {
        viewModelScope.launch {
            val state = dataStoreUseCases.getIsSmsModalShownUseCase.invoke().first()
            _uiState.update { it.copy(isSmsModalShown = state) }
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

    object FetchSmsPermissionModalShownState : HomeUiIntent

    data class SaveSmsPermissionModalShownState(val value: Boolean) : HomeUiIntent

}

data class HomeUiState(
    val isSmsModalShown: Boolean? = null
)