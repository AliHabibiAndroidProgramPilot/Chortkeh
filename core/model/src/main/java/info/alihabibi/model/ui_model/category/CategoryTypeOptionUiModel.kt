package info.alihabibi.model.ui_model.category

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class CategoryTypeOptionUiModel(
    @get:StringRes val labelRes: Int
) {
    INCOME(R.string.income),
    OUTCOME(R.string.outcome)
}