package info.alihabibi.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.ui.buttons.AppButton

@Composable
fun OnBoardingDestination(
    onEnterApplication: () -> Unit
) {

    OnBoardingScreen(
        pages = listOf(
            {
                PagerContent(
                    image = painterResource(R.drawable.saving_pana_onboard),
                    title = stringResource(R.string.piggy_bank),
                    description = stringResource(R.string.piggy_bank_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContent(
                    image = painterResource(R.drawable.budget_pana_onboard),
                    title = stringResource(R.string.budgeting),
                    description = stringResource(R.string.budgeting_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContent(
                    image = painterResource(R.drawable.stairs_onboead),
                    title = stringResource(R.string.stairs),
                    description = stringResource(R.string.stairs_description),
                    onButtonClick = onEnterApplication
                )
            }
        )
    )

}

@Composable
private fun OnBoardingScreen(
    pages: List<@Composable () -> Unit>
) {

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            pages[page]()
        }

        PagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 220.dp)
        )
    }


}

@Composable
private fun PagerContent(
    onButtonClick: () -> Unit,
    image: Painter,
    title: String,
    description: String
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp, bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Gray8)
            )
        }

        AppButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 64.dp, start = 20.dp, end = 20.dp),
            onClick = onButtonClick,
            text = stringResource(R.string.enter_app)
        )
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


@Preview
@Composable
private fun OnBoardingPreview() {

    OnBoardingScreen(
        pages = emptyList()
    )

}