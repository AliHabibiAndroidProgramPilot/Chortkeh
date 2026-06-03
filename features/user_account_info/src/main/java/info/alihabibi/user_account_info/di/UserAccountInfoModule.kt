package info.alihabibi.user_account_info.di

import info.alihabibi.user_account_info.UserAccountInfoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val userAccountInfoModule = module {

    viewModel { UserAccountInfoViewModel(get()) }

}