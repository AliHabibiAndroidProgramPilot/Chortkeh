package info.alihabibi.model.mapper

import info.alihabibi.domain.models.Currencies
import info.alihabibi.model.ui_model.CurrenciesOptionUiModel

fun Currencies.toUiOption(): CurrenciesOptionUiModel = when(this) {
    Currencies.TOMAN -> CurrenciesOptionUiModel.TOMAN
    Currencies.RIAL -> CurrenciesOptionUiModel.RIAL
}

fun CurrenciesOptionUiModel.toDomain(): Currencies = when(this) {
    CurrenciesOptionUiModel.TOMAN -> Currencies.TOMAN
    CurrenciesOptionUiModel.RIAL -> Currencies.RIAL
}