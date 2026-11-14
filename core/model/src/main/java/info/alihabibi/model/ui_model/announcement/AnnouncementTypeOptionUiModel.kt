package info.alihabibi.model.ui_model.announcement

import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

enum class AnnouncementTypeOptionUiModel(@StringRes val labelRes: Int) {
    IN_APP(R.string.inApp),
    BANK_SMS(R.string.bank)
}