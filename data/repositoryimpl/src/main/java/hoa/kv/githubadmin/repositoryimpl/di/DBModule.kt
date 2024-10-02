package hoa.kv.githubadmin.repositoryimpl.di

import hoa.kv.githubadmin.repositoryimpl.db.AppDatabase
import hoa.kv.githubadmin.repositoryimpl.user.UserDao
import org.koin.dsl.module

internal val databaseModule = module {
    single<AppDatabase> { AppDatabase.buildDatabase(context = get()) }
    single<UserDao> { get<AppDatabase>().userDao() }
}