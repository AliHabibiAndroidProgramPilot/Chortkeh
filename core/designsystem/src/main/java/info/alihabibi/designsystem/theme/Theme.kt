package info.alihabibi.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Haiti,
    primaryContainer = BayOfMany,
    onPrimaryContainer = MoonRaker,
    secondary = Portage,
    onSecondary = Haiti,
    secondaryContainer = Biscay,
    onSecondaryContainer = Perano,
    tertiary = Perano,
    onTertiary = Haiti,
    tertiaryContainer = Azure,
    onTertiaryContainer = MoonRaker,
    background = Gray13,
    onBackground = Gray2,
    surface = Gray13,
    onSurface = Gray2,
    surfaceVariant = Gray11,
    onSurfaceVariant = Gray5
)

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
 * @author Ali Habibi
 * @param content Whole app content wrapped inside theme
 * Chortkeh Theme
 */
@Composable
fun ChortkehTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
