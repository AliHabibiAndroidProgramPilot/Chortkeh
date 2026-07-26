package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

    suspend fun saveCategory(category: Category): Long

    suspend fun saveCategory(categories: List<Category>): List<Long>

    suspend fun deleteCategory(categories: List<Category>)

    suspend fun updateCategory(category: Category)

}