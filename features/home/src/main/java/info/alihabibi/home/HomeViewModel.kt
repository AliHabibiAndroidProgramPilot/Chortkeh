package info.alihabibi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.Utils.loog
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.Currencies
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

            is HomeUiIntent.Init -> init()

            is HomeUiIntent.SaveSmsPermissionModalShownState ->
                saveSmsPermissionModalShownState(event.value)

            is HomeUiIntent.SavePreferredCurrency -> savePreferredCurrency(event.currency)

        }
    }

    private fun init() {
        viewModelScope.launch {
            val smsModalShownState = dataStoreUseCases.getIsSmsModalShownUseCase.invoke().first()
            val currency = dataStoreUseCases.getPreferredCurrency.invoke().first()
            currency.name.loog()
            _uiState.update {
                it.copy(
                    isSmsModalShown = smsModalShownState,
                    currency = currency
                )
            }
        }
    }

    private fun saveSmsPermissionModalShownState(value: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSmsModalShown = value) }
            dataStoreUseCases.saveSmsModalShownStateUseCase.invoke(value)
        }
    }

    private fun savePreferredCurrency(currency: Currencies) {
        viewModelScope.launch {
            currency.loog(param = "saved:")
            _uiState.update { it.copy(currency = currency) }
            dataStoreUseCases.savePreferredCurrency.invoke(currency)
        }
    }

}

sealed interface HomeUiIntent {

    object Init : HomeUiIntent

    data class SaveSmsPermissionModalShownState(val value: Boolean) : HomeUiIntent

    data class SavePreferredCurrency(val currency: Currencies) : HomeUiIntent

}

data class HomeUiState(
    val isSmsModalShown: Boolean? = null,
    val currency: Currencies = Currencies.TOMAN
)