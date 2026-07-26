package info.alihabibi.database.converters

import androidx.room.TypeConverter
import info.alihabibi.domain.models.category.CategoryIcon

class CategoryIconConverter {

    @TypeConverter
    fun fromCategoryIcon(icon: CategoryIcon): String = icon.name

    @TypeConverter
    fun toCategoryIcon(value: String): CategoryIcon = CategoryIcon.valueOf(value)

}