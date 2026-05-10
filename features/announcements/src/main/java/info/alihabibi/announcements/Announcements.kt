package info.alihabibi.announcements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import info.alihabibi.designsystem.R
import info.alihabibi.ui.headrs.AppHeader

@Composable
fun AnnouncementsDestination(
    onBackPressed: () -> Unit
) {

    AnnouncementsScreen(onBackPressed = onBackPressed)

}

@Composable
private fun AnnouncementsScreen(
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        AppHeader(
            onNavigationClick = onBackPressed,
            isMenuAvailable = false,
            title = stringResource(id = R.string.announcements)
        )

    }

}

@Preview
@Composable
private fun AnnouncementsPreview() {

    AnnouncementsScreen(
        onBackPressed = {}
    )

}