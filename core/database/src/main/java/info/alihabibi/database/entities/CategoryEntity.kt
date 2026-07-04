package info.alihabibi.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.models.category.CategoryIcon
import info.alihabibi.domain.models.category.CategoryType

@Entity(tableName = Keys.CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val icon: CategoryIcon, // @TypeConverter -> CategoryIconConverter
    val type: CategoryType // @TypeConverter -> CategoryTypeConverter
)
