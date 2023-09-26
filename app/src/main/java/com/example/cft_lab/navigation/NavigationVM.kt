package com.example.cft_lab.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.CheckUserDataUseCase
import kotlinx.coroutines.launch

class NavigationVM(
    private val checkUserDataUseCase: CheckUserDataUseCase
) : ViewModel() {

    fun checkUserUseCase(onDataExist: (Boolean) -> Unit) {
        viewModelScope.launch {
            onDataExist(checkUserDataUseCase())
        }
    }
}