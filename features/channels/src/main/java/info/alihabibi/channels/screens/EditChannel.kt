package info.alihabibi.channels.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.channels.ChannelUiState
import info.alihabibi.channels.ChannelsUiIntent
import info.alihabibi.channels.ChannelsViewModel
import info.alihabibi.designsystem.R
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.headrs.AppHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditChannelDestination(
    viewModel: ChannelsViewModel = koinViewModel(),
    editingChannelId: Int? = null,
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.channelUiState.collectAsStateWithLifecycle()

    LaunchedEffect(editingChannelId) {
        editingChannelId?.let {
            viewModel.onEvent(ChannelsUiIntent.GetChannelById(editingChannelId))
        }
    }

    EditChannelScreen(
        uiState = uiState,
        onEditChannel = {
            viewModel.onEvent(ChannelsUiIntent.EditChannel(editingChannelId ?: 0))
            onBackPressed()
        },
        onChannelDelete = {
            // show Dialog before
        },
        onCardNumberChange = { cardNumber ->
            viewModel.onEvent(ChannelsUiIntent.CardNumberChanged(cardNumber))
        },
        onBalanceChanged = { balance ->
            viewModel.onEvent(ChannelsUiIntent.BalanceChanged(balance))
        },
        onChannelNameChanged = { name ->
            viewModel.onEvent(ChannelsUiIntent.ChannelNameChanged(name))
        },
        onChannelIconChanged = { icon ->
            viewModel.onEvent(ChannelsUiIntent.ChannelIconChanged(icon))
        },
        onBackPressed = onBackPressed
    )

}

@Composable
private fun EditChannelScreen(
    uiState: ChannelUiState,
    onChannelDelete: () -> Unit = {},
    onEditChannel: () -> Unit = {},
    onChannelNameChanged: (name: String) -> Unit = {},
    onCardNumberChange: (cardNumber: String) -> Unit = {},
    onBalanceChanged: (balance: String) -> Unit = {},
    onChannelIconChanged: (icon: ChannelIconOptionUiModel) -> Unit = {},
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.weight(weight = 1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            AppHeader(
                title = stringResource(id = R.string.edit_channel),
                actionIcon = painterResource(id = R.drawable.trash),
                onNavigationClicked = onBackPressed,
                onActionClicked = onChannelDelete
            )

            if (uiState.isBankAccountChannel)
                BankAccountChannelContent(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .imePadding()
                        .verticalScroll(state = rememberScrollState()),
                    channelName = uiState.channelName,
                    cardNumber = uiState.cardNumber,
                    initialBalance = uiState.channelBalance,
                    onChannelNameChange = onChannelNameChanged,
                    onCardNumberChange = onCardNumberChange,
                    onInitialBalanceChange = onBalanceChanged
                )
            else
                OtherChannelContent(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .imePadding()
                        .verticalScroll(state = rememberScrollState()),
                    channelName = uiState.channelName,
                    initialBalance = uiState.channelBalance,
                    channelSelectedIcon = uiState.channelIcon,
                    onChannelNameChange = onChannelNameChanged,
                    onInitialBalanceChange = onBalanceChanged,
                    onChannelIconChanged = onChannelIconChanged
                )

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(bottom = 12.dp),
                onClick = onEditChannel,
                text = stringResource(id = R.string.save_changes),
                enabled = uiState.isChannelRegisterButtonEnabled
            )

        }

    }

}