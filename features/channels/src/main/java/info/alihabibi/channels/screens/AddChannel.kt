package info.alihabibi.channels.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.channels.ChannelUiState
import info.alihabibi.channels.ChannelsUiIntent
import info.alihabibi.channels.ChannelsViewModel
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray3
import info.alihabibi.designsystem.theme.Gray6
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.headrs.AppHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddChannelDestination(
    viewModel: ChannelsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val addChannelUiState by viewModel.channelUiState.collectAsStateWithLifecycle()

    AddNewChannelScreen(
        uiState = addChannelUiState,
        onChannelTypeChange = { isBankAccountChannel ->
            viewModel.onEvent(ChannelsUiIntent.ChannelTypeChanged(isBankAccountChannel))
        },
        onCardNumberChange = { cardNumber ->
            viewModel.onEvent(ChannelsUiIntent.CardNumberChanged(cardNumber))
        },
        onInitialBalanceChanged = { balance ->
            viewModel.onEvent(ChannelsUiIntent.BalanceChanged(balance))
        },
        onChannelNameChanged = { name ->
            viewModel.onEvent(ChannelsUiIntent.ChannelNameChanged(name))
        },
        onChannelIconChanged = { icon ->
            viewModel.onEvent(ChannelsUiIntent.ChannelIconChanged(icon))
        },
        onSaveChannel = {
            viewModel.onEvent(ChannelsUiIntent.SaveChannel)
            onBackPressed()
        },
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AddNewChannelScreen(
    uiState: ChannelUiState,
    onSaveChannel: () -> Unit = {},
    onChannelTypeChange: (isBankAccountChannel: Boolean) -> Unit = {},
    onChannelNameChanged: (name: String) -> Unit = {},
    onCardNumberChange: (cardNumber: String) -> Unit = {},
    onInitialBalanceChanged: (balance: String) -> Unit = {},
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
        ) {

            AppHeader(
                title = stringResource(id = R.string.add_new_channel),
                isActionAvailable = false,
                onNavigationClicked = onBackPressed
            )

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(height = 65.dp)
                        .border(
                            width = 1.dp,
                            color = Gray3,
                            shape = RoundedCornerShape(size = 12.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = stringResource(id = R.string.input_channel_type),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.width(width = 8.dp))

                    RadioButton(
                        modifier = Modifier.scale(scale = 0.8f),
                        selected = uiState.isBankAccountChannel,
                        onClick = {
                            if (!uiState.isBankAccountChannel)
                                onChannelTypeChange(true)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Gray6
                        )
                    )

                    Text(
                        modifier = Modifier.offset(x = (-12).dp),
                        text = stringResource(id = R.string.bank_card),
                        style = MaterialTheme.typography.labelLarge
                    )

                    RadioButton(
                        modifier = Modifier.scale(scale = 0.8f),
                        selected = !uiState.isBankAccountChannel,
                        onClick = {
                            if (uiState.isBankAccountChannel)
                                onChannelTypeChange(false)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Gray6
                        )
                    )

                    Text(
                        modifier = Modifier.offset(x = (-12).dp),
                        text = stringResource(id = R.string.other_source),
                        style = MaterialTheme.typography.labelLarge
                    )


                }

            }

            Crossfade(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth(),
                targetState = uiState.isBankAccountChannel
            ) { isBankAccount ->
                if (isBankAccount)
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
                        onInitialBalanceChange = onInitialBalanceChanged
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
                        onInitialBalanceChange = onInitialBalanceChanged,
                        onChannelIconChanged = onChannelIconChanged
                    )
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(bottom = 12.dp),
                onClick = onSaveChannel,
                text = stringResource(id = R.string.register_channel),
                enabled = uiState.isChannelRegisterButtonEnabled
            )

        }

    }

}