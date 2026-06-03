package info.alihabibi.home.mapper

import info.alihabibi.designsystem.R
import info.alihabibi.domain.models.Genders
import info.alihabibi.domain.models.UserInfo
import info.alihabibi.home.ui_model.UserAccountInfoUiModel

fun UserInfo.toUiModel() = UserAccountInfoUiModel(
    fullName = fullName,
    phone = phone,
    profileImageRes = gender.toProfileDrawable()
)

private fun Genders.toProfileDrawable(): Int = when (this) {
    Genders.MEN   -> R.drawable.men_profile
    Genders.WOMAN -> R.drawable.women_profile
    Genders.UNKNOW -> R.drawable.unknown_gender_profile
}