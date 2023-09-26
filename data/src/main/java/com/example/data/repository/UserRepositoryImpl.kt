package com.example.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.repository.UserRepository
import com.example.domain.models.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


const val DataStore_NAME = "USER_DATA"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)


class UserRepositoryImpl(private val context: Context) : UserRepository {

    companion object {
        val FIRST_NAME = stringPreferencesKey("FIRST_NAME")
        val LAST_NAME = stringPreferencesKey("LAST_NAME")
        val BIRTH_DATE = stringPreferencesKey("BIRTH_DATE")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }

    override suspend fun saveUser(data: User) {
        context.datastore.edit { user ->
            user[FIRST_NAME] = data.firstName
            user[LAST_NAME] = data.lastName
            user[BIRTH_DATE] = data.birthDate
            user[PASSWORD] = data.password
        }
    }

    override suspend fun checkUserData(): Boolean =
        context.datastore.data.map { user ->
            user[FIRST_NAME] != null
        }.first()


    override suspend fun getUserName(): String =
        context.datastore.data.map { user ->
            "${user[FIRST_NAME]} ${user[LAST_NAME]}"
        }.first()

}