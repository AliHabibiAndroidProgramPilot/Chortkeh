package info.alihabibi.chortkeh.di

import info.alihabibi.chortkeh.activity.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainActivityModule = module {

    viewModel {
        MainActivityViewModel(get())
    }

}