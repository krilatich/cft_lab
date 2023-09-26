package com.example.cft_lab.ui.sign_up


sealed class SignUpIntent {
    class UpdateFirstName(val value: String) : SignUpIntent()
    class UpdateLastName(val value: String) : SignUpIntent()
    class UpdateBirthDate(val value: String) : SignUpIntent()
    class UpdatePassword(val value: String) : SignUpIntent()
    class UpdatePasswordConfirm(val password: String, val repeatedPassword: String) : SignUpIntent()
    class CreateUser(val onValidated: (Boolean) -> Unit) : SignUpIntent()
}