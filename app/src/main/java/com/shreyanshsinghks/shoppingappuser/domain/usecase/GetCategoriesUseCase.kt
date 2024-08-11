package com.shreyanshsinghks.shoppingappuser.domain.usecase

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.CategoryModel
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: Repository ) {
    fun getCategories(): Flow<ResultState<List<CategoryModel>>> {
        return repository.getCategories()
    }
}