package info.alihabibi.channels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import info.alihabibi.common.banks.Bank
import info.alihabibi.common.banks.BankCardIdentifier
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChannelsViewModel : ViewModel() {

    private val _addChannelUiState = MutableStateFlow(AddChannelUiState())
    val addChannelUiState: StateFlow<AddChannelUiState> = _addChannelUiState.asStateFlow()

    fun onEvent(event: ChannelsUiIntent) {
        when (event) {

            is ChannelsUiIntent.OnChannelTypeChanged -> changeChannelType(event.isBankAccountChannel)

            is ChannelsUiIntent.OnCardNumberChanged -> changeCardNumber(event.cardNumber)

            is ChannelsUiIntent.OnInitialBalanceChanged -> changeInitialBalance(event.balance)

            is ChannelsUiIntent.OnChannelNameChanged -> changeChannelName(event.name)

            is ChannelsUiIntent.OnChannelIconChanged -> changeChannelIcon(event.icon)

            is ChannelsUiIntent.OnSaveChannel -> saveChannel()

        }
    }

    private fun changeChannelType(isBankAccountChannel: Boolean) {
        _addChannelUiState.update {
            AddChannelUiState(isBankAccountChannel = isBankAccountChannel)
        }
    }

    private fun changeCardNumber(cardNumber: String) {
        // safe with recomposition - StateFlow won't emit duplicated values!
        val digits = cardNumber
            .filter(Char::isDigit)
            .take(16)
        _addChannelUiState.update { it.copy(cardNumber = digits) }
    }

    private fun changeInitialBalance(balance: String) {
        val balanceWithLimit = balance
            .filter(Char::isDigit)
            .take(20)
        _addChannelUiState.update { it.copy(initialBalance = balanceWithLimit) }
    }

    private fun changeChannelName(name: String) {
        if (name.length < 30)
            _addChannelUiState.update { it.copy(channelName = name) }
    }

    private fun changeChannelIcon(icon: ChannelIconOptionUiModel) {
        _addChannelUiState.update { it.copy(channelIcon = icon) }
    }

    private fun saveChannel() {

    }

    /*private fun fetchBank(cardNumber: String): Bank {
        val tmpBank = BankCardIdentifier.identify(cardNumber.take(6))
        return BankCardIdentifier.identifyPossibleNeoBanks(cardNumber.take(8), tmpBank)
    }*/

}

sealed interface ChannelsUiIntent {

    data class OnChannelTypeChanged(val isBankAccountChannel: Boolean) : ChannelsUiIntent

    data class OnCardNumberChanged(val cardNumber: String) : ChannelsUiIntent

    data class OnInitialBalanceChanged(val balance: String) : ChannelsUiIntent

    data class OnChannelNameChanged(val name: String) : ChannelsUiIntent

    data class OnChannelIconChanged(val icon: ChannelIconOptionUiModel) : ChannelsUiIntent

    data object OnSaveChannel : ChannelsUiIntent

}

@Immutable
data class AddChannelUiState(
    val isBankAccountChannel: Boolean = true,
    val channelName: String = "",
    val cardNumber: String = "",
    val initialBalance: String = "",
    val channelIcon: ChannelIconOptionUiModel? = null
) {

    val isChannelRegisterButtonEnabled: Boolean
        get() {
            return if (isBankAccountChannel) {
                channelName.isNotEmpty() && cardNumber.isNotEmpty() && initialBalance.isNotEmpty()
            } else {
                channelName.isNotEmpty() && initialBalance.isNotEmpty()
            }
        }

}