package info.alihabibi.domain.local.usecases.database.category

import info.alihabibi.domain.local.repositories.CategoryRepository
import info.alihabibi.domain.models.category.Category

class SaveCategoryUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: Category): Long = repository.saveCategory(category)

}