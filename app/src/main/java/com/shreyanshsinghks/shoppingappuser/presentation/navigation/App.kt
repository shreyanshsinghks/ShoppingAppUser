package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shreyanshsinghks.shoppingappuser.presentation.screens.CartScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.HomeScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.LoginScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.OnboardingScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.ProfileScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.SignUpScreenUI
import com.shreyanshsinghks.shoppingappuser.presentation.screens.WishListScreenUI

@Composable
fun App(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val startDestination = if (firebaseAuth.currentUser != null) {
        SubNavigation.MainHomeScreen
    } else {
        SubNavigation.LoginSignUpScreen
    }

    val bottomBarItems = listOf(
        BottomBarItem("Home", Icons.Default.Home),
        BottomBarItem("WishList", Icons.Default.Favorite),
        BottomBarItem("Cart", Icons.Default.ShoppingCart),
        BottomBarItem("Profile", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            if (firebaseAuth.currentUser != null) {
                NavigationBar {
                    bottomBarItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                when (index) {
                                    0 -> navController.navigate(Routes.HomeScreen)
                                    1 -> navController.navigate(Routes.WishListScreen)
                                    2 -> navController.navigate(Routes.CartScreen)
                                    3 -> navController.navigate(Routes.ProfileScreen)
                                }
                            },
                            icon = {
                                Image(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            },
                            label = {
                                if (selectedIndex == index) {
                                    Text(text = item.name)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
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

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {

                    composable<Routes.HomeScreen> {
                        HomeScreenUI(navController = navController)
                    }

                    composable<Routes.ProfileScreen> {
                        ProfileScreenUI(firebaseAuth = firebaseAuth, navController = navController)
                    }

                    composable<Routes.WishListScreen> {
                        WishListScreenUI(navController = navController)
                    }

                    composable<Routes.CartScreen> {
                        CartScreenUI(navController = navController)
                    }

                }

                composable<Routes.ProductDetailsScreen> { }

                composable<Routes.CheckoutScreen> { }

            }
        }

    }

}

data class BottomBarItem(val name: String, val icon: ImageVector)
