package hoa.kv.githubadmin.repositoryimpl.di

import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.repositoryimpl.user.UserRepositoryImpl
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val repositoryModule = lazyModule {
    includes(databaseModule, resourceModule)
    single<UserRepository> { UserRepositoryImpl(userRemoteResource = get(), userLocalResource = get()) }
}