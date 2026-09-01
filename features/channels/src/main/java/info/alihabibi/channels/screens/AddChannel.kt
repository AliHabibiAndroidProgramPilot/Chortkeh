package info.alihabibi.channels.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.channels.AddChannelUiState
import info.alihabibi.channels.ChannelsUiIntent
import info.alihabibi.channels.ChannelsViewModel
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray3
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray6
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.dialogs.AppIconSelectionBottomSheet
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.inputs.AppCardNumberTextField
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import info.alihabibi.ui.inputs.AppTitledTextField

@Composable
fun AddChannelDestination(
    viewModel: ChannelsViewModel,
    onBackPressed: () -> Unit
) {

    val addChannelUiState by viewModel.addChannelUiState.collectAsStateWithLifecycle()

    AddNewChannelScreen(
        uiState = addChannelUiState,
        onChannelTypeChange = { isBankAccountChannel ->
            viewModel.onEvent(ChannelsUiIntent.OnChannelTypeChanged(isBankAccountChannel))
        },
        onCardNumberChange = { cardNumber ->
            viewModel.onEvent(ChannelsUiIntent.OnCardNumberChanged(cardNumber))
        },
        onInitialBalanceChanged = { balance ->
            viewModel.onEvent(ChannelsUiIntent.OnInitialBalanceChanged(balance))
        },
        onChannelNameChanged = { name ->
            viewModel.onEvent(ChannelsUiIntent.OnChannelNameChanged(name))
        },
        onChannelIconChanged = { icon ->
            viewModel.onEvent(ChannelsUiIntent.OnChannelIconChanged(icon))
        },
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AddNewChannelScreen(
    uiState: AddChannelUiState,
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
                isMenuAvailable = false,
                onNavigationClick = onBackPressed
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
                        initialBalance = uiState.initialBalance,
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
                        initialBalance = uiState.initialBalance,
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

@Composable
private fun BankAccountChannelContent(
    modifier: Modifier = Modifier,
    channelName: String,
    cardNumber: String,
    initialBalance: String,
    onCardNumberChange: (value: String) -> Unit = {},
    onInitialBalanceChange: (value: String) -> Unit = {},
    onChannelNameChange: (value: String) -> Unit = {},
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppCardNumberTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = cardNumber,
            onValueChange = onCardNumberChange,
            title = stringResource(id = R.string.card_number)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledPriceTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = initialBalance,
            onValueChange = onInitialBalanceChange,
            title = stringResource(id = R.string.balance),
            placeHolderText = stringResource(id = R.string.toman_0)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = channelName,
            onValueChange = onChannelNameChange,
            title = stringResource(id = R.string.source_name)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Switch(
                checked = true,
                onCheckedChange = {

                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    uncheckedThumbColor = White,
                    checkedTrackColor = Primary,
                    uncheckedTrackColor = Gray5,
                    uncheckedBorderColor = Gray5
                )
            )

            Text(
                modifier = Modifier
                    .weight(weight = 1f)
                    .offset(y = 8.dp),
                text = stringResource(id = R.string.read_sms),
                style = MaterialTheme.typography.bodyLarge
            )

        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            text = stringResource(id = R.string.read_sms_description),
            style = MaterialTheme.typography.labelMedium.copy(color = Gray7, textAlign = TextAlign.Right, textDirection = TextDirection.Rtl),
            overflow = TextOverflow.Ellipsis
        )

    }

}

@Composable
private fun OtherChannelContent(
    modifier: Modifier = Modifier,
    channelName: String,
    initialBalance: String,
    channelSelectedIcon: ChannelIconOptionUiModel? = null,
    onInitialBalanceChange: (value: String) -> Unit = {},
    onChannelNameChange: (value: String) -> Unit = {},
    onChannelIconChanged: (value: ChannelIconOptionUiModel) -> Unit = {},
) {

    var showChannelIconSelectionModel by remember { mutableStateOf(false) }
    if(showChannelIconSelectionModel)
        AppIconSelectionBottomSheet(
            title = stringResource(id = R.string.channel_icon),
            options = ChannelIconOptionUiModel.entries.toList(),
            iconsResId = { it.iconResId },
            onOptionSelected = { selectedIcon ->
                onChannelIconChanged(selectedIcon)
                showChannelIconSelectionModel = false
            },
            onDismissRequest = { showChannelIconSelectionModel = false }
        )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppTitledTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = channelName,
            onValueChange = onChannelNameChange,
            title = stringResource(id = R.string.source_name)
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .padding(horizontal = 16.dp)
                .border(width = 1.dp, color = Gray11, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .clickable { showChannelIconSelectionModel = true },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.padding(start = 18.dp),
                painter = painterResource(id = R.drawable.short_arrow_down),
                contentDescription = null,
                tint = Gray8
            )

            Spacer(modifier = Modifier.weight(weight = 1f))

            if (channelSelectedIcon != null)
                Icon(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    painter = painterResource(id = channelSelectedIcon.iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            else
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.icon),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )

        }

        Spacer(modifier = Modifier.height(height = 16.dp))

        AppTitledPriceTextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = initialBalance,
            onValueChange = onInitialBalanceChange,
            title = stringResource(id = R.string.initial_source_balance),
            placeHolderText = stringResource(id = R.string.toman_0)
        )

    }

}