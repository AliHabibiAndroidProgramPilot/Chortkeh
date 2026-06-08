package info.alihabibi.model.mapper

import info.alihabibi.domain.models.Genders
import info.alihabibi.model.ui_model.GenderOptionUiModel

fun Genders.toUiOption(): GenderOptionUiModel = when (this) {
    Genders.MEN -> GenderOptionUiModel.MEN
    Genders.WOMAN -> GenderOptionUiModel.WOMAN
    Genders.UNKNOWN -> GenderOptionUiModel.UNKNOWN
}

fun GenderOptionUiModel.toDomain(): Genders = when (this) {
    GenderOptionUiModel.MEN -> Genders.MEN
    GenderOptionUiModel.WOMAN -> Genders.WOMAN
    GenderOptionUiModel.UNKNOWN -> Genders.UNKNOWN
}