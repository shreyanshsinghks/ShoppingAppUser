package com.shreyanshsinghks.shoppingappuser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.usecase.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(private val createUserUseCase: CreateUserUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SignUpScreenUiState())
    val uiState: StateFlow<SignUpScreenUiState> = _uiState.asStateFlow()

    fun createUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect {
                when (it) {
                    is ResultState.Success -> {
                        // Handle success
                        _uiState.value = _uiState.value.copy(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        // Handle error
                        _uiState.value = _uiState.value.copy(error = it.message)
                    }

                    ResultState.Loading -> {
                        // Handle loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.loginUser(userData).collect {
                when (it) {
                    is ResultState.Success -> {
                        // Handle success
                        _uiState.value = _uiState.value.copy(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        // Handle error
                        _uiState.value = _uiState.value.copy(error = it.message)
                    }

                    ResultState.Loading -> {
                        // Handle loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}

data class SignUpScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: UserData? = null,
    val success: String? = null,
)