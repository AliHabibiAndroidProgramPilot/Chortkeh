package info.alihabibi.domain.di

import info.alihabibi.domain.local.usecases.database.category.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.SaveCategoryUseCase
import info.alihabibi.domain.local.usecases.database.category.UpdateCategoryUseCase
import info.alihabibi.domain.local.usecases.database.category.usecase.CategoryUseCases
import info.alihabibi.domain.local.usecases.database.channel.DeleteChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.GetChannelsUseCase
import info.alihabibi.domain.local.usecases.database.channel.SaveChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.UpdateChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
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

    // region Datastore

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

    // endregion

    // region Category

    factory { SaveCategoryUseCase(get()) }
    factory { DeleteCategoriesUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { UpdateCategoryUseCase(get()) }

    factory {
        CategoryUseCases(
            saveCategoryUseCase = get(),
            getCategoriesUseCase = get(),
            deleteCategoriesUseCase = get(),
            updateCategoryUseCase = get()
        )
    }

    // endregion

    // region Channel

    factory { SaveChannelUseCase(get()) }
    factory { DeleteChannelUseCase(get()) }
    factory { GetChannelsUseCase(get()) }
    factory { UpdateChannelUseCase(get()) }

    factory {
        ChannelUseCases(
            saveChannelUseCase = SaveChannelUseCase(get()),
            getChannelUseCase = GetChannelsUseCase(get()),
            deleteChannelUseCase = DeleteChannelUseCase(get()),
            updateChannelUseCase = UpdateChannelUseCase(get())
        )
    }

    // endregion

}