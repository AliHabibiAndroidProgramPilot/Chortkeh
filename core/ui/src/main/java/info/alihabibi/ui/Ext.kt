package info.alihabibi.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.theme.shimmerMiddleColor
import info.alihabibi.designsystem.theme.shimmerStartColor

fun Modifier.shimmerLoading(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            )
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                shimmerStartColor,
                shimmerMiddleColor,
                shimmerStartColor
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun ReportScreenShimmerLoading(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit
) {
    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val shape = RoundedCornerShape(percent = 8)

            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(height = 45.dp)
                    .clip(shape)
                    .shimmerLoading()
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Box(
                modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .height(height = 260.dp)
                .clip(shape)
                .shimmerLoading()
            )

            Spacer(modifier = Modifier.height(height = 36.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(height = 45.dp)
                    .clip(shape)
                    .shimmerLoading()
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .clip(shape)
                    .shimmerLoading()
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(height = 300.dp)
                    .clip(shape)
                    .shimmerLoading()
            )

        }
    } else contentAfterLoading.invoke()
}