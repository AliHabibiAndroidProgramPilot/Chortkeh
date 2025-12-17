package info.alihabibi.datastore.di

import info.alihabibi.datastore.DatastoreManager
import info.alihabibi.datastore.DatastoreRepositoryImpl
import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunch
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {

    single { DatastoreManager(androidContext()) }

    single<DatastoreRepository> { DatastoreRepositoryImpl(get()) }

    factory { GetIsAppFirstLaunch(get()) }

    factory { SaveFirstLaunchUseCase(get()) }

    factory {
        DatastoreUseCases(
            saveFirstLaunchUseCase = get(),
            getIsAppFirstLaunch = get()
        )
    }

}