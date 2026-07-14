package info.alihabibi.domain.local.defaults

import info.alihabibi.domain.models.category.Category
import info.alihabibi.domain.models.category.CategoryIcon
import info.alihabibi.domain.models.category.CategoryType

object DefaultCategories {

    val categories: List<Category> = listOfNotNull(
        Category(
            title = "خوردنی",
            isDefault = true,
            icon = CategoryIcon.FOOD,
            type = CategoryType.OUTCOME
        ),
        Category(
            title = "خریدنی",
            isDefault = true,
            icon = CategoryIcon.SHOPPING,
            type = CategoryType.OUTCOME
        ),
        Category(
            title = "خوش گذرونی",
            isDefault = true,
            icon = CategoryIcon.HAVING_FUN,
            type = CategoryType.OUTCOME
        ),
        Category(
            title = "خونه",
            isDefault = true,
            icon = CategoryIcon.HOME,
            type = CategoryType.OUTCOME
        ),
        Category(
            title = "ماشین",
            isDefault = true,
            icon = CategoryIcon.CAR,
            type = CategoryType.OUTCOME
        ),
        Category(
            title = "حقوق",
            isDefault = true,
            icon = CategoryIcon.SALARY,
            type = CategoryType.INCOME
        ),
        Category(
            title = "درآمد",
            isDefault = true,
            icon = CategoryIcon.INCOME,
            type = CategoryType.INCOME
        ),
        Category(
            title = "یارانه",
            isDefault = true,
            icon = CategoryIcon.SUBSIDY,
            type = CategoryType.INCOME
        ),
        Category(
            title = "سود",
            isDefault = true,
            icon = CategoryIcon.PROFIT,
            type = CategoryType.INCOME
        ),
        Category(
            title = "سایر",
            isDefault = true,
            icon = CategoryIcon.OTHERS,
            type = CategoryType.INCOME
        )
    )

}