package info.alihabibi.domain.local.di

import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.GetIsSmsModalShownUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveSmsModalShownStateUseCase
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import org.koin.dsl.module

val domainModule = module {

    factory { GetIsAppFirstLaunchUseCase(get()) }
    factory { SaveFirstLaunchUseCase(get()) }

    factory { SaveSmsModalShownStateUseCase(get()) }
    factory { GetIsSmsModalShownUseCase(get()) }

    factory {
        DatastoreUseCases(
            saveFirstLaunchUseCase = get(),
            getIsAppFirstLaunchUseCase = get(),
            saveSmsModalShownStateUseCase = get(),
            getIsSmsModalShownUseCase = get()
        )
    }

}