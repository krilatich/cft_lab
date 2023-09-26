package com.example.cft_lab.ui.home

sealed class HomeIntent {
    object OpenHelloDialog:HomeIntent()
    object CloseHelloDialog:HomeIntent()
}