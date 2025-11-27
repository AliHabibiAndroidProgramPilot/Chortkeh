package info.alihabibi.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)*/

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = MoonRaker,
    onPrimaryContainer = Haiti,
    secondary = BayOfMany,
    onSecondary = White,
    secondaryContainer = Perano,
    onSecondaryContainer = Haiti,
    tertiary = Azure,
    onTertiary = White,
    tertiaryContainer = Portage,
    onTertiaryContainer = Haiti,
    background = White,
    onBackground = Biscay,
    surface = White,
    onSurface = Haiti,
    surfaceVariant = MoonRaker,
    onSurfaceVariant = Haiti
)

/**
 * @author AliHabibi
 * @param content Whole app content wrapped inside theme
 * App has no dark schema!
 * Chortkeh Theme
 */
@Composable
fun ChortkehTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}