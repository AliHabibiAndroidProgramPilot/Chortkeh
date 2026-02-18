package info.alihabibi.onboarding

import android.content.res.Configuration
import android.os.Build
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.common_android.CheckPermission
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.ui.buttons.AppButton

@Composable
fun OnBoardingDestination(
    onEnterApplication: () -> Unit
) {

    val orientation = LocalConfiguration.current.orientation

    val pagerContent = providePagerContent(isLandScape = orientation, onEnterApplication = onEnterApplication)

    OnBoardingScreen(
        screenOrientation = orientation,
        pages = pagerContent
    )

}

@Composable
private fun OnBoardingScreen(
    screenOrientation: Int = Configuration.ORIENTATION_PORTRAIT,
    pages: Array<@Composable () -> Unit>
) {

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2)
        CheckPermission(
            requestPermission = android.Manifest.permission.POST_NOTIFICATIONS,
            rationalDialogTitle = stringResource(R.string.notification_access),
            rationalDialogMessage = stringResource(id = R.string.notification_access_message),
            rationalDialogConfirmButtonText = stringResource(id = R.string.I_give_permission),
            rationalDialogCancelButtonText = stringResource(id = R.string.dismiss)
        )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            state = pagerState
        ) { page ->
            pages[page]()
        }

        PagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) 250.dp else 50.dp),
            pagerState = pagerState
        )

    }

}

@Composable
private fun PagerContentVertical(
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

            Spacer(modifier = Modifier.height(28.dp))

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
private fun PagerContentLandscape(
    image: Painter,
    title: String,
    description: String,
    onButtonClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
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

        Spacer(Modifier.width(24.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = AbsoluteAlignment.Right,
            verticalArrangement = Arrangement.Center
        ) {

            Text(title, style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(12.dp))

            Text(
                description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Gray8)
            )

            Spacer(Modifier.height(24.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClick,
                text = stringResource(R.string.enter_app)
            )
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
    isLandScape: Int,
    onEnterApplication: () -> Unit
): Array<@Composable () -> Unit> {

    return if (isLandScape == Configuration.ORIENTATION_LANDSCAPE) {
        arrayOf<@Composable () -> Unit>(
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.saving_pana_onboard),
                    title = stringResource(R.string.piggy_bank),
                    description = stringResource(R.string.piggy_bank_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.budget_pana_onboard),
                    title = stringResource(R.string.budgeting),
                    description = stringResource(R.string.budgeting_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContentLandscape(
                    image = painterResource(R.drawable.stairs_onboead),
                    title = stringResource(R.string.stairs),
                    description = stringResource(R.string.stairs_description),
                    onButtonClick = onEnterApplication
                )
            }
        )
    } else {
        arrayOf<@Composable () -> Unit>(
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.saving_pana_onboard),
                    title = stringResource(R.string.piggy_bank),
                    description = stringResource(R.string.piggy_bank_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.budget_pana_onboard),
                    title = stringResource(R.string.budgeting),
                    description = stringResource(R.string.budgeting_description),
                    onButtonClick = onEnterApplication
                )
            },
            {
                PagerContentVertical(
                    image = painterResource(R.drawable.stairs_onboead),
                    title = stringResource(R.string.stairs),
                    description = stringResource(R.string.stairs_description),
                    onButtonClick = onEnterApplication
                )
            }
        )
    }

}


@Preview
@Composable
private fun OnBoardingPreview() {

    OnBoardingScreen(
        pages = emptyArray()
    )

}