package hoa.kv.githubadmin.userdetail.di

import hoa.kv.githubadmin.userdetail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val userDetailsModule = lazyModule {
    viewModel<UserDetailViewModel> { UserDetailViewModel(userRepository = get()) }
}