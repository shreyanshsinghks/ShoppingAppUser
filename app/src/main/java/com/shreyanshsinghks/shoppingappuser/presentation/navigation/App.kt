package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shreyanshsinghks.shoppingappuser.presentation.screens.HomeScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.LoginScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.OnboardingScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.ProfileScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.SignUpScreenUI

@Composable
fun App(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    val startDestination = if (firebaseAuth.currentUser != null) {
        SubNavigation.MainHomeScreen
    } else {
        SubNavigation.LoginSignUpScreen
    }

    NavHost(navController = navController, startDestination = startDestination) {

        navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.OnBoardingScreen) {
            composable<Routes.OnBoardingScreen> {
                OnboardingScreenUI(navController = navController)
            }

            composable<Routes.LoginScreen> {
                LoginScreenUI(navController = navController)
            }

            composable<Routes.SignUpScreen> {
                SignUpScreenUI(navController = navController)
            }
        }

        navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.ProfileScreen) {

            composable<Routes.HomeScreen> {
                HomeScreenUI(navController = navController)
            }

            composable<Routes.ProfileScreen> {
                ProfileScreenUI(firebaseAuth = firebaseAuth, navController = navController)
            }

            composable<Routes.WishListScreen> { }

            composable<Routes.CartScreen> { }

        }

        composable<Routes.ProductDetailsScreen> { }

        composable<Routes.CheckoutScreen> { }

    }
}