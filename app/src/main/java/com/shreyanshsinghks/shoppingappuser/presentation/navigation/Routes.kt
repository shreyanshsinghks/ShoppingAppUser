package com.shreyanshsinghks.shoppingappuser.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object HomeScreen

    @Serializable
    object WishListScreen

    @Serializable
    object CartScreen

    @Serializable
    object ProductDetailsScreen

    @Serializable
    object CheckoutScreen

}