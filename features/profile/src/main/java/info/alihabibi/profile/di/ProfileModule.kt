package info.alihabibi.profile.di

import info.alihabibi.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel { ProfileViewModel(get()) }

}