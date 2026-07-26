package info.alihabibi.domain.local.usecases.database.usecase

import info.alihabibi.domain.local.usecases.database.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.SaveCategoryUseCase
import info.alihabibi.domain.local.usecases.database.UpdateCategoryUseCase

data class CategoryUseCases(
    val saveCategoryUseCase: SaveCategoryUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val deleteCategoriesUseCase: DeleteCategoriesUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase
)
