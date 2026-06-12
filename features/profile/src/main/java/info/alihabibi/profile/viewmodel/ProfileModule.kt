package info.alihabibi.profile.viewmodel

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel { ProfileViewModel(get()) }

}