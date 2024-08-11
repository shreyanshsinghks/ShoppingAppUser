package com.shreyanshsinghks.shoppingappuser.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.CategoryModel
import com.shreyanshsinghks.shoppingappuser.domain.models.ProductModel
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.models.UserDataParent
import com.shreyanshsinghks.shoppingappuser.domain.usecase.CreateUserUseCase
import com.shreyanshsinghks.shoppingappuser.domain.usecase.GetAllProductsUseCase
import com.shreyanshsinghks.shoppingappuser.domain.usecase.GetCategoriesUseCase
import com.shreyanshsinghks.shoppingappuser.domain.usecase.GetUserByIdUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByIdUserCase: GetUserByIdUserCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SignUpScreenUiState())
    val uiState: StateFlow<SignUpScreenUiState> = _uiState.asStateFlow()

    private val _profileUiState = MutableStateFlow(ProfileScreenUiState())
    val profileUiState: StateFlow<ProfileScreenUiState> = _profileUiState.asStateFlow()

    private val _productUiState = MutableStateFlow(ProductScreenUiState())
    val productUiState: StateFlow<ProductScreenUiState> = _productUiState.asStateFlow()

    private val _uniqueCategories = MutableStateFlow<List<String>>(emptyList())
    val uniqueCategories = _uniqueCategories.asStateFlow()

    private val _getAllCategories = MutableStateFlow(AllCategoriesState())
    val getAllCategories = _getAllCategories.asStateFlow()

    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

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

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase.getAllProducts().collect {
                when (it) {
                    is ResultState.Success -> {
                        Log.d("ShoppingAppViewModel", "Products fetched successfully: ${it.data}")
                        _productUiState.value =
                            _productUiState.value.copy(productList = it.data, isLoading = false)
                        val uniqueCategories = it.data.map {singleCategory -> singleCategory.category }.distinct().sorted()
                        _uniqueCategories.value = uniqueCategories + listOf("All")
                    }

                    is ResultState.Error -> {
                        Log.e("ShoppingAppViewModel", "Error fetching products: ${it.message}")
                        _productUiState.value =
                            _productUiState.value.copy(error = it.message, isLoading = false)
                    }

                    ResultState.Loading -> {
                        Log.d("ShoppingAppViewModel", "Loading products...")
                        _productUiState.value = _productUiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun getUserById(uid: String) {
        viewModelScope.launch {
            getUserByIdUserCase.getUserById(uid).collect {
                when (it) {
                    is ResultState.Success -> {
                        // Handle success
                        _profileUiState.value =
                            _profileUiState.value.copy(
                                userDataParent = it.data,
                                isLoading = false
                            )
                    }

                    is ResultState.Error -> {
                        // Handle error
                        _profileUiState.value =
                            _profileUiState.value.copy(error = it.message, isLoading = false)
                    }

                    ResultState.Loading -> {
                        // Handle loading
                        _profileUiState.value = _profileUiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.getCategories().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getAllCategories.value =
                            _getAllCategories.value.copy(categoryList = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllCategories.value =
                            _getAllCategories.value.copy(error = it.message, isLoading = false)
                    }

                    ResultState.Loading -> {
                        _getAllCategories.value = _getAllCategories.value.copy(isLoading = true)
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

data class ProfileScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userDataParent: UserDataParent? = null,
)

data class ProductScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val productList: List<ProductModel>? = null,
)

data class AllCategoriesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val categoryList: List<CategoryModel>? = null,
)