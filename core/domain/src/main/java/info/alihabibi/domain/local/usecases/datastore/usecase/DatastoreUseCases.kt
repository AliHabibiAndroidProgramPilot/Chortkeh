package info.alihabibi.domain.local.usecases.datastore.usecase

import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.GetIsSmsModalShownUseCase
import info.alihabibi.domain.local.usecases.datastore.GetPreferredCurrencyUseCase
import info.alihabibi.domain.local.usecases.datastore.GetUserAccountInfoUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.SavePreferredCurrencyUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveSmsModalShownStateUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveUserAccountInfoUseCase

data class DatastoreUseCases(
    val saveFirstLaunchUseCase: SaveFirstLaunchUseCase,
    val getIsAppFirstLaunchUseCase: GetIsAppFirstLaunchUseCase,
    val saveSmsModalShownStateUseCase: SaveSmsModalShownStateUseCase,
    val getIsSmsModalShownUseCase: GetIsSmsModalShownUseCase,
    val savePreferredCurrencyUseCase: SavePreferredCurrencyUseCase,
    val getPreferredCurrencyUseCase: GetPreferredCurrencyUseCase,
    val saveUserAccountInfoUseCase: SaveUserAccountInfoUseCase,
    val getUserAccountInfoUseCase: GetUserAccountInfoUseCase
)