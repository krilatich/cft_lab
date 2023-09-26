package com.example.domain.usecase

import com.example.domain.models.ValidationResult

class ValidateBirthDateUseCase {
    operator fun invoke(field: String): ValidationResult {
        return ValidationResult(
            successful = true
        )
    }
}