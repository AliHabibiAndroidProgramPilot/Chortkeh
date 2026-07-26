package info.alihabibi.common.di

import info.alihabibi.common.ApplicationScope
import org.koin.dsl.module

val commonModule = module {

    single { ApplicationScope() }

}