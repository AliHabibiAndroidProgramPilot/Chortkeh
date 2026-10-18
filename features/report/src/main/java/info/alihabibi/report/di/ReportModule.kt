package info.alihabibi.report.di

import info.alihabibi.report.ReportViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {

    viewModel { ReportViewModel(get()) }

}