package info.alihabibi.chortkeh

import android.app.Application
import android.os.StrictMode
import info.alihabibi.chortkeh.di.mainActivityModule
import info.alihabibi.database.di.databaseModule
import info.alihabibi.datastore.di.datastoreModule
import info.alihabibi.domain.di.domainModule
import info.alihabibi.home.di.homeModule
import info.alihabibi.new_transaction.di.newTransactionModule
import info.alihabibi.onboarding.di.onBoardingModule
import info.alihabibi.profile.viewmodel.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChortkehApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@ChortkehApp)
            modules(
                modules = listOf(
                    mainActivityModule,
                    domainModule,
                    datastoreModule,
                    databaseModule,
                    onBoardingModule,
                    homeModule,
                    newTransactionModule,
                    profileModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

}