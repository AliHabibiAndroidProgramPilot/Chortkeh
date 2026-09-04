package info.alihabibi.channels.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.ui.dialogs.AppIconSelectionBottomSheet
import info.alihabibi.ui.inputs.AppCardNumberTextField
import info.alihabibi.ui.inputs.AppTitledPriceTextField
import info.alihabibi.ui.inputs.AppTitledTextField

@Composable
internal fun BankAccountChannelContent(
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
                checked = false,
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
internal fun OtherChannelContent(
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
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(size = 28.dp),
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