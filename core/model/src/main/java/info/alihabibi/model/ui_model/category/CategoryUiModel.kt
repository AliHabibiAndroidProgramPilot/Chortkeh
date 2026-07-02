package info.alihabibi.model.ui_model.category

import androidx.annotation.DrawableRes
import info.alihabibi.designsystem.R

data class CategoryUiModel(
    val id: Int = 0,
    val title: String = "",
    @get:DrawableRes val iconResId: Int = R.drawable.women_profile, //todo change this
    val type: CategoryTypeOptionUiModel = CategoryTypeOptionUiModel.OUTCOME
)