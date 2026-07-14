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
        isDefault = isDefault,
        iconResId = icon.toIconResId(),
        type = type.toUiOption()
    )

private fun CategoryIcon.toIconResId(): Int = when (this) {
    CategoryIcon.FOOD -> R.drawable.category_food
    CategoryIcon.SHOPPING -> R.drawable.category_shopping
    CategoryIcon.HAVING_FUN -> R.drawable.category_having_fun
    CategoryIcon.HOME -> R.drawable.category_home
    CategoryIcon.CAR -> R.drawable.category_car
    CategoryIcon.SUBSIDY -> R.drawable.category_subsidy
    CategoryIcon.INCOME -> R.drawable.category_income
    CategoryIcon.OTHERS -> R.drawable.category_others
    CategoryIcon.SALARY -> R.drawable.category_salary
    CategoryIcon.PROFIT -> R.drawable.category_profit
}

private fun CategoryType.toUiOption(): CategoryTypeOptionUiModel = when(this) {
    CategoryType.INCOME -> CategoryTypeOptionUiModel.INCOME
    CategoryType.OUTCOME -> CategoryTypeOptionUiModel.OUTCOME
}