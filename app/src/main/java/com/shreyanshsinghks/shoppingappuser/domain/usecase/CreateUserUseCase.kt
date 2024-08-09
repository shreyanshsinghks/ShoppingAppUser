package com.shreyanshsinghks.shoppingappuser.domain.usecase

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository: Repository ) {
    fun createUser(userData: UserData): Flow<ResultState<String>> {
        return repository.registerUserWithEmailAndPassword(userData)
    }

    fun loginUser(userData: UserData): Flow<ResultState<String>> {
        return repository.loginUserWithEmailAndPassword(userData)
    }
}