package info.alihabibi.channels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.banks.Bank
import info.alihabibi.common.banks.BankCardIdentifier
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.model.ui_model.channel.ChannelUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChannelsViewModel(
    private val channelsUseCases: ChannelUseCases
) : ViewModel() {

    private val _addChannelUiState = MutableStateFlow(AddChannelUiState())
    val addChannelUiState: StateFlow<AddChannelUiState> = _addChannelUiState.asStateFlow()

    private val _channels: MutableStateFlow<List<ChannelUiModel>> = MutableStateFlow(emptyList())
    val channels: StateFlow<List<ChannelUiModel>> = _channels.asStateFlow()

    init {
        channelsUseCases.getChannelsUseCase.invoke()
            .onEach { channels ->
                _channels.value = channels
                    .filterNot { it.isAppDefaultChannel }
                    .map(Channel::toUiModel)
            }
            .launchIn(viewModelScope)
    }

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
        viewModelScope.launch {
            val state = _addChannelUiState.value
            val hasEmptyFields = if (state.isBankAccountChannel)
                state.channelName.isEmpty() || state.initialBalance.isEmpty() || state.cardNumber.isEmpty()
            else
                state.channelName.isEmpty() || state.initialBalance.isEmpty() || state.channelIcon == null
            if (hasEmptyFields) return@launch

            val bank = fetchBank(state.cardNumber)
            // May get exception on filling icon field, it's a dangerous code I might change it later!
            try {
                val channel = ChannelUiModel(
                    channelName = state.channelName,
                    channelBalance = state.initialBalance,
                    isBankCardChannel = state.isBankAccountChannel,
                    cardNumber = state.cardNumber,
                    icon = state.channelIcon ?: ChannelIconOptionUiModel.valueOf(bank.name),
                    bankName = if (bank == Bank.UNKNOWN) null else bank.name
                ).toDomain()

                channelsUseCases.saveChannelUseCase.invoke(channel)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                val channel = ChannelUiModel(
                    channelName = state.channelName,
                    channelBalance = state.initialBalance,
                    isBankCardChannel = state.isBankAccountChannel,
                    cardNumber = state.cardNumber,
                    icon = ChannelIconOptionUiModel.UNKNOWN,
                    bankName = if (bank == Bank.UNKNOWN) null else bank.name
                ).toDomain()

                channelsUseCases.saveChannelUseCase.invoke(channel)
            } finally {
                _addChannelUiState.update {
                    AddChannelUiState()
                }
            }
        }
    }

    private fun fetchBank(cardNumber: String): Bank {
        val tmpBank = BankCardIdentifier.identify(cardNumber.take(6))
        return BankCardIdentifier.identifyPossibleNeoBanks(cardNumber.take(8), tmpBank)
    }

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
                channelName.isNotEmpty() && initialBalance.isNotEmpty() && channelIcon != null
            }
        }
}