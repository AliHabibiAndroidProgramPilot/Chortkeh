package info.alihabibi.ui.charts

import androidx.annotation.DrawableRes

data class GaugeChartData(
    val totalIncome: String = "",
    val remainedBalance: String = "",
    val bottomMessage: String = "",
    @get:DrawableRes val iconResId: Int
)

enum class GaugeChartState { EMPTY, GREEN, RED }
