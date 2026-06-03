package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.models.Currencies
import kotlinx.coroutines.flow.Flow

class GetPreferredCurrencyUseCase(
    private val repository: DatastoreRepository
) {

    operator fun invoke(): Flow<Currencies> = repository.preferredCurrency

}