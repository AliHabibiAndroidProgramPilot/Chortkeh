package info.alihabibi.channels.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.ui.buttons.AppButton
import info.alihabibi.ui.headrs.AppHeader

@Composable
fun ChannelsListDestination(
    onBackPressed: () -> Unit
) {

    val fakeListItems = remember { emptyList<Any>() }

    ChannelsListScreen(
        fakeListItems,
        onBackPressed = onBackPressed
    )

}

@Composable
private fun ChannelsListScreen(
    channels: List<Any>,
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
            // both should be fill max width and weight 1
            EmptyChannelState(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth()
            )
        else
            ChannelsListContent(channels)

        AppButton(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .padding(bottom = 16.dp),
            onClick = {},
            text = stringResource(id = R.string.add_new_channel)
        )

    }

}

@Composable
private fun ChannelsListContent(
    channels: List<Any>,
    modifier: Modifier = Modifier
) {

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