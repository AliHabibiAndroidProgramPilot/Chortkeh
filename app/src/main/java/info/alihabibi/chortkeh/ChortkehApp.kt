package info.alihabibi.chortkeh

import android.app.Application
import org.koin.core.context.startKoin
import info.alihabibi.datastore.di.datastoreModule
import org.koin.android.ext.koin.androidContext

class ChortkehApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@ChortkehApp)
            modules(
                modules = listOf(datastoreModule)
            )
        }
    }

}