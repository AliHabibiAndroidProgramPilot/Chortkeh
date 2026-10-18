package info.alihabibi.report

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class ReportTimingOption(
    @get:StringRes val labelRes: Int
) {

    YEARLY(R.string.yearly),

    MONTHLY(R.string.monthly),

    WEEKLY(R.string.weekly);

}