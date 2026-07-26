package info.alihabibi.domain.di

import info.alihabibi.domain.local.usecases.database.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.SaveCategoryUseCase
import info.alihabibi.domain.local.usecases.database.UpdateCategoryUseCase
import info.alihabibi.domain.local.usecases.database.usecase.CategoryUseCases
import info.alihabibi.domain.local.usecases.datastore.GetIsAppFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.GetIsSmsModalShownUseCase
import info.alihabibi.domain.local.usecases.datastore.GetPreferredCurrencyUseCase
import info.alihabibi.domain.local.usecases.datastore.GetUserAccountInfoUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveFirstLaunchUseCase
import info.alihabibi.domain.local.usecases.datastore.SavePreferredCurrencyUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveSmsModalShownStateUseCase
import info.alihabibi.domain.local.usecases.datastore.SaveUserAccountInfoUseCase
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import org.koin.dsl.module

val domainModule = module {

    factory { GetIsAppFirstLaunchUseCase(get()) }
    factory { SaveFirstLaunchUseCase(get()) }
    factory { SaveSmsModalShownStateUseCase(get()) }
    factory { GetIsSmsModalShownUseCase(get()) }
    factory { SavePreferredCurrencyUseCase(get()) }
    factory { GetPreferredCurrencyUseCase(get()) }
    factory { SaveUserAccountInfoUseCase(get()) }
    factory { GetUserAccountInfoUseCase(get()) }

    factory {
        DatastoreUseCases(
            saveFirstLaunchUseCase = get(),
            getIsAppFirstLaunchUseCase = get(),
            saveSmsModalShownStateUseCase = get(),
            getIsSmsModalShownUseCase = get(),
            savePreferredCurrencyUseCase = get(),
            getPreferredCurrencyUseCase = get(),
            saveUserAccountInfoUseCase = get(),
            getUserAccountInfoUseCase = get()
        )
    }

    factory { SaveCategoryUseCase(get()) }
    factory { DeleteCategoriesUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { UpdateCategoryUseCase(get()) }

    factory {
        CategoryUseCases(
            getCategoriesUseCase = get(),
            saveCategoryUseCase = get(),
            deleteCategoriesUseCase = get(),
            updateCategoryUseCase = get()
        )
    }

}