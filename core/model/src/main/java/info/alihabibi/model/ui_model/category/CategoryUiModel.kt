package info.alihabibi.model.ui_model.category

import androidx.annotation.DrawableRes
import info.alihabibi.designsystem.R

data class CategoryUiModel(
    val id: Int = 0,
    val title: String = "",
    val isDefault: Boolean = true,
    @get:DrawableRes val iconResId: Int = R.drawable.category_ic_others,
    val type: CategoryTypeOptionUiModel = CategoryTypeOptionUiModel.OUTCOME
)