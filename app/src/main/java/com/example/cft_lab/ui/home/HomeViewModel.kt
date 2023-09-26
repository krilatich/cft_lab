package com.example.cft_lab.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserName: GetUserNameUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handle(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OpenHelloDialog -> openHelloDialog()
            is HomeIntent.CloseHelloDialog -> closeHelloDialog()
        }
    }

    private fun openHelloDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    dialogIsOpen = true,
                    isLoading = true
                )
            }
            _uiState.update {
                it.copy(
                    userName = getUserName(),
                    isLoading = false
                )
            }
        }
    }

    private fun closeHelloDialog() {
        _uiState.update {
            it.copy(
                dialogIsOpen = false
            )
        }
    }

    private fun getName() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userName = getUserName(),
                    isLoading = false
                )
            }
        }
    }

    data class UiState(
        val dialogIsOpen: Boolean = false,
        val isLoading: Boolean = false,
        val userName: String = ""
    )

}