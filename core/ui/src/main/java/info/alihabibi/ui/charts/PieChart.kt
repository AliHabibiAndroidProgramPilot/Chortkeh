package info.alihabibi.ui.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AppPieChart(
    modifier: Modifier = Modifier,
    segments: List<PieChartData>,
    strokeWidth: Dp = 22.dp,
    gapWidth: Dp = 5.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        PieChart(
            modifier = Modifier
                .weight(weight = 1f)
                .size(size = 200.dp)
                .aspectRatio(ratio = 1f),
            segments = segments,
            strokeWidth = strokeWidth,
            gapWidth = gapWidth
        )

        Spacer(modifier = Modifier.width(24.dp))

        LegendLabels(
            labelItems = segments,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
private fun PieChart(
    modifier: Modifier = Modifier,
    segments: List<PieChartData>,
    strokeWidth: Dp,
    gapWidth: Dp,
    cornerRadius: Dp = 6.5.dp
) {

    val total = segments.sumOf { it.value.toDouble() }.toFloat()

    val animatedProgress =
        remember(key1 = segments) { List(segments.size) { Animatable(initialValue = 0f) } }
    LaunchedEffect(key1 = segments) {
        animatedProgress.forEach { it.snapTo(0f) }
        animatedProgress.forEach {
            // each segment should have independent coroutin scope to all segments animate simultaneously
            launch {
                it.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1500, delayMillis = 130, easing = LinearOutSlowInEasing)
                )
            }
        }
    }

    Canvas(modifier = modifier) {
        val stroke = strokeWidth.toPx()
        val outerR = size.minDimension / 2f
        val innerR = outerR - stroke
        val center = Offset(size.width / 2f, size.height / 2f)
        val gapDeg = Math.toDegrees((gapWidth.toPx() / outerR).toDouble()).toFloat()

        var start = -90f
        segments.forEachIndexed { index, segment ->
            val fullSweep = (segment.value / total) * 360f
            val targetSweep = (fullSweep - gapDeg).coerceAtLeast(2f)

            val animatedProgress = animatedProgress[index].value
            val animatedSweep = targetSweep * animatedProgress

            if (animatedSweep > 0.5f) {
                val a1 = start + gapDeg / 2f
                val a2 = a1 + animatedSweep

                // shrink corner radius while the segment is still small, so the
                // fillet never exceeds half of the currently-visible arc length
                val maxCornerFromSweep = (animatedSweep / 2f) * (Math.PI.toFloat() / 180f) * outerR
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
    modifier: Modifier = Modifier,
    labelItems: List<PieChartData>
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {

        labelItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { segment ->
                    Box(modifier = Modifier.weight(1f)) {
                        LabelItem(segment = segment)
                    }
                }

            }
        }

    }

}

@Composable
private fun LabelItem(segment: PieChartData) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.widthIn(min = 12.dp, max = 46.dp),
            text = segment.label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(width = 10.dp))

        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(shape = RoundedCornerShape(size = 2.dp))
                .background(segment.color)
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
        )                                             // straight edge
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