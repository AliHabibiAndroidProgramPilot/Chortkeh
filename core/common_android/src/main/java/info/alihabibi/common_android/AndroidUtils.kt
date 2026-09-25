package info.alihabibi.common_android

import androidx.compose.ui.graphics.Color

object AndroidUtils {

    fun generateDistinctColors(count: Int,lightness: Float = 0.55f): List<Color> {
        val generatedColors = (0 until count).map { i ->
            val hue = (i * 360f / count) % 360f
            Color.hsl(hue, 0.76f, lightness)
        }
        return generatedColors
    }

}