package info.alihabibi.model.ui_model.category

import androidx.annotation.DrawableRes
import info.alihabibi.designsystem.R

enum class CategoryIconOptionUiModel(
    @get:DrawableRes val iconResId: Int
) {
    FOOD(R.drawable.category_ic_food),
    SHOPPING(R.drawable.category_ic_shopping),
    HAVING_FUN(R.drawable.category_ic_having_fun),
    HOME(R.drawable.category_ic_home),
    CAR(R.drawable.category_ic_car),
    SALARY(R.drawable.category_ic_salary),
    INCOME(R.drawable.category_ic_income),
    OTHERS(R.drawable.category_ic_others),
    PROFIT(R.drawable.category_ic_profit),
    SUBSIDY(R.drawable.category_ic_subsidy),
    ACTIVITY(R.drawable.category_ic_activity),
    AI(R.drawable.category_ic_ai),
    AIRPLANE(R.drawable.category_ic_airplane),
    BANK(R.drawable.category_ic_bank),
    BITCOIN(R.drawable.category_ic_bitcoin),
    BOOK(R.drawable.category_ic_book),
    BOX(R.drawable.category_ic_box),
    CARD_SEND(R.drawable.category_ic_card_send),
    COFFEE(R.drawable.category_ic_coffee),
    COIN(R.drawable.category_ic_coin),
    COMPUTER(R.drawable.category_ic_computer),
    EDUCATION(R.drawable.category_ic_education),
    EMOJI_HAPPY(R.drawable.category_ic_emoji_happy),
    GAME(R.drawable.category_ic_game),
    GIFT(R.drawable.category_ic_gift),
    HEART(R.drawable.category_ic_heart),
    HOSPITAL(R.drawable.category_ic_hospital),
    LAMP(R.drawable.category_ic_lamp),
    PEOPLE(R.drawable.category_ic_people),
    PET(R.drawable.category_ic_pet),
    RECEIPT(R.drawable.category_ic_receipt),
    ROUTING(R.drawable.category_ic_routing),
    TICKET(R.drawable.category_ic_ticket)
}