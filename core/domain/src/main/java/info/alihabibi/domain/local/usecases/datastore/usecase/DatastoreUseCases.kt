package info.alihabibi.domain.local.usecases.datastore.usecase

import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.GetIsSmsModalShownUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveSmsModalShownStateUseCase

data class DatastoreUseCases(
    val saveFirstLaunchUseCase: SaveFirstLaunchUseCase,
    val getIsAppFirstLaunchUseCase: GetIsAppFirstLaunchUseCase,
    val saveSmsModalShownStateUseCase: SaveSmsModalShownStateUseCase,
    val getIsSmsModalShownUseCase: GetIsSmsModalShownUseCase
)