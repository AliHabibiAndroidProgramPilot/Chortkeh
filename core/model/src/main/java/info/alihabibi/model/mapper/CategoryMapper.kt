package info.alihabibi.model.mapper

import info.alihabibi.designsystem.R
import info.alihabibi.domain.models.category.Category
import info.alihabibi.domain.models.category.CategoryIcon
import info.alihabibi.domain.models.category.CategoryType
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel

fun Category.toUiModel(): CategoryUiModel =
    CategoryUiModel(
        id = id,
        title = title,
        iconResId = icon.toIconResId(),
        type = type.toUiOption()
    )

private fun CategoryIcon.toIconResId(): Int = when (this) {
    CategoryIcon.FOOD -> R.drawable.home
    CategoryIcon.SHOPPING -> R.drawable.home
    CategoryIcon.HAVING_FUN -> R.drawable.home
    CategoryIcon.HOME -> R.drawable.home
    CategoryIcon.CAR -> R.drawable.home
}

private fun CategoryType.toUiOption(): CategoryTypeOptionUiModel = when(this) {
    CategoryType.INCOME -> CategoryTypeOptionUiModel.INCOME
    CategoryType.OUTCOME -> CategoryTypeOptionUiModel.OUTCOME
}