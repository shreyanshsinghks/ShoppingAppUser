package com.shreyanshsinghks.shoppingappuser.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.shreyanshsinghks.shoppingappuser.data.repository.RepositoryImpl
import com.shreyanshsinghks.shoppingappuser.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideRepository(firebaseAuth: FirebaseAuth): Repository {
        return RepositoryImpl(firebaseAuth)
    }

}