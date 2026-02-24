package info.alihabibi.ui.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ChortkehTheme
import info.alihabibi.designsystem.theme.Gray10
import info.alihabibi.designsystem.theme.Gray6
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    onItemClicked: (BottomNavItem) -> Unit,
    onFabClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .then(modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(
                modifier = Modifier.shadow(0.4.dp, clip = true),
                thickness = 1.2.dp,
                color = Gray6
            )
            FloatingActionButton(
                modifier = Modifier.size(55.dp),
                onClick = onFabClicked,
                shape = CircleShape,
                containerColor = Primary,
                contentColor = White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp)
        ) {
            AppNavigationItem(item = items[0])
            AppNavigationItem(item = items[1])
            Spacer(modifier = Modifier.weight(1f))
            AppNavigationItem(item = items[2])
            AppNavigationItem(item = items[3])
        }
    }

}

@Composable
private fun AppNavigationItem(item: BottomNavItem) {
    NavigationRailItem(
        selected = false,
        onClick = {},
        icon = {
            Icon(
                painter = item.icon,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = Primary,
            selectedTextColor = Primary,
            indicatorColor = Primary,
            unselectedIconColor = Gray10,
            unselectedTextColor = Gray10
        )
    )
}

@Preview
@Composable
fun NavigationPreview() {

    ChortkehTheme {

        Column {

            AppBottomNavigation(
                items = listOf(
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.profile),
                        icon = painterResource(id = R.drawable.profile)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.profile),
                        icon = painterResource(id = R.drawable.profile)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.profile),
                        icon = painterResource(id = R.drawable.profile)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.profile),
                        icon = painterResource(id = R.drawable.profile)
                    )
                ),
                onItemClicked = {},
                onFabClicked = {}
            )

        }

    }

}