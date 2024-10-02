package hoa.kv.githubadmin.landing

import android.app.Application
import hoa.kv.githubadmin.landing.di.instrumentedTestModule
import hoa.kv.githubadmin.landing.di.mainScreenModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

class TestApplication : Application() {

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            lazyModules(
                mainScreenModule,
                instrumentedTestModule
            )
        }
    }
}