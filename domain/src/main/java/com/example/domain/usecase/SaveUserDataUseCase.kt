package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.domain.models.User

class SaveUserDataUseCase(private val repository: UserRepository) {

	suspend operator fun invoke(data: User) = repository.saveUser(data)
}