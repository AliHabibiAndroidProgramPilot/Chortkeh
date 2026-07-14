package info.alihabibi.database.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import info.alihabibi.common.ApplicationScope
import info.alihabibi.database.AppDatabase
import info.alihabibi.database.repositories.CategoryRepositoryImpl
import info.alihabibi.database.seeding.CategorySeedCallback
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.local.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Keys.DATABASE_NAME
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .addCallback(
                CategorySeedCallback(
                    scope = get<ApplicationScope>(),
                    databaseProvider = { get() }
                )
            )
            .build()
    }

    // Dao's
    single { get<AppDatabase>().categoryDao() }

    // Repositories
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }

}