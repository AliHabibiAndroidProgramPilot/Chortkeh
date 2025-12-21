package info.alihabibi.chortkeh

import android.app.Application
import info.alihabibi.chortkeh.di.mainActivityModule
import org.koin.core.context.startKoin
import info.alihabibi.datastore.di.datastoreModule
import info.alihabibi.domain.local.di.domainModule
import org.koin.android.ext.koin.androidContext

class ChortkehApp : Application() {

    override fun onCreate() {
        startKoin {
            androidContext(androidContext = this@ChortkehApp)
            modules(
                modules = listOf(
                    mainActivityModule,
                    domainModule,
                    datastoreModule
                )
            )
        }
        super.onCreate()
    }

}