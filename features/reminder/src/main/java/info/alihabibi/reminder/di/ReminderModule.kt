package info.alihabibi.reminder.di

import info.alihabibi.reminder.notification.ReminderNotifier
import info.alihabibi.reminder.scheduler.AlarmManagerReminderScheduler
import info.alihabibi.reminder.scheduler.ReminderScheduler
import info.alihabibi.reminder.viewmodels.AddOrEditReminderViewModel
import info.alihabibi.reminder.viewmodels.ReminderListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reminderModule = module {

    single<ReminderScheduler> { AlarmManagerReminderScheduler(androidContext()) }

    single { ReminderNotifier(androidContext()) }

    viewModel { ReminderListViewModel(get()) }

    viewModel { AddOrEditReminderViewModel(get(), get()) }

}