package com.shreyanshsinghks.shoppingappuser.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shreyanshsinghks.shoppingappuser.common.ResultState
import com.shreyanshsinghks.shoppingappuser.common.USER_COLLECTION
import com.shreyanshsinghks.shoppingappuser.domain.models.ProductModel
import com.shreyanshsinghks.shoppingappuser.domain.models.UserData
import com.shreyanshsinghks.shoppingappuser.domain.models.UserDataParent
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : Repository {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(it.result.user?.uid.toString()).set(userData)
                            .addOnCompleteListener { result ->
                                if (result.isSuccessful) {
                                    trySend(ResultState.Success("User Registered Successfully"))
                                } else {
                                    trySend(ResultState.Error(result.exception?.localizedMessage.toString()))
                                }
                            }
                        trySend(ResultState.Success("User Registered Successfully"))
                    } else {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Logged In Successfully"))
                    } else {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun getUserByUID(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(uid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val data = it.result.toObject(UserData::class.java)
                if (data != null) {
                    val userDataParent = UserDataParent(it.result.id, data)
                    trySend(ResultState.Success(userDataParent))
                }
            } else {
                trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
            }
        }
        awaitClose {
            close()
        }

    }

    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
                .set(userDataParent.userData).addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Data Updated Successfully"))
                    } else {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun getAllProducts(): Flow<ResultState<List<ProductModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        val listenerRegistration = firebaseFirestore.collection("products")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(ResultState.Error(error.localizedMessage!!.toString()))
                }
                if (snapshot != null) {
                    val productList = snapshot.toObjects(ProductModel::class.java)
                    trySend(ResultState.Success(productList))
//                    Log.d("ShoppingAppViewModel", "ShoppingAppViewModel initialized${productList}")
                }
            }
        awaitClose{
            listenerRegistration.remove()
        }
    }
}