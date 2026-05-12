package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository

class SaveSmsModalShownStateUseCase(
    private val repository: DatastoreRepository
) {

    suspend operator fun invoke(value: Boolean) = repository.saveSmsModalShownState(value)

}