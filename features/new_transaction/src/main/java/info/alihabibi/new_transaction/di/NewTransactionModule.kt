package info.alihabibi.new_transaction.di

import info.alihabibi.new_transaction.NewTransactionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val newTransactionModule = module {

    viewModel { NewTransactionViewModel() }

}