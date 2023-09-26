package com.example.domain.repository

import com.example.domain.models.User

interface UserRepository {

	suspend fun saveUser(data: User)

	suspend fun checkUserData(): Boolean

	suspend fun getUserName(): String
}