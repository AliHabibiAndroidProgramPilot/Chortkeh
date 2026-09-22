package info.alihabibi.ui.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.Gray5
import info.alihabibi.designsystem.theme.Gray8
import info.alihabibi.designsystem.theme.GreenSuccess
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Draws the semicircle gauge: a solid rounded track, a dotted progress arc
 * inside it, two small ring markers (top and bottom-center), and the center
 * texts — all sized off the composable's own width, so it scales cleanly.
 *
 * Usage:
 * ```
 * GaugeChart(
 *     data = GaugeChartData(
 *         percentage = 0.65f,
 *         topValue = "65%",
 *         centerTitle = "مانده",
 *         centerValue = "۲,۵۰۰,۰۰۰ تومان",
 *     ),
 *     modifier = Modifier.fillMaxWidth().padding(16.dp),
 * )
 * ```
 */
@Composable
fun GaugeChart(
    modifier: Modifier = Modifier,
    data: GaugeChartData,
    progress: Float,
    textStyle: TextStyle,
    centerTitle: String = stringResource(id = R.string.left_over_balance),
) {

    val textMeasurer = rememberTextMeasurer()
    val bottomMessageIcon = painterResource(id = data.iconResId)

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(2000, delayMillis = 200, easing = LinearOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (data.totalIncome.isNotBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.totalIncome,
                textAlign = TextAlign.Center,
                style = textStyle
            )

            Spacer(Modifier.height(height = 10.dp))
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.55f)
        ) {
            val strokeWidth = size.width * 0.085f
            val topSpacing = size.height * 0.10f // room for the top marker + connecting line
            val bottomSpacing = strokeWidth / 1f  // room for the round-cap bulge at the ends

            val diameter = min(
                size.width - strokeWidth,
                (size.height - topSpacing - bottomSpacing) * 2f,
            )
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, topSpacing + radius)
            val arcTopLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(diameter, diameter)

            // --- solid outer track (top half only) ---
            drawArc(
                color = Gray5,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )

            drawArc(
                color = if (progress >= 90) ErrorRed else GreenSuccess,
                startAngle = 180f,
                sweepAngle = animatedProgress.value,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )

            // --- dotted progress arc, inset inside the track ---
            val dotRadius = strokeWidth * 0.13f
            val dotTrackRadius = radius - strokeWidth / 2f - dotRadius * 2.2f
            for (i in 0 until 29) {
                val t = i / (29 - 1).toFloat()
                val angleRad = Math.toRadians((180f + t * 180f).toDouble()).toFloat()
                val dotCenter = Offset(
                    x = center.x + dotTrackRadius * cos(angleRad),
                    y = center.y + dotTrackRadius * sin(angleRad),
                )
                drawCircle(
                    color = if (progress >= 90) {
                        val x: Float = ((progress / 10) * 1.5 + 1).toFloat()
                        if (i <= x) ErrorRed else Gray5
                    } else if (progress == 0f) {
                        Gray5
                    } else {
                        val x: Float = ((progress / 10) * 1.5 + 1).toFloat()
                        if (i <= x) GreenSuccess else Gray5
                    },
                    radius = dotRadius,
                    center = dotCenter,
                )

            }

            // --- top marker: line connecting it to the arc ---
            val markerRadius = strokeWidth * 0.11f
            val markerStroke = markerRadius * 0.45f
            val topMarkerCenter = Offset(center.x, markerRadius + markerStroke)
            drawLine(
                color = ErrorRed,
                start = Offset(center.x, topMarkerCenter.y + markerRadius),
                end = Offset(center.x, center.y - (radius - 20)),
                strokeWidth = markerStroke,
            )

            // --- the two center texts ---
            var textY = center.y - radius * 0.55f
            val titleLayout = textMeasurer.measure(centerTitle, textStyle)
            drawText(
                textLayoutResult = titleLayout,
                topLeft = Offset(center.x - titleLayout.size.width / 2f, textY)
            )
            textY += titleLayout.size.height + 6.dp.toPx()

            if (data.remainedBalance.isNotBlank()) {
                val valueLayout = textMeasurer.measure(data.remainedBalance, textStyle)
                drawText(
                    textLayoutResult = valueLayout,
                    topLeft = Offset(center.x - valueLayout.size.width / 2f, textY),
                )
            }

            if (data.bottomMessage.isNotBlank()) {
                val iconSizePx = 20.dp.toPx()
                val spacingPx = 6.dp.toPx()
                val textLayout = textMeasurer.measure(
                    data.bottomMessage,
                    TextStyle(
                        fontFamily = FontFamily(Font(resId = R.font.iran_yekanx_normal)),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        textDirection = TextDirection.Rtl,
                        color = if (progress >= 90) ErrorRed else if (progress == 0f) Gray8 else GreenSuccess
                    )
                )
                val totalWidth = textLayout.size.width + spacingPx + iconSizePx
                val startX = center.x - totalWidth / 2f
                val rowCenterY = center.y - radius * 0.05f + 30.dp.toPx() // pick your vertical spot

                drawText(
                    textLayoutResult = textLayout,
                    topLeft = Offset(
                        x = startX,
                        y = rowCenterY - textLayout.size.height / 2f
                    )
                )

                translate(
                    left = startX + textLayout.size.width + spacingPx,
                    top = rowCenterY - iconSizePx / 2f
                ) {
                    with(bottomMessageIcon) {
                        draw(
                            size = Size(iconSizePx, iconSizePx),
                            colorFilter = ColorFilter.tint(
                                if (progress >= 90) ErrorRed else if (progress == 0f) Gray8 else GreenSuccess
                            )
                        )
                    }
                }
            }
        }

    }
}