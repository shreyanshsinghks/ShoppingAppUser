package com.shreyanshsinghks.shoppingappuser.domain.repository

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>

    fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
}