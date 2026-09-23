package info.alihabibi.model.ui_model.transaction

data class CategoryTransactionExpensesUiModel(
    val categoryId: Long,
    val categoryTitle: String,
    // Chart only accepts Float
    val totalAmount: Float
)
