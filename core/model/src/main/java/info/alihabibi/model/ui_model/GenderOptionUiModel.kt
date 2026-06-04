package info.alihabibi.model.ui_model

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class GenderOptionUiModel(
    @get:StringRes val labelRes: Int
) {
    MEN(R.string.men),
    WOMAN(R.string.women),
    UNKNOWN(R.string.unknow)
}
