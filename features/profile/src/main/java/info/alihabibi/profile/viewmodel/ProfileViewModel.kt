package info.alihabibi.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.mapper.toUiOption
import info.alihabibi.model.ui_model.CurrenciesOptionUiModel
import info.alihabibi.model.ui_model.GenderOptionUiModel
import info.alihabibi.model.ui_model.UserAccountInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val dataStoreUseCases: DatastoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProfileUiIntent) {
        when (event) {

            is ProfileUiIntent.Init -> init()

            is ProfileUiIntent.SavePreferredCurrency -> savePreferredCurrency(event.currency)

            is ProfileUiIntent.OnGenderChanged -> changeGender(event.gender)

            is ProfileUiIntent.OnFullNameChanged -> changeFullName(event.fullName)

            is ProfileUiIntent.OnPhoneChanged -> changePhone(event.phone)

            is ProfileUiIntent.OnSaveValues -> saveUserAccountInfo()

        }
    }

    private fun init() {
        viewModelScope.launch {
            val currency = dataStoreUseCases.getPreferredCurrencyUseCase.invoke()
                .map { it.toUiOption() }
                .first()
            val userAccountInfo = dataStoreUseCases.getUserAccountInfoUseCase.invoke()
                .map { it.toUiModel() }
                .first()
            val chunkedPhone = userAccountInfo.phone.chunked(4).joinToString(" ")
            _uiState.update {
                it.copy(
                    currency = currency,
                    // UserAccountInfo destination is using this data
                    fullName = userAccountInfo.fullName,
                    userPhone = userAccountInfo.phone,
                    userGender = userAccountInfo.gender,
                    // profile destination is using this data
                    userAccountInfo = userAccountInfo.copy(phone = chunkedPhone)
                )
            }
        }
    }


    private fun savePreferredCurrency(currency: CurrenciesOptionUiModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(currency = currency) }
            val domainCurrency = currency.toDomain()
            dataStoreUseCases.savePreferredCurrencyUseCase.invoke(domainCurrency)
        }
    }

    private fun saveUserAccountInfo() {
        viewModelScope.launch {
            val userInfo = UserAccountInfoUiModel(
                fullName = _uiState.value.fullName,
                phone = _uiState.value.userPhone,
                gender = _uiState.value.userGender,
                profileImageRes = 0
            ).toDomain()
            dataStoreUseCases.saveUserAccountInfoUseCase.invoke(userInfo)
        }
    }

    private fun changeFullName(fullName: String) {
        _uiState.update { it.copy(fullName = fullName) }
    }

    private fun changePhone(phone: String) {
        val digits = phone
            .filter(Char::isDigit)
            .take(11)
        _uiState.update { it.copy(userPhone = digits) }
    }

    private fun changeGender(gender: GenderOptionUiModel) {
        _uiState.update { it.copy(userGender = gender) }
    }

}

sealed interface ProfileUiIntent {

    data object Init : ProfileUiIntent

    data class SavePreferredCurrency(val currency: CurrenciesOptionUiModel) : ProfileUiIntent

    data class OnFullNameChanged(val fullName: String) : ProfileUiIntent

    data class OnPhoneChanged(val phone: String) : ProfileUiIntent

    data class OnGenderChanged(val gender: GenderOptionUiModel) : ProfileUiIntent

    data object OnSaveValues : ProfileUiIntent

}

data class ProfileUiState(
    val currency: CurrenciesOptionUiModel = CurrenciesOptionUiModel.TOMAN,
    val userAccountInfo: UserAccountInfoUiModel = UserAccountInfoUiModel(),
    val fullName: String = "",
    val userPhone: String = "",
    val userGender: GenderOptionUiModel = GenderOptionUiModel.UNKNOWN
)