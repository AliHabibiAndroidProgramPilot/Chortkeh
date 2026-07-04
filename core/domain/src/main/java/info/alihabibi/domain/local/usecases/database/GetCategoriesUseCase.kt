package info.alihabibi.domain.local.usecases.database

import info.alihabibi.domain.local.repositories.CategoryRepository
import info.alihabibi.domain.models.category.Category
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> = repository.getCategories()

}