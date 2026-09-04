package info.alihabibi.domain.local.usecases.database.category.usecase

import info.alihabibi.domain.local.usecases.database.category.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.SaveCategoryUseCase
import info.alihabibi.domain.local.usecases.database.category.UpdateCategoryUseCase

data class CategoryUseCases(
    val saveCategoryUseCase: SaveCategoryUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val deleteCategoriesUseCase: DeleteCategoriesUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase
)
