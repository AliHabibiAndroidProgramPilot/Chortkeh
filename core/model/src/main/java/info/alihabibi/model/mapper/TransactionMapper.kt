package info.alihabibi.model.mapper

import info.alihabibi.domain.models.TransactionType
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel

fun TransactionType.toUiOption(): TransactionTypeOptionUiModel = when(this) {
    TransactionType.INCOME -> TransactionTypeOptionUiModel.INCOME
    TransactionType.OUTCOME -> TransactionTypeOptionUiModel.OUTCOME
}

fun TransactionTypeOptionUiModel.toDomain(): TransactionType = when(this) {
    TransactionTypeOptionUiModel.INCOME -> TransactionType.INCOME
    TransactionTypeOptionUiModel.OUTCOME -> TransactionType.OUTCOME
}