package info.alihabibi.domain.di

import info.alihabibi.domain.local.coordinators.ReminderUndoManager
import info.alihabibi.domain.local.coordinators.TransactionUndoManager
import info.alihabibi.domain.local.usecases.database.category.DeleteCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.GetCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.category.SaveCategoryUseCase
import info.alihabibi.domain.local.usecases.database.category.UpdateCategoryUseCase
import info.alihabibi.domain.local.usecases.database.category.usecase.CategoryUseCases
import info.alihabibi.domain.local.usecases.database.channel.DeleteChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.GetChannelByIdUseCase
import info.alihabibi.domain.local.usecases.database.channel.GetChannelsUseCase
import info.alihabibi.domain.local.usecases.database.channel.GetTotalBalanceUseCase
import info.alihabibi.domain.local.usecases.database.channel.SaveChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.UpdateChannelBalanceUseCase
import info.alihabibi.domain.local.usecases.database.channel.UpdateChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.local.usecases.database.reminder.DeleteReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.GetAllRemindersUseCase
import info.alihabibi.domain.local.usecases.database.reminder.SaveReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.domain.local.usecases.database.transaction.DeleteTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetAllTransactionsUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetLastTransactions
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthExpensesByAllCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalExpensesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalIncomeUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetTransactionByIdUseCase
import info.alihabibi.domain.local.usecases.database.transaction.HasOutcomeTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.HasTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.SaveTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.UpdateTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
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
    factory { GetChannelsUseCase(get()) }
    factory { DeleteChannelUseCase(get()) }
    factory { UpdateChannelUseCase(get()) }
    factory { GetChannelByIdUseCase(get()) }
    factory { GetTotalBalanceUseCase(get()) }
    factory { UpdateChannelBalanceUseCase(get()) }

    factory {
        ChannelUseCases(
            saveChannelUseCase = get(),
            getChannelsUseCase = get(),
            deleteChannelUseCase = get(),
            updateChannelUseCase = get(),
            getChannelByIdUseCase = get(),
            getTotalBalanceUseCase = get(),
            updateChannelBalanceUseCase = get()
        )
    }

    // endregion

    // region Transaction

    factory { SaveTransactionUseCase(get()) }
    factory { UpdateTransactionUseCase(get()) }
    factory { DeleteTransactionUseCase(get()) }
    factory { GetMonthTotalIncomeUseCase(get()) }
    factory { GetMonthTotalExpensesUseCase(get()) }
    factory { HasTransactionUseCase(get()) }
    factory { HasOutcomeTransactionUseCase(get()) }
    factory { GetMonthExpensesByAllCategoriesUseCase(get()) }
    factory { GetLastTransactions(get()) }
    factory { GetAllTransactionsUseCase(get()) }
    factory { GetTransactionByIdUseCase(get()) }

    factory {
        TransactionUseCases(
            saveTransactionUseCase = get(),
            updateTransactionUseCase = get(),
            deleteTransactionUseCase = get(),
            getMonthTotalIncomeUseCase = get(),
            getMonthTotalExpensesUseCase = get(),
            hasTransactionUseCases = get(),
            hasOutcomeTransactionUseCase = get(),
            getMonthExpensesByAllCategoriesUseCase = get(),
            getLastTransactions = get(),
            getAllTransactions = get(),
            getTransactionByIdUseCase = get()
        )
    }

    // endregion

    // region Reminder

    factory { SaveReminderUseCase(get()) }
    factory { DeleteReminderUseCase(get()) }
    factory { GetAllRemindersUseCase(get()) }

    factory {
        ReminderUseCases(
            saveReminderUseCases = get(),
            deleteReminderUseCase = get(),
            getAllRemindersUseCase = get()
        )
    }

    // endregion

    // region coordinators

    single {
        TransactionUndoManager(get(), get(), get())
    }

    single {
        ReminderUndoManager(get(), get())
    }

    // endregion

}
