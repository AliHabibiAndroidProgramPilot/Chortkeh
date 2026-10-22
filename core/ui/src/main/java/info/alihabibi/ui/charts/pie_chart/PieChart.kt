package info.alihabibi.ui.charts.pie_chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AppPieChart(
    segments: List<PieChartData>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 18.dp,
    gapWidth: Dp = 4.dp,
    maxLabelLines: Int = 2
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        // Chart takes ~38% of the width (clamped) so the legend gets the rest
        val chartSize = (maxWidth * 0.45f).coerceAtLeast(110.dp)

        Row(verticalAlignment = Alignment.CenterVertically) {

            PieChart(
                modifier = Modifier.size(chartSize),
                segments = segments,
                strokeWidth = strokeWidth,
                gapWidth = gapWidth
            )

            Spacer(modifier = Modifier.width(16.dp))

            // takes ALL the remaining width, split into 2 equal columns
            LegendLabels(
                modifier = Modifier.weight(1f),
                labelItems = segments,
                maxLabelLines = maxLabelLines
            )
        }
    }
}

@Composable
private fun PieChart(
    segments: List<PieChartData>,
    strokeWidth: Dp,
    gapWidth: Dp,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 6.5.dp
) {
    val total = remember(segments) { segments.sumOf { it.value.toDouble() }.toFloat() }

    val animatedProgress =
        remember(segments) { List(segments.size) { Animatable(initialValue = 0f) } }

    LaunchedEffect(segments) {
        animatedProgress.forEach { it.snapTo(0f) }
        animatedProgress.forEach {
            // independent coroutine per segment so they all animate simultaneously
            launch {
                it.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 1500,
                        delayMillis = 130,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        }
    }

    Canvas(modifier = modifier.padding(all = 8.dp)) {
        if (total <= 0f) return@Canvas // avoid NaN / division by zero

        val outerR = size.minDimension / 2f
        // stroke can never eat more than half of the radius on small charts
        val stroke = strokeWidth.toPx().coerceAtMost(outerR * 0.5f)
        val innerR = outerR - stroke
        val center = Offset(size.width / 2f, size.height / 2f)
        val gapDeg = Math.toDegrees((gapWidth.toPx() / outerR).toDouble()).toFloat()

        var start = -90f
        segments.forEachIndexed { index, segment ->
            val fullSweep = (segment.value / total) * 360f
            val targetSweep = (fullSweep - gapDeg).coerceAtLeast(2f)

            val progress = animatedProgress[index].value // read in draw phase only
            val animatedSweep = targetSweep * progress

            if (animatedSweep > 0.5f) {
                val a1 = start + gapDeg / 2f
                val a2 = a1 + animatedSweep

                val maxCornerFromSweep =
                    (animatedSweep / 2f) * (Math.PI.toFloat() / 180f) * outerR
                val safeCornerPx = min(cornerRadius.toPx(), maxCornerFromSweep)
                    .coerceIn(0f, min(stroke / 2f, (outerR - innerR) / 2f))

                drawPath(
                    path = roundedSegmentPath(center, outerR, innerR, a1, a2, safeCornerPx),
                    color = segment.color
                )
            }
            start += fullSweep
        }
    }
}

@Composable
private fun LegendLabels(
    labelItems: List<PieChartData>,
    maxLabelLines: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        labelItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { segment ->
                    LabelItem(
                        modifier = Modifier.weight(1f),
                        segment = segment,
                        maxLines = maxLabelLines
                    )
                }
                // keeps the 2-column grid aligned when the last row has one item
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun LabelItem(
    segment: PieChartData,
    maxLines: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            // fill = false: the text hugs the badge instead of leaving a gap,
            // but can still grow up to the full cell width
            modifier = Modifier.weight(weight = 1f, fill = false),
            text = segment.label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Right,
                textDirection = TextDirection.Rtl
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = segment.color, shape = RoundedCornerShape(size = 2.dp))
        )
    }
}

private fun roundedSegmentPath(
    center: Offset,
    outerR: Float,
    innerR: Float,
    startDeg: Float,
    endDeg: Float,
    cornerR: Float
): Path {
    fun point(r: Float, deg: Float): Offset {
        val rad = Math.toRadians(deg.toDouble())
        return Offset(center.x + r * cos(rad).toFloat(), center.y + r * sin(rad).toFloat())
    }

    // angular trim so the corner fillet has room on each arc
    val outerTrimDeg = Math.toDegrees((cornerR / outerR).toDouble()).toFloat()
    val innerTrimDeg = Math.toDegrees((cornerR / innerR).toDouble()).toFloat()

    // the 4 "sharp" corner points (used as quadratic control points)
    val outerStartCorner = point(outerR, startDeg)
    val outerEndCorner = point(outerR, endDeg)
    val innerEndCorner = point(innerR, endDeg)
    val innerStartCorner = point(innerR, startDeg)

    // where the outer/inner arcs actually start/end (trimmed back by the corner)
    val outerArcStart = point(outerR, startDeg + outerTrimDeg)
    val innerArcEnd = point(innerR, endDeg - innerTrimDeg)

    // where the straight radial edges start/end (trimmed by exact linear distance)
    fun lerp(a: Offset, b: Offset, t: Float) = Offset(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t)
    val t = (cornerR / (outerR - innerR)).coerceIn(0f, 0.49f)
    val endLineOuterPt = lerp(outerEndCorner, innerEndCorner, t)
    val endLineInnerPt = lerp(innerEndCorner, outerEndCorner, t)
    val startLineInnerPt = lerp(innerStartCorner, outerStartCorner, t)
    val startLineOuterPt = lerp(outerStartCorner, innerStartCorner, t)

    val outerRect = Rect(center.x - outerR, center.y - outerR, center.x + outerR, center.y + outerR)
    val innerRect = Rect(center.x - innerR, center.y - innerR, center.x + innerR, center.y + innerR)

    return Path().apply {
        moveTo(outerArcStart.x, outerArcStart.y)
        arcTo(
            outerRect,
            startDeg + outerTrimDeg,
            sweepAngleDegrees = (endDeg - outerTrimDeg) - (startDeg + outerTrimDeg),
            forceMoveTo = false
        )
        quadraticTo(
            outerEndCorner.x,
            outerEndCorner.y,
            endLineOuterPt.x,
            endLineOuterPt.y
        )   // corner 1
        lineTo(
            endLineInnerPt.x,
            endLineInnerPt.y
        )// straight edge
        quadraticTo(
            innerEndCorner.x,
            innerEndCorner.y,
            innerArcEnd.x,
            innerArcEnd.y
        )          // corner 2
        arcTo(
            innerRect,
            endDeg - innerTrimDeg,
            sweepAngleDegrees = -((endDeg - innerTrimDeg) - (startDeg + innerTrimDeg)),
            forceMoveTo = false
        )
        quadraticTo(
            innerStartCorner.x,
            innerStartCorner.y,
            startLineInnerPt.x,
            startLineInnerPt.y
        ) // corner 3
        lineTo(
            startLineOuterPt.x,
            startLineOuterPt.y
        )                                          // straight edge
        quadraticTo(
            outerStartCorner.x,
            outerStartCorner.y,
            outerArcStart.x,
            outerArcStart.y
        )  // corner 4
        close()
    }
}