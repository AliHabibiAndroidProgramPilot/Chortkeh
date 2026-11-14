package info.alihabibi.announcements.di

import info.alihabibi.announcements.AnnouncementViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val announcementsModule = module {

    viewModel { AnnouncementViewModel(get(), get()) }

}