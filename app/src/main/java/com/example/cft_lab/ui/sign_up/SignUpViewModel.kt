package com.example.cft_lab.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecase.SaveUserDataUseCase
import com.example.domain.usecase.ValidateBirthDateUseCase
import com.example.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.domain.usecase.ValidateFirstNameUseCase
import com.example.domain.usecase.ValidateLastNameUseCase
import com.example.domain.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handle(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UpdateFirstName -> updateFirstName(intent.value)
            is SignUpIntent.UpdateLastName -> updateLastName(intent.value)
            is SignUpIntent.UpdateBirthDate -> updateBirthDate(intent.value)
            is SignUpIntent.UpdatePassword -> updatePassword(intent.value)
            is SignUpIntent.UpdatePasswordConfirm -> updatePasswordConfirm(
                password = intent.password,
                repeatedPassword = intent.repeatedPassword
            )

            is SignUpIntent.CreateUser -> createUser(intent.onValidated)
        }
    }


    private fun updateFirstName(value: String) {
        _uiState.update {
            it.copy(
                firstName = value,
                firstNameError = validateFirstNameUseCase(value).errorMessage
            )
        }
    }

    private fun updateLastName(value: String) {
        _uiState.update {
            it.copy(
                lastName = value,
                lastNameError = validateLastNameUseCase(value).errorMessage
            )
        }
    }

    private fun updateBirthDate(value: String) {
        _uiState.update {
            it.copy(
                birthDate = value,
                birthDateError = validateBirthDateUseCase(value).errorMessage
            )
        }
    }

    private fun updatePassword(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = validatePasswordUseCase(value).errorMessage
            )
        }
    }

    private fun updatePasswordConfirm(password: String, repeatedPassword: String) {
        _uiState.update {
            it.copy(
                passwordConfirm = repeatedPassword,
                passwordConfirmError = validateConfirmPasswordUseCase(
                    password = password,
                    repeatedPassword = repeatedPassword
                ).errorMessage
            )
        }
    }

    private fun createUser(onValidated: (Boolean) -> Unit) {
        val noErrors: Boolean =
            with(uiState.value) {
                (firstNameError == null
                        && lastNameError == null
                        && passwordError == null
                        && passwordConfirmError == null
                        && birthDateError == null)
            }

        if (noErrors) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                saveUserDataUseCase(
                    data = User(
                        firstName = uiState.value.firstName,
                        lastName = uiState.value.lastName,
                        birthDate = uiState.value.birthDate,
                        password = uiState.value.password
                    )
                )
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
        // Отправляем наличие ошибок
        onValidated(noErrors)
    }

    data class UiState(
        val firstName: String = "",
        val firstNameError: String? = null,
        val lastName: String = "",
        val lastNameError: String? = null,
        val birthDate: String = "",
        val birthDateError: String? = null,
        val password: String = "",
        val passwordError: String? = null,
        val passwordConfirm: String = "",
        val passwordConfirmError: String? = null,
        val isLoading: Boolean = false
    )

}