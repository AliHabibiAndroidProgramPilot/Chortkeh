package info.alihabibi.model.ui_model

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class CurrenciesOptionUiModel(
    @get:StringRes val labelRes: Int
) {
    TOMAN(R.string.toman),
    RIAL(R.string.rial)
}