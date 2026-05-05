package info.alihabibi.onboarding.di

import info.alihabibi.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {

    viewModel { OnBoardingViewModel(get()) }

}