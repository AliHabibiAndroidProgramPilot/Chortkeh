package info.alihabibi.model.ui_model.transaction

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class TransactionTypeOptionUiModel(
    @get:StringRes val labelRes: Int
) {
    OUTCOME(R.string.outcome),
    INCOME(R.string.income)
}