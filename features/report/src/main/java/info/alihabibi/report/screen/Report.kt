package info.alihabibi.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.report.ReportTimingOption
import info.alihabibi.report.ReportUiIntent
import info.alihabibi.report.ReportUiState
import info.alihabibi.report.ReportViewModel
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.headrs.AppHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReportDestination(
    viewModel: ReportViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportScreen(
        uiState = uiState,
        onReportTimingOptionChanged = { timingOption ->
            viewModel.onEvent(ReportUiIntent.ReportTimingOptionChanged(timingOption))
        }
    )

}

@Composable
private fun ReportScreen(
    uiState: ReportUiState,
    onReportTimingOptionChanged: (timingOption: ReportTimingOption) -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.reports),
                isActionAvailable = false,
                isNavigationAvailable = false
            )

            if (!uiState.hasTransaction)
                EmptyReportState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            else
                ReportContent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(state = rememberScrollState()),
                    uiState = uiState,
                    onReportTimingOptionChanged = onReportTimingOptionChanged
                )

        }

    }

}

@Composable
private fun ReportContent(
    modifier: Modifier = Modifier,
    uiState: ReportUiState,
    onReportTimingOptionChanged: (timingOption: ReportTimingOption) -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppToggle(
            toggleItems = ReportTimingOption.entries.toList(),
            itemTitle = { timingOption -> stringResource(id = timingOption.labelRes) },
            selectedOption = uiState.reportTimingOption,
            onToggleSelectionChanged = onReportTimingOptionChanged
        )

    }

}

@Composable
private fun EmptyReportState(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.empty_report),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = stringResource(id = R.string.empty_balance_state),
                style = MaterialTheme.typography.labelLarge.copy(color = Gray7)
            )

        }

        val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
        val isCompact = !windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(widthDpBreakpoint = 600)

        if (isCompact)
            Image(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 68.dp)
                    .rotate(degrees = -25f),
                painter = painterResource(id = R.drawable.arrow_shape_down),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

    }

}