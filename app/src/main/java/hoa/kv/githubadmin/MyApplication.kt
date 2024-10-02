package hoa.kv.githubadmin

import android.app.Application
import hoa.kv.githubadmin.landing.di.mainScreenModule
import hoa.kv.githubadmin.repositoryimpl.di.repositoryModule
import hoa.kv.githubadmin.userdetail.di.userDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

@OptIn(KoinExperimentalAPI::class)
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            lazyModules(
                mainScreenModule,
                userDetailsModule,
                repositoryModule
            )
        }
    }
}