package info.alihabibi.channels.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
import info.alihabibi.ui.dialogs.SmsInboxSelectionBottomSheet
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
        onSmsSourceSelected = { smsSource ->
            viewModel.onEvent(ChannelsUiIntent.SelectChannelSmsSource(smsSource))
        },
        onFetchSmsSenders = {
            viewModel.onEvent(ChannelsUiIntent.FetchSmsSenders)
        },
        onSaveChannel = {
            viewModel.onEvent(ChannelsUiIntent.SaveChannel)
            onBackPressed()
        },
        onBackPressed = onBackPressed
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNewChannelScreen(
    uiState: ChannelUiState,
    onSaveChannel: () -> Unit = {},
    onChannelTypeChange: (isBankAccountChannel: Boolean) -> Unit = {},
    onChannelNameChanged: (name: String) -> Unit = {},
    onCardNumberChange: (cardNumber: String) -> Unit = {},
    onInitialBalanceChanged: (balance: String) -> Unit = {},
    onChannelIconChanged: (icon: ChannelIconOptionUiModel) -> Unit = {},
    onSmsSourceSelected: (smsSource: String) -> Unit = {},
    onFetchSmsSenders: () -> Unit = {},
    onBackPressed: () -> Unit
) {

    var showSmsInboxSelectionModal by remember { mutableStateOf(false) }
    if (uiState.smsSenders.isNotEmpty() && showSmsInboxSelectionModal)
        SmsInboxSelectionBottomSheet(
            smsSenders = uiState.smsSenders,
            smsSendersContent = uiState.smsSendersLatestMessage,
            bottomSheetTitle = stringResource(id = R.string.choose_sms_number),
            onSelectItem = { smsSource ->
                onSmsSourceSelected(smsSource)
                showSmsInboxSelectionModal = false
            },
            onDismissRequest = {
                showSmsInboxSelectionModal = false
            }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppHeader(
                    title = stringResource(id = R.string.add_new_channel),
                    isActionAvailable = false,
                    onNavigationClicked = onBackPressed
                )

                Spacer(modifier = Modifier.height(height = 8.dp))

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .heightIn(min = 60.dp)
                            .border(
                                width = 1.dp,
                                color = Gray3,
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        itemVerticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = stringResource(id = R.string.input_channel_type),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        ChannelOption(
                            label = stringResource(id = R.string.bank_card),
                            selected = uiState.isBankAccountChannel,
                            onClick = {
                                if (!uiState.isBankAccountChannel) onChannelTypeChange(true)
                            }
                        )

                        ChannelOption(
                            label = stringResource(id = R.string.other_source),
                            selected = !uiState.isBankAccountChannel,
                            onClick = {
                                if (uiState.isBankAccountChannel) onChannelTypeChange(false)
                            }
                        )

                    }

                }

                Spacer(modifier = Modifier.height(height = 12.dp))

                Crossfade(
                    modifier = Modifier.fillMaxWidth(),
                    targetState = uiState.isBankAccountChannel
                ) { isBankAccount ->
                    if (isBankAccount)
                        BankAccountChannelContent(
                            modifier = Modifier.fillMaxWidth(),
                            channelName = uiState.channelName,
                            cardNumber = uiState.cardNumber,
                            balance = uiState.channelBalance,
                            iconResId = uiState.channelIcon?.iconResId
                                ?: R.drawable.channel_ic_default,
                            selectedSmsSource = uiState.selectedSmsSource,
                            onChannelNameChange = onChannelNameChanged,
                            onCardNumberChange = onCardNumberChange,
                            onInitialBalanceChange = onInitialBalanceChanged,
                            onFetchSmsSenders = {
                                onFetchSmsSenders()
                                showSmsInboxSelectionModal = true
                            }
                        )
                    else
                        OtherChannelContent(
                            modifier = Modifier.fillMaxWidth(),
                            channelName = uiState.channelName,
                            initialBalance = uiState.channelBalance,
                            channelSelectedIcon = uiState.channelIcon,
                            onChannelNameChange = onChannelNameChanged,
                            onInitialBalanceChange = onInitialBalanceChanged,
                            onChannelIconChanged = onChannelIconChanged
                        )
                }

                Spacer(modifier = Modifier.height(height = 16.dp))

            }

            AppButton(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(bottom = 16.dp, top = 8.dp),
                onClick = onSaveChannel,
                text = stringResource(id = R.string.register_channel),
                enabled = uiState.isChannelRegisterButtonEnabled
            )

        }

    }

}

@Composable
private fun ChannelOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary,
                unselectedColor = Gray6
            )
        )

        Spacer(Modifier.width(width = 4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )

    }
}