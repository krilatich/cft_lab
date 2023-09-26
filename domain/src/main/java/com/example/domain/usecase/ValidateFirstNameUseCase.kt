package com.example.domain.usecase

import com.example.domain.models.ValidationResult

class ValidateFirstNameUseCase {
    operator fun invoke(field: String): ValidationResult {
        if(field.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "The firstname needs to consist of at least 2 characters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}