package info.alihabibi.ui.charts

import androidx.annotation.DrawableRes

/**
 * All the data a single [GaugeChart] needs, so the composable itself takes just
 * one parameter and can be reused anywhere (list item, card, dashboard, etc).
 */
data class GaugeChartData(
    val totalIncome: String = "",
    val remainedBalance: String = "",
    val bottomMessage: String = "",
    @get:DrawableRes val iconResId: Int
)
