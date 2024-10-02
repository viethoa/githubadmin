package hoa.kv.githubadmin.landing.di

import hoa.kv.githubadmin.landing.repository.FakeRepository
import hoa.kv.githubadmin.repository.user.UserRepository
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val instrumentedTestModule = lazyModule {
    single<UserRepository> { FakeRepository() }
}