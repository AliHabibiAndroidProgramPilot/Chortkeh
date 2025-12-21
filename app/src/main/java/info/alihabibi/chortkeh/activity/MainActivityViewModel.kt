package info.alihabibi.chortkeh.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val datastoreUseCase: DatastoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainActivityState())
    val uiState: StateFlow<MainActivityState> = _uiState.asStateFlow()

    fun onEvent(event: MainActivityIntent) {
        when (event) {

            is MainActivityIntent.ChangeFirstLaunchFlag -> changeFirstLaunchFlag(event.value)

            is MainActivityIntent.IsFirstLaunch -> isFirstLaunch()

        }
    }

    private fun changeFirstLaunchFlag(firstLaunchFlag: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isFirstLaunch = firstLaunchFlag) }
            datastoreUseCase.saveFirstLaunchUseCase.invoke(firstLaunchFlag)
        }
    }

    private fun isFirstLaunch() {
        viewModelScope.launch {
            val isFirstLaunch = datastoreUseCase.getIsAppFirstLaunch.invoke().first()
            _uiState.update { it.copy(isFirstLaunch = isFirstLaunch) }
        }
    }

}

data class MainActivityState(
    val isFirstLaunch: Boolean = true,
    val isLoggedOn: Boolean = false
)

sealed interface MainActivityIntent {

    object IsFirstLaunch : MainActivityIntent

    data class ChangeFirstLaunchFlag(val value: Boolean) : MainActivityIntent

}