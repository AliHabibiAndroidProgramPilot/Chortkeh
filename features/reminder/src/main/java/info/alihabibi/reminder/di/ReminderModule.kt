package info.alihabibi.reminder.di

import info.alihabibi.reminder.ReminderViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reminderModule = module {

    viewModel { ReminderViewModel() }

}