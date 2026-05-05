package info.alihabibi.chortkeh.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(datastoreUseCase: DatastoreUseCases) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> =
        datastoreUseCase.getIsAppFirstLaunch.invoke().map {
            MainActivityUiState.Success(it)
        }
            .catch { throw Exception("DATA STORE CAN NOT PROVIDE FIRST LAUNCH FLAG") }
            .stateIn(
                scope = viewModelScope,
                initialValue = MainActivityUiState.Loading,
                started = SharingStarted.Eagerly
            )

}

sealed interface MainActivityUiState {

    data object Loading : MainActivityUiState

    /**
     * Returns `true` if the ui state wasn't loaded yet and it should keep showing the splash screen.
     */
    fun shouldKeepSplashScreen(): Boolean = this is Loading

    data class Success(val isFirstLaunch: Boolean) : MainActivityUiState

}