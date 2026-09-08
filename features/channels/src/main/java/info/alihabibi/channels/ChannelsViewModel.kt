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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChannelsViewModel(
    private val channelsUseCases: ChannelUseCases
) : ViewModel() {

    private val _channelUiState = MutableStateFlow(ChannelUiState())
    val channelUiState: StateFlow<ChannelUiState> = _channelUiState.asStateFlow()

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

            is ChannelsUiIntent.ChannelTypeChanged -> changeChannelType(event.isBankAccountChannel)

            is ChannelsUiIntent.CardNumberChanged -> changeCardNumber(event.cardNumber)

            is ChannelsUiIntent.BalanceChanged -> changeInitialBalance(event.balance)

            is ChannelsUiIntent.ChannelNameChanged -> changeChannelName(event.name)

            is ChannelsUiIntent.ChannelIconChanged -> changeChannelIcon(event.icon)

            is ChannelsUiIntent.GetChannelById -> getChannelById(event.id)

            is ChannelsUiIntent.EditChannel -> editChannel(event.id)

            is ChannelsUiIntent.DeleteChannel -> deleteChannel(event.id)

            is ChannelsUiIntent.SaveChannel -> saveChannel()

            is ChannelsUiIntent.ResetChannelsDraft -> resetDraft()

        }
    }

    private fun changeChannelType(isBankAccountChannel: Boolean) {
        _channelUiState.update {
            ChannelUiState(isBankAccountChannel = isBankAccountChannel)
        }
    }

    private fun changeCardNumber(cardNumber: String) {
        // For accurate icon recognition we need to call fetchBank on each value change
        if (cardNumber.length <= 16) {
            val digits = cardNumber.filter(Char::isDigit)
            val bank = fetchBank(digits)
            val possibleIcon: ChannelIconOptionUiModel? =
                ChannelIconOptionUiModel.entries.find { it.name == bank.name }
            _channelUiState.update {
                it.copy(cardNumber = digits, channelIcon = possibleIcon)
            }
        }
    }

    private fun changeInitialBalance(balance: String) {
        val balanceWithLimit = balance
            .filter(Char::isDigit)
            .take(20)
        _channelUiState.update { it.copy(channelBalance = balanceWithLimit) }
    }

    private fun changeChannelName(name: String) {
        if (name.length < 30)
            _channelUiState.update { it.copy(channelName = name) }
    }

    private fun changeChannelIcon(icon: ChannelIconOptionUiModel) {
        _channelUiState.update { it.copy(channelIcon = icon) }
    }

    private fun saveChannel() {
        viewModelScope.launch {
            val state = _channelUiState.value
            val hasEmptyFields = if (state.isBankAccountChannel)
                state.channelName.isEmpty() || state.channelBalance.isEmpty() || state.cardNumber.isEmpty()
            else
                state.channelName.isEmpty() || state.channelBalance.isEmpty() || state.channelIcon == null
            if (hasEmptyFields) return@launch

            val bank = fetchBank(state.cardNumber)

            val channel = ChannelUiModel(
                channelName = state.channelName,
                channelBalance = state.channelBalance,
                isBankCardChannel = state.isBankAccountChannel,
                cardNumber = state.cardNumber,
                icon = state.channelIcon ?: ChannelIconOptionUiModel.UNKNOWN,
                bankName = if (bank == Bank.UNKNOWN) null else bank.name
            ).toDomain()
            channelsUseCases.saveChannelUseCase.invoke(channel)

            resetDraft()
        }
    }

    private fun editChannel(id: Int) {
        viewModelScope.launch {
            val state = _channelUiState.value
            val hasEmptyFields = if (state.isBankAccountChannel)
                state.channelName.isEmpty() || state.channelBalance.isEmpty() || state.cardNumber.isEmpty()
            else
                state.channelName.isEmpty() || state.channelBalance.isEmpty() || state.channelIcon == null
            if (hasEmptyFields) return@launch

            val bank = fetchBank(state.cardNumber)

            val channel = ChannelUiModel(
                id = id,
                channelName = state.channelName,
                channelBalance = state.channelBalance,
                isBankCardChannel = state.isBankAccountChannel,
                cardNumber = state.cardNumber,
                icon = state.channelIcon ?: ChannelIconOptionUiModel.UNKNOWN,
                bankName = if (bank == Bank.UNKNOWN) null else bank.name
            ).toDomain()
            channelsUseCases.updateChannelUseCase.invoke(channel)

            resetDraft()
        }
    }

    private fun deleteChannel(id: Int) {
        viewModelScope.launch {
            val state = _channelUiState.value
            val channel = ChannelUiModel(
                id = id,
                channelName = state.channelName,
                channelBalance = state.channelBalance,
                isBankCardChannel = state.isBankAccountChannel,
                icon = ChannelIconOptionUiModel.UNKNOWN,
            ).toDomain()
            channelsUseCases.deleteChannelUseCase.invoke(channel)
            resetDraft()
        }
    }

    private fun getChannelById(id: Int) {
        viewModelScope.launch {
            val channel = channelsUseCases.getChannelByIdUseCase.invoke(id).first().toUiModel(needsBalanceFormat = false)
            _channelUiState.update {
                ChannelUiState(
                    isBankAccountChannel = channel.isBankCardChannel,
                    channelName = channel.channelName,
                    channelBalance = channel.channelBalance,
                    cardNumber = channel.cardNumber,
                    channelIcon = channel.icon
                )
            }
        }
    }

    private fun resetDraft() {
        _channelUiState.update {
            ChannelUiState()
        }
    }

    private fun fetchBank(cardNumber: String): Bank {
        val tmpBank = BankCardIdentifier.identify(cardNumber.take(6))
        return BankCardIdentifier.identifyPossibleNeoBanks(cardNumber.take(8), tmpBank)
    }

}

sealed interface ChannelsUiIntent {

    data class ChannelTypeChanged(val isBankAccountChannel: Boolean) : ChannelsUiIntent

    data class CardNumberChanged(val cardNumber: String) : ChannelsUiIntent

    data class BalanceChanged(val balance: String) : ChannelsUiIntent

    data class ChannelNameChanged(val name: String) : ChannelsUiIntent

    data class ChannelIconChanged(val icon: ChannelIconOptionUiModel) : ChannelsUiIntent

    data class GetChannelById(val id: Int) : ChannelsUiIntent

    data class EditChannel(val id: Int) : ChannelsUiIntent

    data class DeleteChannel(val id: Int) : ChannelsUiIntent

    data object SaveChannel : ChannelsUiIntent

    data object ResetChannelsDraft : ChannelsUiIntent

}

@Immutable
data class ChannelUiState(
    val isBankAccountChannel: Boolean = true,
    val channelName: String = "",
    val channelBalance: String = "",
    val cardNumber: String = "",
    val channelIcon: ChannelIconOptionUiModel? = null
) {
    val isChannelRegisterButtonEnabled: Boolean
        get() {
            return if (isBankAccountChannel) {
                channelName.isNotEmpty() && cardNumber.isNotEmpty() && channelBalance.isNotEmpty()
            } else {
                channelName.isNotEmpty() && channelBalance.isNotEmpty() && channelIcon != null
            }
        }
}