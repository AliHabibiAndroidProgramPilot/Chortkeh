package info.alihabibi.channels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChannelsViewModel : ViewModel() {

    private val _addChannelUiState = MutableStateFlow(AddChannelUiState())
    val addChannelUiState: StateFlow<AddChannelUiState> = _addChannelUiState.asStateFlow()

    fun onEvent(event: ChannelsUiIntent) {
        when(event) {

            is ChannelsUiIntent.OnChannelTypeChanged -> changeChannelType(event.isBankAccountChannel)

            is ChannelsUiIntent.OnCardNumberChanged -> changeCardNumber(event.cardNumber)

        }
    }

    private fun changeChannelType(isBankAccountChannel: Boolean) {
        _addChannelUiState.update { it.copy(isBankAccountChannel = isBankAccountChannel) }
    }

    private fun changeCardNumber(cardNumber: String) {
        _addChannelUiState.update { it.copy(cardNumber = cardNumber) }
    }

}

sealed interface ChannelsUiIntent {

    data class OnChannelTypeChanged(val isBankAccountChannel: Boolean) : ChannelsUiIntent

    data class OnCardNumberChanged(val cardNumber: String) : ChannelsUiIntent

}

@Immutable
data class AddChannelUiState(
    val isBankAccountChannel: Boolean = true,
    val cardNumber: String = "",
    val initialBalance: String = ""
)