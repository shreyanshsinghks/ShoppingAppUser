package com.shreyanshsinghks.shoppingappuser.domain.usecase

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.ProductModel
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllProductsUseCase @Inject constructor(private val repository: Repository) {
    fun getAllProducts(): Flow<ResultState<List<ProductModel>>>{
        return repository.getAllProducts()
    }
}

