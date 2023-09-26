package com.example.cft_lab.di

import com.example.cft_lab.navigation.NavigationVM
import com.example.cft_lab.ui.home.HomeViewModel
import com.example.cft_lab.ui.sign_up.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    viewModel {
        SignUpViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        HomeViewModel(
            get()
        )
    }

    viewModel{
        NavigationVM(
            get()
        )
    }
}