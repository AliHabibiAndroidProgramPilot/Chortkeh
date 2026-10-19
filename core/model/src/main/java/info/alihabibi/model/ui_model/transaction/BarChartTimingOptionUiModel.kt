package info.alihabibi.model.ui_model.transaction

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class BarChartTimingOptionUiModel(
    @get:StringRes val labelRes: Int
) {

    YEARLY(R.string.yearly),

    MONTHLY(R.string.monthly),

    WEEKLY(R.string.weekly);

}