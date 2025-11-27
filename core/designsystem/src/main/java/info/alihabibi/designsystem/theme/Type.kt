package info.alihabibi.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R

private val font = FontFamily(
    fonts = listOf(
        Font(resId = R.font.iran_yekanx_normal, weight = FontWeight.Normal),
        Font(resId = R.font.iran_yekanx_bold, weight = FontWeight.Bold),
        Font(resId = R.font.iran_yekanx_black, weight = FontWeight.Black),
        Font(resId = R.font.iran_yekanx_medium, weight = FontWeight.Medium),
        Font(resId = R.font.iran_yekanx_light, weight = FontWeight.Light)
    )
)

internal val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        textAlign = TextAlign.Start,
        textDirection = TextDirection.Rtl
    ),

    headlineMedium = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        textAlign = TextAlign.Start,
        textDirection = TextDirection.Rtl
    ),

    headlineSmall = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        textAlign = TextAlign.Start,
        textDirection = TextDirection.Rtl
    ),

    // Default
    bodyLarge = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = Alignment.Center,
            trim = Trim.None,
        ),
        textAlign = TextAlign.Start,
        textDirection = TextDirection.Rtl
    ),

    bodyMedium = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        textAlign = TextAlign.Start,
        textDirection = TextDirection.Rtl
    ),

    bodySmall = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        textDirection = TextDirection.Ltr,
        textAlign = TextAlign.Left,
    ),

    // Used for Button
    labelLarge = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = Alignment.Center,
            trim = Trim.LastLineBottom,
        ),
        textDirection = TextDirection.Ltr,
        textAlign = TextAlign.Left,
    ),
    // Used for Navigation items
    labelMedium = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = Alignment.Center,
            trim = Trim.LastLineBottom,
        ),
        textDirection = TextDirection.Ltr,
        textAlign = TextAlign.Left,
    ),
    // Used for Tag
    labelSmall = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = Alignment.Center,
            trim = Trim.LastLineBottom,
        ),
        textDirection = TextDirection.Ltr,
        textAlign = TextAlign.Left,
    )
)