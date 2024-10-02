package hoa.kv.githubadmin.landing.di

import hoa.kv.githubadmin.landing.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val mainScreenModule = lazyModule {
    viewModel<MainViewModel> { MainViewModel(userRepository = get()) }
}