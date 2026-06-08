package info.alihabibi.chortkeh

import android.app.Application
import android.os.StrictMode
import info.alihabibi.chortkeh.di.mainActivityModule
import info.alihabibi.datastore.di.datastoreModule
import info.alihabibi.domain.local.di.domainModule
import info.alihabibi.home.di.homeModule
import info.alihabibi.onboarding.di.onBoardingModule
import info.alihabibi.user_account_info.di.userAccountInfoModule
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
                    homeModule,
                    userAccountInfoModule
                )
            )
        }
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDialog()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

}