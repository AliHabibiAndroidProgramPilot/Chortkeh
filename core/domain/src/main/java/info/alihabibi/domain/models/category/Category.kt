package info.alihabibi.domain.models.category

data class Category(
    val id: Int = 0,
    val title: String,
    val isDefault: Boolean,
    val icon: CategoryIcon,
    val type: CategoryType
)
