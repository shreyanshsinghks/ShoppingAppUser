package com.shreyanshsinghks.shoppingappuser.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.shreyanshsinghks.shoppingappuser.presentation.navigation.Routes
import com.shreyanshsinghks.shoppingappuser.presentation.viewmodel.ShoppingAppViewModel

@Composable
fun ProfileScreenUI(viewModel: ShoppingAppViewModel = hiltViewModel(), firebaseAuth: FirebaseAuth, navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        viewModel.getUserById(firebaseAuth.currentUser?.uid.toString())
    }

    val uiState = viewModel.profileUiState.collectAsStateWithLifecycle()

    if (uiState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else if (uiState.value.error.isNullOrEmpty().not()) {
        // Show error
        Text(text = uiState.value.error.toString())
    }
    else if (uiState.value.userDataParent != null) {
        // Show data
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Name : ${uiState.value.userDataParent?.userData?.name.toString()}")
            Text(text = "Email : ${uiState.value.userDataParent?.userData?.email.toString()}")
            Text(text = "Phone Number : ${uiState.value.userDataParent?.userData?.phone.toString()}")
            Button(onClick = {
                firebaseAuth.signOut()
                navController.navigate(Routes.LoginScreen)
            }) {
                Text(text = "Log Out")
            }
        }
    }


}