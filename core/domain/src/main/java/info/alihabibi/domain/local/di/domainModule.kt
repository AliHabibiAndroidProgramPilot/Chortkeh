package info.alihabibi.domain.local.di

import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunch
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import org.koin.dsl.module

val domainModule = module {

    factory { GetIsAppFirstLaunch(get()) }

    factory { SaveFirstLaunchUseCase(get()) }

    factory {
        DatastoreUseCases(
            saveFirstLaunchUseCase = get(),
            getIsAppFirstLaunch = get()
        )
    }

}