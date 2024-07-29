package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.shreyanshsinghks.shoppingappuser.presentation.screens.LoginScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.SignUpScreenUI

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SubNavigation.LoginSignUpScreen) {

        navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen){
            composable<Routes.LoginScreen> {
                LoginScreenUI(navController = navController)
            }

            composable<Routes.SignUpScreen> {
                SignUpScreenUI(navController = navController)
            }
        }

        navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen){

            composable<Routes.HomeScreen> {
                Text(text = "Main Screen")
            }

            composable<Routes.ProfileScreen> {  }

            composable<Routes.WishListScreen> {  }

            composable<Routes.CartScreen> {  }

        }

        composable<Routes.ProductDetailsScreen> {  }

        composable<Routes.CheckoutScreen> {  }

    }
}