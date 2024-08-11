package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                BottomNavigationBar(selectedIndex, bottomBarItems) { index ->
                    if (index != selectedIndex) {
                        selectedIndex = index
                        when (index) {
                            0 -> navController.navigate(Routes.HomeScreen)
                            1 -> navController.navigate(Routes.WishListScreen)
                            2 -> navController.navigate(Routes.CartScreen)
                            3 -> navController.navigate(Routes.ProfileScreen)
                        }
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

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    items: List<BottomBarItem>,
    onItemClick: (Int) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.LightGray)
        )
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            items.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = index == selectedIndex,
                    onItemClick = { onItemClick(index) }
                )
            }
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
    item: BottomBarItem,
    isSelected: Boolean = false,
    onItemClick: () -> Unit
) {
    val iconColor = if (isSelected) Color.Black else Color.LightGray
    val textColor = if (isSelected) Color.Black else Color.LightGray
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onItemClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            tint = iconColor,
            modifier = Modifier.size(24.dp),
        )
        if (isSelected) {
            Text(
                text = item.name,
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}


data class BottomBarItem(val name: String, val icon: ImageVector)
