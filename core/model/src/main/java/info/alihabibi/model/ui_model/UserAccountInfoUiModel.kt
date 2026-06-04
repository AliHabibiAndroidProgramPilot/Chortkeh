package info.alihabibi.model.ui_model

import androidx.annotation.DrawableRes
import info.alihabibi.designsystem.R

data class UserAccountInfoUiModel(
    val fullName: String = "",
    val phone: String = "",
    val gender: GenderOptionUiModel = GenderOptionUiModel.UNKNOWN,
    @get:DrawableRes val profileImageRes: Int = R.drawable.unknown_gender_profile
)