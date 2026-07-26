package info.alihabibi.model.ui_model.category

data class CategoryUiModel(
    val id: Int = 0,
    val title: String = "",
    val isDefault: Boolean = true,
    val icon: CategoryIconOptionUiModel = CategoryIconOptionUiModel.OTHERS,
    val type: CategoryTypeOptionUiModel = CategoryTypeOptionUiModel.OUTCOME
)