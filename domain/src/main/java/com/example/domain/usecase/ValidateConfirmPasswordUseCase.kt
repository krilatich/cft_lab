package com.example.domain.usecase

import com.example.domain.models.ValidationResult

class ValidateConfirmPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}