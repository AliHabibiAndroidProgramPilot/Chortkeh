package info.alihabibi.domain.models.category

data class Category(
    val id: Int,
    val title: String,
    val icon: CategoryIcon,
    val type: CategoryType
)
