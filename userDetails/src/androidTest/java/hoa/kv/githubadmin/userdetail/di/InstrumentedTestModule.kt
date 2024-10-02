package hoa.kv.githubadmin.userdetail.di

import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.userdetail.repository.FakeRepository
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val instrumentedTestModule = lazyModule {
    single<UserRepository> { FakeRepository() }
}