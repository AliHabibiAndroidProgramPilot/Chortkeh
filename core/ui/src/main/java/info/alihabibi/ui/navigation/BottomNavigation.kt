package info.alihabibi.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ChortkehTheme
import info.alihabibi.designsystem.theme.Gray10
import info.alihabibi.designsystem.theme.Primary
import info.alihabibi.designsystem.theme.White

@Composable
fun AppBottomNavigation(
    items: List<BottomNavItem>,
    onNavItemClicked: (BottomNavItem) -> Unit,
    onFabClick: () -> Unit
) {

    NavBar(
        navItems = items,
        onFabClick = onFabClick,
        onNavItemClicked = { onNavItemClicked(it) }
    )

}

@Composable
private fun NavBar(
    navItems: List<BottomNavItem>,
    onNavItemClicked: (BottomNavItem) -> Unit,
    onFabClick: () -> Unit,
) {

    var selected by rememberSaveable { mutableStateOf(navItems.last().route) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(color = White) {
            Column {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (index in navItems.subList(0, 2))
                        NavItem(
                            modifier = Modifier.weight(1f),
                            navItem = index,
                            selected = (selected == index.route),
                            onClick = {
                                selected = index.route
                                onNavItemClicked(index)
                            }
                        )
                    Spacer(Modifier.width(72.dp))
                    for (index in navItems.subList(2, 4))
                        NavItem(
                            modifier = Modifier.weight(1f),
                            navItem = index,
                            selected = (selected == index.route),
                            onClick = {
                                selected = index.route
                                onNavItemClicked(index)
                            }
                        )
                }
            }
        }
        FloatingActionButton(
            onClick = onFabClick,
            containerColor = Primary,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-28).dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null
            )
        }
    }
}


@Composable
private fun NavItem(
    modifier: Modifier = Modifier,
    navItem: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) Primary else Gray10
//    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            /*.clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false)
            ) {
                onClick()
            }*/,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(if (selected) Primary else Color.Transparent)
        )
        Spacer(Modifier.height(10.dp))
        Icon(
            painter = navItem.icon,
            contentDescription = null,
            tint = color
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = navItem.label,
            style = MaterialTheme.typography.labelMedium.copy(color = color)
        )
    }
}

@Preview
@Composable
fun AppBottomNavigationPreview() {

    ChortkehTheme {
        NavBar(
            navItems = listOf(
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
            onFabClick = {},
            onNavItemClicked = {}
        )
    }

}