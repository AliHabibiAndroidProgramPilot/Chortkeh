package info.alihabibi.database.di

import androidx.room.Room
import info.alihabibi.database.AppDatabase
import info.alihabibi.database.repositories.CategoryRepositoryImpl
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.local.repositories.CategoryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Keys.DATABASE_NAME
        ).build()
    }

    // Dao's
    single { get<AppDatabase>().categoryDao() }

    // Repositories
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }

}