package info.alihabibi.reminder.di

import info.alihabibi.reminder.viewmodels.AddOrEditReminderViewModel
import info.alihabibi.reminder.viewmodels.ReminderListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reminderModule = module {

    viewModel { AddOrEditReminderViewModel(get()) }

    viewModel { ReminderListViewModel(get()) }

}