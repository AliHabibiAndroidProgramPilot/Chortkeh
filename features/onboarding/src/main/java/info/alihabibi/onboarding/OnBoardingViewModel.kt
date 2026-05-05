package info.alihabibi.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val dataStoreUseCase: DatastoreUseCases
) : ViewModel() {

    fun onEvent(event: OnBoardingUiIntent) {
        when(event) {

            is OnBoardingUiIntent.ChangeIsFirstLaunchFlag -> changeIsFirstLaunchFlag(event.value)

        }
    }

    private fun changeIsFirstLaunchFlag(value: Boolean) {
        viewModelScope.launch {
            dataStoreUseCase.saveFirstLaunchUseCase.invoke(value)
        }
    }

}

sealed interface OnBoardingUiIntent {

    data class ChangeIsFirstLaunchFlag(val value: Boolean) : OnBoardingUiIntent

}