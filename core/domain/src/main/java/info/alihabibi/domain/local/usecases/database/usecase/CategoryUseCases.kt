package info.alihabibi.domain.local.usecases.database.usecase

import info.alihabibi.domain.local.usecases.database.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.SaveCategoryUseCase

data class CategoryUseCases(
    val saveCategoryUseCase: SaveCategoryUseCase,
    val deleteCategoriesUseCase: DeleteCategoriesUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase
)
