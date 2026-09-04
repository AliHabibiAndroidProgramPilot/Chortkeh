package info.alihabibi.channels.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.channels.ChannelsViewModel
import info.alihabibi.common.Utils.loog
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.model.ui_model.channel.ChannelUiModel
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.items.ListedChannelItem

@Composable
fun ChannelsListDestination(
    viewModel: ChannelsViewModel,
    onEditChannel: (channelId: Int) -> Unit = {},
    onAddNewChannel: () -> Unit = {},
    onBackPressed: () -> Unit
) {

    val channels by viewModel.channels.collectAsStateWithLifecycle()

    ChannelsListScreen(
        channels = channels,
        onEditChannel = { channel ->
            onEditChannel(channel.id)
        },
        onAddNewChannel = onAddNewChannel,
        onBackPressed = onBackPressed
    )

}

@Composable
private fun ChannelsListScreen(
    channels: List<ChannelUiModel>,
    onEditChannel: (channel: ChannelUiModel) -> Unit = {},
    onAddNewChannel: () -> Unit = {},
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader(
            title = stringResource(id = R.string.input_channels),
            isMenuAvailable = false,
            onNavigationClick = onBackPressed
        )

        if (channels.isEmpty())
            EmptyChannelState(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth()
            )
        else
            ChannelsListContent(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth(),
                channels = channels,
                onChannelItemClicked = onEditChannel
            )

        AppButton(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .padding(bottom = 16.dp),
            onClick = onAddNewChannel,
            text = stringResource(id = R.string.add_new_channel)
        )

    }

}

@Composable
private fun ChannelsListContent(
    modifier: Modifier = Modifier,
    channels: List<ChannelUiModel>,
    onChannelItemClicked: (channel: ChannelUiModel) -> Unit = {}
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {

        items(
            items = channels,
            key = { it.id }
        ) { channel ->
            channel.loog("Ali")
            ListedChannelItem(
                title = channel.channelName,
                subTitle = channel.channelBalance,
                iconResId = channel.icon.iconResId,
                trailingIcon = {

                    Icon(
                        modifier = Modifier.padding(start = 16.dp),
                        painter = painterResource(id = R.drawable.short_arrow_left),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.weight(weight = 1f))

                },
                onClick = { onChannelItemClicked(channel) }
            )

        }

    }

}

@Composable
private fun EmptyChannelState(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 128.dp),
                painter = painterResource(id = R.drawable.empty_wallet),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 128.dp),
                text = stringResource(id = R.string.empty_channels_list_message),
                style = MaterialTheme.typography.labelLarge.copy(color = Gray7)
            )

            Image(
                modifier = Modifier.padding(end = 128.dp),
                painter = painterResource(id = R.drawable.arrow_shape_down),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

        }

    }

}