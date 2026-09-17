package info.alihabibi.domain.local.usecases.database.category

import info.alihabibi.domain.local.repositories.CategoryRepository
import info.alihabibi.domain.models.category.Category

class DeleteCategoriesUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(vararg categoryId: Long) = repository.deleteCategory(*categoryId)

}