package info.alihabibi.datastore.di

import info.alihabibi.datastore.DatastoreManager
import org.koin.dsl.module

val datastoreModule = module {
    single { DatastoreManager(get()) }
}