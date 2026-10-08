package info.alihabibi.common_android

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.Color

object AndroidUtils {

    // This function is clear to be in core:common_android, because of using compose.ui.graphics.Color
    fun generateDistinctColors(count: Int,lightness: Float = 0.55f): List<Color> {
        val generatedColors = (0 until count).map { i ->
            val hue = (i * 360f / count) % 360f
            Color.hsl(hue, 0.76f, lightness)
        }
        return generatedColors
    }

    fun canScheduleExactAlarms(context: Context): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                context.getSystemService(AlarmManager::class.java)?.canScheduleExactAlarms() == true

}