package com.example.domain.usecase

import com.example.domain.models.ValidationResult

class ValidateLastNameUseCase {
    operator fun invoke(field: String): ValidationResult {
        if(field.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "The lastname needs to consist of at least 2 characters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}