package info.alihabibi.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.ui.buttons.AppButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingDestination(
    onEnterApplication: () -> Unit,
    viewModel: OnBoardingViewModel = koinViewModel()
) {

    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val isWide = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(widthDpBreakpoint = 600)

    val pagerContent = providePagerContent(isWide = isWide)

    OnBoardingScreen(
        pages = pagerContent,
        onEnterApplication = {
            viewModel.onEvent(OnBoardingUiIntent.ChangeIsFirstLaunchFlag(false))
            onEnterApplication()
        }
    )

}

@Composable
private fun OnBoardingScreen(
    pages: Array<@Composable () -> Unit>,
    onEnterApplication: () -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { page ->
            pages[page]()
        }

        PagerIndicator(
            modifier = Modifier.padding(vertical = 16.dp),
            pagerState = pagerState
        )

        AppButton(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            onClick = onEnterApplication,
            text = stringResource(R.string.enter_app)
        )

    }

}

@Composable
private fun PagerContentVertical(
    image: Painter,
    title: String,
    description: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 280.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(color = Gray8),
            textAlign = TextAlign.Center
        )

    }

}

@Composable
private fun PagerContentLandscape(
    image: Painter,
    title: String,
    description: String
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 800.dp)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.width(32.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Text(title, style = MaterialTheme.typography.headlineSmall)

                Spacer(Modifier.height(12.dp))

                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray8)
                )

            }
        }
    }

}


@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { index ->
            val selected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(if (selected) 58.dp else 10.dp)
                    .height(10.dp)
                    .background(
                        color = if (selected) Primary else Gray5,
                        shape = if (selected) RoundedCornerShape(60) else CircleShape
                    )
            )
        }
    }

}

private fun providePagerContent(
    isWide: Boolean
): Array<@Composable () -> Unit> {

    return if (isWide) {
        arrayOf<@Composable () -> Unit>(
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.saving_pana_onboard),
                    title = stringResource(R.string.piggy_bank),
                    description = stringResource(R.string.piggy_bank_description)
                )
            },
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.budget_pana_onboard),
                    title = stringResource(R.string.budgeting),
                    description = stringResource(R.string.budgeting_description)
                )
            },
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.stairs_onboead),
                    title = stringResource(R.string.stairs),
                    description = stringResource(R.string.stairs_description)
                )
            }
        )
    } else {
        arrayOf<@Composable () -> Unit>(
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.saving_pana_onboard),
                    title = stringResource(R.string.piggy_bank),
                    description = stringResource(R.string.piggy_bank_description)
                )
            },
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.budget_pana_onboard),
                    title = stringResource(R.string.budgeting),
                    description = stringResource(R.string.budgeting_description)
                )
            },
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.stairs_onboead),
                    title = stringResource(R.string.stairs),
                    description = stringResource(R.string.stairs_description)
                )
            }
        )
    }

}


@Preview
@Composable
private fun OnBoardingPreview() {

    OnBoardingScreen(
        pages = emptyArray(),
        onEnterApplication = {}
    )

}