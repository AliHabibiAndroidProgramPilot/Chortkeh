package info.alihabibi.ui.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.alihabibi.common.Utils
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.GreenSuccessLight

/** Tick ladder from the design. Ticks are evenly spaced on screen, but their values are not (piecewise-linear scale). */
private val BaseTicks = longArrayOf(0, 100_000, 500_000, 1_000_000, 5_000_000, 10_000_000, 15_000_000)

@Composable
fun IncomeExpenseBarChart(
    labels: List<String>,
    incomes: List<Long>,
    expenses: List<Long>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 260.dp,
    incomeColor: Color = GreenSuccessLight,
    expenseColor: Color = ErrorRed
) {

    val count = minOf(labels.size, incomes.size, expenses.size)
    val textMeasurer = rememberTextMeasurer()
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val labelStyle = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface)

    val ticks = remember(incomes, expenses) {
        resolveTicks(maxOf(incomes.maxOrNull() ?: 0L, expenses.maxOrNull() ?: 0L))
    }
    // measured once per data change, not on every draw frame
    val yLabels = remember(ticks, labelStyle) {
        ticks.map {
            textMeasurer.measure(
                text = Utils.decimalFormatterPattern.format(it),
                style = labelStyle.copy(
                    textDirection = TextDirection.Ltr,
                    textAlign = TextAlign.Left
                )
            )
        }
    }
    val xLabels = remember(labels, labelStyle) {
        labels.map { textMeasurer.measure(text = it, style = labelStyle.copy(textDirection = TextDirection.Rtl)) }
    }

    // replays whenever the data changes (e.g. user switches weekly/monthly/yearly)
    val progress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(incomes, expenses) {
        progress.snapTo(0f)
        progress.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing))
    }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LegendItem(color = incomeColor, text = stringResource(id = R.string.income2))
                LegendItem(color = expenseColor, text = stringResource(id = R.string.outcome2))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
        ) {
            if (count == 0) return@Canvas

            val labelGap = 8.dp.toPx()
            val plotLeft = (yLabels.maxOf { it.size.width }) + labelGap * 1.5f
            val plotTop = yLabels.last().size.height / 2f
            val plotBottom = size.height - ((xLabels.take(count).maxOf { it.size.width }) + labelGap * 1.5f)
            val plotWidth = size.width - plotLeft
            val plotHeight = plotBottom - plotTop
            val topIndex = ticks.lastIndex

            // grid lines + y labels
            ticks.forEachIndexed { index, _ ->
                val y = plotBottom - plotHeight * index / topIndex
                drawLine(
                    color = gridColor,
                    start = Offset(plotLeft, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
                val layout = yLabels[index]
                drawText(
                    textLayoutResult = layout,
                    topLeft = Offset(0f, y - layout.size.height / 2f)
                )
            }

            // vertical axis line
            drawLine(
                color = gridColor,
                start = Offset(plotLeft, plotTop),
                end = Offset(plotLeft, plotBottom),
                strokeWidth = 1.dp.toPx()
            )

            val groupWidth = plotWidth / count
            val barWidth = minOf(groupWidth * 0.26f, 16.dp.toPx())
            val minBarHeight = 2.dp.toPx()

            repeat(count) { i ->
                val center = plotLeft + groupWidth * (i + 0.5f)

                fun barHeight(value: Long): Float =
                    if (value <= 0L) 0f
                    else (heightFraction(value, ticks) * plotHeight).coerceAtLeast(minBarHeight) * progress.value

                // expense (red) on the left, income (green) on the right, touching each other
                drawBar(center - 3.dp.toPx() / 2f - barWidth, plotBottom, barWidth, barHeight(expenses[i]), expenseColor)
                drawBar(center + 3.dp.toPx() / 2f, plotBottom, barWidth, barHeight(incomes[i]), incomeColor)

                // rotated x label under the group
                val layout = xLabels[i]
                val pivot = Offset(center, plotBottom + labelGap + layout.size.width / 2f)
                rotate(degrees = -90f, pivot = pivot) {
                    drawText(
                        textLayoutResult = layout,
                        topLeft = Offset(pivot.x - layout.size.width / 2f, pivot.y - layout.size.height / 2f)
                    )
                }
            }
        }

    }

}

@Composable
private fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color = color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun DrawScope.drawBar(left: Float, bottom: Float, width: Float, height: Float, color: Color) {
    if (height <= 0f) return
    val radius = CornerRadius(minOf(3.dp.toPx(), width / 2f, height))
    val path = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(offset = Offset(left, bottom - height), size = Size(width, height)),
                topLeft = radius,
                topRight = radius
            )
        )
    }
    drawPath(path = path, color = color)
}

/**
 * Keeps the look of the design (0, 100K, 500K, 1M, 5M, 10M, 15M) but scales the whole ladder by powers of
 * ten so any amount fits: small spenders get 0..150K, big ones get 0..150M and so on.
 */
private fun resolveTicks(maxValue: Long): List<Long> {
    var divisor = 100L
    var multiplier = 1L
    while (BaseTicks.last() * multiplier / divisor < maxValue) {
        if (divisor > 1) divisor /= 10 else multiplier *= 10
    }
    return BaseTicks.map { it * multiplier / divisor }
}

/** Piecewise-linear value -> 0..1 height, because ticks are evenly spaced on screen but not in value. */
private fun heightFraction(value: Long, ticks: List<Long>): Float {
    val top = ticks.lastIndex
    for (i in 0 until top) {
        if (value <= ticks[i + 1]) {
            val segment = (value - ticks[i]).toFloat() / (ticks[i + 1] - ticks[i])
            return (i + segment) / top
        }
    }
    return 1f
}