package com.shreyanshsinghks.shoppingappuser.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth): Repository {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password).addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResultState.Success("User Registered Successfully"))
            }else{
                trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
            }
        }
        awaitClose{
            close()
        }
    }

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password).addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResultState.Success("User Logged In Successfully"))
            }else{
                trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
            }
        }
        awaitClose{
            close()
        }
    }
}