package info.alihabibi.database.repositories

import info.alihabibi.database.dao.CategoryDao
import info.alihabibi.database.entities.CategoryEntity
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.CategoryRepository
import info.alihabibi.domain.models.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val dao: CategoryDao
) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> {
        return dao.getCategories()
            .map { entities -> entities.map(CategoryEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun saveCategory(category: Category): Long {
        return withContext(Dispatchers.IO) {
            dao.insertCategory(category.asEntity())
        }
    }

    override suspend fun saveCategory(categories: List<Category>): List<Long> {
        return withContext(Dispatchers.IO) {
            val categoryEntities = categories.map(Category::asEntity)
            dao.insertCategory(categoryEntities)
        }
    }

}