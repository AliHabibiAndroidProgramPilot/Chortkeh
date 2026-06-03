package info.alihabibi.user_account_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.Genders
import info.alihabibi.domain.models.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserAccountInfoViewModel(
    private val dataStoreUseCases: DatastoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserAccountInfoUiState())
    val uiState: StateFlow<UserAccountInfoUiState> = _uiState.asStateFlow()

    fun onEvent(event: UserAccountInfoUiIntent) {
        when (event) {

            is UserAccountInfoUiIntent.Init -> init()

            is UserAccountInfoUiIntent.OnGenderChanged -> changeGender(event.gender)

            is UserAccountInfoUiIntent.OnFullNameChanged -> changeFullName(event.fullName)

            is UserAccountInfoUiIntent.OnPhoneChanged -> changePhone(event.phone)

            is UserAccountInfoUiIntent.OnSaveValues -> saveUserAccountInfo()

        }
    }

    private fun init() {
        viewModelScope.launch {
            val userAccountInfo = dataStoreUseCases.getUserAccountInfoUseCase.invoke().first()
            _uiState.update {
                it.copy(
                    fullName = userAccountInfo.fullName,
                    userPhone = userAccountInfo.phone,
                    userGender = userAccountInfo.gender
                )
            }
        }
    }

    private fun saveUserAccountInfo() {
        viewModelScope.launch {
            val userInfo = UserInfo(
                fullName = _uiState.value.fullName,
                phone = _uiState.value.userPhone,
                gender = _uiState.value.userGender
            )
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

    private fun changeGender(gender: Genders) {
        _uiState.update { it.copy(userGender = gender) }
    }

}

sealed interface UserAccountInfoUiIntent {

    data object Init : UserAccountInfoUiIntent

    data class OnFullNameChanged(val fullName: String) : UserAccountInfoUiIntent

    data class OnPhoneChanged(val phone: String) : UserAccountInfoUiIntent

    data class OnGenderChanged(val gender: Genders) : UserAccountInfoUiIntent

    data object OnSaveValues : UserAccountInfoUiIntent

}

data class UserAccountInfoUiState(
    val fullName: String = "",
    val userPhone: String = "",
    val userGender: Genders = Genders.UNKNOW
)