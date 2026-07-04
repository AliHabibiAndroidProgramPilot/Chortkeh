package info.alihabibi.database.converters

import androidx.room.TypeConverter
import info.alihabibi.domain.models.category.CategoryType

class CategoryTypeConverter {

    @TypeConverter
    fun fromCategoryType(type: CategoryType): String = type.name

    @TypeConverter
    fun toCategoryType(value: String): CategoryType = CategoryType.valueOf(value)

}