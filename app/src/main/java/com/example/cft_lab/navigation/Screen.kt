package com.example.cft_lab.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object SignUpScreen: Screen("signUp_screen")
    object StartScreen: Screen("start_screen")
}