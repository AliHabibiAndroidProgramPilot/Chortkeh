package info.alihabibi.channels.di

import info.alihabibi.channels.ChannelsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val channelsModule = module {

    viewModel { ChannelsViewModel(get()) }

}