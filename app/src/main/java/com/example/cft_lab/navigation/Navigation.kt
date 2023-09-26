package com.example.cft_lab.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cft_lab.ui.home.HomeScreen
import com.example.cft_lab.ui.home.HomeViewModel
import com.example.cft_lab.ui.sign_up.SignUpScreen
import com.example.cft_lab.ui.sign_up.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {

        composable(Screen.StartScreen.route) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            val vm: NavigationVM = koinViewModel()
            vm.checkUserUseCase {
                if (it) navController.navigate(Screen.HomeScreen.route)
                else navController.navigate(Screen.SignUpScreen.route)
            }
        }

        composable(Screen.SignUpScreen.route) {
            val vm: SignUpViewModel = koinViewModel()
            val uiState by vm.uiState.collectAsState()
            SignUpScreen(
                onSuccess = {
                    navController.navigate(Screen.HomeScreen.route)
                },
                uiState = uiState,
                onIntent = vm::handle
            )
        }

        composable(Screen.HomeScreen.route) {
            val vm: HomeViewModel = koinViewModel()
            val uiState by vm.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                onIntent = vm::handle
            )
        }
    }
}