package hoa.kv.githubadmin.repositoryimpl.di

import hoa.kv.githubadmin.repositoryimpl.user.UserLocalResource
import hoa.kv.githubadmin.repositoryimpl.user.UserRemoteResource
import org.koin.dsl.module

internal val resourceModule = module {
    single<UserRemoteResource> { UserRemoteResource() }
    single<UserLocalResource> { UserLocalResource(userDao = get()) }
}