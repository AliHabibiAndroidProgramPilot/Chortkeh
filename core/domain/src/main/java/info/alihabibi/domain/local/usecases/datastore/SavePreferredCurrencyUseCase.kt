package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.models.Currencies

class SavePreferredCurrencyUseCase(
    private val repository: DatastoreRepository
) {

    suspend operator fun invoke(value: Currencies) = repository.savePreferredCurrency(value.name)

}