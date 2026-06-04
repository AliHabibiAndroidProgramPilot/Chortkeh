package info.alihabibi.model.mapper

import info.alihabibi.designsystem.R
import info.alihabibi.domain.models.Genders
import info.alihabibi.domain.models.UserAccountInfo
import info.alihabibi.model.ui_model.UserAccountInfoUiModel

fun UserAccountInfo.toUiModel() = UserAccountInfoUiModel(
    fullName = fullName,
    phone = phone,
    gender = gender.toUiOption(),
    profileImageRes = gender.toProfileDrawable()
)

fun UserAccountInfoUiModel.toDomain() = UserAccountInfo(
    fullName = fullName,
    phone = phone,
    gender = gender.toDomain()
)

private fun Genders.toProfileDrawable(): Int = when (this) {
    Genders.MEN -> R.drawable.men_profile
    Genders.WOMAN -> R.drawable.women_profile
    Genders.UNKNOWN -> R.drawable.unknown_gender_profile
}