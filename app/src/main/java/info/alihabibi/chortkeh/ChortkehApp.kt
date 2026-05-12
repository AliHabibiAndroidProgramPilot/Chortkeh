package info.alihabibi.chortkeh

import android.app.Application
import info.alihabibi.chortkeh.di.mainActivityModule
import info.alihabibi.datastore.di.datastoreModule
import info.alihabibi.domain.local.di.domainModule
import info.alihabibi.home.di.homeModule
import info.alihabibi.onboarding.di.onBoardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChortkehApp : Application() {

    override fun onCreate() {
        startKoin {
            androidContext(androidContext = this@ChortkehApp)
            modules(
                modules = listOf(
                    mainActivityModule,
                    domainModule,
                    datastoreModule,
                    onBoardingModule,
                    homeModule
                )
            )
        }
        super.onCreate()
    }

}