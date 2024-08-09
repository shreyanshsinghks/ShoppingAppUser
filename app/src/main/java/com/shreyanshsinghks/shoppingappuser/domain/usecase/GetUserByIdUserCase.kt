package com.shreyanshsinghks.shoppingappuser.domain.usecase

import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.UserDataParent
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUserCase @Inject constructor(private val repository: Repository) {
    fun getUserById(uid: String) : Flow<ResultState<UserDataParent>> {
        return repository.getUserByUID(uid)
    }
}