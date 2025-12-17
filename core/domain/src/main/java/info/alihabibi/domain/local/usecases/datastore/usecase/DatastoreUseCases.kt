package info.alihabibi.domain.local.usecases.datastore.usecase

import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunch
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase

data class DatastoreUseCases(
    val saveFirstLaunchUseCase: SaveFirstLaunchUseCase,
    val getIsAppFirstLaunch: GetIsAppFirstLaunch
)