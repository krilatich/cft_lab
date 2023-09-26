package com.example.cft_lab.di


import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserRepository> {
        get<UserRepositoryImpl>()
    }

    single {
        UserRepositoryImpl(get())
    }


}