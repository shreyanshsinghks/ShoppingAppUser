package com.shreyanshsinghks.shoppingappuser.domain.models

data class ProductModel(
    var name: String = "",
    var price: String = "",
    var image: String = "",
    var category: String = "",
    var description: String = "",
    val date: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val imageUrl: String = ""
)