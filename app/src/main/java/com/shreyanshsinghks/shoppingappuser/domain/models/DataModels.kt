package com.shreyanshsinghks.shoppingappuser.domain.models

data class UserData(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
)

data class UserDataParent(
    val nodeId: String = "",
    val userData: UserData = UserData(),
)

data class CategoryModel (
    var name: String = "",
    val date: Long = System.currentTimeMillis(),
    val createdBy: String = "",
)