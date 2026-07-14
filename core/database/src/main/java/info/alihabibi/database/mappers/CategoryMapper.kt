package info.alihabibi.database.mappers

import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.domain.models.category.Category

fun CategoryEntity.asExternalModel(): Category = Category(
    id = id.toInt(),
    title = title,
    isDefault = isDefault,
    icon = icon,
    type = type
)

fun Category.asEntity(): CategoryEntity = CategoryEntity(
    id = id.toLong(),
    title = title,
    isDefault = isDefault,
    icon = icon,
    type = type
)