package info.alihabibi.datastore.di

import info.alihabibi.datastore.DatastoreManager
import info.alihabibi.datastore.DatastoreRepositoryImpl
import info.alihabibi.domain.local.repositories.DatastoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {

    single { DatastoreManager(androidContext()) }

    single<DatastoreRepository> { DatastoreRepositoryImpl(get()) }

}