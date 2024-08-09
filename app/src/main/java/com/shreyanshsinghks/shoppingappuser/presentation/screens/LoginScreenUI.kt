package com.shreyanshsinghks.shoppingappuser.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.presentation.navigation.Routes
import com.shreyanshsinghks.shoppingappuser.presentation.navigation.SubNavigation
import com.shreyanshsinghks.shoppingappuser.presentation.viewmodel.ShoppingAppViewModel

@Composable
fun LoginScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val showDialog = remember { mutableStateOf(false) }

    if (state.value.isLoading) {
        // Show loading
        CircularProgressIndicator()
    } else if (state.value.error.isNullOrEmpty().not()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.value.error.toString())
        }
    } else if (state.value.success.isNullOrEmpty().not()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AlertDialog(onDismissRequest = { }, confirmButton = {
                Button(onClick = { navController.navigate(SubNavigation.MainHomeScreen) }) {
                    Text(text = "Go to home")
                }
            }, title = { Text(text = "Congratulations Login Successful") })
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(value = email, onValueChange = { email = it }, label = { Text(text = "Email") })
            Spacer(modifier = Modifier.height(20.dp))
            TextField(value = password, onValueChange = { password = it }, label = { Text(text = "Password") })
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                val userData = UserData(email = email, password = password, name = "", phone = "")
                viewModel.loginUser(userData)
                email = ""
                password = ""
            }) {
                Text(text = "Log In")
            }

            Button(onClick = { navController.navigate(Routes.SignUpScreen) }) {
                Text(text = "New User! Click to Sign Up")
            }
        }
    }
}