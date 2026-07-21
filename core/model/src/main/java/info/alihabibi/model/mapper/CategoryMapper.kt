package info.alihabibi.model.mapper

import info.alihabibi.designsystem.R
import info.alihabibi.domain.models.category.Category
import info.alihabibi.domain.models.category.CategoryIcon
import info.alihabibi.domain.models.category.CategoryType
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel

fun Category.toUiModel(): CategoryUiModel =
    CategoryUiModel(
        id = id,
        title = title,
        isDefault = isDefault,
        iconResId = icon.toIconResId(),
        type = type.toUiOption()
    )

private fun CategoryIcon.toIconResId(): Int = when (this) {
    CategoryIcon.FOOD -> R.drawable.category_ic_food
    CategoryIcon.SHOPPING -> R.drawable.category_ic_shopping
    CategoryIcon.HAVING_FUN -> R.drawable.category_ic_having_fun
    CategoryIcon.HOME -> R.drawable.category_ic_home
    CategoryIcon.CAR -> R.drawable.category_ic_car
    CategoryIcon.SUBSIDY -> R.drawable.category_ic_subsidy
    CategoryIcon.INCOME -> R.drawable.category_ic_income
    CategoryIcon.OTHERS -> R.drawable.category_ic_others
    CategoryIcon.SALARY -> R.drawable.category_ic_salary
    CategoryIcon.PROFIT -> R.drawable.category_ic_profit
    CategoryIcon.ACTIVITY -> R.drawable.category_ic_activity
    CategoryIcon.AI -> R.drawable.category_ic_ai
    CategoryIcon.AIRPLANE -> R.drawable.category_ic_airplane
    CategoryIcon.BANK -> R.drawable.category_ic_bank
    CategoryIcon.BITCOIN -> R.drawable.category_ic_bitcoin
    CategoryIcon.BOOK -> R.drawable.category_ic_book
    CategoryIcon.BOX -> R.drawable.category_ic_box
    CategoryIcon.CARD_SEND -> R.drawable.category_ic_card_send
    CategoryIcon.COFFEE -> R.drawable.category_ic_coffee
    CategoryIcon.COIN -> R.drawable.category_ic_coin
    CategoryIcon.COMPUTER -> R.drawable.category_ic_computer
    CategoryIcon.EDUCATION -> R.drawable.category_ic_education
    CategoryIcon.EMOJI_HAPPY -> R.drawable.category_ic_emoji_happy
    CategoryIcon.GAME -> R.drawable.category_ic_game
    CategoryIcon.GIFT -> R.drawable.category_ic_gift
    CategoryIcon.HEART -> R.drawable.category_ic_heart
    CategoryIcon.HOSPITAL -> R.drawable.category_ic_hospital
    CategoryIcon.LAMP -> R.drawable.category_ic_lamp
    CategoryIcon.PEOPLE -> R.drawable.category_ic_people
    CategoryIcon.PET -> R.drawable.category_ic_pet
    CategoryIcon.RECEIPT -> R.drawable.category_ic_receipt
    CategoryIcon.ROUTING -> R.drawable.category_ic_routing
    CategoryIcon.TICKET -> R.drawable.category_ic_ticket
}

private fun CategoryType.toUiOption(): CategoryTypeOptionUiModel = when(this) {
    CategoryType.INCOME -> CategoryTypeOptionUiModel.INCOME
    CategoryType.OUTCOME -> CategoryTypeOptionUiModel.OUTCOME
}