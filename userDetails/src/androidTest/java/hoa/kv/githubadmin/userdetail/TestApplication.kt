package hoa.kv.githubadmin.userdetail

import android.app.Application
import hoa.kv.githubadmin.userdetail.di.instrumentedTestModule
import hoa.kv.githubadmin.userdetail.di.userDetailsModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

class TestApplication : Application() {

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            lazyModules(
                userDetailsModule,
                instrumentedTestModule
            )
        }
    }
}