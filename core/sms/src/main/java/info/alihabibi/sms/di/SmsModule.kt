package info.alihabibi.sms.di

import info.alihabibi.domain.local.repositories.SmsSendersSourceRepository
import info.alihabibi.sms.SmsSendersSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val smsModule = module {

    single<SmsSendersSourceRepository> { SmsSendersSourceImpl(androidContext()) }

}