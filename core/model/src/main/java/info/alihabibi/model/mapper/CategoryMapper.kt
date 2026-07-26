package info.alihabibi.model.mapper

import info.alihabibi.domain.models.category.Category
import info.alihabibi.domain.models.category.CategoryIcon
import info.alihabibi.domain.models.category.CategoryType
import info.alihabibi.model.ui_model.category.CategoryIconOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel

fun Category.toUiModel(): CategoryUiModel =
    CategoryUiModel(
        id = id,
        title = title,
        isDefault = isDefault,
        icon = icon.toUiOption(),
        type = type.toUiOption()
    )

fun CategoryUiModel.toDomain(): Category =
    Category(
        id = id,
        title = title,
        isDefault = isDefault,
        icon = icon.toDomain(),
        type = type.toDomain()
    )

private fun CategoryType.toUiOption(): CategoryTypeOptionUiModel = when (this) {
    CategoryType.INCOME -> CategoryTypeOptionUiModel.INCOME
    CategoryType.OUTCOME -> CategoryTypeOptionUiModel.OUTCOME
}

private fun CategoryIcon.toUiOption(): CategoryIconOptionUiModel = CategoryIconOptionUiModel.valueOf(name)


private fun CategoryTypeOptionUiModel.toDomain(): CategoryType = when (this) {
    CategoryTypeOptionUiModel.INCOME -> CategoryType.INCOME
    CategoryTypeOptionUiModel.OUTCOME -> CategoryType.OUTCOME
}

private fun CategoryIconOptionUiModel.toDomain(): CategoryIcon = CategoryIcon.valueOf(name)