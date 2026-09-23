package info.alihabibi.domain.models.transaction

data class CategoryTransactionExpenses(
    val categoryId: Long,
    val categoryTitle: String,
    val totalAmount: Long
)
