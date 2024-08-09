package com.shreyanshsinghks.shoppingappuser.domain.repository

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.ProductModel
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>

    fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>

    fun getUserByUID(uid: String): Flow<ResultState<UserDataParent>>

    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>

    fun getAllProducts(): Flow<ResultState<List<ProductModel>>>
}