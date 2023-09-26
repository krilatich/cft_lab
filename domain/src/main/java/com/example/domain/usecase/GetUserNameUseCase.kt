package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.domain.models.User

class GetUserNameUseCase(private val repository: UserRepository) {

	suspend operator fun invoke() = repository.getUserName()
}