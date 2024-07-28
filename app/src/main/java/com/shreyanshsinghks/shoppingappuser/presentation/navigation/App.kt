package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shreyanshsinghks.shoppingappuser.presentation.screens.LogInScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.SignUpScreenUI

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LoginScreen) {
        composable<Routes.LoginScreen> {
            LogInScreenUI()
        }

        composable<Routes.SignUpScreen> {
            SignUpScreenUI()
        }

    }
}