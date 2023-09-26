package com.example.cft_lab.di


import com.example.domain.usecase.CheckUserDataUseCase
import com.example.domain.usecase.GetUserNameUseCase
import com.example.domain.usecase.SaveUserDataUseCase
import com.example.domain.usecase.ValidateBirthDateUseCase
import com.example.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.domain.usecase.ValidateFirstNameUseCase
import com.example.domain.usecase.ValidateLastNameUseCase
import com.example.domain.usecase.ValidatePasswordUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SaveUserDataUseCase(get()) }
    factory { GetUserNameUseCase(get()) }
    factory { CheckUserDataUseCase(get()) }
    factory { ValidateFirstNameUseCase() }
    factory { ValidateLastNameUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateConfirmPasswordUseCase() }
    factory { ValidateBirthDateUseCase() }
}